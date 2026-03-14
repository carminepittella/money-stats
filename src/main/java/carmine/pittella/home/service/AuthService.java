package carmine.pittella.home.service;

import carmine.pittella.home.model.dto.request.LoginRequestDto;
import carmine.pittella.home.model.dto.response.LoginResponseDto;

public interface AuthService {

    LoginResponseDto login (LoginRequestDto request);
}
