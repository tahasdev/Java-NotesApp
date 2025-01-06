package Enter2;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NotesArea extends JPanel {

    private int currentUserId;
    private String currentUsername;
    private NotesDB notesDB;
    private NotesUI notesUI;
    private NotesTextStyle notesTextStyle;

    public NotesArea(int userId, String username) {
        this.currentUserId = userId;
        this.currentUsername = username;
        this.notesDB = new NotesDB(userId);
        this.notesUI = new NotesUI();
        notesUI.getNewNoteButton().setBackground(new Color(255, 255, 255));
        notesUI.getDeleteButton().setBackground(new Color(255, 255, 255));
        notesUI.getSaveButton().setBackground(new Color(255, 255, 255));
        this.notesTextStyle = new NotesTextStyle(notesUI.getTextPane());

        notesDB.createNotesTable();
        loadSavedNotes();

        setLayout(new BorderLayout());
        add(notesUI, BorderLayout.CENTER);

        notesUI.getNoteList().addListSelectionListener(e -> loadNoteContent());
        notesUI.getSaveButton().addActionListener(e -> saveNote());
        notesUI.getDeleteButton().addActionListener(e -> deleteNote());
        notesUI.getNewNoteButton().addActionListener(e -> createNewNote());
        notesUI.getLogoutButton().addActionListener(e -> logout());

        add(createFormattingPanel(), BorderLayout.NORTH);
    }

    private JPanel createFormattingPanel() {
        JPanel formattingPanel = new JPanel();

        // Yazı tipi seçimi
        JComboBox<String> fontComboBox = new JComboBox<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        fontComboBox.addActionListener(e -> notesTextStyle.applyFont((String) fontComboBox.getSelectedItem()));
        formattingPanel.add(new JLabel("Font:"));
        formattingPanel.add(fontComboBox);

        // Yazı boyutu seçimi
        JComboBox<Integer> sizeComboBox = new JComboBox<>(new Integer[]{10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 40});
        sizeComboBox.addActionListener(e -> notesTextStyle.applyFontSize((Integer) sizeComboBox.getSelectedItem()));
        formattingPanel.add(new JLabel("Punto:"));
        formattingPanel.add(sizeComboBox);

        // Kalın, italik, altı çizili butonları
        JButton boldButton = new JButton("B");
        boldButton.addActionListener(e -> notesTextStyle.applyStyle("bold"));
        formattingPanel.add(boldButton);

        JButton italicButton = new JButton("I");
        italicButton.addActionListener(e -> notesTextStyle.applyStyle("italic"));
        formattingPanel.add(italicButton);

        JButton underlineButton = new JButton("U");
        underlineButton.addActionListener(e -> notesTextStyle.applyStyle("underline"));
        formattingPanel.add(underlineButton);

        // Renk seçimi
        JButton colorButton = new JButton("Renk Seç");
        colorButton.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Renk Seç", Color.BLACK);
            if (color != null) {
                notesTextStyle.applyColor(color);
            }
        });
        formattingPanel.add(colorButton);

        return formattingPanel;
    }

    private void loadNoteContent() {
        int selectedIndex = notesUI.getNoteList().getSelectedIndex();
        if (selectedIndex != -1) {
            List<Note> notes = notesDB.loadNotes();
            if (notes != null && !notes.isEmpty() && selectedIndex < notes.size()) {
                Note selectedNote = notes.get(selectedIndex);
                notesUI.getTextPane().setText(selectedNote.getContent());
                notesUI.getTitleField().setText(selectedNote.getTitle());
            }
        }
    }

    private void loadSavedNotes() {
        notesUI.getNoteListModel().clear();
        for (Note note : notesDB.loadNotes()) {
            notesUI.getNoteListModel().addElement(note.getTitle());
        }
    }

    private void saveNote() {
        String title = notesUI.getTitleField().getText().trim();
        String content = notesUI.getTextPane().getText();

        if (title.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Başlık ve içerik boş bırakılamaz!");
            return;
        }

        notesDB.saveNote(title, content);
        loadSavedNotes();
        JOptionPane.showMessageDialog(this, "Not başarıyla kaydedildi!");
    }

    private void deleteNote() {
        int selectedIndex = notesUI.getNoteList().getSelectedIndex();
        if (selectedIndex != -1) {
            List<Note> notes = notesDB.loadNotes();
            if (notes != null && !notes.isEmpty() && selectedIndex < notes.size()) {
                String noteTitle = notes.get(selectedIndex).getTitle();
                int confirmation = JOptionPane.showConfirmDialog(this, "Bu notu silmek istediğinize emin misiniz?", "Silme Onayı", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    notesDB.deleteNote(noteTitle);
                    loadSavedNotes();
                }
            }
        }
    }

    private void createNewNote() {
        notesUI.getTitleField().setText("");
        notesUI.getTextPane().setText("");
        notesUI.getNoteList().clearSelection();
    }

    private void logout() {
        currentUserId = -1;
        currentUsername = "";
        setVisible(false);
        dispose();
        Form form = new Form();
        form.setVisible(true);
    }

	private void dispose() {
		// TODO Auto-generated method stub
		
	}
}
