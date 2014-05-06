package pl.mdziedzic.unittests.services;

import java.util.HashSet;
import java.util.Set;

public class HistoryCollector {

	private Set<HistoryChange> changes = new HashSet<HistoryChange>();

	public void collect(HistoryChange change) {
		changes.add(change);
	}

}
