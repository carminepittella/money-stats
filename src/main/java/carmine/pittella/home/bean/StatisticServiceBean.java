package carmine.pittella.home.bean;

import carmine.pittella.home.model.dto.MovimentoDto;
import carmine.pittella.home.model.dto.response.StatisticsResponseDto;
import carmine.pittella.home.model.enums.IntervalStatsEnum;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticServiceBean {

    private int daysDiff = 0;

    public StatisticsResponseDto prova (List<MovimentoDto> movimentiList, LocalDate dataInizio, LocalDate dataFine) {
        StatisticsResponseDto response = new StatisticsResponseDto();

        daysDiff = (int) ChronoUnit.DAYS.between(dataInizio, dataFine);

        Map<IntervalStatsEnum, List<Double>> statsInterval = this.initializeMapStats();
        Map<IntervalStatsEnum, List<Double>> statsIntervalIn = this.initializeMapStats();
        Map<IntervalStatsEnum, List<Double>> statsIntervalOut = this.initializeMapStats();

        /* ************** variabili riciclate ************** */
        LocalDate dataNextDay = dataInizio.plusDays(1);
        LocalDate dataNextWeek = dataInizio.plusWeeks(1);
        LocalDate dataNextMonth = dataInizio.plusMonths(1);

        LocalDate dataMov;
        /* ************** variabili riciclate ************** */

        // io li scorro tutti una volta sola (devono essere ordinati per data crescente)
        // mi chiedo progressivamente (magari salvando un indice di dove sono arrivato)
        // se la data rientra nel range --> posso fare tutti gli intervalli in un solo ciclo.
        movimentiList.sort(MovimentoDto.COMPARATOR_DATA_ASC);
        int currentIdxDay = 0;
        int currentIdxWeek = 0;

        for (MovimentoDto m : movimentiList) {
            dataMov = m.getData().toLocalDate();
            double importoMov = m.getImporto();

            // giorno
            if (dataMov.isBefore(dataNextDay)) {
                int finalCurrentIdxDay = currentIdxDay;
                if (m.getImporto() > 0) {
                    statsIntervalIn.computeIfPresent(IntervalStatsEnum.GIORNO, (interval, sum) -> {
                        sum.set(finalCurrentIdxDay, sum.get(finalCurrentIdxDay) + importoMov);
                        return sum;
                    });
                } else {
                    statsIntervalOut.computeIfPresent(IntervalStatsEnum.GIORNO, (interval, sum) -> {
                        sum.set(finalCurrentIdxDay, sum.get(finalCurrentIdxDay) + importoMov);
                        return sum;
                    });
                }
                statsInterval.computeIfPresent(IntervalStatsEnum.GIORNO, (interval, sum) -> {
                    sum.set(finalCurrentIdxDay, sum.get(finalCurrentIdxDay) + importoMov);
                    return sum;
                });
            } else {
                // guardo quanti giorni sono passati
                long slotVuoti = ChronoUnit.DAYS.between(dataNextDay, dataMov);
                for (int i = currentIdxDay + 1; i < slotVuoti + i; i++) {
                    int finalI = i;
                    statsInterval.computeIfPresent(IntervalStatsEnum.GIORNO, (interval, sum) -> {
                        sum.set(finalI, (double) 0);
                        return sum;
                    });
                }

                dataNextDay = dataMov;

            }

            // settimana
            if (dataMov.isBefore(dataNextWeek)) {
                int finalCurrentIdxWeek = currentIdxWeek;
                if (m.getImporto() > 0) {
                    statsIntervalIn.computeIfPresent(IntervalStatsEnum.SETTIMANA, (interval, sum) -> {
                        sum.set(finalCurrentIdxWeek, sum.get(finalCurrentIdxWeek) + importoMov);
                        return sum;
                    });
                } else {
                    statsIntervalOut.computeIfPresent(IntervalStatsEnum.SETTIMANA, (interval, sum) -> {
                        sum.set(finalCurrentIdxWeek, sum.get(finalCurrentIdxWeek) + importoMov);
                        return sum;
                    });
                }
                statsInterval.computeIfPresent(IntervalStatsEnum.SETTIMANA, (interval, sum) -> {
                    sum.set(finalCurrentIdxWeek, sum.get(finalCurrentIdxWeek) + importoMov);
                    return sum;
                });
            } else {
                dataNextWeek = dataMov;
            }

            // ecc
        }


        return response;
    }


    // inizializza tutte le mappe con la chiave Interval, e una lista vuota
    private Map<IntervalStatsEnum, List<Double>> initializeMapStats () {
        Map<IntervalStatsEnum, List<Double>> map = new HashMap<>();
        map.put(IntervalStatsEnum.GIORNO, new ArrayList<>(daysDiff));
        map.put(IntervalStatsEnum.SETTIMANA, new ArrayList<>(daysDiff % 7 > 0 ? daysDiff / 7 : 1));
        map.put(IntervalStatsEnum.MESE, new ArrayList<>(daysDiff % 30 > 0 ? daysDiff / 30 : 1));
        map.put(IntervalStatsEnum.TRIMESTRE, new ArrayList<>(daysDiff % 90 > 0 ? daysDiff / 90 : 1));
        map.put(IntervalStatsEnum.ANNO, new ArrayList<>(daysDiff % 365 > 0 ? daysDiff / 365 : 1));
        return map;
    }


}

