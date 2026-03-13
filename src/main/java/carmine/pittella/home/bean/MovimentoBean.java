package carmine.pittella.home.bean;

import carmine.pittella.home.mapper.MovimentoMapper;
import carmine.pittella.home.model.dto.MovimentoDto;
import carmine.pittella.home.repository.MovimentoRepository;
import carmine.pittella.home.service.MovimentoService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
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

    @Override
    public void importExcelMovimenti () {
        //TODO: passare il file excel
        // facciamo finta che abbiamo estratto la lista dei movimenti
        List<MovimentoDto> movimentiDtoList = new ArrayList<>();

        if (movimentiDtoList == null || movimentiDtoList.isEmpty()) {
            //TODO: lanciare eccezione custom
            return; // da rimuovere
        }

        movimentiDtoList.forEach(movimento -> {
            // controllo se esiste già (capire come identificarlo)
            // --> gli id no perché non ci sono e saranno diversi
            boolean alreadyExist;
            if (alreadyExist) {
                // loggare
                continue;
            }

            movimentoRepository.

        });
    }
}
