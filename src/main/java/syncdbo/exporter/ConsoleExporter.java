package syncdbo.exporter;

import java.util.Collection;
import java.util.Iterator;

import syncdbo.dao.DBO;

public class ConsoleExporter implements Exporter {

	@Override
	public int export(Collection<DBO> dbos) {

		int count = 0;

		if (!dbos.isEmpty()) {
			Iterator<DBO> itrDbos = dbos.iterator();
			while (itrDbos.hasNext()) {
				System.out.println(itrDbos.next().getFields().toString());
				count ++;
			}
		}
		return count;
	}

}
