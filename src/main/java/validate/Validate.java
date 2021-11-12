package validate;


public class Validate{
	
	public boolean FileTypeValidate(String extension) {
		if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png") 
				|| extension.equalsIgnoreCase("jpeg")) {
			return true;
		}
		return false;
	}
	
}
