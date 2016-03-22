package skyit.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import skyit.todo.controller.ToDoCtrl;
import skyit.todo.database.DBAdapter;

public class AddEditToDo extends AppCompatActivity {

    DBAdapter db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_to_do);
        db = new DBAdapter(this);

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

        Editable e_name, e_desc;
        String s_name, s_desc;

        e_name = name.getText();
        e_desc = desc.getText();
        s_name = e_name.toString();
        s_desc = e_desc.toString();

        Date date = new Date();

        if (s_name.length() > 0 && s_desc.length() > 0) {
            db.open();
            db.createToDo(s_name, s_desc, HelperMethods.dateToLongString(date), important.isChecked(), done.isChecked());

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
