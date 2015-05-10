package practice.fackernate.mapping;

import java.util.ArrayList;
import java.util.List;

import practice.fackernate.exception.ClassNotFoundRuntimeException;

public class MappingObject {
	
	/**
	 * full class name ex : practice.fackernate.config.MappingObject
	 */
	private String className;
	private String tableName;
	private Class<?> clazz;
	private Property id;
	private List<Property> properties = new ArrayList<Property>();
	
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * initialize the class during set class name
	 * @param className the className to set
	 * @throws ClassNotFoundException 
	 */
	public void setClassName(String className) {
		this.className = className;
		try {
			this.clazz = Class.forName(this.className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new ClassNotFoundRuntimeException(e);
		}
	}
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the id
	 */
	public Property getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Property id) {
		this.id = id;
	}
	/**
	 * @return the properties
	 */
	public List<Property> getProperties() {
		return properties;
	}
	/**
	 * @param properties the properties to set
	 */
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	/**
	 * @return the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}

}
