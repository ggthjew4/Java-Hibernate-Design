package practice.fackernate.exception;

public class ClassNotFoundRuntimeException extends FackernateException {

	public ClassNotFoundRuntimeException(Throwable e) {
		super(e);
		// TODO Auto-generated constructor stub
	}
	
	public ClassNotFoundRuntimeException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
