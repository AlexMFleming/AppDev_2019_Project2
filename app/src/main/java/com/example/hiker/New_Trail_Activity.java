package com.example.hiker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class New_Trail_Activity extends AppCompatActivity {

    private FrameLayout setPictureLayout;
    private ImageView generatedThumbnail;
    private TextView frameLayoutText;
    private TrailDatabase TrailDb;
    private final int REQUEST_IMAGE_CAPTURE = 1;

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
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
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
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            frameLayoutText.setVisibility(View.INVISIBLE);
            generatedThumbnail.setImageBitmap(imageBitmap);
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

        Trail trail = new Trail(trailName, distance, elevation, waterfalls, creek, wildlife, trailDesc);
        TrailDb.addTrail(trail);
        Toast.makeText(this, " " + trail.getTrail_name() +" added", Toast.LENGTH_LONG).show();
    }
}
