import java.util.*;
import java.io.*;

public class FortuneData {
  //Data is public, so it is OK if my attributes containing the data are too, so users can access them.
  public static File LEADERS500;
  public static Scanner LEADERS500_SC;
  public static ArrayList<String> columnTitles;

  public FortuneData() throws FileNotFoundException {
   LEADERS500 = new File("fortune500Leaders.csv");
   resetScannerSetUp(LEADERS500);
  }

 private static String[] nextLineOfFile() {
  String[] nextLineOfFile = new String[32];
  String nextLine = "";

  if(!LEADERS500_SC.hasNextLine()) { return null; }
  nextLine = LEADERS500_SC.nextLine();
  nextLine = nextLine.replaceAll(", Inc", "");
  nextLineOfFile = nextLine.split(",",0);

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
  public static int[][] countGender() throws FileNotFoundException {
    //At the end of countGender() and other methods, the Scanner is closed.
    //Call resetScannerSetUp to assure that you have a running Scanner that is set to the right lines,
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

  //Find how many occurences there are of donations under an "NA" industry
  private static int numIndustry(ArrayList<IndustryDonations> iDAL, String indName) {
    int count = 0;
    //index of NA in IndustryDonations.industryNames is 33
    for(int i = 0; i < iDAL.size(); i++) {
      if(iDAL.get(i).industry.equals(indName)) { count++; }
    }
    return count;
  }

  public static ArrayList<String> getCompaniesNA() throws FileNotFoundException {
    resetScannerSetUp(LEADERS500);
    ArrayList<String> companiesNA = new ArrayList<>();

    while(LEADERS500_SC.hasNextLine()) {
      String[] nextLineOfFileS = nextLineOfFile();
      if(nextLineOfFileS == null) { break; }
      boolean b = true;
      //Does the current array have an industry NA?
      if(nextLineOfFileS[columnTitles.indexOf("industry")].equals("NA")) {
      //If yes, iterate through companiesNA, and see if the company name of the array is already in companiesNA, if not, add it
      for (int i = 0; i < companiesNA.size(); i++) {
          if (companiesNA.get(i).equals(nextLineOfFileS[columnTitles.indexOf("corp.name")])) { b = false; }
      }
      if (b) { companiesNA.add(nextLineOfFileS[columnTitles.indexOf("corp.name")]); }
    }
  }
 return companiesNA;
}

  //Which industry of company’s CEOs/directors donate the most/least total money to republicans/democrats?
   public static IndustryDonations[] totalIndustryDonations() throws FileNotFoundException {
     resetScannerSetUp(LEADERS500);
     ArrayList<IndustryDonations> iDAL= new ArrayList<>();
     IndustryDonations[] toRet = new IndustryDonations[4];
     double[] totalReps;
     double[] totalDems;
     String industry;
     double totalDem;
     double totalRep;

     while(LEADERS500_SC.hasNextLine()) {
       String[] nextLineOfFileS = nextLineOfFile();
       if(nextLineOfFileS == null) { break; }

       industry = nextLineOfFileS[columnTitles.indexOf("industry")];
       totalDem = Double.parseDouble(nextLineOfFileS[columnTitles.indexOf("total.dem")]);
       totalRep = Double.parseDouble(nextLineOfFileS[columnTitles.indexOf("total.rep")]);
       iDAL.add(new IndustryDonations(industry, totalDem, totalRep));
 //Get arraylist with every line equivalent ID OBJECT
 //Get arraylist with industryNames
 //Make parallel array and increment through arraylist of ID objects and increment each industry's total
     }

     totalDems = new double[IndustryDonations.industryNames.size()];
     totalReps = new double[IndustryDonations.industryNames.size()];
     for(int i = 0; i < iDAL.size(); i++) {
       industry = iDAL.get(i).industry;
       int index = IndustryDonations.industryNames.indexOf(industry);
       totalDems[index] = totalDems[index] + iDAL.get(i).totalDem;
       totalReps[index] = totalReps[index] + iDAL.get(i).totalRep;
     }

//Finding the largest value in each array
double max = totalDems[0];
int indexD = 0;
for(int i = 0; i < totalDems.length; i++) {
    if(max < totalDems[i]) {
      max = totalDems[i];
      indexD = i;
    }
 }
max = totalReps[0];
int indexR = 0;
for(int i = 0; i < totalDems.length; i++) {
   if(max < totalReps[i]) {
     max = totalReps[i];
     indexR = i;
   }
}

toRet[0] = new IndustryDonations(IndustryDonations.industryNames.get(indexD), totalDems[indexD], totalReps[indexD]);
toRet[1] = new IndustryDonations(IndustryDonations.industryNames.get(indexR), totalDems[indexR], totalReps[indexR]);

//Finding and printing the second largest value in the totalDems array, since the largest donation total came from "NA" industry, so I wanted to find a more specific answer
max = totalDems[0];
indexR = 0;
for(int i = 0; i < totalDems.length; i++) {
   if(max < totalDems[i] && i!=33) {
     max = totalDems[i];
     indexD = i;
   }
}
System.out.println("The second highest Democrat contribution total was: " + max + " from the " + IndustryDonations.industryNames.get(indexD) + " industry. This industry contributed a total " + totalReps[indexD] + " to Republicans.");

//I found that the largest donors to democrats were a part of the "NA" industry, which includes various companies like Eastman Kodak
//To add perspective, I wanted to find the smallest donating industry to Republicans and Democrats, these industries are index 2 and 3 of toRet
//Finding the smallest value in each array
double min = totalDems[0];
int indexDMin = 0;
for(int i = 0; i < totalDems.length; i++) {
    if(min > totalDems[i]) {
      min = totalDems[i];
      indexDMin = i;
    }
 }
min = totalReps[0];
int indexRMin = 0;
for(int i = 0; i < totalDems.length; i++) {
   if(min > totalReps[i]) {
     min = totalReps[i];
     indexRMin = i;
   }
}

  toRet[2] = new IndustryDonations(IndustryDonations.industryNames.get(indexDMin), totalDems[indexDMin], totalReps[indexDMin]);
  toRet[3] = new IndustryDonations(IndustryDonations.industryNames.get(indexRMin), totalDems[indexRMin], totalReps[indexRMin]);

   System.out.println("Number of leaders working for companies in NA industry: " + numIndustry(iDAL, "NA"));
   System.out.println("Number of leaders working for companies in the Computer Hardware industry: " + numIndustry(iDAL, "Computer Hardware"));
   System.out.println("Number of leaders working for companies in the Electric Utilities industry: " + numIndustry(iDAL, "Electric Utilities"));
   System.out.println("Number of leaders working for companies in Computer Peripherals industry: " + numIndustry(iDAL, "Computer Peripherals"));


  LEADERS500_SC.close();
   return toRet;
   }

  public static void main(String[] args) throws FileNotFoundException {
    FortuneData test = new FortuneData();
    System.out.println("[totalMaleLeaders, maleCEOs, maleChairmen, maleBoardMembers, doubleCounted],[totalFemaleLeaders, femaleCEOs, femaleChairmen, femaleBoardMembers, doubleCounted]");
    int[][] numMaleFemale = countGender();
    System.out.println(Arrays.deepToString(numMaleFemale));
    System.out.println(Arrays.toString(totalIndustryDonations()));
    //By the way the dataset was organized, some company names are listed with slight variations.
    //As a result, the statement below prints a list with some repeats.
    //Refer to my infographic to see the fixed list, which does not contain the repeats (ex. Berkshire Hatahway and Berkshire Hathaway)
    System.out.println("Companies falling under the NA industry: " + getCompaniesNA());
  }
}
