public class Vector3D extends Point3D {
	private float magnitude;
	private float direction;

	public Vector3D(float v1, float v2, float v3) {
		super(v1, v2, v3);
	}

	public void add(Vector3D w) {
		setX(getX() + w.getX());
		setY(getY() + w.getY());
		setZ(getZ() + w.getZ());
	}

	public void add(Point3D p) {
		setX(getX() + p.getX());
		setY(getY() + p.getY());
		setZ(getZ() + p.getZ());
	}

	public void multiply(float scalar) {
		setX(getX() * scalar);
		setY(getY() * scalar);
		setZ(getZ() * scalar);
	}
}