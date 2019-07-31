package com.example.hiker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.ContextCompat;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hikerchat.ChatActivity;

import static android.app.Activity.RESULT_OK;

public class DetailsFragment extends Fragment {

    private Trail mTrail;
    private Station mStation;
    private Park mPark;
    private Trip mTrip = null;
    private TextView nameTextView, descriptionTextView, lengthTextView, elevationTextView, featuresTextView, parkTextView, stationTextView, tripTextView;
    private ImageView imageView;
    private Menu menu;
    private Button chatButton;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private final int REQUEST_WRITE_CODE = 0;
    Random rand = new Random();//used to randomly pick one of the pictures associated with this trail

    public static DetailsFragment newInstance(long trailId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putLong("trailId", trailId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // Get the ID from the intent that started DetailsActivity
        long trailId = 1;
        if (getArguments() != null) {
            trailId = getArguments().getLong("trailId");
        }

        mTrail = TrailDatabase.getInstance(getContext()).getTrailById(trailId);
        Log.i("Final trail name: ", mTrail.getTrail_name());
        mStation = TrailDatabase.getInstance(getContext()).getStationById(mTrail.getStationId());
        mPark = TrailDatabase.getInstance(getContext()).getParkById(mStation.getPark_id());
        mTrip = TrailDatabase.getInstance(getContext()).getTrip(mTrail.getTrail_id());
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){//builds options menu

        inflater.inflate(R.menu.appbar_details_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        nameTextView = view.findViewById(R.id.trailName);
        descriptionTextView = view.findViewById(R.id.trailDescription);
        lengthTextView = view.findViewById(R.id.trailLength);
        elevationTextView = view.findViewById(R.id.trailElevate);
        imageView = view.findViewById(R.id.imageView);
        parkTextView = view.findViewById(R.id.trailPark);
        stationTextView = view.findViewById(R.id.trailStation);
        tripTextView = view.findViewById(R.id.trailPlannedTrips);

        //Chat room button hookup
        chatButton = view.findViewById(R.id.chatButton);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ChatActivity.class);
                i.putExtra("parkname", mStation.getName());
                Log.i("Park name is ", mStation.getName());
                startActivity(i);
            }
        });

        nameTextView.setText(mTrail.getTrail_name());
        descriptionTextView.setText(mTrail.getDescription());
        lengthTextView.setText(String.valueOf(mTrail.getTrail_distance()));
        elevationTextView.setText(String.valueOf(mTrail.getElevation()));
        parkTextView.setText(mPark.getName());
        stationTextView.setText(mStation.getName() + "\nphone number: " + mStation.getPhone_number());
        if (mTrip != null){
            tripTextView.setText("departure date: " + mTrip.getDeparture() + "\nReturn Date : " + mTrip.getReturndate());//will have to be formatted better later. this return date is what we will use to compare with current time and trigger sns
        }
        List<Image> images = new ArrayList<Image>();
        images = TrailDatabase.getInstance(getContext()).getImageById(mTrail.getTrail_id());
        Log.d("images array", "onCreateView: " + images.toString());
        if (images.size()>0){
            int n = rand.nextInt(images.size());//random number between 0 and images.size()(exclusive)
            try { //i struggled for hours to find out how to read a file based off the filename. this hardcoded string part may change between emulators/ phone versions/ and apis.
                //imageView.setImageBitmap(BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/" + images.get(n).getFilename()));
                imageView.setImageURI(Uri.parse(images.get(n).getFilename()));

            } catch (Exception e) {
               e.printStackTrace();
            }

        }

        featuresTextView = view.findViewById(R.id.trailFeatures);
            String features = "";
            if (mTrail.hasWildlife()==1){
                features += "Wildlife\n";
            }
            if(mTrail.hasWaterfalls()==1){
                features += "Waterfalls\n";
            }
            if (mTrail.hasCreeks()==1){
                features += "Creeks\n";
            }
        featuresTextView.setText(features);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.addImage:
                if(hasFilePermissions()) {
                    Intent takePictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                break;
            case R.id.deleteTrail:
                TrailDatabase.getInstance(getContext()).deleteTrail(mTrail.getTrail_id());
                break;
            case R.id.updateTrip:
                Intent intent = new Intent(getContext(), Trip_Activity.class);
                intent.putExtra("TRAILID", mTrail.getTrail_id());
                startActivity(intent);
                break;
        }
        return false;
    }
    private boolean hasFilePermissions(){
        String writePermission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if(ContextCompat.checkSelfPermission(getContext(), writePermission)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(getActivity(), new String[]{ writePermission }, REQUEST_WRITE_CODE);

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap imageBitmap;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                imageView.setImageBitmap(imageBitmap);//sets the current image as the one you just selected

                String filePath = imageUri.getPath();
                //im not really sure what this cursor is querying
                Cursor cursor = getContext().getContentResolver().query(imageUri, null, null, null, null);
                if (cursor.moveToFirst())
                {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    imageUri = Uri.parse(cursor.getString(column_index));
                    Log.d("imageUri", "image URI " +imageUri);//this returned the filepath that finally gave me the location of the file. this is the
                    //harcoded part i use when loading the file from storage, we could probably save this as the filename in the Image object
                    filePath = imageUri.getLastPathSegment().toString();//this gets the image name
                }
                cursor.close();
                //Image image = new Image (mTrail.getTrail_id(), filePath);
                Image image = new Image (mTrail.getTrail_id(), imageUri.toString());
                TrailDatabase.getInstance(getContext()).addImage(image);
                Log.d("imageUri", "filePath " + filePath);
            }catch(IOException E){
                Log.d("IOException", "onActivityResult: " + E);
            }

        }
    }
    private OnFragmentInteractionListener mListener;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
