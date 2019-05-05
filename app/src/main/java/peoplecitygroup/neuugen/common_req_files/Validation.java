package peoplecitygroup.neuugen.common_req_files;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static boolean isValidName( String name) {
        String NAME_PATTERN = "^[a-zA-Z\\s]*$";
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();

    }
    public static boolean isValidPassword(String password){
        if(password.length()>=8)
            return true;
        else
            return false;
    }
    public static boolean isValidEmail(String mail) {
        return Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }


    public static boolean isValidPhone(String target) {
        if (target == null || target.length() < 10 || target.length() > 10) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

    public static boolean isValidCity(String citytext) {
        String Cities[]= LinkCities.cities.split(",");
        for (String name:Cities)
            if(citytext.equalsIgnoreCase(name.trim()))
                return true;
         return false;
    }
}
