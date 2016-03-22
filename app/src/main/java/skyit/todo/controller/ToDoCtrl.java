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

    public ArrayList<ToDo> getNotDoneTodos() {
        ArrayList notdone = new ArrayList();

        for (ToDo current : todos) {
            if (!current.getDone()) notdone.add(current);
        }

        return notdone;
    }

    public void addToDo (ToDo todo) {
        long rowid;
        ToDo tosave = new ToDo(todo.getName(),todo.getDesc(), todo.getDate(), todo.getImportant(),todo.getDone());

        rowid = db.createToDo(tosave);
        tosave.setRowid(rowid);
        Log.i("ToDoCtrl", "Created ToDo with rowId =" + rowid);
        todos.add(tosave);
    }

    public void addToDo (String name, String desc, Date date, Boolean important) {
        ToDo temp = new ToDo();
        long rowid;

        temp.setName(name);
        temp.setDesc(desc);
        temp.setDate(date);
        temp.setImportant(important);
        temp.setDone(false);

        rowid = db.createToDo(temp);
        temp.setRowid(rowid);
        Log.i("ToDoCtrl","Created ToDo with rowId =" + rowid);
        todos.add(temp);
    }

    public void deleteAll() {
        todos.clear();
    }

    public ToDo getToDo (int i) {
        return todos.get(i);
    }

    public ToDoCtrl() {
        todos = new ArrayList<>();
    }

    public ToDoCtrl(ArrayList<ToDo> todos) {
        this.todos = todos;
    }

    public void initDB (Context c) throws SQLException {
        db = new DBAdapter(c);
        db.open();
        Log.i("ToDOCtrl","Opened...");
    }

    public void writeDB (Context c) throws SQLException {
        db.truncateTable();

        for (ToDo current : todos) {
            long msec = current.getDate().getTime();
            String s_msec = Long.toString(msec);
            db.createToDo(current.getName(), current.getDesc(), s_msec,current.getImportant(), current.getDone());
        }
    }

    public void readDB (Context c) {
        String name, desc, date;
        boolean important, done;
        ToDo temp;

       cursor  = db.fetchAllTodo();
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(1);
                desc = cursor.getString(2);
                date = cursor.getString(3);
                long fdate = Long.valueOf(date);
                important = HelperMethods.intToBool(cursor.getInt(4));
                done = HelperMethods.intToBool(cursor.getInt(5));
                temp = new ToDo(name,desc,new Date(fdate), important, done);

                todos.add(temp);
            } while (cursor.moveToNext());
        }
    }
}
