import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainPanel extends JPanel implements KeyListener, Subject {
    private ArrayList observers;

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers(GameStatus gameStatus) {
        for (int i=0; i<observers.size(); i++) {
            Observer observer = (Observer) observers.get(i);
            observer.update(gameStatus);
        }
    }

    @Override
    public void notifyObservers(KeyEvent keyEvent) {
        for (int i=0; i<observers.size(); i++) {
            Observer observer = (Observer) observers.get(i);
            observer.update(keyEvent);
        }
    }

    @Override
    public void notifyObservers(WhiteBall ball) {
        //do nothing
    }

    @Override
    public void notifyObservers() {
        for (int i=0; i<observers.size(); i++) {
            Observer observer = (Observer) observers.get(i);
            observer.update();
        }
    }

    enum GameStatus {PREPARING, START, STOP}

    private GameStatus gameStatus;
    private int score;
    Random random = new Random();
    Timer t;

    public MainPanel() {
        super();
        setLayout(null);
        setSize(590, 590);
        setFocusable(true);
        this.addKeyListener(this);
        observers = new ArrayList();
        t = new Timer(50, e -> moveBalls());
        restartGame();
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void startGame() {
        this.gameStatus = GameStatus.START;
        notifyObservers(GameStatus.START);
    }

    public void stopGame() {
        this.gameStatus = GameStatus.STOP;
        this.t.stop();
        notifyObservers(GameStatus.STOP);

        repaint();
    }

    public void restartGame() {
        this.gameStatus = GameStatus.PREPARING;
        notifyObservers(GameStatus.PREPARING);

        while (observers.size() > 1) {
            removeObserver((Observer) observers.get(1));
        }

        Ball.setCount(0);
        this.score = 100;

        this.t.start();
        repaint();
    }

    public void setWhiteBall(Ball whiteBall) {
        whiteBall.setVisible(false);
        add(whiteBall);
    }

    public void moveBalls() {
        notifyObservers();
        if (this.gameStatus == GameStatus.START) {
            score--;
            notifyObservers(GameStatus.START);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 20, 40);

        if (gameStatus == GameStatus.START) {
            this.setBackground(Color.WHITE);
        }

        if (gameStatus == GameStatus.STOP) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 45));
            g.drawString("Game Over!", 200, 200);
            g.setFont(new Font("", Font.BOLD, 40));
            g.drawString("Your score is " + score, 190, 280);
        }
    }

    public void scoreIncrement(int increment) {
        this.score += increment;
    }


    public void addBallToPanel(Ball ball) {
        this.add(ball);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();
        System.out.println("Press: " + keyChar);
        notifyObservers(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}
