import java.awt.event.KeyEvent;

public interface Observer {
    public void update(MainPanel.GameStatus gameStatus);
    public void update(Ball whiteBall);
    public void update(KeyEvent keyEvent);
    public void update();
}
