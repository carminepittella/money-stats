package carmine.pittella.home.controller;

import carmine.pittella.home.model.dto.HashtagDto;
import carmine.pittella.home.service.HashtagService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Path("/hashtag")
@RequestScoped
@RequiredArgsConstructor
public class HashtagController {

    private final HashtagService hashtagService;

    @GET
    @Path("/find-all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<HashtagDto> findAll () {
        return hashtagService.findAll();
    }
}
