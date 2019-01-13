package serve.serveup.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import serve.serveup.R;


/**
 * author: urban_jagodic
 * Class for validating input.
 * Tool class with static methods for
 * easier validation checks.
 */
public class Utils {

    public static boolean isNullOrEmpty(String input) {
        return input == null || input.trim().length() == 0;
    }


    // check validity of email address
    public static boolean isEmailValid(String email){
        if(!isNullOrEmpty(email)) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";
            Pattern pat = Pattern.compile(emailRegex);
            return pat.matcher(email).matches();
        }
        return false;
    }

    //check password validity
    // must contain at least 6 chars, one upper-case letter and a number
    // (?=.*\W) for non-word char
    public static boolean isPasswordValid(String password) {
        if(!isNullOrEmpty(password)) {
            String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$";
            return isInRegex(password, passwordRegex);
        }
        return false;
    }

    public static boolean isInRegex(String input, String regex) {
        Pattern pat = Pattern.compile(regex);
        return pat.matcher(input).matches();
    }


    public static void showToast(Context myContext, String message) {
        Toast.makeText(myContext, message, Toast.LENGTH_SHORT).show();
    }

    public static void logInfo(String text) {
        Log.d("serveup_test", text);
    }

    public static void logMultipleData(Object ... data) {
        if (data.length > 0) {
            for (int i = 0; i < data.length; i += 2) {
                logInfo(data[i] + " : " + data[i + 1]);
            }
        }
    }

    public static Bitmap parseBitmapFromBase64(@NonNull Context myContext, String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap rawBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return Bitmap.createScaledBitmap(rawBitmap,
                (int) myContext.getResources().getDimension(R.dimen.cardview_restaurant_image_width),
                (int) myContext.getResources().getDimension(R.dimen.cardview_restaurant_image_height),
                false);
    }


    public static ArrayList<String> readFromFile(String fileName, Context context) {
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        ArrayList<String> myStrings = new ArrayList<>();
        try {
            fIn = context.getResources().getAssets().open(fileName, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line;
            while ((line = input.readLine()) != null) {
                if(line.length() > 0 && !line.contains("NEXT_STRING"))
                    myStrings.add(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null) isr.close();
                if (fIn != null) fIn.close();
                if (input != null) input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return myStrings;
    }


    public static void createDialog(Activity myActivity, String title, String message, String okButton, String cancelButton,
                                    final DialogPassableMethod myCommand) {
        AlertDialog.Builder myBuilder;
        myBuilder = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ?
                new AlertDialog.Builder(myActivity, android.R.style.Theme_Material_Light_Dialog_Alert) : new AlertDialog.Builder(myActivity);

        myBuilder.setTitle(title).setMessage(message)
                .setPositiveButton(okButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myCommand.executeMethod();
                    }
                })
                .setNegativeButton(cancelButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // blank
                    }
                })
                .show();
    }


    @NonNull
    public static String randomID() {
        return RandomStringUtils.randomAlphanumeric(20);
    }


    public static String createDateTimeString(String... addedTime) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date myDate = new Date();
        if(addedTime.length > 0) {
            int addedMinutes = Integer.parseInt(addedTime[0]);
            myDate = org.apache.commons.lang3.time.DateUtils.
                    addMinutes(myDate, addedMinutes);
        }
        String returnDate = format.format(myDate)
                .replace(" ", "T");

        return returnDate;
    }

    public static String parseDateTimeString(String dateTime) {
        try {
            dateTime = dateTime
                    .replace("T", " ")
                    .replace("Z", "");
            DateFormat previosForamt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date newDate = previosForamt.parse(dateTime);
            DateFormat newFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm");
            return newFormat.format(newDate);
        }
        catch (ParseException e) { Utils.logInfo("Parse DateTime exception"); }
        return "DEFAULT";
    }


}
