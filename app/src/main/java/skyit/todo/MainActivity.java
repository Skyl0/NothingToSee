package skyit.todo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import skyit.todo.adapter.ToDoAdapter;
import skyit.todo.controller.ToDoCtrl;
import skyit.todo.database.DBAdapter;
import skyit.todo.model.ToDo;

public class MainActivity extends AppCompatActivity {

   // ListView listview;
    ToDoCtrl ctrl = new ToDoCtrl();
    ToDoAdapter todoadpt;
    DBAdapter db = new DBAdapter(this);
    boolean showdone;
    String currentmode = "SORT_DATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listview = (ListView) findViewById(R.id.listViewToDo);

         showdone = true;
        db.open();

        try  {
            ctrl.initDB(this);
            ctrl.readDB(this, "SORT_DATE", showdone);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        todoadpt = new ToDoAdapter(ctrl.getTodos(),this, db);

        listview.setAdapter(todoadpt);

        listview.setClickable(true);
        listview.setLongClickable(true);
/*
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onClick(AdapterView<?> parent, View v, int position, long id) {

                Context _c = getApplicationContext();
                ToDo o = (ToDo) parent.getItemAtPosition(position);
                long rowid = o.getRowid();

                Log.d("MAIN", "Click registered at position " + position + " ! RowId caught =>" ); //+ rowid);

                Intent i = new Intent(_c, AddEditToDo.class);
                i.putExtra("rowid", rowid);
                startActivity(i);
            }
        });*/

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {

                final Context _c = getApplicationContext();
                ToDo o = (ToDo) listview.getItemAtPosition(position);
                final long rowid = o.getRowid();

                Log.d("MAIN", "LongClick registered at position " + position + " ! RowId caught =>" + rowid);

                AlertDialog.Builder builder = new AlertDialog.Builder(_c);
                builder
                        .setTitle(R.string.dialog_erasetitlesingle)
                        .setMessage(R.string.dialog_erasemsgsingle)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                db.deleteToDo(rowid);
                                db.close();
                                ctrl.readDB(_c, currentmode, showdone);
                                todoadpt.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "ToDo gelöscht!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Nein", null)
                        .show();
                return true;
            }
        });


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
          //  i.putExtra("rowid",43L);
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
                    .setNegativeButton("Nein", null)
                    .show();

        }
        else if (id == R.id.action_sortdate) {
            ctrl.readDB(this,"SORT_DATE",showdone);
            currentmode = "SORT_DATE";
            todoadpt.set_data(ctrl.getTodos());
            todoadpt.notifyDataSetChanged();
        }
        else if (id == R.id.action_sortimportance) {
            ctrl.readDB(this,"SORT_IMPORTANCE_DATE",showdone);
            currentmode = "SORT_IMPORTANCE_DATE";
            todoadpt.set_data(ctrl.getTodos());
            todoadpt.notifyDataSetChanged();
        }
        else if (id == R.id.action_showhidedone) {
            showdone = !showdone;

            ctrl.readDB(this,currentmode,showdone);
           /* ctrl.readDB(this,"ONLY_NOT_DONE");
            todoadpt.set_data(ctrl.getTodos()); */
            todoadpt.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

}
