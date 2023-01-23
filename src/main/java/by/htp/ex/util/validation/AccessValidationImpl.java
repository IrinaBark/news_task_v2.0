package by.htp.ex.util.validation;

import jakarta.servlet.http.HttpSession;

public class AccessValidationImpl implements AccessValidation {

	private static final String ROLE_PARAM = "role";
	private static final String ADMIN_ROLE = "admin";
	private static final String USER_PARAM = "user";
	private static final String NOT_AUTHORIZED_USER = "not active";

	@Override
	public boolean haveSession(HttpSession session) {
		if (session == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean canExecuteAdminCommand(HttpSession session) {

		if (this.haveSession(session)) {

			String role = (String) session.getAttribute(ROLE_PARAM);
			return role.equals(ADMIN_ROLE);

		} else {
			return false;
		}
	}

	@Override
	public boolean notAuthorizedUser(HttpSession session) {
		if (this.haveSession(session)) {
			return false;
		} else {
			return true;
		}
	}
}
