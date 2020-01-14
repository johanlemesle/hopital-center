package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.awt.Component;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import app.Hopital;
import app.database_manager.Utils;

public class MyDomainCheckBoxTree {

    HashSet<TreePath> includedPaths = new HashSet<>();
    HashSet<TreePath> excludedPaths = new HashSet<>();
    TreeModel treeModel;

    public MyDomainCheckBoxTree(boolean testDefault, JPanel workingPane) {
        workingPane.setLayout(new BorderLayout());

        final JCheckBoxTree cbt;
        if (testDefault) {
            treeModel = null;
            cbt = new JCheckBoxTree();
        } else {
            treeModel = buildModel();
            LazyCheckBoxCellRenderer treeCellRenderer = new LazyCheckBoxCellRenderer();
            cbt = new JCheckBoxTree(treeModel, null, treeCellRenderer);
            treeCellRenderer.setCheckBoxTree(cbt);
            cbt.addTreeExpansionListener(new NodeExpansionListener());
        }

        JScrollPane s = new JScrollPane();
        s.getViewport().add(cbt);
        workingPane.add(s, BorderLayout.CENTER);

        // this.getContentPane().add(cbt);

        cbt.addCheckChangeEventListener(new JCheckBoxTree.CheckChangeEventListener() {
            public void checkStateChanged(JCheckBoxTree.CheckChangeEvent event) {
                updatePaths(cbt, event);
                // For Debugging (correctness and laziness)
                System.out.println("\n========== Current State ========");
                System.out.println("+ + + Included Paths: ");
                printPaths(includedPaths);
                System.out.println("- - - Excluded Paths: ");
                printPaths(excludedPaths);
                System.out.println("Size of node-checkState cache = " + cbt.nodesCheckingState.size());
                //
            }
        });
    }

    // (As prelude)Purges any of clickedPath's children from the 2 path-sets.
    // Then adds/removes clickedPath from the 2 path-sets if appropriate.
    protected void updatePaths(JCheckBoxTree cbt, JCheckBoxTree.CheckChangeEvent event) {
        boolean parentAlreadyIncluded = false;
        boolean parentAlreadyExcluded = false;
        TreePath clickedPath = (TreePath) event.getSource();
        HashSet<TreePath> toBeRemoved = new HashSet<>();

        // When a node is included/excluded, its children are implied as
        // included/excluded.
        // Note: The direct-parent check is needed to avoid problem if immediate-parent
        // is excluded
        // but grand-father/higher-ancestor is included
        for (TreePath exp : excludedPaths) {
            if (clickedPath.isDescendant(exp)) // exp is descended from clickedPath
                toBeRemoved.add(exp);
            if (isParent(exp, clickedPath)) // clickedPath is child of exp
                parentAlreadyExcluded = true;
        }
        excludedPaths.removeAll(toBeRemoved);
        toBeRemoved.clear();

        for (TreePath inp : includedPaths) {
            if (clickedPath.isDescendant(inp)) // inp is descended from clickedPath
                toBeRemoved.add(inp);
            if (isParent(inp, clickedPath)) // clickedPath is child of inp
                parentAlreadyIncluded = true;
        }
        includedPaths.removeAll(toBeRemoved);
        toBeRemoved.clear();

        // Now add/remove clickedPath from the path-sets as appropriate
        if (cbt.getCheckMode(clickedPath)) { // selected => to be included
            if (!parentAlreadyIncluded)
                includedPaths.add(clickedPath);
            excludedPaths.remove(clickedPath);
        } else { // deselected => to be excluded
            if (!parentAlreadyExcluded)
                excludedPaths.add(clickedPath);
            includedPaths.remove(clickedPath);
        }
    }

    // returns true if aPath is immediate parent of bPath; both must be non-null
    protected boolean isParent(TreePath aPath, TreePath bPath) {
        return aPath.equals(bPath.getParentPath());
    }

    protected void printPaths(HashSet<TreePath> pathSet) {
        TreePath[] paths = pathSet.toArray(new TreePath[pathSet.size()]);
        for (TreePath tp : paths) {
            for (Object pathPart : tp.getPath()) {
                System.out.print(pathPart + ",");
            }
            System.out.println();
        }
    }

    private class LazyCheckBoxCellRenderer extends JPanel implements TreeCellRenderer {
        /**
         *
         */
        private static final long serialVersionUID = -1463615185786561018L;
        JCheckBoxTree cbt;
        JCheckBox checkBox;

        public LazyCheckBoxCellRenderer() {
            super(new BorderLayout());
            checkBox = new JCheckBox();
            add(checkBox, BorderLayout.CENTER);
            setOpaque(false);
        }

        public void setCheckBoxTree(JCheckBoxTree someCbt) {
            cbt = someCbt;
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                boolean leaf, int row, boolean hasFocus) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object obj = node.getUserObject();

            checkBox.setText(obj.toString());

            if (obj instanceof Boolean)
                checkBox.setText("Retrieving data...");
            else {
                TreePath tp = new TreePath(node.getPath());
                JCheckBoxTree.CheckedNode cn = null;
                if (cbt != null)
                    cn = cbt.getCheckedNode(tp);
                if (cn == null) {
                    return this;
                }
                checkBox.setSelected(cn.isSelected);
                checkBox.setText(obj.toString());
                checkBox.setOpaque(cn.isSelected && cn.hasChildren && !cn.allChildrenSelected);
            }
            return this;
        }
    }

    // Make sure expansion is threaded and updating the tree model
    // only occurs within the event dispatching thread.
    class NodeExpansionListener implements TreeExpansionListener {
        public void treeExpanded(TreeExpansionEvent event) {
            final DefaultMutableTreeNode node = JCheckBoxTree.getTreeNode(event.getPath());
            Object obj = node.getUserObject();

            // Expand by adding any children nodes
            Thread runner = new Thread() {
                public void run() {
                    if (obj != null && ((MyDomainObject) obj).expand(node)) {
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

    // ====================== Your Domain specific stuff goes here

    protected TreeModel buildModel() {
        DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("Hopital");
        DefaultMutableTreeNode node;

        for (Field field : Utils.extractFields(Hopital.class)) {
            MyDomainObject d = new MyDomainObject(Utils.getTypeFromMap(field), field.getName());
            d.hasChildren = true;
            node = new DefaultMutableTreeNode(d);

            topNode.add(node);
            node.add(new DefaultMutableTreeNode(true));
        }

        return new DefaultTreeModel(topNode);
    }

    // sample impl of a domain-object; should have expand method
    class MyDomainObject {
        protected Class<?> type;
        protected String name;
        protected boolean hasChildren;

        public MyDomainObject(Class<?> type, String name) {
            this.type = type;
            this.name = name;
            hasChildren = !Utils.isStandardType(type);
        }

        // Expand the tree at parent node and add nodes.
        public boolean expand(DefaultMutableTreeNode parent) {
            DefaultMutableTreeNode flagNode = (DefaultMutableTreeNode) parent.getFirstChild();
            if (flagNode == null) // No flag
                return false;
            Object obj = flagNode.getUserObject();
            if (!(obj instanceof Boolean))
                return false; // Already expanded

            parent.removeAllChildren(); // Remove FlagNode

            Field[] children = getChildren();
            if (children == null)
                return true;

            // Create a sorted list of domain-objects
            ArrayList<Object> sortedChildDomainObjects = new ArrayList<>();

            for (Field child : children) {
                MyDomainObject newNode = new MyDomainObject(child.getType(), child.getName());
                // System.out.println("Size of arraylist=" + sortedChildDomainObjects.size());
                boolean isAdded = false;
                for (int i = 0; i < sortedChildDomainObjects.size(); i++) {
                    MyDomainObject nd = (MyDomainObject) sortedChildDomainObjects.get(i);
                    if (newNode.compareTo(nd) < 0) {
                        sortedChildDomainObjects.add(i, newNode);
                        isAdded = true;
                        break;
                    }
                }
                if (!isAdded)
                    sortedChildDomainObjects.add(newNode);
            }

            // Add children nodes under parent in the tree
            for (Object aChild : sortedChildDomainObjects) {
                MyDomainObject nd = (MyDomainObject) aChild;
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(nd);
                parent.add(node);

                if (nd.hasChildren)
                    node.add(new DefaultMutableTreeNode(true));
            }

            return true;
        }

        private int compareTo(MyDomainObject toCompare) {
            assert toCompare.type != null;
            return type.toString().compareToIgnoreCase(toCompare.type.toString());
        }

        // should be Domain specific; dummy impl provided
        private Field[] getChildren() {
            if (type == null || (!hasChildren))
                return null;

            List<Field> tmp = Utils.extractFields(type);
            Field children[] = new Field[tmp.size()];
            for (int i = 0; i < children.length; i++) {
                children[i] = tmp.get(i);
            }
            return children;
        }

        public String toString() {
            return type != null ? name : "(EMPTY)";
        }
    }
}