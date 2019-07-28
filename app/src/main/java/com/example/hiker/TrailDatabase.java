package com.example.hiker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class TrailDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 12;
    private static final String DATABASE_NAME = "trailDatabase.db";
    private static final PopulateDB databaseInstantiate = new PopulateDB();


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
        private static final String COL_STATION_ID = "station_id";
    }

    private static final class TripsTable {
        private static final String TABLE = "trips";
        private static final String COL_TR_ID = "tr_id";
        private static final String COL_DEPARTURE = "departure";
        private static final String COL_RETURN = "return";
    }

    private static final class ImagesTable {
        private static final String TABLE = "images";
        private static final String COL_TR_ID = "tr_id";
        private static final String COL_FILENAME = "filename";
    }

    private static final class ParkTable {
        private static final String TABLE = "park";
        private static final String COL_PARK_ID = "park_id";
        private static final String COL_NAME = "name";
        private static final String COL_STATE = "state";
        private static final String COL_CITY = "city";
    }

    private static final class StationTable {
        private static final String TABLE = "station";
        private static final String COL_STATION_ID = "station_id";
        private static final String COL_PARK_ID = "park";
        private static final String COL_NAME = "name";
        private static final String COL_PHONE_NUMBER = "phone_number";
    }

    private static final class EmergencyContactTable {
        private static final String TABLE = "emergency_contact";
        private static final String COL_NAME = "name";
        private static final String COL_PHONE_NUMBER = "phone_number";
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
                TrailsTable.COL_DESCRIPTION + " text," +
                TrailsTable.COL_STATION_ID + ", " +
                "foreign key (" + TrailsTable.COL_STATION_ID + ") references " +
                StationTable.TABLE + "(" + StationTable.COL_STATION_ID + "))");

        sqLiteDatabase.execSQL("create table " + TripsTable.TABLE + " (" +
                TripsTable.COL_TR_ID + " integer, " +
                TripsTable.COL_DEPARTURE + " , " +
                TripsTable.COL_RETURN + ", " +
                "foreign key(" + TripsTable.COL_TR_ID + ") references " +
                TrailsTable.TABLE + "(" + TrailsTable.COL_TRAIL_ID + ") on delete cascade, " +
                "primary key (" + TripsTable.COL_TR_ID + ", " + TripsTable.COL_DEPARTURE + "))");

        sqLiteDatabase.execSQL("create table " + ImagesTable.TABLE + " (" +
                ImagesTable.COL_TR_ID + " integer, " +
                ImagesTable.COL_FILENAME + ", " +
                "foreign key (" + ImagesTable.COL_TR_ID + ") references " +
                TrailsTable.TABLE + "(" + TrailsTable.COL_TRAIL_ID + ") on delete cascade, " +
                "primary key (" + ImagesTable.COL_TR_ID + ", " + ImagesTable.COL_FILENAME +  "))");

        sqLiteDatabase.execSQL("create table " + ParkTable.TABLE + " (" +
                ParkTable.COL_PARK_ID + " integer primary key autoincrement, " +
                ParkTable.COL_NAME + ", " +
                ParkTable.COL_STATE + ", " +
                ParkTable.COL_CITY + ")" );

        sqLiteDatabase.execSQL("create table " + StationTable.TABLE + " (" +
                StationTable.COL_STATION_ID + " integer primary key autoincrement," +
                StationTable.COL_PARK_ID + " integer, " +
                StationTable.COL_NAME + ", " +
                StationTable.COL_PHONE_NUMBER + ", " +
                "foreign key (" + StationTable.COL_PARK_ID + ") references " +
                ParkTable.TABLE + "(" + ParkTable.COL_PARK_ID + "))");

        sqLiteDatabase.execSQL("create table " + EmergencyContactTable.TABLE + " (" +
                EmergencyContactTable.COL_NAME + " primary key," +
                EmergencyContactTable.COL_PHONE_NUMBER + ")");

        //BUILD_DATA: since stations reference parks, and trails reference stations, they must build in this order.
        Log.d(TAG, "onCreate: db created");
        ArrayList<Park> parkArrayList = databaseInstantiate.createParks();
        for (int i = 0; i < parkArrayList.size(); i++) {
            addParkOnBuild(parkArrayList.get(i), sqLiteDatabase);
        }
        parkArrayList.clear();
        ArrayList<Station> stationArrayList = databaseInstantiate.createStations();
        for (int i = 0; i < stationArrayList.size(); i++) {
            addStationOnBuild(stationArrayList.get(i), sqLiteDatabase);
        }
        parkArrayList.clear();
        ArrayList<Trail> trailArrayList= databaseInstantiate.createTrails();
        //Adds trails to database from PopulateDB
        for (int i = 0; i < trailArrayList.size(); i++) {
            addTrailOnBuild(trailArrayList.get(i), sqLiteDatabase);
        }
        trailArrayList.clear();


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion>oldVersion) {
            sqLiteDatabase.execSQL("drop table if exists " + TrailsTable.TABLE);
            sqLiteDatabase.execSQL("drop table if exists " + TripsTable.TABLE);
            sqLiteDatabase.execSQL("drop table if exists " + ImagesTable.TABLE);
            sqLiteDatabase.execSQL("drop table if exists " + StationTable.TABLE);
            sqLiteDatabase.execSQL("drop table if exists " + ParkTable.TABLE);
            sqLiteDatabase.execSQL("drop table if exists " + EmergencyContactTable.TABLE);
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
        values.put(TrailsTable.COL_STATION_ID, trail.getStationId());
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
        values.put(TrailsTable.COL_STATION_ID, trail.getStationId());
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
        values.put(TripsTable.COL_DEPARTURE, trip.getDeparture());
        values.put(TripsTable.COL_RETURN, trip.getReturndate());
        db.insert(TripsTable.TABLE, null, values);
    }
    public void addTrip (Trip trip) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TripsTable.COL_TR_ID, trip.getTr_id());
        values.put(TripsTable.COL_DEPARTURE, trip.getDeparture());
        values.put(TripsTable.COL_RETURN, trip.getReturndate());
        db.insert(TripsTable.TABLE, null, values);
    }
    public void addImage (Image image) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ImagesTable.COL_TR_ID, image.getTr_id());
        values.put(ImagesTable.COL_FILENAME, image.getFilename());
        long x = db.insert(ImagesTable.TABLE, null, values);
        Log.d(TAG, "addImage: " + x);
    }

    public void addParkOnBuild (Park park, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(ParkTable.COL_NAME, park.getName());
        values.put(ParkTable.COL_STATE, park.getState());
        values.put(ParkTable.COL_CITY, park.getCity());
        long x = db.insert(ParkTable.TABLE, null, values);
        park.setPark_id(x);
        Log.d(TAG, "addPark: id = " + park.getPark_id() + " name = " + park.getName());
    }

    public void addStationOnBuild (Station station, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(StationTable.COL_PARK_ID, station.getPark_id());
        values.put(StationTable.COL_NAME, station.getName());
        values.put(StationTable.COL_PHONE_NUMBER, station.getPhone_number());
        long x = db.insert(StationTable.TABLE, null, values);
        Log.d(TAG, "addStation: id = " + x + " name = " + station.getName());
    }

    public void addEmergencyContact (EmergencyContact emergencyContact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        db.execSQL("drop table if exists " + EmergencyContactTable.TABLE);
        db.execSQL("create table " + EmergencyContactTable.TABLE + " (" +
                EmergencyContactTable.COL_NAME + " primary key," +
                EmergencyContactTable.COL_PHONE_NUMBER + ")");
        values.put(EmergencyContactTable.COL_NAME, emergencyContact.getName());
        values.put(EmergencyContactTable.COL_PHONE_NUMBER, emergencyContact.getPhoneNumber());
    }

    public Trail getTrailById(long id){
        String sql;
        Trail trail=null;
        SQLiteDatabase readDb = getReadableDatabase();
        sql = "select * from " + TrailsTable.TABLE + " where " + TrailsTable.COL_TRAIL_ID + " = " + id;
        Log.d(TAG, "getTrailById query: " + sql);
        Cursor cursor = readDb.rawQuery(sql, new String[]{});
        if (cursor.moveToFirst()) {
            do {
                trail = new Trail(cursor.getLong(0),cursor.getString(1), cursor.getInt(2), cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7), cursor.getLong(8));
                Log.d(TAG, "cursorCount: " + cursor.getCount());
            } while (cursor.moveToNext());
        }
        cursor.close();
        return trail;
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
                trails.add(new Trail(cursor.getLong(0),cursor.getString(1), cursor.getInt(2), cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7), cursor.getLong(8)));
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

    public void deleteTrail(long id) {
        String sql;
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TrailsTable.TABLE, TrailsTable.COL_TRAIL_ID + "=?", new String[]{id+""});
        sql = "select * from " + ImagesTable.TABLE + " where " + ImagesTable.COL_TR_ID + " = " + id;
        Cursor cursor = db.rawQuery(sql, new String[]{});
        
        if (cursor.moveToFirst()) {
            do {
                Log.d(TAG, "deleteTrail: " + cursor.getLong(0));
            } while (cursor.moveToNext());
        }else {
            Log.d(TAG, "deleteTrail: sucess");
        }
        cursor.close();
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

    public List<Image> getImageById(long id){
        List<Image> images = new ArrayList<>();
        String sql;
        SQLiteDatabase db = this.getReadableDatabase();
        sql = "select * from " + ImagesTable.TABLE + " where " + ImagesTable.COL_TR_ID + " = " + id;
        Cursor cursor = db.rawQuery(sql, new String[]{});
        if (cursor.moveToFirst()) {
            do {
                images.add(new Image(cursor.getLong(0),cursor.getString(1)));
                Log.d(TAG, "cursorCount: " + cursor.getCount());
            } while (cursor.moveToNext());
        }
        cursor.close();
        return images;
    }

    public List<Station> getStations (long parkId){
        ArrayList<Station> stations = new ArrayList<>();
        String sql;
        SQLiteDatabase db = this.getReadableDatabase();
        sql = "select * from " + StationTable.TABLE + " where " + StationTable.COL_PARK_ID + " = " + parkId;
        Cursor cursor = db.rawQuery(sql, new String[]{});
        if (cursor.moveToFirst()) {
            do {
                stations.add(new Station(cursor.getLong(0), cursor.getLong(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return stations;
    }

    public List<Park> getParks (){
        ArrayList<Park> parks = new ArrayList<>();
        String sql;
        SQLiteDatabase db = this.getReadableDatabase();
        sql = "select * from " + ParkTable.TABLE;
        Cursor cursor = db.rawQuery(sql, new String[]{});
        if (cursor.moveToFirst()) {
            do {
                parks.add(new Park(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return parks;
    }

    public Station getStationById(long stationId){
        String sql;
        Station station=null;
        SQLiteDatabase readDb = getReadableDatabase();
        sql = "select * from " + StationTable.TABLE + " where " + StationTable.COL_STATION_ID + " = " + stationId;
        Cursor cursor = readDb.rawQuery(sql, new String[]{});
        if (cursor.moveToFirst()) {
            do {
                station = new Station(cursor.getLong(0), cursor.getLong(1), cursor.getString(2), cursor.getString(3));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return station;
    }

    public Park getParkById(long parkId){
        String sql;
        Park park=null;
        SQLiteDatabase readDb = getReadableDatabase();
        sql = "select * from " + ParkTable.TABLE + " where " + ParkTable.COL_PARK_ID + " = " + parkId;
        Cursor cursor = readDb.rawQuery(sql, new String[]{});
        if (cursor.moveToFirst()) {
            do {
                park = new Park (cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return park;
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
