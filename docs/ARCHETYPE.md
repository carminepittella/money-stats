# Archetipo di Deployment Render: BE Quarkus + Oracle Autonomous DB (OCI)

Guida di riferimento e standard operativo completo per la configurazione, containerizzazione e pubblicazione a costo zero di un backend REST basato su **Quarkus (Java 21)** su piattaforma **Render**, integrato con **Oracle Cloud Infrastructure (OCI) Autonomous Database** tramite connessione TLS senza Wallet.

---

## 1. Configurazione Database su Oracle Cloud (OCI)

Per eliminare la gestione di file binari proprietari (`cwallet.sso`, `ewallet.p12`) all'interno dell'ambiente containerizzato, si impiega la connessione diretta standard TLS su protocollo `tcps`.

### A. Rete e Sicurezza
1. Accedi alla console **Oracle Cloud Infrastructure (OCI)** e apri la scheda dell'istanza **Autonomous Database**.
2. Nella sezione **Rete (Network)**:
    * **Autenticazione mTLS (Mutual TLS):** imposta su **Non richiesto (Not Required)** o autorizza l'opzione **TLS**.
    * **Tipo di accesso alla rete:** seleziona **Consenti accesso sicuro da qualsiasi posizione** (*Secure access from everywhere*).
        * *In alternativa con ACL attiva:* aggiungi una voce di tipo **Blocco CIDR** con valore `0.0.0.0/0`.
3. Attendi che l'istanza termini l'aggiornamento e ritorni allo stato **Disponibile**.

### B. Stringa di Connessione JDBC
1. Nella pagina del database, seleziona **Connessione al database (Database connection)**.
2. Nel selettore **Autenticazione TLS**, scegli **TLS** (invece di *Mutual TLS*).
3. Copia la stringa di connessione relativa al profilo target (es. `medium` o `low`):
   ```text
   (description= (retry_count=20)(retry_delay=3)(address=(protocol=tcps)(port=1521)(host=adb.<regione>.oraclecloud.com))(connect_data=(service_name=<nome_servizio>.adb.oraclecloud.com))(security=(ssl_server_dn_match=yes)))
   ```
4. Aggiungi il prefisso del driver JDBC per ottenere il valore finale:
   ```text
   jdbc:oracle:thin:@(description=(retry_count=20)(retry_delay=3)(address=(protocol=tcps)(port=1521)(host=adb.<regione>.oraclecloud.com))(connect_data=(service_name=<nome_servizio>.adb.oraclecloud.com))(security=(ssl_server_dn_match=yes)))
   ```

---

## 2. File di Configurazione del Progetto

### A. `../src/main/resources/application.properties`
Configurazione ottimizzata per il binding dinamico delle porte, policy CORS abilitate per test/Postman e credenziali caricate tramite variabili di runtime.

```properties
# ==========================================
# RETE E BINDING PORTA
# ==========================================
# Necessario per ascoltare su tutte le interfacce all'esterno del container
quarkus.http.host=0.0.0.0
# Render inietta la porta a runtime tramite la variabile d'ambiente PORT
quarkus.http.port=${PORT:8080}

# ==========================================
# CORS (DISPOSIZIONE FRONTEND & TEST)
# ==========================================
quarkus.http.cors=true
quarkus.http.cors.origins=${CORS_ORIGINS:http://localhost:4200}
quarkus.http.cors.methods=GET,PUT,POST,DELETE,OPTIONS
quarkus.http.cors.headers=accept,authorization,content-type,x-requested-with

# ==========================================
# DATASOURCE ORACLE (PROFILO PROD)
# ==========================================
%prod.quarkus.datasource.db-kind=oracle
%prod.quarkus.datasource.jdbc.url=${ORACLE_DB_URL}
%prod.quarkus.datasource.username=${ORACLE_DB_USER}
%prod.quarkus.datasource.password=${ORACLE_DB_PASSWORD}

# ==========================================
# OTTIMIZZAZIONE CONNECTION POOL (FREE TIER)
# ==========================================
%prod.quarkus.datasource.jdbc.min-size=2
%prod.quarkus.datasource.jdbc.max-size=5
```

---

### B. `../Dockerfile` (Multi-Stage Build nella root del progetto)
Pipeline di build e runtime che compila direttamente da sorgente con Maven (senza dipendenza dai wrapper locali `mvnw`) ed esegue l'applicazione limitando il consumo di RAM per non incorrere in crash OOM (Exit Code 137).

```dockerfile
# ==========================================
# STAGE 1: Compilazione con Maven
# ==========================================
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Cache delle dipendenze: scarica i plugin e le librerie prima dei sorgenti
COPY ../pom.xml ./
RUN mvn dependency:go-offline -B

# Copia dei sorgenti ed esecuzione del packaging Quarkus
COPY ../src ./src
RUN mvn package -DskipTests -B

# ==========================================
# STAGE 2: Immagine Runtime Minimale
# ==========================================
FROM registry.access.redhat.com/ubi9/openjdk-21-runtime:1.24
ENV LANGUAGE='it_IT:it'
USER 185
WORKDIR /deployments

# Copia dei layout fast-jar generati da Quarkus
COPY --from=builder --chown=185 /app/target/quarkus-app/lib/ /deployments/lib/
COPY --from=builder --chown=185 /app/target/quarkus-app/*.jar /deployments/
COPY --from=builder --chown=185 /app/target/quarkus-app/app/ /deployments/app/
COPY --from=builder --chown=185 /app/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080

# Esecuzione diretta con limitazione di memoria per Free Tier (Max Heap: 320MB su 512MB)
CMD ["java", "-Xms128m", "-Xmx320m", "-Dquarkus.http.host=0.0.0.0", "-jar", "/deployments/quarkus-run.jar"]
```

---

### C. `.dockerignore` (nella root del progetto)
Impedisce il caricamento nel daemon Docker di file locali pesanti, configurazioni di IDE e chiavi riservate.

```text
target/
.git
.github
.idea
*.iml
.env
sql/
*.md
```

---

## 3. Configurazione del Web Service su Render

1. Accedi alla dashboard di [Render](https://dashboard.render.com).
2. Clicca su **New +** e seleziona **Web Service**.
3. Connetti il repository Git (**Public Git Repository** oppure autorizzazione diretta dell'account GitHub).
4. Configura i parametri generali:
    * **Name:** Nome dell'applicazione (genera l'URL: `https://<nome>.onrender.com`).
    * **Region:** `Frankfurt (EU Central)` (per minimizzare la latenza con OCI Milan).
    * **Branch:** `main` (o il branch di default del repository).
    * **Root Directory:** Lascia vuoto se `pom.xml` e `Dockerfile` risiedono nella radice del repository; altrimenti indica la sottocartella specifica.
    * **Runtime / Language:** `Docker`.
    * **Instance Type:** `Free`.

---

## 4. Environment Variables su Render

All'interno della sezione **Environment** del Web Service, registra i seguenti valori:

| Chiave (Key) | Descrizione / Valore Tipo |
| :--- | :--- |
| `QUARKUS_PROFILE` | `prod` |
| `ORACLE_DB_USER` | Utente del database (es. `ADMIN` o utente dedicato) |
| `ORACLE_DB_PASSWORD` | Password dell'utente database |
| `ORACLE_DB_URL` | Stringa completa: `jdbc:oracle:thin:@(description=...)` |
| `CORS_ORIGINS` | `http://localhost:4200` (da aggiornare in seguito con il dominio del frontend) |
| `JWT_PUBLIC_KEY` | *(Opzionale)* Chiave pubblica RSA per verifica JWT |
| `JWT_PRIVATE_KEY` | *(Opzionale)* Chiave privata RSA per firma JWT |

---

## 5. Risoluzione Errori Comuni

* **`Port scan timeout reached, no open ports detected`**:  
  Il container non risponde sulla porta assegnata da Render. Assicurati che `application.properties` contenga `quarkus.http.port=${PORT:8080}` e `quarkus.http.host=0.0.0.0`.
* **`Multiple garbage collectors selected`**:  
  Conflitto generato da script container RedHat UBI invocati insieme a flag GC personalizzati. Si risolve avviando direttamente via `CMD ["java", "-jar", ...]` nel Dockerfile.
* **`Failed to validate Maven distribution SHA-256`**:  
  Discrepanza di checksum sul wrapper `./mvnw` dentro Linux. Si risolve invocando il binario di sistema `mvn` dell'immagine builder.
* **`IO Error: Got minus one from a read call`**:  
  Il database rifiuta la connessione a livello di handshake TLS o rete. Verifica che su OCI la rete accetti connessioni da `0.0.0.0/0` (o *Secure access from everywhere*) e che la stringa JDBC usi porta `1521` (o `1522`) con protocollo `tcps`.
* **Cold Start / Latenza iniziale**:  
  Sul piano gratuito di Render, le istanze inattive per più di 15 minuti entrano in sleep mode. La prima richiesta HTTP dopo lo sleep impiega circa 30–50 secondi; le chiamate successive rispondono istantaneamente.