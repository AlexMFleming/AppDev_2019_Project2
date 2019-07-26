package com.example.hiker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class New_Trail_Activity extends AppCompatActivity {

    private FrameLayout setPictureLayout;
    private ImageView generatedThumbnail;
    private TextView frameLayoutText;
    private TrailDatabase TrailDb;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    String filePath;
    private final int REQUEST_WRITE_CODE = 0;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trail);

        generatedThumbnail = findViewById(R.id.camera_frame);
        frameLayoutText = findViewById(R.id.text_frame);
        //For creating thumbnail
        setPictureLayout = findViewById(R.id.frameLayout);
        setPictureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        TrailDb = TrailDatabase.getInstance(getApplicationContext());//get database instance for every new activity, we dont have to pass it through intent
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        if (hasFilePermissions()) {
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    //SY - This solution will be wonky when trying to set a new thumnail repeatedly.
    //Needs a better solution.
    public void setGeneratedThumbnail(ImageView thumbnail) {
        ViewGroup frameLayout = (ViewGroup) findViewById(R.id.frameLayout);
        View imageIcon = frameLayout.findViewById(R.id.camera_frame);
        frameLayout.removeView(imageIcon);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap imageBitmap;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                frameLayoutText.setVisibility(View.INVISIBLE);
                generatedThumbnail.setImageBitmap(imageBitmap);

                filePath = imageUri.getPath();

                Cursor cursor = this.getContentResolver().query(imageUri, null, null, null, null);
                if (cursor.moveToFirst())
                {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Instead of "MediaStore.Images.Media.DATA" can be used "_data"
                    imageUri = Uri.parse(cursor.getString(column_index));
                    Log.d("imageUri", "image URI " +imageUri);
                    filePath = imageUri.getLastPathSegment().toString();
                }
                cursor.close();
                Log.d("imageUri", "filePath " + filePath);
            }catch(IOException E){
                Log.d("IOException", "onActivityResult: " + E);
            }

        }
    }
    private boolean hasFilePermissions(){
        String writePermission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if(ContextCompat.checkSelfPermission(this, writePermission)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{ writePermission }, REQUEST_WRITE_CODE);

        }return true;
    }

    private void showPermissionRationaleDialog() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int [] grantResults) {
        switch(requestCode) {
            case REQUEST_WRITE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {// Permission granted!}else{// Permission denied!}return;
                } else {

                }
                return;
            }
        }
    }
    public void createTrailClick(View view){
        EditText text = (EditText)findViewById(R.id.newTrail_name_entry);
        String trailName = text.getText().toString();

        text = (EditText)findViewById(R.id.newTrail_elevation_entry);
        int elevation = Integer.parseInt(text.getText().toString());

        text = (EditText)findViewById(R.id.newTrail_length_entry);
        int distance = Integer.parseInt(text.getText().toString());

        EditText traildesc = findViewById(R.id.newTrail_description);
        String trailDesc = traildesc.getText().toString();


        //store the 3 feature elements in one 3 digit binary number. I thought this would be an efficient way to store it but it might end up being
        //more of a hassle than its worth.
        //orientation: (waterfalls, creeks, wildlife)
        CheckBox checkBox = findViewById(R.id.checkBox);
        CheckBox checkBox1 = findViewById(R.id.checkBox2);
        CheckBox checkBox2 = findViewById(R.id.checkBox3);
        int waterfalls = 0;
        int creek = 0;
        int wildlife = 0;
        if (checkBox.isChecked()){
            waterfalls =1;
        }
        if (checkBox1.isChecked()){
            creek=1;
        }
        if (checkBox2.isChecked()){
            wildlife = 1;
        }
        long stationid = -1; //placeholder until we get station selection working
        Trail trail = new Trail(trailName, distance, elevation, waterfalls, creek, wildlife, trailDesc, stationid);
        TrailDb.addTrail(trail);
        Log.d("filepath", "createTrailClick: " + filePath);
        if (filePath!=null){
            Image image = new Image(trail.getTrail_id(),filePath);
            TrailDb.addImage(image);
        }
        Toast.makeText(this, " " + trail.getTrail_name() +" added", Toast.LENGTH_LONG).show();
    }
}
