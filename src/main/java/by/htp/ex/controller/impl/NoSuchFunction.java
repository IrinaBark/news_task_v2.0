package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NoSuchFunction implements Command {

	private final static String ERROR_MESSAGE = "local.error.name.general_message";
	private final static String ERROR_MESSAGE_PARAM = "errorMessage";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getSession().setAttribute(ERROR_MESSAGE_PARAM, ERROR_MESSAGE);
		response.sendRedirect("controller?command=go_to_error_page");

	}
}
