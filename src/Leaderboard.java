import javax.swing.*;
import java.awt.*;

public class Leaderboard extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel panel;

    public Leaderboard(){
        setTitle("Leaderboard");
        setSize(370, 500);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setBackground(Color.decode("#d5edff"));

        JLabel leaderboard = new JLabel("LEADERBOARD");
        leaderboard.setFont(new Font("Nirmala UI", Font.BOLD, 35));
        leaderboard.setForeground(Color.decode("#3e16eb"));
        leaderboard.setHorizontalAlignment(SwingConstants.CENTER);
        leaderboard.setPreferredSize(new Dimension(350, 90));

        panel.add(leaderboard);
    }
}
