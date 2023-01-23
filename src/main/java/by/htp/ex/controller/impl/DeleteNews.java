package by.htp.ex.controller.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import by.htp.ex.util.validation.AccessValidation;
import by.htp.ex.util.validation.ValidationProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DeleteNews implements Command {

	private static final String HAVE_NO_ACCESS_ERROR_LOCAL_KEY = "local.error.name.no_access";
	private static final String JSP_NEWS_TO_DELETE_PARAM = "newsToDelete";
	private static final String ERROR_MESSAGE_PARAM = "errorMessage";
	private static final String USER_INFO_MESSAGE_PARAM = "user_info_message";
	private static final String INFO_MESSAGE_LOCAL_KEY = "local.error.name.news_successfully_deleted";

	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	private final AccessValidation accessValidation = ValidationProvider.getInstance().getAccessValidation();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (accessValidation.canExecuteAdminCommand(session)) {

			String[] id;

			List<String> idNewsToDelete = new ArrayList<String>();
			id = request.getParameterValues(JSP_NEWS_TO_DELETE_PARAM);

			for (String idOfNews : id) {
				idNewsToDelete.add(idOfNews);
			}
			
			try {
				newsService.delete(idNewsToDelete);
				request.getSession(true).setAttribute(USER_INFO_MESSAGE_PARAM, INFO_MESSAGE_LOCAL_KEY);
				response.sendRedirect("controller?command=go_to_base_page");

			} catch (ServiceException e) {
				response.sendRedirect("controller?command=go_to_error_page");
			}

		} else {
			request.getSession().setAttribute(ERROR_MESSAGE_PARAM, HAVE_NO_ACCESS_ERROR_LOCAL_KEY);
			response.sendRedirect("controller?command=go_to_error_page");
		}
	}
}
