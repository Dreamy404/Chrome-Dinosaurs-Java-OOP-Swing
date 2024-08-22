import java.awt.Image;

public class Dinosaur extends GameObject {
    private Image jumpImg;
    private Image duckImg;
    private int duckWidth, duckHeight;
    private int fixedY;
    public static final int NORMAL = 8347;
    public static final int JUMP = 92934;
    public static final int DUCK = 6434;

    public Dinosaur(int x, int y, int width, int height, int duckWidth, int duckHeight, Image img, Image jumpImg,
            Image duckImage) {
        super(x, y, width, height, img);
        this.jumpImg = jumpImg;
        this.duckImg = duckImage;
        this.duckHeight = duckHeight;
        this.duckWidth = duckWidth;
        this.fixedY = y;
    }

    public void increaseY(int value) {
        y += value;
        y = Math.min(y, fixedY);
    }

    public Image getImage(int flag) {
        if (flag == JUMP)
            return jumpImg;
        if (flag == DUCK)
            return duckImg;
        return img;
    }

    public int getHeight(int flag) {
        if (flag == DUCK)
            return duckHeight;
        return height;
    }

    public int getWidth(int flag) {
        if (flag == DUCK)
            return duckWidth;
        return width;
    }

    public int getY(int flag, int uiHeight) {
        if (flag == DUCK)
            return uiHeight - duckHeight - 10;
        else if (flag == NORMAL)
            return y = uiHeight - height - 10;
        else
            return y;
    }
}
