package by.htp.ex.util.validation;

import jakarta.servlet.http.HttpSession;

public interface AccessValidation {
	
	boolean haveSession(HttpSession session);
	boolean canExecuteAdminCommand(HttpSession session);
	boolean notAuthorizedUser(HttpSession session);

}
