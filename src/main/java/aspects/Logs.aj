package aspects;

import org.apache.log4j.Logger;

public aspect Logs {
	
	static Logger logger = Logger.getLogger(Logs.class);

	before(Exception e): handler(Exception+) && args(e) {
		logger.error(thisJoinPoint.getSourceLocation().toString() + " Error: " + e.toString());
	}

}
