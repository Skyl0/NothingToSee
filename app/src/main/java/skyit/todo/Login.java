package skyit.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import skyit.todo.sharedpreferences.UserData;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
     //   getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void login(View v){

        final EditText user = (EditText)findViewById(R.id.username);
        final EditText pw = (EditText)findViewById(R.id.pw1);

        Editable e_user, e_pw;
        String s_user, s_pw;

        e_user = user.getText();
        e_pw = pw.getText();

        s_user = e_user.toString();
        s_pw = e_pw.toString();

        if (s_user.compareTo(UserData.getUsername(this)) == 0 && s_pw.compareTo(UserData.getPassword(this)) == 0) {
            Toast.makeText(Login.this, "Korrekte Eingabe!",
                    Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);

        } else {
            Toast.makeText(Login.this, "Ungültige Eingabe!",
                    Toast.LENGTH_SHORT).show();
        }


    }
}
