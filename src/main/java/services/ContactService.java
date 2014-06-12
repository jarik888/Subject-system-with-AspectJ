package services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import backend.dao.GenericDAO;
import backend.data.Address;
import backend.data.Contact;

public class ContactService {
	
	EntityManager manager;

	public ContactService(EntityManager em) {
		manager = em;
	}
	
	public Contact getContactById(long id) {
		Contact c = null;
		try {
			GenericDAO contactDAO = new GenericDAO(manager, Contact.class);
			c = (Contact) contactDAO.find(id);
		} catch (Exception e) {}
		return c;
	}
	
	public List<Contact> getSubjectContacts(long subject_fk, long subject_type_fk) {
		List<Contact> cList = null;
		try {
			GenericDAO personDAO = new GenericDAO(manager, Contact.class);
			String query = "SELECT cList FROM Contact cList WHERE cList.subject_fk = :subject_fk "
						 + "AND cList.subject_type_fk = :subject_type_fk";
			List<String> paramNamesList = new ArrayList<String>();
			ArrayList<Long> paramValuesList = new ArrayList<Long>();
			paramNamesList.add("subject_fk");
			paramValuesList.add(subject_fk);
			paramNamesList.add("subject_type_fk");
			paramValuesList.add(subject_type_fk);
			cList = (List<Contact>) personDAO.findByQueryWithMultipleParams(query, paramNamesList, paramValuesList);
		} catch (Exception e) {}
		return cList;
	}
	
	public void addSubjectContact(long subjectId, long subject_type_fk,	HttpServletRequest req) {		
		GenericDAO contactDAO = new GenericDAO(manager, Contact.class);
		Contact c = new Contact();
		c.setSubjectTypeFk(subject_type_fk);
		c.setSubjectFk(subjectId);
		c.setContactTypeFk(Long.parseLong(req.getParameter("contact_type")));
		c.setValueText(req.getParameter("contact"));
		c.setOrderby(Long.parseLong(req.getParameter("contact_orderby")));
		c.setNote(req.getParameter("note"));
		contactDAO.persist(c);
	}
	
	public HttpServletRequest addSubjectContactsToReq(HttpServletRequest req,
			List<Contact> sContList) {
		for(Contact c : sContList) {
			req.setAttribute("contact_id", c.getContact());
			req.setAttribute("contact_type", c.getContactTypeFk());
			req.setAttribute("contact", c.getValueText());
			req.setAttribute("contact_orderby", c.getOrderby()); // test it!
			req.setAttribute("note", c.getNote());
			sContList.remove(c);
			req.setAttribute("sContList", sContList);
			break;
		}
		return req;
	}

	public void updateSubjectContacts(long subjectId, HttpServletRequest req) {
		
		Contact contact = getContactById(Long.parseLong(req.getParameter("contact_id")));
		contact.setContactTypeFk(Long.parseLong(req.getParameter("contact_type")));
		contact.setValueText((req.getParameter("contact")));
		contact.setOrderby(Long.parseLong(req.getParameter("contact_orderby")));
		contact.setNote(req.getParameter("note"));
		
		int i = 1;
		while(req.getParameter("contact"+i) != null) {

			Contact c = null;
			if (req.getParameter("contact_id"+i) != "") { //edit contact
				c = getContactById(Long.parseLong(req.getParameter("contact_id"+i)));
			} else { // create new contact
				c = new Contact();
			}
			c.setSubjectTypeFk(Long.parseLong(req.getParameter("subjectTypeFk")));
			c.setSubjectFk(subjectId);
			c.setContactTypeFk(Long.parseLong(req.getParameter("contact_type"+i)));
			c.setValueText((req.getParameter("contact"+i)));
			c.setOrderby(Long.parseLong(req.getParameter("contact_orderby"+i)));
			c.setNote(req.getParameter("note"+i));
			
			if (req.getParameter("contact_id"+i) == "") {
				GenericDAO contactDAO = new GenericDAO(manager, Contact.class);
				contactDAO.persist(c); // save new contact
			}			
			i++;
		}		
	}

	public boolean deleteContact(long id) {
		try {
			Contact c = getContactById(id);
			manager.remove(c);
			return true;
		} catch (Exception e) {}
		return false;
	}

}
