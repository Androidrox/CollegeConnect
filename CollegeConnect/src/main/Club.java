package main;
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
   // setter for name
   public void setName(String name) {
      this.name = name;
   }
   // setter for description
   public void setDescription(String description) {
      this.description = description;
   }
   // setter for description
   public void setFees(String fees) {
      this.fees = fees;
   }
   // setter for keywords
   public void setKeyWords(ArrayList<String> keyWords) {
      this.keyWords = keyWords;
   }
   // setter for location
   public void setLocation(String location) {
      this.location = location;
   }
   // setter for meeting time
   public void setMeetingTime(String meetingTime) {
      this.meetingTime = meetingTime;
   }
   // setter for names id
   public void setNamesId(int namesId) {
      this.namesId = namesId;
   }
   // setter for number of members
   public void setNumMembers(int numMembers) {
      this.numMembers = numMembers;
   }
   // setter for president email
   public void setPresEmail(String presEmail) {
      this.presEmail = presEmail;
   }
   // setter for president
   public void setPresident(String president) {
      this.president = president;
   }
   // setter for president phone
   public void setPresPhone(String presPhone) {
      this.presPhone = presPhone;
   }

   // method for checking if keyword is contained
   // within the array list keywords
   // returns true if keyword matches and false
   // if it doesn't
   public boolean hasKeyword(String keyword){
      for(int i = 0; i < keyWords.size(); i++)
      {
         if(keyWords.get(i).equals(keyword))
            return true;
      }
      return false;
   }
}
