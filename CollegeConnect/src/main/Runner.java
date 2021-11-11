package main;
import java.util.ArrayList;
import java.util.Scanner;

public class Runner {

	private static int loggedIn = -1;
	public static void main(String[] args) {


		Scanner input = new Scanner(System.in);
		//the loggedIn integer is set to -1 as arrays have an index at 0.  

		//asks user is they have an existing file.  if not, they can make one and then sign in.  If they do, then it skips to sign in.
		System.out.println("Do you have an account? (y/n)");
		String hasAccount = input.nextLine();
		if(hasAccount.equalsIgnoreCase("n")) {
			createProfile();
			FileIO.readPersons();
		}
		FileIO.readPersons();
		FileIO.readClubs();
		//This while loop runs the logIn() method and stops looping if the returned index is greater than -1.
		while(loggedIn < 0) {
			loggedIn = logIn();
			if(loggedIn < 0)
				System.out.println("Incorrect Username or Password!\n");
		}

		int choice = 0;
		//this while loop will continue to loop through the main method until you input a valid variable.  Entering 5 terminates the program.
		while(choice != 5) {
			mainMenu();
			choice = input.nextInt();
			if(choice > 0 && choice < 5) {
				switch(choice) {
				case 1:
					Person match = matchPeoples(FileIO.getProfiles().get(loggedIn));
					if(match != null) {
						FileIO.createMatch(FileIO.getProfiles().get(loggedIn),match);
						FileIO.sendMessage(FileIO.getProfiles().get(loggedIn), match, privateMessage(match));
					}
					break;
				case 2:
					editInformation(FileIO.getProfiles().get(loggedIn));
					break;
				case 3:
					findClub();
					break;
				case 4:
					deleteProfile(FileIO.getProfiles().get(loggedIn));
					break;
				case 5:
					privateMessage();
				}
			}
			else if(choice == 5) {
			}
			else
			{
				System.out.println("Incorrect Input.  Try again!");
			}
		}



	}
	/*
	public static Person matchPeople(Person profile){
		Scanner scan = new Scanner(System.in);
		int input = -1;
		String choice = "";

		ArrayList<String> tempArray = new ArrayList<String>();
		while(input != 0) {
			System.out.println("Here are all eligible profiles:  ");
			for(int i = 0; i < FileIO.getProfiles().size(); i++) {
				if(FileIO.getProfiles().get(i).getGender().equals(profile.getGender()))
					tempArray.add(FileIO.getProfiles().get(i).getName());
			}
			for(int i = 0; i < tempArray.size(); i++) {
				System.out.println(i + 1 + ".  " + tempArray.get(i));
			}
			System.out.println("Enter the number you'd wish to look at.  Enter 0 to exit.");
			input = scan.nextInt();
			scan.nextLine();
			//this if statement determines that the value the user inputed fits the indexs of the arraylist
			if(input > 0 && input < tempArray.size()+1) {
				System.out.println("Name:  " + tempArray.get(input-1).getName());
				System.out.println("Age:  " + tempArray.get(input-1).getAge());
				System.out.println("Gender:  " + tempArray.get(input-1).getGender());
				System.out.println("Major:  " + tempArray.get(input-1).getMajor());
				System.out.println("Email:  " + tempArray.get(input-1).getEmail());
				System.out.println("Hobbies:  " + tempArray.get(input-1).getHobbies());
				System.out.println("Peeves:  " + tempArray.get(input-1).getPetPeeves());
				System.out.println("Roommate Preferences:  " + tempArray.get(input-1).getRoomatePreferences());
				System.out.println("Roomate Dislikes:  " + tempArray.get(input-1).getRoomateDislikes());
				System.out.println("Would you like to match with this person?  (y/n)");
				choice = scan.nextLine();
				if(choice.equals("y")) {
					break;
				}
			}
			else
			{
				System.out.println("Not a valid input.  Try again.");
			}

		}
		if(input == 0)
			return null;
		else
		{
			for(int i = 0; i < FileIO.getProfiles().size(); i++) {
				if(tempArray.get(input-1).getName().equals(FileIO.getProfiles().get(i).getName()))
					return FileIO.getProfiles().get(i);
			}
		}
	}*/

	public static Person matchPeoples(Person profile) {
		Scanner scanner = new Scanner(System.in);
		int input = -1;
		int i = 0;
		String choice = "";
		System.out.println("Instructions:\nEnter -1 to exit.\nEnter > to move to the right, and < to move to the left");
		while(true) {
			if((!profile.getName().equals(FileIO.getProfiles().get(i).getName()) && profile.getGender() != FileIO.getProfiles().get(i).getGender())) {
				System.out.println("--------------------------------------------------------");
				System.out.println("Name:  " + FileIO.getProfiles().get(i).getName());
				System.out.println("Age:  " + FileIO.getProfiles().get(i).getAge());
				System.out.println("Gender:  " + FileIO.getProfiles().get(i).getGender());
				System.out.println("Major:  " + FileIO.getProfiles().get(i).getMajor());
				System.out.println("Email:  " + FileIO.getProfiles().get(i).getEmail());
				System.out.println("Hobbies:  " + FileIO.getProfiles().get(i).getHobbies());
				System.out.println("Peeves:  " + FileIO.getProfiles().get(i).getPetPeeves());
				System.out.println("Roommate Preferences:  " + FileIO.getProfiles().get(i).getRoommatePreferences());
				System.out.println("Roomate Dislikes:  " + FileIO.getProfiles().get(i).getRoommateDislikes());
				System.out.println("Would you like to match with this person? (y) (<) (>) (-1)");
				choice = scanner.nextLine();
				if(choice.equalsIgnoreCase("y"))
					break;
				if(choice.equals("-1"))
					break;
				while(true) {
					if(choice.equals(">") && i >= FileIO.getProfiles().size()-1) {
						System.out.println("Already at last user.");
					}
					else if(choice.equals("<") && i <= 0) {
						System.out.println("Already at first user");
					}
					else if((choice.equals("<") && i >= 2) && FileIO.getProfiles().get(i-1).getName().equals(profile.getName())) {
						i-=2;
						break;
					}
					else if((choice.equals(">") && i <= FileIO.getProfiles().size()-3) && FileIO.getProfiles().get(i+1).getName().equals(profile.getName())) {
						i+=2;
						break;
					}
					else if((choice.equals("<") && i == 1) && FileIO.getProfiles().get(i-1).getName().equals(profile.getName())) {
						System.out.println("Already at first user");
					}
					else if((choice.equals("<") && i == FileIO.getProfiles().size()-2) && FileIO.getProfiles().get(i+1).getName().equals(profile.getName())) {
						System.out.println("Already at first user");
					}
					else if(choice.equals(">")) {
						i++;
						break;
					}
					else if(choice.equals("<")) {
						i--;
						break;
					}
					else
						break;
					System.out.println("Would you like to match with this person? (y) (<) (>) (-1)");
					choice = scanner.nextLine();
				}
			}
			else
				i++;

		}
		if(!choice.equals("-1")) {
			return FileIO.getProfiles().get(i);
		}
		return null;
	}




	/*This method takes the ArrayList of person objects from the FileIO class and asks for the User to enter a username or password
	Then method then scans through each person object to see if a username and password match the inputed Strings
	If they do, the method returns the index of the profile logged in as the integer "loggedIn"
	 */
	public static int logIn() {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter your Username:  ");
		String username = input.nextLine();
		System.out.print("Enter your Password:  ");
		String password = input.nextLine();
		int index = -1;
		for(int i = 0; i < FileIO.getProfiles().size(); i++) {
			if(username.equals(FileIO.getProfiles().get(i).getUsername()) && (password.equals(FileIO.getProfiles().get(i).getPassword()))){

				index = i;
			}
		}

		return index;
	}
	//prints off options for user to interact with.
	public static void mainMenu() {

		System.out.println("\nWelcome!");
		System.out.println("1.  Find Roommate");
		System.out.println("2.  Edit Information");
		System.out.println("3.  Find a Club");
		System.out.println("4.  Delete Profile");
		System.out.println("5.  Exit");
		System.out.println("Please enter the corresponding number with what you'd like to do:  ");


	}

	//this method allows the user to edit profile information
	public static void editInformation(Person profile) {
		Scanner input = new Scanner(System.in);
		int choice = input.nextInt();
		System.out.println("What would you like to change?");
		System.out.println("1.  Name");
		System.out.println("2.  Age");
		System.out.println("3.  Gender");
		System.out.println("4.  Year in School");
		System.out.println("5.  Major");
		System.out.println("6.  Hobbies ");
		System.out.println("7.  Pet Peeves");
		System.out.println("8.  Roommate Preferences");
		System.out.println("9.  Roommate Dislikes");
		System.out.println("10.  Dorm Choices");

		//this switch takes the users input and runs a block of code to list what a current value is, and then gives you the option to change it
		switch(choice) {
		case 1:  
			System.out.println("Current name is:  " + profile.getName());
			System.out.print("Change name to:  ");
			String name = input.nextLine();
			profile.setName(name);
			break;
		case 2:
			System.out.println("Current age is:  " + profile.getAge());
			System.out.print("Change age to:  ");
			int age = input.nextInt();
			profile.setAge(age);
			break;
		case 3:
			System.out.println("Current gender is:  " + profile.getGender());
			System.out.print("Change gender to:  ");
			String gender = input.nextLine();
			Gender genderEnum = null;
			try {
				genderEnum = Gender.valueOf(gender.toLowerCase());
			}catch(Exception e) {
				System.out.println("Not a valid option for gender, Use male, female, or other.");
			}
			if(genderEnum != null)
				profile.setGender(genderEnum);
			else
				System.out.println("Not a valid option for gender, Use male, female, or other.");
			break;
		case 4:
			System.out.println("Current Year is :  " + profile.getYearInSchool());
			System.out.print("Change current year to:  ");
			String year = input.nextLine();
			profile.setYearInSchool(year);
			break;
		case 5:
			System.out.println("Current Major:  " + profile.getMajor());
			System.out.println("Change Major to:  ");
			String major = input.nextLine();
			profile.setMajor(major);
			break;
		case 6:
			System.out.println("Current Hobbies are:  " + profile.getHobbies());
			System.out.print("Change Hobbies to:  ");
			String hobbies = input.nextLine();
			profile.setHobbies(hobbies);
			break;
		case 7:
			System.out.println("Current Peeves are:  " + profile.getPetPeeves());
			System.out.print("Change Peeves to:  ");
			String peeves = input.nextLine();
			profile.setPetPeeves(peeves);
			break;
		case 8:
			System.out.println("Current Preferences are:  " + profile.getRoommatePreferences());
			System.out.print("Change Preferences to:  ");
			String preferences = input.nextLine();
			profile.setRoommatePreferences(preferences);
			break;
		case 9:
			System.out.println("Current Dislikes are:  " + profile.getRoommateDislikes());
			System.out.print("Change Dislikes to:  ");
			String dislikes = input.nextLine();
			profile.setRoommateDislikes(dislikes);
			break;
		case 10:
			System.out.print("Current Dorm choices are:  ");
			//should this arraylist be casted as a <Dorm> arraylist?  doesn't work if I do that.
			ArrayList<Dorms> dorms = profile.getDormChoices();
			for(int i = 0; i < dorms.size(); i++) {
				if(i == dorms.size()-1)
					System.out.println(dorms.get(i));
				else
					System.out.println(dorms.get(i) + ", ");

			}
			System.out.print("(Enter one dorm name at a time)/nChange dorms to:  ");
			String dorm = input.nextLine();
			//temporary solution for now, still don't know what to do for arrayList casting here
			//I need to input the user inputed string into the arrayList at index i, however since its an enum without an assigned string I literally can't
			for(int i = 0; i < 3; i ++) {
				while(true) {
					try {
						dorms.set(i,Enum.valueOf(Dorms.class, dorm));
					} catch(Exception e) {
						System.out.print("Error.  Not a valid option.");
						continue;
					}
					break;
				}
				dorm = input.nextLine();
			}

		}

	}

	//this method will allow the user to remove their profile, then terminate program
	public static void deleteProfile(Person profile) {
		FileIO.deleteProfile(profile);
		System.out.println("Account Deleted!");
		System.exit(0);
	}

	//for now this just checks for one keyword, can change it to check for more later
	public static void findClub() {
		//makes a temporary club ArrayList and then adds any clubs with the keyword the user enters into that arrayList
		ArrayList<Club> tempClubs = new ArrayList<Club>();
		Scanner input = new Scanner(System.in);
		System.out.println("Enter a Keyword in the club you're looking for!");
		String keyWord = input.nextLine();
		for(int i = 0; i < FileIO.getClubs().size(); i++) {
			if(FileIO.getClubs().get(i).hasKeyword(keyWord))
				tempClubs.add(FileIO.getClubs().get(i));
		}
		//prints out all clubs with the keyword the user entered, and then allows the user to pick which club to look at in detail
		System.out.println("Here are all the clubs with your keyword!  Enter the value of the club you'd like to look into.");
		for(int i = 0; i < tempClubs.size(); i++) {
			System.out.println((i+1) + ".  " + tempClubs.get(i).getName());
		}
		int choice = input.nextInt()-1;
		if(choice < tempClubs.size() && choice > 0) {
			System.out.println("Club name:  " + tempClubs.get(choice).getName() + "\n");
			System.out.println("Club Description:  " + tempClubs.get(choice).getDescription() + "\n");
			System.out.println("Number of members:  " + tempClubs.get(choice).getNumMembers() + "\n");
			System.out.println("Fees:  $" + tempClubs.get(choice).getFees() + "\n");
			System.out.println("Location:  " + tempClubs.get(choice).getLocation() + "\n");
			System.out.println("Meeting Time:  " + tempClubs.get(choice).getMeetingTime() + "\n");
			System.out.println("Club President:  " + tempClubs.get(choice).getPresident() + "\n");
			System.out.println("Presidents Email:  " + tempClubs.get(choice).getPresEmail() + "\n");
			System.out.println("Presidents Phone Number:  " + tempClubs.get(choice).getPresPhone() + "\n");		
		}
		else
		{
			System.out.println("Invalid answer.");
		}


	}

	//takes in all the parameters for the createProfile method in the FileIO class. 
	//Returns a statement saying if account was created or not.
	//if profile is not created, user is notified and program terminates.
	public static void createProfile() {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter Username:  ");
		String username = input.nextLine();
		System.out.print("Enter Password:  ");
		String password = input.nextLine();
		System.out.print("Enter Name:  ");
		String name = input.nextLine();
		System.out.print("Enter Year in School:  ");
		String yearInSchool = input.nextLine();
		System.out.print("Enter Gender:  ");
		String gender = input.nextLine();
		System.out.print("Enter age:  ");
		int age = input.nextInt();
		input.nextLine();
		System.out.print("Enter email:  ");
		String email = input.nextLine();
		if(FileIO.createProfile(username, password, name, yearInSchool, gender, age, email)) 
			System.out.println("Profile Created!");
		else {
			System.out.println("Profile already exists or there was an error creating the profile.");
			System.exit(0);
		}
	}

	public static String privateMessage() {
		Scanner scan = new Scanner(System.in);
		String input;
		int choice;
		String message;
		//loop through profile list and find person to send message to. return matts method with parameters user profile, their profile, and the message.

		System.out.println("Who do you want to send a message to? (enter their associated number)");
		for(int i = 0; i < FileIO.getProfiles().size(); i++) {
			System.out.println(i+1 + ".  " + FileIO.getProfiles().get(i).getName());
		}
		choice = scan.nextInt()-1;
		scan.nextLine();



		System.out.println("Do you want to send " + FileIO.getProfiles().get(choice).getName() + " a message?  (y/n)");
		input = scan.nextLine();
		if(input.equals("y")){
			System.out.print("Type the message to send:  ");
			message = scan.nextLine();
			return FileIO.sendMessage(FileIO.getProfiles().get(loggedIn), FileIO.getProfiles().get(choice), message);
		}

		return null;
	}

	public static String privateMessage(Person match) {
		Scanner scan = new Scanner(System.in);
		String input;
		String message;
		System.out.println("Do you want to send a user a message?  (y/n)");
		input = scan.nextLine();
		if(input.equals("y")){
			System.out.print("Type the message to send:  ");
			message = scan.nextLine();
			return message;
		}

		return null;
	}
}
