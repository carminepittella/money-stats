package carmine.pittella.home.bean;

import carmine.pittella.home.model.dto.CategoriaDto;
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

    @Override
    public StatisticsResponseDto getStatistics (MovimentiFilterRequestDto filter) {

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
            LocalDate firstDayOfYear = dataMov.with(TemporalAdjusters.firstDayOfYear());

            // separo i movimenti in entrata da quelli in uscita
            boolean isMovInEntrata = importo > 0;
            double importoIn = isMovInEntrata ? importo : 0;
            double importoOut = !isMovInEntrata ? importo : 0;

            this.aggregaValori(statsInterval, statsIntervalIn, statsIntervalOut, IntervalStatsEnum.GIORNO, dataMov, (importoIn + importoOut), importoIn, importoOut);
            this.aggregaValori(statsInterval, statsIntervalIn, statsIntervalOut, IntervalStatsEnum.SETTIMANA, firstDayOfWeek, (importoIn + importoOut), importoIn, importoOut);
            this.aggregaValori(statsInterval, statsIntervalIn, statsIntervalOut, IntervalStatsEnum.MESE, firstDayOfMonth, (importoIn + importoOut), importoIn, importoOut);
            this.aggregaValori(statsInterval, statsIntervalIn, statsIntervalOut, IntervalStatsEnum.ANNO, firstDayOfYear, (importoIn + importoOut), importoIn, importoOut);
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

