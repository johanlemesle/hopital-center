package app.graphical_user_interface;

import java.util.ArrayList;

import javax.swing.JPanel;

import org.apache.commons.lang3.tuple.Pair;

import app.graphical_user_interface.display_modes.QueryResultDisplayer;

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
        QueryResultDisplayer qrd = new QueryResultDisplayer(this, arrayList);

    }

}