package practice.fackernate;

import practice.fackernate.impl.OracleConnector;

/**
 * database connection info
 * @author JohnFang21
 *
 */
public class DataSource {
	
	private String url;
	private String userName;
	private String password;
	private String driverName;
	
	private Connector connector;
	
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}
	/**
	 * decide what kind connector by drivername
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	/**
	 * @return the connector
	 */
	public Connector getConnector() {
		
		if (connector == null) {
			if (true) {
				connector = new OracleConnector(this);
			}
		}
		
		return connector;
	}

}
