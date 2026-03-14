-- tabella utente
CREATE TABLE UTENTE
(
    id_utente NUMBER PRIMARY KEY,
    nome      VARCHAR2(50) NOT NULL,
    cognome   VARCHAR2(50) NOT NULL,
    username  VARCHAR2(50) NOT NULL,
    password  VARCHAR2(255) NOT NULL,
    ruolo     VARCHAR2(50) NOT NULL,
    UNIQUE (username)
);


-- tabella conto
CREATE TABLE CONTO
(
    id_conto    NUMBER PRIMARY KEY,
    descrizione VARCHAR2(50) NOT NULL,
    UNIQUE (descrizione)
);


-- tabella categoria
CREATE TABLE CATEGORIA
(
    id_categoria NUMBER PRIMARY KEY,
    categoria    VARCHAR2(50) NOT NULL,
    UNIQUE (categoria)
);


-- tabella hashtag
CREATE TABLE HASHTAG
(
    id_hashtag NUMBER PRIMARY KEY,
    hashtag    VARCHAR2(50) NOT NULL,
    UNIQUE (hashtag)
);


-- tabella movimento
CREATE TABLE MOVIMENTO
(
    id_movimento NUMBER PRIMARY KEY,
    data         DATE NOT NULL,
    tipologia    VARCHAR2(50) CHECK (tipologia IN ('ENTRATA', 'USCITA')) NOT NULL,
    id_conto          NOT NULL,
    titolo       VARCHAR2(100) NOT NULL,
    importo      NUMBER(10,2) NOT NULL,
    commento     VARCHAR2(255),
    id_categoria,
    id_hashtag,
    id_ricevente,
    UNIQUE (id_conto, data, importo, titolo, id_categoria),
    CONSTRAINT fk_conto FOREIGN KEY (id_conto) REFERENCES CONTO (id_conto),
    CONSTRAINT fk_categoria FOREIGN KEY (id_categoria) REFERENCES CATEGORIA (id_categoria),
    CONSTRAINT fk_hashtag FOREIGN KEY (id_hashtag) REFERENCES HASHTAG (id_hashtag),
    CONSTRAINT fk_utente FOREIGN KEY (id_ricevente) REFERENCES UTENTE (id_utente)
);