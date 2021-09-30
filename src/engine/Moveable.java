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
		float dx =  (float) (x_velocity * tickRate + 0.5 * x_acceleration * tickRate * tickRate);
		float dy =  (float) (y_velocity * tickRate + 0.5 * y_acceleration * tickRate * tickRate);
		super.translate(dx, dy);
		
		if(this instanceof Collidable) {
			
			/*
			 * 	Checks if this polygon is colliding with any other polygons
			 *  Invokes the collide method of this polygon and the other if they are colliding.
			 */
			
			Collidable c0 = Collidable.collidingAny((Collidable)this);
			if(c0 != null) {
				Collidable c1 = (Collidable)this;
				float mass = c0.mass, x_velocity = c0.x_velocity, y_velocity = c0.y_velocity;
				c0.collide(c1.mass, c1.x_velocity, c1.y_velocity);
				c1.collide(mass, x_velocity, y_velocity);
			}
		}
		x_velocity += tickRate * x_acceleration;
		y_velocity += tickRate * y_acceleration;
	}

}
