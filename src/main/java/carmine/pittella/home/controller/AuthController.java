package carmine.pittella.home.controller;

import carmine.pittella.home.model.dto.request.LoginRequestDto;
import carmine.pittella.home.model.dto.request.RegisterRequestDto;
import carmine.pittella.home.model.dto.response.LoginResponseDto;
import carmine.pittella.home.service.AuthService;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/auth")
@PermitAll
@RequestScoped
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    private final AuthService authService;

    @POST
    @Path("/login")
    public LoginResponseDto login (@Valid LoginRequestDto request) {
        return authService.login(request);
    }

    @POST
    @Path("/register")
    public LoginResponseDto register(@Valid RegisterRequestDto request) {
        return authService.register(request);
    }

}
