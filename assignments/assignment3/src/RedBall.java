import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class RedBall extends Ball implements Observer{
    private Subject mainPanel;
    private Subject whiteBall;
    private Random random;

    public RedBall(Color color, int xSpeed, int ySpeed, int ballSize, Subject mainPanel, Subject whiteBall) {
        super(color, xSpeed, ySpeed, ballSize);
        this.mainPanel = mainPanel;
        this.whiteBall = whiteBall;
        this.random = new Random();
        mainPanel.registerObserver(this);
        whiteBall.removeObserver(this);
    }

    @Override
    public void update(MainPanel.GameStatus gameStatus) {
        this.move();
        switch (gameStatus) {
            case START :
                this.setVisible(this.isIntersect((Ball) whiteBall));
                break;
            case STOP :
                if (this.isVisible())
                    ((MainPanel) mainPanel).scoreIncrement(80);
                break;
            case PREPARING:
                this.setVisible(false);
                break;
        }
    }

    @Override
    public void update(Ball whiteBall) {
        if(this.isIntersect(whiteBall)) {
            this.setXSpeed(whiteBall.getXSpeed());
            this.setYSpeed(whiteBall.getYSpeed());
        }
    }

    @Override
    public void update(KeyEvent keyEvent) {
        char keyChar = keyEvent.getKeyChar();
        switch (keyChar) {
            case 'a':
                this.setXSpeed(-random.nextInt(3) - 1);
                break;
            case 'd':
                this.setXSpeed(random.nextInt(3) + 1);
                break;
            case 'w':
                this.setYSpeed(-random.nextInt(3) - 1);
                break;
            case 's':
                this.setYSpeed(random.nextInt(3) + 1);
        }
    }

    @Override
    public void update() {
        this.move();
    }
}
