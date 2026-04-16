package carmine.pittella.home.service;

import carmine.pittella.home.model.dto.request.MovimentiFilterRequestDto;
import carmine.pittella.home.model.dto.response.StatisticsResponseDto;

public interface StatisticService {

    StatisticsResponseDto getStatistics (MovimentiFilterRequestDto filter);
    StatisticsResponseDto getStatisticsOptimized (MovimentiFilterRequestDto filter);

}
