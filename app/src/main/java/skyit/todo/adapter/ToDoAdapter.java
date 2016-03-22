package skyit.todo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import skyit.todo.R;
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

    public ToDoAdapter(ArrayList<ToDo> _data, Context _c) {
        this._data = _data;
        this._c = _c;
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

        if (v == null) {
            LayoutInflater vi = (LayoutInflater)_c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_todo_single,null);
        }

        ToDo actual = _data.get(position);

        CheckBox doneView = (CheckBox)v.findViewById(R.id.done);
        TextView nameView = (TextView)v.findViewById(R.id.name);
        TextView descView = (TextView)v.findViewById(R.id.description);
        TextView dateView = (TextView)v.findViewById(R.id.date);

        doneView.setChecked(actual.getDone());
        nameView.setText(actual.getName());
        descView.setText(actual.getDesc());
        dateView.setText(actual.getDate().toString());

        String lightred = "#ffaaaa";
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

        return v;
    }


}
