package ca.teamTen.recitopia;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


/**
 * Stores a Photo and handles encoding/scaling/etc.
 * 
 * not yet implemented.
 */
public class Photo {

	private Bitmap mImageBitmap;
	
	
	public Bitmap getmImageBitmap()
	{
	
		return mImageBitmap;
	}

	
	public void setmImageBitmap(Bitmap mImageBitmap)
	{
	
		this.mImageBitmap = mImageBitmap;
	}

	public Photo(Bitmap pic) {
		this.mImageBitmap = pic;
		
	}
	
	public Photo(byte[] photoByteArray) {
		this.mImageBitmap = BitmapFactory.decodeByteArray(photoByteArray , 0, photoByteArray.length);
	}
	/*
	public void compressPhoto() {
		this.mImageBitmap = Bitmap.createScaledBitmap(this.mImageBitmap, this.mImageBitmap.getWidth()/5, this.mImageBitmap.getHeight()/5, false);
	}
	*/
	public byte[] toByteArray() {

		int size = mImageBitmap.getWidth() * mImageBitmap.getHeight();
		Log.v("filesize", new Integer(size).toString());
		ByteArrayOutputStream out = new ByteArrayOutputStream(size);
		mImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);   
		return out.toByteArray();
	}
	
	
	
	
}
