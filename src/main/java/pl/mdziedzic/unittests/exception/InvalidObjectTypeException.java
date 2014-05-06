package pl.mdziedzic.unittests.exception;

public class InvalidObjectTypeException extends ModelException {
	
	private static final long serialVersionUID = 1L;

	public InvalidObjectTypeException(String message) {
	  super(message);
  }

}
