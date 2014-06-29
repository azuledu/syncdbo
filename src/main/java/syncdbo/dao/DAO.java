package syncdbo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.sql.DataSource;

import syncdbo.ConfManager;

import java.sql.PreparedStatement;

public abstract class DAO {

	static Logger logger = Logger.getLogger(DAO.class.getName());

	protected String dboName;
	protected Properties prop;
	protected Connection connection;
	protected DataSource datasource;

	public DAO(Properties prop) throws SQLException {
		String pr = "sql." + (String) ConfManager.getContext().get("dboName") + ".alias";
		String alias = prop.getProperty(pr);
		if (alias != null) {
			this.dboName = alias;
		} else {
			this.dboName = (String) ConfManager.getContext().get("dboName");
		}
		this.prop = prop;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public Collection<String> getSharedIDs() throws SQLException, NamingException {

		ArrayList<String> sharedIDs = new ArrayList<String>();

		String sharedID = prop.getProperty("sql." + dboName + ".sharedID");

		PreparedStatement stmt = getStmt();
		ResultSet rs = stmt.executeQuery();
		
		String sharedIDValue;
		while (rs.next()) {
			if (rs.getObject(sharedID) != null) {
				sharedIDValue = rs.getObject(sharedID).toString();
				sharedIDs.add(sharedIDValue);
			}
		}
		rs.close();
		stmt.close();
		return sharedIDs;
	}

	public Collection<DBO> getDBOs(Collection<String> sharedIDs) throws SQLException, NamingException {

		ArrayList<DBO> dbos = new ArrayList<DBO>();

		String sharedID = prop.getProperty("sql." + dboName + ".sharedID");

		PreparedStatement stmt = getStmt();
		ResultSet rs = stmt.executeQuery();

		int columnMax = stmt.getMetaData().getColumnCount();
		String[] columnLabel = new String[columnMax + 1];
		for (int columnN = 1; columnN <= columnMax; columnN++) {
			columnLabel[columnN] = stmt.getMetaData().getColumnLabel(columnN);
		}

		while (rs.next()) {
			if (sharedIDs.contains(rs.getString(sharedID))) {
				DBO dbo = new DBO();
				for (int columnN = 1; columnN <= columnMax; columnN++) {
					dbo.fields.put(columnLabel[columnN], rs.getString(columnLabel[columnN]));
				}
				dbos.add(dbo);
			}
		}
		rs.close();
		stmt.close();
		return dbos;
	}

	protected PreparedStatement getStmt() throws SQLException {

		String parameters[] = null;
		String parameterIndex[] = null;
		String sql;
		PreparedStatement stmt = null;

		parameters = getParameters();
		
		// TODO trows DBONameException
		// SQL Query with parameters
		if ((parameters != null) && (prop.getProperty("sql." + dboName + "." + parameters.length) != null)) {
			sql = prop.getProperty("sql." + dboName + "." + parameters.length);
			parameterIndex = prop.getProperty("sql." + dboName + "." + parameters.length + ".parameters").split(",");
			stmt = connection.prepareStatement(sql);
			for (int i = 0; i < parameterIndex.length; i++) {
				int index = Integer.parseInt(parameterIndex[i]) - 1;
				stmt.setString(i + 1, parameters[index]);
			}
		} else if (prop.getProperty("sql." + dboName) != null) { // SQL Query without parameters
			sql = prop.getProperty("sql." + dboName);
			stmt = connection.prepareStatement(sql);
		} else {
			logger.log(Level.SEVERE, "'" + dboName + "' DBO not implemented.");
			System.exit(-1);
		}
		return stmt;
	}

	public String[] getParameters() {

		String parameters[] = ((String) ConfManager.getContext().get("parameters")).split(",");
		
		return parameters;
	}

}
