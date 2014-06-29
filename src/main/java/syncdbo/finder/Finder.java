package syncdbo.finder;

import java.sql.SQLException;
import java.util.Collection;

public interface Finder {

	public Found find(Collection<String> localDBOs, Collection<String> extDBOs) throws SQLException;

}
