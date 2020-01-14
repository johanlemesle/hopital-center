package app.graphical_user_interface;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.lang3.tuple.Pair;

import app.database_manager.Utils;
import app.graphical_user_interface.helpers.ListDisplayer;
import app.graphical_user_interface.helpers.TableDeleter;
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
        super(new FlowLayout(FlowLayout.CENTER, 0, 0));
    }

    public void displayQueryOutput(ArrayList<Pair<String, Object>> arrayList) {
        this.removeAll();
        this.revalidate();
        QueryResultDisplayer qrd = new QueryResultDisplayer(this, arrayList);
        this.repaint();
    }

    public void displayTable(HashMap<?, ?> hm) {

        // get le type
        if (!hm.isEmpty()) {
            Class<?> type = hm.entrySet().iterator().next().getValue().getClass();

            Object laList[] = new Object[hm.size()];

            int i = 0;
            for (Object object : hm.values()) {
                laList[i] = object;
                ++i;
            }
            ListDisplayer lsd = new ListDisplayer(this, type, laList);
        } else {
            this.removeAll();
            this.revalidate();
            JOptionPane.showMessageDialog(this, "La liste selectionnée est vide", "Info", JOptionPane.INFORMATION_MESSAGE);
            this.repaint();
        }
    }

    public void displayTable_delete(HashMap<?, ?> hm) {

        // get le type
        if (!hm.isEmpty()) {
            Class<?> type = hm.entrySet().iterator().next().getValue().getClass();

            Object laList[] = new Object[hm.size()];

            int i = 0;
            for (Object object : hm.values()) {
                laList[i] = object;
                ++i;
            }
            TableDeleter td = new TableDeleter(this, type, laList);
        } else {
            this.removeAll();
            this.revalidate();
            JOptionPane.showMessageDialog(this, "La liste selectionnée est vide", "Info", JOptionPane.INFORMATION_MESSAGE);
            this.repaint();
        }
    }

}