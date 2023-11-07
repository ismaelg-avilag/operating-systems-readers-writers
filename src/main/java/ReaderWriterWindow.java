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
        });

        writeButton.addActionListener(e -> {
        });

        saveButton.addActionListener(e -> {
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
