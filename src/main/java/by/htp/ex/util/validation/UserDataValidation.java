package by.htp.ex.util.validation;

public interface UserDataValidation {
       boolean loginValidation(String login);
       boolean passwordValidation(String password);
       boolean emailValidation (String email);

}
