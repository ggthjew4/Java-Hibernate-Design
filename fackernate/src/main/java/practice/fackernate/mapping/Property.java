package practice.fackernate.mapping;

import java.lang.reflect.InvocationTargetException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

import practice.fackernate.exception.ClassFomatException;
import practice.fackernate.exception.ClassNotFoundRuntimeException;

public class Property {
	
	/**
	 * db column
	 */
	private String column;
	
	/**
	 * class property
	 */
	private String name;
	
	/**
	 * property type
	 */
	private String type;
	
	private Class<?> typeClazz;
	
	public Property() {
		
	}
	
	/**
	 * create property from xml node
	 * @param node
	 * @throws DOMException 
	 * @throws ClassNotFoundException 
	 */
	public Property(Element e) {
		
		this.setColumn(e.getAttribute("column"));
		this.setName(e.getAttribute("name"));
		this.setType(e.getAttribute("type"));
	}
	
	public Property(String column, String name, String type) {
		this.setColumn(column);
		this.setName(name);
		this.setType(type);
	}
	
	/**
	 * set this property in input object
	 * @param obj object to be invoked
	 * @param value setter input value
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public void invokeSetter(Object obj, Object value) {
		try {
			obj.getClass().getMethod(this.getSetterMethodName(), this.typeClazz).invoke(obj, value);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			// TODO Auto-generated catch block
			throw new ClassFomatException(e);
		}
	}
	
	/**
	 * get thi property in input object
	 * @param obj object to be invoked
	 * @return value of this property of input object 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public Object invokeGetter(Object obj) {
		try {
			return obj.getClass().getMethod(this.getGetterMethodName(), new Class<?>[0]).invoke(obj, new Object[0]);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new ClassFomatException(e);
		}
	}
	
	
	public Class<?> getTypeClazz() {
		return this.typeClazz;
	}
	
	/**
	 * get setXxx by property name
	 * name -> setName
	 * @return
	 */
	public String getSetterMethodName() {
		
		if (name != null && name.length() > 0) {
			return "set" + name.substring(0,1).toUpperCase() + name.substring(1);
		} else {
			throw new StringIndexOutOfBoundsException("name length must larger than 0");
		}
	}
	
	/**
	 * get getXxx by property name
	 * name -> getName
	 * @return
	 */
	public String getGetterMethodName() {
		
		if (name != null && name.length() > 0) {
			return "get" + name.substring(0,1).toUpperCase() + name.substring(1);
		} else {
			throw new StringIndexOutOfBoundsException("name length must larger than 0");
		}
	}
	
	/**
	 * @return the column
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 * @throws ClassNotFoundException 
	 */
	public void setType(String type) {
		this.type = type;
		try {
			this.typeClazz = Class.forName(type);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new ClassNotFoundRuntimeException(e);
		}
	}

	
}
