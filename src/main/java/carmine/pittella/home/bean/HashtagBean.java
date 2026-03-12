package carmine.pittella.home.bean;

import carmine.pittella.home.mapper.HashtagMapper;
import carmine.pittella.home.model.dto.HashtagDto;
import carmine.pittella.home.repository.HashtagRepository;
import carmine.pittella.home.service.HashtagService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class HashtagBean implements HashtagService {

    private final HashtagMapper hashtagMapper;
    private final HashtagRepository hashtagRepository;

    @Override
    public List<HashtagDto> findAll () {
        return hashtagMapper.toDtoList(hashtagRepository.listAll().stream().toList());
    }
}
