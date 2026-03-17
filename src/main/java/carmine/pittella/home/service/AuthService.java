package carmine.pittella.home.service;

import carmine.pittella.home.model.dto.request.LoginRequestDto;
import carmine.pittella.home.model.dto.request.RegisterRequestDto;
import carmine.pittella.home.model.dto.response.LoginResponseDto;
import jakarta.validation.Valid;

public interface AuthService {

    LoginResponseDto login (LoginRequestDto request);
    LoginResponseDto register (@Valid RegisterRequestDto request);
}
