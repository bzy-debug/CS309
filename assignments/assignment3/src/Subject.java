import java.awt.event.KeyEvent;

public interface Subject {
    public void registerObserver(Observer o);

    public void removeObserver(Observer o);

    public void notifyObservers(MainPanel.GameStatus gameStatus);

    public void notifyObservers(KeyEvent keyEvent);

    public void notifyObservers(WhiteBall ball);

    public void notifyObservers();
}
