import java.util.*;
import java.io.*;
//WHEN DONE TESTING, CHANGE CLASS/STATIC METHODS TO NON-STATIC WHERE NEEDED
//MOST METHODS CAN ONLY RUN ONCE AN OBJECT OF THIS FILE HAS BEEN MADE

public class FortuneData {
  //Data is public, so it is OK if my attributes containing the data are too, so users can access them.
  public static File LEADERS500;
  public static Scanner LEADERS500_SC;
  public static ArrayList<String> columnTitles;

  public FortuneData() throws FileNotFoundException {
   LEADERS500 = new File("fortune500Leaders.csv");
   resetScannerSetUp(LEADERS500);
  }
 //Which industry of company’s CEOs/directors donate the most total money to republicans/democrats?
  //industry = index 13
  //18 = total contributions to democrats
  //19 = total contributions to republicans
  public static IndustryDonations[] totalIndustryDonations(Scanner LEADERS500_SC) throws FileNotFoundException {
    resetScannerSetUp(LEADERS500);

//need IndustryDonations to contain each Industry and the total to republicans and democrats for that Industry
//Use a while loop to go through and find the totaldems and totalreps for each company (chunks of industry title all together for each company)
//then use an embdedded for-loop that goes through and gets the first industry, then looks to see if any other elements have the same industry,
//if so, combine them into one Industry Donation object
    while(LEADERS500_SC.hasNextLine()) {
      String[] nextLineOfFileS = nextLineOfFile();
      if(nextLineOfFileS == null) { break; }


    }
  }


 private static String[] nextLineOfFile() {
  String[] nextLineOfFile = new String[32];
  String nextLine = "";

  if(!LEADERS500_SC.hasNextLine()) { return null; }
  nextLine = LEADERS500_SC.nextLine();
  System.out.println(nextLine);
  nextLine = nextLine.replaceAll(", Inc", "");
  nextLineOfFile = nextLine.split(",",0);
  //System.out.println(Arrays.toString(nextLineOfFile));

  return nextLineOfFile;
 }

 //Creates columnTitles ArrayList and increments past the title row of the File
 //Important method as it places Scanner on the line of the dataset where the data begins
 //When you want to "reset" the Scanner, call this method
  private static void resetScannerSetUp(File LEADERS500) throws FileNotFoundException {
    LEADERS500_SC = new Scanner(LEADERS500);
    LEADERS500_SC.useDelimiter(",");

    String nextLine = "";

    //first line is just the title, so increment past this
    LEADERS500_SC.nextLine();
    //second line is column titles, saving them to an array
    nextLine  = LEADERS500_SC.nextLine();
    columnTitles = new ArrayList<>(Arrays.asList(nextLine.split(",",0)));
  }

  //returns an integer 2Darray with index 0 being number of male CEOs, chairmen, board members, and executive chairmen, and index 1 the number of female CEOs, chairmen, board members, and executive chairmen.
  public static int[][] countGender(Scanner LEADERS500_SC) throws FileNotFoundException {
    //At the end of countGender() and other methods, the Scanner is closed.
    //Call resetScannerSetUp to assure that you have a running Scanner that is set to the write lines,
    //in case your Scanner was closed due to running other methods.
    resetScannerSetUp(LEADERS500);

    //gender code for data set: "M" for male, "F" for female (no NB option)

    //variables for this method

    int[][] maleVsFemale = new int[2][5];
    int gendInd = 0;
    int ceoInd = 0;
    int chairmanInd = 0;
    //{numMale, numMaleCEO, numMaleChairmen, numMaleBoardMembers, numMaleChairManAndCeo}
    //{numFemale, numFemaleCEO, numFemaleChairmen, numFemaleBoardMembers, numFemaleChairManAndCeo}



    //while loop to go through the file and count number of male and female leaders
    while(LEADERS500_SC.hasNextLine()) {
      String[] nextLineOfFileS = nextLineOfFile();

      if(nextLineOfFileS == null) { break; }

      //creates an array with the entire row Scanner is looking at
      gendInd = columnTitles.indexOf("gender");
      ceoInd = columnTitles.indexOf("ceo");
      chairmanInd = columnTitles.indexOf("chairman");
      //index of gender = 8
      if(nextLineOfFileS[gendInd].equals("M")) {
        maleVsFemale[0][0] = maleVsFemale[0][0] + 1;
        //9 = index of CEO (0 = not CEO, 1 = CEO)
        maleVsFemale[0][1] = maleVsFemale[0][1] + Integer.parseInt(nextLineOfFileS[9]);
        //10 = index of chairman (0 = not CEO, 1 = CEO)
        maleVsFemale[0][2] = maleVsFemale[0][2] + Integer.parseInt(nextLineOfFileS[10]);
        //if CEO and chairman both = 0, that means the person is a board member
        if(Integer.parseInt(nextLineOfFileS[ceoInd]) == 0 && Integer.parseInt(nextLineOfFileS[10]) == 0) {
          maleVsFemale[0][3] = maleVsFemale[0][3] + 1;
        }
        //if someone is an executive chairman, increment index 4
        if(Integer.parseInt(nextLineOfFileS[ceoInd]) == 1 && Integer.parseInt(nextLineOfFileS[10]) == 1) {
          maleVsFemale[0][4] = maleVsFemale[0][4] + 1;
        }
      }
      else {
        maleVsFemale[1][0] = maleVsFemale[1][0] + 1;
        maleVsFemale[1][1] = maleVsFemale[1][1] + Integer.parseInt(nextLineOfFileS[9]);
        maleVsFemale[1][2] = maleVsFemale[1][2] + Integer.parseInt(nextLineOfFileS[10]);
        if(Integer.parseInt(nextLineOfFileS[ceoInd]) == 0 && Integer.parseInt(nextLineOfFileS[chairmanInd]) == 0)  {
          maleVsFemale[1][3] = maleVsFemale[1][3] + 1;
        }
        if(Integer.parseInt(nextLineOfFileS[ceoInd]) == 1 && Integer.parseInt(nextLineOfFileS[chairmanInd]) == 1) {
          maleVsFemale[1][4] = maleVsFemale[1][4] + 1;
        }
      }
    }

  LEADERS500_SC.close();
  return maleVsFemale;
  }

  public static void main(String[] args) throws FileNotFoundException {
    FortuneData test = new FortuneData();
    int[][] numMaleFemale = countGender(LEADERS500_SC);
    System.out.println(Arrays.deepToString(numMaleFemale));
  }
}
