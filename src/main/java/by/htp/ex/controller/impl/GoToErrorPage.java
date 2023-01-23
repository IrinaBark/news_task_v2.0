package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToErrorPage implements Command {

	private static final String ERROR_MESSAGE_PARAM = "errorMessage";
	private static final String PRESENTATION_PARAM = "presentation";
	private static final String PRESENTATION_VALUE_FOR_ERROR_PAGE = "error";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String message;
		message = (String) request.getSession().getAttribute(ERROR_MESSAGE_PARAM);

		if (message != null) {
			request.getSession(true).setAttribute(ERROR_MESSAGE_PARAM, message);
		}
		request.setAttribute(PRESENTATION_PARAM, PRESENTATION_VALUE_FOR_ERROR_PAGE);
		request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);
	}
}
