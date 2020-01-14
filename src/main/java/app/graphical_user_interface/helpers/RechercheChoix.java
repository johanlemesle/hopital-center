package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.database_manager.Utils;

/**
 * RechercheChoix
 */
public class RechercheChoix extends JPanel implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private JCheckBox parent;
    private Class<?> type;
    private String name;
    private JTextField txtField = null;
    private JFrame forChildren;

    private List<RechercheChoix> children = new ArrayList<>();

    public RechercheChoix(JCheckBox parent, Class<?> type, String name) {
        forChildren = new JFrame("Les enfants de " + name);
        forChildren.setSize(400, 400);
        this.parent = parent;
        this.type = type;
        this.name = name;

        final JPanel jPanel2 = new JPanel(new GridLayout(1, 2));
        parent.setText(this.name);
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
            if (!Utils.isStandardType(type)) {
                JPanel tmp = new JPanel();
                tmp.setLayout(new BoxLayout(tmp, BoxLayout.Y_AXIS));
                for (Field f : Utils.extractFields(type)) {
                    Class<?> cls;
                    String name = f.getName();
                    cls = Utils.getTypeFromMap(f);
                    RechercheChoix t = new RechercheChoix(new JCheckBox(), cls, name);
                    t.parent.setSelected(true);
                    children.add(t);
                    tmp.add(t);
                }
                forChildren.setContentPane(tmp);
                forChildren.setVisible(true);
            }

        } else {
            killChildren();
        }
    }

    public void killChildren() {
        for (RechercheChoix child : children) {
            if (child.children.size() > 0) {
                child.killChildren();
            } else {
                child.parent.setSelected(false);
                forChildren.setVisible(false);
            }
        }
        children.clear();
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
        for (RechercheChoix child : children) {
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

        // return txtField.getText();
    }

}