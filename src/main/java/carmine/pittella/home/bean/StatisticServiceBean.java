package carmine.pittella.home.bean;

import carmine.pittella.home.model.dto.MovimentoDto;
import carmine.pittella.home.model.dto.request.MovimentiFilterRequestDto;
import carmine.pittella.home.model.dto.response.StatisticsResponseDto;
import carmine.pittella.home.model.enums.IntervalStatsEnum;
import carmine.pittella.home.service.MovimentoService;
import carmine.pittella.home.service.StatisticService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class StatisticServiceBean implements StatisticService {

    private final MovimentoService movimentoService;

    public StatisticsResponseDto getStatistics (MovimentiFilterRequestDto filter) {

        List<MovimentoDto> movimentiList = movimentoService.findAllFiltered(filter);
        if (movimentiList == null || movimentiList.isEmpty()) {
            return new StatisticsResponseDto();
        }

        /* ************** variabili riciclate ************** */
        List<MovimentoDto> movimentiInDay;

        Map<IntervalStatsEnum, Map<LocalDate, Double>> statsInterval = this.initializeMapStats();
        Map<IntervalStatsEnum, Map<LocalDate, Double>> statsIntervalIn = this.initializeMapStats();
        Map<IntervalStatsEnum, Map<LocalDate, Double>> statsIntervalOut = this.initializeMapStats();
        /* ************** variabili riciclate ************** */

        // raggruppo i Movimenti per Data ordinate in modo crescente
        Map<LocalDate, List<MovimentoDto>> movimentiGroupedMap = movimentiList
                .stream().collect(Collectors.groupingBy(m ->
                        m.getData().toLocalDate(), TreeMap::new, Collectors.toList()));

        LocalDate firstDayWithMovimenti = movimentiGroupedMap.entrySet().iterator().next().getKey();

        // prendo il primo e ultimo giorno della settimana
        LocalDate firstDayOfWeek = firstDayWithMovimenti.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate lastDayOfWeek = firstDayWithMovimenti.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        double sumWeekIn = 0, sumWeekOut = 0;

        // prendo il primo e ultimo giorno del mese
        LocalDate firstDayOfMonth = firstDayWithMovimenti.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfMonth = firstDayWithMovimenti.with(TemporalAdjusters.lastDayOfMonth());
        double sumMonthIn = 0, sumMonthOut = 0;

        // ecc...


        // scorro tutte le date in cui ci sono Movimenti
        for (LocalDate dataIdx : movimentiGroupedMap.keySet()) {
            double sumDayIn = 0, sumDayOut = 0;
            movimentiInDay = movimentiGroupedMap.get(dataIdx);

            // calcolo importi di ogni giorno
            for (MovimentoDto mov : movimentiInDay) {
                if (mov.getImporto() > 0) {
                    sumDayIn += mov.getImporto();
                } else {
                    sumDayOut += mov.getImporto();
                }
            }

            //------------------ GIORNO ------------------//
            statsInterval.get(IntervalStatsEnum.GIORNO).put(dataIdx, sumDayIn + sumDayOut);
            statsIntervalIn.get(IntervalStatsEnum.GIORNO).put(dataIdx, sumDayIn);
            statsIntervalOut.get(IntervalStatsEnum.GIORNO).put(dataIdx, sumDayOut);

            //------------------ SETTIMANA ------------------//
            // il primo giorno della settimana sarà la chiave
            // finché non arrivo a un giorno successivo a lastDayOfWeek, continuo a sommare dentro la stessa chiave firstDayOfWeek
            // quando lo supero, resetto i contatori sumWeek e aggiorno firstDayOfWeek con lastDayOfWeek + 1
            if (dataIdx.isAfter(lastDayOfWeek)) {
                statsInterval.get(IntervalStatsEnum.SETTIMANA).put(firstDayOfWeek, sumWeekIn + sumWeekOut);
                statsIntervalIn.get(IntervalStatsEnum.SETTIMANA).put(firstDayOfWeek, sumWeekIn);
                statsIntervalOut.get(IntervalStatsEnum.SETTIMANA).put(firstDayOfWeek, sumWeekOut);

                // passo alla settimana successiva
                firstDayOfWeek = dataIdx.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)); // lunedì successivo
                lastDayOfWeek = dataIdx.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

                // reset contatori somma della settimana
                sumWeekIn = 0;
                sumWeekOut = 0;
            }
            // incremento i contatori della settimana
            sumWeekIn += sumDayIn;
            sumWeekOut += sumDayOut;

            //------------------ MESE ------------------//
            // il primo giorno del mese sarà la chiave
            // finché non arrivo a un giorno successivo a lastDayOfMonth, continuo a sommare dentro la stessa chiave firstDayOfMonth
            // quando lo supero, resetto i contatori sumMonth e aggiorno firstDayOfMonth con lastDayOfMonth + 1
            if (dataIdx.isAfter(lastDayOfMonth)) {
                statsInterval.get(IntervalStatsEnum.MESE).put(firstDayOfMonth, sumMonthIn + sumMonthOut);
                statsIntervalIn.get(IntervalStatsEnum.MESE).put(firstDayOfMonth, sumMonthIn);
                statsIntervalOut.get(IntervalStatsEnum.MESE).put(firstDayOfMonth, sumMonthOut);

                // passo al mese successivo
                firstDayOfMonth = dataIdx.with(TemporalAdjusters.firstDayOfMonth()); // primo giorno del mese successivo
                lastDayOfMonth = dataIdx.with(TemporalAdjusters.lastDayOfMonth()); // ultimo giorno del mese

                // reset contatori somma del mese
                sumMonthIn = 0;
                sumMonthOut = 0;
            }
            // incremento i contatori del mese
            sumMonthIn += sumDayIn;
            sumMonthOut += sumDayOut;

            // ecc...
        }

        // quando esco dal for, devo aggiungere gli ultimi contatori di week, month ecc
        statsInterval.get(IntervalStatsEnum.SETTIMANA).put(firstDayOfWeek, sumWeekIn + sumWeekOut);
        statsIntervalIn.get(IntervalStatsEnum.SETTIMANA).put(firstDayOfWeek, sumWeekIn);
        statsIntervalOut.get(IntervalStatsEnum.SETTIMANA).put(firstDayOfWeek, sumWeekOut);

        statsInterval.get(IntervalStatsEnum.MESE).put(firstDayOfMonth, sumMonthIn + sumMonthOut);
        statsIntervalIn.get(IntervalStatsEnum.MESE).put(firstDayOfMonth, sumMonthIn);
        statsIntervalOut.get(IntervalStatsEnum.MESE).put(firstDayOfMonth, sumMonthOut);

        // build response
        StatisticsResponseDto response = new StatisticsResponseDto();
        response.setStatsInterval(statsInterval);
        response.setStatsIntervalIn(statsIntervalIn);
        response.setStatsIntervalOut(statsIntervalOut);
        return response;
    }


    @Override
    public StatisticsResponseDto getStatisticsOptimized (MovimentiFilterRequestDto filter) {

        List<MovimentoDto> movimentiList = movimentoService.findAllFiltered(filter);
        if (movimentiList == null || movimentiList.isEmpty()) {
            return new StatisticsResponseDto();
        }

        /* ************** variabili riciclate ************** */
        Map<IntervalStatsEnum, Map<LocalDate, Double>> statsInterval = this.initializeMapStats();
        Map<IntervalStatsEnum, Map<LocalDate, Double>> statsIntervalIn = this.initializeMapStats();
        Map<IntervalStatsEnum, Map<LocalDate, Double>> statsIntervalOut = this.initializeMapStats();
        /* ************** variabili riciclate ************** */

        for (MovimentoDto mov : movimentiList) {
            LocalDate dataMov = mov.getData().toLocalDate();
            double importo = mov.getImporto();

            // calcolo le date key per la settimana e il mese
            LocalDate firstDayOfWeek = dataMov.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate firstDayOfMonth = dataMov.with(TemporalAdjusters.firstDayOfMonth());

            // separo i movimenti in entrata da quelli in uscita
            boolean isMovInEntrata = importo > 0;
            double importoIn = isMovInEntrata ? importo : 0;
            double importoOut = !isMovInEntrata ? importo : 0;

            this.aggregaValori(statsInterval, statsIntervalIn, statsIntervalOut, IntervalStatsEnum.GIORNO, dataMov, (importoIn + importoOut), importoIn, importoOut);
            this.aggregaValori(statsInterval, statsIntervalIn, statsIntervalOut, IntervalStatsEnum.SETTIMANA, firstDayOfWeek, (importoIn + importoOut), importoIn, importoOut);
            this.aggregaValori(statsInterval, statsIntervalIn, statsIntervalOut, IntervalStatsEnum.MESE, firstDayOfMonth, (importoIn + importoOut), importoIn, importoOut);
        }

        // build response
        return StatisticsResponseDto.builder()
                .statsInterval(statsInterval)
                .statsIntervalIn(statsIntervalIn)
                .statsIntervalOut(statsIntervalOut)
                .build();
    }


    // inizializza tutte le mappe con la chiave Interval, e una mappa vuota
    private Map<IntervalStatsEnum, Map<LocalDate, Double>> initializeMapStats () {
        Map<IntervalStatsEnum, Map<LocalDate, Double>> map = new HashMap<>();
        map.put(IntervalStatsEnum.GIORNO, new TreeMap<>());
        map.put(IntervalStatsEnum.SETTIMANA, new TreeMap<>());
        map.put(IntervalStatsEnum.MESE, new TreeMap<>());
        map.put(IntervalStatsEnum.TRIMESTRE, new TreeMap<>());
        map.put(IntervalStatsEnum.ANNO, new TreeMap<>());
        return map;
    }

    private void aggregaValori (
            Map<IntervalStatsEnum, Map<LocalDate, Double>> statsTot,
            Map<IntervalStatsEnum, Map<LocalDate, Double>> statsIn,
            Map<IntervalStatsEnum, Map<LocalDate, Double>> statsOut,
            IntervalStatsEnum interval, LocalDate dataKey, double impTot, double impIn, double impOut) {

        statsTot.get(interval).merge(dataKey, impTot, Double::sum);

        if (impIn != 0) {
            statsIn.get(interval).merge(dataKey, impIn, Double::sum);
        }
        if (impOut != 0) {
            statsOut.get(interval).merge(dataKey, impOut, Double::sum);
        }
    }
}

