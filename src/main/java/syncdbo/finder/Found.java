package syncdbo.finder;

import java.util.Collection;

import syncdbo.dao.DAO;

public class Found {

	protected DAO dao;
	protected Collection<String> foundSharedIDs;

	public Found() {
	}

	public Found(DAO dao, Collection<String> foundSharedIDs) {
		this.dao = dao;
		this.foundSharedIDs = foundSharedIDs;
	}

	public DAO getDAO() {
		return dao;
	}

	public void setDAO(DAO dao) {
		this.dao = dao;
	}

	public Collection<String> getFoundSharedIDs() {
		return foundSharedIDs;
	}

	public void setFoundSharedIDs(Collection<String> foundSharedIDs) {
		this.foundSharedIDs = foundSharedIDs;
	}

}
