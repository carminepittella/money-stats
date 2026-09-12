package carmine.pittella.home.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponseDto {

    private Double entrate;
    private Double uscite;
    private Double saldo;

}
