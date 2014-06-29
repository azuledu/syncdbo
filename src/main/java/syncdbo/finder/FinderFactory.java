package syncdbo.finder;

public class FinderFactory {

	public static Finder getFinder(String db) {

		if (db.toLowerCase().equals("new")) {
			NewFinder newFinder = new NewFinder();
			return newFinder;
		} else if (db.toLowerCase().equals("deleted")) {
			DeletedFinder deletedFinder = new DeletedFinder();
			return deletedFinder;
		} else if (db.toLowerCase().equals("equal")) {
			EqualFinder equalFinder = new EqualFinder();
			return equalFinder;
		} else {
			return null;
		}
	}

}
