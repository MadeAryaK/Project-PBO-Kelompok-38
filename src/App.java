import java.awt.*;
import javax.swing.*;

public class App extends JFrame {

    private static final long serialVersionUID = 1L;
    final int WIDTH = 850;
    final int HEIGHT = 500;
    Menu menu = new Menu();
 
    App() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.add(menu);
        this.pack();
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);
    }

    public static void main(String[] args) {
        try {
            new App();
        } catch (Exception e) {
        }
    }

}
