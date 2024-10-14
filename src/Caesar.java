import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Caesar {

    private static final String ENG = "abcdefghijklmnopqrstuvwxyz";
    private static final String SPECIAL = "!@#$%^&*()_+=-`~[]{};':\"\\|,.<>/? ";

    private static final int RANGE = 25;

    private String alphabet;
    private int key1;
    private int key2;

    private JFrame frame;
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JTextField keyField1;
    private JTextField keyField2;
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton bruteforceButton;
    private JButton analysisButton;

    public Caesar() {
        GUI();
        alphabet = ENG;
    }

    private void GUI() {
        frame = new JFrame("SimpleTextEncrypter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 400);
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
        keyField1 = new JTextField(2);
        keyField1.setText("1");
        controlPanel.add(new JLabel("Ключ 1:"));
        controlPanel.add(keyField1);

        keyField2 = new JTextField(2);
        keyField2.setText("2");
        controlPanel.add(new JLabel("Ключ 2:"));
        controlPanel.add(keyField2);

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

        bruteforceButton = new JButton("Подбор ключей");
        bruteforceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bruteforce();
            }
        });
        controlPanel.add(bruteforceButton);

        analysisButton = new JButton("Анализ");
        analysisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analysis();
            }
        });
        controlPanel.add(analysisButton);

        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private int validate(String keyText) {
        try {
            int key = Integer.parseInt(keyText);
            if (key >= 1 && key <= RANGE) {
                return key;
            } else {
                JOptionPane.showMessageDialog(frame, "Ключ должен быть в диапазоне от 1 до " + RANGE + "!!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return 1;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Ключ должен быть числом!!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
    }

    private void encrypt() {
        String inputText = inputArea.getText();
        String outputText = "";
        key1 = validate(keyField1.getText());
        key2 = validate(keyField2.getText());
        for (char c : inputText.toCharArray()) {
            if (Character.isLetter(c)) {
                outputText += shift(c, key1, key2);
            } else if (SPECIAL.indexOf(c) != -1) {
                outputText += shift(c, key1, key2);
            } else {
                outputText += c;
            }
        }
        outputArea.setText(outputText);
    }

    private void decrypt() {
        String inputText = inputArea.getText();
        String outputText = "";
        key1 = validate(keyField1.getText());
        key2 = validate(keyField2.getText());
        for (char c : inputText.toCharArray()) {
            if (Character.isLetter(c)) {
                outputText += shift(c, -key1, -key2);
            } else if (SPECIAL.indexOf(c) != -1) {
                outputText += shift(c, -key1, -key2);
            } else {
                outputText += c;
            }
        }
        outputArea.setText(outputText);
    }

    private void bruteforce() {
        String inputText = inputArea.getText();
        for (int i = 1; i <= RANGE; i++) {
            for (int j = 1; j <= RANGE; j++) {
                String decryptedText = "";
                for (char c : inputText.toCharArray()) {
                    if (Character.isLetter(c)) {
                        decryptedText += shift(c, -i, -j);
                    } else if (SPECIAL.indexOf(c) != -1) {
                        decryptedText += shift(c, -i, -j);
                    } else {
                        decryptedText += c;
                    }
                }
                outputArea.append("Ключи " + i + ", " + j + ": " + decryptedText + "\n");
            }
        }
    }

    private void analysis() {
        String input = inputArea.getText();
        Map<Character, Integer> letterFrequencies = new HashMap<>();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                c = Character.toLowerCase(c);
                letterFrequencies.put(c, letterFrequencies.getOrDefault(c, 0) + 1);
            }
        }
        Character frequentLetter1 = null;
        Character frequentLetter2 = null;
        int maxFrequency1 = 0;
        int maxFrequency2 = 0;
        for (Map.Entry<Character, Integer> entry : letterFrequencies.entrySet()) {
            if (entry.getValue() > maxFrequency1) {
                maxFrequency2 = maxFrequency1;
                frequentLetter2 = frequentLetter1;
                maxFrequency1 = entry.getValue();
                frequentLetter1 = entry.getKey();
            }
            else if (entry.getValue() > maxFrequency2) {
                maxFrequency2 = entry.getValue();
                frequentLetter2 = entry.getKey();
            }
        }
        int key1 = (int) frequentLetter1 - (int) 'e';
        int key2 = (int) frequentLetter2 - (int) 't';
        if (key1 < 0) {
            key1 += alphabet.length();
        }
        if (key2 < 0) {
            key2 += alphabet.length();
        }

        String decryptedText = "";
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                decryptedText += shift(c, -key1, -key2);
            } else {
                decryptedText += c;
            }
        }
        outputArea.setText("Взломанные ключи: " + key1 + ", " + key2 + "\n" + decryptedText);
    }

    private char shift(char c, int shift1, int shift2) {
        if (Character.isLetter(c)) {
            int index = alphabet.indexOf(Character.toLowerCase(c));
            int newIndex = (index + shift1 + shift2) % alphabet.length();
            if (newIndex < 0) {
                newIndex += alphabet.length();
            }
            return Character.isUpperCase(c) ? Character.toUpperCase(alphabet.charAt(newIndex)) : alphabet.charAt(newIndex);
        } else if (SPECIAL.indexOf(c) != -1) {
            int index = SPECIAL.indexOf(c);
            int newIndex = (index + shift1 + shift2) % SPECIAL.length();
            if (newIndex < 0) {
                newIndex += SPECIAL.length();
            }
            return SPECIAL.charAt(newIndex);
        } else if (c == ' ') {
            return ' ';
        } else {
            return c;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Caesar();
            }
        });
    }
}