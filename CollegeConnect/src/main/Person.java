package main;

import java.util.ArrayList;

public class Person {
    int ID, age;
    String name, yearInSchool, major, password, username, email;
    String hobbies, petPeeves, roommatePreferences, roommateDislikes;
    ArrayList<Dorms> dormChoices;
    Gender gender;

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
    public void setYearInSchool(){
    }

    public ArrayList<Dorms> getDormChoices(){
        return dormChoices;
    }
    public void setDormChoices(ArrayList<Dorms> dormChoices){
    	this.dormChoices = dormChoices;
    }

    public String getMajor(){
        return major;
    }
    public void setMajor(){
        this.major = major;
    }

    public Gender getGender(){
        return gender;
    }
    public void setGender(){
        this.gender = gender;
    }

    public String getHobbies(){
        return hobbies;
    }
    public void setHobbies(){
        this.hobbies = hobbies;
    }

    public String getPetPeeves(){
        return petPeeves;
    }
    public void setPetPeeves(){
        this.petPeeves;
    }

    public String getRoommatePreferences(){
        return roommatePreferences;
    }
    public void setRoommatePreferences(){
        this.roommatePreferences = roommatePreferences;
    }

    public String getRoommateDislikes(){
        return roommateDislikes;
    }
    public void setRoommateDislikes(){
        this.roommatesDislikes = roommatePreferences;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(){
        this.email = email;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(){
        this.password = password;
    }
}



