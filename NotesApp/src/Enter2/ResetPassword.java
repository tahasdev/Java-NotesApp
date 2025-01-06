package Enter2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class ResetPassword extends JFrame {
	
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	public String user;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField ResetPass;
	private JTextField ResetPassVerify;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 592);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblifre = new JLabel("Yeni Şifre");
		lblifre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblifre.setBounds(272, 180, 111, 33);
		contentPane.add(lblifre);
		
		ResetPass = new JTextField();
		ResetPass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ResetPass.setColumns(10);
		ResetPass.setBounds(272, 210, 243, 33);
		contentPane.add(ResetPass);
		
		JButton btnSifreGuncelle = new JButton("Güncelle");
		btnSifreGuncelle.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) { 
		        if (ResetPass.getText().equals(ResetPassVerify.getText())) {
		            try {
		                String guncelle = "UPDATE `forgotpass` SET `password`=? WHERE `email`=?";
		                con = DriverManager.getConnection("jdbc:mysql://localhost/notes", "root", "");
		                pst = con.prepareStatement(guncelle);
		                pst.setString(1, ResetPassVerify.getText());
		                pst.setString(2, user); // Kullanıcı adını parametre olarak ekliyoruz
		                pst.executeUpdate();
		                JOptionPane.showMessageDialog(null, "Şifre sıfırlama başarılı");
		            } catch (Exception ex) {
		                JOptionPane.showMessageDialog(null, ex);
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Şifreler eşleşmiyor");
		        }
		    }
		});


		btnSifreGuncelle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSifreGuncelle.setBounds(339, 340, 111, 43);
		contentPane.add(btnSifreGuncelle);
		
		JLabel lblYeniifre = new JLabel("Yeni Şifre (Tekrar)");
		lblYeniifre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblYeniifre.setBounds(272, 253, 178, 33);
		contentPane.add(lblYeniifre);
		
		ResetPassVerify = new JTextField();
		ResetPassVerify.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ResetPassVerify.setColumns(10);
		ResetPassVerify.setBounds(272, 283, 243, 33);
		contentPane.add(ResetPassVerify);
	}
	
	public ResetPassword(String username) {
	this.user=username;	
	initialize();
	
	}

}
