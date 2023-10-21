import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Prompt {
  private static InputStreamReader streamReader = new InputStreamReader(System.in);
  
  private static BufferedReader bufReader = new BufferedReader(streamReader);
  
  public static String getString(String paramString) {
    System.out.print(paramString + " -> ");
    String str = "";
    try {
      str = bufReader.readLine();
    } catch (IOException iOException) {
      System.err.println("ERROR: BufferedReader could not read line");
    } 
    return str;
  }
  
  public static char getChar(String paramString) {
    String str = getString(paramString);
    while (str.length() != 1)
      str = getString(paramString); 
    return str.charAt(0);
  }
  
  public static int getInt(String paramString) {
    int i = 0;
    boolean bool = false;
    while (!bool) {
      String str = getString(paramString);
      try {
        i = Integer.parseInt(str);
        bool = true;
      } catch (NumberFormatException numberFormatException) {
        bool = false;
      } 
    } 
    return i;
  }
  
  public static int getInt(String paramString, int paramInt1, int paramInt2) {
    int i = 0;
    while (true) {
      i = getInt(paramString + " (" + paramInt1 + ", " + paramInt2 + ")");
      if (i >= paramInt1 && i <= paramInt2)
        return i; 
    } 
  }
  
  public static double getDouble(String paramString) {
    double d = 0.0D;
    boolean bool = false;
    while (!bool) {
      String str = getString(paramString);
      try {
        d = Double.parseDouble(str);
        bool = true;
      } catch (NumberFormatException numberFormatException) {
        bool = false;
      } 
    } 
    return d;
  }
  
  public static double getDouble(String paramString, double paramDouble1, double paramDouble2) {
    double d = 0.0D;
    boolean bool = false;
    while (true) {
      d = getDouble(paramString + " (" + paramDouble1 + ", " + paramDouble2 + ")");
      if (d >= paramDouble1 && d <= paramDouble2)
        return d; 
    } 
  }
}
