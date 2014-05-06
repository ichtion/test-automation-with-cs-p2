package pl.mdziedzic.unittests;

public enum AUCTION_TYPE {
	
	BID(1, "BID_AUCTION", "Aukcja z licytacja"),
	BUY_NOW(2, "BUY_NOW_AUCTION", "Aukcja kup teraz");
	
	private final int id;
	private final String name;
	private final String desc;

	AUCTION_TYPE(int id, String name, String desc) {
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
