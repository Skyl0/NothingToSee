package skyit.todo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marc on 21.03.2016.
 */
public final class HelperMethods {


    public static boolean isPasswordValid (char[] pw) {

        if (pw.length < 5 || pw.length > 20 ) return false;

        boolean hasNumber = false;

        for (char c : pw) {
            if (!Character.isLetterOrDigit(c)){
                return false;
            }
            if (Character.isDigit(c)) {
                hasNumber = true;
            }
        }
        return hasNumber;
    }

    public static int boolToInt(boolean b) {
        if (b) return 1;
        return 0;
    }

    public static boolean intToBool (int i) {
        if (i == 0) return false;
        return true;
    }

    public static String dateToLongString(Date d) {
        String temp;
        temp = Long.toString(d.getTime());
        return temp;
    }

}
