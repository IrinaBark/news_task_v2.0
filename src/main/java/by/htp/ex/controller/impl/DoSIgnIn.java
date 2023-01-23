package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import by.htp.ex.service.IUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DoSIgnIn implements Command {

	private final IUserService service = ServiceProvider.getInstance().getUserService();

	private static final String JSP_LOGIN_PARAM = "login";
	private static final String JSP_PASSWORD_PARAM = "password";
	private static final String JSP_LOCALE_PARAM = "local";
	
	private static final String GUEST_ROLE_VALUE = "guest";
	private static final String USER_PARAM = "user";
	private static final String ROLE_PARAM = "role";
	
	private static final String AUTHENTIFICATION_ERROR_PARAM = "AuthenticationError";
	private static final String AUTHENTIFICATION_ERROR_LOCAL_KEY = "local.header.name.login_error";
	private static final String ERROR_MESSAGE_PARAM = "errorMessage";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login;
		String password;
		String local;

		local = (String) request.getSession().getAttribute(JSP_LOCALE_PARAM);
		System.out.println(local);

		login = request.getParameter(JSP_LOGIN_PARAM);
		password = request.getParameter(JSP_PASSWORD_PARAM);

		try {

			String role = service.signIn(login, password);

			if (!role.equals(GUEST_ROLE_VALUE)) {
				request.getSession(true).setAttribute(USER_PARAM, "active");
				request.getSession().setAttribute(ROLE_PARAM, role);
				response.sendRedirect("controller?command=go_to_news_list&local=" + local);
			} else {
				request.getSession(true).setAttribute(USER_PARAM, "not active");
				request.getSession().setAttribute(AUTHENTIFICATION_ERROR_PARAM, AUTHENTIFICATION_ERROR_LOCAL_KEY);
				response.sendRedirect("controller?command=go_to_news_list&local=" + local);
			}

		} catch (ServiceException e) {
			request.getSession().setAttribute(ERROR_MESSAGE_PARAM, e.getMessage());
			response.sendRedirect("controller?command=go_to_error_page");
		}
	}
}
