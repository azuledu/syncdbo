package syncdbo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import syncdbo.dao.DAO;
import syncdbo.dao.DAOFactory;
import syncdbo.exporter.ExporterFactory;
import syncdbo.finder.FinderFactory;

public class ConfManager {

	static Logger logger = Logger.getLogger(ConfManager.class.getName());
	static HashMap<String, Object> context = new HashMap<String, Object>();

	public static HashMap<String, Object> getContext() {
		return context;
	}

	protected static void setContext(String[] args) throws SQLException {

		if (args.length < 5 || args.length > 6) {
			printHelp();
			System.exit(1);
		}

		context.put("propSyncDBO", setProperties("conf/syncdbo.properties"));

		context.put("dboName", args[2]);

		DAO localDAO = DAOFactory.getDbDAO(args[0]);
		if (localDAO == null) {
			logger.log(Level.SEVERE, "'" + args[0] + "' DAO not implemented.");
			System.exit(-1);
		} else {
			context.put("localDAOName", args[0]);
			context.put("localDAO", localDAO);
			logger.log(Level.INFO, "Connected with " + args[0]);
		}

		DAO extDAO = DAOFactory.getDbDAO(args[1]);
		if ( extDAO == null) {
			logger.log(Level.SEVERE, "'" + args[1] + "' DAO not implemented.");
			System.exit(-1);
		} else {
			context.put("extDAOName", args[1]);
			context.put("extDAO", extDAO);
			logger.log(Level.INFO, "Connected with " + args[1]);
		}

		if (FinderFactory.getFinder(args[3]) == null) {
			logger.log(Level.SEVERE, "'" + args[3] + "' Finder not implemented.");
			System.exit(-1);
		} else {
			context.put("finderName", args[3]);
			context.put("finder", FinderFactory.getFinder(args[3]));
		}

		if (ExporterFactory.getExporter(args[4]) == null) {
			logger.log(Level.SEVERE, "'" + args[4] + "' Exporter not implemented.");
			System.exit(-1);
		} else {
			context.put("exporterName", args[4]);
			context.put("exporter", ExporterFactory.getExporter(args[4]));
		}

		if (args.length == 6)
			context.put("parameters", args[5]);
	}

	private static void printHelp() {

		System.out.println("usage: syncdbo.jar LocalDB ExtDB DBO Finder Exporter [Parameters]");
		System.out.println();
		System.out.println("where: ");
		System.out.println("LocalDB    - Local Database with read/write permissions.");
		System.out.println("ExtDB 	   - External Database with read permissions.");
		System.out.println("DBO 	   - Database Object. There should be a dbo.properties file with the sql queries.");
		System.out.println("Finder 	   - Diff tool to find differences between Database Objects.");
		System.out.println("Exporter   - Format to export found Database Objects.");
		System.out.println("Parameters - Coma separated sql parameters.");
		// TODO: Show available LocalDBs, ExtDBs, DBOs, Finders and Exporters.
	}

	protected static void setLogProperties(String fileName) {

		InputStream file = null;
		// in = Syncdb.class.getResourceAsStream(fileName); // files in jar
		try {
			file = new FileInputStream(fileName);
			LogManager.getLogManager().readConfiguration(file);
			file.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error reading/closing file '" + fileName + "'");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static Properties setProperties(String fileName) {

		InputStream file = null;
		Properties prop = new Properties();

		// in = Syncdb.class.getResourceAsStream(fileName); // files in jar
		try {
			file = new FileInputStream(fileName);
			prop.load(file);
			file.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error reading/closing file '" + fileName + "'");
			e.printStackTrace();
			System.exit(-1);
		}
		return prop;
	}
}
