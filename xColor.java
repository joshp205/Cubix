import java.awt.Color;

public class xColor {

  public static Color blipRed(int r, int g, int b, int alpha) {
    if(r != 255) {
      r = 255;
    } else {
      r = 0;
    }
    Color c = new Color(r, g, b, alpha);
    return c;
  }

  public static Color blipGreen(int r, int g, int b, int alpha) {
    if(g != 255) {
      g = 255;
    } else {
      g = 0;
    }
    Color c = new Color(r, g, b, alpha);
    return c;
  }

  public static Color blipBlue(int r, int g, int b, int alpha) {
    if(b != 255) {
      b = 255;
    } else {
      b = 0;
    }
    Color c = new Color(r, g, b, alpha);
    return c;
  }



  public static Color rainbowShift(int r, int g, int b, int alpha) {

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
    Color c = new Color(r, g, b, alpha);
    return c;
  }

public static Color lilShift(int r, int g, int b, int alpha) {
  if(r < 255) {
    r++;
  } else if(r > 0) {
    r--;
  }
  Color c = new Color(r, g, b, alpha);
  return c;
}
  

}