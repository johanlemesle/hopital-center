package app.graphical_user_interface.prompts;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.tuple.Pair;

/**
 * EntityInput
 */
public class EntityInput extends JPanel implements ActionListener {

    /**
     *
     */

    private static final long serialVersionUID = -8967610453616776864L;

    public EntityInput(final List<Pair<Field, Class<?>>> listPairs) {
        super(new GridLayout(5, listPairs.size() / 5));
        for (final Pair<Field, Class<?>> pair : listPairs) {
            final JPanel jPanel = new JPanel(new GridLayout(1, 2));
            final JLabel label = new JLabel(pair.getLeft().getName());
            jPanel.add(label);
            jPanel.add(new JTextField());
            this.add(jPanel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
}