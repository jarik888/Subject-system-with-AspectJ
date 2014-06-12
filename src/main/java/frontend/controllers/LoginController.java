package frontend.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.LoginService;
import services.MainService;

public class LoginController {
	
	LoginService loginS;
	MainService mainS;
	
	public LoginController(MainService ms, 
			HttpServletRequest req, HttpServletResponse res) {
		loginS = new LoginService(ms.getFactory());
		this.mainS = ms;
		navigate(req, res);
	}

	private void navigate(HttpServletRequest req, HttpServletResponse res) {
		
		String page = "/login.jsp";
		String action = req.getParameter("action");
		
		if (action.equals("login")) {
			String username = req.getParameter("username");
			Long user_id = loginS.login(username, req.getParameter("password"));
			if (user_id != null) {
				mainS.session(req); //createNewSession
				mainS.getSesson().set("username", username);
				mainS.getSesson().set("user_id", String.valueOf(user_id));
				page = "/main.jsp";
			} else {
				req.setAttribute("LoginError", "Login or password error!");
				req.setAttribute("username", username);
			}
		} else if (action.equals("logout")) {
			mainS.getSesson().invalidateCurrent();
		}		
		new NavigationController().showPage(req, res, page);
	}
	
}
