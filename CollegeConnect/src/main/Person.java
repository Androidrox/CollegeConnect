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
    }

    public Gender getGender(){
        return gender;
    }
    public void setGender(){
    }

    public String getHobbies(){
        return hobbies;
    }
    public void setHobbies(){
    }

    public String getPetPeeves(){
        return petPeeves;
    }
    public void setPetPeeves(){
    }

    public String getRoommatePreferences(){
        return roommatePreferences;
    }
    public void setRoommatePreferences(){
    }

    public String getRoommateDislikes(){
        return roommateDislikes;
    }
    public void setRoommateDislikes(){
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(){
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(){
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(){
    }
}



