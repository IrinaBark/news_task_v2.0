package by.htp.ex.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import by.htp.ex.bean.NewUserInfo;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.IUserDAO;

public class UserDAO implements IUserDAO {

	private List<NewUserInfo> database = new ArrayList<NewUserInfo>();
	
	private static final String ADMIN_LOGIN = "admin";
	private static final String ADMIN_PASSWORD = "adminPassword1";

	private static final String USER_ROLE = "user";
	private static final String ADMIN_ROLE = "admin";
	
	@Override
	public boolean logination(String login, String password) throws DaoException {
		
		database.add(new NewUserInfo("bhf", "barkun", "bar@gmail.com", "ira", "Barkun1"));
		database.add(new NewUserInfo("bhf", "barkun", "bar@gmail.com", "admin", "adminPassword1"));

		for (NewUserInfo userInfo : database) {
			if (userInfo.getLogin().equals(login) && userInfo.getPassword().equals(password)) {

				return true;
			} 
		}
		
		return false;

		/*
		 * Random rand = new Random(); int value = rand.nextInt(1000);
		 * 
		 * if(value % 3 == 0) { try { throw new SQLException("stub exception");
		 * }catch(SQLException e) { throw new DaoException(e); } }else if (value % 2 ==
		 * 0) { return true; }else { return false; }
		 */

	}

	public String getRole(String login, String password) {

		if (login.equals(ADMIN_LOGIN) && password.equals(ADMIN_PASSWORD)) {
			return ADMIN_ROLE;
		} else {
			return USER_ROLE;
		}
	}

	@Override
	public boolean registration(NewUserInfo user) throws DaoException {
		
		database.add(new NewUserInfo("bhf", "barkun", "bar@gmail.com", "ira", "barkun"));
		database.add(new NewUserInfo("bhf", "barkun", "bar@gmail.com", "admin", "adminPassword1"));

		for (NewUserInfo userInfo : database) {
			if (userInfo.getLogin().equals(user.getLogin())) {
				throw new DaoException("user with this login is already registered");
			}
		}
		database.add(user);
		
		
		return true;
	}

}

