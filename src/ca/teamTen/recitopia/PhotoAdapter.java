package ca.teamTen.recitopia;

import ca.teamTen.recitopia.Photo;
import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import android.util.Base64;

/**
 * Gson.TypeAdapter for reading/writing Photo instances as
 * base64 strings.
 */
public class PhotoAdapter extends TypeAdapter<Photo> {

	/**
	 * Turn a base64 string into a Photo
	 */
	@Override
	public Photo read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return null;
		}
		byte[] photoByteArray = Base64.decode(reader.nextString(), Base64.URL_SAFE);
		return new Photo(photoByteArray);
	}

	/**
	 * Turn a Photo into a base64 string
	 */
	@Override
	public void write(JsonWriter writer, Photo photo) throws IOException {
		if (photo == null) {
			writer.nullValue();
			return;
		}
		String base64Photo = Base64.encodeToString(photo.getByteImage(), Base64.URL_SAFE);
		writer.value(base64Photo);
	}

}
