package com.example.recupdonneesgps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClientDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "location.db";

    public final String SQL_CREATE = "CREATE TABLE Location(id INTEGER PRIMARY KEY AUTOINCREMENT, nomVoiture TEXT, long TEXT, lat TEXT)";
    public final String SQL_DELETE = "DROP TABLE IF EXISTS Location";

    public ClientDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(this.SQL_DELETE);
        this.onCreate(db);
    }
}
