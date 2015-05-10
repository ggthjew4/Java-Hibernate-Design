package practice.fackernate;

import practice.fackernate.mapping.MappingObject;


public interface SqlCreator {
	
	/**
	 * insert into ${tableName} (${column}, ${column}..) values(?, ?..)
	 * @return
	 */
	String createSaveSql(MappingObject obj);
	
	/**
	 * update ${tableName}
	 * set ${column} = ?, ${column} = ?
	 * where ${id} = ?
	 * @return
	 */
	String createUpdateSql(MappingObject obj);
	
	/**
	 * delete from ${tableName{
	 * where ${id} = ?
	 * @param clazz
	 * @return
	 */
	String createDeleteSql(MappingObject obj);
	
	/**
	 * select $[column}, ${column} from ${tableName}
	 * where ${id} = ? 
	 * @param clazz
	 * @return
	 */
	String createGetSql(MappingObject obj);
}
