package operation;

import java.util.HashSet;
import java.util.Set;

import operation.model.Btuser;
import practice.fackernate.DataSource;
import practice.fackernate.Session;
import practice.fackernate.config.impl.XmlConfiguration;
import practice.fackernate.impl.SessionFactoryImpl;

public class LetsGo {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/test";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "admin";
	
	public static void main(String[] args) {
		DataSource dataSource = new DataSource();
		dataSource.setUrl(DB_URL);
		dataSource.setUserName(USER);
		dataSource.setPassword(PASS);
		dataSource.setDriverName(JDBC_DRIVER);
		
		Set<String> fileNames = new HashSet<String>();
		fileNames.add("Btuser.fbm.xml");
		XmlConfiguration config = new XmlConfiguration();
		config.bindFileNames(fileNames);
		
		SessionFactoryImpl sessionFactory = new SessionFactoryImpl(dataSource, config);
		
		Session session = sessionFactory.getSession();
		
		System.out.println("\tstart save");
		Btuser user = new Btuser();
		user.setUsrId("123456789");
		user.setUsrName("John");
		user.setUsrPwd("password");
		user.setUsrAge(27);
		user.setUsrVer(100);
		
		System.out.println("saved user ver : " + user.getUsrVer());
		
		session.save(user);
		System.out.println("save success, usrSn : " + user.getUsrSn());
		
		System.out.println("\tstart update, usrVer 100 > 101");
		user.setUsrVer(101);
		session.update(user);
		System.out.println("update success");
		
		
		System.out.println("\tstart find usrSn = 1");
		Btuser result = (Btuser) session.get(Btuser.class, new Long(1));
		
		if (result != null) {
			System.out.println("find success");
			
			System.out.print(" user sn : " + result.getUsrSn());
			System.out.print(" user id : " + result.getUsrId());
			System.out.print(" user name : " + result.getUsrName());
			System.out.print(" user pwd : " + result.getUsrPwd());
			System.out.print(" user age : " + result.getUsrAge());
			System.out.println(" user ver : " + result.getUsrVer());
		} else {
			System.out.println("user not found");
		}
		
		System.out.println("\tstart delete user_sn : " + user.getUsrSn());
		session.delete(user);
	}

}
