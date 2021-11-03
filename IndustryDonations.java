import java.util.*;
import java.io.*;

public class IndustryDonations
{
    public String industry;
    public double totalDem;
    public double totalRep;
    public static ArrayList<String> industryNames = new ArrayList<String>();

    public IndustryDonations(String industry, double totalDem, double totalRep) {
        this.industry = industry;
        this.totalDem = totalDem;
        this.totalRep = totalRep;
        this.sortIndustries();
    }

    private void sortIndustries() {
        boolean b = true;
        for (int i = 0; i < industryNames.size(); i++) {
            if (industryNames.get(i).equals(industry)) {
                b = false;
            }
        }
        if (b) {
            industryNames.add(this.industry);
        }
    }

    public void changeIndustry(final String industry) {
        this.industry = industry;
    }

    public double findTotalDemRep() {
        return this.totalDem + this.totalRep;
    }

    public String toString() {
        return industry + ", " + "total amount contributed to Democrats: " + totalDem + "total amount contributed to Republicans: " + totalRep;
    }
  }
