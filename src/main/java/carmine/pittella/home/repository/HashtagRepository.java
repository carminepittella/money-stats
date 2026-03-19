package carmine.pittella.home.repository;

import carmine.pittella.home.model.entity.HashtagEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class HashtagRepository implements PanacheRepository<HashtagEntity> {

    public HashtagEntity findOrCreate (String hashtag) {
        HashtagEntity exists = find("hashtag", hashtag).firstResult();
        if (exists != null) return exists;

        HashtagEntity hashtagEntity = new HashtagEntity();
        hashtagEntity.setHashtag(hashtag);
        persist(hashtagEntity);
        return hashtagEntity;
    }
}
