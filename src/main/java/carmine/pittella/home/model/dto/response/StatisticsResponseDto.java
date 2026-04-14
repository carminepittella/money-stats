package carmine.pittella.home.model.dto.response;

import carmine.pittella.home.model.dto.CategoriaDto;
import carmine.pittella.home.model.enums.IntervalStatsEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class StatisticsResponseDto {

    private Map<CategoriaDto, Double> statsCategoria;
    private Map<CategoriaDto, Double> statsCategoriaIn;
    private Map<CategoriaDto, Double> statsCategoriaOut;

    private Map<IntervalStatsEnum, List<Double>> statsInterval;
    private Map<IntervalStatsEnum, List<Double>> statsIntervalIn;
    private Map<IntervalStatsEnum, List<Double>> statsIntervalOut;



}
