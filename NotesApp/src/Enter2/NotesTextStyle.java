package Enter2;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class NotesTextStyle {

    private JTextPane textPane;

    public NotesTextStyle(JTextPane textPane) {
        this.textPane = textPane;
    }

    public void applyStyle(String style) {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet attr = new SimpleAttributeSet();

        switch (style) {
            case "bold":
                StyleConstants.setBold(attr, true);
                break;
            case "italic":
                StyleConstants.setItalic(attr, true);
                break;
            case "underline":
                StyleConstants.setUnderline(attr, true);
                break;
        }
        textPane.setCharacterAttributes(attr, false);
    }

    public void applyFont(String fontName) {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attr, fontName);
        textPane.setCharacterAttributes(attr, false);
    }

    public void applyFontSize(int size) {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setFontSize(attr, size);
        textPane.setCharacterAttributes(attr, false);
    }

    public void applyColor(Color color) {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setForeground(attr, color);
        textPane.setCharacterAttributes(attr, false);
    }
}
