package engine;

public abstract class Moveable extends Entity {
	
	protected float x_velocity, y_velocity;
	protected float x_acceleration, y_acceleration;
	
	protected Moveable(Vector2D[] vertices) {
		super(vertices);
	}
	
	/**
	 * Computes a Moveable entity's new position, and velocity after a specified time has passed.
	 * 
	 * Subclasses implementing this method must call 'super.update(tickRate)' if motion is to work correctly.
	 * 
	 * @param tickRate number of seconds that have passed since the last frame repaint
	 */

	@Override
	public void update(float tickRate) {
		
		for(int i = 0; i < super.outline().npoints; ++i) {
			float x1 = (float)(getX(i) + x_velocity * tickRate + 0.5 * x_acceleration * tickRate * tickRate);
			float y1 = (float)(getY(i) + y_velocity * tickRate + 0.5 * y_acceleration * tickRate * tickRate);
			setVertex(x1, y1,i);
		}
		
		x_velocity += tickRate * x_acceleration;
		y_velocity += tickRate * y_acceleration;
	}

}
