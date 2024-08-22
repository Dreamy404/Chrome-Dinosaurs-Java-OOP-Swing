import java.awt.Image;

public class Obstacle extends GameObject {

    public Obstacle(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);
    }

    public void increaseX(int value) {
        x += value;
    }
}
