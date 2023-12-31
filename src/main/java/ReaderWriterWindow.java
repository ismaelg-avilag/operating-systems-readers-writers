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
    Boolean isReading = false;

    private ArrayList<ReaderWriterWindow> windowsList = new ArrayList<>();
    private Semaphore readingSemaphore;
    private Semaphore writingSemaphore;

    private File selectedFile;


    public ReaderWriterWindow() {}

    public ReaderWriterWindow(Semaphore readingSemaphore, Semaphore writingSemaphore, ArrayList<ReaderWriterWindow> windowsList, File selectedFile) {
        this.readingSemaphore = readingSemaphore;
        this.writingSemaphore = writingSemaphore;
        this.windowsList = windowsList;
        this.selectedFile = selectedFile;


        readButton.addActionListener(e -> {
            isReading = !isReading;

            if(isReading) {
                enableRead();

                readButton.setText("Cancelar Lectura");
                readButton.setBackground(new java.awt.Color(255,19,67));

                writeButton.setEnabled(true);
            } else {
                disableRead();

                readButton.setText("Habilitar Lectura");
                readButton.setBackground(new java.awt.Color(17,138,178));

                readButton.setEnabled(true);
                writeButton.setEnabled(false);
            }

        });

        writeButton.addActionListener(e -> {
            isWriting = !isWriting;

            if(isWriting) {
                writeButton.setText("Cancelar Escritura");
                writeButton.setBackground(new java.awt.Color(255,19,67));

                textArea.setEnabled(true);
                saveButton.setEnabled(true);
                readButton.setEnabled(false);

                enableWrite();
            } else {
                writeButton.setText("Habilitar Escritura");
                writeButton.setBackground(new java.awt.Color(255,209,102));

                textArea.setEnabled(false);
                saveButton.setEnabled(false);
                readButton.setEnabled(true);

                disableWrite();
            }
        });

        saveButton.addActionListener(e -> {
            saveFileContent();
            disableWrite();

            textArea.setEnabled(false);
            saveButton.setEnabled(false);
            writeButton.setEnabled(true);
            readButton.setEnabled(true);

            if(isWriting) {
                writeButton.setText("Habilitar Escritura");
                writeButton.setBackground(new java.awt.Color(255,209,102));
                isWriting = !isWriting;
            }
        });
    }

    private void updateTextArea(String fileContent)
    {
        textArea.setText("");
        textArea.append(fileContent);
    }

    private String readFileContent()
    {
        StringBuilder fileContent = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
            String line = reader.readLine();

            while (line != null) {
                fileContent.append(line).append("\n");
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return fileContent.toString();
    }

    private void saveFileContent()
    {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
            writer.write(textArea.getText());
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void enableRead()
    {
        try {
            readingSemaphore.acquire();
            updateTextArea(readFileContent());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void disableRead()
    {
        readingSemaphore.release();
        textArea.setText("");
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
    }

    private void enableWindow()
    {
        readButton.setEnabled(true);
        writeButton.setEnabled(true);
        saveButton.setEnabled(false);

        if(isReading)
            updateTextArea(readFileContent());
    }


}
