import java.awt.Color;

public class xColor {

  public static Color rainbowShift(int r, int g, int b) {

    if(r >= 255 && g <= 0 && b < 255) {
      b++;
    } else if(r > 0 && b >= 255) {
      r--;
    }

    if(r <= 0 && g < 255 && b >= 255) {
      g++;
    } else if(r<=0 && g >=255 && b > 0) {
      b--;
    }

    if(r < 255 && g >= 255 && b <= 0) {
      r++;
    } else if(g > 0 && r >=255) {
      g--;
    }
    Color c = new Color(r, g, b);
    return c;
  }

  

}