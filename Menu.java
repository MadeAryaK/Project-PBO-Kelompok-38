import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JPanel {

    final int WIDTH = 850;
    final int HEIGHT = 475;

    JButton play = new RoundedButton("PLAY");
    JButton leaderboard = new RoundedButton("LEADERBOARD");
    JButton about = new RoundedButton("ABOUT");
    JButton exit = new RoundedButton("EXIT");
    JPanel buttonContainer = new JPanel();

    JLabel judulLabel = new JLabel("ROCK PAPER SCISSOR");
    JPanel judulContainer = new JPanel() {
        // Override paintComponent untuk border melengkung
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Menggambar border melengkung dengan radius 30
        }
    };

    Menu() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        this.setBackground(Color.decode("#d5edff"));
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
                removeAll();
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
                });
                revalidate();
                repaint();
            }
        });

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
                removeAll();
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

        // Menggunakan GridBagLayout agar tombol berada di tengah
        buttonContainer.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;   // kolom 0 (tengah)
        gbc.gridy = GridBagConstraints.RELATIVE;  // Urutkan vertikal
        gbc.insets = new Insets(10, 0, 10, 0);   // Jarak antar tombol
        buttonContainer.add(play, gbc);
        buttonContainer.add(leaderboard, gbc);
        buttonContainer.add(about, gbc);
        buttonContainer.add(exit, gbc);

        buttonContainer.setBackground(Color.decode("#6d6d8f"));
        buttonContainer.setPreferredSize(new Dimension(500, 350));

        // Mengatur judulContainer agar menggunakan FlowLayout.CENTER
        judulContainer.setLayout(new FlowLayout(FlowLayout.CENTER)); // Menambahkan FlowLayout.CENTER
        judulContainer.setPreferredSize(new Dimension(500, 100));
        judulContainer.setBackground(Color.decode("#0b0b45"));
        judulContainer.setOpaque(true); // Tetap menggunakan opaque true agar background terlihat

        // Menggunakan EmptyBorder untuk memberikan margin atas
        judulContainer.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0)); // Menambahkan margin atas

        // Mengubah font judulLabel agar lebih estetis dan mencerminkan sebuah game
        judulLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        judulLabel.setForeground(Color.WHITE);
        judulLabel.setOpaque(false);

        judulContainer.add(judulLabel); // Menambahkan label ke dalam container
        this.add(judulContainer);
        this.add(buttonContainer);
    }

    // RoundedButton class tetap sama seperti sebelumnya
    public class RoundedButton extends JButton {
        private int radius;

        // Konstruktor untuk RoundedButton
        public RoundedButton(String text) {
            super(text);
            this.radius = 30; // radius lebih besar untuk sudut yang lebih melengkung
            setContentAreaFilled(false); // Menghindari area konten yang tidak berbentuk bulat
            setBackground(Color.decode("#000080")); //  warna tombol menjadi biru navy
            setForeground(Color.WHITE); //  warna teks menjadi putih
            setFont(new Font("Comic Sans MS", Font.BOLD, 20)); //  font agar lebih estetis dan mencerminkan game
        }

        // tombol dengan sudut melengkung
        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isPressed()) {
                g.setColor(getBackground().darker());
            } else if (getModel().isRollover()) {
                g.setColor(getBackground().brighter());
            } else {
                g.setColor(getBackground());
            }

            g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius); // Menimpa area tombol dengan sudut melengkung
            super.paintComponent(g); // Memastikan teks tetap digambar
        }

        // Mengatur border tombol
        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius); // Menambah border bulat
        }

        // Mengubah ukuran tombol
        @Override
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            size.width = 200;
            size.height = 60; // Mengubah tinggi tombol agar lebih besar
            return size;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rock Paper Scissor Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new Menu());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
