import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class MainWindow {
    private JButton generateWindowsButton;
    private JPanel mainPanel;
    private JSpinner spinner;
    private JButton selectFileButton;

    private ArrayList<ReaderWriterWindow> windowsList = new ArrayList<>();
    private Semaphore readingSemaphore;
    private Semaphore writingSemaphore;

    private File selectedFile;

    public MainWindow()
    {
        spinner.setModel(new SpinnerNumberModel(2, 2, 5, 1));

        selectFileButton.addActionListener(e ->  {
            selectedFile = selectFile();

            selectFileButton.setText("Archivo Seleccionado");
            selectFileButton.setEnabled(false);
            spinner.setEnabled(true);
            generateWindowsButton.setEnabled(true);
        });

        generateWindowsButton.addActionListener(e -> {
            createMultipleWindows((int) spinner.getValue());

            spinner.setEnabled(false);
            generateWindowsButton.setEnabled(false);
        });
    }

    private File selectFile()
    {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");

        fileChooser.setDialogTitle("Selecciona un archivo de texto");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(mainPanel);

        return returnValue == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFile() : null;
    }

    private void createMultipleWindows(int windows)
    {
        readingSemaphore = new Semaphore(windows);
        writingSemaphore = new Semaphore(1);

        for(int i = 0; i < windows; i++) {
            JFrame frame = new JFrame("Lector - Escritor " + (i + 1));
            ReaderWriterWindow window = new ReaderWriterWindow(readingSemaphore, writingSemaphore, windowsList, selectedFile);

            frame.setContentPane(window.mainPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

            windowsList.add(window);
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
