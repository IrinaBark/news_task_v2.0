package by.htp.ex.service.impl;

import by.htp.ex.bean.NewUserInfo;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.DaoProvider;
import by.htp.ex.dao.IUserDAO;
import by.htp.ex.service.ServiceException;
import by.htp.ex.util.validation.UserDataValidation;
import by.htp.ex.util.validation.ValidationProvider;
import by.htp.ex.service.IUserService;

public class UserServiceImpl implements IUserService {

	private final IUserDAO userDAO = DaoProvider.getInstance().getUserDao();
	private final UserDataValidation userDataValidation = ValidationProvider.getInstance().getUserDataValidation();

	private static final String ERROR_MESSAGE_FOR_INVALID_LOGIN = "invalid login value";
	private static final String ERROR_MESSAGE_FOR_INVALID_PASSWORD = "invalid password value";
	private static final String ERROR_MESSAGE_FOR_INVALID_EMAIL = "invalid email value";

	@Override
	public String signIn(String login, String password) throws ServiceException {

		if (!userDataValidation.loginValidation(login)) {
			throw new ServiceException(ERROR_MESSAGE_FOR_INVALID_LOGIN);
		}

		if (!userDataValidation.passwordValidation(password)) {
			throw new ServiceException(ERROR_MESSAGE_FOR_INVALID_PASSWORD);
		}

		try {
			if (userDAO.logination(login, password)) {

				return userDAO.getRole(login, password);
			} else {

				return "guest";
			}

		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean registration(NewUserInfo user) throws ServiceException {

		if (!userDataValidation.loginValidation(user.getLogin())) {
			throw new ServiceException(ERROR_MESSAGE_FOR_INVALID_LOGIN);
		}

		if (!userDataValidation.passwordValidation(user.getPassword())) {
			throw new ServiceException(ERROR_MESSAGE_FOR_INVALID_PASSWORD);
		}

		if (!userDataValidation.loginValidation(user.getEmail())) {
			throw new ServiceException(ERROR_MESSAGE_FOR_INVALID_EMAIL);
		}

		try {
			userDAO.registration(user);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return true;
	}

}
