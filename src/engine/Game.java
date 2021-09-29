package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

import test.Barrier;
import test.Car;
import test.Sun;
import test.Target;

public class Game extends JFrame implements ActionListener, KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1120950737124825046L;
	
	public static int tickRateMillis = 20;
	private final Timer gameClock;
	
	private final Display display;
	
	private ArrayList<Entity> entities;
	private ArrayList<Collidable> collidables;

	public Game() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameClock = new Timer(tickRateMillis, this);
		entities = new ArrayList<>();
		collidables = new ArrayList<>();
		display = new Display(entities);
		add(display);
		start();
	}
	
	public void addEntity(Entity e) {
		if(e instanceof Collidable)
			collidables.add((Collidable)e);
		if(e instanceof Clickable)
			display.addClickable((Clickable)e);
		entities.add(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}	

	@Override
	public void actionPerformed(ActionEvent ignored) {
		for(Entity e : entities)
			e.update((float)(tickRateMillis/1000.0));
		
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
	
	public void start() {
		gameClock.start();
		new Thread(() -> {
			while(true)
				display.repaint();
				
				
		}).start();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.setSize(300, 300);
		game.addEntity(new Barrier());
		game.addEntity(new Car());
		game.addEntity(new Sun());
		game.addEntity(new Target());
		game.setVisible(true);
	}
	
}
