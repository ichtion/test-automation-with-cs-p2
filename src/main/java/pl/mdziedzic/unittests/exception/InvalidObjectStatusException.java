package pl.mdziedzic.unittests.exception;

public class InvalidObjectStatusException extends ModelException {

	private static final long serialVersionUID = 1L;

  public InvalidObjectStatusException(String message) {
	  super(message);
  }

}
