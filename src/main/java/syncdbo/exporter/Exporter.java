/**
 * 
 */
package syncdbo.exporter;

import java.util.Collection;

import syncdbo.dao.DBO;

/**
 * 
 *
 */
public interface Exporter {

	public int export(Collection<DBO> dbos);

}