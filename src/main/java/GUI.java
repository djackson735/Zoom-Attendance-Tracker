import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GUI {
    private File classFile;
    private Font courier = new Font("Courier", Font.PLAIN, 25);

    public GUI() {
        JFrame frame = new JFrame();
        frame.setSize(800,500);
        frame.setLocationRelativeTo(null);
        frame.setFont(courier);

        // TODO: This label should change depending on if a classfile exists already. Another button should exist to swap that classfile out if desired
        JLabel label = new JLabel("Please select the CSV file you wish to scan");
        label.setFont(courier);

        JButton fileButton = new JButton("Select file");
        fileButton.addActionListener(e -> {
           JFileChooser fileChooser = new JFileChooser("Choose File");
           FileFilter filter = new FileNameExtensionFilter("CSV files", "csv");
           int returnValue = fileChooser.showOpenDialog(fileButton);
           if (returnValue == JFileChooser.APPROVE_OPTION) {
               File selectedFile = fileChooser.getSelectedFile();
               try {
                   getResultMessage(selectedFile);
               } catch (IOException ex) {
                   //TODO Get a better error message
                   ex.printStackTrace();
               }
           }
        });

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(label);
        panel.add(fileButton);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("TE Attendance");
        frame.pack();
        frame.setVisible(true);
    }

    // True if file exists
    public boolean fileCheck() {
        String filePath = "src/main/resources/Class-List.csv";
        if (new File(filePath).exists()) {
            classFile = new File(filePath);
            return true;
        } else return false;
    }

    public void getResultMessage(File inputFile) throws IOException {
        JFrame frame = new JFrame("Attendance Results");
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setFont(courier);
        Reader reader = new Reader();
        String resultMessage = reader.resultWriter(inputFile);
        JTextArea result = new JTextArea(resultMessage);
        frame.add(result);
        frame.setVisible(true);
    }

}
