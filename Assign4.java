/*
* Team 9 - W. Robleh, M. Mariscal, T. Doan, Y. Nikulyak, C. Piwarski
* CST338 - Software Design
* Assignment 4
* 27 March 2019
* Assignment 4 contains classes for a BarcodeImage and DataMatrix. The 
* DataMatrix stores the BarcodeImage and provides methods for accessing
* the data. An interface BarcodeIO that the DataMatrix implements
* ensuring all necessary methods are created.
*/


/*
 * This interface defines the necessary methods needed to provide input / 
 * output for a Barcode image.
 */
interface BarcodeIO
{
   public boolean scan(BarcodeImage bc);
   public boolean readText(String text);
   public boolean generateImageFromText();
   public boolean translateImageToText();
   public void displayTextToConsole();
   public void displayImageToConsole();
}

public class Assig4
{

   public static void main(String[] args)
   {

      String[] sImageIn =
      { "                                               ", 
         "                                               ",
         "                                               ", 
         "     * * * * * * * * * * * * * * * * * * * * * ",
         "     *                                       * ", 
         "     ****** **** ****** ******* ** *** *****   ",
         "     *     *    ****************************** ", 
         "     * **    * *        **  *    * * *   *     ",
         "     *   *    *  *****    *   * *   *  **  *** ", 
         "     *  **     * *** **   **  *    **  ***  *  ",
         "     ***  * **   **  *   ****    *  *  ** * ** ", 
         "     *****  ***  *  * *   ** ** **  *   * *    ",
         "     ***************************************** ", 
         "                                               ",
         "                                               ", 
         "                                               " };

      String[] sImageIn_2 =
      { "                                          ", 
         "                                          ",
         "* * * * * * * * * * * * * * * * * * *     ", 
         "*                                    *    ",
         "**** *** **   ***** ****   *********      ", 
         "* ************ ************ **********    ",
         "** *      *    *  * * *         * *       ", 
         "***   *  *           * **    *      **    ",
         "* ** * *  *   * * * **  *   ***   ***     ", 
         "* *           **    *****  *   **   **    ",
         "****  *  * *  * **  ** *   ** *  * *      ", 
         "**************************************    ",
         "                                          ", 
         "                                          ",
         "                                          ", 
         "                                          " };

      BarcodeImage bc = new BarcodeImage(sImageIn);
      System.out.println("Beginning BarcodeImage and DataMatrix test");
      System.out.print("Printing out BarcodeImage input:");
      bc.displayToConsole();
      DataMatrix dm = new DataMatrix(bc);

      // First secret message
      System.out.println("Translating image to text...");
      dm.translateImageToText();
      dm.displayTextToConsole();
      System.out.println("\nNow you see formatted image without blank right " + 
         "columns and blank top rows.\nEnjoy!\n");
      dm.displayImageToConsole();

      // second secret message
      System.out.println("\nProcessing second test image...");
      bc = new BarcodeImage(sImageIn_2);
      dm.scan(bc);
      dm.translateImageToText();
      dm.displayTextToConsole();
      System.out.println("\nNow you see formatted image without blank right " + 
         "columns and blank top rows.\nEnjoy!\n");
      dm.displayImageToConsole();

      // create your own message
      System.out.println("\nProcessing custom message...");
      dm.readText("What a great resume builder this is!");
      dm.generateImageFromText();
      System.out.println("\nDisplaying the message and BarcodeImage: ");
      dm.displayTextToConsole();
      dm.displayImageToConsole();

   }
}

/*
 * This class will realize all the essential data and methods associated with a
 * 2D pattern, an image of a square or rectangular bar code.
 */
class BarcodeImage implements Cloneable
{

   public static final int MAX_HEIGHT = 30;
   public static final int MAX_WIDTH = 65;
   private boolean[][] imageData;

   /*
    * Default constructor instantiates a 2D array (MAX_HEIGHTxMAX_WIDTH) and 
    * fills it all with blanks - false - boolean default value.
    */
   public BarcodeImage()
   {
      imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
   }

   /*
    * Takes a 1D array of Strings and converts it to the internal 2D array of
    * booleans.
    */
   public BarcodeImage(String[] strData)
   {

      this();
      // test validity of a parameter
      if (!checkSize(strData))
      {
         return;
      }
      int row = MAX_HEIGHT - 1;
      int col = 0;
      for (int i = strData.length - 1; i >= 0; i--)
      {
         String str = strData[i];
         for (int j = 0; j < str.length(); j++)
         {
            if (str.charAt(j) == DataMatrix.BLACK_CHAR)
            {
               imageData[row][col] = true;
            }
            col++;
         }
         row--;
         col = 0;
      }
   }

   /*
    * Private helper for constructor with parameter to check the incoming data 
    * for every conceivable size or null error. Smaller size is acceptable, 
    * bigger or null is not.
    */
   private boolean checkSize(String[] data)
   {

      // check string array
      if (data == null || data.length > MAX_HEIGHT)
      {
         return false;
      }

      // check each string in a string array
      for (int i = 0; i < data.length; i++)
      {
         if (data[i] == null || data[i].length() >= MAX_WIDTH)
         {
            return false;
         }
      }
      return true;
   }

   /*
    * This method is an accessor that returns the boolean value for that 
    */
   public boolean getPixel(int row, int col)
   {
      // test validate of parameters
      if (row >= MAX_HEIGHT || col >= MAX_WIDTH || row < 0 || col < 0)
      {
         return false;
      }
      return imageData[row][col];
   }

   // mutator for each bit in the image
   public boolean setPixel(int row, int col, boolean value)
   {
      // test validity of parameters
      if (row >= MAX_HEIGHT || col >= MAX_WIDTH || row < 0 || col < 0)
      {
         return false;
      }
      imageData[row][col] = value;
      return true;
   }

   /*
    * clone method need to be overriden if a class implements Cloneable 
    * interface
    */
   @Override
   public Object clone() throws CloneNotSupportedException
   {
      BarcodeImage copy = (BarcodeImage) super.clone();

      copy.imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
      for (int i = 0; i < MAX_HEIGHT; i++)
      {
         for (int j = 0; j < MAX_WIDTH; j++)
         {
            copy.imageData[i][j] = this.imageData[i][j];
         }
      }
      return copy;
   }

   // Method just for debugging
   public void displayToConsole()
   {

      for (int i = 0; i < MAX_HEIGHT; i++)
      {
         for (int j = 0; j < MAX_WIDTH; j++)
         {
            System.out.print(imageData[i][j] ? '*' : ' ');
         }
         System.out.println();
      }
   }
}

/*
 * One object of class DataMatrix contains a 2D array that represents a
 */
class DataMatrix implements BarcodeIO
{
   private BarcodeImage image;
   private int actualWidth;
   private int actualHeight;
   private String text;

   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';

   /*
    * Default constructor for the DataMatrix object.
    */
   public DataMatrix()
   {
      this.text = "";
      this.image = new BarcodeImage();
      this.actualWidth = 0;
      this.actualHeight = 0;
   }

   /*
    * This constructor receives a BarcodeImage parameter and initializes the
    * DataMatrix with it.
    */
   public DataMatrix(BarcodeImage image)
   {
      this.text = "";    
      this.scan(image); 
   }

   /*
    * This constructor method receives a text string and creates an associated 
    * BarcodeImage.
    */
   public DataMatrix(String text)
   {
      this.readText(text);
      this.image = new BarcodeImage();
   }

   /*
    * This method receives a string and determines if it is valid for a 
    * BarcodeImage object.
    */
   public boolean readText(String text)
   {
      if (text == null || text.length() > BarcodeImage.MAX_WIDTH)
         return false;
      this.text = text;
      return true;
   }

   /*
    * This method receives a BarcodeImage object and processes it for storage
    * in the DataMatrix. The image is bottom left justified, and its 
    * dimensions are calculated.
    */
   public boolean scan(BarcodeImage image)
   {
      if (image == null)
         return false;
      try
      {
         this.image = (BarcodeImage) image.clone();
      } catch (CloneNotSupportedException e)
      {
         //Program spec asks this section be left empty
      }
      this.cleanImage();
      this.actualWidth = this.computeSignalWidth();
      this.actualHeight = this.computeSignalHeight();
      return true;
   }

   /*
    * This method returns the width for the stored Barcode Image.
    */
   public int getWidth()
   {
      return this.actualWidth;
   }

   /*
    * This method returns the height for the stored Barcode Image.
    */
   public int getHeight()
   {
      return this.actualHeight;
   }

   /*
    * This method computes how many characters long the message is contained
    * in the BarcodeImage.
    */
   private int computeSignalWidth()
   {
      int width = 0;
      for (int i = 1; i < BarcodeImage.MAX_WIDTH; i++)
      {
         if (image.getPixel(BarcodeImage.MAX_HEIGHT - 1, i))
         {
            width++;
         }
      }
      return width - 1;
   }

   /*
    * This method computes the width of the barcode image in the DataMatrix 
    * object.
    */
   private int computeSignalHeight()
   {
      int height = 0;
      for (int i = BarcodeImage.MAX_HEIGHT - 1; i >= 0; i--)
      {
         if (image.getPixel(i, 0))
         {
            height ++;
         }
      }
      return height - 2;
   }

   /*
    * This method processes the stored BarcodeImage and justifies it in the
    * bottom left of the image grid.
    */
   private void cleanImage()
   {
      int x = 0;
      int y = 0;
      boolean pixel = false;

      // locate top left corner of barcode image
      for (x = 0; x < BarcodeImage.MAX_WIDTH; x++)
      {
         for (y = 0; y < BarcodeImage.MAX_HEIGHT; y++)
         {
            pixel = this.image.getPixel(y, x);
            if (pixel == true)
               break;
         }
         if (pixel == true)
            break;
      }

      // traverse to lower left corner of BarcodeImage
      while (pixel)
      {
         pixel = this.image.getPixel(y, x);
         y++;
      }
      this.moveImageToLowerLeft(x, BarcodeImage.MAX_HEIGHT - y);
   }

   /*
    * This method shifts the barcode image to the lower left of the 
    * barcode grid.
    */
   private void moveImageToLowerLeft(int xOffset, int yOffset)
   {
      this.shiftImageLeft(xOffset);
      this.shiftImageDown(yOffset);
   }

   /*
    * This method shifts the barcode image to the bottom of the barcode grid.
    * 
    */
   private void shiftImageDown(int offset)
   {
      if (offset < 0) 
      {
         return;
      }
      boolean pixelValue;
      BarcodeImage tempImage = new BarcodeImage();
      for (int x = 0; x < BarcodeImage.MAX_WIDTH; x++)
      {
         for (int y = 0; y <  BarcodeImage.MAX_HEIGHT; y++)
         {
            pixelValue = this.image.getPixel(y, x);
            tempImage.setPixel(y + offset + 1, x, pixelValue);
         }
      }
      this.image = tempImage;
   }

   /*
    * This method shifts the barcode image to the left of the barcode grid.
    */
   private void shiftImageLeft(int offset)
   {
      if (offset < 0) 
      {
         return;
      }
      boolean pixelValue;
      for (int x = 0; x < BarcodeImage.MAX_WIDTH; x++)
      {
         for (int y = 0; y < BarcodeImage.MAX_HEIGHT; y++)
         {
            pixelValue = this.image.getPixel(y, x);
            this.image.setPixel(y, x - offset, pixelValue);
         }
      }
   }

   /*
    * This should display only the relevant portion of the image, clipping the
    * excess blank/white from the top and right. Also, show a border as in:
    */
   public void displayImageToConsole()
   {
      for (int x = 0; x < getWidth() + 4; x++)
      {
         System.out.print("-");
      }
      System.out.println();
      
      for (int i = BarcodeImage.MAX_HEIGHT - getHeight() - 2; 
         i < BarcodeImage.MAX_HEIGHT; i++)
      {
         System.out.print("|");
         for (int j = 0; j < getWidth() + 2; j++)
         {
            System.out.print(this.image.getPixel(i,j) ? '*' : ' ');
         }
         System.out.println("|");
      }
   }

   /*
    * This method generates a Barcode Image from the text contained in the
    * DataMatrix.
    */
   public boolean generateImageFromText()
   {
      
      int index = 0;
      int letter;

      if (this.text == null)
         return false;

      this.clearImage();
      this.generateLimitationLine();
      this.generateOpenBorderLine();

      // fill in barcode image.
      for (int x = 1; x < this.text.length() + 1; x++)
      {
         letter = this.text.charAt(index);
         index++;
         this.writeCharToCol(x, letter);
      }
      this.actualHeight = this.computeSignalHeight();
      this.actualWidth = this.computeSignalWidth();
      return false;
   }

   /*
    * This method writes a character value to a column in the BarcodeImage 
    * saved in the DataMatrix.
    */
   private boolean writeCharToCol(int col, int code)
   {

      int y = BarcodeImage.MAX_HEIGHT - 2;
      
      while(code != 0)
      {
         if (code % 2 == 1)
            this.image.setPixel(y, col, true);
         y--;
         code /= 2;
      }
      
      return true;
   }

   /*
    * This method creates a limitation line for the BarcodeImage object saved in
    * the DataMatrix.
    */
   private void generateLimitationLine()
   {
      for (int y = BarcodeImage.MAX_HEIGHT; 
         y > BarcodeImage.MAX_HEIGHT - 11; y--)
      {
         this.image.setPixel(y, 0, true);
      }
      for (int x = 0; x < this.text.length() + 1; x++)
      {
         this.image.setPixel(BarcodeImage.MAX_HEIGHT - 1, x, true);
      }
   }

   /*
    * This method creates the open border line for the BarcodeImage object saved
    * in the DataMatrix.
    */
   private void generateOpenBorderLine()
   {
      for (int x = 0; x < this.text.length() + 2; x++)
      {
      if (x % 2 == 0)
         this.image.setPixel(BarcodeImage.MAX_HEIGHT - 10, x, true);
      }
      for (int y = BarcodeImage.MAX_HEIGHT; y > BarcodeImage.MAX_HEIGHT - 11;
         y--)
      {
         if (y % 2 == 1)
            this.image.setPixel(y, this.text.length() + 1, true);
      }
   }

   /*
    * This method generates the text by reading the barcode image in the
    * DataMatrix.
    */
   public boolean translateImageToText()
   {
      this.text = "";
      if (this.image == null)
      {
         return false;
      }
      for (int i = 1; i < getWidth() + 1; i++)
      {
         this.text += readCharFromCol(i);
      }
      return true;
   }
   
   /*
    * This method receives a column number and converts it to the appropriate
    * char value.
    */
   private char readCharFromCol(int col)
   {
      char output = 0;
      int exp = 0;
      for (int row = BarcodeImage.MAX_HEIGHT - 2; 
         row > BarcodeImage.MAX_HEIGHT - getHeight() - 1; row--)
      {
         if (image.getPixel(row, col))
         {
            output += Math.pow(2, exp);
         }
         exp++;
      }
      return output;
   }

   /*
    * This method displays the text contained in the DataMatrix to the 
    * console.
    */
   public void displayTextToConsole()
   {
      System.out.println("The message is: " + this.text);
   }
   
   /*
    * This method sets the image to white (false).
    */
   private void clearImage()
   {
      for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++)
      {
         for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++)
         {
            image.setPixel(i,j, false);
         }
      }
      this.actualWidth = 0;
      this.actualHeight = 0; 
   }
}

/****************************OUTPUT*********************************************
Beginning BarcodeImage and DataMatrix test
Printing out BarcodeImage input:                                                
                 
                                                                 
                                                                 
                                                                 
                                                                 
                                                                 
                                                                 
                                                                 
                                                                 
                                                                 
                                                                 
                                                                 
                                                                 
                                                                 
                                                                 
                                                                 
                                                                 
     * * * * * * * * * * * * * * * * * * * * *                   
     *                                       *                   
     ****** **** ****** ******* ** *** *****                     
     *     *    ******************************                   
     * **    * *        **  *    * * *   *                       
     *   *    *  *****    *   * *   *  **  ***                   
     *  **     * *** **   **  *    **  ***  *                    
     ***  * **   **  *   ****    *  *  ** * **                   
     *****  ***  *  * *   ** ** **  *   * *                      
     *****************************************                   
                                                                 
                                                                 
                                                                 
Translating image to text...
The message is: CSUMB CSIT online program is top notch.

Now you see formatted image without blank right columns and blank top rows.
Enjoy!

-------------------------------------------
|* * * * * * * * * * * * * * * * * * * * *|
|*                                       *|
|****** **** ****** ******* ** *** *****  |
|*     *    ******************************|
|* **    * *        **  *    * * *   *    |
|*   *    *  *****    *   * *   *  **  ***|
|*  **     * *** **   **  *    **  ***  * |
|***  * **   **  *   ****    *  *  ** * **|
|*****  ***  *  * *   ** ** **  *   * *   |
|*****************************************|

Processing second test image...
The message is: You did it!  Great work.  Celebrate.

Now you see formatted image without blank right columns and blank top rows.
Enjoy!

----------------------------------------
|* * * * * * * * * * * * * * * * * * * |
|*                                    *|
|**** *** **   ***** ****   *********  |
|* ************ ************ **********|
|** *      *    *  * * *         * *   |
|***   *  *           * **    *      **|
|* ** * *  *   * * * **  *   ***   *** |
|* *           **    *****  *   **   **|
|****  *  * *  * **  ** *   ** *  * *  |
|**************************************|

Processing custom message...

Displaying the message and BarcodeImage: 
The message is: What a great resume builder this is!
----------------------------------------
|* * * * * * * * * * * * * * * * * * * |
|*                                    *|
|***** * ***** ****** ******* **** **  |
|* ************************************|
|**  *    *  * * **    *    * *  *  *  |
|* *               *    **     **  *  *|
|**  *   * * *  * ***  * ***  *        |
|**      **    * *    *     *    *  * *|
|** *  * * **   *****  **  *    ** *** |
|**************************************|

*******************************************************************************/