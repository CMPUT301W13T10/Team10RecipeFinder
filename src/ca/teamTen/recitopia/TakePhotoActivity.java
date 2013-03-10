package ca.teamTen.recitopia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class TakePhotoActivity extends Activity {

	private static final int CAMERA_PIC_REQUEST = 2500;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_photo);
		
		Button b = (Button)findViewById(R.id.Button01);
        b.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                 Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                 startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_take_photo, menu);
		return true;
	}

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
              Bitmap image = (Bitmap) data.getExtras().get("data");
              ImageView imageview = (ImageView) findViewById(R.id.ImageView01);
              imageview.setImageBitmap(image);
        }
    }
}
