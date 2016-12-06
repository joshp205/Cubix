/*******************************************
* Authors :		Dustin Shropshire/Josh Perkins
* Class :		Menu
* Description :	Menu handling based off
*				original PauseMenu class by
*				Dustin
********************************************/

import java.util.ArrayList;
import java.awt.Color;

public class Menu{

	private MenuType type, parent;
	private int cursorPos;
	private ArrayList<Button>[] buttons;

	private int temp;

	private Color xShade = new Color(0,255,0,65);
	private Color xCursor = new Color(255,0,0,255);
	private Color xButton = new Color(10,10,10,235);

	public enum MenuType {
      MAIN (0), PAUSE (1), OPTIONS (2), DEBUG (3), NONE (4);

      private int id;

      MenuType(int id) {
      	this.id = id;
      }
      public int getID() {
      	return id;
      }
	}

	public Menu(MenuType type, String[][] labels) {
		this.type = type;
		this.parent = type;
		cursorPos = 0;

		this.buttons = (ArrayList<Button>[]) new ArrayList[labels.length];
		for(int i = 0; i < labels.length; i++) {
			this.buttons[i] = new ArrayList<Button>();
			for(int j = 0; j < labels[i].length; j++) {
				this.buttons[i].add(new Button(labels[i][j], xButton, 0, 0, 150, 50));
			}
		}
	}

	public void update(int screenW, int screenH) {
		temp = this.type.getID();
		for(int i = 0; i < buttons[temp].size(); i++) {
			buttons[temp].get(i).setX((screenW/2) - (buttons[temp].get(i).getW()/2));
			buttons[temp].get(i).setY(screenH/2 + (i*buttons[temp].get(i).getH()));
		}
		xCursor = xColor.rainbowShift(xCursor.getRed(),xCursor.getGreen(),xCursor.getBlue(),xCursor.getAlpha());
		xShade = xColor.rainbowShift(xShade.getRed(),xShade.getGreen(),xShade.getBlue(),xShade.getAlpha());
	}

	public void render(Drawing dSurface) {
		temp = this.type.getID();
		dSurface.drawRect2D(xShade,0,0,(int)dSurface.getScreenWidth(),(int)dSurface.getScreenHeight());
		for(int i = 0; i < buttons[temp].size(); i++) {
			buttons[temp].get(i).render(dSurface);
			if(i == cursorPos) {
				dSurface.drawRect2D(xCursor, buttons[temp].get(i).getX() - 25, buttons[temp].get(i).getY() + buttons[temp].get(i).getH()/4, 15, 15);
			}
		}
	}

	public void menuUp() {
		cursorPos--;
		if(cursorPos < 0) {
			cursorPos = buttons[temp].size() - 1;
		}
	}

	public void menuDown() {
		cursorPos++;
		if(cursorPos >= buttons[temp].size()) {
			cursorPos = 0;
		}
	}

	public MenuType getMenuType() {
		return type;
	}

	public MenuType getParentMenu() {
		return parent;
	}

	public int getCursorPos() {
		return cursorPos;
	}

	public void setMenuType(MenuType type) {
		this.cursorPos = 0;
		this.parent = this.type;
		this.type = type;
	}

	public void setParentMenu(MenuType type) {
		this.parent = type;
	}

	public void setCursorPos(int pos) {
		this.cursorPos = pos;
	}
}