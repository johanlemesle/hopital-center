package app.graphical_user_interface.input_modes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import app.Hopital;
import app.database_manager.Utils;
import app.graphical_user_interface.helpers.TreeNodeData;

/**
 * Seacher2
 */
public class Searcher2 extends JTree {

    /**
     *
     */
    private static final long serialVersionUID = 7272210466424999777L;

    private TreeNodeData entry;
    private HashMap<TreePath, TreeNodeData> nodesState = new HashMap<>();

    private Searcher2 selfPointer = this;

    public Searcher2(Class<?> type, String name) {
        super(Utils.extractFields(type).toArray());
        entry = new TreeNodeData(type, name);

        for (TreeNodeData tnd : entry.expand()) {
            nodesState.put(new TreePath(tnd.getPath()), tnd);
        }

        super.setModel(new DefaultTreeModel(entry));
        this.setToggleClickCount(0);
        this.setCellRenderer(new CheckBoxCellRenderer());

        // Overriding selection model by an empty one
        DefaultTreeSelectionModel dtsm = new DefaultTreeSelectionModel() {
            private static final long serialVersionUID = -8190634240451667286L;

            // Totally disabling the selection mechanism
            public void setSelectionPath(TreePath path) {
            }

            public void addSelectionPath(TreePath path) {
            }

            public void removeSelectionPath(TreePath path) {
            }

            public void setSelectionPaths(TreePath[] pPaths) {
            }
        };
        // Calling checking mechanism on mouse click
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent arg0) {
                TreePath tp = selfPointer.getPathForLocation(arg0.getX(), arg0.getY());
                if (tp == null) {
                    return;
                }
                boolean checkMode = !nodesState.get(tp).isSelected();
                checkSubTree(tp, checkMode);
                // Firing the check change event
                fireCheckChangeEvent();
                // Repainting tree after the data structures were updated
                selfPointer.repaint();
            }

            public void mouseEntered(MouseEvent arg0) {
            }

            public void mouseExited(MouseEvent arg0) {
            }

            public void mousePressed(MouseEvent arg0) {
            }

            public void mouseReleased(MouseEvent arg0) {
            }
        });
        this.setSelectionModel(dtsm);
    }

    protected void checkSubTree(TreePath tp, boolean check) {
        TreeNodeData cn = nodesState.get(tp);
        cn.setSelected(check);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
        for (int i = 0; i < node.getChildCount(); i++) {
            checkSubTree(tp.pathByAddingChild(node.getChildAt(i)), check);
        }
    }

    protected void fireCheckChangeEvent() {

    }

    private class CheckBoxCellRenderer extends JPanel implements TreeCellRenderer {
        private static final long serialVersionUID = -7341833835878991719L;
        JCheckBox checkBox;

        public CheckBoxCellRenderer() {
            super();
            this.setLayout(new BorderLayout());
            checkBox = new JCheckBox();
            add(checkBox, BorderLayout.CENTER);
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                boolean leaf, int row, boolean hasFocus) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            TreePath tp = new TreePath(node.getPath());
            TreeNodeData cn = nodesState.get(tp);
            if (cn == null) {
                return this;
            }
            checkBox.setSelected(cn.isSelected());
            checkBox.setText(cn.getName());
            return this;
        }
    }

    class NodeExpansionListener implements TreeExpansionListener {
        public void treeExpanded(TreeExpansionEvent event) {
            final TreeNodeData node = nodesState.get(event.getPath());

            // Expand by adding any children nodes
            Thread runner = new Thread() {
                public void run() {
                    if (node != null && node.expand() != null) {
                        Runnable runnable = new Runnable() {
                            public void run() {
                                ((DefaultTreeModel) treeModel).reload(node);
                            }
                        };
                        SwingUtilities.invokeLater(runnable);
                    }
                }
            };
            runner.start();
        }

        public void treeCollapsed(TreeExpansionEvent event) {
        }
    }

    public String buildQuery() {
        return "";
    }

}
