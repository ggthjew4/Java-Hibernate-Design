package practice.fackernate.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import practice.fackernate.Connector;
import practice.fackernate.DataSource;
import practice.fackernate.SqlCreator;
import practice.fackernate.exception.SQLRuntimeException;
import practice.fackernate.mapping.MappingObject;
import practice.fackernate.mapping.Property;

public class OracleConnector implements Connector{
	
	/**
	 * data base connection info
	 */
	private DataSource dataSource;
	
	/**
	 * create sql 
	 */
	private SqlCreator sqlCreator;
	
	public OracleConnector(DataSource dataSource) {
		this.dataSource = dataSource;
		//oracel go with oracle sql creator
		this.sqlCreator = new OracleSqlCreator();
	}
	
	@Override
	public Long save(Object obj, MappingObject configObj) {
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		Long generatedKey = null;
		try {
			conn = DriverManager.getConnection(dataSource.getUrl(), 
					dataSource.getUserName(), dataSource.getPassword());
			
			st = conn.prepareStatement(sqlCreator.createSaveSql(configObj),Statement.RETURN_GENERATED_KEYS);
			int i = 1;
			st.setObject(i++, configObj.getId().invokeGetter(obj)); //null or id
			for (Property property : configObj.getProperties()) {
				st.setObject(i++, property.invokeGetter(obj));
			}
			st.execute();
			rs = st.getGeneratedKeys();
			if (rs.first()) {
				generatedKey = rs.getLong(1);
				configObj.getId().invokeSetter(obj, generatedKey); //set id
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					throw new SQLRuntimeException(e);
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new SQLRuntimeException(e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new SQLRuntimeException(e);
				}
			}
		}
		return generatedKey;
	}

	@Override
	public void update(Object obj, MappingObject configObj) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DriverManager.getConnection(dataSource.getUrl(), 
					dataSource.getUserName(), dataSource.getPassword());
			
			st = conn.prepareStatement(sqlCreator.createUpdateSql(configObj));
			
			int i = 1;
			for (Property property : configObj.getProperties()) {
				st.setObject(i++, property.invokeGetter(obj));
			}
			st.setObject(i, configObj.getId().invokeGetter(obj));
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					throw new SQLRuntimeException(e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new SQLRuntimeException(e);
				}
			}
		}
	}

	@Override
	public void delete(Object obj, MappingObject configObj) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DriverManager.getConnection(dataSource.getUrl(), 
					dataSource.getUserName(), dataSource.getPassword());
			
			st = conn.prepareStatement(sqlCreator.createDeleteSql(configObj));
			st.setObject(1, configObj.getId().invokeGetter(obj));
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					throw new SQLRuntimeException(e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new SQLRuntimeException(e);
				}
			}
		}
	}

	@Override
	public Map<String, Object> get(Serializable id, MappingObject configObj) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		Map<String, Object> map = null;
		try {
			conn = DriverManager.getConnection(dataSource.getUrl(), 
					dataSource.getUserName(), dataSource.getPassword());
			
			st = conn.prepareStatement(sqlCreator.createGetSql(configObj));
			st.setObject(1, id);
			rs = st.executeQuery();
			
			if (rs.first()) {
				//resutl set to map
				map = new HashMap<String, Object>();
				map.put(configObj.getId().getColumn(), rs.getObject(configObj.getId().getColumn(),
						configObj.getId().getTypeClazz()));
				for (Property property : configObj.getProperties()) {
					map.put(property.getColumn(), rs.getObject(property.getColumn(),
							property.getTypeClazz()));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					throw new SQLRuntimeException(e);
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new SQLRuntimeException(e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new SQLRuntimeException(e);
				}
			}
		}
		return map;
	}

}
