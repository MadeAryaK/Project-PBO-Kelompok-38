// Source code is decompiled from a .class file using FernFlower decompiler.
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BotArea extends JPanel {
   private static final long serialVersionUID = 1L;
   final int WIDTH = 415;
   final int HEIGHT = 275;
   Image icon;
   int x = 157;
   int y = 74;
   int yVelocity = 1;
   private boolean isMovingDown = true;
   private int originalWidth = 200;
   private int originalHeight = 200;
   private int currentWidth;
   private int currentHeight;
   private boolean isZoomingIn;

   BotArea() {
      this.currentWidth = this.originalWidth;
      this.currentHeight = this.originalHeight;
      this.isZoomingIn = true;
      this.setPreferredSize(new Dimension(415, 275));
      this.setOpaque(true);
      Timer timer = new Timer(30, (e) -> {
         this.zoom();
         this.repaint();
      });
      timer.start();
   }

   void setDead(Image icon) {
      Image resized = icon.getScaledInstance(200, 200, 4);
      this.icon = resized;
      this.x = 147;
      this.y = 37;
      this.repaint();
   }

   void setImage(Image icon) {
      Image resized = icon.getScaledInstance(200, 200, 4);
      this.icon = resized;
      this.repaint();
   }

   Image getImage() {
      return this.icon;
   }

   public void paint(Graphics g) {
      super.paint(g);
      Graphics2D g2D = (Graphics2D)g;
      if (this.icon != null) {
         int centerX = (415 - this.currentWidth) / 2;
         int centerY = (275 - this.currentHeight) / 2;
         g2D.drawImage(this.icon, centerX, centerY, this.currentWidth, this.currentHeight, (ImageObserver)null);
      }

   }

   public void move() {
      if (this.y >= 275 - this.icon.getHeight((ImageObserver)null) || this.y < 0) {
         this.isMovingDown = !this.isMovingDown;
      }

      if (this.isMovingDown) {
         this.y += this.yVelocity;
      } else {
         this.y -= this.yVelocity;
      }

      this.repaint();
   }

   public void zoom() {
      if (this.isZoomingIn) {
         this.currentWidth += 2;
         this.currentHeight += 2;
         if (this.currentWidth >= this.originalWidth + 50) {
            this.isZoomingIn = false;
         }
      } else {
         this.currentWidth -= 2;
         this.currentHeight -= 2;
         if (this.currentWidth <= this.originalWidth - 50) {
            this.isZoomingIn = true;
         }
      }

   }
}
