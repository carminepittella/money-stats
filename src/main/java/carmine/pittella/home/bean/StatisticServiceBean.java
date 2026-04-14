package carmine.pittella.home.bean;

import carmine.pittella.home.model.dto.MovimentoDto;
import carmine.pittella.home.model.dto.response.StatisticsResponseDto;
import carmine.pittella.home.model.enums.IntervalStatsEnum;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class StatisticServiceBean {

    public void prova () {

        IntervalStatsEnum interval = IntervalStatsEnum.MESE;

        LocalDate dataInizio = LocalDate.now();
        LocalDate dataFine = LocalDate.now();

        Period age = Period.between(dataInizio, dataFine);

        int divisore = 0;

        switch (interval) {
            case GIORNO: {
                divisore = Math.max(age.getDays(), 1);
                break;
            }

            case SETTIMANA: {
                divisore = Math.max(age.getDays() / 7, 1);
                break;
            }

            case MESE: {
                divisore = Math.max(age.getMonths(), 1);
                break;
            }

            case TRIMESTRE: {
                divisore = Math.max((age.getMonths() / 3), 3);
                break;
            }

            case ANNO: {
                divisore = Math.max(age.getYears(), 1);
                break;
            }
        }

        List<MovimentoDto> movimentiList = new ArrayList<>();

        double totale = 0;
        double totaleIn = 0;
        double totaleOut = 0;

        for (MovimentoDto m : movimentiList) {
            double importo = m.getImporto();

            if (importo > 0) {
                totaleIn += importo;
            } else {
                totaleOut += importo;
            }
            totale += importo;
        }


        StatisticsResponseDto statisticsDto = new StatisticsResponseDto();

        statisticsDto.setMedia(totale / divisore);
        statisticsDto.setMediaIn(totaleIn / divisore);
        statisticsDto.setMediaOut(totaleOut / divisore);

        List<Double> statsInterval;
        List<Double> statsIntervalIn;
        List<Double> statsIntervalOut;

        for (int i = 0; i < divisore; i++) {

        }


    }


}


























