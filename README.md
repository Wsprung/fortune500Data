Whitney Sprung
November, 2021

##Methods
  Dataset: Describe what you know about how the data was generated. If possible, include information about who owns the dataset and why you are able to use it.
    The dataset was created by an Associate Professor in the Department of Political Science at Stanford University, Adam Bonica. He derived this data from publicly available information on executives and board members of Fortune 500 companies from DIME, the Database on Ideology, Money in Politics, and Elections. DIME contains over 130 million instances of political contributions made by individuals and organizations to local, state, and federal elections over a large time period. DIME was created using publicly accessible data from various sources, such as government websites and groups dedicated to transparency in politics. Advanced methods were used to collect and compile this data, and using R, Bonica created the dataset I analyzed that is smaller and more specific than DIME.

    The dataset I analyzed focuses on total contributions made by Fortune 500 directors and CEOs, instead of those made by a variety of people and groups, from political elites to interest groups. The dataset I analyzed was published in 2017, and covers political contributions of corporate CEOs and directors serving as of July 2012. I know Bonica used R to create the dataset I analyzed as I was able to get in touch with him regarding gaps in the dataset and industries listed for certain companies. Since his source for the company's industry codes has been updated since the dataset was produced, some industries are listed as "NA." This dataset is publicly available with citation:
      Bonica, Adam, 2017, "Replication Data for: Avenues of Influence: On the Political Expenditures of Corporations and Their Directors and Executives", https://doi.org/10.7910/DVN/6R1HAS, Harvard Dataverse, V1, UNF:6:THMAHB0v4+WMET/RvYi1+g== [fileUNF]

  Process: How did you go about answering your questions? What specific pieces of information did you need from your dataset and how did you combine data to arrive at your answer?
    I tackled question 2 first, as it was simpler to address and needed only one method to do so (along with smaller, general helper methods). I used a Scanner object to go through each line of my dataset, create an array with this line, and then find the String at a specific column that marked the leader's gender, and the int (0 or 1) that marked whether or not the leader was a CEO, chairman, or board member. I created a 2D array containing the total leaders for each gender as well as the number of CEOs, chairmen, and board members. I also included the number of "double counted" leaders, which means they were both CEOs and chairman. I originally only planned to find the gender distribution of total leaders, but felt as if more information on the actual position of these leaders was needed to fully attack my proposed question. It is worth noting that this dataset is limited to two genders under the "gender" column, male and female, and those who may identify differently were not represented.

    Question 1 was more difficult to answer, as the more information I derived from my dataset, the more I realized that I needed additional information from the dataset to answer this question. I first created the method totalIndustryDonations(), which returns an IndustryDonations array, containing the industry, Democrat contributions, and Republican contributions of the industries whose leaders donated the most and least to each party. Once I got these results, I realized that not much is said with just the highest and lowest total contributions, as industries could have the largest expenditures due to having the most leaders in their industry. So, I created numIndustry(), which returns the number of individuals within an industry. I divided the total contributions by this number to find the average donation per leader for those industries. I also found that the highest Democrat contributions were from companies labeled "NA" under industry. "NA" is listed as a result of an update in the original industries that the dataset creator has not accounted for as this is an older dataset. In order to find more valuable information, I also found the second highest contributing industry to Democrats. In addition, I wrote getCompaniesNA() to get an ArrayList of the companies falling under the NA industry to determine whether they were miscellaneous companies, or ones with antiquated labels. I saw that companies labeled as NA under industry did in fact come from a common industry, such as retail, so I decided to reach out to the dataset creator to inquire about the labelings. He kindly responded with information on how the labels were acquired, and why NA was now included. With combined information on the industries with the highest and lowest contributions, the number of leaders in these industries, and companies listed as "NA" under industry, I was able to tackle this question.

  Challenges: Did you run into any difficulties? If so, how did you approach solving them?
    One of the main difficulties I ran into was continuously not having enough information to answer question 1. I approached this problem by writing more code covering different gaps in my answer to provide more foundation to my findings.

    Another infamous difficulty I faced was in running the command "git reset --hard" in my terminal after "git pull" had overridden my local repository and synced it with the one on the cloud, which did not contain updated methods. Since I had my .class files still and I remembered how I wrote my method originally, I was able to recreate my original method. It ended up being a blessing that I ran this command which killed my method, as it turns out my original one was producing incorrect results due to a logic error.

    I also faced difficulties in taking data from my dataset, as some companies, for example “Harley-Davidson, Inc” have a comma in their title. Commas are the delimiter used for this dataset as well, so this title got split into multiple spaces in the array. As a result, the indexes got messed up for some of the arrays. For example, gender is at index 8 in the ArrayList columnTitles, but for companies with a comma in their name it is at index 9. To fix this issue, I said nextLine = nextLine.replaceAll(", Inc", ""), to replace all titles with commas in their name. The only company titles with commas were ones with “, Inc.” This worked fine when solving the gender distribution question as it did not focus on the company names, so it did not affect my results that a few names had “, Inc” removed from them.

    Lastly, some people are both the CEOs and chairman of their company. Those filling both these positions made the total number of leaders for each gender appear larger than the actual amount in the dataset. I realized this upon checking my answers with the number of rows in the dataset. To show the possibility of "double counted" leaders, I included a 5th column in my 2D array that represents the number of men (row 0) and women (row 1) who fill both these roles.

##Results and Conclusion
  What did the data show? What are your takeaways?
    Please refer to the Illustrator infographic containing my results to see what my data shows https://github.com/Wsprung/fortune500Data/blob/main/FortuneDataSummaryPage.ai. I recommend opening this file in Illustrator in order to see it in high resolution, so small text can be read properly.

    Not included in this infographic however is a comparison of these industry's political preferences with the ones of their leaders. Stanford political science professors in 2017 completed a study on the political preferences of tech elites, and found through surveying them, that "broadly speaking...they are pretty liberal" (https://www.vox.com/policy-and-politics/2017/9/6/16260326/tech-entrepreneurs-survey-politics-liberal-regulation-unions). This is interesting, because in the dataset I analyzed the highest Republican contributions were from individuals in the computer hardware industry. Silicon Valley also houses many of the listed computer hardware companies, like Apple and Hewlett Packard, and Silicon Valley is often characterized as progressive and liberal. NY Mag did an interesting article on the contrast between how tech elites characterize their preferences versus what they really are, which can be found here: https://nymag.com/intelligencer/2018/11/study-shows-tech-elites-are-less-liberal-than-they-think.html. Not only did computer hardware companies donate the most to Republicans, but they also donated the most per person to political contributions period, with a whopping amount that can be found in the infographic. The political contributions of other industries significant in my findings, computer peripherals and electric utilities, are not as discussed online, but if I had more time I would like to explore more about these lesser known industries and what political affiliation they have and how it benefits them.

  What are some related or additional questions that you would explore if you had more time and resources (ex. other datasets, additional Java data analysis libraries)?
   If I had more time and resources, I would like to explore the difference between donations to incumbent and nonincumbent candidates made by these leaders, as incumbents usually get more contributions due to higher winning likelihood and more pre-existing power in politics. In addition, I feel as it would be interesting to examine the political contributions of female vs. male leaders and whether wealth or gender would be a more powerful indicator of their political preferences. I would also like to look at the gender distribution of these leaders over time, to see if, despite numbers of female leaders being low, the number is growing and has been improving in recent years. Finally, I would like to explore whether the companies in this dataset are still powerful and if the leaders are still donating the same way, as the political landscape has changed a lot in the past for years.
