package by.htp.ex.controller.impl;

import java.io.IOException;
import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToBasePage implements Command {

	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

	private static final String MESSAGE_PARAM = "user_info_message";
	private static final String NEWS_PARAM = "news";
	private static final String PRESENTATION_PARAM = "presentation";
	private static final String PRESENTATION_VALUE_FOR_NEWS_LIST = "newsList";
	private static final String JSP_COMMAND_PARAM = "command";
	private static final String JSP_LOCALE_PARAM = "local";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		List<News> latestNews;
		String infoMessage;

		String command;
		String local;

		command = request.getParameter(JSP_COMMAND_PARAM);
		local = request.getParameter(JSP_LOCALE_PARAM);
		if (local == null) {
			local = "ru";
		}
		Cookie previousRequest = new Cookie(JSP_COMMAND_PARAM, command);

		try {
			infoMessage = (String) request.getSession().getAttribute(MESSAGE_PARAM);
			latestNews = newsService.latestList(5);
			if (infoMessage != null) {
				request.setAttribute(MESSAGE_PARAM, infoMessage);
			}
			response.addCookie(previousRequest);

			session.setAttribute(JSP_LOCALE_PARAM, local);
			request.setAttribute(NEWS_PARAM, latestNews);
			request.setAttribute(PRESENTATION_PARAM, PRESENTATION_VALUE_FOR_NEWS_LIST);

			request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").include(request, response);

		} catch (ServiceException e) {
			response.sendRedirect("controller?command=go_to_error_page");
		}
	}
}
