package skyit.todo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import skyit.todo.AddEditToDo;
import skyit.todo.HelperMethods;
import skyit.todo.R;
import skyit.todo.controller.ToDoCtrl;
import skyit.todo.database.DBAdapter;
import skyit.todo.model.ToDo;

/**
 * Programmiert von  Marc on 21.03.2016.
 * H_DA (Hochschule Darmstadt)
 * Die Nutzung ist für private Zwecke gestattet.
 */
public class ToDoAdapter extends BaseAdapter {

    public void set_data(ArrayList<ToDo> _data) {
        this._data = _data;
    }

    private ArrayList<ToDo> _data;
    private Context _c;
    private DBAdapter _db;
    private ToDoCtrl _ctrl;

    public ToDoAdapter(ArrayList<ToDo> _data, Context _c, DBAdapter db, ToDoCtrl ctrl) {
        this._data = _data;
        this._c = _c;
        this._db = db;
        this._ctrl = ctrl;
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int position) {
        return _data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return _data.get(position).getUID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final  long rowid = _data.get(position).getRowid();

        final int pos = position;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater)_c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_todo_single,null);
        }

        final ToDo actual = _data.get(position);

        final CheckBox doneView = (CheckBox)v.findViewById(R.id.done);
        TextView nameView = (TextView)v.findViewById(R.id.name);
        TextView descView = (TextView)v.findViewById(R.id.description);
        TextView dateView = (TextView)v.findViewById(R.id.date);

        doneView.setChecked(actual.getDone());
        nameView.setText(actual.getName());
        descView.setText(actual.getDesc());

        Calendar cal = new GregorianCalendar();
        cal.setTimeZone(TimeZone.getTimeZone("CET"));
        cal.setTimeInMillis(actual.getDate().getTime());

        dateView.setText(cal.get(Calendar.DAY_OF_MONTH) +"."+ cal.get(Calendar.MONTH) +"."+cal.get(Calendar.YEAR) +" - "+ cal.get(Calendar.HOUR_OF_DAY) +":"+cal.get(Calendar.MINUTE));

        String lightred = "#33ff0000";
        if (actual.getImportant()) {
            doneView.setBackgroundColor(Color.parseColor(lightred));
            nameView.setBackgroundColor(Color.parseColor(lightred));
            descView.setBackgroundColor(Color.parseColor(lightred));
            dateView.setBackgroundColor(Color.parseColor(lightred));
        } else {
            doneView.setBackgroundColor(Color.parseColor("#ffffff"));
            nameView.setBackgroundColor(Color.parseColor("#ffffff"));
            descView.setBackgroundColor(Color.parseColor("#ffffff"));
            dateView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("MAIN", "Click registered at position " + pos + " ! RowId caught =>" + rowid);

                Intent i = new Intent(_c, AddEditToDo.class);
                i.putExtra("rowid", rowid);
                _c.startActivity(i);
            }
        });


        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Log.d("MAIN", "LongClick registered at position " + pos + " ! RowId caught =>" + rowid);

                AlertDialog.Builder builder = new AlertDialog.Builder(_c);
                builder
                        .setTitle(R.string.dialog_erasetitlesingle)
                        .setMessage(R.string.dialog_erasemsgsingle)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                _db.deleteToDo(rowid);
                                _db.close();
                                _ctrl.readDB(_c, "SORT_DATE", true);
                                notifyDataSetChanged();
                                Toast.makeText(_c, "ToDo gelöscht!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Nein", null)
                        .show();
                return true;
                //return false;
            }
        });



        // ViewHolder private class

        return v;
    }


}
