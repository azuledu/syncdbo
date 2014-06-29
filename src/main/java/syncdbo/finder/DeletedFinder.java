package syncdbo.finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import syncdbo.ConfManager;
import syncdbo.dao.DAO;

public class DeletedFinder implements Finder {

	@Override
	public Found find(Collection<String> localSharedIDs, Collection<String> extSharedIDs) {

		ArrayList<String> deletedSharedIDs = new ArrayList<String>();

		Object ArrayExtSharedID[] = extSharedIDs.toArray();
		Object ArrayLocalSharedIDs[] = localSharedIDs.toArray();

		Arrays.sort(ArrayExtSharedID);
		Arrays.sort(ArrayLocalSharedIDs);

		for (Object sharedID : ArrayLocalSharedIDs) {
			if (!(sharedID.toString().equals("") || sharedID.toString().equals(" "))) {
				int id = Arrays.binarySearch(ArrayExtSharedID, sharedID);
				if (id < 0) {
					deletedSharedIDs.add(sharedID.toString());
				}
			}
		}
		/*
		 * Iterator<DBO> itrLocalDBO = localDBOs.iterator();
		 * 
		 * while (itrLocalDBO.hasNext()) { DBO localDBO = itrLocalDBO.next();
		 * 
		 * if (!extDBOs.contains(localDBO)) { deletedDBOs.add(localDBO); } }
		 */

		DAO localDAO = (DAO) ConfManager.getContext().get("localDAO");
		Found foundSharedIDs = new Found(localDAO, deletedSharedIDs);
		return foundSharedIDs;
	}

}
