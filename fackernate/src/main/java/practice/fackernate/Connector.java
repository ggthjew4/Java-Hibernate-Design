package practice.fackernate;

import java.io.Serializable;
import java.util.Map;

import practice.fackernate.mapping.MappingObject;

public interface Connector {
	
	/**
	 * create db connect
	 * create sql from config
	 * set parameter from input obj
	 * id -> auto key
	 * execute sql
	 * close connection
	 * return id
	 * @param obj
	 * @param configObj
	 * @return
	 */
	Long save(Object obj, MappingObject configObj);
	
	/**
	 * create connection
	 * create sql from config
	 * set parameter from input object
	 * execute sql
	 * close connection 
	 * @param obj
	 * @param configObj
	 */
	void update(Object obj, MappingObject configObj);
	
	/**
	 * create connection
	 * create sql from config
	 * set id from input object
	 * execute sql
	 * close connection
	 * @param obj
	 * @param configObj
	 */
	void delete(Object obj, MappingObject configObj);
	
	/**
	 * create connection
	 * create sql from config
	 * set id from input object
	 * execute sql
	 * close connection
	 * return map from result set
	 * @param obj
	 * @param configObj
	 * @return
	 */
	Map<String, Object> get(Serializable id, MappingObject configObj);

}
