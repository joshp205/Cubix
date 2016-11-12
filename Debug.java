import java.awt.Color;

public abstract class Debug {
	private int messages;
	private String[] status;

	public void update() {
		status = new String[messages];
		messages = 0;
	}

	public void render(Drawing dSurface, Color color, int x, int y) {
		if(status == null) {
			return;
		}
	}
/*	TODO Implement proper debug message/dispatch system

		for(int i = 0; i < status.getLength(); i++) {
			if(status[i] == null) {
				break;
			}
			dSurface.drawText(status[i], color, x, (i*15) + y);
			}
		}

	public void addMessage(String message) {
		messages++;
	} */
}