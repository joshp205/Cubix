public class Vector2D extends Point2D {
	private float magnitude;
	private float direction;

	public Vector2D(float v1, float v2) {
		super(v1, v2);
	}

	public void add(Vector2D w) {
		setX(getX() + w.getX());
		setY(getY() + w.getY());
	}

	public void add(Point2D p) {
		setX(getX() + p.getX());
		setY(getY() + p.getY());
	}

	public void multiply(float scalar) {
		setX(getX() * scalar);
		setY(getY() * scalar);
	}
}