package Enter2;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CardLayoutExample extends JFrame {
    private JPanel contentPane;
    private CardLayout cardLayout;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CardLayoutExample frame = new CardLayoutExample();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CardLayoutExample() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        cardLayout = new CardLayout();
        contentPane.setLayout(cardLayout);
        setContentPane(contentPane);

        // Panel 1
        JPanel panel1 = new JPanel();
        JButton btnToPanel2 = new JButton("Go to Panel 2");
        btnToPanel2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "formPanel");
            }
        });
        panel1.add(btnToPanel2);

        // Form Panel
        Form formPanel = new Form();
        
        // Panelleri ana panele ekleyin
        contentPane.add(panel1, "panel1");
        contentPane.add(formPanel.getContentPane(), "formPanel");

        // İlk paneli gösterin
        cardLayout.show(contentPane, "panel1");
    }
}
