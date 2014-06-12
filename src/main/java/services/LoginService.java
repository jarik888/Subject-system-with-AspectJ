package services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import backend.dao.GenericDAO;
import backend.data.User_account;

public class LoginService {

	EntityManager manager;

	public LoginService(EntityManagerFactory factory) {
		manager = factory.createEntityManager();
	}

	public Long login(String username, String passw) {
		
		User_account ua = null;
		try {
			ua = getUserAccByUserName(username);
			if (ua != null && ua.getPassw().equals(generateMD5(passw))) {
				return ua.getSubjectFk();
			}
		} catch (Exception e) {}
		return null;
	}
	
	public User_account getUserAccByUserName(String username) {
		
		User_account ua = null;
		GenericDAO usrDAO = new GenericDAO(manager, User_account.class);
		String query = "SELECT ua FROM User_account ua WHERE ua.username = :username";
		String param_name = "username";
		String param_value = username;
		ua = (User_account) usrDAO.findOneObjectByQuery(query, param_name, param_value);		
		return ua;
	}
	
	public User_account getUserAccByEmplyeeId(long subject_fk) {
		
		User_account ua = null;
		GenericDAO usrDAO = new GenericDAO(manager, User_account.class);
		String query = "SELECT ua FROM User_account ua WHERE ua.subject_fk = :subject_fk";
		String param_name = "subject_fk";
		Long param_value = subject_fk;
		ua = (User_account) usrDAO.findOneObjectByQuery(query, param_name, param_value);		
		return ua;
	}
	
	public static String generateMD5(String passw) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(passw.getBytes(), 0, passw.length());
			String md5 = new BigInteger(1,m.digest()).toString(16);
			return md5;			
		} catch (NoSuchAlgorithmException e) {}
		return null;
	}
	
}