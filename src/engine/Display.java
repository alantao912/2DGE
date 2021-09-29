package engine;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Display extends JPanel implements MouseListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final ArrayList<Entity> entities;
	private final ArrayList<Clickable> clickables;
	
	public Display(ArrayList<Entity> e) {
		entities = e;
		clickables = new ArrayList<Clickable>();
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void addClickable(Clickable c) {
		clickables.add(c);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		for(Clickable clickable : clickables)
			clickable.moveAction(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		for(Clickable clickable : clickables)
			clickable.dragAction(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		for(Clickable clickable : clickables)
			clickable.pressAction(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		for(Clickable clickable : clickables)
			clickable.releaseAction(e);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(Entity entity : entities)
			entity.render(g);
	}
	
}
