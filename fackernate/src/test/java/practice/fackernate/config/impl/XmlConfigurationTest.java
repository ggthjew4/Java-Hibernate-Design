package practice.fackernate.config.impl;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import practice.fackernate.config.Configuration;
import practice.fackernate.exception.ClassNotFoundRuntimeException;
import practice.fackernate.exception.DuplicateClassConfigException;
import practice.fackernate.exception.XmlParsingException;
import practice.fackernate.mapping.MappingObject;
import practice.fackernate.model.Address;
import practice.fackernate.model.Btuser;


/**
 * you should run this test by maven test
 * because this test need a file from scr/test/resources
 * @author JohnFang21
 *
 */
public class XmlConfigurationTest {

	static Configuration xmlConfig;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		//initial
		XmlConfiguration config = new XmlConfiguration();
		
		Set<String> fileNames = new HashSet<String>();
		fileNames.add("Btuser" + XmlConfiguration.XMLFILE_POSTFIX);
		fileNames.add("Address" + XmlConfiguration.XMLFILE_POSTFIX);
		
		config.bindFileNames(fileNames);
		
		xmlConfig = config;
	}
	
	@Test(expected=DuplicateClassConfigException.class)
	public void testClassDuplicate() {
		XmlConfiguration config = (XmlConfiguration) xmlConfig;
		config.bindFileName("Btuser" + XmlConfiguration.XMLFILE_POSTFIX);
	}
	
	@Test(expected=ClassNotFoundRuntimeException.class)
	public void testClassNotFound() {
		XmlConfiguration config = (XmlConfiguration) xmlConfig;
		config.bindFileName("AddressFake1" + XmlConfiguration.XMLFILE_POSTFIX);
	}
	
	@Test(expected=XmlParsingException.class)
	public void testXmlValidate() {
		XmlConfiguration config = (XmlConfiguration) xmlConfig;
		config.bindFileName("AddressFake2" + XmlConfiguration.XMLFILE_POSTFIX);
	}
	
	@Test
	public void testGetMappingObjectByClass() {
		
		MappingObject obj = xmlConfig.getMappingObjectByClass(Btuser.class);
		
		assertEquals("btuser", obj.getTableName());
		assertEquals("USR_SN", obj.getId().getColumn());
		assertEquals("usrName", obj.getProperties().get(1).getName());
		assertEquals(Long.class, obj.getId().getTypeClazz());
		assertEquals("USR_VER", obj.getProperties().get(4).getColumn());
		assertEquals(Integer.class, obj.getProperties().get(4).getTypeClazz());
		
		MappingObject address = xmlConfig.getMappingObjectByClass(Address.class);
		
		assertEquals("address", address.getTableName());
		assertEquals("ID", address.getId().getColumn());
		assertEquals(Long.class, address.getId().getTypeClazz());
		assertEquals("zipCode", address.getProperties().get(1).getName());
	}
	
}
