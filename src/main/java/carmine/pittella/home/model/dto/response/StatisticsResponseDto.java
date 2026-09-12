package carmine.pittella.home.model.dto.response;

import carmine.pittella.home.model.dto.CategoriaDto;
import carmine.pittella.home.model.enums.IntervalStatsEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsResponseDto {

    private Map<CategoriaDto, Double> statsCategoria;
    private Map<CategoriaDto, Double> statsCategoriaIn;
    private Map<CategoriaDto, Double> statsCategoriaOut;

    private Map<IntervalStatsEnum, Map<LocalDate, Double>> statsInterval;
    private Map<IntervalStatsEnum, Map<LocalDate, Double>> statsIntervalIn;
    private Map<IntervalStatsEnum, Map<LocalDate, Double>> statsIntervalOut;


}
