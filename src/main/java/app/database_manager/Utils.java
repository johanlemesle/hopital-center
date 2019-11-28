package app.database_manager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
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

    public static ArrayList<Object> get(String what, Object fromWhere) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        ArrayList<Object> result = new ArrayList<>();
        StringBuilder entity = new StringBuilder(10);
        for (int i = 0; i < what.length(); i++) {
            if (Character.isLetter(what.charAt(i))) {
                entity.append(what.charAt(i));
            } else {
                for (Object object : getCollection(fromWhere)) {
                    switch (what.charAt(i)) {
                    case '{':
                        result.add(get(what.substring(i + 1), getFieldValue(entity.toString(), object)));
                        break;
                    case '&':
                        // result.add(getFieldValue(entity.toString(), object));
                        result.add(get(what.substring(i + 1), fromWhere));
                        break;
                    case '}':
                        result.add(getFieldValue(entity.toString(), object));
                        break;
                    case '*':
                        result.add(object);
                        break;
                    default:
                        break;
                    }
                }
                System.out.println();
                break;
            }
        }
        return result;
    }

    private static Object getFieldValue(String fieldName, Object parent) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return parent.getClass().getMethod("get" + capitalize(fieldName)).invoke(parent);
    }

    private static ArrayList<Object> getCollection(Object obj) {
        ArrayList<Object> result = new ArrayList<>();
        if (obj instanceof Collection<?>) {
            for (Object object : (Collection<?>) obj) {
                result.add(object);
            }
        } else if (obj instanceof Map<?, ?>) {
            for (Object object : ((Map<?, ?>) obj).values()) {
                result.add(object);
            }
        } else {
            result.add(obj);
        }
        return result;
    }

    public static Object matchConditions(Object obj, String conditions) {

        return conditions;

    }

}