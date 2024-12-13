import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;

public class Menu extends JPanel {

    final int WIDTH = 850;
    final int HEIGHT = 475;

    JButton play = new RoundedButton("PLAY", Color.decode("#00BFFF")); // Biru
    JButton leaderboard = new RoundedButton("LEADERBOARD", Color.decode("#32CD32")); // Hijau
    JButton exit = new RoundedButton("EXIT", Color.decode("#FF6347")); // Merah
    JPanel buttonContainer = new JPanel();

    JLabel judulLabel = new JLabel("ROCK PAPER SCISSOR");
    JPanel judulContainer = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        }
    };

    Image backgroundImage;

    Menu(String imagePath) {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        try {
            backgroundImage = ImageIO
                    .read(new File("src\\\\asset\\\\Background.jpeg"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
                removeAll();
                add(new GamePanel());
                revalidate();
                repaint();
            }
        });

        leaderboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException evt) {
                        evt.printStackTrace();
                    }
                    Leaderboard leaderboardFrame = new Leaderboard();
                    leaderboardFrame.setVisible(true);
                });
                revalidate();
                repaint();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonContainer.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        buttonContainer.add(play, gbc);
        buttonContainer.add(leaderboard, gbc);
        buttonContainer.add(exit, gbc);

        buttonContainer.setBackground(Color.decode("#FFFFFF"));
        buttonContainer.setPreferredSize(new Dimension(500, 350));

        judulContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        judulContainer.setPreferredSize(new Dimension(500, 100));
        judulContainer.setBackground(Color.decode("#27cf9f"));
        judulContainer.setOpaque(true);
        judulContainer.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        judulLabel.setFont(new Font("Monospaced", Font.BOLD, 30));
        judulLabel.setForeground(Color.WHITE);
        judulLabel.setOpaque(false);

        judulContainer.add(judulLabel);
        this.add(judulContainer);
        this.add(buttonContainer);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this);
        }
    }

    public class RoundedButton extends JButton {
        private int radius;
        private Color buttonColor;

        public RoundedButton(String text, Color color) {
            super(text);
            this.radius = 30;
            this.buttonColor = color; // Set warna tombol
            setContentAreaFilled(false);
            setBackground(buttonColor);
            setForeground(Color.WHITE);
            setFont(new Font("Monospaced", Font.BOLD, 20));
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isPressed()) {
                g.setColor(getBackground().darker());
            } else if (getModel().isRollover()) {
                g.setColor(getBackground().brighter());
            } else {
                g.setColor(getBackground());
            }

            g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            size.width = 200;
            size.height = 60;
            return size;
        }
    }
}
