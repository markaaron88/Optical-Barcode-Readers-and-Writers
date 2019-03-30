/*
 * Team 9 - W. Robleh, M. Mariscal, T. Doan, Y. Nikulyak, C. Piwarski
 * CST338 - Software Design
 * Assignment 4
 * 27 March 2019
 */


public class Assign4
{

   public static void main(String[] args)
   {
      // TODO Auto-generated method stub

   }

}

public interface BarcodeIO {
   //implamenting methods 
	boolean scan(final BarcodeImage bc);
	
	boolean readText(final String test);
	
	boolean generateImageFromText();
	
	boolean translateImageToText();
	
	void displayTextToConsole();
	
	void displayImageToConsole();
	
}

public class BarcodeImage implements Cloneable
{
	
	public static final int MAX_HEIGHT = 30;
	
	public static final int MAX_WIDTH = 65;
	
	private boolean [][] imageData;
	public BarcodeImage() {
		imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
		for(int x = 0; x < MAX_WIDTH; x++) {
			for (int y = 0; y < MAX_HEIGHT; y++) {
				setPixel(x, y, false);
			}
		}
	}
	public BarcodeImage(String[] strData) {
		
	}
	
	public boolean getPixel(int row, int col) {
		return false;
	}
	
	public boolean setPixel(int row, int col, boolean value) {
		return false;
	}
	
	
}
