import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.MatteBorder;

public class TimerCustome {
	private int seconds;
	private String time;
	private JLabel timerLabel = new JLabel();
	private Thread timerThread;
	private boolean running = true;

	TimerCustome(int seconds) {
		this.seconds = seconds;
		timerLabel.setOpaque(true);
		timerLabel.setBorder(new MatteBorder(4, 4, 4, 4, Color.decode("#2f6eff")));
		timerLabel.setOpaque(false);
		timerLabel.setFont(new Font("Nirmala UI", 1, 20));
		timerLabel.setForeground(Color.decode("#10329f"));
		timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		time = String.valueOf(seconds);
		timerLabel.setText(time);
	}

	String getTime() {
		return this.time;
	}

	boolean hasExpired() {
		return seconds <= 0;
	}
	JLabel getLabel() {
        return timerLabel;
    }
	void start() {
		if (timerThread == null || !timerThread.isAlive()) {
			timerThread = new Thread(new Runnable() {
				public void run() {
					while (running && seconds >= 0) {
						time = String.valueOf(seconds);
						SwingUtilities.invokeLater(() -> timerLabel.setText(time));
						seconds--;
						timerLabel.repaint();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
					}
					SwingUtilities.invokeLater(() -> timerLabel.setText("Time's up!"));
				}
			});
			timerThread.start();
		}
	}
	void stopTimer() {
		this.running = false;
        timerThread.interrupt();
		timerLabel.setText("GAME OVER");
		timerLabel.repaint();
    }
	int getSeconds() {
        return this.seconds;
    }
	void restart(int newSeconds) {
        this.seconds = newSeconds;
        this.time = String.valueOf(seconds);
        this.running = true;
        timerLabel.setText(time);
        start();
    }
}
