package app.database_manager;

/**
 * EntitiyID
 */
public class EntityID {
    private int id;

    public EntityID(int id) {
        this.id = id;
    }

    public int value() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return Integer.toString(id);
    }

}