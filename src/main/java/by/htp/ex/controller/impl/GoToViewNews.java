package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToViewNews implements Command {

	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

	private static final String JSP_ID_PARAM = "id";
	private static final String JSP_COMMAND_PARAM = "command";
	private static final String JSP_LOCALE_PARAM = "local";
	private static final String NEWS_PARAM = "news";
	private static final String PRESENTATION_PARAM = "presentation";
	private static final String PRESENTATION_PARAM_VALUE_FOR_VIEW_NEWS = "viewNews";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		News news;
		String id;
		String command;
		String local;

		command = request.getParameter(JSP_COMMAND_PARAM);
		local = request.getParameter(JSP_LOCALE_PARAM);
		id = request.getParameter(JSP_ID_PARAM);

		Cookie previousRequest = new Cookie(JSP_COMMAND_PARAM, command);
		Cookie idCookie = new Cookie(JSP_ID_PARAM, id);
		idCookie.setMaxAge(-1);

		try {
			news = newsService.findById(Integer.parseInt(id));

			request.setAttribute(NEWS_PARAM, news);
			request.getSession().setAttribute(JSP_LOCALE_PARAM, local);
			request.setAttribute(PRESENTATION_PARAM, PRESENTATION_PARAM_VALUE_FOR_VIEW_NEWS);

			response.addCookie(idCookie);
			response.addCookie(previousRequest);

			request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").include(request, response);
		} catch (ServiceException e) {
			response.addCookie(previousRequest);
			response.sendRedirect("controller?command=go_to_error_page");
		}
	}
}
