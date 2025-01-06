package Enter2;

import javax.swing.*;
import java.awt.*;

public class NotesUI extends JPanel {

    private JTextField titleField;
    private JTextPane textPane;
    private DefaultListModel<String> noteListModel;
    private JList<String> noteList;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton newNoteButton;
    private JButton logoutButton;

    public NotesUI() {
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        noteListModel = new DefaultListModel<>();
        noteList = new JList<>(noteListModel);
        JScrollPane scrollPane = new JScrollPane(noteList);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        titleField = new JTextField();
        rightPanel.add(titleField, BorderLayout.NORTH);
        textPane = new JTextPane();
        textPane.setContentType("text/html");
        JScrollPane noteScrollPane = new JScrollPane(textPane);
        rightPanel.add(noteScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Kaydet");
        deleteButton = new JButton("Sil");
        newNoteButton = new JButton("Yeni Not");
        logoutButton = new JButton("Oturumu Kapat");

        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(newNoteButton);
        buttonPanel.add(logoutButton);

        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(200);
        add(splitPane, BorderLayout.CENTER);
    }

    public JTextField getTitleField() {
        return titleField;
    }

    public JTextPane getTextPane() {
        return textPane;
    }

    public DefaultListModel<String> getNoteListModel() {
        return noteListModel;
    }

    public JList<String> getNoteList() {
        return noteList;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getNewNoteButton() {
        return newNoteButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }
}
