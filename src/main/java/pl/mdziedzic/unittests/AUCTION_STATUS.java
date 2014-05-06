package pl.mdziedzic.unittests;

public enum AUCTION_STATUS {

	NEW(1, "AUCTION_STATUS_NEW", "Nowa"),
	ACTIVE(2, "AUCTION_STATUS_ACTIVE", "Aktywna"),
	FINISHED(3, "AUCTION_STATUS_FINISHED", "Zakonczona"), 
	CANCELLED(4, "AUCTION_STATUS_CANCELLED", "Anulowana");
	
	private final int id;
	private final String name;
	private final String desc;

	AUCTION_STATUS(int id, String name, String desc) {
		this.id = id;
		this.name = name;
		this.desc = desc;
	}

	public int getId() {
  	return id;
  }

	public String getName2() {
  	return name;
  }

	public String getDesc() {
  	return desc;
  }
	
}
