package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
public class ListDisplayer {

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
            return false;
        }
    };
    private JTable jTable = new JTable(model);
    private JLabel infoText = new JLabel("Nombre d'elements : 0");

    private HashMap<Integer, Object> listElements = new HashMap<>();

    public ListDisplayer(JPanel workingPane, Class<?> cls) {
        workingPane.removeAll();
        workingPane.revalidate();
        workingPane.setLayout(new BorderLayout());
        JScrollPane jsp = new JScrollPane(jTable);
        jTable.setFillsViewportHeight(true);
        jTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = jTable.getSelectedRow();
                    Object obj = listElements.get(jTable.getValueAt(row, 0));
                    EntityInputWindow euw = new EntityInputWindow(obj);
                    euw.setVisible(true);
                }
            }
        });

        workingPane.add(infoText, BorderLayout.NORTH);
        workingPane.add(jsp);

        model.addColumn("id");
        for (Field f : Utils.extractFields(cls)) {
            model.addColumn(Utils.normalizeCamelCase(f.getName()));
        }
        workingPane.repaint();

    }

    public ListDisplayer(JPanel workingPane, Class<?> cls, Object initial[]) {
        this(workingPane, cls);
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