import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class ReaderWriterWindow {
    public JPanel mainPanel;
    private JButton readButton;
    private JButton saveButton;
    private JButton writeButton;
    private JTextArea textArea;

    Boolean isWriting = false;


    public ReaderWriterWindow() {
        readButton.addActionListener(e -> {
            updateTextArea();

            readButton.setEnabled(false);
            writeButton.setEnabled(true);
        });

        writeButton.addActionListener(e -> {
            isWriting = !isWriting;

            if(isWriting) {
                writeButton.setText("Cancelar escritura");
                writeButton.setBackground(new java.awt.Color(255,19,67));

                // block write access

                textArea.setEnabled(true);
                saveButton.setEnabled(true);
            } else {
                writeButton.setText("Escribir");
                writeButton.setBackground(new java.awt.Color(255,209,102));

                // unlock write access

                textArea.setEnabled(false);
                saveButton.setEnabled(false);
            }
        });

        saveButton.addActionListener(e -> {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("ReaderWriterFiles/content.txt"));
                writer.write(textArea.getText());

                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            textArea.setEnabled(false);
            saveButton.setEnabled(false);
            writeButton.setEnabled(true);

            if(isWriting) {
                writeButton.setText("Escribir");
                writeButton.setBackground(new java.awt.Color(255,209,102));
                isWriting = !isWriting;
            }
        });
    }

    private void updateTextArea()
    {
        textArea.setText("");

        try {
            BufferedReader reader = new BufferedReader(new FileReader("ReaderWriterFiles/content.txt"));

            String line = reader.readLine();
            while (line != null) {
                textArea.append(line + "\n");
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("ReaderWriterWindow");
        frame.setContentPane(new ReaderWriterWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
