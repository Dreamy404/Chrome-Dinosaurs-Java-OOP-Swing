import javax.swing.*;


public class ChromeDino extends JFrame implements Runnable{

    public ChromeDino(){
        setTitle("Chrome Dino Jump");
    }

    @Override
    public void run() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        requestFocus();
        
        add(new Game());
        pack();
        setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new ChromeDino());
    }
    
}
