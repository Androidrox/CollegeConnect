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
 */
public class FileIO {
	//File locations for the two ids lists
	private static File personIdFile = new File("resources//personIdFile.txt");
	private static File clubIdFile = new File("clubIdFile.txt");

	//ArrayLists to store the profiles and clubs
	private static ArrayList<Person> profileList = new ArrayList<Person>();
	private static ArrayList<Club> clubList = new ArrayList<Club>();

	//Reads from the personIdFile and will populate the profileList
	public static void readPersons() {

		try {
			Scanner personIdReader = new Scanner(personIdFile);

			//Goes through each entry in the personIdFile and then accesses the file with the id name that it reads
			while(personIdReader.hasNextLine()) {
				String temp = personIdReader.nextLine();
				if(temp.equals(""))
					continue;
				Scanner profileReader = new Scanner(new File("resources//profiles//" + temp +"//" + temp +".txt"));
				Person tempPerson = new Person();
				while(profileReader.hasNextLine()) {
					String currentToken = profileReader.nextLine();
					assignPersonValues(tempPerson,currentToken);

				}
				profileReader.close();
				profileList.add(tempPerson);

			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(0);
		}

	}

	//Reads from the clubIdFile and will populate the clubList
	public static void readClubs() {
		try {
			Scanner clubIdReader = new Scanner(clubIdFile);

			//Goes through each entry in the clubIdFile and then accesses the file with the id name that it reads
			while(clubIdReader.hasNext()) {
				Scanner clubReader = new Scanner(new File("resources//clubs//" + clubIdReader.next()));
				clubReader.useDelimiter("//");
				Club tempClub = new Club();
				while(clubReader.hasNextLine()) {
					String currentToken = clubReader.nextLine();
					assignClubValues(tempClub,currentToken);
				}
				clubList.add(tempClub);
			}
		}catch(FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(0);
		}
	}
	public static void write(File file, ArrayList<String> lines) {
		write(file,lines,false);
	}

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
	public static void write(File file, String line, boolean append) {
		try {
			FileWriter writer = new FileWriter(file,append);
			writer.write("\n"+line);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Returns profileList
	public static ArrayList<Person> getProfiles(){
		validatePersons();
		return profileList;
	}

	//Returns clubList
	public static ArrayList<Club> getClubs(){
		validateClubs();
		return clubList;
	}

	//This method assigns values to the current club
	private static void assignClubValues(Club club, String s) {


		//name
		if(s.contains("name:")) {
			club.setName(s.substring(s.indexOf("name:")+"name:".length()));
		}
		if(s.contains("numMembers:")) {
			club.setNumMembers(s.substring(s.indexOf("numMembers:")+"numMembers:".length()));
		}
		if(s.contains("location:")) {
			s.setLocation(s.substring(s.indexOf("location:")+"location:".length()));
		}
		if(s.contains("meetingTime:")) {
			s.setMeetingTime(s.substring(s.indexOf("meetingTime:")+"meetingTime".length()));
		}
		if(s.contains("fees:")) {
			s.setFees(s.substring(s.indexOf("fees:")+"fees:".length()));
		}
		if(s.contains("description:")) {
			s.setDescription(s.substring(s.indexOf("description:")+"description:".length()));
		}
		if(s.contains("president:")) {
			s.setPresident(s.substring(s.indexOf("president:")+"president:".length()));
		}
		if(s.contains("presPhone:")) {
			s.setPresPhone(s.substring(s.indexOf("presPhone:")+"presPhone:".length()));
		}
		if(s.contains("presEmail:")) {
			s.setPresEmail(s.substring(s.indexOf("presEmail:")+"presEmail:".length()));
		}
		if(s.contains("keywords:")) {
			s.setKeywords(new ArrayList<String>(Arrays.asList(s.substring(s.indexOf("keywords:")+"keywords:".length()).split("/"))));
		}

	}


	//This method assigns values to the current person from each profile
	//Its just used to not clutter up the important parts too much
	private static void assignPersonValues(Person person, String s) {

		//name
		if(s.contains("name:") && !s.contains("username:")){
			person.setName(s.substring(s.indexOf("name:")+"name:".length()));
		}

		//age
		if(s.contains("age:")){
			person.setAge(Integer.parseInt(s.substring(s.indexOf("age:")+"age:".length())));
		}
		else
			person.setAge(0);

		//yearInSchool
		if(s.contains("yearInSchool:")){
			person.setYearInSchool(s.substring(s.indexOf("yearInSchool:")+"yearInSchool:".length()));
		}
		else
			person.setYearInSchool(null);

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
					person.setDormChoices(null);
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
		else
			person.setMajor(null);

		//username
		if(s.contains("username:")) {
			person.setUsername(s.substring(s.indexOf("username:")+"username:".length()));
		}
		else
			person.setUsername(null);

		//password
		if(s.contains("password:")) {
			person.setPassword(s.substring(s.indexOf("password:")+"password:".length()));
		}
		else
			person.setPassword(null);

		//email
		if(s.contains("email:")) {
			person.setEmail(s.substring(s.indexOf("email:")+"email:".length()));
		}
		else
			person.setEmail(null);

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
		else
			person.setHobbies(null);

		//petPeeves
		if(s.contains("petPeeves:")) {
			person.setPetPeeves(s.substring(s.indexOf("petPeeves:")+"petPeeves:".length()));
		}
		else
			person.setPetPeeves(null);

		//roomatePreferences
		if(s.contains("roomatePref:")) {
			person.setRoommatePreferences(s.substring(s.indexOf("roomatePrefs:")+"roomatePrefs:".length()));
		}
		else
			person.setRoommatePreferences(null);

		//rooamteDislikes
		if(s.contains("roomateDislikes:")) {
			person.setRoommateDislikes(s.substring(s.indexOf("roomateDislikes:")+"roomateDislikes:".length()));
		}
		else
			person.setRoommateDislikes(null);
		if(s.contains("match:")) {
			person.setMatchName(s.substring(s.indexOf("match:")+"match:".length()));
		}
		else
			person.setMatchName(null);
	}

	/* Makes sure that certain essential elements are part of each persons profile
	 * These fields are: name , age , yearInSchool , username , password , email , gender
	 * These fields are important because they are either required for the app to function as intended
	 * In addition, profiles that are less complete will have lower scores
	 * These scores will be used to help match users
	 */

	private static void validatePersons() {
		for(int i = 0; i < profileList.size(); i++) {
			if(profileList.get(i).getName() == null || profileList.get(i).getAge() == 0 || profileList.get(i).getYearInSchool() == null || profileList.get(i).getUsername() == null || profileList.get(i).getPassword() == null || profileList.get(i).getEmail() == null) {
				profileList.remove(i);
				i--;
			}
		}
	}

	private static void validateClubs() {
		for(int i = 0; i < clubList.size(); i++) {
			if(clubList.get(i).getName() == null || clubList.get(i).getDescription() == null || clubList.get(i).getLocation() == null || clubList.get(i).getMeetingTime() == null) {
				clubList.remove(i);
				i--;
			}
		}
	}

	public static String sendMessage(Person sender, Person recipient, String message) {
		File senderMessageFile = new File("resources//profiles//"+sender.getName()+ "//" + recipient.getName()+".txt");
		File recipientMessageFile = new File("resources//profiles//"+recipient.getName()+ "//" + sender.getName()+".txt");
		if(senderMessageFile.exists()) {
			write(senderMessageFile,"["+LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))+" " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE)+"] " + sender.getName() + ": " + message+"\n", true);
		}
		else {
			try {
				File messageFile = new File("resources//profiles//" + sender.getName()+"//messages.txt");
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
				File messageFile = new File("resources//profiles//" + recipient.getName()+"//messages.txt");
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
	public static void viewMessages(Person p) {
		File file = new File("resources//profiles//" + p.getName()+"//messages.txt");
		if(file.exists()) {
			try {
				Scanner reader = new Scanner(file);
				while(reader.hasNextLine()) {
					System.out.println(reader.nextLine());
				}
			} catch (FileNotFoundException e) {
				System.out.println("Could not find file");
			}
		}

	}
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
	public static void deleteProfile(Person profile) {
		deleteFile(new File("resources//profiles//"+profile.getName()));
		try {
			Scanner reader = new Scanner(personIdFile);
			ArrayList<String> lines = new ArrayList<String>();
			while(reader.hasNextLine()) {
				String temp = reader.nextLine();
				if(!temp.equals(profile.getName())) {
					lines.add(temp);
				}
			}
			for(int i = profileList.size()-1; i >= 0; i--) {
				if(profileList.get(i).getName().equals(profile.getName())) {
					profileList.remove(i);
				}
			}
			if(!new File("resources//profiles//"+profile.getName()).exists())
				write(personIdFile, lines,false);
		} catch (FileNotFoundException e) {
		}

	}

	public static void getMessage(Person accessor, Person other) {
		File messageFile = new File("resources//profiles//"+accessor.getName()+"//"+other.getName()+".txt");
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

	//Creates a profile for a new user
	//Profile is created under the path "resources//profiles//name" folder, where name is the name of the new user
	public static boolean createProfile(String username, String password, String name, String yearInSchool, String gender, int age, String email) {
		File profileFolder = new File("resources//profiles//" + name);
		if(!profileFolder.exists())
			profileFolder.mkdir();
		File profileFile = new File("resources//profiles//"+name+"//"+name+".txt");
		if(!profileFile.exists()) {
			try {
				profileFile.createNewFile();
				String[] lines = {"name:"+name+"\n","username:"+username+"\n","password:"+password+"\n","yearInSchool:"+yearInSchool+"\n","age:"+age+"\n","email:"+email+"\n"};
				write(profileFile,new ArrayList<String>(Arrays.asList(lines)));
				write(personIdFile,name+"\n",true);
				return true;
			} catch (IOException e) {
				System.out.println("Could not create file");
				return false;
			}
		}
		else
			return false;
	}

	public static void createMatch(Person user, Person match) {
		File profileFile = new File("resources//profiles//"+user.getName()+"//"+user.getName()+".txt");
		if(profileFile.exists())
			write(profileFile,"match:"+user.getName(),true);
		else
			System.out.println("Could not find file");
	}
}
