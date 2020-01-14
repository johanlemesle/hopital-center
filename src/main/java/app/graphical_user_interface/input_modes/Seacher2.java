package app.graphical_user_interface.input_modes;

import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import app.graphical_user_interface.helpers.JCheckBoxTree;
import app.graphical_user_interface.helpers.MyDomainCheckBoxTree;

/**
 * Seacher2
 */
public class Seacher2 {

    public Seacher2(JPanel workingPane) {

        MyDomainCheckBoxTree m = new MyDomainCheckBoxTree(false, workingPane);
        

    }
}