package app.graphical_user_interface.input_modes;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import app.database_manager.Utils;

/**
 * Toto
 */
public class Searcher extends JPanel implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = -405109346423065534L;

    private JCheckBox parent;
    private Class<?> type;
    private String name;
    private JTextField txtField = null;

    private List<Searcher> children = new ArrayList<>();
    private Box box = Box.createVerticalBox();

    public Searcher(JCheckBox parent, Class<?> type, String name) {
        super(new BorderLayout());
        this.parent = parent;
        this.type = type;
        this.name = name;

        final JLabel jl2 = new JLabel(this.name);
        final JPanel jPanel2 = new JPanel(new GridLayout(1, 2));
        jPanel2.add(jl2);
        jPanel2.add(parent);
        if (Utils.isStandardType(type)) {
            txtField = new JTextField();
            txtField.setColumns(10);
            jPanel2.add(txtField);
        }
        this.parent.addActionListener(this);

        this.add(jPanel2);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JCheckBox cbx = (JCheckBox) e.getSource();

        System.out.println("je suis la");
        if (cbx.isSelected()) {
            if (!Utils.isStandardType(type))
                for (Field f : Utils.extractFields(type)) {
                    Class<?> cls;
                    String name = f.getName();
                    cls = Utils.getTypeFromMap(f);
                    Searcher t = new Searcher(new JCheckBox(), cls, name);
                    t.parent.setSelected(true);
                    children.add(t);
                    box.add(t);
                }

            this.add(new JScrollPane(box), BorderLayout.EAST);
        } else {
            for (Searcher toto : children) {
                box.remove(toto);
            }
            children.clear();
        }

        for (Searcher query : children) {
            query.revalidate();
            query.repaint();
        }
        this.revalidate();
        this.repaint();
    }

    public boolean isSelected() {
        return parent.isSelected();
    }

    // requete : infirmiers:salaire>2000:{nom&prenom}&docteurs:nom%NSM:{*}

    public String buildQuery() {
        String conds = "";
        String condQuerry = "";
        List<String> lesConds = new ArrayList<>();
        List<String> lesStrings = new ArrayList<>();
        for (Searcher child : children) {
            if (child.isSelected()) {
                lesStrings.add(child.buildQuery());
                if (child.txtField != null) {
                    String tmp = child.txtField.getText();
                    if (!tmp.isEmpty()) {
                        // cond = ":" + child.name + tmp + ":";
                        conds = child.name + tmp;
                        System.out.println("condition : " + conds);
                        lesConds.add(conds);
                    }
                }
            }
        }

        condQuerry = ":" + String.join("&", lesConds) + ":";

        if (lesStrings.isEmpty() && lesConds.isEmpty()) {
            return name;
        } else if (lesStrings.isEmpty() && !lesConds.isEmpty()) {
            return name + condQuerry;
        } else if (!lesStrings.isEmpty() && lesConds.isEmpty()) {
            return name + "{" + String.join("&", lesStrings) + "}";
        } else
            return name + condQuerry + "{" + String.join("&", lesStrings) + "}";
    }

}