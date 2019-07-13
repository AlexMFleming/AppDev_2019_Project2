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

    private static final int VERSION = 1;
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
        super(context, DATABASE_NAME, null, VERSION);
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
                TripsTable.COL_DATE + " int, " +
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
        Log.d(TAG, "onCreate: db created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TrailsTable.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + TripsTable.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + ImagesTable.TABLE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        super.onOpen(sqLiteDatabase);
        if (!sqLiteDatabase.isReadOnly()) {
            sqLiteDatabase.setForeignKeyConstraintsEnabled(true);
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
            Log.d(TAG, "addTrail: worked");
        }
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
