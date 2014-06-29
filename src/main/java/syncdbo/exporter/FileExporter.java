package syncdbo.exporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import syncdbo.ConfManager;

public abstract class FileExporter {

	static Logger logger = Logger.getLogger(CSVExporter.class.getName());
	Properties propFileExporter;
	String filename;

	public FileExporter(String exporterName) {
		String dboName = (String) ConfManager.getContext().get("dboName");
		String finderName = (String) ConfManager.getContext().get("finderName");

		this.filename = dboName.toLowerCase() + "_" + finderName.toLowerCase() + "." + exporterName.toLowerCase();
		this.propFileExporter = (Properties) ConfManager.getContext().get("propSyncDBO");
	}

	/**
	 * Open a text file, returning a handler.
	 */
	public PrintWriter createFile() {
		PrintWriter escritor = null;
		File fichero = null;
		String archivoTxt = propFileExporter.getProperty("file_exporter.directory") + filename;

		try {
			fichero = new File(archivoTxt);
			if (fichero.exists()) {
				if (!fichero.delete()) {
					logger.log(Level.WARNING, "It was not possible to delete the file " + archivoTxt + " before creating a new one.");
				}
			}
			fichero.createNewFile();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		try {
			escritor = new PrintWriter(new FileWriter(fichero, true));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return escritor;
	}
}
