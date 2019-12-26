package app.database_manager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

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
    public static int makeHash(Personne pers) {
        String str = pers.getNom() + pers.getPrenom() + pers.getTel();
        return str.hashCode();
    }

    public static int makeHash(Chambre chambre) {
        String str = Integer.toString(chambre.getBatiment()) + Integer.toString(chambre.getId());
        return str.hashCode();
    }

    public static int makeHash(Service service) {
        String str = Integer.toString(service.getBatiment()) + Integer.toString(service.getId());
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

    // fonction qui retourne une list de pair (attribut:type de l'attribut) d'une
    // classe passée en paramètre
    // ex. extractFieldNames(Chambre.class) retourne une liste
    // [(id:int), (service:Service), (surveillant:Infirmier), (nbLits:byte) ... etc]

    public static List<Pair<Field, Class<?>>> extractFieldNames(Class<?> fromWhere) 
    {
        List<Pair<Field, Class<?>>> result = new ArrayList<>();
        List<Field> fields = getAllFields(new ArrayList<Field>(), fromWhere);

        for (Field field : fields) 
        {
            System.out.println("attribut trouvé : " + field.getName() + ". Type : " + field.getType().getSimpleName());
        }

        // ... a continuer
        return result;
    }

    // fonction qui prend en parametre un string en camel case et le retourne
    // normalisé
    // ex : "jeSuisPasBlond" devient "je suis pas blond"

    public String normalizeCamelCase(String str) 
    {
        return str.replaceAll(
           String.format("%s|%s|%s",
              "(?<=[A-Z])(?=[A-Z][a-z])",
              "(?<=[^A-Z])(?=[A-Z])",
              "(?<=[A-Za-z])(?=[^A-Za-z])"
           ),
           " "
        );
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

}
