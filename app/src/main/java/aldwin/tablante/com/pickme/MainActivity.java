package aldwin.tablante.com.pickme;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    ImageView imageView;
    private static final int PICK_IMAGE = 222;
    private Button btnCam;
    private Button btnShare;
    private ImageView imagePic;
    private ImageView igPic;
    //private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btnCam = (Button) findViewById(R.id.btnCam);
        // igPicCam = (ImageView) findViewById(R.id.igPicCam);
        btnShare = (Button) findViewById(R.id.btnShare);
        imagePic = (ImageView) findViewById(R.id.imagePic);
        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);

        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                imagePic.setVisibility(View.GONE);
                btnShare.setVisibility(View.VISIBLE);
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "Text I want to share.";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
            }
        });


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        return super.onCreateOptionsMenu(menu);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            imageView.setImageURI(selectedImage);
        }


    }
}




