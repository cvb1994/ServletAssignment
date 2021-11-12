package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {
	public String copyFile(InputStream  file, String fileExtension, String rootPath, String projectName) throws IOException{
		String directoryPath = rootPath.concat("\\public\\uploadedImage");
		File dir = new File(directoryPath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String fileCopied = timeStamp.concat("."+fileExtension);
		
		rootPath = rootPath.concat("\\public\\uploadedImage\\"+fileCopied);
		Path dest = Paths.get(rootPath); 
		
		Files.copy(file, dest, StandardCopyOption.REPLACE_EXISTING);
		String pathSave = projectName+"/public/uploadedImage/"+fileCopied;
		return pathSave;
	}
}
