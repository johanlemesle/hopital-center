package app.graphical_user_interface;

import app.database_manager.Adresse;
import app.database_manager.Docteur;

/**
 * menu
 */
enum MyEnum {
    lol, xd, mdr;

}

public class Menu {

    public static void main(String[] args) {
        Docteur d = new Docteur("alain", "morelle", "07 67 24 84 91", new Adresse(), "généraliste");
        System.out.println(d);
    }

}