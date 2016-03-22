package skyit.todo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import skyit.todo.adapter.ToDoAdapter;
import skyit.todo.controller.ToDoCtrl;
import skyit.todo.database.DBAdapter;
import skyit.todo.model.ToDo;

public class MainActivity extends AppCompatActivity {

    ListView listview;
    ToDoCtrl ctrl = new ToDoCtrl();
    ToDoAdapter todoadpt;
    DBAdapter db = new DBAdapter(this);
    boolean showDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listViewToDo);
        showDone = true;

        try  {
            ctrl.initDB(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /** ------------------------ *
         *  --- TO DELETE BEGIN ---- *
         *  ------------------------ **/
        Date now = new Date();
        Date d = new Date(1458010492L * 1000);
        Date d2 = new Date(1457762092L * 1000);

        ToDo test = new ToDo("Aufstehen","Gähn",d,true,true);

       // ctrl.addToDo("Kaffee kochen","Kaffee kochen",now,true);
       // ctrl.addToDo("Wäsche waschen","Waschmittel nicht vergessen",d2,false);
       // ctrl.addToDo(test);

        /** ------------------------ *
         *  --- TO DELETE END ------ *
         *  ------------------------ **/

        ctrl.deleteAll();

        ctrl.readDB(this);

        todoadpt = new ToDoAdapter(ctrl.getTodos(),this);
        listview.setAdapter(todoadpt);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent i = new Intent(this, UserManagement.class);
            startActivity(i);
        }
        else if (id == R.id.action_new) {
         //  setContentView(R.layout.activity_add_edit_to_do);
            Intent i = new Intent(this, AddEditToDo.class);
            startActivity(i);
        }
        else if (id == R.id.action_deleteall) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle(R.string.dialog_erasetitle)
                    .setMessage(R.string.dialog_erasemsg)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            db.open();
                            db.truncateTable();
                            db.close();
                            ctrl.deleteAll();
                            todoadpt.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, "Alle ToDo gelöscht!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Nein", null)						//Do nothing on no
                    .show();

        }
        else if (id == R.id.action_sortdate) {
            ArrayList<ToDo> temp = ctrl.getTodos();
            Collections.sort(temp);
            ctrl.setTodos(temp);
            todoadpt.notifyDataSetChanged();
        }
        else if (id == R.id.action_sortimportance) {
            ArrayList<ToDo> temp = ctrl.getTodos();
            Collections.sort(temp, new Comparator<ToDo>() {

                @Override
                public int compare(ToDo todo1, ToDo todo2) {
                if (todo1.getImportant() && !todo2.getImportant()) {
                        return -1;
                    }
                    if (!todo1.getImportant() && todo2.getImportant()) {
                        return 1;
                    }
                    if (todo1.getDate().compareTo(todo2.getDate()) == -1) {
                        return -1;
                    }
                    if (todo2.getDate().compareTo(todo1.getDate()) == -1) {
                        return 1;
                    }
                    return 0;
                }
            });
            ctrl.setTodos(temp);
            todoadpt.notifyDataSetChanged();
        }
        else if (id == R.id.action_showhidedone) {
            if (showDone) {
                showDone = false;
                todoadpt.set_data(ctrl.getNotDoneTodos());
                todoadpt.notifyDataSetChanged();
            } else {
                showDone = true;
                todoadpt.set_data(ctrl.getTodos());
                todoadpt.notifyDataSetChanged();
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
