package pl.mdziedzic.unittests.services;

public interface HistoryProvider {
	
	void collect(Object oldValue, Object newValue);

	HistoryCollector getHistory(Object object);

	void saveHistory(HISTORY_EVENT event);

}
