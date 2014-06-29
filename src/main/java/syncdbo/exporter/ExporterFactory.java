package syncdbo.exporter;

public class ExporterFactory {

	public static Exporter getExporter(String exporter) {

		if (exporter.toLowerCase().equals("console")) {
			ConsoleExporter consoleExporter = new ConsoleExporter();
			return consoleExporter;
		} else if (exporter.toLowerCase().equals("csv")) {
			CSVExporter csvExporter = new CSVExporter();
			return csvExporter;
		} else if (exporter.toLowerCase().equals("db")) {
			DBExporter dbExporter = new DBExporter();
			return dbExporter;
		} else {
			return null;
		}
	}

}
