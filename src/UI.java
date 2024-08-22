import javax.swing.*;
import java.awt.*;

public class UI {

   public class Level {
      public final int velocityX;
      public final int velocityY;
      public final int obstacleTime;
      public final int gravity = 1;

      public Level(int x, int y, int time) {
         velocityX = x;
         velocityY = y;
         obstacleTime = time;
      }
   }

   public final Level[] levels = { new Level(-10, -15, 1500), new Level(-12, -16, 1200), new Level(-15, -16, 1000) };

   public final Color backGround = Color.LIGHT_GRAY;
   public final Color fontColor = Color.BLACK;
   public final Font scoreFont = new Font("Courier", Font.PLAIN, 24);

   public final Image dinoImg = new ImageIcon(getClass().getResource("./img/dino-run.gif")).getImage();
   public final Image jumpImg = new ImageIcon(getClass().getResource("img/dino-jump.png")).getImage();
   public final Image duckImg = new ImageIcon(getClass().getResource("img/dino-duck.gif")).getImage();
   public final Image deadImg = new ImageIcon(getClass().getResource("img/dino-dead.png")).getImage();
   public final Image obstacle1 = new ImageIcon(getClass().getResource("img/big-cactus1.png")).getImage();
   public final Image obstacle2 = new ImageIcon(getClass().getResource("img/big-cactus2.png")).getImage();
   public final Image obstacle3 = new ImageIcon(getClass().getResource("img/cactus3.png")).getImage();
   public final Image obstacle4 = new ImageIcon(getClass().getResource("img/big-cactus3.png")).getImage();
   public final Image flyingObstacleImg = new ImageIcon(getClass().getResource("img/bird.gif")).getImage();
   public final Image trackImg = new ImageIcon(getClass().getResource("img/track.png")).getImage();
   public final Image cloudImg = new ImageIcon(getClass().getResource("img/cloud.png")).getImage();
   public final Image gameOverImg = new ImageIcon(getClass().getResource("img/game-over.png")).getImage();
   public final Image resetImg = new ImageIcon(getClass().getResource("img/reset.png")).getImage();

   public final int HEIGHT = 250;
   public final int WIDTH = 700;

   public final int trackHeight = 20;

   public final int dinoWidth = 80;
   public final int dinoHeight = 80;
   public final int dinoDuckWidth = 90;
   public final int dinoDuckHeight = 55;
   public final int dinoX = 30;
   public final int dinoYFixed = HEIGHT - dinoHeight - trackHeight / 2;

   public final int obstacleHeight = 60;
   public final int obstacle1Width = 30;
   public final int obstacle2Width = 50;
   public final int obstacle3Width = 70;
   public final int obstacle4Width = 80;

   public final int flyingObstacleHeight = 40;
   public final int flyingObstacleWidth = 40;
   public final int flyingObjectMinHeight = 60;
   

   public final int gameOverWidth = 400;
   public final int gameOverHeight = 90;
   public final int gameOverX = (WIDTH - gameOverWidth) / 2;
   public final int gameOverY = 20;

   public final int resetWidth = 50;
   public final int resetHeight = 50;
   public final int resetX = (WIDTH - resetWidth) / 2;
   public final int resetY = 90;

   public final int scoreX = 30;
   public final int scoreY = 30;


   public final int minCloudLength=60;
   public final int maxCloudLength=100;

}