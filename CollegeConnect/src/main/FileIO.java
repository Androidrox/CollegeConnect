package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.Scanner;


/*FileIO class
 * Handles the I/O for our CollegeConnect project
 * the personIdFile and clubIdFile hold id numbers for the different profiles and clubs
 * These ids will correspond to file names in the 
 * 
 * 
 */
/**
 * 
 * @author Matthew Betanski
 *
 */
public class FileIO {
	//File locations for the two ids lists
	/**
	 * Shortcut for the main file path for the program
	 */
	private static final String absolutePath = System.getenv("APPDATA") + "\\CollegeConnect\\resources";
	/**
	 * File that contains a list of all profiles
	 */
	private static File personIdFile = new File(System.getenv("APPDATA") + "\\CollegeConnect\\resources\\personIdFile.txt");
	/**
	 * File that contains a list of all clubs
	 */
	private static File clubIdFile = new File(absolutePath+"\\clubIdFile.txt");

	/**
	 * ArrayList of all created profiles
	 */
	private static ArrayList<Person> profileList = new ArrayList<Person>();
	/**
	 * ArrayList of all created clubs
	 */
	private static ArrayList<Club> clubList = new ArrayList<Club>();

	/**
	 * Populates the profileList with profiles specified by personIdFile
	 */
	public static void readPersons() {
		profileList = new ArrayList<Person>();
		try {
			Scanner personIdReader = new Scanner(personIdFile);
			createProfilesFolder();
			//Goes through each entry in the personIdFile and then accesses the file with the id name that it reads
			while(personIdReader.hasNextLine()) {
				String temp = personIdReader.nextLine();
				if(temp.equals(""))
					continue;
				File profileFile = new File(absolutePath +"//profiles//" + temp +"//" + temp +".txt");
				if(profileFile.exists()) {
					Scanner profileReader = new Scanner(profileFile);
					Person tempPerson = new Person();
					while(profileReader.hasNextLine()) {
						String currentToken = profileReader.nextLine();
						assignPersonValues(tempPerson,currentToken);

					}
					profileReader.close();
					profileList.add(tempPerson);

				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(0);
		}

	}

	/**
	 * Populates clubList with clubs specified by clubIdFile
	 */
	public static void readClubs() {
		try {
			Scanner clubIdReader = new Scanner(clubIdFile);

			//Goes through each entry in the clubIdFile and then accesses the file with the id name that it reads
			while(clubIdReader.hasNext()) {
				String temp = clubIdReader.next();
				Scanner clubReader = new Scanner(new File(absolutePath+"\\clubs\\" + temp +"\\" + temp +".txt"));
				clubReader.useDelimiter("//");
				Club tempClub = new Club();
				while(clubReader.hasNextLine()) {
					String currentToken = clubReader.nextLine();
					assignClubValues(tempClub,currentToken);
				}
				clubReader.close();
				clubList.add(tempClub);
			}
		}catch(FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(0);
		}
	}
	
	/**
	 * Writes the specified lines to the specified file, overrides the file
	 * @param file - File to write to
	 * @param lines - Lines to write to the file
	 */
	public static void write(File file, ArrayList<String> lines) {
		write(file,lines,false);
	}
	
	//Writes the lines to the indicated file
	//Append parameter indicates whether the lines should overwrite the file or be appended on
	/**
	 * 
	 * @param file - file to write to
	 * @param lines - lines to write to the file
	 * @param append - whether it should apppend or overwrite
	 */
	public static void write(File file, ArrayList<String> lines, boolean append) {
		try {
			FileWriter writer = new FileWriter(file,append);
			for(String s: lines) {
				if(!s.equals(""))
					writer.write("\n"+s);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param file - file to write to
	 * @param line - line to write to the file
	 * @param append - whether it should append or overwrite
	 */
	public static void write(File file, String line, boolean append) {
		try {
			FileWriter writer = new FileWriter(file,append);
			writer.write("\n"+line);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes to the specified profiles main file "profileName.txt"
	 * @param profile - Profile that contains file to write to
	 * @param lines - lines to write
	 * @param append - useless
	 */
	public static void write(Person profile, ArrayList<String> lines, boolean append) {
		File personFile = new File(absolutePath +"//profiles//"+ profile.getName().replace(" ", "_") + "//" + profile.getName().replace(" ", "_")+".txt");
		if(personFile.exists()) {
			try {
				FileWriter writer = new FileWriter(personFile,false);
				if(profile.getName() != null)
					writer.write("\nname:" + profile.getName());
				if(profile.getAge() > 0 && profile.getAge() < 120)
					writer.write("\nage:" + Integer.toString(profile.getAge()));
				if(profile.getGender() != null)
					writer.write("\ngender:" + profile.getGender().toString());
				if(profile.getHobbies() != null)
					writer.write("\nhobbies:" + profile.getHobbies());
				if(profile.getPetPeeves() != null)
					writer.write("\npetPeeves:" + profile.getPetPeeves());
				if(profile.getRoommatePreferences() != null)
					writer.write("\nroommatePreferences:" + profile.getRoommatePreferences());
				if(profile.getRoommateDislikes() != null)
					writer.write("\nroommateDislikes:" + profile.getRoommateDislikes());
				if(profile.getEmail() != null)
					writer.write("\nemail:" + profile.getEmail());
				if(profile.getUsername() != null)
					writer.write("\nusername:" + profile.getUsername());
				if(profile.getPassword() != null)
					writer.write("\npassword:" + profile.getPassword());
				if(profile.getMatchName() != null)
					writer.write("\ngetMatchName:" + profile.getMatchName());
				if(profile.getYearInSchool() != null)
					writer.write("\nyearInSchool:" + profile.getYearInSchool());
				try {
					writer.write("\ndormChoices:" + profile.getDormChoices().get(0).toString()+"/"+profile.getDormChoices().get(1).toString()+"/"+profile.getDormChoices().get(2).toString());
				}catch(NullPointerException e) {
				}
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @return Returns the list of profiles
	 */
	public static ArrayList<Person> getProfiles(){
		validatePersons();
		return profileList;
	}

	/**
	 * 
	 * @return Returns the list of Clubs
	 */
	public static ArrayList<Club> getClubs(){
		validateClubs();
		return clubList;
	}

	/**Assigns values to the specified club to populate it with information
	 * 
	 * @param club - Club to assign values to
	 * @param s - String containing the field and value to be assigned
	 */
	private static void assignClubValues(Club club, String s) {

		try {
			//name
			if(s.contains("name:")) {
				club.setName(s.substring(s.indexOf("name:")+"name:".length()));
			}
			if(s.contains("numMembers:")) {
				club.setNumMembers(Integer.valueOf(s.substring(s.indexOf("numMembers:")+"numMembers:".length())));
			}
			if(s.contains("location:")) {
				club.setLocation(s.substring(s.indexOf("location:")+"location:".length()));
			}
			if(s.contains("meetingTime:")) {
				club.setMeetingTime(s.substring(s.indexOf("meetingTime:")+"meetingTime:".length()));
			}
			if(s.contains("fees:")) {
				club.setFees(s.substring(s.indexOf("fees:")+"fees:".length()));
			}
			if(s.contains("description:")) {
				club.setDescription(s.substring(s.indexOf("description:")+"description:".length()));
			}
			if(s.contains("president:")) {
				club.setPresident(s.substring(s.indexOf("president:")+"president:".length()));
			}
			if(s.contains("presPhone:")) {
				club.setPresPhone(s.substring(s.indexOf("presPhone:")+"presPhone:".length()));
			}
			if(s.contains("presEmail:")) {
				club.setPresEmail(s.substring(s.indexOf("presEmail:")+"presEmail:".length()));
			}
			if(s.contains("keywords:")) {
				club.setKeyWords(new ArrayList<String>(Arrays.asList(s.substring(s.indexOf("keywords:")+"keywords:".length()).split("/"))));
			}
		}catch(Exception e) {
			club.setName(null);
		}

	}


	/**
	 * 
	 * @param person - The Person to assign values to
	 * @param s - The field and value to be assignd
	 */
	private static void assignPersonValues(Person person, String s) {

		//name
		if(s.contains("name:") && !s.contains("username:")){
			person.setName(s.substring(s.indexOf("name:")+"name:".length()));
		}

		//age
		if(s.contains("age:")){
			person.setAge(Integer.parseInt(s.substring(s.indexOf("age:")+"age:".length())));
		}

		//yearInSchool
		if(s.contains("yearInSchool:")){
			person.setYearInSchool(s.substring(s.indexOf("yearInSchool:")+"yearInSchool:".length()));
		}

		//dormChoices
		if(s.contains("dormChoices:")){
			String temp = s.substring(s.indexOf("dormChoices:")+"dormChoices:".length());
			String[] tempArr = temp.split("/");
			ArrayList<Dorms> dormList = new ArrayList<Dorms>();
			Boolean flag = true;
			for(String enumString : tempArr) {
				try {
					dormList.add(Enum.valueOf(Dorms.class, enumString));
				}catch(Exception e) {
					flag = !flag;
					person.setDormChoices(new ArrayList<Dorms>(3));
					break;
				}
			}
			if(flag) {
				person.setDormChoices(dormList);
			}
		}

		//major
		if(s.contains("major:")){
			person.setMajor(s.substring(s.indexOf("major:")+"major:".length()));
		}

		//username
		if(s.contains("username:")) {
			person.setUsername(s.substring(s.indexOf("username:")+"username:".length()));
		}

		//password
		if(s.contains("password:")) {
			person.setPassword(s.substring(s.indexOf("password:")+"password:".length()));
		}

		//email
		if(s.contains("email:")) {
			person.setEmail(s.substring(s.indexOf("email:")+"email:".length()));
		}

		//gender
		if(s.contains("gender:")){
			String temp = s.substring(s.indexOf("gender:")+"gender:".length());
			Boolean flag = true;
			if(EnumSet.allOf(Gender.class).contains(Enum.valueOf(Gender.class, temp)))
				person.setGender(Enum.valueOf(Gender.class, temp));
			else {
				person.setGender(null);

			}
		}

		//hobbies
		if(s.contains("hobbies:")){
			person.setHobbies(s.substring(s.indexOf("hobbies:")+"hobbies:".length()));
		}

		//petPeeves
		if(s.contains("petPeeves:")) {
			person.setPetPeeves(s.substring(s.indexOf("petPeeves:")+"petPeeves:".length()));
		}

		//roomatePreferences
		if(s.contains("roomatePref:")) {
			person.setRoommatePreferences(s.substring(s.indexOf("roomatePrefs:")+"roomatePrefs:".length()));
		}

		//rooamteDislikes
		if(s.contains("roomateDislikes:")) {
			person.setRoommateDislikes(s.substring(s.indexOf("roomateDislikes:")+"roomateDislikes:".length()));
		}
		if(s.contains("match:")) {
			person.setMatchName(s.substring(s.indexOf("match:")+"match:".length()));
		}
		if(person.getDormChoices() == null) {
			Dorms[] dorms = {null,null,null};
			person.setDormChoices(new ArrayList<Dorms>(Arrays.asList(dorms)));
			person.getDormChoices().ensureCapacity(3);
		}
	}

	/* Makes sure that certain essential elements are part of each persons profile
	 * These fields are: name , age , yearInSchool , username , password , email , gender
	 * These fields are important because they are either required for the app to function as intended
	 * In addition, profiles that are less complete will have lower scores
	 * These scores will be used to help match users
	 */
	/**
	 * Makes sure that certain essential elements of a person are present
	 * name,age,yearInSchol,username,password,email,gender
	 * If not all fields are present, then the profile will be removed from the list
	 */
	private static void validatePersons() {
		for(int i = 0; i < profileList.size(); i++) {
			if(profileList.get(i).getName() == null || profileList.get(i).getAge() == 0 || profileList.get(i).getYearInSchool() == null || profileList.get(i).getUsername() == null || profileList.get(i).getPassword() == null || profileList.get(i).getEmail() == null) {
				profileList.remove(i);
				i--;
			}
		}
	}

	/**
	 * Makes sure that certain essential elements of a club are present
	 * name,description,location,meetingTime
	 * If not all fields are present, then the club will be removed from the list
	 */
	private static void validateClubs() {
		for(int i = 0; i < clubList.size(); i++) {
			if(clubList.get(i).getName() == null || clubList.get(i).getDescription() == null || clubList.get(i).getLocation() == null || clubList.get(i).getMeetingTime() == null) {
				clubList.remove(i);
				i--;
			}
		}
	}
/**
 * Sends message from sender to recipient
 * @param sender - Person to send the message
 * @param recipient - Person to recieve the message
 * @param message - Message to be sent
 * @return - String about whether the message was sent sucesfully or not
 */
	public static String sendMessage(Person sender, Person recipient, String message) {
		if(message == null)
			return "";
		File senderMessageFile = new File(absolutePath+"//profiles//"+sender.getName().replace(" ", "_") + "//" + recipient.getName().replace(" ", "_")+".txt");
		File recipientMessageFile = new File(absolutePath+"//profiles//"+recipient.getName().replace(" ", "_")+ "//" + sender.getName().replace(" ", "_")+".txt");
		if(senderMessageFile.exists()) {
			write(senderMessageFile,"["+LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))+" " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE)+"] " + sender.getName() + ": " + message+"\n", true);
		}
		else {
			try {
				File messageFile = new File(absolutePath+"//profiles//" + sender.getName().replace(" ", "_")+"//messages.txt");
				messageFile.createNewFile();
				if(messageFile.exists()) {
					write(messageFile,recipient.getName()+"\n",true);
				}
				senderMessageFile.createNewFile();
				write(senderMessageFile,"["+LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))+" " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE)+"] " + sender.getName() + ": " + message+"\n", true);
			} catch (IOException e) {
				return "Error occured. Message not sent";
			}
		}
		if(recipientMessageFile.exists()) {
			write(recipientMessageFile,"["+LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))+" " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE)+"] " + sender.getName() + ": " + message+"\n", true);
			return "Message Sent!";
		}
		else {
			try {
				File messageFile = new File(absolutePath+"//profiles//" + recipient.getName().replace(" ", "_")+"//messages.txt");
				messageFile.createNewFile();
				if(messageFile.exists()) {
					write(messageFile,sender.getName()+"\n",true);
				}
				recipientMessageFile.createNewFile();
				write(recipientMessageFile,"["+LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))+" " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE)+"] " + sender.getName() + ": " + message+"\n", true);
				return "Message Sent!";
			} catch (IOException e) {
				return "Error occured. Message not sent";
			}
		}
	}

	//Prints out the list of messages that this user has with other users
	//Prints out the name of the users this user has messages with
	/**
	 * Prints out all the profile that Person p has messages with
	 * @param p - Person to view messages of
	 */
	public static void viewMessages(Person p) {
		File file = new File(absolutePath+"//profiles//" + p.getName()+"//messages.txt");
		if(file.exists()) {
			try {
				Scanner reader = new Scanner(file);
				while(reader.hasNextLine()) {
					System.out.println(reader.nextLine());
				}
				reader.close();
			} catch (FileNotFoundException e) {
				System.out.println("Could not find file");
			}
		}

	}
	/**
	 * Returns a String[] of all persons that p has messages with
	 * @param p - Person to view messages of
	 * @return - String[] containing all persons that p has messages with
	 */
	public static String[] viewMessageList(Person p) {
		File file = new File(absolutePath+"//profiles//" + p.getName().replace(" ","_")+"//messages.txt");
		ArrayList<String> messageList = new ArrayList<String>();
		if(file.exists()) {
			try {
				Scanner reader = new Scanner(file);
				while(reader.hasNextLine()) {
					String temp = reader.nextLine();
					if(!temp.equals(""))
						messageList.add(temp);
				}
				reader.close();
			} catch (FileNotFoundException e) {
				System.out.println("Could not find file");
			}
		}
		return messageList.toArray(new String[0]);

	}
	/**
	 * Recursively deletes all files from file down
	 * @param file - File/Folder to delete
	 */
	public static void deleteFile(File file) {
		if(file.exists()) {
			if(file.isDirectory()) {
				for(File f : file.listFiles()) {
					deleteFile(f);
				}
			}
			file.delete();
		}
	}
	/**
	 * Deletes the specified profile
	 * @param profile - profile to be deleted
	 */
	public static void deleteProfile(Person profile) {
		String replacedName = profile.getName().replace(' ', '_');
		deleteFile(new File(absolutePath+"//profiles//"+replacedName));
		try {
			Scanner reader = new Scanner(personIdFile);
			ArrayList<String> lines = new ArrayList<String>();
			while(reader.hasNextLine()) {
				String temp = reader.nextLine();
				if(!temp.equals(replacedName)) {
					lines.add(temp);
				}
			}
			for(int i = profileList.size()-1; i >= 0; i--) {
				if(profileList.get(i).getName().equals(profile.getName())) {
					profileList.remove(i);
				}
			}
			if(!new File(absolutePath+"//profiles//"+replacedName).exists())
				write(personIdFile, lines,false);
		} catch (FileNotFoundException e) {
		}

	}
	/**
	 * Prints out messages between accessor and other
	 * @param accessor - Person that is currently logged in
	 * @param other - Person to view messages with
	 */
	public static void displayMessages(Person accessor, Person other) {
		File messageFile = new File(absolutePath+"//profiles//"+accessor.getName().replace(" ", "_")+"//"+other.getName().replace(" ", "_")+".txt");
		if(messageFile.exists()) {
			try {
				Scanner messageReader = new Scanner(messageFile);
				while(messageReader.hasNextLine()) {
					String temp = messageReader.nextLine();
					if(!temp.equals(""))
						System.out.println(temp);
				}
				messageReader.close();
			} catch (FileNotFoundException e) {
				System.out.println("Could not find file.");
			}
		}
	}
	/**
	 * Returns a list of all messages between accessor and other
	 * @param accessor - Currently logged in user
	 * @param other - User to view messages with
	 * @return - Returns messages
	 */
	public static String getMessage(Person accessor, Person other) {
		File messageFile = new File(absolutePath+"//profiles//"+accessor.getName().replace(" ", "_")+"//"+other.getName().replace(" ", "_")+".txt");
		String lines = "";
		if(messageFile.exists()) {
			try {
				Scanner messageReader = new Scanner(messageFile);
				while(messageReader.hasNextLine()) {
					String temp = messageReader.nextLine();
					if(!temp.equals(""))
						lines += temp +"\n";
				}
				messageReader.close();
				return lines;
			} catch (FileNotFoundException e) {
				System.out.println("Could not find file.");
			}
		}
		return lines;
	}

	//Creates a profile for a new user
	//Profile is created under the path "resources//profiles//name" folder, where name is the name of the new user
	/**
	 * Creates a new profile
	 * @param username - Username for profile
	 * @param password - Password for profile
	 * @param name - Name of user
	 * @param yearInSchool - The year in school that the user is
	 * @param gender - Gender of the user
	 * @param age - Age of user
	 * @param email - email of user
	 * @return - returns status of whether creation was successful or not
	 */
	public static boolean createProfile(String username, String password, String name, String yearInSchool, String gender, int age, String email) {
		String replacedName = name.replace(' ', '_');
		createProfilesFolder();
		File profileFolder = new File(absolutePath+"//profiles//" + replacedName);
		if(!profileFolder.exists())
			profileFolder.mkdir();
		File profileFile = new File(absolutePath+"//profiles//"+replacedName+"//"+replacedName+".txt");
		if(!profileFile.exists()) {
			try {
				profileFile.createNewFile();
				String[] lines = {"name:"+name+"\n","username:"+username+"\n","password:"+password+"\n","yearInSchool:"+yearInSchool+"\n","gender:"+gender+"\n","age:"+age+"\n","email:"+email+"\n"};
				write(profileFile,new ArrayList<String>(Arrays.asList(lines)));
				write(personIdFile,replacedName+"\n",true);
				return true;
			} catch (IOException e) {
				System.out.println("Could not create file");
				return false;
			}
		}
		else
			return false;
	}
	/**
	 * Sets matchName in Person user to name of Person match
	 * @param user - Currently logged in user
	 * @param match - Person that user matches with
	 */
	public static void createMatch(Person user, Person match) {

		File profileFile = new File(absolutePath+"//profiles//"+user.getName().replace(" ", "_")+"//"+user.getName().replace(" ", "_")+".txt");
		if(profileFile.exists())
			write(profileFile,"match:"+match.getName(),true);
		else
			System.out.println("Could not find file");
	}
	/**
	 * Creates the profiles folder
	 */
	private static void createProfilesFolder() {
		File profilesFolder = new File(absolutePath+"//profiles");
		if(!profilesFolder.exists())
			profilesFolder.mkdir();
	}

	//Creates necessary files and folders for the start of the program if they do not already exist
	/**
	 * Creates all necessary files and folders at runtime if not already present
	 * All files and folders are stored in the currently logged in users computer under USER/AppData/Roaming/CollegeConnect
	 */
	public static void startUp() {
		try {
			//Creates folder under "AppData/Roaming/CollegeConnect
			if(!(new File(System.getenv("APPDATA")+"//CollegeConnect").exists()))
				new File(System.getenv("APPDATA")+"//CollegeConnect").mkdir();
			if(!(new File(absolutePath).exists()))
				new File(absolutePath).mkdir();
			if(!personIdFile.exists())
				personIdFile.createNewFile();
			if(!clubIdFile.exists())
				clubIdFile.createNewFile();
			if(!(new File(absolutePath+"//profiles").exists()))
				new File(absolutePath+"//profiles").mkdir();
			if(!(new File(absolutePath+"//clubs").exists()))
				new File(absolutePath+"//clubs").mkdir();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}



}
