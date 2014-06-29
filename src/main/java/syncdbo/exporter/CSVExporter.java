package syncdbo.exporter;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

import syncdbo.dao.DBO;

public class CSVExporter extends FileExporter implements Exporter {

	public CSVExporter() {
		super("csv");
	}

	@Override
	public int export(Collection<DBO> dbos) {

		int count = 0;
		if (!dbos.isEmpty()) {
			PrintWriter csvFile = createFile();

			Iterator<DBO> itrDbos = dbos.iterator();

			// First line
			DBO dbo = itrDbos.next();
			csvFile.println(dbo.keysToString());
			csvFile.println(dbo.valuesToString());
			csvFile.flush();
			count ++;
			
			while (itrDbos.hasNext()) {
				csvFile.println(itrDbos.next().valuesToString());
				csvFile.flush();
				count ++;
			}
			csvFile.close();
		}
		return count;
	}

}
