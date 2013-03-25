package ca.teamTen.recitopia;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


/**
 * Stores a Photo and handles encoding/scaling/etc.
 * 
 * 
 */
public class Photo {

	private Bitmap bitmap;
	
	
	public Bitmap getImageBitmap()
	{
	
		return bitmap;
	}

	
	public void setImageBitmap(Bitmap bitmap)
	{
	
		this.bitmap = bitmap;
	}

	/**
	 * @param a constructor that uses a Bitmap to construct a Photo Object
	 */
	public Photo(Bitmap picture) {
		this.bitmap = picture;
		
	}
	/**
	 * @param a byte array that represents the photo
	 * @return a Photo object that represents the byte array
	 */
	public Photo(byte[] photoByteArray) {
		this.bitmap = BitmapFactory.decodeByteArray(photoByteArray , 0, photoByteArray.length);
	}
	
	/**
	 * Called to turn a Photo object into byte arrays so that they can be stored on Elastic Search
	 * @return a byte array representing a Photo
	 */
	public byte[] toByteArray() {

		int size = bitmap.getWidth() * bitmap.getHeight();
		ByteArrayOutputStream out = new ByteArrayOutputStream(size);
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);   
		return out.toByteArray();
	}
	
	
	
	
}
