package syncdbo.dao;

import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class LocalDAO extends DAO {

	static Logger logger = Logger.getLogger(LocalDAO.class.getName());

	public LocalDAO(Properties prop) throws SQLException {
		super(prop);
		MysqlDataSource ds = new MysqlDataSource();
		ds.setUrl(prop.getProperty("connection.url"));
		ds.setUser(prop.getProperty("connection.user"));
		ds.setPassword(prop.getProperty("connection.password"));
		super.setConnection(ds.getConnection());
		super.setDatasource(ds);
	}

}
