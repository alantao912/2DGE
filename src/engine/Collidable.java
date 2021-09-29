package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Collidable extends Moveable {

	protected float mass;
	
	protected Collidable(Vector2D[] vertices, float m) {
		super(vertices);
		mass = m;
	}
	
	public static <T extends Collidable> boolean isColliding(T object1, T object2, double maxDist) {
		if(maxDist == -1)
			return runSAT(object1.vertices(), object2.vertices());
		else if(Math.pow(object1.getX(1) - object2.getX(0), 2) + Math.pow(object1.getY(1) - object2.getY(0), 2) <= Math.pow(maxDist, 2))
                // Collision is possible so run SAT on the polys
                return runSAT(object1.vertices(), object2.vertices());
         else
        	 return false;
				 
	}
	
	/**
	 * 
	 * @param poly1 The first polygon to check
	 * @param poly2 The second polygon to check
	 * @return Whether or not two polygons are colliding
	 */
	
	private static boolean runSAT(Vector2D[] poly1, Vector2D[] poly2) {
        // Implements the actual SAT algorithm
        ArrayList<Vector2D> edges = polyToEdges(poly1);
        edges.addAll(polyToEdges(poly2));
        
        Vector2D[] axes = new Vector2D[edges.size()];
        for(int i = 0; i < edges.size(); i++)
            axes[i] = edges.get(i).orthogonal();

        for(Vector2D axis : axes)
            if(!overlap(project(poly1, axis), project(poly2, axis)))
                // The polys don't overlap on this axis so they can't be touching
                return false;

        // The polys overlap on all axes so they must be touching
        return true;
    }

    /**
     * Returns a vector going from point1 to point2
     */
    private static Vector2D edgeVector(Vector2D point1, Vector2D point2) {
        return new Vector2D(point2.x_component - point1.x_component, point2.y_component - point1.y_component);
    }

    /**
     * Returns an array of the edges of the poly as vectors
     */
    
    private static ArrayList<Vector2D> polyToEdges(Vector2D[] poly) {
        ArrayList<Vector2D> vectors = new ArrayList<>(poly.length);
        for(int i = 0; i < poly.length; i++)
            vectors.add(edgeVector(poly[i], poly[(i + 1) % poly.length]));
        return vectors;
    }

    /**
     * Returns the dot (or scalar) product of the two vectors
     */
    private static double dotProduct(Vector2D vector1, Vector2D vector2) {
        return vector1.x_component * vector2.x_component + vector1.y_component * vector2.y_component;
    }

    /**
     * Returns a vector showing how much of the poly lies along the axis
     */
    private static Vector2D project(Vector2D[] poly, Vector2D axis) {
        List<Double> dots = new ArrayList<>(poly.length);
        for(Vector2D vector : poly)
            dots.add(dotProduct(vector, axis));
        
        return new Vector2D(Collections.min(dots), Collections.max(dots));
    }

    /**
     * Returns a boolean indicating if the two projections overlap
     */
    private static boolean overlap(Vector2D projection1, Vector2D projection2) {
        return projection1.x_component <= projection2.y_component &&
                projection2.x_component <= projection1.y_component;
    }
	
	public abstract void collide(float otherMass, float other_x_velocity, float other_y_velocity);

}
