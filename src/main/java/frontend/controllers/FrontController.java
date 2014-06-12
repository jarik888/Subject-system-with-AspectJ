package frontend.controllers;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.MainService;



@WebServlet("/s")
public class FrontController extends HttpServlet {
	
	private MainService ms;

	public void init(ServletConfig config) throws ServletException {
		ms = new MainService();
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
//		ms.session(req);
//		if (ms.getSesson().loggedIn()) {
			String service = req.getParameter("service");
			if (service.equals("login")) {
				new LoginController(ms, req, res);
			} else if (service.equals("subject")) {
				new SubjectController(ms, req, res);
			} else {
				req.getRequestDispatcher("/main.jsp").forward(req,res);
			}
			
//		} else {
//			new LoginController(ms, req, res);
//		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}
}
