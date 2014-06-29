/**
 * 
 */
package syncdbo;

import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import syncdbo.dao.DAO;
import syncdbo.dao.DBO;
import syncdbo.exporter.Exporter;
import syncdbo.finder.Finder;
import syncdbo.finder.Found;

/**
 * 
 * 
 */
public class SyncDBO {

	static Logger logger = Logger.getLogger(SyncDBO.class.getName());

	/**
	 * @param args
	 * @throws SQLException
	 * @throws NamingException
	 */
	public static void main(String[] args) throws SQLException, NamingException {

		ConfManager.setLogProperties("conf/log.properties");
		ConfManager.setContext(args);

		DAO localDAO = (DAO) ConfManager.context.get("localDAO");
		String localDAOName = (String) ConfManager.context.get("localDAOName");

		DAO extDAO = (DAO) ConfManager.context.get("extDAO");
		String extDAOName = (String) ConfManager.context.get("extDAOName");

		String dboName = (String) ConfManager.context.get("dboName");
		String finderName = (String) ConfManager.context.get("finderName");
		String exporterName = (String) ConfManager.context.get("exporterName");

		Collection<String> localSharedIDs = localDAO.getSharedIDs();
		logger.log(Level.INFO, localSharedIDs.size() + " " + dboName + " got from " + localDAOName);
		Collection<String> extSharedIDs = extDAO.getSharedIDs();
		logger.log(Level.INFO, extSharedIDs.size() + " " + dboName + " got from " + extDAOName);

		Finder finder = (Finder) ConfManager.context.get("finder");

		Found foundSharedIDs = finder.find(localSharedIDs, extSharedIDs);
		logger.log(Level.INFO, foundSharedIDs.getFoundSharedIDs().size() + " " + finderName + " " + dboName + " IDs found");

		Collection<DBO> dbos = foundSharedIDs.getDAO().getDBOs(foundSharedIDs.getFoundSharedIDs());
		logger.log(Level.INFO, dbos.size() + " " + finderName + " " + dboName + " DBOs found");

		Exporter exporter = (Exporter) ConfManager.context.get("exporter");
		int count = exporter.export(dbos);
		logger.log(Level.INFO, "\n" + "-------------------------------------------------------" + "\n" + count + " " + finderName + " "
				+ dboName + " found and exported to " + exporterName + "\n" + "-------------------------------------------------------");

		localDAO.getConnection().close();
		extDAO.getConnection().close();
	}
}
