package skyit.todo.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import skyit.todo.HelperMethods;
import skyit.todo.database.DBAdapter;
import skyit.todo.model.ToDo;

/**
 * Created by Marc on 21.03.2016.
 */
public class ToDoCtrl {

    public void setTodos(ArrayList<ToDo> todos) {
        this.todos = todos;
    }

    private ArrayList<ToDo> todos;
    private DBAdapter db;
    private Cursor cursor;

    public ArrayList<ToDo> getTodos() {
        return todos;
    }

    public void deleteAll() {
        todos.clear();
    }

    public ToDoCtrl() {
        todos = new ArrayList<>();
    }

    public void initDB (Context c) throws SQLException {
        db = new DBAdapter(c);
        db.open();
        Log.i("ToDOCtrl","Opened...");
    }

    public void readDB (Context c, String mode, boolean showdone) {
        String name, desc, date;
        int rowid;
        boolean important, done;
        ToDo temp;
       deleteAll();

        switch (mode) {
            case "ALL": cursor  = db.fetchAllTodo(showdone); break;
            case "SORT_DATE": cursor  = db.querySortDate(showdone); break;
            case "SORT_IMPORTANCE_DATE": cursor  = db.querySortImportanceThenDate(showdone); break;
            //case "ONLY_NOT_DONE": cursor =  db.queryOnlyNotDone(); break;
            default: cursor  = db.fetchAllTodo(showdone); break;
        }

        if (cursor.moveToFirst()) {
            do {
                rowid = cursor.getInt(0);
                name = cursor.getString(1);
                desc = cursor.getString(2);
                date = cursor.getString(3);
                long fdate = Long.valueOf(date);
                important = HelperMethods.intToBool(cursor.getInt(4));
                done = HelperMethods.intToBool(cursor.getInt(5));
                temp = new ToDo(name,desc,new Date(fdate), important, done);

                temp.setRowid(rowid);
            //    Log.i(this.toString(),"Rowid added " + rowid);

                todos.add(temp);
            } while (cursor.moveToNext());
        }
    }
}
