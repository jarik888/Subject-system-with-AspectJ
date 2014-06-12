package services;

import java.util.List;

import javax.persistence.EntityManager;

import backend.dao.GenericDAO;
import backend.data.Address;
import backend.data.Contact;
import backend.data.Customer;
import backend.data.Employee;
import backend.data.Employee_role;
import backend.data.Enterprise;
import backend.data.Enterprise_person_relation;
import backend.data.Person;
import backend.data.Subject_attribute;
import backend.data.User_account;

public class DeleteSubjectService {
	
	EntityManager manager;
	SubjectService subjectS;

	public DeleteSubjectService(EntityManager em, SubjectService ss) {
		manager = em;
		subjectS = ss;
	}
	
	public boolean deleteSubject(Long subjectId, Long subjectType) {
		
		try {
			
			if (subjectType == 1) {
				deletePerson(subjectId);
				deleteAttributes(subjectId, subjectType);
				deleteAddresses(subjectId, subjectType);
				deleteContacts(subjectId, subjectType);
			} else if (subjectType == 2) {
				if (getEnterprisePersonRelationListByEnterpriseId(subjectId).size() > 0) {
					return false;
				}
				deleteEnterprise(subjectId);
				deleteAttributes(subjectId, subjectType);
				deleteAddresses(subjectId, subjectType);
				deleteContacts(subjectId, subjectType);
			} else if (subjectType == 3) {
				deleteEmployee(subjectId);
			} else if (subjectType == 4) {
				if (subjectS.getCustomerBySubjectId(subjectId, 1) != null) { //person
					Customer c = subjectS.getCustomerBySubjectId(subjectId, 1);
					deletePerson(subjectId);
					deleteAttributes(subjectId, 1L);
					deleteAttributes(subjectId, 4L);
					deleteAddresses(subjectId, 1L);
					deleteContacts(subjectId, 1L);
					manager.remove(c);
				} else if (subjectS.getCustomerBySubjectId(subjectId, 2) != null) { //enterprise
					Customer c = subjectS.getCustomerBySubjectId(subjectId, 2);
					Long enterpriseId = c.getSubjectFk();
					if (getEnterprisePersonRelationListByEnterpriseId(enterpriseId).size() > 0) {
						System.out.println("eprList!");
						return false;
					}
					deleteEnterprise(subjectId);
					deleteAttributes(subjectId, 2L);
					deleteAttributes(subjectId, 4L);
					deleteAddresses(subjectId, 2L);
					deleteContacts(subjectId, 2L);					
					manager.remove(c);					
				}				
			}
			
			return true;			
		} catch (Exception e) {
			return false;
		}
		
		
	}

	private List<Enterprise_person_relation> getEnterprisePersonRelationListByEnterpriseId(Long enterprise_fk) {
		List<Enterprise_person_relation> eprList = null;
		try {
			GenericDAO eprDAO = new GenericDAO(manager, Enterprise_person_relation.class);
			String query = "SELECT epr FROM Enterprise_person_relation epr WHERE epr.enterprise_fk = :enterprise_fk";
			String param_name = "enterprise_fk";
			Long param_value = enterprise_fk;
			eprList = (List<Enterprise_person_relation>) eprDAO.findByQuery(query, param_name, param_value);
		} catch (Exception e) {}
		return eprList;
	}

	private void deleteEmployee(Long subjectId) {
		
		Employee emp = subjectS.employeeS.getEmployeeById(subjectId);
		Long personId = emp.getPersonFk();
		
		Enterprise_person_relation epr = subjectS.employeeS.getEnterprisePersonRelationByPersonId(personId);
		manager.remove(epr);
		
		Employee_role empR = subjectS.employeeS.getEmplyeeRoleByEmpId(emp.getEmployee());
		manager.remove(empR);
		
		User_account ua = subjectS.loginS.getUserAccByEmplyeeId(emp.getEmployee());
		manager.remove(manager.merge(ua));
		
		deletePerson(personId);
		deleteAttributes(personId, 1L);
		deleteAttributes(personId, 3L);
		deleteAddresses(personId, 1L);
		deleteContacts(personId, 1L);
		
		manager.remove(emp);
		
	}

	private void deleteContacts(Long subjectId, Long subjectType) {
		List<Contact> cList = subjectS.contactS.getSubjectContacts(subjectId, subjectType);
		for (Contact c : cList) {
			manager.remove(c);
		}
	}

	private void deleteAddresses(Long subjectId, Long subjectType) {
		List<Address> aList = subjectS.addressS.getSubjectAdresses(subjectId, subjectType);
		for (Address a : aList) {
			manager.remove(a);
		}
		
	}

	private void deleteAttributes(Long subjectId, Long subjectType) {
		List<Subject_attribute> saList = subjectS.getSubjectAtributes(subjectId);
		for (Subject_attribute sa : saList) {
			if (sa.getSubjectFk() == subjectId && sa.getSubjectTypeFk() == subjectType) {
				manager.remove(sa);
			}
		}
	}

	private void deletePerson(Long personId) {
		Person p = subjectS.getPersonById(personId);
		manager.remove(p);
	}
	
	private void deleteEnterprise(Long subjectId) {
		Enterprise e = subjectS.getEnterpriseById(subjectId);
		manager.remove(e);
		
	}
	
}
