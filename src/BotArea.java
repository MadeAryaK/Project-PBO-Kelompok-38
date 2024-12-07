import java.awt.*;
import javax.swing.*;

public class BotArea extends JPanel {
	private static final long serialVersionUID = 1L;
	final int WIDTH = (830) / 2;
	final int HEIGHT = 275;

	Image icon;
	int x = (int) (WIDTH - 101) / 2;
	int y = (int) (HEIGHT - 201);
	int yVelocity = 1;
	private boolean isMovingDown = true;

	BotArea() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setOpaque(true);
	}

	void setDead(Image icon) {
		Image resized = icon.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		this.icon = resized;
		x = (int) 147;
		y = (int) 37;
		repaint();
	}

	void setImage(Image icon) {
		Image resized = icon.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		this.icon = resized;
		repaint();
	}

	Image getImage() {
		return icon;
	}

	public void paint(Graphics g) {

		super.paint(g);

		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(icon, x, y, null);
		repaint();
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

}
