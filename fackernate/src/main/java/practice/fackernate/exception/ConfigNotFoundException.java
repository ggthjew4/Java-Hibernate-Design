package practice.fackernate.exception;

/**
 * throw when class can't find the mapping config
 * @author JohnFang21
 *
 */
public class ConfigNotFoundException extends FackernateException {

	public ConfigNotFoundException(Throwable e) {
		super(e);
		// TODO Auto-generated constructor stub
	}
	public ConfigNotFoundException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
