package services;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;

public class MainService {
	
	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
	public SessionService ss;
	
	public EntityManagerFactory getFactory() {
		return factory;
	}
	
	public void session(HttpServletRequest req) {
		ss = new SessionService(req);
	}
	
	public SessionService getSesson() {
		return ss;
	}
}
