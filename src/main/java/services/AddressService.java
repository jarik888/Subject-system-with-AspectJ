package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import backend.dao.GenericDAO;
import backend.data.Address;
import backend.data.Person;

public class AddressService {
	
	EntityManager manager;

	public AddressService(EntityManager em) {
		manager = em;
	}
	
	public Address getAddressById(long id) {
		Address a = null;
		try {
			GenericDAO addressDAO = new GenericDAO(manager, Address.class);
			a = (Address) addressDAO.find(id);
		} catch (Exception e) {}
		return a;
	}
	
	public List<Address> getSubjectAdresses(long subject_fk, long subject_type_fk) {
		List<Address> sAddresses = null;
		try {
			GenericDAO addressDAO = new GenericDAO(manager, Address.class);
			String query = "SELECT sa FROM Address sa WHERE sa.subject_fk = :subject_fk AND sa.subject_type_fk = :subject_type_fk";
			List<String> paramNamesList = new ArrayList<String>();
			ArrayList<Long> paramValuesList = new ArrayList<Long>();
			paramNamesList.add("subject_fk");
			paramValuesList.add(subject_fk);
			paramNamesList.add("subject_type_fk");
			paramValuesList.add(subject_type_fk);
			sAddresses = (List<Address>) addressDAO.findByQueryWithMultipleParams(query, paramNamesList, paramValuesList);
		} catch (Exception e) {}
		return sAddresses;
	}
	
	public void addSubjectAddress(long subjectId, long subject_type_fk, HttpServletRequest req) {
		GenericDAO addressDAO = new GenericDAO(manager, Address.class);
		Address a = new Address();
		a.setSubjectTypeFk(subject_type_fk);
		a.setAddressType(Long.parseLong(req.getParameter("address_type_fk")));		
		a.setSubjectFk(subjectId);
		a.setCountry(req.getParameter("country"));
		a.setCounty(req.getParameter("county"));
		a.setTownVillage(req.getParameter("town_village"));
		a.setStreetAddress(req.getParameter("street_address"));
		a.setZipcode(req.getParameter("zipcode"));
		addressDAO.persist(a);
	}
	
	public HttpServletRequest addSubjectAddressesToReq(HttpServletRequest req,
			List<Address> sAddrList) {
		for(Address a : sAddrList) {
			if(a.getAddressType() == 1 || a.getAddressType() == 3) {
				req.setAttribute("address_id", a.getAddress());
				req.setAttribute("country", a.getCountry());
				req.setAttribute("county", a.getCounty());
				req.setAttribute("town_village", a.getTownVillage());
				req.setAttribute("street_address", a.getStreetAddress());
				req.setAttribute("zipcode", a.getZipcode());
				sAddrList.remove(a);
				req.setAttribute("sAddrList", sAddrList);
				break;
			}
		}
		return req;
	}

	public void updateSubjectAddresses(long subjectId, HttpServletRequest req) {
		
		Address address = getAddressById(Long.parseLong(req.getParameter("address_id")));
		address.setCountry(req.getParameter("country"));
		address.setCounty(req.getParameter("county"));
		address.setTownVillage(req.getParameter("town_village"));
		address.setStreetAddress(req.getParameter("street_address"));
		address.setZipcode(req.getParameter("zipcode"));
		
		int i = 1;
		while(req.getParameter("country"+i) != null) {
			Address a = null;
			if (req.getParameter("address_id"+i) != "") { //edit address
				a = getAddressById(Long.parseLong(req.getParameter("address_id"+i)));
			} else { // create new address
				a = new Address();
			}
			a.setSubjectTypeFk(Long.parseLong(req.getParameter("subjectTypeFk")));
			a.setAddressType(2L); //additional address
			a.setSubjectFk(subjectId);
			a.setCountry(req.getParameter("country"+i));
			a.setCounty(req.getParameter("county"+i));
			a.setTownVillage(req.getParameter("town_village"+i));
			a.setStreetAddress(req.getParameter("street_address"+i));
			a.setZipcode(req.getParameter("zipcode"+i));
			
			if (req.getParameter("address_id"+i) == "") {
				GenericDAO addressDAO = new GenericDAO(manager, Address.class);
				addressDAO.persist(a); // save new address
			}					
			i++;
		}		
	}
	
	public boolean deleteAddress(long id) {
		try {
			Address a = getAddressById(id);
			manager.remove(a);
			return true;
		} catch (Exception e) {}
		return false;
	}

}
