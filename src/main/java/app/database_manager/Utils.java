package app.database_manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;


/**
 * utils
 */
public class Utils {

    public static Object extract(String what, Object fromWhere, Object... args) {

        if (fromWhere instanceof Map<?, ?> || fromWhere instanceof Collection<?>)
            return invokeFunction("get", fromWhere, args);
        else
            try {
                return FieldUtils.readField(fromWhere, what, true);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return invokeFunction("get" + StringUtils.capitalize(what), fromWhere);
            }
    }

    public static Object invokeFunction(final String functionName, final Object obj, final Object... args) {
        final Class<?> paramsTypes[] = new Class[args.length];
        for (int i = 0; i < paramsTypes.length; i++) {
            paramsTypes[i] = args[i].getClass();
        }
        try {
            return obj.getClass().getMethod(functionName, paramsTypes).invoke(obj, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Pair<String, Object>> get(String what, Object fromWhere) {
        try {
            if (Character.isLetter(what.charAt(what.length() - 1))) {
                what += '&';
            }

            return getRecur(what, fromWhere, "");
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 
     * @param what      string query with special identifiers : '&', '{', '}', '*',
     *                  ':'
     * @param fromWhere object where to pull data from
     * @return array of pairs (attribute : value)
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public static ArrayList<Pair<String, Object>> getRecur(String what, Object fromWhere, String condition)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {

        // start constructing query from string
        StringBuilder entity = new StringBuilder(10);
        ArrayList<Pair<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < what.length(); i++) {
            char ch = what.charAt(i);

            // check if encountered a seperator
            if (Character.isLetter(ch)) {
                entity.append(ch);
            } else if (entity.length() > 0) {
                switch (ch) {

                case '&':
                    // simply get the attribute recursively and add it to the result array
                    result.add(Pair.of(entity.toString(), getRecur(entity.toString(), fromWhere, condition)));
                    break;
                case ':':
                    int idxMatchingColon = what.indexOf(':', i + 1);
                    condition = what.substring(i + 1, idxMatchingColon);
                    what = what.replace(":" + condition + ":", "");
                case '{':
                    int idxOfMatchingClosingBrace = indexOfMatchingClosingBrace(what, i); // getMatchingBrace

                    /**
                     * 1. recursively get the attribute that has been constructed 2. create new
                     * query according to what is in the braces 3. pass both values to the main get
                     * function : it can be treated as its own query 4. add it to the array
                     */
                    result.add(Pair.of(entity.toString(), get(what.substring(i + 1, idxOfMatchingClosingBrace),
                            getRecur(entity.toString(), fromWhere, condition))));

                    i = idxOfMatchingClosingBrace; // update i as what is in the braces has been processed
                    condition = "";
                    break;
                default:
                    break;
                }
                entity.setLength(0); // reset the entity variable
            }
        }
        if (result.isEmpty() && what.length() > 0) {

            // iterate over the father object
            for (Object object : getCollection(fromWhere)) {
                if (object instanceof Pair<?, ?>) {
                    // only the value interests us if its a pair
                    object = ((Pair<?, ?>) object).getValue();
                }
                ArrayList<Pair<String, Object>> tPairs = new ArrayList<>();
                for (Object obj : getCollection(getFieldValue(what, object))) {
                    if (obj instanceof Pair<?, ?>) {
                        Pair<?, ?> p = (Pair<?, ?>) obj;
                        tPairs.add(Pair.of(p.getKey().toString(), p.getValue()));
                    } else {
                        if (testConditions(obj, condition))
                            result.add(Pair.of(obj.getClass().getSimpleName(), obj));
                    }
                }
                if (!tPairs.isEmpty()) {
                    result.add(Pair.of(what, tPairs));
                }
            }
        }
        return result;
    }

    public static int indexOfMatchingClosingBrace(final String str, final int idxOfOpeningBrace) {
        int idxOfMatchingClosingBrace = -1;
        int nbOpeningBraces = 0;
        for (int i = idxOfOpeningBrace; i < str.length(); i++) {
            final char ch = str.charAt(i);
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

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        return fields;
    }

    private static Object getFieldValue(final String fieldName, final Object parent) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        if (fieldName.equals("*")) {
            final ArrayList<Pair<String, Object>> fArrayList = new ArrayList<>();
            for (final Field f : getAllFields(new ArrayList<>(), parent.getClass())) {
                fArrayList.add(Pair.of(f.getName(), getFieldValue(f.getName(), parent)));
            }
            return fArrayList;

        } else {
            return extract(fieldName, parent);
        }
    }

    private static ArrayList<Object> getCollection(final Object obj) {
        final ArrayList<Object> result = new ArrayList<>();
        if (obj instanceof Collection<?>) {
            for (final Object object : (Collection<?>) obj) {
                result.add(object);
            }
        } else if (obj instanceof Map<?, ?>) {
            for (final Object object : ((Map<?, ?>) obj).values()) {
                result.add(object);
            }
        } else {
            result.add(obj);
        }
        return result;
    }

    /*
     * public static boolean matchConditions(char condition, Object ob, Object val)
     * { boolean matchCond = false; switch (condition) { case '=': if (ob == val) {
     * matchCond = true; } else matchCond = false; case '<': if (ob < val) {
     * matchCond = true; } else matchCond = false; case '>': if (ob > val) {
     * matchCond = true; } else matchCond = false; } return matchCond; }
     */

    public static boolean testConditions(final Object obj, final String conditions) {
        if (conditions.isEmpty())
            return true;
        StringBuilder elem = new StringBuilder();
        StringBuilder val = new StringBuilder();
        boolean portal = false;

        boolean matchCond = false;

        char cond = 'c';
        for (int i = 0; i < conditions.length(); i++) {
            if (!portal) {

                if (Character.isLetter(conditions.charAt(i))) {
                    elem.append(conditions.charAt(i));
                } else {
                    cond = conditions.charAt(i);
                    portal = true;
                    continue;
                }
            }
            if (portal) {
                val.append(conditions.charAt(i));
            }
        }

        Object ob = null;
        try {
            ob = getFieldValue(elem.toString(), obj);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (ob instanceof Double) {
            Double newval = Double.valueOf(val.toString());
            Double newobj = (Double) ob;
            switch (cond) {
            case '=':
                if (newobj == newval) {
                    matchCond = true;
                } else
                    matchCond = false;
                break;
            case '<':
                if (newobj < newval) {
                    matchCond = true;
                } else
                    matchCond = false;
                break;
            case '>':
                if (newobj > newval) {
                    matchCond = true;
                } else
                    matchCond = false;
                break;
            }

        } else if (ob instanceof String) {
            String newval = val.toString().toLowerCase();
            String newobj = ((String) ob).toLowerCase();
            switch (cond) {
            case '=':
                if (newobj.equals(newval)) {
                    matchCond = true;
                } else
                    matchCond = false;
                break;
            case '%':
                Pattern patt = Pattern.compile(newval);
                Matcher match = patt.matcher(newobj);
                if (match.find()) {
                    matchCond = true;
                } else
                    matchCond = false;
                break;
            }

        }

        return matchCond;
    }

    public static List<Field> extractFields(Class<?> fromWhere) {
        List<Field> result = new ArrayList<>();
        for (Field field : getAllFields(new ArrayList<Field>(), fromWhere)) {
            result.add(field);
        }

        return result;
    }

    public static String normalizeCamelCase(String str) {
        return StringUtils.capitalize(str.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])",
                "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])"), " "));
    }

    /**
     * Scans all classes accessible from the context class loader which belong to
     * the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static Class<?>[] getClasses(final String packageName) throws ClassNotFoundException, IOException {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        final String path = packageName.replace('.', '/');
        final Enumeration<URL> resources = classLoader.getResources(path);
        final List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            final URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        final ArrayList<Class<?>> classes = new ArrayList<>();
        for (final File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base
     *                    directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> findClasses(final File directory, final String packageName)
            throws ClassNotFoundException {
        final List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        final File[] files = directory.listFiles();
        for (final File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(
                        Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public static void save(Serializable obj, String fileName) throws IOException {
        System.out.println(String.format("Saving %s to %s", obj, fileName));
        FileOutputStream fos;
        ObjectOutputStream oos;
        fos = new FileOutputStream(fileName);
        oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos.flush();
        oos.close();
    }

    public static Object load(String fileName) throws IOException, ClassNotFoundException {
        System.out.println(String.format("loading from %s", fileName));
        FileInputStream fis;
        ObjectInputStream ois;
        fis = new FileInputStream(fileName);
        ois = new ObjectInputStream(fis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }

    public static Class<?> getTypeFromMap(Field f) {
        Class<?> c;
        if (f.getType().isAssignableFrom(HashMap.class)) {
            ParameterizedType pt = (ParameterizedType) f.getGenericType();
            c = (Class<?>) pt.getActualTypeArguments()[1];
        } else {
            c = f.getType();
        }
        return c;
    }

    public static boolean isStandardType(Class<?> fromWhere) {

        if (fromWhere.getCanonicalName().startsWith("java.") || fromWhere.getCanonicalName().startsWith("javax.")
                || fromWhere.getCanonicalName().startsWith("org.")) {
            return true;
        }

        else
            return false;
    }
}
