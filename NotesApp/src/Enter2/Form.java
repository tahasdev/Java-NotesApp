package Enter2;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class Form extends JFrame {

    Connection baglanti = null;
    ResultSet sonuc = null;
    PreparedStatement yerlestir = null;
    
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textUsername;
    private JPasswordField textPassword;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Form frame = new Form();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Form() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 660);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new CardLayout(0, 0));  // CardLayout ayarı
        setContentPane(contentPane);

        // Giriş Paneli
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);

        JLabel lblKullaniciAdi = new JLabel("Kullanıcı adı");
        lblKullaniciAdi.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblKullaniciAdi.setBounds(291, 213, 111, 33);
        loginPanel.add(lblKullaniciAdi);

        textUsername = new JTextField();
        textUsername.setFont(new Font("Tahoma", Font.PLAIN, 18));
        textUsername.setBounds(291, 248, 243, 33);
        textUsername.setColumns(10);
        loginPanel.add(textUsername);

        JLabel lblSifre = new JLabel("Şifre");
        lblSifre.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblSifre.setBounds(291, 306, 69, 33);
        loginPanel.add(lblSifre);

        JButton btnNewButton = new JButton("Giriş Yap");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	    String girdi = "SELECT * FROM forgotpass WHERE username=? AND password=?";
        	    
        	    String username = textUsername.getText();
        	    String password = textPassword.getText();
        	    
        	    if (username.contains("@") || username.contains("<") || username.contains(">")) {
        	        JOptionPane.showMessageDialog(null, "Kullanıcı adı '@', '<', '>' karakterlerini içeremez!", "Hata", JOptionPane.ERROR_MESSAGE);
        	        return;
        	    }
        	    
        	    try {
        	        Connection baglanti = DriverManager.getConnection("jdbc:mysql://localhost/notes", "root", "");
        	        PreparedStatement yerlestir = baglanti.prepareStatement(girdi);
        	        yerlestir.setString(1, username);
        	        yerlestir.setString(2, password);
        	        ResultSet sonuc = yerlestir.executeQuery();
        	        
        	        if (sonuc.next()) {
        	            int userId = sonuc.getInt("id");
        	            String foundUsername = sonuc.getString("username");

        	            // NotesArea paneline geçiş
        	            NotesArea notesArea = new NotesArea(userId, foundUsername);
        	            contentPane.add(notesArea, "notesArea"); // getContentPane() kullanmaya gerek yok
        	            CardLayout cardLayout = (CardLayout) contentPane.getLayout();
        	            cardLayout.show(contentPane, "notesArea");
        	        } else {
        	            JOptionPane.showMessageDialog(null, "Giriş başarısız");
        	        }

        	        sonuc.close();
        	        yerlestir.close();
        	        baglanti.close();
        	    } catch (Exception e2) {
        	        JOptionPane.showMessageDialog(null, e2);
        	    }
        	}
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnNewButton.setBounds(291, 406, 243, 43);
        loginPanel.add(btnNewButton);

        JLabel ForgotPassword = new JLabel("Şifremi unuttum");
        ForgotPassword.setForeground(new Color(0, 0, 255));
        ForgotPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "resetpassPanel"); // ResetPass paneline geçiş
            }
        });
        ForgotPassword.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                ForgotPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
        ForgotPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
        ForgotPassword.setBounds(434, 371, 138, 25);
        loginPanel.add(ForgotPassword);
        
        contentPane.add(loginPanel, "loginPanel");
        
        JLabel lblhesabinzyokmu = new JLabel("Hesabınız yok mu?");
        lblhesabinzyokmu.setHorizontalAlignment(SwingConstants.CENTER);
        lblhesabinzyokmu.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblhesabinzyokmu.setBounds(291, 459, 243, 33);
        loginPanel.add(lblhesabinzyokmu);
        
        JLabel goregister = new JLabel("Hesap oluştur");
        goregister.addMouseMotionListener(new MouseMotionAdapter() {
        	@Override
        	public void mouseMoved(MouseEvent e) {
        		goregister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        	}
        });
        goregister.setHorizontalAlignment(SwingConstants.CENTER);
        goregister.setForeground(new Color(0, 0, 255));
        goregister.setFont(new Font("Tahoma", Font.PLAIN, 15));
        goregister.setBounds(291, 496, 243, 33);
        goregister.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Yeni bir Register penceresi aç
                Register registerFrame = new Register();
                registerFrame.setVisible(true);
                setVisible(false);
            }         
        });
        loginPanel.add(goregister);
        
        textPassword = new JPasswordField();
        textPassword.setBounds(291, 344, 243, 33);
        loginPanel.add(textPassword);

        // Reset Password Panel
        SendCode resetPassPanel = new SendCode();
        contentPane.add(resetPassPanel.getContentPane(), "resetpassPanel");
    }
}
