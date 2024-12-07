import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;
import API.Database;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 7841699317463796446L;
	final int WIDTH = 850;
	final int HEIGHT = 475;
	JPanel nameContainer = new JPanel();
	JLabel youLabel = new JLabel();
	JLabel computerLabel = new JLabel();
	private Image backgroundImage;

	JPanel panelHealth = new JPanel();
	TimerCustome timerLabel = new TimerCustome(5);
	JProgressBar playerHealthBar = new JProgressBar();
	JProgressBar botHealthBar = new JProgressBar();

	JPanel panelEnergy = new JPanel();
	JLabel scoreLabel = new JLabel();
	JProgressBar playerEnergyBar = new JProgressBar();
	JProgressBar botEnergyBar = new JProgressBar();

	JButton batuButton = new JButton();
	JButton kertasButton = new JButton();
	JButton guntingButton = new JButton();
	JButton exitButton = new JButton();
	JToggleButton skill1Button = new JToggleButton();
	JToggleButton skill2Button = new JToggleButton();

	Image kertas = new ImageIcon("D:\\SEMESTER 5\\PBO\\Project\\ProjekPBO - Copy\\src\\asset\\KERTASCAT.jpeg")
			.getImage();
	Image batu = new ImageIcon("D:\\SEMESTER 5\\PBO\\Project\\ProjekPBO - Copy\\src\\asset\\BATUCAT.jpeg").getImage();
	Image gunting = new ImageIcon("D:\\SEMESTER 5\\PBO\\Project\\ProjekPBO - Copy\\src\\asset\\GUNTINGCAT.jpeg")
			.getImage();
	Image mati = new ImageIcon("D:\\SEMESTER 5\\PBO\\Project\\ProjekPBO\\src\\asset\\mati.png").getImage();

	PlayerArea playerArea = new PlayerArea();
	BotArea botArea = new BotArea();

	Bot bot = new Bot("YOU", 1000, 100, 200, 30);
	User user = new User("BOT", 1000, 100, 200, 30);

	Thread playerMovement;
	Thread botMovement;

	int score = 0;
	boolean startPlay = false;

	GamePanel() {

		try {
			// Muat gambar latar belakang dari path yang sudah Anda tentukan
			backgroundImage = new ImageIcon(
					"D:\\SEMESTER 5\\PBO\\Project\\ProjekPBO - Copy\\src\\asset\\Baground.jpg")
					.getImage(); // Ganti dengan path gambar Anda
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		youLabel.setPreferredSize(new Dimension(330, 0));
		youLabel.setText("YOU");
		youLabel.setForeground(Color.decode("#10329f"));
		youLabel.setHorizontalAlignment(SwingConstants.CENTER);
		youLabel.setFont(new Font("Monospaced", 1, 20));
		computerLabel.setPreferredSize(new Dimension(330, 0));
		computerLabel.setText("BOT");
		computerLabel.setForeground(Color.decode("#10329f"));
		computerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		computerLabel.setFont(new Font("Monospaced", 1, 20));

		nameContainer.setOpaque(false);
		nameContainer.setLayout(new BorderLayout());
		nameContainer.setPreferredSize(new Dimension(WIDTH - 10, 25));
		nameContainer.add(youLabel, BorderLayout.WEST);
		nameContainer.add(computerLabel, BorderLayout.EAST);

		panelHealth.setPreferredSize(new Dimension(WIDTH - 10, 40));
		panelHealth.setOpaque(false);
		panelHealth.setLayout(new BorderLayout());

		playerHealthBar.setBackground(Color.decode("#FFA500"));
		botHealthBar.setBackground(Color.decode("#FFA500"));
		playerHealthBar.setForeground(Color.decode("#32CD32")); // green
		botHealthBar.setForeground(Color.decode("#32CD32")); // green
		playerHealthBar.setPreferredSize(new Dimension(330, 0));
		botHealthBar.setPreferredSize(new Dimension(330, 0));
		playerHealthBar.setStringPainted(true);
		botHealthBar.setStringPainted(true);
		playerHealthBar.setBorderPainted(false);
		botHealthBar.setBorderPainted(false);

		playerHealthBar.setMinimum(0);
		botHealthBar.setMinimum(0);
		playerHealthBar.setMaximum(1000);
		botHealthBar.setMaximum(1000);
		playerHealthBar.setValue(user.getHealth());
		botHealthBar.setValue(bot.getHealth());
		playerHealthBar.setFont(new Font("Monospaced", 1, 18));
		botHealthBar.setFont(new Font("Monospaced", 1, 18));
		playerHealthBar.setString(playerHealthBar.getValue() + "/" + playerHealthBar.getValue());
		botHealthBar.setString(botHealthBar.getValue() + "/" + botHealthBar.getValue());

		playerHealthBar.setUI(new BasicProgressBarUI() {
			protected Color getSelectionBackground() {
				return Color.decode("#66FF66"); // Warna teks pada progress bar
			}

			protected Color getSelectionForeground() {
				return Color.decode("#FF3300"); // Warna teks pada progress bar
			}
		});
		botHealthBar.setUI(new BasicProgressBarUI() {
			protected Color getSelectionBackground() {
				return Color.decode("#66FF66"); // Warna teks pada progress bar
			}

			protected Color getSelectionForeground() {
				return Color.decode("#FF3300"); // Warna teks pada progress bar
			}
		});

		panelHealth.add(timerLabel.getLabel(), BorderLayout.CENTER);
		panelHealth.add(playerHealthBar, BorderLayout.WEST);
		panelHealth.add(botHealthBar, BorderLayout.EAST);

		////////////////////////////

		panelEnergy.setPreferredSize(new Dimension(WIDTH - 10, 25));
		panelEnergy.setOpaque(false);
		panelEnergy.setLayout(new BorderLayout());

		scoreLabel.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(Color.decode("#3465ff"), 2), // Border biru
				BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding: top, left, bottom, right
		));
		panelEnergy.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4)); // Menambahkan padding di seluruh panel

		playerEnergyBar.setBackground(Color.decode("#2205ff"));
		botEnergyBar.setBackground(Color.decode("#2205ff"));
		playerEnergyBar.setForeground(Color.decode("#00BFFF"));
		botEnergyBar.setForeground(Color.decode("#00BFFF"));
		playerEnergyBar.setPreferredSize(new Dimension(330, 0));
		botEnergyBar.setPreferredSize(new Dimension(330, 0));
		playerEnergyBar.setStringPainted(true);
		botEnergyBar.setStringPainted(true);
		playerEnergyBar.setBorderPainted(false);
		botEnergyBar.setBorderPainted(false);

		playerEnergyBar.setMinimum(0);
		botEnergyBar.setMinimum(0);
		playerEnergyBar.setMaximum(200);
		botEnergyBar.setMaximum(200);
		playerEnergyBar.setValue(user.getEnergy());
		botEnergyBar.setValue(bot.getEnergy());
		playerEnergyBar.setFont(new Font("Monospaced", 1, 12));
		botEnergyBar.setFont(new Font("Monospaced", 1, 12));
		playerEnergyBar.setString(playerEnergyBar.getValue() + "/" + playerEnergyBar.getValue());
		botEnergyBar.setString(botEnergyBar.getValue() + "/" + botEnergyBar.getValue());

		playerEnergyBar.setUI(new BasicProgressBarUI() {
			protected Color getSelectionBackground() {
				return Color.decode("#5bf1ff"); // Warna teks pada progress bar
			}

			protected Color getSelectionForeground() {
				return Color.decode("#2205ff"); // Warna teks pada progress bar
			}
		});
		botEnergyBar.setUI(new BasicProgressBarUI() {
			protected Color getSelectionBackground() {
				return Color.decode("#5bf1ff"); // Warna teks pada progress bar
			}

			protected Color getSelectionForeground() {
				return Color.decode("#2205ff"); // Warna teks pada progress bar
			}
		});

		scoreLabel.setFont(new Font("Monospaced", 1, 12));
		scoreLabel.setOpaque(false);
		scoreLabel.setForeground(Color.decode("#10329f"));
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setText("SCORE : 0");

		panelEnergy.add(scoreLabel, BorderLayout.CENTER);
		scoreLabel.setBorder(new LineBorder(Color.decode("#3465ff"), 2));
		panelEnergy.add(playerEnergyBar, BorderLayout.WEST);
		panelEnergy.add(botEnergyBar, BorderLayout.EAST);

		playerArea.setOpaque(false); // Make background transparent
		botArea.setOpaque(false); // Make background transparent

		// Set borders (only edges will be colored)
		playerArea.setBorder(new LineBorder(Color.decode("#3465ff"), 2)); // Blue border for player
		botArea.setBorder(new LineBorder(Color.decode("#ff9900"), 2)); // Orange border for bot

		/////////////////////////////

		playerMovement = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					playerArea.move();
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		});
		botMovement = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					botArea.move();
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		});

		batuButton.setFont(new Font("Monospaced", 1, 18));
		batuButton.setPreferredSize(new Dimension(150, 35));
		batuButton.setBackground(Color.decode("#3465ff"));
		batuButton.setForeground(Color.WHITE);
		batuButton.setText("ROCK");
		batuButton.setFocusable(false);

		guntingButton.setFont(new Font("Monospaced", 1, 18));
		guntingButton.setPreferredSize(new Dimension(150, 35));
		guntingButton.setBackground(Color.decode("#3465ff"));
		guntingButton.setForeground(Color.WHITE);
		guntingButton.setText("SCISSORS");
		guntingButton.setFocusable(false);

		kertasButton.setFont(new Font("Monospaced", 1, 18));
		kertasButton.setPreferredSize(new Dimension(150, 35));
		kertasButton.setBackground(Color.decode("#3465ff"));
		kertasButton.setForeground(Color.WHITE);
		kertasButton.setText("PAPER");
		kertasButton.setFocusable(false);

		exitButton.setFont(new Font("Monospaced", 1, 12));
		exitButton.setPreferredSize(new Dimension(150, 35));
		exitButton.setBackground(Color.decode("#FF5500"));
		exitButton.setForeground(Color.WHITE);
		exitButton.setText("EXIT");
		exitButton.setFocusable(false);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Membuat panel untuk dialog kustom
				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout());

				// Menambahkan teks kustom
				JLabel messageLabel = new JLabel(
						"<html><font size='5'>Apakah kamu yakin ingin keluar dari permainan?</font></html>");
				messageLabel.setFont(new Font("Monospaced", Font.PLAIN, 18));
				panel.add(messageLabel, BorderLayout.CENTER);

				// Membuat tombol Yes/No dengan gaya kustom
				JPanel buttonPanel = new JPanel();
				JButton yesButton = new JButton("Yes");
				yesButton.setBackground(Color.decode("#FF5500"));
				yesButton.setForeground(Color.WHITE);
				yesButton.setFont(new Font("Monospaced", Font.BOLD, 14));
				yesButton.setFocusPainted(false);

				JButton noButton = new JButton("No");
				noButton.setBackground(Color.decode("#66cc66"));
				noButton.setForeground(Color.WHITE);
				noButton.setFont(new Font("Monospaced", Font.BOLD, 14));
				noButton.setFocusPainted(false);

				// Aksi untuk tombol Yes
				yesButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						playerMovement.interrupt();
						botMovement.interrupt();
						System.exit(0);
					}
				});

				// Aksi untuk tombol No
				noButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Menutup dialog tanpa melakukan apa-apa
					}
				});

				buttonPanel.add(yesButton);
				buttonPanel.add(noButton);
				panel.add(buttonPanel, BorderLayout.SOUTH);

				// Menampilkan dialog kustom dengan latar belakang dan ukuran tertentu
				JOptionPane.showOptionDialog(null, panel, "Konfirmasi Keluar", JOptionPane.DEFAULT_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
			}
		});

		skill1Button.setFont(new Font("Monospaced", 1, 12));
		skill1Button.setPreferredSize(new Dimension(150, 35));
		skill1Button.setBackground(Color.decode("#27cf9f"));
		skill1Button.setForeground(Color.WHITE);
		skill1Button.setText("HEAL");
		skill1Button.setFocusable(false);

		skill2Button.setFont(new Font("Monospaced", 1, 12));
		skill2Button.setPreferredSize(new Dimension(150, 35));
		skill2Button.setBackground(Color.decode("#FF8800"));
		skill2Button.setForeground(Color.WHITE);
		skill2Button.setText("CRITICAL");
		skill2Button.setFocusable(false);

		batuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timerLabel.hasExpired()) {
					timerLabel.restart(5);
					botAttack(10);
					repaint();
					revalidate();
				} else {
					timerLabel.restart(5);
					playerArea.setImage(batu);
					user.setSign("batu");
					botPick();
					startPlay();
					checkWin();
					repaint();
					revalidate();
				}

			}
		});
		guntingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timerLabel.hasExpired()) {
					timerLabel.restart(5);
					botAttack(10);
					repaint();
					revalidate();
				} else {
					timerLabel.restart(5);
					playerArea.setImage(gunting);
					user.setSign("gunting");
					botPick();
					startPlay();
					checkWin();
					repaint();
					revalidate();
				}

			}
		});
		kertasButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timerLabel.hasExpired()) {
					timerLabel.restart(5);
					botAttack(10);
					repaint();
					revalidate();
				} else {
					timerLabel.restart(5);
					playerArea.setImage(kertas);
					user.setSign("kertas");
					botPick();
					startPlay();
					checkWin();
					repaint();
					revalidate();
				}

			}
		});

		skill1Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (skill1Button.isSelected() == true) {
					skill2Button.setSelected(false);
				}
			}
		});
		skill2Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (skill2Button.isSelected() == true) {
					skill1Button.setSelected(false);
				}
			}
		});

		this.add(nameContainer);
		this.add(panelHealth);
		this.add(panelEnergy);
		this.add(playerArea);
		this.add(botArea);
		this.add(batuButton);
		this.add(kertasButton);
		this.add(guntingButton);
		this.add(skill1Button);
		this.add(skill2Button);
		this.add(exitButton);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Gambar background pada panel
		if (backgroundImage != null) {
			g.drawImage(backgroundImage, 0, 0, this); // Gambar mulai dari titik (0,0)
		}
	}

	void checkWin() {
		if (timerLabel.hasExpired()) { // damage akan terlihat ketika user menyerang bot (serangan biasa), serangan
										// yang dipilih (gunting/batu/kertas) tidak akan ditampilkan (tidak dihitung
										// serangan) namun secara otomatis pengguna akan dikurangi 10 (bot akan
										// melakukan serangan ke pemain dengan kekuatan serangan sebesar 10 poin
										// menggunakan botAttack(10))
			botAttack(10);
		} else if (user.getSign().equals(bot.getSign())) { // jika kondisi seri (misalnya keduanya memilih batu, atau
															// keduanya memilih gunting), maka permainan dianggap seri)
			System.out.println("SERI");

			// KONDISI PEMAIN MENANG
		} else if ((user.getSign().equals("batu") && bot.getSign().equals("gunting"))
				|| (user.getSign().equals("gunting") && bot.getSign().equals("kertas"))
				|| (user.getSign().equals("kertas") && bot.getSign().equals("batu"))) {

			if (user.getEnergy() >= 200) { // kondisi jika energy lebih dari 200

				/*
				 * jika tombol skill 1 dipilih, maka 200 poin akan ditambahkan ke skor pemain,
				 * pemain menggunakan skill penyembuhan (user.skill1()), dan bar kesehatan
				 * pemain diperbarui.
				 * Energi pemain diset ke 0 setelah keterampilan digunakan.
				 */
				if (skill1Button.isSelected() == true) { // HEAL
					score += 200;
					scoreLabel.setText("SCORE : " + String.valueOf(score));
					scoreLabel.repaint();

					user.skill1();
					playerHealthBar.setValue(user.getHealth());
					playerHealthBar.setString(playerHealthBar.getValue() + "/1000");

					user.setEnergy(0);
					playerEnergyBar.setValue(user.getEnergy());
					playerEnergyBar.setString(playerEnergyBar.getValue() + "/200");
					skill1Button.setSelected(false);

					/*
					 * Jika skill 2 dipilih, serangan kritis akan dilakukan menggunakan
					 * user.skill2().
					 * Pemain mendapatkan 200 poin (sko) tambahan, energi mereka diset ke 0, dan bar
					 * energi diperbarui.
					 */
				} else if (skill2Button.isSelected() == true) { // CRITICAL
					score += 200;
					scoreLabel.setText("SCORE : " + String.valueOf(score));
					scoreLabel.repaint();

					userAttack(user.skill2());

					user.setEnergy(0);
					playerEnergyBar.setValue(user.getEnergy());
					playerEnergyBar.setString(playerEnergyBar.getValue() + "/200");
					skill2Button.setSelected(false);

					/*
					 * Pemain mendapatkan 50 poin dan melakukan serangan normal terhadap bot.
					 * Setelah serangan, energi bot bertambah 25 poin, dan energi pemain bertambah
					 * 10 poin
					 */
				} else { // serangan normal
					score += 50;
					scoreLabel.setText("SCORE : " + String.valueOf(score));
					scoreLabel.repaint();

					userAttack(user.getAttack() - bot.getDefence());

					bot.setEnergy(bot.getEnergy() + 25); // energy bot bertambah 25 ketika diserang user
					botEnergyBar.setValue(bot.getEnergy());
					botEnergyBar.setString(botEnergyBar.getValue() + "/200");

					user.setEnergy(user.getEnergy() + 10); // energy user bertambah ketika berhasil menyerang
															// (menglahakn) bot
					playerEnergyBar.setValue(user.getEnergy());
					playerEnergyBar.setString(playerEnergyBar.getValue() + "/200");
				}

				/*
				 * Jika energi pemain kurang dari 200, maka pemain tetap mendapatkan 50 poin dan
				 * melakukan serangan normal terhadap bot (serupa dengan kondisi sebelumnya).
				 * Energi bot bertambah 25, dan energi pemain bertambah 10
				 */
			} else { // kondisi jika energy kurang dari 200
				score += 50;
				scoreLabel.setText("SCORE : " + String.valueOf(score));
				scoreLabel.repaint();

				userAttack(user.getAttack() - bot.getDefence());

				bot.setEnergy(bot.getEnergy() + 25); // Energi bot bertambah 25
				botEnergyBar.setValue(bot.getEnergy());
				botEnergyBar.setString(botEnergyBar.getValue() + "/200");

				user.setEnergy(user.getEnergy() + 10); // energi pemain bertambah 10
				playerEnergyBar.setValue(user.getEnergy());
				playerEnergyBar.setString(playerEnergyBar.getValue() + "/200");
			}

			// KONDISI BOT MENANG
		} else if ((bot.getSign().equals("batu") && user.getSign().equals("gunting"))
				|| (bot.getSign().equals("gunting") && user.getSign().equals("kertas"))
				|| (bot.getSign().equals("kertas") && user.getSign().equals("batu"))) {

			// Jika energi bot lebih besar dari atau sama dengan 200, maka bot dapat
			// menggunakan salah satu dari kemampuan khususnya.
			if (bot.getEnergy() >= 200) {

				Random random = new Random(); // pilihan random
				int pick = random.nextInt(3);
				if (pick == 0) { // jika pilihan acak = 0 maka bot akan menggunakan skill 1, dan
					score -= 50; // maka kor pemain berkurang 50
					if (score <= 0) {
						score = 0;
					}
					scoreLabel.setText("SCORE : " + String.valueOf(score));
					scoreLabel.repaint();

					bot.skill1();
					botHealthBar.setValue(bot.getHealth());
					botHealthBar.setString(botHealthBar.getValue() + "/1000");

					bot.setEnergy(0);
					botEnergyBar.setValue(bot.getEnergy());
					botEnergyBar.setString(botEnergyBar.getValue() + "/200");

				} else if (pick == 1) { // jika pilihan acak = 1 maka bot akan menggunakan skill 2, dan
					score -= 50; // skor penggun - 50
					if (score <= 0) {
						score = 0;
					}
					scoreLabel.setText("SCORE : " + String.valueOf(score));
					scoreLabel.repaint();

					botAttack(bot.skill2());

					bot.setEnergy(0);
					botEnergyBar.setValue(bot.getEnergy());
					botEnergyBar.setString(botEnergyBar.getValue() + "/200");

				} else if (pick == 2) { // Jika pilihan acak menghasilkan 2, maka bot melakukan serangan normal terhadap
										// pemain. dan
					score -= 10; // skor pemain -10
					if (score <= 0) {
						score = 0;
					}
					scoreLabel.setText("SCORE : " + String.valueOf(score));
					scoreLabel.repaint();

					botAttack(bot.getAttack() - user.getDefence());
				}

				// jika energi bot kurang dari 200, bot melakukan serangan normal terhadap
				// pemain:
			} else {
				score -= 10; // Skor pemain berkurang 10 poin.
				if (score <= 0) {
					score = 0;
				}
				scoreLabel.setText("SCORE : " + String.valueOf(score));
				scoreLabel.repaint();

				botAttack(bot.getAttack() - user.getDefence()); // bot melakukan serangan normal

				bot.setEnergy(bot.getEnergy() + 10); // energi bot bertambah 10
				botEnergyBar.setValue(bot.getEnergy());
				botEnergyBar.setString(botEnergyBar.getValue() + "/200");

				user.setEnergy(user.getEnergy() + 25); // energi pemain bertambah 25
				playerEnergyBar.setValue(user.getEnergy());
				playerEnergyBar.setString(playerEnergyBar.getValue() + "/200");
			}
		}
	}

	void userAttack(int damage) {
		Thread attack = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < damage; i++) {
					bot.setHealth(bot.getHealth() - 1);
					botHealthBar.setValue(bot.getHealth());
					botHealthBar.setString(botHealthBar.getValue() + "/1000");
					try {
						Thread.sleep(5);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (bot.dead()) {
					updateScore(user.getName(), 940);

					timerLabel.stopTimer();
					botArea.setDead(mati);
					botMovement.interrupt();
					repaint();

					batuButton.setEnabled(false);
					guntingButton.setEnabled(false);
					kertasButton.setEnabled(false);

					// Declare inputFrame here
					JFrame inputFrame = new JFrame();

					// Membuat panel untuk dialog kustom
					JPanel panel = new JPanel();
					panel.setLayout(new BorderLayout());

					// Menambahkan teks kustom untuk "ENTER NAME"
					JLabel messageLabel = new JLabel(
							"<html><font size='5'>ENTER YOUR NAME!</font></html>");
					messageLabel.setFont(new Font("Monospaced", Font.PLAIN, 18));
					messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(messageLabel, BorderLayout.CENTER);

					// Membuat text field untuk input nama
					JTextField textInput = new JTextField();
					textInput.setPreferredSize(new Dimension(250, 25));

					// Membuat tombol Submit dan Cancel dengan gaya kustom
					JPanel buttonPanel = new JPanel();
					JButton submitButton = new JButton("Submit");
					submitButton.setBackground(Color.decode("#FF5500"));
					submitButton.setForeground(Color.WHITE);
					submitButton.setFont(new Font("Monospaced", Font.BOLD, 14));
					submitButton.setFocusPainted(false);

					JButton cancelButton = new JButton("Cancel");
					cancelButton.setBackground(Color.decode("#66cc66"));
					cancelButton.setForeground(Color.WHITE);
					cancelButton.setFont(new Font("Monospaced", Font.BOLD, 14));
					cancelButton.setFocusPainted(false);

					// Aksi untuk tombol Submit
					submitButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String inputText = textInput.getText();
							updateScore(inputText, score);
							inputFrame.dispose();
							botMovement.interrupt();
							playerMovement.interrupt();
							timerLabel.stopTimer();
							removeAll();
							add(new Menu("D:\\SEMESTER 5\\PBO\\Project\\ProjekPBO - Copy\\src\\asset\\Baground.jpg"));
							revalidate();
							repaint();
						}
					});

					// Aksi untuk tombol Cancel
					cancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// Menutup dialog tanpa melakukan apa-apa
							inputFrame.dispose();
						}
					});

					buttonPanel.add(submitButton);
					buttonPanel.add(cancelButton);
					panel.add(textInput, BorderLayout.NORTH);
					panel.add(buttonPanel, BorderLayout.SOUTH);

					// Menampilkan dialog kustom dengan latar belakang dan ukuran tertentu
					inputFrame.setSize(370, 150);
					inputFrame.setLocationRelativeTo(null);
					inputFrame.add(panel);
					inputFrame.setVisible(true);
				}
			}
		});
		attack.start();
	}

	void botAttack(int damage) {
		Thread attack = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < damage; i++) {
					user.setHealth(user.getHealth() - 1);
					playerHealthBar.setValue(user.getHealth());
					playerHealthBar.setString(playerHealthBar.getValue() + "/1000");
					try {
						Thread.sleep(5);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (user.dead()) {
					timerLabel.stopTimer();
					playerArea.setDead(mati);
					playerMovement.interrupt();
					repaint();

					batuButton.setEnabled(false);
					guntingButton.setEnabled(false);
					kertasButton.setEnabled(false);

					// Membuat panel untuk dialog kustom
					JPanel panel = new JPanel();
					panel.setLayout(new BorderLayout());

					// Menambahkan teks kustom untuk "YOU LOSE"
					JLabel messageLabel = new JLabel(
							"<html><font size='5'>YOU LOSE!</font></html>");
					messageLabel.setFont(new Font("Monospaced", Font.PLAIN, 18));
					messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(messageLabel, BorderLayout.CENTER);

					// Membuat tombol Yes/No dengan gaya kustom
					JPanel buttonPanel = new JPanel();

					JButton exitButton = new JButton("Exit");
					exitButton.setBackground(Color.decode("#66cc66"));
					exitButton.setForeground(Color.WHITE);
					exitButton.setFont(new Font("Monospaced", Font.BOLD, 14));
					exitButton.setFocusPainted(false);

					// Aksi untuk tombol Restart

					// Aksi untuk tombol Exit
					exitButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							playerMovement.interrupt();
							botMovement.interrupt();
							System.exit(0); // Keluar dari permainan
						}
					});

					// Menambahkan tombol-tombol ke panel
					buttonPanel.add(exitButton);
					panel.add(buttonPanel, BorderLayout.SOUTH);

					// Menampilkan dialog kustom dengan latar belakang dan ukuran tertentu
					JOptionPane.showOptionDialog(null, panel, "Game Over", JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
				}
			}
		});
		attack.start();
	}

	void botPick() {
		ArrayList<Image> imageBot = new ArrayList<>();
		imageBot.add(kertas);
		imageBot.add(batu);
		imageBot.add(gunting);
		Random random = new Random();
		int randompick = random.nextInt(3);
		if (randompick == 0) {
			bot.setSign("kertas");
		} else if (randompick == 1) {
			bot.setSign("batu");
		} else if (randompick == 2) {
			bot.setSign("gunting");
		}
		botArea.setImage(imageBot.get(randompick));
	}

	void startPlay() {
		if (!isPlay()) {
			playerMovement.start();
			botMovement.start();
			setPlay(); // Pembaruan nilai startPlay setelah memulai permainan
		}
	}

	boolean isPlay() {
		return startPlay;
	}

	void setPlay() {
		startPlay = true;
	}

	void updateScore(String nama, int score) {
		String selectQuery = "SELECT id_user FROM user WHERE nama = ?";
		String updateQuery = "UPDATE user SET score = ? WHERE nama = ?";
		String insertQuery = "INSERT INTO user (nama, score) VALUES (?, ?)";

		try {
			Connection connection = Database.getConnection();

			// Mengecek apakah nama sudah ada di tabel user
			PreparedStatement selectPst = connection.prepareStatement(selectQuery);
			selectPst.setString(1, nama);
			ResultSet rs = selectPst.executeQuery();

			if (rs.next()) {
				// Jika nama sudah ada, update nilai score
				PreparedStatement updatePst = connection.prepareStatement(updateQuery);
				updatePst.setInt(1, score);
				updatePst.setString(2, nama);
				updatePst.executeUpdate();
				updatePst.close();
				System.out.println("Score updated for " + nama);
			} else {
				// Jika nama belum ada, lakukan penambahan data baru
				PreparedStatement insertPst = connection.prepareStatement(insertQuery);
				insertPst.setString(1, nama);
				insertPst.setInt(2, score);
				insertPst.executeUpdate();
				insertPst.close();
				System.out.println("New data inserted for " + nama);
			}

			rs.close();
			selectPst.close();
			Database.closeConnection();
		} catch (SQLException e) {
			System.out.println("Insert or update failed: " + e.getMessage());
		}
	}

}