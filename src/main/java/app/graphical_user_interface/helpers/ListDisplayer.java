package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.reflect.FieldUtils;

import app.database_manager.Utils;

/**
 * ListDisplayer
 */
public class ListDisplayer extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -7680091293928844911L;
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
    private JLabel infoText = new JLabel("Nombre d'elements : 0");

    private HashMap<Integer, Object> listElements = new HashMap<>();

    public ListDisplayer(Class<?> cls) {
        super(new BorderLayout());
        JScrollPane jsp = new JScrollPane(jTable);
        jTable.setFillsViewportHeight(true);
        jTable.getTableHeader().setReorderingAllowed(false);

        this.add(infoText, BorderLayout.NORTH);
        this.add(jsp);

        model.addColumn("id");
        for (Field f : Utils.extractFields(cls)) {
            model.addColumn(Utils.normalizeCamelCase(f.getName()));
        }
    }

    public ListDisplayer(Class<?> cls, Object initial[]) {
        this(cls);
        for (Object object : initial) {
            addRow(object);
        }
    }

    public void addRow(Object e) {
        List<Object> row = new ArrayList<>();
        row.add(e.hashCode());
        for (Field f : Utils.extractFields(e.getClass())) {
            try {
                Object obj = FieldUtils.readField(f, e, true);
                row.add(obj);
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }

        model.addRow(row.toArray());
        model.fireTableRowsInserted(jTable.getRowCount() - 1, jTable.getRowCount());

        listElements.put(e.hashCode(), e);
        infoText.setText("Nombre d'elements : " + jTable.getRowCount());

    }
}