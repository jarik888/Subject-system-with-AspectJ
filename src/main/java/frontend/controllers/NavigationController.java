package frontend.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NavigationController {
	
	public void showPage(HttpServletRequest req, HttpServletResponse res, String jsp) {
		try {
			req.getRequestDispatcher(jsp).forward(req, res); //default "/main.jsp"
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
