package practice.fackernate.impl;

import java.io.Serializable;
import java.util.Map;

import practice.fackernate.Connector;
import practice.fackernate.Session;
import practice.fackernate.config.Configuration;
import practice.fackernate.exception.ClassFomatException;
import practice.fackernate.mapping.MappingObject;
import practice.fackernate.mapping.Property;

public class SessionImpl implements Session {

	Configuration config;
	Connector connector;
	/**
	 * only allow to be created by sessionfactory(same package)
	 */
	protected SessionImpl(Configuration config, Connector connector) {
		this.config = config;
		this.connector = connector;
	}
	
	@Override
	public void save(Object obj) {
		connector.save(obj, config.getMappingObjectByClass(obj.getClass()));
	}

	@Override
	public void update(Object obj) {
		connector.update(obj, config.getMappingObjectByClass(obj.getClass()));
	}

	@Override
	public boolean delete(Object obj) {
		connector.delete(obj, config.getMappingObjectByClass(obj.getClass()));
		return false;
	}

	@Override
	public Object get(Class<?> clazz, Serializable id) {
		
		Object returnObj = null;
		try {
			MappingObject mappingObj = config.getMappingObjectByClass(clazz);
			
			returnObj = clazz.newInstance();
			mappingObj.getId().invokeSetter(returnObj, id);
			//do sql query
			Map<String, Object> result = connector.get(id, mappingObj);
			
			//set result
			mappingObj.getId().invokeSetter(returnObj, result.get(mappingObj.getId().getColumn()));
			for (Property property : mappingObj.getProperties()) {
				property.invokeSetter(returnObj, result.get(property.getColumn()));
			}
			
			//do setting parameter
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
			// TODO Auto-generated catch block
			throw new ClassFomatException(e);
		}
		
		return returnObj;
	}

}
