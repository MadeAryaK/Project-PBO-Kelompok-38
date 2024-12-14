import java.awt.*;
import javax.swing.*;

public class PlayerArea extends JPanel {
	private static final long serialVersionUID = 1L;
	final int WIDTH = (830) / 2;
	final int HEIGHT = 275;

	Image icon;
	int x = (int) (WIDTH - 301) / 2;
	int y = 0;
	int yVelocity = 1;
	private boolean isMovingDown = true;

	// Untuk zoom in/out
	private int originalWidth = 200; // Ukuran awal gambar
	private int originalHeight = 200;
	private int currentWidth = originalWidth;
	private int currentHeight = originalHeight;
	private boolean isZoomingIn = true;

	PlayerArea() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setOpaque(true);

		// Menambahkan Timer untuk animasi zoom
		Timer timer = new Timer(30, e -> {
			zoom(); // Panggil zoom() setiap 30ms
			repaint(); // Memastikan tampilan diperbarui
		});
		timer.start();
	}

	void setDead(Image icon) {
		Image resized = icon.getScaledInstance(originalWidth, originalHeight, Image.SCALE_SMOOTH);
		this.icon = resized;
		x = 77;
		y = 37;
		repaint();
	}

	void setImage(Image icon) {
		Image resized = icon.getScaledInstance(originalWidth, originalHeight, Image.SCALE_SMOOTH);
		this.icon = resized;
		repaint();
	}

	Image getImage() {
		return icon;
	}

	public void paint(Graphics g) {
		super.paint(g); // paint background

		Graphics2D g2D = (Graphics2D) g;

		// Gambar ulang dengan ukuran yang disesuaikan (zoom in/out)
		if (icon != null) {
			int centerX = (WIDTH - currentWidth) / 2;
			int centerY = (HEIGHT - currentHeight) / 2;
			g2D.drawImage(icon, centerX, centerY, currentWidth, currentHeight, null);
		}
	}

	public void move() {
		if (y >= HEIGHT - icon.getHeight(null) || y < 0) {
			isMovingDown = !isMovingDown;
		}

		if (isMovingDown) {
			y += yVelocity;
		} else {
			y -= yVelocity;
		}
		repaint();
	}

	// Gerakan zoom in/zoom out
	public void zoom() {
		if (isZoomingIn) {
			currentWidth += 2;
			currentHeight += 2;
			if (currentWidth >= originalWidth + 50) { // Maksimal zoom in
				isZoomingIn = false;
			}
		} else {
			currentWidth -= 2;
			currentHeight -= 2;
			if (currentWidth <= originalWidth - 50) { // Maksimal zoom out
				isZoomingIn = true;
			}
		}
	}
}
