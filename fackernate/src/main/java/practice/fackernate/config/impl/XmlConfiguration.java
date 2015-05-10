package practice.fackernate.config.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import practice.fackernate.config.Configuration;
import practice.fackernate.exception.ConfigNotFoundException;
import practice.fackernate.exception.DuplicateClassConfigException;
import practice.fackernate.exception.XmlParsingException;
import practice.fackernate.mapping.MappingObject;
import practice.fackernate.mapping.Property;

public class XmlConfiguration implements Configuration {
	
	
	public static final String XMLFILE_POSTFIX = ".fbm.xml";
	
	/**
	 * key : classs.getName()
	 * value : config mapping object
	 */
	private Map<String, MappingObject> xmlConfig = new HashMap<String, MappingObject>();
	
	/**
	 * clear all data in memory
	 */
	public void clear() {
		if (xmlConfig != null) {
			xmlConfig.clear();
		}
	}
	
	/**
	 * bind data form xml file
	 * @param xmlFile
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void bindXmlFile(File xmlFile)  {
		try {
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			if (xmlFile.exists()) {
				final Document doc = dBuilder.parse(xmlFile);
				
				//validate xml
				// create a SchemaFactory capable of understanding WXS schemas
				SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	
				// load a WXS schema, represented by a Schema instance
				Source schemaFile = new StreamSource(Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("FackernateSchema.xsd"));
				Schema schema = factory.newSchema(schemaFile);
	
				// create a Validator instance, which can be used to validate an instance document
				Validator validator = schema.newValidator();
	
				// validate the DOM tree
				try {
				    validator.validate(new DOMSource(doc));
				} catch (SAXException e) {
					throw new XmlParsingException(e);
				}
				
				final Element root = doc.getDocumentElement();
				final Element classNode = (Element) root.getElementsByTagName("class").item(0);

				final String fullClassName = root.getAttribute("package") + "."
						+ classNode.getAttribute("name");

				MappingObject obj = new MappingObject();
				obj.setClassName(fullClassName);
				obj.setTableName(classNode.getAttribute("table"));

				obj.setId(new Property((Element) classNode.getElementsByTagName("id").item(0)));

				final NodeList propertyNodes = classNode.getElementsByTagName("property");
				for (int i = 0; i < propertyNodes.getLength(); i++) {
					final Element propertyElement = (Element) propertyNodes.item(i);
					obj.getProperties().add(new Property(propertyElement));
				}

				if (xmlConfig.containsKey(fullClassName)) {
					throw new DuplicateClassConfigException(fullClassName);
				} else {
					xmlConfig.put(fullClassName, obj);
				}

			} else {
				throw new FileNotFoundException();
			}
		} catch (DOMException
				| ParserConfigurationException | SAXException | IOException e) {
			throw new XmlParsingException(e);
		}
	}
	
	/**
	 * bind one file
	 * @param fileName
	 */
	public void bindFileName(String fileName) {
		
		//get file in the src/main(test)/java/resources
		URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
		
		if (url == null) {
			throw new ConfigNotFoundException(fileName);
		}
		
		final File file = new File(url.getPath());
		this.bindXmlFile(file);
	}
	
	/**
	 * bind a set of files
	 * @param fileNames
	 */
	public void bindFileNames(Set<String> fileNames) {
		for (String fileName : fileNames) {
			this.bindFileName(fileName);
		}
	}
	
	@Override
	public MappingObject getMappingObjectByClass(Class<?> clazz) {
		
		MappingObject obj = xmlConfig.get(clazz.getName());
		
		if (obj == null) {
			throw new ConfigNotFoundException(clazz.getName());
		}
		
		return obj;
	}

}
