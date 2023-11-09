import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class ReaderWriterWindow {
    public JPanel mainPanel;
    private JButton readButton;
    private JButton saveButton;
    private JButton writeButton;
    private JTextArea textArea;

    Boolean isWriting = false;

    private ArrayList<ReaderWriterWindow> windowsList = new ArrayList<>();
    private Semaphore writingSemaphore;


    public ReaderWriterWindow() {}

    public ReaderWriterWindow(Semaphore writingSemaphore, ArrayList<ReaderWriterWindow> windowsList) {
        this.writingSemaphore = writingSemaphore;
        this.windowsList = windowsList;


        readButton.addActionListener(e -> {
            updateTextArea(readFileContent());

            readButton.setEnabled(false);
            writeButton.setEnabled(true);
        });

        writeButton.addActionListener(e -> {
            isWriting = !isWriting;

            if(isWriting) {
                writeButton.setText("Cancelar Escritura");
                writeButton.setBackground(new java.awt.Color(255,19,67));

                textArea.setEnabled(true);
                saveButton.setEnabled(true);

                enableWrite();
            } else {
                writeButton.setText("Habilitar Escritura");
                writeButton.setBackground(new java.awt.Color(255,209,102));

                textArea.setEnabled(false);
                saveButton.setEnabled(false);

                disableWrite();
            }
        });

        saveButton.addActionListener(e -> {
            saveFileContent();
            disableWrite();

            textArea.setEnabled(false);
            saveButton.setEnabled(false);
            writeButton.setEnabled(true);

            if(isWriting) {
                writeButton.setText("Habilitar Escritura");
                writeButton.setBackground(new java.awt.Color(255,209,102));
                isWriting = !isWriting;
            }
        });
    }

    private void updateTextArea(ArrayList<String> fileContent)
    {
        textArea.setText("");

        for(String line : fileContent)
            textArea.append(line + "\n");
    }

    private ArrayList<String> readFileContent()
    {
        ArrayList<String> fileContent = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("ReaderWriterFiles/content.txt"));
            String line = reader.readLine();

            while (line != null) {
                fileContent.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return fileContent;
    }

    private void saveFileContent()
    {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("ReaderWriterFiles/content.txt"));
            writer.write(textArea.getText());
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void enableWrite()
    {
        try {
            writingSemaphore.acquire();
            disableOtherWindows();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void disableWrite()
    {
        writingSemaphore.release();
        enableOtherWindows();
    }

    private void disableOtherWindows()
    {
        for(ReaderWriterWindow window : windowsList)
            if(window != this)
                window.disableWindow();
    }

    private void enableOtherWindows()
    {
        for(ReaderWriterWindow window : windowsList)
            if(window != this)
                window.enableWindow();
    }

    private void disableWindow()
    {
        readButton.setEnabled(false);
        writeButton.setEnabled(false);
        saveButton.setEnabled(false);
        textArea.setText("Otra ventana está escribiendo en este momento.");
    }

    private void enableWindow()
    {
        readButton.setEnabled(true);
        writeButton.setEnabled(true);
        saveButton.setEnabled(false);
        updateTextArea(readFileContent());
    }


}
