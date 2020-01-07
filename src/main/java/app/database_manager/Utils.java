package app.database_manager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import app.database_manager.entities.Chambre;
import app.database_manager.entities.Personne;
import app.database_manager.entities.Service;

/**
 * util
 */
public class Utils {

    /**
     * Hashes a person object according to nom, prenom, tel fields
     * 
     * @param pers person object from which hash will be made
     * @return hash value for person object
     */
    public static int makeHash(final Personne pers) {
        final String str = pers.getNom() + pers.getPrenom() + pers.getTel();
        return str.hashCode();
    }

    public static int makeHash(final Chambre chambre) {
        final String str = Integer.toString(chambre.getBatiment()) + Integer.toString(chambre.getId());
        return str.hashCode();
    }

    public static int makeHash(final Service service) {
        final String str = Integer.toString(service.getBatiment()) + Integer.toString(service.getId());
        return str.hashCode();
    }

    /**
     * removes special characetrs and puts string to lower case
     * 
     * @param str
     * @return new string
     */
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

    public static Object extract(final String what, final Object fromWhere, final Object... args) {
        if (fromWhere instanceof Map<?, ?> || fromWhere instanceof Collection<?>)
            return invokeFunction("get", fromWhere, args);
        else
            return invokeFunction("get" + StringUtils.capitalize(what), fromWhere, args);
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

    public static List<Field> getAllFields(final List<Field> fields, final Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

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
            String newval = val.toString();
            String newobj = (String) ob;
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

    // fonction qui retourne une list de pair (attribut:type de l'attribut) d'une
    // classe passée en paramètre
    // ex. extractFieldNames(Chambre.class) retourne une liste
    // [(id:int), (service:Service), (surveillant:Infirmier), (nbLits:byte) ... etc]
    public static List<Pair<Field, Class<?>>> extractFieldNames(final Class<?> fromWhere) {
        final List<Pair<Field, Class<?>>> result = new ArrayList<>();
        final List<Field> fields = getAllFields(new ArrayList<Field>(), fromWhere);
        for (final Field field : fields) {
            System.out.println("attribut trouvé : " + field.getName() + ". Type : " + field.getType().getSimpleName());
        }
        // ... a continuer
        return result;
    }

    // fonction qui prend en parametre un string en camel case et le retourne
    // normalisé
    // ex : "jeSuisPasBlond" devient "je suis pas blond"
    public String normalizeCamelCase(final String str) {
        final String result = "";

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

    public static List<Pair<Object, Class<?>>> extractNestedFields(final Class<?> fromWhere) {
        final List<Pair<Object, Class<?>>> result = new ArrayList<>();
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
}
