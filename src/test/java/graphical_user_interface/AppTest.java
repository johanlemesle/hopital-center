package graphical_user_interface;

import org.junit.Test;

import app.Hopital;
import app.database_manager.*;

import static org.junit.Assert.*;

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
        System.out.println("running test");
        Utils.extractFieldNames(Hopital.class);
        assertTrue(true);
    }

    @Test
    public void test2() {
        System.out.println("running test 2");
    }
}
