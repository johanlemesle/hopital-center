package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

import app.App;
import app.database_manager.Utils;

/**
 * ListDisplayer
 */
public class TableDeleter {

    /**
     *
     */
    private DefaultTableModel model=new DefaultTableModel(){
    /**
     *
     */
    private static final long serialVersionUID=-8146084245073453982L;

    /**
     *
     */

    @Override public boolean isCellEditable(int row,int column){return false;}};
    private JTable jTable = new JTable(model);
    private JLabel infoText = new JLabel("Nombre d'elements : 0");

    private HashMap<Integer, Object> listElements = new HashMap<>();

    public TableDeleter(JPanel workingPane, Class<?> cls) {
        workingPane.removeAll();
        workingPane.revalidate();
        workingPane.setLayout(new BorderLayout());
        JScrollPane jsp = new JScrollPane(jTable);
        jTable.setFillsViewportHeight(true);
        jTable.addKeyListener(new KeyListener(){
        
            @Override
            public void keyTyped(KeyEvent e) {
            }
        
            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }
        
            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    int row = jTable.getSelectedRow();
                    Object obj = listElements.get(jTable.getValueAt(row, 0));
                    App.hopital.delete(obj.getClass().getSimpleName(), obj);
                    model.removeRow(row);
                    model.fireTableDataChanged();
                    infoText.setText("Nombre d'elements : " + jTable.getRowCount());
                 }
                
            }
        });
        jTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });jTable.getTableHeader().setReorderingAllowed(false);
        

    workingPane.add(infoText,BorderLayout.NORTH);workingPane.add(jsp);

    model.addColumn("id");for(

    Field f:Utils.extractFields(cls))
    {
        model.addColumn(Utils.normalizeCamelCase(f.getName()));
    }workingPane.repaint();

    }

    public TableDeleter(JPanel workingPane, Class<?> cls, Object initial[]) {
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

    }
}