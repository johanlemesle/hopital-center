package graphical_user_interface;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import app.database_manager.*;

import static org.junit.Assert.*;

import java.text.Format.Field;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Unit test for simple App.
 */

// ici on test les fonctions.
// 1. déclarer une fonction précédée par le keyword @Test
// 2. mettre les test dans cette fonction
// 3. cliquer sur 'run test' ou 'debug test' au dessus de la fonction.
// 'run test' au dessus de la classe va executer tous les test à l'intérieur


public class AppTest {
    /**
     * Rigorous Test.
     */
    @Test
    public void test1() {
       /* System.out.println("running test");
        List<Pair<Field, Class<?> >> lp = Utils.extractFieldNames(Hopital.class);
        for (Pair<Field,Class<?>> p : lp) {
            System.out.println("nom du field : " + p.getLeft().getName());
            System.out.println("nom du type : " + p.getRight().getDeclaredType());
        }*/
    }

    @Test
    public void test2() {
       // String str = "JeanMarieLapine" ;
        // System.out.println(Utils.normalizeCamelCase(str));
       System.out.println(Integer.class.getCanonicalName());
    }

}
