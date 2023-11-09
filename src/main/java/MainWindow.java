import javax.swing.*;

public class MainWindow {
    private JButton generateWindowsButton;
    private JPanel mainPanel;
    private JSpinner spinner;

    public MainWindow()
    {
        spinner.setModel(new SpinnerNumberModel(1, 1, 5, 1));

        generateWindowsButton.addActionListener(e -> {
            // get the number of windows to generate
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
