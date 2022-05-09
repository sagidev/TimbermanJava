import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalLogs = 30;

    private int playerX = 150;

    private int logX = 120;
    private int logY = 300;

    private Timer timer;

    private int delay = 8;
    public Gameplay()
    {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        StartGame();

        //repaint();


    }
    public void StartGame()
    {
        Arrays.fill(branches, null);
        score = 0;
        spawnBranches();
    }
    public Branch[] branches = new Branch[5];
    public ArrayList<Integer> scores = new ArrayList<>();

    public void paint(Graphics g)
    {
        //Background
        g.setColor(Color.BLACK);
        g.fillRect(1,1,692,592);
        //border
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);

        //player
        g.setColor(Color.WHITE);
        g.fillRect(playerX, 500, 50 ,50);

        //tree
        g.setColor(Color.YELLOW);
        g.fillRect(325,0,50,800);

        //Logs
        //Random rng = new Random();
        for(int i = 0; i < 5; i++)
        {
            g.fillRect(branches[i].x,branches[i].y,branches[i].width,branches[i].height);
        }

        //Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 45));
        g.drawString(""+score,50,50);

        g.dispose();
    }
    public void spawnBranches()
    {

        Random rng = new Random();
        for(int i = 0; i < 5; i++)
        {
            int random = rng.nextInt(2);
            if(random == 0)
            {
                Branch br = new Branch(150,400 - (i*100),200,50);
                //g.fillRect(150,400 - (i*100),200,50);
                branches[i] = br;
            }
            if(random == 1)
            {
                Branch br = new Branch(350,400 - (i*100),200,50);
                //g.fillRect(350,400 - (i*100),200,50);
                branches[i] = br;
            }
        }
        repaint();
    }

    public void GameOver()
    {
        play = false;
        JOptionPane.showMessageDialog(null,"Score: " + (score - 1));
        scores.add(score-1);
        StartGame();
    }


    public void UpdateGame()
    {
        //UpdateGame();
        for(Branch branch : branches)
        {
            if(branch != null)
            {
                if(play == true)
                {
                    branch.y = branch.y + 100;
                }

                if(playerX == 250 && branch.x == 150 && branch.y == 500)
                {
                    GameOver();
                }
                if(playerX == 400 && branch.x == 350 && branch.y == 500)
                {
                    GameOver();
                }
                if(branch.y == 600)
                {
                    score++;
                    Random rng = new Random();
                    int random = rng.nextInt(2);
                    if(random == 0)
                    {
                        branch.x = 150;
                        branch.y = 100;
                    }
                    else
                    {
                        branch.x = 350;
                        branch.y = 100;
                    }
                }
            }
        }
    }

    void showLeaderboard()
    {
        if(scores.get(0) == 1)
        {
            JOptionPane.showMessageDialog(null, "Leaderboard:\n"+ scores.get(0) + "\n");
        }
        if(scores.size() == 2)
        {
            JOptionPane.showMessageDialog(null, "Leaderboard:\n"+ scores.get(0) + "\n"+ scores.get(1));
        }
        if(scores.get(0) > 2)
        {
            JOptionPane.showMessageDialog(null, "Leaderboard:\n"+ scores.get(0) + "\n"+ scores.get(1) + "\n" + scores.get(2));
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //timer.start();
        //repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A) //LEFT
        {
            play=true;
            playerX = 250;
            UpdateGame();
            repaint();
        }
        else if(e.getKeyCode() == KeyEvent.VK_D) //RIGHT
        {
            play=true;
            playerX = 400;
            UpdateGame();
            repaint();
        }
        else if(e.getKeyCode() == KeyEvent.VK_S) //RIGHT
        {
            showLeaderboard();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
