package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.reflect.FieldUtils;

/**
 * ListDisplayer
 */
public class ListDisplayer extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -7680091293928844911L;
    private DefaultTableModel model = new DefaultTableModel();
    private JTable jTable = new JTable(model);
    private JLabel infoText = new JLabel("Nombre d'elements : 0");

    public ListDisplayer(Class<?> cls) {
        super(new BorderLayout());
        JScrollPane jsp = new JScrollPane(jTable);
        jTable.setFillsViewportHeight(true);

        this.add(infoText, BorderLayout.NORTH);
        this.add(jsp);

        model.addColumn("id");
        for (Field f : FieldUtils.getAllFields(cls)) {
            model.addColumn(f.getName());
        }

    }

    public ListDisplayer(Object initial[], Class<?> cls) {
        this(cls);
        for (Object object : initial) {
            addRow(object);
        }
    }

    public void addRow(Object e) {
        List<Object> row = new ArrayList<>();
        row.add(e.hashCode());
        for (Field f : FieldUtils.getAllFieldsList(e.getClass())) {
            try {
                Object obj = FieldUtils.readField(f, e, true);
                row.add(obj);
            } catch (IllegalAccessException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        model.addRow(row.toArray());
        // model.fireTableDataChanged();
        model.fireTableRowsInserted(jTable.getRowCount() - 1, jTable.getRowCount());
        // jTable.getRowCount());

        infoText.setText("Nombre d'elements : " + jTable.getRowCount());

    }
}