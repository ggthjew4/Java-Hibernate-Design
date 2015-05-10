package practice.fackernate.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import operation.model.Btuser;

import org.junit.Before;
import org.junit.Test;

import practice.fackernate.Connector;
import practice.fackernate.Session;
import practice.fackernate.config.Configuration;
import practice.fackernate.config.impl.XmlConfiguration;
import practice.fackernate.mapping.MappingObject;
import practice.fackernate.mapping.Property;

public class SessionImplTest {
	
	Configuration config;
	Session session;
	Connector connector;
	
	@Before
	public void setUp() throws Exception {
		
		MappingObject obj = new MappingObject();
		obj.setTableName("btuser");
		obj.setId(new Property("USR_SN", "usrSn", "java.lang.Long"));
		obj.getProperties().add(new Property("USR_ID", "usrId", "java.lang.String"));
		obj.getProperties().add(new Property("USR_NAME", "usrName", "java.lang.String"));
		obj.getProperties().add(new Property("USR_PWD", "usrPwd", "java.lang.String"));
		obj.getProperties().add(new Property("USR_AGE", "usrAge", "java.lang.Integer"));
		obj.getProperties().add(new Property("USR_VER", "usrVer", "java.lang.Integer"));
		
		config = mock(XmlConfiguration.class);
		connector = mock(OracleConnector.class);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("USR_SN", 99L);
		map.put("USR_ID", "123456789");
		map.put("USR_NAME", "mockedUser");
		map.put("USR_PWD", "mockedPwd");
		map.put("USR_AGE", 30);
		map.put("USR_VER", 1);
		
		when(config.getMappingObjectByClass(Btuser.class)).thenReturn(obj);
		when(connector.get(new Long(99),obj)).thenReturn(map);
		
		session = new SessionImpl(config, connector);
	}

	@Test
	public void testGet() {
		Btuser user = (Btuser) session.get(Btuser.class, 99L);
		
		assertEquals(new Long(99), user.getUsrSn());
		assertEquals("123456789", user.getUsrId());
		assertEquals("mockedUser", user.getUsrName());
		assertEquals("mockedPwd", user.getUsrPwd());
		assertEquals(new Integer(30), user.getUsrAge());
		assertEquals(new Integer(1), user.getUsrVer());
	}

}
