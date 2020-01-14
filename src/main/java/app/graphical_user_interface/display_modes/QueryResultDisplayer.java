package app.graphical_user_interface.display_modes;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.tuple.Pair;

/**
 * SearchDisplayer
 */
public class QueryResultDisplayer {

    private DefaultTableModel model = new DefaultTableModel() {
        /**
         *
         */
        private static final long serialVersionUID = 4291712734077871213L;

        @Override
        public boolean isCellEditable(int row, int column) {

            // Only the third column
            return false;
        }
    };
    private JTable jTable = new JTable(model);

    public QueryResultDisplayer(JPanel workingPane, ArrayList<Pair<String, Object>> arrayList) {
        JScrollPane jsp = new JScrollPane(jTable);
        jTable.setFillsViewportHeight(true);

        List<Pair<String, List<Object>>> theTables = new ArrayList<>();
        getColumns(arrayList, theTables);
        for (Pair<String, List<Object>> pair : theTables) {
            model.addColumn(pair.getLeft(), pair.getRight().toArray());
        }
        model.fireTableDataChanged();

        workingPane.setLayout(new BorderLayout());
        workingPane.add(new JLabel("Nombre d'éléments : " + jTable.getRowCount()), BorderLayout.NORTH);
        workingPane.add(jsp);
    }

    public static void getColumns(ArrayList<Pair<String, Object>> source, List<Pair<String, List<Object>>> target) {
        for (Pair<String, Object> pair : source) {
            if (pair.getRight() instanceof Collection<?>) {
                ArrayList<Object> al = new ArrayList<>((Collection<?>) pair.getRight());
                if (!al.isEmpty()) {
                    ArrayList<Pair<String, Object>> nextDepth = new ArrayList<>();
                    for (Object obj : al) {
                        if (obj instanceof Pair<?, ?>) {
                            Pair<?, ?> toAdd = (Pair<?, ?>) obj;
                            if (toAdd.getLeft() instanceof String) {
                                nextDepth.add(Pair.of((String) toAdd.getLeft(), toAdd.getRight()));
                            }
                        }
                    }
                    if (nextDepth.get(0).getRight() instanceof Collection<?>) {
                        getColumns(nextDepth, target);
                    } else {
                        target.add(Pair.of(pair.getLeft(), al));
                    }
                }
            }
        }
    }
}