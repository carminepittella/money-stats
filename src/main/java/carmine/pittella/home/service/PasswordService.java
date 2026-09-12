package carmine.pittella.home.service;

public interface PasswordService {

    String cryptPassword (String password);
    boolean verifyPassword (String password, String passwordStored);
}
