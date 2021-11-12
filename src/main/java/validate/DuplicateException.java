package validate;

public class DuplicateException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateException(){
		super("Tên đã tồn tại");
	}
}
