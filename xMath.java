public class xMath {

	public static Point3D rotateX(Point3D p, float theta) {
		Point3D calc3D = new Point3D(p);
		calc3D.setZ(p.getY() * (float)Math.cos(Math.toDegrees(theta)) - p.getZ() * (float)Math.sin(Math.toDegrees(theta)));
		calc3D.setY(p.getY() * (float)Math.sin(Math.toDegrees(theta)) + p.getZ() * (float)Math.cos(Math.toDegrees(theta)));
		calc3D.setX(p.getX());
		return calc3D;
	}

	public static Point3D rotateY(Point3D p, float theta) {
		Point3D calc3D = new Point3D(p);
		calc3D.setX(p.getZ() * (float)Math.cos(Math.toDegrees(theta)) - p.getX() * (float)Math.sin(Math.toDegrees(theta)));
		calc3D.setZ(p.getZ() * (float)Math.sin(Math.toDegrees(theta)) + p.getX() * (float)Math.cos(Math.toDegrees(theta)));
		calc3D.setY(p.getY());
		return calc3D;
	}

	public static Point3D rotateZ(Point3D p, float theta) {
		Point3D calc3D = new Point3D(p);
		calc3D.setX(p.getX() * (float)Math.cos(Math.toDegrees(theta)) - p.getY() * (float)Math.sin(Math.toDegrees(theta)));
		calc3D.setY(p.getX() * (float)Math.sin(Math.toDegrees(theta)) + p.getY() * (float)Math.cos(Math.toDegrees(theta)));
		calc3D.setZ(p.getZ());
		return calc3D;
	}

	public static Point3D getCenter3D(float x, float y, float z, float w, float h, float d) {
		Point3D calc3D = new Point3D(x + w/2f, y + h/2f, z - d/2f);
		return calc3D;
	}

	public static Point3D getCenter3D(Point3D p, Point3D dim) {
		return getCenter3D(p.getX(), p.getY(), p.getZ(), dim.getX(), dim.getY(), dim.getZ());
	}

	public static Point3D getCenter3D(TechnoType t) {
		return getCenter3D(t.getCoords3D(), t.getDimensions());
	}

	public static float getDistance3D(float x1, float y1, float z1, float x2, float y2, float z2) {
		return (float)Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2));
	}

}