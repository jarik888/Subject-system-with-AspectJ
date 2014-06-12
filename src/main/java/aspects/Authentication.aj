package aspects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.MainService;
import frontend.controllers.LoginController;

public aspect Authentication {
	
	private MainService mainS;

	//capture MainService constructor - Dependency injection
	after(MainService ms) : execution(MainService.new()) && this(ms) {
		mainS = ms;
    }
	
	pointcut checkUserSession(HttpServletRequest req, HttpServletResponse res):
		within(frontend.controllers.FrontController) &&
		execution(* doGet(HttpServletRequest,HttpServletResponse)) && args(req, res);
	
	void around(HttpServletRequest req, HttpServletResponse res) : checkUserSession(req, res) {
		mainS.session(req);
		if (mainS.getSesson().loggedIn()) {
			proceed(req, res);
		} else {
			new LoginController(mainS, req, res);
		}		
	}
}
