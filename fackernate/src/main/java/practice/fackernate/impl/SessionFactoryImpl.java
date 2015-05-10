package practice.fackernate.impl;




import practice.fackernate.DataSource;
import practice.fackernate.Session;
import practice.fackernate.SessionFactory;
import practice.fackernate.config.Configuration;

public class SessionFactoryImpl implements SessionFactory {
	
	DataSource dataSource;
	Session session;
	Configuration config;
	
	
	public SessionFactoryImpl(DataSource dataSource, Configuration config) {
		this.dataSource = dataSource;
		this.config = config;
	}
	
	@Override
	public Session getSession() {
		
		if (session == null) {
			session = new SessionImpl(config, dataSource.getConnector());
		}
		
		return session;
	}
	
}
