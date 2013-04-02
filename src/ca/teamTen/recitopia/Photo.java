package ca.teamTen.recitopia;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Stores a Photo and handles encoding/scaling/etc.
 * 
 */
public class Photo implements Serializable {
	
	/**
	 * Serializable version id
	 */
	private static final long serialVersionUID = -4043280211171951668L;
	private byte[] byteImage;

	/**
	 * @return the byteImage
	 */
	public byte[] getByteImage() {
		return byteImage;
	}

	/**
	 * @param byteImage the byteImage to set
	 */
	public void setByteImage(byte[] byteImage) {
		this.byteImage = byteImage;
	}
	
	/**
	 * a constructor that uses a Bitmap to construct a Photo Object
	 * @param Bitmap picture
	 */
	public Photo(Bitmap picture) {
		int size = picture.getWidth() * picture.getHeight();
		ByteArrayOutputStream out = new ByteArrayOutputStream(size);
		picture.compress(Bitmap.CompressFormat.PNG, 100, out);   
		this.byteImage = out.toByteArray();
	}
	
	/**
	 * @param a byte array that represents the photo
	 * @return a Photo object that represents the byte array
	 */
	public Photo(byte[] photoByteArray) {
		this.byteImage = photoByteArray;
	}
	
	/**
	 * 
	 * Converts the Photo Byte Array into a Bitmap
	 * @return a Bitmap representing this photo
	 * 
	 */
	public Bitmap getBitmap(){
		return BitmapFactory.decodeByteArray(this.byteImage , 0, this.byteImage.length);
	}	
}

