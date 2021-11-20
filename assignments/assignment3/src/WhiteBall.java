import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class WhiteBall extends Ball implements Subject, Observer{
    private Subject mainPanel;
    private ArrayList observers;

    public WhiteBall(Color color, int xSpeed, int ySpeed, int ballSize, Subject mainPanel) {
        super(color, xSpeed, ySpeed, ballSize);
        this.mainPanel = mainPanel;
        mainPanel.registerObserver(this);
        this.observers = new ArrayList();
    }

    @Override
    public void update(MainPanel.GameStatus gameStatus) {
        switch (gameStatus) {
            case START :
                this.setVisible(true);
                this.move();
                notifyObservers(this);
                break;
            case PREPARING :
                this.setVisible(false);
                while (observers.size() > 0) {
                    removeObserver((Observer) observers.get(0));
                }
                break;
        }
    }

    @Override
    public void update(Ball whiteBall) {

    }

    @Override
    public void update(KeyEvent keyEvent) {
        char keyChar = keyEvent.getKeyChar();

        if (((MainPanel)mainPanel).getGameStatus() == MainPanel.GameStatus.START) {
            switch (keyChar) {
                case 'a':
                    this.setXSpeed(-8);
                    break;
                case 'd':
                    this.setXSpeed(8);
                    break;
                case 'w':
                    this.setYSpeed(-8);
                    break;
                case 's':
                    this.setYSpeed(8);
                    break;
            }
        }
    }

    @Override
    public void update() {
    }

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
    public void notifyObservers(MainPanel.GameStatus gameStatus) {
        //do nothing
    }

    @Override
    public void notifyObservers(KeyEvent keyEvent) {
        //do nothing
    }

    @Override
    public void notifyObservers(WhiteBall ball) {
        for (int i=0; i<observers.size(); i++) {
            Observer observer = (Observer) observers.get(i);
            observer.update(ball);
        }
    }

    @Override
    public void notifyObservers() {

    }
}
