import java.awt.*;

import javax.swing.*;

public class Game extends JPanel{
    private UI ui;
    private GameLogic gameLogic;
    public Game(){
         ui=new UI();
         setPreferredSize(new Dimension(ui.WIDTH,ui.HEIGHT));
         setBackground(ui.backGround);
         setFocusable(true);

         gameLogic=new GameLogic(this, ui);
         addKeyListener(gameLogic); 
         gameLogic.newGame();
    }

    @Override 
    public void paintComponent(Graphics g){
           super.paintComponent(g);
           gameLogic.draw(g);
    }
}
