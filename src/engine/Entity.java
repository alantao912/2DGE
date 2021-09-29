package engine;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

public abstract class Entity {
	
	private Vector2D[] vertices;
	
	private Polygon outline;
	
	public Entity(Vector2D[] vertices) {
		this.vertices = vertices;
		
		int[] x = new int[vertices.length], y = new int[vertices.length];
		
		for(int i = 0; i < vertices.length; ++i) {
			x[i] = (int)vertices[i].x_component;
			y[i] = (int)vertices[i].y_component;
		}
		outline = new Polygon(x, y, x.length);
		
	}
	
	
	protected BufferedImage texture;
	
	public abstract void update(float tickRate);
	
	public abstract void render(Graphics g);
	
	/**
	 * Sets a specified vertex vertex in the vertex array to a new value
	 * and updates the backing Polygon object.
	 * 
	 * @param x The new x value to modify the specified vertex to
	 * @param y The new y value to modify the specified vertex to
	 * @param n The nth vertex of the vertex array to modify
	 */
	
	protected void setVertex(float x, float y, int n) {
		if(n >= vertices.length || n < 0)
			return;
		vertices[n].x_component = x;
		vertices[n].y_component = y;
		
		outline.xpoints[n] = (int)x;
		outline.ypoints[n] = (int)y;
	}
	
	/**
	 * Retrieves the x component of a specified vertex
	 * 
	 * @param n Index of the vertex to get
	 * @return the x component of the nth vertex
	 */
	
	protected float getX(int n) {
		return vertices[n].x_component;
	}
	
	/**
	 * Retrieves the y component of a specified vertex
	 * 
	 * @param n Index of the vertex to get
	 * @return the y component of the nth vertex
	 */
	
	protected float getY(int n) {
		return vertices[n].y_component;
	}
	
	protected Vector2D[] vertices() {
		return vertices;
	}
	
	/**
	 * Determines whether or not a point is within a convex 2D polygon
	 * 
	 * @param x component of the point in question
	 * @param y component of the point in question
	 * @return whether or not a point is within a convex polygon
	 */
	
	public boolean contains(float x, float y) {
		if(vertices.length <= 2)
            return false;
        int hits = 0;

        //int lastx = xpoints[npoints - 1];
        float lastx = (float)vertices[vertices.length - 1].x_component;
        //int lasty = ypoints[npoints - 1];
        float lasty = (float)vertices[vertices.length - 1].y_component;
        float curx, cury;

        // Walk the edges of the polygon
        for(int i = 0; i < vertices.length; lastx = curx, lasty = cury, i++) {
            curx = (float)vertices[i].x_component;
            cury = (float)vertices[i].y_component;

            if(cury == lasty)
                continue;

            float leftx;
            if (curx < lastx) {
                if (x >= lastx) {
                    continue;
                }
                leftx = curx;
            } else {
                if (x >= curx) {
                    continue;
                }
                leftx = lastx;
            }

            double test1, test2;
            if (cury < lasty) {
                if (y < cury || y >= lasty) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - curx;
                test2 = y - cury;
            } else {
                if (y < lasty || y >= cury) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - lastx;
                test2 = y - lasty;
            }

            if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
                hits++;
            }
        }

        return ((hits & 1) != 0);
	}
	
	/**
	 * To be used for drawing purposes
	 * 
	 * @return the backing polygon with integer vertex
	 */
	
	protected Polygon outline() {
		return outline;
	}
}
