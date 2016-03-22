package skyit.todo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import skyit.todo.HelperMethods;
import skyit.todo.model.ToDo;

/**
 * Created by Marc on 21.03.2016.
 */
public class DBAdapter {


    private static final String TABLE_NAME = "todos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESC = "desc";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_IMPORTANT = "important";
    private static final String COLUMN_DONE = "done";

    private Context _c;
    private SQLiteDatabase _db;
    private Database _helper;

    public DBAdapter (Context c) {
        this._c = c;
    }

    public DBAdapter open () throws SQLException {
        _helper = new Database(_c);
        _db = _helper.getWritableDatabase();
        return this;
    }

    public void close() {
        _helper.close();
    }

    public long createToDo (String name, String desc, String date, Boolean important, Boolean done) {
        ContentValues initValues = createContentValues (name, desc, date, important, done);
        return _db.insert(TABLE_NAME, null, initValues);
    }

    public long createToDo (ToDo t) {
        ContentValues initValues = createContentValues (t.getName(), t.getDesc(), HelperMethods.dateToLongString(t.getDate()), t.getImportant(), t.getDone());
        return _db.insert(TABLE_NAME, null, initValues);
    }

    public Cursor querySortDate(boolean showdone) {
        String s = null;
        if (!showdone) s = "done = 0";
        return _db.query(TABLE_NAME, null, s, null, null, null, "date ASC");
    }

    public Cursor querySortImportanceThenDate(boolean showdone) {
        String s = null;
        if (!showdone) s = "done = 0";
        return _db.query(TABLE_NAME, null, s, null, null, null, "important DESC, date ASC");
    }

    public Cursor queryOnlyNotDone() {
        return _db.query(TABLE_NAME, null, "done = 0", null, null, null, "date ASC");
    }

    public boolean updateToDo(long rowId, String name, String desc, String date, Boolean important, Boolean done) {
        ContentValues updateValues = createContentValues (name, desc, date, important, done);
        return _db.update(TABLE_NAME, updateValues, COLUMN_ID + "=" + rowId, null) >0;
    }

    private ContentValues createContentValues (String name, String desc, String date, Boolean important, Boolean done) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESC, desc);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_IMPORTANT, HelperMethods.boolToInt(important));
        values.put(COLUMN_DONE, HelperMethods.boolToInt(done));
        return values;
    }

    public boolean deleteTool (long rowId) {
        return _db.delete(TABLE_NAME, COLUMN_ID + "=" + rowId, null) >0;
    }

    public Cursor fetchAllTodo (boolean showdone) throws SQLException {
        return _db.query(TABLE_NAME, new String[] {COLUMN_ID, COLUMN_NAME, COLUMN_DESC, COLUMN_DATE, COLUMN_IMPORTANT, COLUMN_DONE}, null,null,null,null,null);
    }

    public Cursor fetchTodo (long rowId) throws SQLException {
        Cursor cur = _db.query(true,TABLE_NAME, new String[] {COLUMN_ID, COLUMN_NAME, COLUMN_DESC, COLUMN_DATE, COLUMN_IMPORTANT, COLUMN_DONE}, COLUMN_ID + "=" + rowId, null,null,null,null,null);
        if (cur != null) {
            cur.moveToFirst();
        }
        return cur;
    }

    public void truncateTable() throws SQLException {
        _db.delete(TABLE_NAME,"",null);
    }


}
