package app.graphical_user_interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) {
        Window w = new Window();
        w.createMenu();
        JTextPane textPane = new JTextPane();
        addColoredText(textPane, "bonjour", Color.BLACK);
        w.getContentPane().add(textPane);

        // theme.setBackground(Color.ORANGE);
    }

    public static void addColoredText(JTextPane pane, String text, Color color) {
        StyledDocument doc = pane.getStyledDocument();

        Style style = pane.addStyle("Color Style", null);
        StyleConstants.setForeground(style, color);
        try {
            doc.insertString(doc.getLength(), text, style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}