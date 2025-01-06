package Enter2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class SendCode extends JFrame {

	int randomCode;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField resetpassemail;
	private JTextField resetpassverifycodearea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SendCode frame = new SendCode();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SendCode() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 867, 665);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		resetpassemail = new JTextField();
		resetpassemail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		resetpassemail.setColumns(10);
		resetpassemail.setBounds(230, 209, 243, 33);
		contentPane.add(resetpassemail);
		
		resetpassverifycodearea = new JTextField();
		resetpassverifycodearea.setColumns(10);
		resetpassverifycodearea.setBounds(230, 339, 243, 33);
		contentPane.add(resetpassverifycodearea);
		
		JButton btnGnder = new JButton("Gönder");
		btnGnder.addActionListener(new ActionListener() {
			
			

			public void actionPerformed(ActionEvent e) {
				
				Random rastgelesayi = new Random();
				randomCode = rastgelesayi.nextInt(999999);
				
				
				  String gonderen = "tahahesap0001@gmail.com";
				  String sifre = "dhvd ecmh yztz dfla"; //Notes App'in
				  
				  // String alici = "tahahesap0002@gmail.com";
				  String alici = resetpassemail.getText();
				
			      // SMTP sunucusu ayarları
			      Properties properties = new Properties();
			      properties.put("mail.smtp.auth", "true");
			      properties.put("mail.smtp.starttls.enable", "true");
			      properties.put("mail.smtp.host", "smtp.gmail.com");
			      properties.put("mail.smtp.port", "587");

			      // Oturum oluşturma
			      Session session = Session.getInstance(properties, new Authenticator() {
			          @Override
			          protected PasswordAuthentication getPasswordAuthentication() {
			              return new PasswordAuthentication(gonderen, sifre);
			          }
			      });
			      
			        try {
			            // Mesaj oluşturma
			            Message message = new MimeMessage(session);
			            message.setFrom(new InternetAddress(gonderen));// Gönderen
			            message.setRecipient(Message.RecipientType.TO, // Alıcı
			                    new InternetAddress(alici));
			            message.setSubject("Notes App | Hesap Kurtarma "); // Konu
			            message.setText("Doğrulama Kodunuz: "+randomCode);// İçerik

			            // Mesajı gönder
			            Transport.send(message);
			            System.out.println("Mail başarıyla gönderildi!");
			        } catch (Exception error) {
			        	error.printStackTrace();
			        }
		
				
				
			 
				
				
			}
			
			
			
		});
		btnGnder.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnGnder.setBounds(483, 209, 111, 33);
		contentPane.add(btnGnder);
		
		JButton btnDorula = new JButton("Doğrula");
		btnDorula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(Integer.valueOf(resetpassverifycodearea.getText())==randomCode) {
					
					
					
					ResetPassword rs = new ResetPassword(resetpassemail.getText());
					rs.setVisible(true);	
					SendCode.this.setVisible(false);
					
		}
				
			}
		});
		btnDorula.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnDorula.setBounds(483, 336, 111, 33);
		contentPane.add(btnDorula);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEmail.setBounds(230, 177, 111, 33);
		contentPane.add(lblEmail);
		
		JLabel lblDogrulamakodu = new JLabel("Doğrulama Kodu");
		lblDogrulamakodu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDogrulamakodu.setBounds(230, 309, 185, 33);
		contentPane.add(lblDogrulamakodu);
		
		JLabel lblNewLabel = new JLabel("< Geri");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                setVisible(false);
                dispose();
                Form form = new Form();
                form.setVisible(true);
			}
		});
		lblNewLabel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				lblNewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 10, 90, 25);
		contentPane.add(lblNewLabel);
		
		/*
		JLabel label = new JLabel("Eposta başarıyla gönderildi");
		label.setBounds(230, 252, 200, 13);
		contentPane.add(label);
		*/
	}
}
