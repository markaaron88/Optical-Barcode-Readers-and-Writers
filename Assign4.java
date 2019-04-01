/*
* Team 9 - W. Robleh, M. Mariscal, T. Doan, Y. Nikulyak, C. Piwarski
* CST338 - Software Design
* Assignment 4
* 27 March 2019
*/


//interface
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
      System.out.println("Debug BarcodeImage class.");
      bc.displayToConsole();
      DataMatrix dm = new DataMatrix(bc);

      // First secret message
      dm.translateImageToText();
      dm.displayTextToConsole();
      System.out.println("\nNow you see a raw unformatted image\n");
      dm.displayRawImage();
      System.out
            .println("\nNow you see formatted image without blank right " + "columns and blank top rows.\nEnjoy!\n");
      dm.displayImageToConsole();

      // second secret message
      bc = new BarcodeImage(sImageIn_2);
      dm.scan(bc);
      System.out.println("translate image to text below");
      dm.translateImageToText();
      System.out.println("translate image to text above");
      dm.displayTextToConsole();
      System.out.println("\nNow you see a raw unformatted image\n");
      dm.displayRawImage();
      System.out
            .println("\nNow you see formatted image without blank right " + "columns and blank top rows.\nEnjoy!\n");
      dm.displayImageToConsole();

      // create your own message
      System.out.println("Look Below");
      dm.readText("What a great resume builder this is!");
      System.out.println("Look Above");
      dm.generateImageFromText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
      
      
      bc = new BarcodeImage(sImageIn);
      System.out.println("Debug BarcodeImage class.");
      bc.displayToConsole();
      DataMatrix testMatrix = new DataMatrix(bc);
      System.out.println("Printing DataMatrix Raw (clean):");
      testMatrix.displayRawImage();
      System.out.println("Printing Clean "
            + "Image:");
      testMatrix.displayImageToConsole();
      System.out.println("Display Test Message: ");
      testMatrix.translateImageToText();
      testMatrix.displayTextToConsole();
      
      System.out.println("Testing String constructor");
      DataMatrix testMatrix2 = new DataMatrix("What a great resume builder this is!");
      testMatrix2.generateImageFromText();
      testMatrix2.displayImageToConsole();
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
    * Default constructor instantiates a 2D array (MAX_HEIGHTxMAX_WIDTH) and stuffs
    * it all with blanks - false - boolean default value.
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
    * Private helper for constructor with parameter to check the incoming data for
    * every conceivable size or null error. Smaller size is acceptable, bigger or
    * null is not.
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

   // accessor for each bit in the image
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
    * clone method need to be overriden if a class implements Cloneable interface
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
    * This constructor method receives a text string and creates an associated BarcodeImage.
    */
   public DataMatrix(String text)
   {
       this.readText(text);
       this.image = new BarcodeImage();
   }

   // readText(String text) - a mutator for text. Like the constructor; in fact it
   // is called by the constructor.
    public boolean readText(String text)
   {
      if (text == null || text.length() > BarcodeImage.MAX_WIDTH)
         return false;
      this.text = text;
      return true;
   }
   /*
    * This method receives an image
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
    * This method computes the width of the barcode image in the DataMatrix object.
    */
   private int computeSignalHeight() // goes from lower left corner to the right and checks for value
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
   // This private method will make no assumption about the placement of the
   // "signal" within a passed-in BarcodeImage.
   // In other words, the in-coming BarcodeImage may not be lower-left justified.
   // Here's an example of the placement of such an un-standardized image:
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

      // get lower left corner of BarcodeImage
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
      System.out.println("width: " + getWidth());
      System.out.println("height: " + getHeight());
      for (int i = BarcodeImage.MAX_HEIGHT - getHeight() - 2; i < BarcodeImage.MAX_HEIGHT; i++)
      {
         for (int j = 0; j < getWidth() + 2; j++)
         {
            System.out.print(this.image.getPixel(i,j) ? '*' : ' ');
         }
         System.out.println();
      }
   }

   /*
    * This method shows the full image data including the blank top and right.
    */
   public void displayRawImage()
   {
      this.image.displayToConsole();
   }

   /*
    * This method generates a Barcode Image from the text contained in the
    * DataMatrix.
    */
   public boolean generateImageFromText()
   {
      int index = 0;
      int letter;

      // create closed limitation line
      this.generateLimitationLine();
      // create open border line
      this.generateOpenBorderLine();

      // fill in barcode image.
      for (int x = 1; x < this.text.length(); x++)
      {
         letter = this.text.charAt(index++);
         this.writeCharToCol(x, letter);
      }
      return false;
   }

   /*
    * This method writes a character value to a column in the BarcodeImage saved in
    * the DataMatrix.
    */
   private boolean writeCharToCol(int col, int code)
   {
      double divisor = 128;
      int columnCounter = 9;

      do
      {
         if ((code / divisor) > 1)
            this.image.setPixel(col, columnCounter, true);
         code /= divisor;
         divisor /= 2;
         columnCounter--;
      } while (columnCounter > 0);
      return false;
   }

   /*
    * This method creates a limitation line for the BarcodeImage object saved in
    * the DataMatrix.
    */
   private void generateLimitationLine()
   {
      for (int y = 0; y < 11; y++)
      {
         this.image.setPixel(0, y, true);
      }
      for (int x = 0; x < this.text.length(); x++)
      {
         this.image.setPixel(x, 0, true);
      }
   }

   /*
    * This method creates the open border line for the BarcodeImage object saved in
    * the DataMatrix.
    */
private void generateOpenBorderLine()
{
     for (int x = 0; x < this.text.length() + 1; x++)
   {
      if (x % 2 == 0)
        this.image.setPixel(10, x, true);
   }
  for (int y = 0; y < 11; y++)
  {
     if (y % 2 == 0)
        this.image.setPixel(y, 10, true);
  }
}

   /*
    * This method generates the text by reading the barcode image in the
    * DataMatrix.
    */
   public boolean translateImageToText()
   {
      // String translate = "";
      text = ""; // initialize text variable
      if (this.image == null)
      {
         return false;
      }
      for (int i = 1; i < getWidth() + 1; i++)
      {
         text += readCharFromCol(i);
      }
      return true;
   }
   
   /*
    * accepts int col. The values are then added into exponent form.
    */
   private char readCharFromCol(int col)
   {

      char output = 0;
      int exp = 0;
      for (int row = BarcodeImage.MAX_HEIGHT - 2; row > BarcodeImage.MAX_HEIGHT - getHeight() - 1; row--)
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
    * This method sets the image to white (false).
    */
   private void clearImage()
   {
      for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++)
      {
         for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++)
         {
            System.out.print(image.setPixel(' ',' ', false));
         }
         System.out.println();
      }
   }
}