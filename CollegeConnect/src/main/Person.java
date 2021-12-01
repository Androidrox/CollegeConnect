package main;

import java.util.ArrayList;
//This class alows us to create a user and allow them to repond to question to add to there profile

public class Person {
    int ID, age;
    String name, yearInSchool, major, password, username, email, matchName;
    String hobbies, petPeeves, roommatePreferences, roommateDislikes;
    ArrayList<Dorms> dormChoices;
    Gender gender;
//Allows us to get basic information
    public String getName() {
        return name;
    }
    public void setName(String name){
    	this.name = name;
    }

    public int getAge(){
        return age;
    }
    public void setAge(int age){
    	this.age = age;
    }

    public String getYearInSchool(){
        return yearInSchool;
    }
    public void setYearInSchool(String yearInSchool){
        this.yearInSchool = yearInSchool;
    }

    public ArrayList<Dorms> getDormChoices(){
        return dormChoices;
    }
    public void setDormChoices(ArrayList<Dorms> dormChoices){
    	this.dormChoices = dormChoices;
    }
// This also allows them to enter more peronsal information to help match students
    public String getMajor(){
        return major;
    }
    public void setMajor(String major){
        this.major = major;
    }

    public Gender getGender(){
        return gender;
    }
    public void setGender(Gender gender){
        this.gender = gender;
    }

    public String getHobbies(){
        return hobbies;
    }
    public void setHobbies(String hobbies){
        this.hobbies = hobbies;
    }

    public String getPetPeeves(){
        return petPeeves;
    }
    public void setPetPeeves(String petPeeves){
        this.petPeeves = petPeeves;
    }

    public String getRoommatePreferences(){
        return roommatePreferences;
    }
    public void setRoommatePreferences(String roommatePreferences){
        this.roommatePreferences = roommatePreferences;
    }

    public String getRoommateDislikes(){
        return roommateDislikes;
    }
    public void setRoommateDislikes(String roomateDislikes){
        this.roommateDislikes = roomateDislikes;
    }
//Thsi create there profile so they can log in and out of the program
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getMatchName() {
    	return matchName;
    }
    public void setMatchName(String matchName) {
    	this.matchName = matchName;
    }
}



