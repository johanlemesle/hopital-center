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
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * utils
 */
public class Utils {

    public static Object extract(String what, Object fromWhere, Object... args) {
        if (fromWhere instanceof Map<?, ?> || fromWhere instanceof Collection<?>)
            return invokeFunction("get", fromWhere, args);
        else
            return invokeFunction("get" + StringUtils.capitalize(what), fromWhere, args);
    }

    public static Object invokeFunction(String functionName, Object obj, Object... args) {
        Class<?> paramsTypes[] = new Class[args.length];
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

    // fromWhere: Infirmier inf
    // service{nom&batiment&directeur{*}}&salaire&rotation
    /**
     * 
     * @param what
     * @param fromWhere
     * @return
     */
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
    public static ArrayList<Pair<String, Object>> getRecur(String what, Object fromWhere) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

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
                case '{':
                    int idxOfMatchingClosingBrace = indexOfMatchingClosingBrace(what, i); // getMatchingBrace

                    /**
                     * 1. recursively get the attribute that has been constructed 2. create new
                     * query according to what is in the braces 3. pass both values to the main get
                     * function : it can be treated as its own query 4. add it to the array
                     */
                    result.add(Pair.of(entity.toString(), get(what.substring(i + 1, idxOfMatchingClosingBrace),
                            getRecur(entity.toString(), fromWhere))));

                    i = idxOfMatchingClosingBrace; // update i as what is in the braces has been processed
                    break;
                case '&':
                    // simply get the attribute recursively and add it to the result array
                    result.add(Pair.of(entity.toString(), getRecur(entity.toString(), fromWhere)));
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
            return extract(fieldName, parent);
        }
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

    public static List<Field> extractFields(Class<?> fromWhere) {
        List<Field> result = new ArrayList<>();

        // remplir la list result de nom/type attribut

        for (Field field : getAllFields(new ArrayList<Field>(), fromWhere)) {
            result.add(field);
        }

        return result;
    }

    // fonction qui prend en parametre un string en camel case et le retourne
    // normalisé
    // ex : "jeSuisPasBlond" devient "je suis pas blond"
    public String normalizeCamelCase(String str) {
        String result = "";

        // a coder ..
        return result;
    }
    // ========================================================================\\
    // HARD:

    // fonction qui prend en parametre un objet et qui retourne ses attributs et les
    // attributs emboités des objets à l'intérieur
    // on s'arrête quand on arrive à des types qui ne sont pas définits dans le
    // modèle objet
    // la fonction retourne une pair (attribut:type)

    // exemple : extractNestedFields(Chambre.class) retourne :
    // Chambre:[(id:int),(service:[(id:int),(nom:String),(batiment:int),(directeur:[(nom:String),(prenom:String)])])]

    // tip #1 : le prototype marche car Object peut lui meme etre une
    // List<Pair<Object,Class<?>>. mais change le à ta maniere
    // tip #2 : partir sur une implementation recursive
    // tip #3 : attention, pour l'implementation recursive, il y a des dependances
    // cycliques : un Patient a Docteur comme attribut, et un docteur a un patient
    // comme attribut. je te conseil d'ajouter une variable 'maxDepth' qui lui dira
    // de s'arreter quand il atteint la profondeur maximale de l'arbre
    // tip #4 : attention, parfois les attributs sont des List, ArrayList, ou
    // HashMap, auquel cas il faut choper le type qui constitue la
    // List/ArrayList/Map(si t'as une List<Patient>, il faut choper 'Patient')

    public static List<Pair<Object, Class<?>>> extractNestedFields(Class<?> fromWhere) {
        List<Pair<Object, Class<?>>> result = new ArrayList<>();
        // a coder ..
        return result;
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
    public static Class<?>[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
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
    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
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

}
