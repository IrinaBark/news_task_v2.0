package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.bean.NewUserInfo;
import by.htp.ex.controller.Command;
import by.htp.ex.service.IUserService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DoRegistration implements Command {

	private final IUserService service = ServiceProvider.getInstance().getUserService();

	private static final String SUCCESSFUL_REGISTRATION_LOCAL_KEY = "local.header.name.successful_registration";
	private static final String FAILED_REGISTRATION_LOCAL_KEY = "local.header.name.failed_registration";
	
	private static final String JSP_NAME_PARAM = "firstName";
	private static final String JSP_SURMANE_PARAM = "lastName";
	private static final String JSP_EMAIL_PARAM = "email";
	private static final String JSP_LOGIN_PARAM = "login";
	private static final String JSP_PASSWORD_PARAM = "password";
	
	private static final String ERROR_MESSAGE_PARAM = "errorMessage";
	private static final String INFO_MESSAGE_PARAM = "info_message";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name;
		String surname;
		String email;
		String login;
		String password;

		boolean registeredSuccessfully = false;

		name = request.getParameter(JSP_NAME_PARAM);
		surname = request.getParameter(JSP_SURMANE_PARAM);
		email = request.getParameter(JSP_EMAIL_PARAM);
		login = request.getParameter(JSP_LOGIN_PARAM);
		password = request.getParameter(JSP_PASSWORD_PARAM);

		NewUserInfo newUserInfo = new NewUserInfo(name, surname, email, login, password);

		try {
			registeredSuccessfully = service.registration(newUserInfo);
			
			if (registeredSuccessfully) {
				request.getSession(true).setAttribute(INFO_MESSAGE_PARAM, SUCCESSFUL_REGISTRATION_LOCAL_KEY);
				response.sendRedirect("controller?command=go_to_base_page");
			} else {
				request.getSession(true).setAttribute(INFO_MESSAGE_PARAM, FAILED_REGISTRATION_LOCAL_KEY);
				response.sendRedirect("controller?command=go_to_base_page");
			}

		} catch (ServiceException e) {
			request.getSession().setAttribute(ERROR_MESSAGE_PARAM, e.getMessage());
			response.sendRedirect("controller?command=go_to_error_page");
		}	
	}
}
