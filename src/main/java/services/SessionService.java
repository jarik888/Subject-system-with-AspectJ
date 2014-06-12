package services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionService {
	
	private HttpServletRequest req = null;

	public SessionService(HttpServletRequest req) {
		this.req = req;
	}

	public HttpSession getSession(boolean create) {

		return this.req.getSession(create);
	}

	public boolean isExist() {
		HttpSession existingSession = req.getSession(false);
		if (existingSession != null) {
			return true;
		} else {
			return false;
		}
	}

	public void invalidateCurrent() {
		req.getSession(false).invalidate();
	}

	//If session not exists, creates a new session.
	public void createNewSession() {
		req.getSession(true);
	}

	public boolean loggedIn() {
		if (this.isExist() && req.getSession(false).getAttribute("username") != null) {
			return true;
		} else {
			return false;
		}
	}

	public void set(String atrName, String atrValue) {
		req.getSession(false).setAttribute(atrName, atrValue);
	}
	
	public String get(String atrName) {
		return (String) req.getSession(false).getAttribute(atrName);
	}
	
	public String getUserId() {
		return this.get("user_id");
	}

}
