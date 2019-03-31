/*
 * Team 9 - W. Robleh, M. Mariscal, T. Doan, Y. Nikulyak, C. Piwarski
 * CST338 - Software Design
 * Assignment 4
 * 27 March 2019
 */


//interface
interface BarcodeIO {
   public boolean scan(BarcodeImage bc);
   public boolean readText(String text);
   public boolean generateImageFromText();
   public boolean translateImageToText();
   public void displayTextToConsole();
   public void displayImageToConsole();
}

public class Assig4 {

   public static void main(String[] args) {
      
      String[] sImageIn =
         {
            "                                               ",
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
            "                                               "
         };
      
      String[] sImageIn_2 =
         {
               "                                          ",
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
               "                                          "
         };
      
      BarcodeImage bc = new BarcodeImage(sImageIn);
      System.out.println("Debug BarcodeImage class.");
      bc.displayToConsole();
      DataMatrix dm = new DataMatrix(bc);
     
      // First secret message
      dm.translateImageToText();
      dm.displayTextToConsole();
      System.out.println("\nNow you see a raw unformatted image\n");
      dm.displayRawImage();
      System.out.println("\nNow you see formatted image without blank right "
            + "columns and blank top rows.\nEnjoy!\n");
      dm.displayImageToConsole();
      
      // second secret message
      bc = new BarcodeImage(sImageIn_2);
      dm.scan(bc);
      dm.translateImageToText();
      dm.displayTextToConsole();
      System.out.println("\nNow you see a raw unformatted image\n");
      dm.displayRawImage();
      System.out.println("\nNow you see formatted image without blank right "
            + "columns and blank top rows.\nEnjoy!\n");
      dm.displayImageToConsole();
      
      // create your own message
      dm.readText("What a great resume builder this is!");
      dm.generateImageFromText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
   }
}
/*This class will realize all the essential data and methods associated with a 
 *2D pattern, an image of a square or rectangular bar code. 
 */
class BarcodeImage  implements Cloneable {
   
   public static final int MAX_HEIGHT = 30;
   public static final int MAX_WIDTH = 65;
   private boolean[][] imageData;
   
   /*Default constructor instantiates a 2D array (MAX_HEIGHTxMAX_WIDTH) and 
    * stuffs it all with blanks - false - boolean default value.
    */
   public BarcodeImage() {
      imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
   }
   
   /*Takes a 1D array of Strings and converts it to the internal 2D array of 
    * booleans.
    */
   public BarcodeImage(String[] strData) {
      
      this();
      //test validity of a parameter
      if (!checkSize(strData)) {
         return;
      }
      int row = MAX_HEIGHT -1;
      int col = 0;
      for (int i = strData.length - 1; i >= 0; i--) {
         String str = strData[i];
         for (int j = 0; j < str.length(); j++) {
            if (str.charAt(j) == DataMatrix.BLACK_CHAR) {
               imageData[row][col] = true;
            }
            col++;
         }
         row--;
         col = 0;
      }         
   }
   
   /*Private helper for constructor with parameter to check the incoming data 
    * for every conceivable size or null error. Smaller size is acceptable, 
    * bigger or null is not.
    */
   private boolean checkSize(String[] data) {
      
      //check string array
      if (data == null || data.length > MAX_HEIGHT) {
         return false;
      }
      
      //check each string in a string array
      for (int i = 0; i < data.length; i++) {
         if (data[i] == null || data[i].length() >= MAX_WIDTH) {
            return false;
         }
      }
      return true;
   }
   
   //accessor for each bit in the image
   public boolean getPixel(int row, int col) {
      //test validate of parameters
      if (row >= MAX_HEIGHT || col >= MAX_WIDTH || row < 0 || col < 0) {
         return false;
      }
      return imageData[row][col];
   }
   
   //mutator for each bit in the image
   public boolean setPixel(int row, int col, boolean value) {
      //test validity of parameters
      if (row >= MAX_HEIGHT || col >= MAX_WIDTH || row < 0 || col < 0) {
         return false;
      }
      imageData[row][col] = value;
      return true;
   }
   /*clone method need to be overriden if a class implements Cloneable interface*/
   @Override
   public Object clone() throws CloneNotSupportedException {
      BarcodeImage copy = (BarcodeImage)super.clone();
      
      copy.imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
      for (int i = 0; i < MAX_HEIGHT; i++) {
         for (int j = 0; j < MAX_WIDTH; j++) {
            copy.imageData[i][j] = this.imageData[i][j];
         }
      }
      return copy;
   }
   
   //Method just for debugging
   public void displayToConsole() {
      
      for (int i = 0; i < MAX_HEIGHT; i++) {
         for (int j = 0; j < MAX_WIDTH; j++) {
            System.out.print(imageData[i][j] ? '*' : ' ');
         }
         System.out.println();
      }
   }
}

class DataMatrix implements BarcodeIO {
   
   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';
}

