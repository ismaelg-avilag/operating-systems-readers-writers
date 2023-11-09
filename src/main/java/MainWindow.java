import javax.swing.*;

public class MainWindow {
    private JButton generateWindowsButton;
    private JPanel mainPanel;
    private JSpinner spinner;

    public MainWindow()
    {
        spinner.setModel(new SpinnerNumberModel(2, 2, 5, 1));

        generateWindowsButton.addActionListener(e -> {
            int windows = (int) spinner.getValue();
            for(int i = 0; i < windows; i++) {
                JFrame frame = new JFrame("ReaderWriterWindow");
                frame.setContentPane(new ReaderWriterWindow().mainPanel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }

            spinner.setEnabled(false);
            generateWindowsButton.setEnabled(false);
        });
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
