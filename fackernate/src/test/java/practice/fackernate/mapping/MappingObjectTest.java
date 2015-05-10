package practice.fackernate.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;

import org.junit.BeforeClass;
import org.junit.Test;

import practice.fackernate.exception.ClassNotFoundRuntimeException;
import practice.fackernate.model.Btuser;

public class MappingObjectTest {
	
	final String className = "practice.fackernate.model.Btuser";
	static MappingObject obj;
	
	
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
	}

	@Test(expected=ClassNotFoundRuntimeException.class)
	public void testSetClassName() {
		obj.setClassName(className);
		assertTrue(true);
		obj.setClassName(className + "_postfix"); //exception
	}
	
	@Test
	public void testGetGetterSetterMethodName() {
		assertEquals("getUsrSn", obj.getId().getGetterMethodName());
		assertEquals("getUsrId", obj.getProperties().get(0).getGetterMethodName());
		assertEquals("getUsrName", obj.getProperties().get(1).getGetterMethodName());
		assertEquals("getEmail", new Property("","email", "java.lang.String").getGetterMethodName());
		assertEquals("getEMail", new Property("","eMail","java.lang.String").getGetterMethodName());
		assertEquals("setUsrSn", obj.getId().getSetterMethodName());
	}
	
	@Test(expected=StringIndexOutOfBoundsException.class)
	public void testGetGetterSetterMethodName2() {
		new Property("", null, "java.lang.String").getGetterMethodName();
	}
	
	@Test(expected=StringIndexOutOfBoundsException.class)
	public void testGetGetterSetterMethodName3() {
		new Property("", "", "java.lang.String").getGetterMethodName();
	}
	
	@Test
	public void testClassMethodInvoke() {
		Btuser user = new Btuser();
		user.setUsrSn(100L);
		user.setUsrName("username");
		
		Object oUser = (Object) user;
		
		try {
			
			assertEquals(oUser.getClass().getMethod("getUsrSn", new Class<?>[0]).invoke(oUser, new Object[0]),100L);
			assertEquals(oUser.getClass().getMethod("getUsrName", new Class<?>[0]).invoke(oUser, new Object[0]),"username");
			
			assertEquals(oUser.getClass().getMethod(obj.getId().getGetterMethodName(), new Class<?>[0]).invoke(oUser, new Object[0]),100L);
			assertEquals(oUser.getClass().getMethod(obj.getProperties().get(1).getGetterMethodName(), new Class<?>[0])
					.invoke(oUser, new Object[0]), "username");
			
			oUser.getClass().getMethod("setUsrName", String.class).invoke(oUser, "newUserName");
			assertEquals(oUser.getClass().getMethod("getUsrName", new Class<?>[0]).invoke(oUser, new Object[0]),"newUserName");
			
			oUser.getClass().getMethod(obj.getId().getSetterMethodName(), Long.class).invoke(oUser, 99L);
			assertEquals(oUser.getClass().getMethod(obj.getId().getGetterMethodName(), new Class<?>[0]).invoke(oUser, new Object[0]),99L);
			
			obj.getId().invokeSetter(oUser, 50L);
			assertEquals(obj.getId().invokeGetter(oUser), 50L);
			
			obj.getProperties().get(3).invokeSetter(oUser, 100);
			assertEquals(obj.getProperties().get(3).invokeGetter(oUser), 100);
			
			obj.getProperties().get(1).invokeSetter(oUser, "usernameagain");
			assertEquals(obj.getProperties().get(1).invokeGetter(oUser),"usernameagain");
			
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail("what are you doing here?");
		}
	}
}
