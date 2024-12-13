import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Leaderboard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panel;

    public Leaderboard() {
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
        String selectQuery = "SELECT nama, score FROM user ORDER BY score DESC LIMIT 10";
        try {
            Connection connection = Database.getConnection();
            PreparedStatement pst = connection.prepareStatement(selectQuery);
            ResultSet rs = pst.executeQuery();
            int number = 0;
            while (rs.next()) {
                String nama = rs.getString("nama");
                int score = rs.getInt("score");
                number++;
                JLabel nomor = new JLabel(String
                        .valueOf("<html><p style='margin:0 0px 0 7px;'>" + String.valueOf(number) + "</p></html>"));
                JLabel namaLabel = new JLabel(nama);
                JLabel scoreLabel = new JLabel(
                        "<html><p style='margin:0 7px 0 0px;'>" + String.valueOf(score) + " PTS" + "</p></html>");
                scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                nomor.setPreferredSize(new Dimension(40, 30));
                namaLabel.setPreferredSize(new Dimension(160, 30));
                scoreLabel.setPreferredSize(new Dimension(120, 30));

                nomor.setFont(new Font("Nirmala UI", Font.BOLD, 15));
                namaLabel.setFont(new Font("Nirmala UI", Font.BOLD, 15));
                scoreLabel.setFont(new Font("Nirmala UI", Font.BOLD, 15));

                nomor.setForeground(Color.WHITE);
                namaLabel.setForeground(Color.WHITE);
                scoreLabel.setForeground(Color.WHITE);

                if (number % 2 == 1) {
                    nomor.setBackground(Color.decode("#0a49eb"));
                    namaLabel.setBackground(Color.decode("#0a49eb"));
                    scoreLabel.setBackground(Color.decode("#0a49eb"));
                } else {
                    nomor.setBackground(Color.decode("#50a6ff"));
                    namaLabel.setBackground(Color.decode("#50a6ff"));
                    scoreLabel.setBackground(Color.decode("#50a6ff"));
                }

                nomor.setOpaque(true);
                namaLabel.setOpaque(true);
                scoreLabel.setOpaque(true);

                panel.add(nomor);
                panel.add(namaLabel);
                panel.add(scoreLabel);
            }

            rs.close();
            pst.close();
            Database.closeConnection();
        } catch (SQLException e) {
            System.out.println("Failed to fetch data: " + e.getMessage());
        }

        getContentPane().add(panel, BorderLayout.CENTER);
    }
}
