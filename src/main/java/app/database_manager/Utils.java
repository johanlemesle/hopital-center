package app.database_manager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * util
 */
public class Utils {

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

    public static String capitalize(String str) {
        char ch = Character.toUpperCase(str.charAt(0));
        return ch + str.substring(1);
    }

    public static Object getOne(String what, Object fromWhere, Object... args) {
        String str = "get" + capitalize(what);
        try {
            Class<?> paramsTypes[] = new Class[args.length];
            for (int i = 0; i < paramsTypes.length; i++) {
                paramsTypes[i] = args[i].getClass();
            }
            return fromWhere.getClass().getMethod(str, paramsTypes).invoke(fromWhere, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static Object get(String what, Object fromWhere) {
        ArrayList<String> elems = new ArrayList<String>(Arrays.asList(what.split("\\.")));
        for (int i = 0; i < elems.size(); i++) {
            elems.set(i, "get" + capitalize(elems.get(i)));
        }

        try {
            return Utils.getRecur(elems, fromWhere);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static Object getRecur(ArrayList<String> elems, Object obj) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        if (!elems.isEmpty()) {
            String elem = elems.get(0);
            elems.remove(0);
            obj = obj.getClass().getMethod(elem).invoke(obj);

            obj = getRecur(elems, obj);
        }
        return obj;
    }

    public static Object matchConditions(Object obj, String conditions) {

        return conditions;

    }

}