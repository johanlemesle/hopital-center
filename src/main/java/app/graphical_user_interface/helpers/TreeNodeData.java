package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import app.database_manager.Utils;

/**
 * Toto
 */
public class TreeNodeData extends DefaultMutableTreeNode {

    /**
     *
     */
    private static final long serialVersionUID = -405109346423065534L;

    private boolean isSelected = false;
    private Class<?> type;
    private String name;
    private String condition;

    private List<TreeNodeData> children = new ArrayList<>();

    public TreeNodeData(Class<?> type, String name) {
        this.type = type;
        this.name = name;
        this.condition = "";
    }

    public TreeNodeData[] expand() {
        if (!Utils.isStandardType(type)) {
            List<Field> fields = Utils.extractFields(type);
            TreeNodeData tndatas[] = new TreeNodeData[fields.size()];
            int i = 0;
            for (Field f : Utils.extractFields(type)) {
                Class<?> cls;
                String name = f.getName();
                cls = Utils.getTypeFromMap(f);
                TreeNodeData t = new TreeNodeData(cls, name);
                children.add(t);
                this.add(t);
                tndatas[i++] = t;
            }
            return tndatas;
        }
        return null;
    }

    // requete : infirmiers:salaire>2000:{nom&prenom}&docteurs:nom%NSM:{*}

    public String buildQuery() {
        String conds = "";
        String condQuerry = "";
        List<String> lesConds = new ArrayList<>();
        List<String> lesStrings = new ArrayList<>();
        for (TreeNodeData child : children) {
            lesStrings.add(child.buildQuery());
            if (!condition.isEmpty()) {
                // cond = ":" + child.name + tmp + ":";
                conds = child.name + condition;
                System.out.println("condition : " + conds);
                lesConds.add(conds);
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean b) {
        isSelected = b;
    }

    public String getName() {
        return name;
    }

    public List<TreeNodeData> getChildren() {
        return children;
    }
}