package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import by.htp.ex.util.validation.AccessValidation;
import by.htp.ex.util.validation.ValidationProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToEditNews implements Command {

	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	private final AccessValidation accessValidation = ValidationProvider.getInstance().getAccessValidation();

	private static final String HAVE_NO_ACCESS_ERROR_LOCAL_KEY = "local.error.name.no_access";
	private static final String ERROR_MESSAGE_PARAM = "errorMessage";
	private static final String JSP_COMMAND_PARAM = "command";
	private static final String JSP_ID_PARAM = "id";
	private static final String JSP_LOCALE_PARAM = "local";
	private static final String NEWS_PARAM = "news";
	
	private static final String PRESENTATION_PARAM = "presentation";
	private static final String PRESENTATION_VALUE_FOR_ADD_NEWS = "addNews";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (accessValidation.canExecuteAdminCommand(session)) {

			News news;
			String command;
			String local;
			String id;

			id = request.getParameter(JSP_ID_PARAM);
			command = request.getParameter(JSP_COMMAND_PARAM);
			local = request.getParameter(JSP_LOCALE_PARAM);

			Cookie previousRequest = new Cookie(JSP_COMMAND_PARAM, command);
			Cookie idCookie = new Cookie(JSP_ID_PARAM, id);
			idCookie.setMaxAge(-1);

			try {
				news = newsService.findById(Integer.parseInt(id));

				response.addCookie(previousRequest);
				response.addCookie(idCookie);

				session.setAttribute(JSP_LOCALE_PARAM, local);
				request.setAttribute(NEWS_PARAM, news);
				request.setAttribute(PRESENTATION_PARAM, PRESENTATION_VALUE_FOR_ADD_NEWS);

				request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").include(request, response);
			} catch (ServiceException e) {
				response.sendRedirect("controller?command=go_to_error_page");
			}

		} else {
			request.getSession().setAttribute(ERROR_MESSAGE_PARAM, HAVE_NO_ACCESS_ERROR_LOCAL_KEY);
			response.sendRedirect("controller?command=go_to_error_page");
		}
	}

}
