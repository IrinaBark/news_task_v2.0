package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import by.htp.ex.util.validation.AccessValidation;
import by.htp.ex.util.validation.ValidationProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToRegistrationPageCommand implements Command {

	private static final String HAVE_ALREADY_AUTHORIZED_ERROR_LOCAL_KEY = "local.registration.name.error_is_authorized";
	private static final String ERROR_MESSAGE_PARAM = "errorMessage";
	private static final String JSP_COMMAND_PARAM = "command";
	private static final String JSP_LOCALE_PARAM = "local";
	
	private final AccessValidation accessValidation = ValidationProvider.getInstance().getAccessValidation();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		
		String command;
		String local;

		command = request.getParameter(JSP_COMMAND_PARAM);
		local = request.getParameter(JSP_LOCALE_PARAM);
		
		Cookie previousRequest = new Cookie("command", command);

		if (!accessValidation.notAuthorizedUser(session)) {
			
			request.getSession().setAttribute(JSP_LOCALE_PARAM, local);
			request.setAttribute("presentation", "registration");
			
			response.addCookie(previousRequest);
			request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);

		} else {
			
			request.getSession().setAttribute(ERROR_MESSAGE_PARAM, HAVE_ALREADY_AUTHORIZED_ERROR_LOCAL_KEY);
			response.sendRedirect("controller?command=go_to_error_page");
		}
	}
}
