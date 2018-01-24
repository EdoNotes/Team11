package Entities;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javafx.scene.image.Image;
/**
 * This class helps take handle of file that come from or into the server via socket
 * @author Tomer Arzuan
 *
 */

public class ImageConverter {
	
  	/**
  	 * this method converts InputStream object into array of bytes(byte[])
  	 * 
  	 * 
  	 * @param inStrm - InputStream to convert
  	 * @return  - array of bytes
  	 * @throws IOException 
  	 */
  	public static byte[] convertInputStreamToByteArray(InputStream inStrm) throws IOException {
  		
  		byte [] retByteArray=null;
  		byte[] buff = new byte[4096];
  		int bytesRead = 0;

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        
        while((bytesRead = inStrm.read(buff)) != -1) {							//read the entire stream
            bao.write(buff, 0, bytesRead);
         }

         retByteArray = bao.toByteArray();
  
  		return retByteArray;
  	}
  	
  	
  	
 	/**
  	 * converts array of bytes (byte[]) into a InputStream in order to enter it to the database as blob
  	 * 
  	 * 
  	 * @param byteArray - the byte[] to convert
  	 */
  	public static InputStream convertByteArrayToInputStream(byte [] byteArray) {
  		
  		InputStream retInputStream = new ByteArrayInputStream(byteArray);
  		
  		return retInputStream;
  	}
  	
  	
	/**
	 * converts File to an byte array
	 * 
	 * @param path - path to needed file
	 * @throws IOException 
	 */
	public static byte[] convertFileToByteArray(File file) throws IOException {
	
		
		byte [] byteArray;
		byteArray = new byte [(int)file.length()];
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		bis.read(byteArray,0,byteArray.length); 					// copied file into this.file
		
		return byteArray;
	}
	
	/**
	 * this method converts byteArray to an Image
	 * 
	 * @param byteArray - the byte array you want to convert
	 * @return - returns an image made out of the byte array
	 */
	public static Image convertByteArrayToImage(byte[] byteArray) {

		Image image = new Image(new ByteArrayInputStream(byteArray));
		
		return image;

	}
  	
  	
}
