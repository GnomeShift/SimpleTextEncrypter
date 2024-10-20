import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {

    private JFrame mainFrame;
    private JButton caesarButton;
    private JButton vigenereButton;

    public App() {
        createMain();
    }

    private void createMain() {
        mainFrame = new JFrame("SimpleTextEncrypter");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 150);
        mainFrame.setLayout(new FlowLayout());

        caesarButton = new JButton("Шифр Цезаря");
        caesarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Caesar();
            }
        });
        mainFrame.add(caesarButton);

        vigenereButton = new JButton("Шифр Виженера");
        vigenereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Vigenere();
                mainFrame.dispose();
            }
        });
        mainFrame.add(vigenereButton);

        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
            }
        });
    }
}