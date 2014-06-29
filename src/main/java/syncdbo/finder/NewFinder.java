package syncdbo.finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
//import java.util.Iterator;



import syncdbo.ConfManager;
import syncdbo.dao.DAO;

public class NewFinder implements Finder {

	@Override
	public Found find(Collection<String> localSharedIDs, Collection<String> extSharedIDs) {

		ArrayList<String> newSharedIDs = new ArrayList<String>();

		Object ArrayExtSharedID[] = extSharedIDs.toArray();
		Object ArrayLocalSharedIDs[] = localSharedIDs.toArray();

		Arrays.sort(ArrayExtSharedID);
		Arrays.sort(ArrayLocalSharedIDs);

		for (Object sharedID : ArrayExtSharedID) {
			if (!(sharedID.toString().equals("") || sharedID.toString().equals(" "))) {
				int id = Arrays.binarySearch(ArrayLocalSharedIDs, sharedID);
				if (id < 0) {
					newSharedIDs.add(sharedID.toString());
				}
			}
		}

		/*
		 * Iterator<String> itrExtSharedIDs = extSharedIDs.iterator(); while (itrExtSharedIDs.hasNext()) { String
		 * extSharedID = itrExtSharedIDs.next(); if (!(extSharedID.equals("") || extSharedID.equals(" "))) {
		 * if(!localSharedIDs.contains(extSharedID)) { newSharedIDs.add(extSharedID); } } }
		 */

		DAO extDAO = (DAO) ConfManager.getContext().get("extDAO");
		Found foundSharedIDs = new Found(extDAO, newSharedIDs);
		return foundSharedIDs;
	}

}
