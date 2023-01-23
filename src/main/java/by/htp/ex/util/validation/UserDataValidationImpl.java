package by.htp.ex.util.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDataValidationImpl implements UserDataValidation {
	//с ограничением 2-20 символов, которыми могут быть буквы и цифры, первый символ обязательно буква
	private static final String PATTERN_FOR_LOGIN = "^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$";
	//Строчные и прописные латинские буквы, цифры
	private static final String PATTERN_FOR_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";
	private static final String PATTERN_FOR_EMAIL = "^(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\\\s).*$";

	private static final Pattern LOGIN_PATTERN = Pattern.compile(PATTERN_FOR_LOGIN);
	private static final Pattern PASSWORD_PATTERN = Pattern.compile(PATTERN_FOR_PASSWORD);
	private static final Pattern EMAIL_PATTERN = Pattern.compile(PATTERN_FOR_EMAIL);

	@Override
	public boolean loginValidation(String login) {
		Matcher loginMatcher = LOGIN_PATTERN.matcher(login);
		return loginMatcher.matches();
	}

	@Override
	public boolean passwordValidation(String password) {
		Matcher passwordMatcher = PASSWORD_PATTERN.matcher(password);
		return passwordMatcher.matches();
	}

	@Override
	public boolean emailValidation(String email) {
		Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
		return emailMatcher.matches();
	}

}
