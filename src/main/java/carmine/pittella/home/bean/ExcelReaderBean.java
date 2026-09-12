package carmine.pittella.home.bean;

import carmine.pittella.home.exception.InternalServerErrorException;
import carmine.pittella.home.model.dto.CategoriaDto;
import carmine.pittella.home.model.dto.ContoDto;
import carmine.pittella.home.model.dto.HashtagDto;
import carmine.pittella.home.model.dto.MovimentoDto;
import carmine.pittella.home.model.enums.TipologiaEnum;
import carmine.pittella.home.service.CategoriaService;
import carmine.pittella.home.service.ContoService;
import carmine.pittella.home.service.ExcelReaderService;
import carmine.pittella.home.service.HashtagService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class ExcelReaderBean implements ExcelReaderService {

    private final ContoService contoService;
    private final CategoriaService categoriaService;
    private final HashtagService hashtagService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ITALIAN);
    private final DateTimeFormatter stringDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final int COL_CONTO = 0;
    private final int COL_DATA = 1;
    private final int COL_IMPORTO = 2;
    private final int COL_TITOLO = 6;
    private final int COL_RICEVENTE = 7;
    private final int COL_CATEGORIA = 9;
    private final int COL_COMMENTO = 10;
    private final int COL_HASHTAG = 11;

    @Override
    public List<MovimentoDto> extractMovimenti (FileUpload fileUpload) {
        List<MovimentoDto> movimenti = new ArrayList<>();

        // Strutture dati per inizializzare i "Set" e mantenere i riferimenti univoci
        Map<String, ContoDto> contiMap = new HashMap<>();
        Map<String, CategoriaDto> categorieMap = new HashMap<>();
        Map<String, HashtagDto> hashtagMap = new HashMap<>();

        try (InputStream is = Files.newInputStream(fileUpload.uploadedFile());
             Workbook wb = new XSSFWorkbook(is)) {

            if (wb.getNumberOfSheets() == 0) {
                throw new InternalServerErrorException("IMPORT_MOVIMENTI", "File Excel vuoto o senza fogli");
            }

            // Scorro tutti i fogli del workbook (tutti i mesi)
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet sheet = wb.getSheetAt(i);

                for (Row row : sheet) {
                    // Salto la prima riga di intestazione
                    if (row.getRowNum() == 0) continue;

                    Cell cellConto = row.getCell(COL_CONTO);
                    // Controllo di validità base: se il conto non è specificato probabile riga vuota
                    if (cellConto == null || cellConto.getCellType() == CellType.BLANK) {
                        continue;
                    }

                    // 1. Estrazione Importo e logica per la Tipologia
                    double importoVal = this.getNumericValue(row.getCell(COL_IMPORTO));
                    Double importo = importoVal;
                    TipologiaEnum tipologia = importoVal < 0 ? TipologiaEnum.USCITA : TipologiaEnum.ENTRATA;

                    // 2. Estrazione Data
                    LocalDateTime data = this.getLocalDateTime(row.getCell(COL_DATA));

                    // 3. Estrazione Titoli, Categorie, ecc (gestendo i possibili null)
                    String titolo = this.getStringValue(row.getCell(COL_TITOLO));
                    String commento = this.getStringValue(row.getCell(COL_COMMENTO));
                    String contoStr = this.getStringValue(cellConto);
                    String catStr = this.getStringValue(row.getCell(COL_CATEGORIA));
                    String hashStr = this.getStringValue(row.getCell(COL_HASHTAG));

                    // 4. Popolamento e Recupero degli oggetti univoci tramite computeIfAbsent
                    ContoDto contoDto = null;
                    if (contoStr != null && !contoStr.isBlank()) {
                        contoDto = contiMap.computeIfAbsent(contoStr, contoService::findOrCreate);
                    }

                    CategoriaDto categoriaDto = null;
                    if (catStr != null && !catStr.isBlank()) {
                        categoriaDto = categorieMap.computeIfAbsent(catStr, categoriaService::findOrCreate);
                    }

                    HashtagDto hashtagDto = null;
                    if (hashStr != null && !hashStr.isBlank()) {
                        hashtagDto = hashtagMap.computeIfAbsent(hashStr, hashtagService::findOrCreate);
                    }

                    // 5. Creazione del MovimentoDto finale
                    MovimentoDto movimento = MovimentoDto.builder()
                            .data(data)
                            .tipologia(tipologia)
                            .titolo(titolo)
                            .importo(importo)
                            .commento(commento)
                            .conto(contoDto)
                            .categoria(categoriaDto)
                            .hashtag(hashtagDto)
                            .ricevente(null) //TODO: futura implementazione
                            .build();
                    movimenti.add(movimento);
                }
            }

            log.info("Estrazione completata: {} movimenti totali. Set univoci trovati -> Conti: {}, Categorie: {}, Hashtag: {}",
                    movimenti.size(), contiMap.size(), categorieMap.size(), hashtagMap.size());

        } catch (IOException e) {
            throw new InternalServerErrorException("IMPORT_MOVIMENTI", "Errore durante l'estrazione dei Movimenti", e.getMessage());
        }

        return movimenti;
    }

    // --- Helper Methods per gestire i tipi di celle di Apache POI ---

    private String getStringValue (Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue()).replaceAll("\\.0*$", "");
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> null;
        };
    }

    private double getNumericValue (Cell cell) {
        if (cell == null) return 0.0;
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Double.parseDouble(cell.getStringCellValue().replace(",", "."));
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }

    private LocalDateTime getLocalDateTime (Cell cell) {
        if (cell == null) return null;

        // Se in Excel la data è nativamente formattata come Date
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue();
        }
        // Se in Excel è testuale
        else if (cell.getCellType() == CellType.STRING) {
            String dateStr = cell.getStringCellValue().trim();
            try {
                // Tenta prima il parse con ISO formattazione (yyyy-MM-dd)
                return LocalDate.parse(dateStr, stringDateFormatter).atStartOfDay();
            } catch (Exception e1) {
                try {
                    // Fallback sul tuo formatter personalizzato
                    return LocalDate.parse(dateStr, formatter).atStartOfDay();
                } catch (Exception e2) {
                    log.warn("Errore di parse della data testuale: {}", dateStr);
                }
            }
        }
        return null;
    }
}
