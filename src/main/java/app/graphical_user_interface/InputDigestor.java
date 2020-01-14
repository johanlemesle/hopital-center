package app.graphical_user_interface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import app.App;
import app.Hopital;
import app.graphical_user_interface.helpers.ListDialog;
import app.graphical_user_interface.input_modes.EntityBuilder;
import app.graphical_user_interface.input_modes.Modifier;

/**
 * InputDigester
 */
public class InputDigestor extends JPanel implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = -4206706321166972386L;
    private final JPanel contentPane = new JPanel();
    private final JButton executeButton = new JButton("Executer");

    private EntityBuilder adder;
    // private Searcher2 query;
    private Modifier updater;

    // private Adder adder;
    // private Searcher2 searcher;

    private Window parentWindow;

    public InputDigestor(Window parentWindow) {
        super(new BorderLayout());
        this.add(contentPane);
        this.add(executeButton, BorderLayout.SOUTH);
        executeButton.addActionListener(this);
        this.parentWindow = parentWindow;
    }

    public void addMode() {
        final HashMap<String, Class<?>> possibleVMap = new HashMap<>();
        final String possibleValues[] = new String[EntityBuilder.ENTITIES.length];
        int i = 0;
        for (final Class<?> cl : EntityBuilder.ENTITIES) {
            possibleValues[i] = cl.getSimpleName();
            possibleVMap.put(possibleValues[i], cl);
            i++;
        }
        String input = ListDialog.showDialog(this, this, "Selectionnez le type d'entité que vous souhaitez ajouter",
                "Selection entité", possibleValues, null, null);
        if (input != null) {
            contentPane.removeAll();
            contentPane.revalidate();
            adder = new EntityBuilder(contentPane, possibleVMap.get(input));
            contentPane.repaint();
        }

    }

    public void searchMode() {
        contentPane.removeAll();
        contentPane.revalidate();
        // Searcher query = new Searcher(new JCheckBox(), Hopital.class, "hopital");
        // Searcher2 query = new Searcher2(Hopital.class, "hopital");
        JScrollPane s = new JScrollPane();
        // s.getViewport().add(query);
        this.add(s, BorderLayout.CENTER);
        // contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        // contentPane.add(query);
        contentPane.repaint();
    }

    public void updateMode() {
        contentPane.removeAll();
        contentPane.revalidate();
        updater = new Modifier(contentPane);
        contentPane.repaint();
    }

    public void reportMode() {
        PieChartTest.tst(App.hopital);
    }

    // catch l'exec
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (App.window.ACTION_MODE) {
        case ADD_MODE:
            try {
                Object obj = adder.buildEntity();
                App.hopital.insert(obj.getClass().getSimpleName(), obj);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                    | InstantiationException e1) {
                e1.printStackTrace();
            }
            break;
        case QUERY_MODE:
            // String str = query.buildQuery();
            // System.out.println("la requete : " + str);
            // parentWindow.displayQueryResult(str);
            break;
        case UPDATE_MODE:
            parentWindow.displayTable(updater.getLaMap());
            break;
        case REPORT_MODE:
            //
            break;
        default:
            break;
        }
    }
}