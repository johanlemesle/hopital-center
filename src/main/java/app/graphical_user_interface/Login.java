package app.graphical_user_interface;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.lang3.tuple.Pair;

import app.App;

public class Login extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    JPanel panel;
    JLabel user_label, password_label, message;
    JTextField userNameField;
    JPasswordField userPasswordField;
    JButton valider, annuler;
    static List<Pair<String, String>> users = new ArrayList<Pair<String, String>>();

    // identifiants.add("admin","admin");

    public Login() {
        // On s'occupe de l'utilisateur
        user_label = new JLabel();
        user_label.setText("Nom d'utilisateur : ");
        userNameField = new JTextField();

        // On s'occupe du mot de passe
        password_label = new JLabel();
        password_label.setText("Mot de passe : ");
        userPasswordField = new JPasswordField();

        final JButton valider = new JButton("VALIDER");
        final JPanel panel = new JPanel(new GridLayout(3, 1));

        // On ajoute au panel les éléments créés

        panel.add(user_label);
        panel.add(userNameField);
        panel.add(password_label);
        panel.add(userPasswordField);

        message = new JLabel();
        panel.add(message);
        panel.add(valider);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Listeners
        valider.addActionListener(this);
        add(panel, BorderLayout.CENTER); // On ajoute au screen le panel en position centrale
        setSize(260, 100);
        setTitle("Identifiez vous : ");
        setVisible(true);

    }

    public static void initUsers() {
        List<String> logins = new ArrayList<>();
        String u1 = "johan";
        String u2 = "walid";
        String u3 = "redoine";

        logins.add(u1);
        logins.add(u2);
        logins.add(u3);

        String pass_default = "1234";

        for (String string : logins) {
            Pair<String, String> p = Pair.of(string, pass_default);
            users.add(p);
        }
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        // TODO Auto-generated method stub
        // Check si les identifiants sont corrects

        final String username = userNameField.getText();
        final String password = userPasswordField.getText();
        if (username.trim().equals(users.get(0).getLeft()) && password.trim().equals(users.get(0).getRight())) {
            message.setText("<html><font color='green'>Bonjour</font></html>" + users.get(0).getLeft());
            App.launch();
            this.setVisible(false);
        } else if (username.trim().equals(users.get(1).getLeft()) && password.trim().equals(users.get(1).getRight())){
            message.setText("<html><font color='green'>Bonjour</font></html>" + users.get(1).getLeft());
            App.launch();
            this.setVisible(false);
        }
        else if (username.trim().equals(users.get(2).getLeft()) && password.trim().equals(users.get(2).getRight())){
            message.setText("<html><font color='green'>Bonjour</font></html>" + users.get(2).getLeft());
            App.launch();
            this.setVisible(false);
        }
        else
            message.setText("<html><font color='red'>Incorrect</font></html>");
        
    }

}