package engine;
import java.awt.event.MouseEvent;

public interface Clickable {
	
	void pressAction(MouseEvent e);
	
	void dragAction(MouseEvent e);
	
	void moveAction(MouseEvent e);
	
	void releaseAction(MouseEvent e);

}
