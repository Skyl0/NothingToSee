package skyit.todo.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Marc on 21.03.2016.
 */
public final class UserData {

    public static final String FILENAME = "userdata";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    public static String getPassword (Context c) {
        SharedPreferences prefs = c.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return prefs.getString(PASSWORD,"");
    }

    public static void setPassword (Context c, String pw) {
        SharedPreferences prefs = c.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PASSWORD, pw);
        editor.commit();
    }

    public static String getUsername(Context c){
        SharedPreferences prefs = c.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return prefs.getString(USERNAME,"UNKNOWN");
    }

    public static void setUsername(Context c, String user) {
        SharedPreferences prefs = c.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USERNAME, user);
        editor.commit();
    }
}
