import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class MainWindow {
    private JButton generateWindowsButton;
    private JPanel mainPanel;
    private JSpinner spinner;

    private ArrayList<ReaderWriterWindow> windowsList = new ArrayList<>();
    private Semaphore readingSemaphore;
    private Semaphore writingSemaphore;

    public MainWindow()
    {
        spinner.setModel(new SpinnerNumberModel(2, 2, 5, 1));

        generateWindowsButton.addActionListener(e -> {
            createMultipleWindows((int) spinner.getValue());

            spinner.setEnabled(false);
            generateWindowsButton.setEnabled(false);
        });
    }

    private void createMultipleWindows(int windows)
    {
        readingSemaphore = new Semaphore(windows);
        writingSemaphore = new Semaphore(1);

        for(int i = 0; i < windows; i++) {
            JFrame frame = new JFrame("Lector - Escritor " + (i + 1));
            ReaderWriterWindow window = new ReaderWriterWindow(readingSemaphore, writingSemaphore, windowsList);

            frame.setContentPane(window.mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
