package practice.fackernate;

import java.io.Serializable;

public interface Session {
	
	/**
	 * 1. get connection
	 * 2. find config by obj class package name and obj class name
	 * 3. create save sql base on config
	 * 4. set parameters base on config
	 * 5. send sql 
	 * 6. return result
	 * @param obj
	 */
	void save(Object obj);
	
	/**
 	 * 1. get connection
	 * 2. find config by obj class package name and obj class name
	 * 3. create update sql base on config
	 * 4. set parameters base on config
	 * 5. send sql 
	 * 6. return result 
	 * @param update
	 */
	void update(Object obj);
	
	/**
	 * 1. get connection
	 * 2. find config by obj class package name and obj class name
	 * 3. create delete sql base on config
	 * 4. set parameters base on config
	 * 5. send sql 
	 * 6. return result
	 * @param obj
	 * @return is delete success
	 */
	boolean delete(Object obj);
	
	/**
	 * 1. get connection
	 * 2. find config by obj class package name and obj class name
	 * 3. create get sql base on config
	 * 4. set parameters base on config
	 * 5. send sql 
	 * 6. return result
	 * @param clazz
	 * @param id
	 * @return get by id
	 */
	Object get(Class<?> clazz, Serializable id);

}
