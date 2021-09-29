package engine;

public class Vector2D {
	
	public float x_component, y_component;
	
	public Vector2D(double x, double y) {
		x_component = (float)x;
		y_component = (float)y;
	}
	
	public Vector2D(float x, float y) {
		x_component = x;
		y_component = y;
	}
	
	public Vector2D(int x, int y) {
		x_component = x;
		y_component = y;
	}
	
	public Vector2D() {
		this(0, 0);
	}
	
	public Vector2D orthogonal() {
		return new Vector2D(y_component, -x_component);
	}

}
