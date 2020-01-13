package app.graphical_user_interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.apache.commons.lang3.tuple.Pair;

import app.database_manager.Utils;
import app.graphical_user_interface.helpers.ListDisplayer;

/**
 * ResultDisplayer
 */
public class ResultDisplayer extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -2954110439818779774L;

    public ResultDisplayer() {

    }

    public void displayQueryOutput(ArrayList<Pair<String, Object>> arrayList) {

    }

    public void displayTable(HashMap<?, ?> hm) {
        // get le type
        Class<?> type = hm.entrySet().iterator().next().getValue().getClass();

        Object laList[] = new Object[hm.size()];

        int i = 0;
        for (Object object : hm.values()) {
            laList[i] = object;
            ++i;
        }
        ListDisplayer lsd = new ListDisplayer(type, laList);

        this.add(lsd);
    }

}