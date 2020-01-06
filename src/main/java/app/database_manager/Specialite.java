package app.database_manager;

public enum Specialite {
    generaliste("Généraliste"), dentiste("Dentiste"), ophtalmologue("Ophtalmologue");

    private String str;

    Specialite(String s) {
        this.str = s;
    }

    @Override
    public String toString() {
        return str;
    }

};
