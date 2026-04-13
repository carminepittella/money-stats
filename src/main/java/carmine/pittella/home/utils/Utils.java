package carmine.pittella.home.utils;

import carmine.pittella.home.exception.InternalServerErrorException;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class Utils {

    public static LocalDate getDataInizioMese (LocalDate dataRiferimento) {
        checkDataValida(dataRiferimento);
        return dataRiferimento.with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate getDataFineMese (LocalDate dataRiferimento) {
        checkDataValida(dataRiferimento);
        return dataRiferimento.with(TemporalAdjusters.lastDayOfMonth());
    }


    /*========================== PRIVATE ==========================*/
    private static void checkDataValida (LocalDate data) {
        if (data == null) {
            throw new InternalServerErrorException("VALIDAZIONE_DATA", "è stata inserita una data non valida");
        }
    }
}
