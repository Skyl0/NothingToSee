package skyit.todo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import skyit.todo.sharedpreferences.UserData;

public class FirstStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);


    }

    public void createUser(View v) {
        final EditText user = (EditText)findViewById(R.id.username);
        final EditText pw1 = (EditText)findViewById(R.id.pw1);
        final EditText pw2 = (EditText)findViewById(R.id.pw2);

        Editable e_user, e_pw1, e_pw2;
        String s_user, s_pw1, s_pw2;

        e_user = user.getText();
        e_pw1 = pw1.getText();
        e_pw2 = pw2.getText();
        s_user = e_user.toString();
        s_pw1 = e_pw1.toString();
        s_pw2 = e_pw2.toString();

        if ( s_user.length() > 0 && s_pw1.compareTo(s_pw2) == 0 && HelperMethods.isPasswordValid(s_pw1.toCharArray()) ) {
            Toast.makeText(FirstStart.this, "Korrekte Eingabe!",
                    Toast.LENGTH_SHORT).show();

            UserData.setUsername(this, s_user);
            UserData.setPassword(this, s_pw1);

            Log.i("Usermanagement", "User and Password added!");

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);

        } else {
            // Ungültige Eingabe, alles löschen
            user.setText("");
            pw1.setText("");
            pw2.setText("");
            Toast.makeText(FirstStart.this, "Ungültige Eingabe!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    //    getMenuInflater().inflate(R.menu.menu_first_start, menu);
        return true;
    }

}
