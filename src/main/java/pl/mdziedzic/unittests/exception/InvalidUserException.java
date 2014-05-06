package pl.mdziedzic.unittests.exception;

public class InvalidUserException extends ModelException {
	
	private static final long serialVersionUID = 1L;

  public InvalidUserException(String message) {
	  super(message);
  }
  
}
