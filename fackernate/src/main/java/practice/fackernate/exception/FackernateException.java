package practice.fackernate.exception;

public class FackernateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FackernateException(Throwable e) {
		super(e);
	}
	
	public FackernateException(String message) {
		super(message);
	}

}
