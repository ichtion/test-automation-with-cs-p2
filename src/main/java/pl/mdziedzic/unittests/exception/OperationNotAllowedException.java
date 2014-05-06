package pl.mdziedzic.unittests.exception;

public class OperationNotAllowedException extends ModelException {

	private static final long serialVersionUID = 1L;
	
  public OperationNotAllowedException(String message) {
	  super(message);
  }

}
