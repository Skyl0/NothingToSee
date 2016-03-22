package skyit.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import skyit.todo.sharedpreferences.UserData;

public class UserManagement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        final TextView user = (TextView)findViewById(R.id.user);
        user.setText(UserData.getUsername(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_management, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changePw(View view) {

        final EditText actualpw = (EditText)findViewById(R.id.actualpw);
        final EditText pw1 = (EditText)findViewById(R.id.pw1);
        final EditText pw2 = (EditText)findViewById(R.id.pw2);

        Editable e_actualpw, e_pw1, e_pw2;
        String s_actualpw, s_pw1, s_pw2;

        e_actualpw = actualpw.getText();
        e_pw1 = pw1.getText();
        e_pw2 = pw2.getText();

        s_actualpw = e_actualpw.toString();
        s_pw1 = e_pw1.toString();
        s_pw2 = e_pw2.toString();

        if ( s_actualpw.compareTo(UserData.getPassword(this)) == 0 && s_pw1.compareTo(s_pw2) == 0 && HelperMethods.isPasswordValid(s_pw1.toCharArray())) {
            UserData.setPassword(this, s_pw1);

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else {
            // FEHLERMELDUNG
            Toast.makeText(UserManagement.this, "Ungültige Eingabe!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void goBack(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
