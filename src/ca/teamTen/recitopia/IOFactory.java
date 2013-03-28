package ca.teamTen.recitopia;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Factory that creates input/output streams used for
 * loading/saving (respectively).
 * 
 * If either method returns null, the load/save is not
 * executed.
 */
public interface IOFactory {
	InputStream getInputStream() throws IOException;
	OutputStream getOutputStream() throws IOException;

	/**
	 * Simple IOFactory implementation that always returns
	 * null.
	 */
	public static class NullIOFactory implements IOFactory {
		@Override
		public InputStream getInputStream() throws IOException {
			return null;
		}

		@Override
		public OutputStream getOutputStream() throws IOException {
			return null;
		}
	}
}