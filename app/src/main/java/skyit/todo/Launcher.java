package skyit.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import skyit.todo.sharedpreferences.UserData;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        if (UserData.getPassword(this).length() > 0) {
            // WENN BEREITS EIN PASSWORT FESTGELEGT WURDE

            Intent i = new Intent(this, Login.class);
            startActivity(i);
        } else {
            // Ansonsten neu anlegen eines Benutzers
            Intent i = new Intent(this, FirstStart.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_launcher, menu);
        return true;
    }

}
