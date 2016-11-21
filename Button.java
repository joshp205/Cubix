import java.awt.Color;

public class Button{
	private String label;
	private Color color;
	private int[] XYWH;

	public Button(String label, Color c, int x, int y, int w, int h) {
		this.label = label;
		this.color = c;

		XYWH = new int[4];
		XYWH[0] = x;
		XYWH[1] = y;
		XYWH[2] = w;
		XYWH[3] = h;
	}

	public void update() {
		// TODO
	}

	public void render(Drawing dSurface) {
		dSurface.drawRect2D(color, XYWH[0], XYWH[1], XYWH[2], XYWH[3]);
		dSurface.drawText(label, Color.white, XYWH[0] + (XYWH[2]/4), XYWH[1] + (XYWH[3]/3));
	}

	public int getX() {
		return XYWH[0];
	}

	public int getY() {
		return XYWH[1];
	}

	public int getW() {
		return XYWH[2];
	}

	public int getH() {
		return XYWH[3];
	}

	public void setX(int x) {
		XYWH[0] = x;
	}

	public void setY(int y) {
		XYWH[1] = y;
	}

	public void setW(int w) {
		XYWH[2] = w;
	}

	public void setH(int h) {
		XYWH[3] = h;
	}
}