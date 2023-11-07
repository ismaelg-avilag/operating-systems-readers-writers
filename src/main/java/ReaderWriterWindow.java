import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class ReaderWriterWindow {
    private JPanel mainPanel;
    private JButton readButton;
    private JButton saveButton;
    private JButton writeButton;
    private JTextArea textArea;

    ArrayList<String> fileContent = new ArrayList<>();
    Boolean isWriting = false;


    public ReaderWriterWindow() {
        readButton.addActionListener(e -> {
            textArea.setText("");
            fileContent.clear();

            try {
                BufferedReader reader = new BufferedReader(new FileReader("ReaderWriterFiles/content.txt"));

                String line = reader.readLine();
                while (line != null) {
                    fileContent.add(line + "\n");
                    line = reader.readLine();
                }

                for(String s : fileContent) {
                    textArea.append(s);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            readButton.setEnabled(false);
            writeButton.setEnabled(true);
        });

        writeButton.addActionListener(e -> {
            isWriting = !isWriting;

            if(isWriting) {
                writeButton.setText("Cancelar escritura");
                writeButton.setBackground(new java.awt.Color(255,19,67));

                // writing semaphore process

                textArea.setEnabled(true);
                saveButton.setEnabled(true);
            } else {
                writeButton.setText("Escribir");
                writeButton.setBackground(new java.awt.Color(255,209,102));

                // writing semaphore process

                textArea.setEnabled(false);
                saveButton.setEnabled(false);
            }
        });

        saveButton.addActionListener(e -> {
            // save changes to file

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


    public static void main(String[] args) {
        JFrame frame = new JFrame("ReaderWriterWindow");
        frame.setContentPane(new ReaderWriterWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
