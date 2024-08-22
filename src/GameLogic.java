import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.util.LinkedList;
import java.util.Random;

public class GameLogic implements ActionListener, KeyListener {
    private JPanel panel;
    private UI ui;
    private int flag;
    private LinkedList<Obstacle> obstacleArray, cloudArray;
    private Timer gameLoop, obstacleTimer;
    private int score = 0;
    private int velocityY = 0;
    private int levelIndex = 0;
    private Random random;
    private boolean gameOver = false;
    private Dinosaur dino;

    public GameLogic(JPanel panel, UI ui) {
        setUI(ui);
        this.panel = panel;

        gameLoop = new Timer(1000 / 60, this);
        random = new Random(System.currentTimeMillis());
        obstacleArray = new LinkedList<>();
        cloudArray = new LinkedList<>();
        obstacleTimer = new Timer(1500, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                placeObstacle();
            }

        });
    }

    public void setUI(UI ui) {
        this.ui = ui;
        flag = Dinosaur.NORMAL;
        dino = new Dinosaur(ui.dinoX, ui.dinoYFixed, ui.dinoWidth, ui.dinoHeight, ui.dinoDuckWidth, ui.dinoDuckHeight,
                ui.dinoImg, ui.jumpImg, ui.duckImg);
    }

    public void startGame() {
        gameLoop.start();
        obstacleTimer.start();
    }

    public void pauseGame() {
        gameLoop.stop();
        obstacleTimer.stop();
    }

    public void newGame() {
        score = 0;
        velocityY = 0;
        obstacleArray.clear();
        cloudArray.clear();
        gameOver = false;
        flag = Dinosaur.NORMAL;
        startGame();
    }

    private void setLevel() {
        int index = score / 1000;
        if (index < ui.levels.length && index != levelIndex) {
            levelIndex = index;
            obstacleTimer.stop();
            obstacleTimer = new Timer(ui.levels[levelIndex].obstacleTime, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    placeObstacle();
                }

            });
            obstacleTimer.start();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameOver) {
            newGame();
        } else if ((flag == Dinosaur.NORMAL) && e.getKeyCode() == KeyEvent.VK_UP) {
            if (dino.getY() == ui.dinoYFixed) {
                velocityY = ui.levels[levelIndex].velocityY;
                flag = Dinosaur.JUMP;
            }
        } else if (flag == Dinosaur.NORMAL && e.getKeyCode() == KeyEvent.VK_DOWN) {
            flag = Dinosaur.DUCK;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            flag = Dinosaur.NORMAL;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        panel.repaint();
    }

    private void move() {
        setLevel();
        velocityY += ui.levels[levelIndex].gravity;
        dino.increaseY(velocityY);
        if (flag != Dinosaur.DUCK && dino.getY() == ui.dinoYFixed) {
            flag = Dinosaur.NORMAL;
        }

        for (Obstacle cloud : cloudArray) {
            cloud.increaseX(ui.levels[levelIndex].velocityX);
        }

        while (cloudArray.size() > 10) {
            cloudArray.removeFirst();
        }

        for (Obstacle obstacle : obstacleArray) {
            obstacle.increaseX(ui.levels[levelIndex].velocityX);
            if (collision(obstacle, dino)) {
                gameOver = true;
                pauseGame();
            }

        }

        while (obstacleArray.size() > 10) {
            obstacleArray.removeFirst();
        }

        score++;

    }

    private boolean collision(Obstacle a, Dinosaur b) {
        Rectangle recA = new Rectangle(a.getX(), a.getY(), a.getWidth(), a.getHeight());
        Rectangle recB = new Rectangle(b.getX(), b.getY(flag, ui.HEIGHT), b.getWidth(flag), b.getHeight(flag));

        return recA.intersects(recB);
    }

    public void draw(Graphics g) {
        // path
        g.drawImage(ui.trackImg, 0, ui.HEIGHT - ui.trackHeight, ui.WIDTH, ui.trackHeight, null);

        // clouds
        for (Obstacle cloud : cloudArray) {
            g.drawImage(cloud.getImage(), cloud.getX(), cloud.getY(), cloud.getWidth(), cloud.getHeight(), null);
        }

        // dinosaurs
        if (!gameOver)
            g.drawImage(dino.getImage(flag), dino.getX(), dino.getY(flag, ui.HEIGHT), dino.getWidth(flag),
                    dino.getHeight(flag), null);
        else
            g.drawImage(ui.deadImg, dino.getX(), dino.getY(), dino.getWidth(), dino.getHeight(), null);

        // Obstacle
        for (Obstacle obstacle : obstacleArray) {
            g.drawImage(obstacle.getImage(), obstacle.getX(), obstacle.getY(), obstacle.getWidth(),
                    obstacle.getHeight(), null);
        }

        // Scoring
        g.setColor(ui.fontColor);
        g.setFont(ui.scoreFont);
        g.drawString(String.valueOf(score), ui.scoreX, ui.scoreY);
        if (gameOver) {
            g.drawImage(ui.gameOverImg, ui.gameOverX, ui.gameOverY, ui.gameOverWidth, ui.gameOverHeight, null);
            g.drawImage(ui.resetImg, ui.resetX, ui.resetY, ui.resetWidth, ui.resetHeight, null);
        }
        g.dispose();
    }

    private void placeObstacle() {
        double probability = Math.random();
        Obstacle obstacle = null;

        if (probability > 0.9) {
            probability = Math.random();

            if (probability > 0.5 && levelIndex > 0) {
                obstacle = new Obstacle(ui.WIDTH - ui.obstacle4Width, ui.HEIGHT - ui.obstacleHeight - 9,
                        ui.obstacle4Width, ui.obstacleHeight, ui.obstacle4);
            } else {
                probability = Math.random();

                if (probability > 0.5) {
                    obstacle = new Obstacle(ui.WIDTH - ui.flyingObstacleWidth,
                            ui.HEIGHT - (ui.trackHeight + ui.flyingObstacleHeight), ui.flyingObstacleWidth,
                            ui.flyingObstacleHeight, ui.flyingObstacleImg);
                } else {

                    int height = ui.flyingObstacleHeight + ui.trackHeight / 2 + ui.flyingObjectMinHeight
                            + random.nextInt(10);
                    obstacle = new Obstacle(ui.WIDTH - ui.flyingObstacleWidth, ui.HEIGHT - height,
                            ui.flyingObstacleWidth,
                            ui.flyingObstacleHeight, ui.flyingObstacleImg);
                }
            }
        } else if (probability > 0.7) {
            obstacle = new Obstacle(ui.WIDTH - ui.obstacle3Width, ui.HEIGHT - ui.obstacleHeight - 9,
                    ui.obstacle3Width, ui.obstacleHeight, ui.obstacle3);
        } else if (probability > 0.5) {
            obstacle = new Obstacle(ui.WIDTH - ui.obstacle2Width, ui.HEIGHT - ui.obstacleHeight - 9,
                    ui.obstacle2Width, ui.obstacleHeight, ui.obstacle2);
        } else if (probability > 0.3) {
            obstacle = new Obstacle(ui.WIDTH - ui.obstacle1Width, ui.HEIGHT - ui.obstacleHeight - 9,
                    ui.obstacle1Width, ui.obstacleHeight, ui.obstacle1);
        }
        if (obstacle != null)
            obstacleArray.add(obstacle);

        placeClouds();
    }

    private void placeClouds() {
        int offset=20;
        int length = ui.minCloudLength + random.nextInt(ui.maxCloudLength - ui.minCloudLength);
        int height = ui.trackHeight + length+offset + random.nextInt(2*length);
        Obstacle cloud = new Obstacle(ui.WIDTH, ui.HEIGHT - height, length, length, ui.cloudImg);
        cloudArray.add(cloud);

    }

}
