import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vigenere {

    private static final String ENG = "abcdefghijklmnopqrstuvwxyz";
    private static final String RUS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    private String alphabet;
    private String language;
    private String key;

    private JFrame frame;
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JTextField keyField;
    private JButton encryptButton;
    private JButton decryptButton;
    private JComboBox<String> alphabetSelector;

    public Vigenere() {
        GUI();
        alphabet = ENG;
    }

    private void GUI() {
        frame = new JFrame("Шифр Виженера");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputArea = new JTextArea();
        inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        inputPanel.add(new JLabel("Введите текст:"), BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.NORTH);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputArea = new JTextArea();
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        outputPanel.add(new JLabel("Результат:"), BorderLayout.NORTH);
        frame.add(outputPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout());
        keyField = new JTextField(10);
        controlPanel.add(new JLabel("Ключ:"));
        controlPanel.add(keyField);

        alphabetSelector = new JComboBox<>(new String[]{"ENG", "RUS"});
        alphabetSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                language = alphabetSelector.getSelectedItem().toString();
                setAlphabet();
            }
        });
        controlPanel.add(alphabetSelector);

        encryptButton = new JButton("Шифровать");
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encrypt();
            }
        });
        controlPanel.add(encryptButton);

        decryptButton = new JButton("Расшифровать");
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decrypt();
            }
        });
        controlPanel.add(decryptButton);

        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void setAlphabet() {
        if (language.equals("ENG")) {
            alphabet = ENG;
        } else if (language.equals("RUS")) {
            alphabet = RUS;
        }
    }

    private char shift(char c, int shift) {
        if (Character.isLetter(c)) {
            int index = alphabet.indexOf(Character.toLowerCase(c));
            if (index == -1) {
                return c;
            }
            int newIndex = (index + shift) % alphabet.length();
            if (newIndex < 0) {
                newIndex += alphabet.length();
            }
            return Character.isUpperCase(c) ? Character.toUpperCase(alphabet.charAt(newIndex)) : alphabet.charAt(newIndex);
        } else {
            return c;
        }
    }

    private void encrypt() {
        String inputText = inputArea.getText();
        key = keyField.getText().toLowerCase();
        String outputText = "";
        int keyIndex = 0;
        for (int i = 0; i < inputText.length(); i++) {
            char c = inputText.charAt(i);
            if (Character.isLetter(c)) {
                int shift = alphabet.indexOf(key.charAt(keyIndex));
                outputText += shift(c, shift);
                keyIndex = (keyIndex + 1) % key.length();
            } else {
                outputText += c;
            }
        }
        outputArea.setText(outputText);
    }

    private void decrypt() {
        String inputText = inputArea.getText();
        key = keyField.getText().toLowerCase();
        String outputText = "";
        int keyIndex = 0;
        for (int i = 0; i < inputText.length(); i++) {
            char c = inputText.charAt(i);
            if (Character.isLetter(c)) {
                int shift = alphabet.indexOf(key.charAt(keyIndex));
                outputText += shift(c, -shift);
                keyIndex = (keyIndex + 1) % key.length();
            } else {
                outputText += c;
            }
        }
        outputArea.setText(outputText);
    }
}