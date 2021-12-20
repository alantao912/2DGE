package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Game extends JFrame implements ActionListener, KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1120950737124825046L;
	
	public static int tickRateMillis = 10;
	private final Timer gameClock;
	
	private final Display display;
	
	private ArrayList<Entity> entities;
	private ArrayList<KeyInteraction> keyInteractions;

	public Game() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameClock = new Timer(tickRateMillis, this);
		
		entities = new ArrayList<>();
		Collidable.collidables = new ArrayList<>();
		keyInteractions = new ArrayList<>();
		display = new Display(entities);
		
		add(display);
		addKeyListener(this);
		
		start();
	}
	
	public void addEntity(Entity e) {
		if(e instanceof Collidable)
			Collidable.collidables.add((Collidable)e);
		if(e instanceof Clickable)
			display.addClickable((Clickable)e);
		if(e instanceof KeyInteraction)
			keyInteractions.add((KeyInteraction)e);
		entities.add(e);
	}


	@Override
	public void keyPressed(KeyEvent e) {
		for(KeyInteraction k : keyInteractions)
			k.keyPressed(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		for(KeyInteraction k : keyInteractions)
			k.keyReleased(e);
		
	}	

	@Override
	public void keyTyped(KeyEvent ignored) {}
	
	@Override
	public void actionPerformed(ActionEvent ignored) {
		for(Entity e : entities)
			e.update((float)(tickRateMillis/1000.0));
	}
	
	public void start() {
		gameClock.start();
		new Thread(() -> {
			while(true)
				display.repaint();
				
				
		}).start();
	}
	
	public static void main(String[] args) {
		System.out.println("HelloWorld");
	}
	
}
