package aspects;

import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import services.SubjectService;

public aspect Transactions {
	
	pointcut dbTrans(SubjectService sService, HttpServletRequest req):
		target(sService) && args(req) &&
		execution(* add*ToDB(HttpServletRequest));
	
	Long around(SubjectService sService, HttpServletRequest req): dbTrans(sService, req) {
		
		EntityTransaction tx = sService.getTransactionsManager();
		
		tx.begin();
		try {
			return proceed(sService, req);
		} catch (Exception e) {
			tx.rollback();
			return null;
		} finally {
			tx.commit();
		}		
	}
	
	pointcut deleteTrans(SubjectService sService, HttpServletRequest req):
		target(sService) && args(req) &&
		execution(* delete*FromDB(HttpServletRequest));
	
	HttpServletRequest around(SubjectService sService, HttpServletRequest req): deleteTrans(sService, req) {
		
		EntityTransaction tx = sService.getTransactionsManager();
		
		tx.begin();
		try {
			return proceed(sService, req);
		} catch (Exception e) {
			tx.rollback();
			return null;
		} finally {
			tx.commit();
		}		
	}
	
}
	
