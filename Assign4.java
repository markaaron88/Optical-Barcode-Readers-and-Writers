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

interface BarcodeIO {
        //methods to implement 
	boolean scan(final BarcodeImage bc);
	
	boolean readText(final String test);
	
	boolean generateImageFromText();
	
	boolean translateImageToText();
	
	void displayTextToConsole();
	
	void displayImageToConsole();
	
}

class BarcodeImage implements Cloneable
{
	
	//the dimension highest height of dimension for our data
	public static final int MAX_HEIGHT = 30;
	//max dimension of our data
	public static final int MAX_WIDTH = 65;
	//array to store image in x and y
	private boolean [][] imageData;
	
	/*default constructor to set image data to false
	 * whih is empty space. looping through height and
	 * width to setpixel to false
	 */
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
	//accessor method that return image data in 2d array
	public boolean getPixel(int row, int col) {
		try {
			return imageData[row][col];
			}catch(IndexOutOfBoundsException e)
		{
				return false;
		}
	}
	/*mutator method for index 2d array of image
	 * and re-assign it to a value
	 */
	public boolean setPixel(int row, int col, boolean value) {
		try {
			imageData[row][col] = value;
			return true;
		}catch (IndexOutOfBoundsException e)
		{
			return false;
		}
	}
	
}
