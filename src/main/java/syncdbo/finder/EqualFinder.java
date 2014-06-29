package syncdbo.finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import syncdbo.ConfManager;
import syncdbo.dao.DAO;

public class EqualFinder implements Finder {

	@Override
	public Found find(Collection<String> localSharedIDs, Collection<String> extSharedIDs) {

		ArrayList<String> equalSharedIDs = new ArrayList<String>();

		Object ArrayExtSharedID[] = extSharedIDs.toArray();
		Object ArrayLocalSharedIDs[] = localSharedIDs.toArray();

		Arrays.sort(ArrayExtSharedID);
		Arrays.sort(ArrayLocalSharedIDs);

		for (Object sharedID : ArrayLocalSharedIDs) {
			if (!(sharedID.toString().equals("") || sharedID.toString().equals(" "))) {
				int id = Arrays.binarySearch(ArrayExtSharedID, sharedID);
				if (id > 0) {
					equalSharedIDs.add(sharedID.toString());
				}
			}
		}
		DAO localDAO = (DAO) ConfManager.getContext().get("localDAO");
		Found foundSharedIDs = new Found(localDAO, equalSharedIDs);
		return foundSharedIDs;
	}

}
