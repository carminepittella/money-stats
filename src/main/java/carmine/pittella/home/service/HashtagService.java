package carmine.pittella.home.service;

import carmine.pittella.home.model.dto.HashtagDto;

import java.util.List;

public interface HashtagService {

    List<HashtagDto> findAll ();
    HashtagDto findOrCreate (String h);
}
