import java.awt.*;
import java.awt.event.KeyEvent;

public class BlueBall extends Ball implements Observer{
    private Subject mainPanel;
    private Subject whiteBall;

    public BlueBall(Color color, int xSpeed, int ySpeed, int ballSize, Subject mainPanel, Subject whiteBall) {
        super(color, xSpeed, ySpeed, ballSize);
        this.mainPanel = mainPanel;
        this.whiteBall = whiteBall;
        mainPanel.registerObserver(this);
        whiteBall.removeObserver(this);
    }

    @Override
    public void update(MainPanel.GameStatus gameStatus) {
        switch (gameStatus) {
            case START :
                this.setVisible(this.isIntersect((Ball) whiteBall));
                break;
            case STOP :
                if (this.isVisible())
                    ((MainPanel) mainPanel).scoreIncrement(-80);
                break;
            case PREPARING:
                this.setVisible(false);
                break;
        }
    }

    @Override
    public void update(Ball whiteBall) {
        this.setXSpeed(-this.getXSpeed());
        this.setYSpeed(-this.getYSpeed());
    }

    @Override
    public void update(KeyEvent keyEvent) {
        this.setXSpeed(-1 * this.getXSpeed());
        this.setYSpeed(-1 * this.getYSpeed());
    }

    @Override
    public void update() {
        this.move();
    }
}
