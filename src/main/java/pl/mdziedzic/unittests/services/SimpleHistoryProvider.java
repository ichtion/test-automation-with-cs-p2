package pl.mdziedzic.unittests.services;

public class SimpleHistoryProvider implements HistoryProvider {
	
	private final HistoryCollector collector;

	public SimpleHistoryProvider(HistoryCollector collector) {
		this.collector = collector;
	}

	@Override
	public void saveHistory(HISTORY_EVENT event) {
	}

	@Override
	public HistoryCollector getHistory(Object object) {
		return null;
	}

	@Override
  public void collect(Object oldValue, Object newValue) {
		collector.collect(new HistoryChange(oldValue, newValue));
  }

}
