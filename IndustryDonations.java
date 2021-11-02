
public class IndustryDonations {
  //Public variables because the dataset is public, so it is OK for my user to have access to these attributes.
  public String industry;
  public int totalDem;
  public int totalRep;

  //String[] that holds the current line of the File that totalIndustryDonations is working on
  public IndustryDonations(String[] nextLineOfFile) {
    ArrayList<String> colTitles = FortuneData.columnTitles;
    this.industry = nextLineOfFile[columnTitles.indexOf("industry")];
    this.totalDem = nextLineOfFile[columnTitles.indexOf("total.dem")];
    this.totalRep = nextLineOfFile[columnTitles.indexOf("total.rep")];
  }

  private void findIndustry() {

  }

  private void findTotalDemRep() {

  }

  public void incrementTotalDem() {

  }
  public void incrementTotalRep() {

  }
}
