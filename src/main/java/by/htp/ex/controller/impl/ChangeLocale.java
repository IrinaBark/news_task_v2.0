package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChangeLocale implements Command {

	private static final String COMMAND_PARAM = "command";
	private static final String ID_PARAM = "id";
	private static final String LOCALE_PARAM = "local";
	private static final String REQUEST_START = "controller?command=";

	private StringBuilder previousRequest;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		previousRequest = new StringBuilder(REQUEST_START);

		Cookie[] cookies = request.getCookies();
		Cookie commandCookie = null;
		Cookie idCookie = null;
		String requestToExecute;

		String locale = request.getParameter(LOCALE_PARAM);

		if (cookies != null) {
			commandCookie = findCookie(cookies, COMMAND_PARAM);
			idCookie = findCookie(cookies, ID_PARAM);

		}

		if (idCookie != null) {

			String id = idCookie.getValue();

			previousRequest.append(commandCookie.getValue());
			previousRequest.append("&").append(ID_PARAM).append("=");
			previousRequest.append(id);
			previousRequest.append("&").append(LOCALE_PARAM).append("=");
			previousRequest.append(locale);

			idCookie.setMaxAge(0);

			requestToExecute = previousRequest.toString();

			response.addCookie(idCookie);
			response.sendRedirect(requestToExecute);

		} else {

			previousRequest.append(commandCookie.getValue());
			previousRequest.append("&").append(LOCALE_PARAM).append("=");
			previousRequest.append(locale);

			requestToExecute = previousRequest.toString();
			response.sendRedirect(requestToExecute);
		}

	}

	private static Cookie findCookie(Cookie[] cookies, String cookieName) {
		Cookie cookie = null;
		for (Cookie c : cookies) {
			if (cookieName.equals(c.getName())) {
				cookie = c;
				return cookie;
			}
		}
		return cookie;
	}

}
