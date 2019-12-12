package app.database_manager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

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

    public static void insert(String what, Object where, Object... args) {
        String str = "add" + StringUtils.capitalize(what);
        applyFunction(str, where, args);
    }

    public static Object getOne(String what, Object fromWhere, Object... args) {
        String str = "get" + StringUtils.capitalize(what);
        return applyFunction(str, fromWhere, args);
    }

    private static Object applyFunction(String functionName, Object obj, Object... args) {
        Class<?> paramsTypes[] = new Class[args.length];
        for (int i = 0; i < paramsTypes.length; i++) {
            paramsTypes[i] = args[i].getClass();
        }
        try {
            return obj.getClass().getMethod(functionName, paramsTypes).invoke(obj, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Pair<String, Object>> get(String what, Object fromWhere) {
        try {
            if (Character.isLetter(what.charAt(what.length() - 1))) {
                what += '&';
            }
            return getRecur(what, fromWhere);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<Pair<String, Object>> getRecur(String what, Object fromWhere)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        StringBuilder entity = new StringBuilder(10);
        ArrayList<Pair<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < what.length(); i++) {
            char ch = what.charAt(i);
            if (Character.isLetter(ch)) {
                entity.append(ch);
            } else if (entity.length() > 0) {
                switch (ch) {
                case '{':
                    int idxOfMatchingClosingBrace = indexOfMatchingClosingBrace(what, i);
                    result.add(Pair.of(entity.toString(), get(what.substring(i + 1, idxOfMatchingClosingBrace),
                            getRecur(entity.toString(), fromWhere))));
                    i = idxOfMatchingClosingBrace;
                    break;
                case '&':
                    result.add(Pair.of(entity.toString(), getRecur(entity.toString(), fromWhere)));
                    break;
                default:
                    break;
                }
                entity.setLength(0);
            }
        }
        if (result.isEmpty() && what.length() > 0) {
            for (Object object : getCollection(fromWhere)) {
                if (object instanceof Pair<?, ?>) {
                    object = ((Pair<?, ?>) object).getValue();
                }
                ArrayList<Pair<String, Object>> tPairs = new ArrayList<>();
                for (Object obj : getCollection(getFieldValue(what, object))) {
                    if (obj instanceof Pair<?, ?>) {
                        Pair<?, ?> p = (Pair<?, ?>) obj;
                        tPairs.add(Pair.of(p.getKey().toString(), p.getValue()));
                    } else {
                        result.add(Pair.of(what, obj));
                    }
                }
                if (!tPairs.isEmpty()) {
                    result.add(Pair.of(what, tPairs));
                }
            }
        }
        return result;
    }

    public static int indexOfMatchingClosingBrace(String str, int idxOfOpeningBrace) {
        int idxOfMatchingClosingBrace = -1;
        int nbOpeningBraces = 0;
        for (int i = idxOfOpeningBrace; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '{') {
                nbOpeningBraces++;
            } else if (ch == '}') {
                nbOpeningBraces--;
            }
            if (nbOpeningBraces == 0) {
                idxOfMatchingClosingBrace = i;
                break;
            }
        }
        return idxOfMatchingClosingBrace;
    }

    public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    private static Object getFieldValue(String fieldName, Object parent) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        if (fieldName.equals("*")) {
            ArrayList<Pair<String, Object>> fArrayList = new ArrayList<>();
            for (Field f : getAllFields(new ArrayList<>(), parent.getClass())) {
                fArrayList.add(Pair.of(f.getName(), getFieldValue(f.getName(), parent)));
            }
            return fArrayList;

        } else {
            return parent.getClass().getMethod("get" + StringUtils.capitalize(fieldName)).invoke(parent);
        }
    }

    public static ArrayList<Object> normalized(Object obj) {
        ArrayList<Object> data = new ArrayList<>();
        for (Object object : getCollection(obj)) {
            if (object instanceof Collection<?> || object instanceof Map<?, ?>) {
                data.addAll(normalized(object));
            } else if (object instanceof Pair<?, ?>) {
                data.add(((Pair<?, ?>) object).getValue());
            } else {
                data.add(object);
            }
        }
        return data;
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
