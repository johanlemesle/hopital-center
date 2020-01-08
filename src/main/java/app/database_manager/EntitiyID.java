package app.database_manager;

/**
 * EntitiyID
 */
public class EntitiyID {
    private int id;

    public EntitiyID(int id) {
        this.id = id;
    }

    public int value() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}