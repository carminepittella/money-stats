package carmine.pittella.home.bean;

import carmine.pittella.home.service.PasswordService;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class PasswordBean implements PasswordService {
    
    @Override
    public String cryptPassword (String password) {
        return BcryptUtil.bcryptHash(password);
    }

    @Override
    public boolean verifyPassword (String password, String passwordStored) {
        return BcryptUtil.matches(password, passwordStored);
    }
}
