package app.database_manager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * util
 */
public class Util {

    public static int makeHash(Personne pers) {
        String str = pers.getNom() + pers.getPrenom() + pers.getTel();
        return str.hashCode();
    }

    public static Object match(Object regex, Object target) {
        Pattern p = Pattern.compile(regex.toString());
        Matcher m = p.matcher(target.toString());
        if (m.matches()) {
            return target;
        }
        return null;
    }

    public static String purge(String str) {
        str = str.toLowerCase();
        str = str.replace("é", "e");
        str = str.replace("è", "e");
        str = str.replace("ê", "e");
        str = str.replace("à", "a");
        str = str.replace("â", "a");
        str = str.replace("î", "i");

        return str;
    }
}