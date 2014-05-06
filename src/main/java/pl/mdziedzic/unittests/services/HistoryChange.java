package pl.mdziedzic.unittests.services;

public class HistoryChange {

	private final Object oldValue;
	private final Object newValue;

	public HistoryChange(Object oldValue, Object newValue) {
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public Object getOldValue() {
		return oldValue;
	}

	public Object getNewValue() {
		return newValue;
	}

	@Override
  public String toString() {
	  return "HistoryChange [oldValue=" + oldValue + ", newValue=" + newValue + "]";
  }

}
