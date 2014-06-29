package syncdbo.dao;

import java.sql.SQLException;
import java.util.Properties;

import syncdbo.ConfManager;

public class DAOFactory {

	public static DAO getDbDAO(String db) throws SQLException {

		Properties propDB = ConfManager.setProperties("conf/db/" + db + ".properties");

		if (db.toLowerCase().equals("localdb")) {
			DAO localDAO = new LocalDAO(propDB);
			return localDAO;
		} else if (db.toLowerCase().equals("extdb")) {
			DAO extDAO = new ExtDAO(propDB);
			return extDAO;
		} else {
			return null;
		}
	}

}
