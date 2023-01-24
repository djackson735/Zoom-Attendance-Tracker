import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

public class JavaFXGUI {

    private File classFile;
    private Font courier = new Font("Courier", Font.PLAIN, 25);

    public JavaFXGUI() {
        Stage stage = new Stage();
        stage.setTitle("TE Attendance Checker");
        stage.show();

    }
}
