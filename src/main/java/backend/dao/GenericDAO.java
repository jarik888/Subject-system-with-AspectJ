package backend.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.*;

public class GenericDAO<T> {
	
	private EntityManager manager;
	private Class<T>      genericType;

	public GenericDAO(EntityManager manager, Class<T> clazz) {
		this.manager = manager;
		this.genericType =  (Class<T>) clazz;
	}
	   
	   
	public void persist(T item)	{
		manager.persist(item);
	}
	
	public T find(long id) {
		return (T) manager.find(genericType, id);
	}
	
	public void refresh(T item) {
		manager.refresh(item);
	}
	
	public void merge(T item) {
		manager.merge(item);
	}
	
	public List<T> findByQuery(String query, String param_name, T param_value) {
		TypedQuery <T> q = manager.createQuery  (query, genericType);
		q.setParameter(param_name, param_value);
		List<T> results = (List<T>) q.getResultList();
		return results;
	}
	
	public List<T> findByQueryWithMultipleParams(String query, List<String> paramsNamesList, List<T> paramValuesList) {
		TypedQuery <T> q = manager.createQuery  (query, genericType);
		for (int i = 0; i < paramsNamesList.size(); i++) {
			q.setParameter(paramsNamesList.get(i), paramValuesList.get(i));
		}		
		List<T> results = (List<T>) q.getResultList();
		return results;
	}
	
	public T findOneObjectByQueryWithMultipleParams(String query, List<String> paramsNamesList, List<T> paramValuesList) {
		TypedQuery <T> q = manager.createQuery  (query, genericType);
		for (int i = 0; i < paramsNamesList.size(); i++) {
			q.setParameter(paramsNamesList.get(i), paramValuesList.get(i));
		}		
		T results = (T) q.getSingleResult();
		return results;
	}

	public T findOneObjectByQuery(String query, String param_name, T param_value) {
		TypedQuery <T> q = manager.createQuery  (query, genericType);
		q.setParameter(param_name, param_value);
		try {
			T results = (T) q.getSingleResult();
			return results;
		} catch (Exception e) {}
		return null;
	}

	public T find(String s1) {
		return (T) manager.find(genericType, s1);
	}
	
}
