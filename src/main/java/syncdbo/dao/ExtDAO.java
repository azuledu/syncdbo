package syncdbo.dao;

import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class ExtDAO extends DAO {

	static Logger logger = Logger.getLogger(ExtDAO.class.getName());

	public ExtDAO(Properties propDB) throws SQLException {
		super(propDB);	
		MysqlDataSource ds = new MysqlDataSource();
		ds.setURL(propDB.getProperty("connection.url"));
		ds.setUser(propDB.getProperty("connection.user"));
		ds.setPassword(propDB.getProperty("connection.password"));
		super.setConnection(ds.getConnection());
		super.setDatasource(ds);
	}

}
