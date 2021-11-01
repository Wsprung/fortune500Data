import java.util.*;
import java.io.*;

public class fortuneData {
  public static void main(String[] args) throws FileNotFoundException {
    //File leaders500 = new File("test.txt");
    File leaders500 = new File("fortune500Leaders.csv");
    Scanner leaders500_sc = new Scanner(leaders500);
    leaders500_sc.useDelimiter(",");
//make an array of every row
    System.out.println(leaders500_sc.next());
    System.out.println("space");
    System.out.println(leaders500_sc.next());
    System.out.println("space");
    // System.out.println(leaders500_sc.next());
    // System.out.println("space");
    // System.out.println(leaders500_sc.next());
    // System.out.println("space");
    // System.out.println(leaders500_sc.next());**/
  }
}
