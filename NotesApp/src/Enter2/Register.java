package Enter2;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Register extends JFrame {

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Register frame = new Register();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void registerfunction() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kullanıcı adı, şifre ve email boş bırakılamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Geçerli bir email adresi giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/notes", "root", "");
            String checkQuery = "SELECT * FROM forgotpass WHERE username = ? OR email = ?";
            PreparedStatement checkPst = con.prepareStatement(checkQuery);
            checkPst.setString(1, username);
            checkPst.setString(2, email);
            ResultSet rs = checkPst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Kullanıcı adı veya email zaten kullanılıyor!", "Hata", JOptionPane.ERROR_MESSAGE);
                rs.close();
                checkPst.close();
                con.close();
                return;
            }

            rs.close();
            checkPst.close();

            String query = "INSERT INTO forgotpass (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, email);

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Kayıt başarılı!");
            } else {
                JOptionPane.showMessageDialog(this, "Kayıt başarısız!", "Hata", JOptionPane.ERROR_MESSAGE);
            }

            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Kayıt hatası: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }


    public Register() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 660);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new CardLayout(0, 0));

        // İlk panel
        JPanel panelarea = new JPanel();
        contentPane.add(panelarea, "registerPanel");
        panelarea.setLayout(null);

        JLabel lblKullaniciAdi = new JLabel("Kullanıcı adı");
        lblKullaniciAdi.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblKullaniciAdi.setBounds(308, 129, 111, 33);
        panelarea.add(lblKullaniciAdi);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        usernameField.setColumns(10);
        usernameField.setBounds(308, 164, 243, 33);
        panelarea.add(usernameField);

        JLabel lblSifre = new JLabel("Şifre");
        lblSifre.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblSifre.setBounds(308, 265, 69, 33);
        panelarea.add(lblSifre);

        JButton btnHesapOlutur = new JButton("Hesap oluştur");
        btnHesapOlutur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerfunction();
            }
        });
        btnHesapOlutur.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnHesapOlutur.setBounds(347, 352, 157, 43);
        panelarea.add(btnHesapOlutur);

        JLabel girisyapekraninagit = new JLabel("Giriş Yap");
        girisyapekraninagit.setHorizontalAlignment(SwingConstants.CENTER);
        girisyapekraninagit.setForeground(Color.BLUE);
        girisyapekraninagit.setFont(new Font("Tahoma", Font.PLAIN, 15));
        girisyapekraninagit.setBounds(347, 424, 157, 25);
        panelarea.add(girisyapekraninagit);
        girisyapekraninagit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "formPanel"); // Form paneline geçiş
            }
        });
        girisyapekraninagit.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                girisyapekraninagit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });

        emailField = new JTextField();
        emailField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        emailField.setColumns(10);
        emailField.setBounds(308, 234, 243, 33);
        panelarea.add(emailField);

        JLabel lblemail = new JLabel("E-posta");
        lblemail.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblemail.setBounds(308, 199, 111, 33);
        panelarea.add(lblemail);

        JLabel lblHesabnzZatenVar_2 = new JLabel("Hesabınız zaten var mı?");
        lblHesabnzZatenVar_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblHesabnzZatenVar_2.setForeground(new Color(0, 0, 0));
        lblHesabnzZatenVar_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblHesabnzZatenVar_2.setBounds(308, 405, 243, 25);
        panelarea.add(lblHesabnzZatenVar_2);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(308, 296, 243, 35);
        panelarea.add(passwordField);

        // Form paneli
        Form formPanel = new Form(); // Form nesnesini oluşturuyoruz
        contentPane.add(formPanel.getContentPane(), "formPanel");
    }
}
