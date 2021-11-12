package validate;

public class ExistException extends Exception{
	public ExistException() {
		super("Hồ này đang có cá. Xóa thất bại");
	}
}
