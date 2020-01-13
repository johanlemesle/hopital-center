package app.database_manager;

import java.io.Serializable;

/**
 * Entity
 */

public class EntityID implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -8451723162886241153L;
    int id;

    public EntityID(int id) {
        this.id = id;
    }
}