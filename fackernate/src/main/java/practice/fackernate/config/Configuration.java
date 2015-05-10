package practice.fackernate.config;

import practice.fackernate.mapping.MappingObject;

public interface Configuration {
	
	/**
	 * according to class package name , class name to get the config content
	 * @param clazz ( package name, class name)
	 * @return config mapping object
	 */
	MappingObject getMappingObjectByClass(Class<?> clazz);

}
