package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.reflect.FieldUtils;

import app.database_manager.Utils;

/**
 * TablePicker
 */
public class TablePicker extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = -5611374534443360251L;

    private static Object theObject = null;
    private static TablePicker tp;

    public static Object pickObject(HashMap<Integer, Object> hm) {
        JPanel jp = new JPanel();
        if (!hm.isEmpty()) {
            Class<?> type = hm.entrySet().iterator().next().getValue().getClass();

            Object laList[] = new Object[hm.size()];

            int i = 0;
            for (Object object : hm.values()) {
                laList[i] = object;
                ++i;
            }

            tp = new TablePicker(jp, type, laList);
            tp.setVisible(true);
            return theObject;
        } else {
            JOptionPane.showMessageDialog(null, "La liste selectionnée est vide", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
    }

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

    public TablePicker(JPanel workingPane, Class<?> cls) {
        super("Selectionner un élement");

        workingPane.removeAll();
        workingPane.revalidate();
        workingPane.setLayout(new BorderLayout());
        JScrollPane jsp = new JScrollPane(jTable);
        jTable.setFillsViewportHeight(true);

        jTable.getTableHeader().setReorderingAllowed(false);

        workingPane.add(infoText, BorderLayout.NORTH);
        workingPane.add(jsp);
        JButton jb = new JButton("ok");
        jb.addActionListener(this);
        workingPane.add(jb, BorderLayout.SOUTH);
        model.addColumn("id");
        for (Field f : Utils.extractFields(cls)) {
            model.addColumn(Utils.normalizeCamelCase(f.getName()));
        }
        workingPane.repaint();
        this.pack();
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setContentPane(workingPane);

    }

    public TablePicker(JPanel workingPane, Class<?> cls, Object initial[]) {
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

    public Object getObject() {
        return theObject;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        int row = jTable.getSelectedRow();
        theObject = listElements.get(jTable.getValueAt(row, 0));
        this.setVisible(false);
    }
}