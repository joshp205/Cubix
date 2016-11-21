/*******************************************
* Authors :		Dustin Shropshire/Josh Perkins
* Class :		Menu
* Description :	Menu handling based off
*				original PauseMenu class by
*				Dustin
********************************************/

import java.awt.Color;

public class Menu{

	private MenuType type;
	private int cursorPos;
	private Button[] buttons;

	private Color xShade = new Color(0,255,0,65);
	private Color xCursor = new Color(255,0,0,255);

	public enum MenuType {
      MAIN, PAUSE, OPTIONS, DEBUG, NONE
	}

	public Menu(MenuType type, String[] labels) {
		this.type = type;
		cursorPos = 0;

		this.buttons = new Button[labels.length];
		for(int i = 0; i < labels.length; i++) {
			this.buttons[i] = new Button(labels[i], Color.black, 0, 0, 150, 50);
		}
	}

	public void update(int screenW, int screenH) {
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].setX((screenW/2) - (buttons[i].getW()/2));
			buttons[i].setY(screenH/2 + (i*buttons[i].getH()));
		}
		xCursor = xColor.rainbowShift(xCursor.getRed(),xCursor.getGreen(),xCursor.getBlue(),xCursor.getAlpha());
		xShade = xColor.rainbowShift(xShade.getRed(),xShade.getGreen(),xShade.getBlue(),xShade.getAlpha());
	}

	public void render(Drawing dSurface) {
		dSurface.drawRect2D(xShade,0,0,(int)dSurface.getScreenWidth(),(int)dSurface.getScreenHeight());
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].render(dSurface);
			if(i == cursorPos) {
				dSurface.drawRect2D(xCursor, buttons[i].getX() - 25, buttons[i].getY() + buttons[i].getH()/4, 15, 15);
			}
		}
	}

	public void menuUp() {
		cursorPos--;
		if(cursorPos < 0) {
			cursorPos = buttons.length - 1;
		}
	}

	public void menuDown() {
		cursorPos++;
		if(cursorPos >= buttons.length) {
			cursorPos = 0;
		}
	}

	public MenuType getMenuType() {
		return type;
	}

	public int getCursorPos() {
		return cursorPos;
	}

	public void setMenuType(MenuType type) {
		this.type = type;
	}

	public void setCursorPos(int pos) {
		this.cursorPos = pos;
	}
}