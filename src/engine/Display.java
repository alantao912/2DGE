package engine;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import test.Barrier;
import test.Car;
import test.Sun;
import test.Target;

public class Display extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int tickRate;
	private final Timer gameClock;
	
	private ArrayList<Entity> entities;
	private ArrayList<Collidable> collidables;
	private ArrayList<Clickable> clickables;
	
	public boolean running = true;
	
	public Display(int t) {
		tickRate = t;
		gameClock = new Timer(tickRate, this);
		entities = new ArrayList<>();
		collidables = new ArrayList<>();
		clickables = new ArrayList<>();
		addMouseListener(this);
		addMouseMotionListener(this);
		start();
	}
	
	public void addEntity(Entity e) {
		if(e instanceof Collidable)
			collidables.add((Collidable)e);
		if(e instanceof Clickable)
			clickables.add((Clickable)e);
		entities.add(e);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ignored) {
		if(running) {
			/* Updates every entity */
			
			for(Entity e : entities)
				e.update((float)(tickRate/1000.0));
			
			/* Checks for collisions between any two collidable entities */
			
			for(int i = 0; i < collidables.size() - 1; ++i) {
				Collidable c0 = collidables.get(i);
				for(int j = i + 1; j < collidables.size(); ++j) {
					Collidable c1 = collidables.get(j);
					if(Collidable.isColliding(c0, c1, -1)) {
						float mass = c0.mass, x_velocity = c0.x_velocity, y_velocity = c0.y_velocity;
						c0.collide(c1.mass, c1.x_velocity, c1.y_velocity);
						c1.collide(mass, x_velocity, y_velocity);
					}
						
				}
			}
		}
	}
	
	public void start() {
		gameClock.start();
		new Thread(() -> {
			while(true)
				if(running)
					repaint();
				
				
		}).start();
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
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(Entity e : entities)
			e.render(g);
	}
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setSize(300, 300);
		Display display = new Display(20);
		display.addEntity(new Car());
		display.addEntity(new Barrier());
		display.addEntity(new Sun());
		display.addEntity(new Target());
		
		window.add(display);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		display.start();
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
}
