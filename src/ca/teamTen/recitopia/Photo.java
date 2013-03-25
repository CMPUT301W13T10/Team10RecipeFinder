package ca.teamTen.recitopia;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Stores a Photo and handles encoding/scaling/etc.
 * 
 * TODO Saving this class
 */
public class Photo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4043280211171951668L;
	private byte[] byteImage;

	/**
	 * Overriding the writeObject method
	 */
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.write(this.byteImage);
	}

	/**
	 * 
	 * @return
	 */
	private void readObject(ObjectInputStream in) throws IOException,ClassNotFoundException{
		this.byteImage = (byte[])in.readObject();
	}

	/**
	 * @param a constructor that uses a Bitmap to construct a Photo Object
	 */
	public Photo(Bitmap picture) {
		
		int size = picture.getWidth() * picture.getHeight();
		ByteArrayOutputStream out = new ByteArrayOutputStream(size);
		picture.compress(Bitmap.CompressFormat.PNG, 100, out);   
		this.byteImage = out.toByteArray();
	}
	
	public Bitmap getBitmap(){
		return BitmapFactory.decodeByteArray(this.byteImage , 0, this.byteImage.length);

	}
	/**
	 * @param a byte array that represents the photo
	 * @return a Photo object that represents the byte array
	 */
	public Photo(byte[] photoByteArray) {
		this.byteImage = photoByteArray;
	}





}

