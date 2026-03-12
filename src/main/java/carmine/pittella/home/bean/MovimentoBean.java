package carmine.pittella.home.bean;

import carmine.pittella.home.mapper.MovimentoMapper;
import carmine.pittella.home.model.dto.MovimentoDto;
import carmine.pittella.home.repository.MovimentoRepository;
import carmine.pittella.home.service.MovimentoService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class MovimentoBean implements MovimentoService {

    private final MovimentoMapper movimentoMapper;
    private final MovimentoRepository movimentoRepository;

    @Override
    public List<MovimentoDto> findAll () {
        return movimentoMapper.toDtoList(movimentoRepository.listAll().stream().toList());
    }
}
