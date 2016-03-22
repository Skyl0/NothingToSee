package skyit.todo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import skyit.todo.controller.ToDoCtrl;
import skyit.todo.database.DBAdapter;
import skyit.todo.model.ToDo;

public class AddEditToDo extends AppCompatActivity {

    DBAdapter db;
    long currentrowid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_to_do);

        Intent i = getIntent();
        currentrowid = i.getLongExtra("rowid", -1L);

        Log.i("AddEdit", "RowId received " + currentrowid);
        db = new DBAdapter(this);

        if (currentrowid != -1L) {
            final EditText name = (EditText)findViewById(R.id.name);
            final EditText desc = (EditText)findViewById(R.id.description);
            final CheckBox important = (CheckBox)findViewById(R.id.important);
            final CheckBox done = (CheckBox)findViewById(R.id.done);
            final DatePicker date = (DatePicker)findViewById(R.id.date);
            final TimePicker time = (TimePicker)findViewById(R.id.time);

            db.open();
           Cursor cursor =  db.fetchTodo(currentrowid);
            db.close();

            cursor.moveToFirst();
           String s_name = cursor.getString(1);
            String s_desc = cursor.getString(2);
            String s_date = cursor.getString(3);
            long fdate = Long.valueOf(s_date);
            boolean b_important = HelperMethods.intToBool(cursor.getInt(4));
            boolean b_done = HelperMethods.intToBool(cursor.getInt(5));

            name.setText(s_name);
            desc.setText(s_desc);
            important.setChecked(b_important);
            done.setChecked(b_done);

            Calendar cal = new GregorianCalendar();
            cal.setTimeInMillis(fdate);

            date.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            Log.i("YEAR","Get Year ergab " + cal.get(Calendar.YEAR));
            time.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
            time.setCurrentMinute(cal.get(Calendar.MINUTE));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_add_edit_to_do, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_usermenu) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void backtomainview(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        //setContentView(R.layout.activity_main);
    }


    public void add(View view) {
        final EditText name = (EditText)findViewById(R.id.name);
        final EditText desc = (EditText)findViewById(R.id.description);
        final CheckBox important = (CheckBox)findViewById(R.id.important);
        final CheckBox done = (CheckBox)findViewById(R.id.done);
        final DatePicker date = (DatePicker)findViewById(R.id.date);
        final TimePicker time = (TimePicker)findViewById(R.id.time);

        Editable e_name, e_desc;
        String s_name, s_desc;

        e_name = name.getText();
        e_desc = desc.getText();
        s_name = e_name.toString();
        s_desc = e_desc.toString();

        Calendar c = new GregorianCalendar(date.getYear(),date.getMonth(),date.getDayOfMonth(), time.getCurrentHour(), time.getCurrentMinute());
        Date date_milli = c.getTime();

        if (s_name.length() > 0 && s_desc.length() > 0) {
            db.open();

            if (currentrowid != -1) {
            db.updateToDo(currentrowid,s_name,s_desc,HelperMethods.dateToLongString(date_milli),important.isChecked(),done.isChecked());
            } else {
            long rowid = db.createToDo(s_name, s_desc, HelperMethods.dateToLongString(date_milli), important.isChecked(), done.isChecked());
                Log.i("AddEdit","Added at RowId " + rowid);
            }

            //ctrl.addToDo(s_name, s_desc, new Date(), true);

            Toast.makeText(AddEditToDo.this, "Hinzugefügt!",
                    Toast.LENGTH_SHORT).show();

            db.close();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            // todoadpt.notifyDataSetChanged();
        } else {
            Toast.makeText(AddEditToDo.this, "Ungültige Eingabe!",
                    Toast.LENGTH_LONG).show();
        }
    }
}
