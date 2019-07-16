package com.example.hiker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class TrailDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "trailDatabase.db";

    private static TrailDatabase mTraildb;

    public static TrailDatabase getInstance(Context context) {

        if (mTraildb == null) {
            mTraildb = new TrailDatabase(context);
        }
        return mTraildb;
    }

    //constructor
    private TrailDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final class TrailsTable {
        private static final String TABLE = "trails";
        private static final String COL_TRAIL_ID = "trail_id";
        private static final String COL_TRAIL_NAME = "trail_name";
        private static final String COL_FEATURES = "features";
        private static final String COL_ELEVATION = "elevation";
        private static final String COL_TRAIL_DISTANCE = "trail_distance";
    }

    private static final class TripsTable {
        private static final String TABLE = "trips";
        private static final String COL_TR_ID = "tr_id";
        private static final String COL_DATE = "date";
        private static final String COL_DESCRIPTION = "description";
    }

    private static final class ImagesTable {
        private static final String TABLE = "images";
        private static final String COL_TR_ID = "tr_id";
        private static final String COL_FILENAME = "filename";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TrailsTable.TABLE + " (" +
                TrailsTable.COL_TRAIL_ID + " integer primary key autoincrement, " +
                TrailsTable.COL_TRAIL_NAME + ", " +
                TrailsTable.COL_FEATURES + " integer, " +
                TrailsTable.COL_ELEVATION + " integer, " +
                TrailsTable.COL_TRAIL_DISTANCE + " integer)");

        sqLiteDatabase.execSQL("create table " + TripsTable.TABLE + " (" +
                TripsTable.COL_TR_ID + " integer, " +
                TripsTable.COL_DATE + " , " +
                TripsTable.COL_DESCRIPTION + ", " +
                "foreign key(" + TripsTable.COL_TR_ID + ") references " +
                TrailsTable.TABLE + "(" + TrailsTable.COL_TRAIL_ID + ") on delete cascade, " +
                "primary key (" + TripsTable.COL_TR_ID + ", " + TripsTable.COL_DATE + "))");

        sqLiteDatabase.execSQL("create table " + ImagesTable.TABLE + " (" +
                ImagesTable.COL_TR_ID + " integer, " +
                ImagesTable.COL_FILENAME + ", " +
                "foreign key (" + ImagesTable.COL_TR_ID + ") references " +
                TrailsTable.TABLE + "(" + TrailsTable.COL_TRAIL_ID + ") on delete cascade, " +
                "primary key (" + ImagesTable.COL_TR_ID + ", " + ImagesTable.COL_FILENAME +  "))");
        //BUILD_DATA: build data goes here. create the tables as objects and pass that object and the database instance to the add<element>OnBuild method.
        //i was getting an error for recursively creating the database when trying to add elements from this class using the normal add<element> methods.
        //this is because those methods need to instantiate a writable database within their scope, that writable database is already in this scope so calling them from here created that recursion.
        Log.d(TAG, "onCreate: db created");
        Trail trail = new Trail("Art Loeb", 29, 8257, 011);
        addTrailOnBuild(trail, sqLiteDatabase);
        trail = new Trail ("Blood Mountain", 6, 1545, 001);
        addTrailOnBuild(trail, sqLiteDatabase);
        trail = new Trail ("Bear Creek", 6, 1108, 011);
        addTrailOnBuild(trail, sqLiteDatabase);
        Trip trip = new Trip (1);
        trip.setDate("07/11/2011");
        trip.setDescription("Went with Corn and Shelby. made a wrong turn which added 10 miles to the journey.");
        addTripOnBuild(trip, sqLiteDatabase);
        trip = new Trip(2);
        trip.setDate("8/12/2018");
        trip.setDescription("Rained the whole time but was still beautiful");
        addTripOnBuild(trip, sqLiteDatabase);
        trip = new Trip(3);
        trip.setDate("04/10/2014");
        trip.setDescription("Went with brown and kumu. Almost ate jeffrey the turtle but he ran away");
        addTripOnBuild(trip, sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion>oldVersion) {
            sqLiteDatabase.execSQL("drop table if exists " + TrailsTable.TABLE);
            sqLiteDatabase.execSQL("drop table if exists " + TripsTable.TABLE);
            sqLiteDatabase.execSQL("drop table if exists " + ImagesTable.TABLE);
            onCreate(sqLiteDatabase);
            Log.d(TAG, "onUgrade: upgraded to version " + DATABASE_VERSION);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        super.onOpen(sqLiteDatabase);
        if (!sqLiteDatabase.isReadOnly()) {
            sqLiteDatabase.setForeignKeyConstraintsEnabled(true);
        }
        Log.d(TAG,"opened on version " + DATABASE_VERSION);
    }

    private void addTrailOnBuild(Trail trail, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(TrailsTable.COL_TRAIL_NAME, trail.getTrail_name());
        values.put(TrailsTable.COL_ELEVATION, trail.getElevation());
        values.put(TrailsTable.COL_FEATURES, trail.getFeatures());
        values.put(TrailsTable.COL_TRAIL_DISTANCE, trail.getTrail_distance());
        long trailId = db.insert(TrailsTable.TABLE, null, values);
        if (trailId == -1) {
            Log.println(Log.INFO, "error:", trail.getTrail_name());
        } else {
            trail.setTrail_id(trailId);
            Log.d(TAG, "addTrail: id = "+ trailId);
        }
    }

    public void addTrail(Trail trail) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TrailsTable.COL_TRAIL_NAME, trail.getTrail_name());
        values.put(TrailsTable.COL_ELEVATION, trail.getElevation());
        values.put(TrailsTable.COL_FEATURES, trail.getFeatures());
        values.put(TrailsTable.COL_TRAIL_DISTANCE, trail.getTrail_distance());
        long trailId = db.insert(TrailsTable.TABLE, null, values);
        if (trailId == -1) {
            Log.println(Log.INFO, "error:", trail.getTrail_name());
        } else {
            trail.setTrail_id(trailId);
            Log.d(TAG, "addTrail: id = "+ trailId);
        }
    }

    private void addTripOnBuild (Trip trip, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(TripsTable.COL_TR_ID, trip.getTr_id());
        values.put(TripsTable.COL_DATE, trip.getDate());
        values.put(TripsTable.COL_DESCRIPTION, trip.getDescription());
        db.insert(TripsTable.TABLE, null, values);
    }
    public void addTrip (Trip trip) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TripsTable.COL_TR_ID, trip.getTr_id());
        values.put(TripsTable.COL_DATE, trip.getDate());
        values.put(TripsTable.COL_DESCRIPTION, trip.getDescription());
        db.insert(TripsTable.TABLE, null, values);
    }

    public List<String> getTrails(int distanceUpper, int distanceLower){
        List<String> trailnames = new ArrayList<>();

        SQLiteDatabase readDb = this.getReadableDatabase();

        String sql = "select " + TrailsTable.COL_TRAIL_NAME + " from " + TrailsTable.TABLE + " where " + TrailsTable.COL_TRAIL_DISTANCE + " between " + distanceLower + " and " + distanceUpper;
        Cursor cursor = readDb.rawQuery(sql, new String[]{});
        if (cursor.moveToFirst()){
            do {
                trailnames.add(cursor.getString(0));
            }while (cursor.moveToNext());

        }
        cursor.close();
        return trailnames;
    }
}
