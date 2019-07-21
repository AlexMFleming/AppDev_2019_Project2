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

    private static final int DATABASE_VERSION = 7;
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
        private static final String COL_WATERFALLS = "waterfalls";
        private static final String COL_CREEKS = "creeks";
        private static final String COL_WILDLIFE = "wildlife";
        private static final String COL_ELEVATION = "elevation";
        private static final String COL_TRAIL_DISTANCE = "trail_distance";
        private static final String COL_DESCRIPTION = "description";
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
                TrailsTable.COL_TRAIL_DISTANCE + " integer," +
                TrailsTable.COL_ELEVATION + " integer, " +
                TrailsTable.COL_WATERFALLS + " integer, " +
                TrailsTable.COL_CREEKS + " integer, " +
                TrailsTable.COL_WILDLIFE + " integer, " +
                TrailsTable.COL_DESCRIPTION + " text)");

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
        Trail trail = new Trail("Art Loeb", 29, 8257, 0, 1, 1, "Traverses through many different biomes and summits the highest peak of the Appalachian Mountains and the 4th most isolated peak in the country, Mt. Mitchell");
        addTrailOnBuild(trail, sqLiteDatabase);
        trail = new Trail ("Blood Mountain", 6, 1545, 0, 0, 1, "Steep climb on the north Georgia AT with a great view at the top");
        addTrailOnBuild(trail, sqLiteDatabase);
        trail = new Trail ("Bear Creek", 6, 1108, 0, 1, 1, "a beautiful, mossy, fern-filled creek valley to the Gennett Poplar, the second largest living tree in Georgia.");
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
        values.put(TrailsTable.COL_WATERFALLS, trail.hasWaterfalls());
        values.put(TrailsTable.COL_CREEKS, trail.hasCreeks());
        values.put(TrailsTable.COL_WILDLIFE, trail.hasWildlife());
        values.put(TrailsTable.COL_DESCRIPTION, trail.getDescription());
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
        values.put(TrailsTable.COL_WATERFALLS, trail.hasWaterfalls());
        values.put(TrailsTable.COL_CREEKS, trail.hasCreeks());
        values.put(TrailsTable.COL_WILDLIFE, trail.hasWildlife());
        values.put(TrailsTable.COL_DESCRIPTION, trail.getDescription());
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

    public List<Trail> getTrails(int distance, int elevation, int waterfalls, int creeks, int wildlife) {//elements not entered by user recieve a -1 value to search for all cases. features should be a binary integer if not empty
        //elevation and distance can be -1, 0, 1, or 2. -1 being unset, the others corresponding to the selected radio button
        List<Trail> trails = new ArrayList<>();
        String sql;
        SQLiteDatabase readDb = getReadableDatabase();
        if(distance==-1 && elevation==-1 && !(wildlife==1||creeks==1||waterfalls==1)){
            sql = "select * from " + TrailsTable.TABLE + " where " + TrailsTable.COL_TRAIL_ID + ">-1";

        }else if (distance==-1 && elevation==-1){
            sql = "select * from " + TrailsTable.TABLE + " where " + getFeatureCase(waterfalls, creeks, wildlife);

        }else if (distance==-1 && !(wildlife==1||creeks==1||waterfalls==1)){
            sql = "select * from " + TrailsTable.TABLE + " where " + getTrailElevationCase(elevation);
        }else if (elevation==-1 && !(wildlife==1||creeks==1||waterfalls==1)) {
            sql = "select * from " + TrailsTable.TABLE + " where " + getTrailDistanceCase(distance);

        }else if (elevation==-1){
            sql = "select * from " + TrailsTable.TABLE + " where (" + getTrailDistanceCase(distance) + ") AND (" + getFeatureCase(waterfalls, creeks, wildlife) + ")";

        }else if (distance==-1){
            sql = "select * from " + TrailsTable.TABLE + " where (" +getTrailElevationCase(elevation) + ") AND (" + getFeatureCase(waterfalls, creeks, wildlife) + ")";

        }else if (!(wildlife==1||creeks==1||waterfalls==1)){
            sql = "select * from " + TrailsTable.TABLE + " where (" +getTrailElevationCase(elevation) + ") AND (" + getTrailDistanceCase(distance) + ")" ;
        }else {
            sql = "select * from " + TrailsTable.TABLE + " where (" +getTrailElevationCase(elevation) + ") AND (" + getTrailDistanceCase(distance) + ") AND (" + getFeatureCase(waterfalls, creeks, wildlife) + ")";
        }
        Log.d(TAG, "getTrails query: " + sql);
        Cursor cursor = readDb.rawQuery(sql, new String[]{});
        if (cursor.moveToFirst()) {
            do {
                trails.add(new Trail(cursor.getLong(0),cursor.getString(1), cursor.getInt(2), cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7)));
                Log.d(TAG, "cursorCount: " + cursor.getCount());
            } while (cursor.moveToNext());
        }
        cursor.close();
        return trails;


    }
    private String getTrailElevationCase(int n){
        String query = "";
        switch (n) {
            case (0):
                query = TrailsTable.COL_ELEVATION + " between 0 and 700";
                break;
            case (1):
                query = TrailsTable.COL_ELEVATION + " between 700 and 1500";
                break;
            case (2):
                query = TrailsTable.COL_ELEVATION + "> 1500";
                break;
        }
        return query;
    }
    private String getTrailDistanceCase(int n){
        String query = "";
        switch (n) {
            case (0):
                query = TrailsTable.COL_TRAIL_DISTANCE + " between 0 and 5";
                break;
            case (1):
                query = TrailsTable.COL_TRAIL_DISTANCE + " between 5 and 15";
                break;
            case (2):
                query = TrailsTable.COL_TRAIL_DISTANCE + " > 15";
                break;
        }
        return query;
    }
    private String getFeatureCase(int waterfalls, int creeks, int wildlife){
        String query="";
        if(waterfalls==1 && creeks==1 && wildlife==1){
            query = TrailsTable.COL_CREEKS + " = 1 AND " + TrailsTable.COL_WATERFALLS + " = 1 AND " + TrailsTable.COL_WILDLIFE + " = 1";
        }else if (waterfalls==1 && creeks==1){
            query = TrailsTable.COL_CREEKS + " = 1 AND " + TrailsTable.COL_WATERFALLS + " = 1";
        }else if (waterfalls==1 && wildlife==1){
            query = TrailsTable.COL_WATERFALLS + " = 1 AND " + TrailsTable.COL_WILDLIFE + " = 1";
        }else if (creeks==1 && wildlife==1){
            query = TrailsTable.COL_CREEKS + " = 1 AND " + TrailsTable.COL_WILDLIFE + " = 1";
        }else if (waterfalls==1){
            query = TrailsTable.COL_WATERFALLS + " = 1";
        }else if (creeks==1) {
            query = TrailsTable.COL_CREEKS + " = 1";
        }else if (wildlife==1){
            query = TrailsTable.COL_WILDLIFE + " = 1";
        }
        return query;
    }

    public List<Trip> getTrips(long id){//takes the trail id of the selected trail
        List<Trip> trips = new ArrayList<>();
        String sql;
        SQLiteDatabase readDb = this.getReadableDatabase();
        sql = "select * from " + TripsTable.TABLE + " where " + TripsTable.COL_TR_ID + " = " + id;
        Cursor cursor = readDb.rawQuery(sql, new String[]{});
        if (cursor.moveToFirst()) {
            do {
                trips.add(new Trip(cursor.getLong(0),cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return trips;

    }
//getTrails method for if we use ranges of distances instead of checkboxes
//    public List<String> getTrails(int distanceUpper, int distanceLower){
//        List<String> trailnames = new ArrayList<>();
//
//        SQLiteDatabase readDb = this.getReadableDatabase();
//
//        String sql = "select " + TrailsTable.COL_TRAIL_NAME + " from " + TrailsTable.TABLE + " where " + TrailsTable.COL_TRAIL_DISTANCE + " between " + distanceLower + " and " + distanceUpper;
//        Cursor cursor = readDb.rawQuery(sql, new String[]{});
//        if (cursor.moveToFirst()){
//            do {
//                trailnames.add(cursor.getString(0));
//            }while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        return trailnames;
//    }
}
