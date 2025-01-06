package Enter2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class NotesDB {

    private int currentUserId;

    public NotesDB(int userId) {
        this.currentUserId = userId;
    }

    public void createNotesTable() {
        String tableName = "user_" + currentUserId + "_notes";
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "noteid INT PRIMARY KEY AUTO_INCREMENT, "
                + "title VARCHAR(255) NOT NULL, "
                + "content TEXT, "
                + "picdirectories TEXT, "
                + "folderinfo VARCHAR(255)"
                + ")";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/notes", "root", "");
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Tablo oluşturulurken hata: " + e.getMessage());
        }
    }

    public List<Note> loadNotes() {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT title, content FROM user_" + currentUserId + "_notes";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/notes", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                String content = rs.getString("content");
                notes.add(new Note(title, content));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Notlar yüklenemedi: " + e.getMessage());
        }
        return notes;
    }

    public void saveNote(String title, String content) {
        String insertSQL = "INSERT INTO user_" + currentUserId + "_notes (title, content) VALUES (?, ?)";
        String updateSQL = "UPDATE user_" + currentUserId + "_notes SET content = ? WHERE title = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/notes", "root", "")) {
            PreparedStatement stmt;
            if (noteExists(title)) {
                stmt = conn.prepareStatement(updateSQL);
                stmt.setString(1, content);
                stmt.setString(2, title);
            } else {
                stmt = conn.prepareStatement(insertSQL);
                stmt.setString(1, title);
                stmt.setString(2, content);
            }
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Not başarıyla kaydedildi!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kaydetme hatası: " + e.getMessage());
        }
    }

    public void deleteNote(String title) {
        String deleteSQL = "DELETE FROM user_" + currentUserId + "_notes WHERE title = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/notes", "root", "");
             PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {

            stmt.setString(1, title);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Not silindi!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Silme hatası: " + e.getMessage());
        }
    }

    private boolean noteExists(String title) throws SQLException {
        String query = "SELECT COUNT(*) FROM user_" + currentUserId + "_notes WHERE title = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/notes", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }
}
