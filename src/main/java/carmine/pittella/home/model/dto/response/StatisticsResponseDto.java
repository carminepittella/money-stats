package carmine.pittella.home.model.dto.response;

import carmine.pittella.home.model.dto.CategoriaDto;
import carmine.pittella.home.model.enums.IntervalStatsEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class StatisticsResponseDto {

    private IntervalStatsEnum interval;

    private Map<CategoriaDto, Double> statsCategoria;

    private Double media;
    private Double mediaIn;
    private Double mediaOut;

    private List<Double> statsInterval;
    private List<Double> statsIntervalIn;
    private List<Double> statsIntervalOut;
}
