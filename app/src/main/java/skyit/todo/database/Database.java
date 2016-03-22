package skyit.todo.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Marc on 21.03.2016.
 */
public class Database extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "todos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESC = "desc";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_IMPORTANT = "important";
    private static final String COLUMN_DONE = "done";

    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text, "
            + COLUMN_DESC + " text, "
            + COLUMN_DATE + " text, "
            + COLUMN_IMPORTANT + " integer, "
            + COLUMN_DONE + " integer)";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DB", "Trying to create DB with Query =>" + DATABASE_CREATE);
        try {
            db.execSQL(DATABASE_CREATE);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Database.class.getName(),
                "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void truncateTable (SQLiteDatabase db) throws SQLException {
        db.execSQL("DELETE FROM TABLE " + TABLE_NAME);
    }
}
