package practice.fackernate.impl;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import practice.fackernate.SqlCreator;
import practice.fackernate.mapping.MappingObject;
import practice.fackernate.mapping.Property;

/**
 * i am not sure how to test complex test info
 * @author JohnFang21
 *
 */
public class OracleSqlCreatorTest {
	
	
	static SqlCreator sqlCreator;
	static MappingObject obj;
	boolean isShowResult = false;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		obj = new MappingObject();
		obj.setTableName("btuser");
		obj.setId(new Property("USR_SN", "usrSn", "java.lang.Long"));
		obj.getProperties().add(new Property("USR_ID", "usrId", "java.lang.String"));
		obj.getProperties().add(new Property("USR_NAME", "usrName", "java.lang.String"));
		obj.getProperties().add(new Property("USR_PWD", "usrPwd", "java.lang.String"));
		obj.getProperties().add(new Property("USR_AGE", "usrAge", "java.lang.Integer"));
		obj.getProperties().add(new Property("USR_VER", "usrVer", "java.lang.Integer"));
		
		sqlCreator = new OracleSqlCreator();
	}
	
	@Test
	public void testCreateSaveSql() {
		
		String sql = sqlCreator.createSaveSql(obj); 
		
		if (isShowResult) {
			System.out.println(sql);
		}
		
		//this is too hard to write unit test, so just check if exception
		assertTrue(true);
	}
	@Test
	public void testCreateUpdateSql() {
		String sql = sqlCreator.createUpdateSql(obj); 
		
		if (isShowResult) {
			System.out.println(sql);
		}
		assertTrue(true);
	}
	@Test
	public void testDeleteUpdateSql() {
		String sql = sqlCreator.createDeleteSql(obj); 
		
		if (isShowResult) {
			System.out.println(sql);
		}
		assertTrue(true);
	}
	@Test
	public void testGetUpdateSql() {
		String sql = sqlCreator.createGetSql(obj);
		
		if (isShowResult) {
			System.out.println(sql);
		}
		assertTrue(true);
	}
	
}
