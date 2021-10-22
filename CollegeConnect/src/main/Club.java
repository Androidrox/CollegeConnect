import java.util.ArrayList;

public class Club {
   private int namesId;
   private String name;
   private int numMembers;
   private String location;
   private String meetingTime;
   private String fees;
   private String description;
   private String president;
   private String presPhone;
   private String presEmail;
   private ArrayList<String> keyWords = new ArrayList<String>();

   Club() {

   }

   Club(String name, int numMembers, String location,
        String meetingTime, String fees, String description,
        String president, String presPhone, String presEmail,
        ArrayList<String> keyWords){
      this.name = name;
      this.numMembers = numMembers;
      this.location = location;
      this.meetingTime = meetingTime;
      this.fees = fees;
      this.description = description;
      this.president = president;
      this.presPhone = presPhone;
      this.presEmail = presEmail;
      this.keyWords = keyWords;
      this.namesId = namesId;
   }

   // getter for name
   public String getName() {
      return name;
   }
   // getter for numMembers
   public int getNumMembers() {
      return numMembers;
   }
   // getter for description
   public String getDescription() {
      return description;
   }
   // getter for fees
   public String getFees() {
      return fees;
   }
   // getter for location
   public String getLocation() {
      return location;
   }
   // getter for meetingTime
   public String getMeetingTime() {
      return meetingTime;
   }
   // getter for presEmail
   public String getPresEmail() {
      return presEmail;
   }
   // getter for president
   public String getPresident() {
      return president;
   }
   // getter for presPhone
   public String getPresPhone() {
      return presPhone;
   }
   // getter for namesID
   public int getNamesId() {
      return namesId;
   }

   // getter for keyWords
   public ArrayList<String> getKeyWords() {
      return keyWords;
   }

   // method for checking if keyword is contained
   // within the array list keywords
   // returns true if keyword matches and false
   // if it doesn't
   public boolean hasKeyword(String keyword){
      boolean found = false;
      for(int i = 0; i < keyWords.size(); i++)
      {
         if(keyWords.get(i) == keyword)
            found = true;
      }
      if (found == true)
         return true;

      else
         return false;
   }
}
