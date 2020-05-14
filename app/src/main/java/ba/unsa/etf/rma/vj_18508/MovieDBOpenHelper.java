package ba.unsa.etf.rma.vj_18508;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDBOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "RMADataBase.db";
    public static final int DATABASE_VERSION = 1;

    public MovieDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public MovieDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String MOVIE_TABLE = "movies";
    public static final String MOVIE_ID = "id";
    public static final String MOVIE_INTERNAL_ID = "internalId";
    public static final String MOVIE_TITLE = "title";
    public static final String MOVIE_GENRE = "genre";
    public static final String MOVIE_HOMEPAGE = "homepage";
    public static final String MOVIE_POSTERPATH = "posterPath";
    public static final String MOVIE_RELEASEDATE = "releaseDate";
    public static final String MOVIE_OVERVIEW = "overview";


    private static final String MOVIE_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + MOVIE_TABLE + " ("
                    + MOVIE_INTERNAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + MOVIE_ID + " INTEGER UNIQUE, "
                    + MOVIE_TITLE + " TEXT NOT NULL, "
                    + MOVIE_GENRE + " TEXT, "
                    + MOVIE_HOMEPAGE + " TEXT, "
                    + MOVIE_POSTERPATH + " TEXT, "
                    + MOVIE_RELEASEDATE + " TEXT, "
                    + MOVIE_OVERVIEW + " TEXT);";

    private static final String MOVIE_DROP = "DROP TABLE IF EXISTS " + MOVIE_TABLE;


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(MOVIE_TABLE_CREATE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL(MOVIE_DROP);
        onCreate(db);

    }



}
