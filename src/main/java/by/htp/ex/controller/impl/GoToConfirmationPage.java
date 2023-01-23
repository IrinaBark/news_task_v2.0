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

public class GoToConfirmationPage implements Command {

	private static final String ERROR_NO_SELECTED_NEWS_LOCAL_KEY = "local.error.name.no_selected_news";
	private static final String NO_ACCESS_ERROR_LOCAL_KEY = "local.error.name.no_access";
	private static final String ERROR_MESSAGE_PARAM = "errorMessage";

	private static final String ID_NEWS_PARAM = "idNews";
	private static final String PRESENTATION_PARAM = "presentation";
	private static final String PRESENTATION_VALUE_FOR_CONFIRMATION_PAGE = "confirmationPage";

	private static final String JSP_COMMAND_PARAM = "command";
	private static final String JSP_LOCALE_PARAM = "local";

	private final AccessValidation accessValidation = ValidationProvider.getInstance().getAccessValidation();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		String command = request.getParameter(JSP_COMMAND_PARAM);
		String local = request.getParameter(JSP_LOCALE_PARAM);

		Cookie previousRequest = new Cookie(JSP_COMMAND_PARAM, command);

		if (accessValidation.canExecuteAdminCommand(session)) {

			String[] idNews;
			idNews = request.getParameterValues(ID_NEWS_PARAM);

			if (idNews == null) {

				request.getSession().setAttribute(ERROR_MESSAGE_PARAM, ERROR_NO_SELECTED_NEWS_LOCAL_KEY);
				request.setAttribute(JSP_LOCALE_PARAM, local);

				response.addCookie(previousRequest);
				response.sendRedirect("controller?command=go_to_error_page");

			} else {

				request.setAttribute(ID_NEWS_PARAM, idNews);
				request.setAttribute(PRESENTATION_PARAM, PRESENTATION_VALUE_FOR_CONFIRMATION_PAGE);
				request.setAttribute(JSP_LOCALE_PARAM, local);

				response.addCookie(previousRequest);
				request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);

			}
		} else {
			request.getSession().setAttribute(ERROR_MESSAGE_PARAM, NO_ACCESS_ERROR_LOCAL_KEY);
			request.setAttribute(JSP_LOCALE_PARAM, local);

			response.addCookie(previousRequest);
			response.sendRedirect("controller?command=go_to_error_page");
		}
	}
}
