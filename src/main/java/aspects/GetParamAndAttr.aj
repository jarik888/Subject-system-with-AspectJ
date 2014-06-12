package aspects;

import javax.servlet.ServletRequest;

public aspect GetParamAndAttr {
	
	pointcut getParamInControllers(ServletRequest req, String s):
		target(req) && args(s) && within(frontend.controllers.*) &&
		call(String javax.servlet.ServletRequest.getParameter(String));
	
	String around(ServletRequest req, String s): getParamInControllers(req, s) {
		String result = req.getParameter(s);
		if (result == null) {
			return "";
		}
		return result;
	}
	
	//all get() calls in this aspect
	pointcut getCallsInAspect(): this(GetParamAndAttr) && call(* get*(String));
	
	pointcut getAttrEverywhere(ServletRequest req, String s):
		target(req) && args(s) && call(* *.getAttribute(..));
	
	Object around(ServletRequest req, String s): getAttrEverywhere(req, s) && !getCallsInAspect() {
		Object result = req.getAttribute(s); // avoid loop -> !getCallInAspect()
		if (result == null) {
			return "";
		}
		return result;
	}
}
