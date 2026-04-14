package carmine.pittella.home.bean;

import carmine.pittella.home.model.dto.MovimentoDto;
import carmine.pittella.home.model.dto.response.StatisticsResponseDto;
import carmine.pittella.home.model.enums.IntervalStatsEnum;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticServiceBean {

    public void prova () {

        // TODO: dati che saranno valorizzati in seguito
        List<MovimentoDto> movimentiList = new ArrayList<>();
        LocalDate dataInizio = LocalDate.now();
        LocalDate dataFine = LocalDate.now();

        Period age = Period.between(dataInizio, dataFine);

        Map<IntervalStatsEnum, List<Double>> statsInterval = this.initializeMapStats();
        Map<IntervalStatsEnum, List<Double>> statsIntervalIn = this.initializeMapStats();
        Map<IntervalStatsEnum, List<Double>> statsIntervalOut = this.initializeMapStats();

        double sumDay = 0;
        double sumDayIn = 0;
        double sumDayOut = 0;

        double sumWeek = 0;
        double sumWeekIn = 0;
        double sumWeekOut = 0;

        double sumMonth = 0;
        double sumMonthIn = 0;
        double sumMonthOut = 0;

        // ecc...



        //TODO: sbagliatissimo !!! cosa se ci sono 185564611 giorni di differenza? cosa facciamo?
        // non è detto che che tutti quei movimenti...
        for (int i = 0; i < age.getDays(); i++) {
            double importo = movimentiList.get(i).getImporto();

            if (importo > 0) {
                sumDayIn += importo;
                sumWeekIn += importo;
                sumMonthIn += importo;
                // ecc...
            } else {
                sumDayOut += importo;
                sumWeekOut += importo;
                sumMonthOut += importo;
                // ecc...
            }
            sumDay += importo;
            sumWeek += importo;
            sumMonth += importo;
            // ecc...

            // giorno
            statsInterval.get(IntervalStatsEnum.GIORNO).add(sumDay);
            statsIntervalIn.get(IntervalStatsEnum.GIORNO).add(sumDayIn);
            statsIntervalOut.get(IntervalStatsEnum.GIORNO).add(sumDayOut);
            sumDay = 0;
            sumDayIn = 0;
            sumDayOut = 0;

            // settimana
            if (i % 7 == 0) {
                statsInterval.get(IntervalStatsEnum.SETTIMANA).add(sumWeek);
                statsIntervalIn.get(IntervalStatsEnum.SETTIMANA).add(sumWeekIn);
                statsIntervalOut.get(IntervalStatsEnum.SETTIMANA).add(sumWeekOut);
                sumWeek = 0;
                sumWeekIn = 0;
                sumWeekOut = 0;
            }

            // mese
            if (i % 30 == 0) {
                statsInterval.get(IntervalStatsEnum.MESE).add(sumMonth);
                statsIntervalIn.get(IntervalStatsEnum.MESE).add(sumMonthIn);
                statsIntervalOut.get(IntervalStatsEnum.MESE).add(sumMonthOut);
                sumMonth = 0;
                sumMonthIn = 0;
                sumMonthOut = 0;
            }

            // ecc...


        }


    }


    // inizializza tutte le mappe con la chiave Interval, e una lista vuota
    private Map<IntervalStatsEnum, List<Double>> initializeMapStats () {
        Map<IntervalStatsEnum, List<Double>> map = new HashMap<>();
        map.put(IntervalStatsEnum.GIORNO, new ArrayList<>());
        map.put(IntervalStatsEnum.SETTIMANA, new ArrayList<>());
        map.put(IntervalStatsEnum.MESE, new ArrayList<>());
        map.put(IntervalStatsEnum.TRIMESTRE, new ArrayList<>());
        map.put(IntervalStatsEnum.ANNO, new ArrayList<>());
        return map;
    }


}

