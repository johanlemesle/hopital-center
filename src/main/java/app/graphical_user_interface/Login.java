package app.graphical_user_interface;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.lang3.tuple.Pair;

public class Login extends JFrame implements ActionListener 
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    JPanel panel;
    JLabel user_label, password_label,message;
    JTextField userNameField;
    JPasswordField userPasswordField;
    JButton valider,annuler;



   // identifiants.add("admin","admin");
  
    Login()
    {
        //On s'occupe de l'utilisateur 
        user_label = new JLabel();
        user_label.setText("Nom d'utilisateur : ");
        userNameField = new JTextField();

        //On s'occupe du mot de passe 
        password_label = new JLabel();
        password_label.setText("Mot de passe : ");
        userPasswordField = new JPasswordField();
        
        JButton valider = new JButton("VALIDER");
        JPanel panel = new JPanel(new GridLayout(3,1));

        //On ajoute au panel les éléments créés 

        panel.add(user_label);
        panel.add(userNameField);
        panel.add(password_label);
        panel.add(userPasswordField);

        message = new JLabel();
        panel.add(message);
        panel.add(valider);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Listeners 
        valider.addActionListener(this);
        add(panel,BorderLayout.CENTER); //On ajoute au screen le panel en position centrale
        setSize(260, 100);
        setTitle("Identifiez vous : ");
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        // TODO Auto-generated method stub
        //Check si les identifiants sont corrects 

        String username = userNameField.getText();
        String password = userPasswordField.getText();

        if (username.trim().equals("admin") && password.trim().equals("admin"))
            message.setText("<html><font color='green'>Bonjour</font></html>");
        else
            message.setText("<html><font color='red'>Incorrect</font></html>");
    }


    public static void main(String[] args) {
        new Login();
    }



    

}