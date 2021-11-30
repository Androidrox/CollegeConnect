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
		//this while loop will continue to loop through the main method until you input a valid variable.  Entering 6 terminates the program.
		while(choice != 6) {
			mainMenu();
			choice = input.nextInt();
			if(choice > 0 && choice < 6) {
				switch(choice) {
				//this switch has an option for each choice in the mainMenu() method, and it will do whatever each line explains e.g. editInformation() edits information
				case 1:
					Person match = matchPeoples(FileIO.getProfiles().get(loggedIn));
					if(match != null) {
						FileIO.createMatch(FileIO.getProfiles().get(loggedIn),match);
						FileIO.sendMessage(FileIO.getProfiles().get(loggedIn), match, privateMessage(match));
					}
					break;
				case 2:
					//calls the editInformation method to change a profiles info.  It's also used to add info that you don't add right away e.g. major, dorm choices, etc.
					editInformation(FileIO.getProfiles().get(loggedIn));
					break;
				case 3:
					//this method is used to find clubs from a club file, club files need to be manually added.
					findClub();
					break;
				case 4:
					//this method is used to remove a profile from the program
					//should be changed to "double check" that the user wants to delete their profile
					deleteProfile(FileIO.getProfiles().get(loggedIn));
					break;
				case 5:
					//should allow the user to view messages from other profiles
					viewMessages();
				}
			}
			else if(choice == 6) {
				System.out.println("\n---------------");
				System.out.println("Exiting program");
				System.out.println("---------------");
			}
			else
			{
				System.out.println("Incorrect Input.  Try again!");
			}
		}
	}

	public static Person matchPeoples(Person profile) {
		//this if statement checks to make sure that there is at least two profiles in the arrayList of all existing profiles, otherwise you'd only be able to match with yourself
		if(FileIO.getProfiles().size() <= 1) {
			System.out.println("It seems that theres no other profiles.");
			return null;
		}
		//this else allows the user to match with others assuming there is more than 1 profile
		else
		{
			Scanner scanner = new Scanner(System.in);
			String choice = "";
			//A temp array is made with all possible candidates for the user (people of the same gender, and not their own profile)
			ArrayList<Person> tempArray = new ArrayList<Person>();
			
			//this for loop assigns the profiles to the temoArray
			for(int i = 0; i < FileIO.getProfiles().size(); i++) {
				//checks to see if a profile isn't a duplicate or of another gender; adds it to the arraylist if it isn't
				if((!(profile.getName().equals(FileIO.getProfiles().get(i).getName())) && (profile.getGender() == FileIO.getProfiles().get(i).getGender()))) {
					tempArray.add(FileIO.getProfiles().get(i));
				}
			}
			//This checks to see if there is at least 1 profile that is eligible for the user to match with, otherwise they are told there is no elgible accounts
			//e.g if You have the only male profile, it won't allow you to match with any of the others and will let you know why
			if(tempArray.size() != 0) {
				System.out.println("\nInstructions:\nEnter -1 to exit.\nEnter > to move to the right, and < to move to the left");
				int i = 0;
				//while it seems pointless, this block of println's are to list the first possible match in the menu
				System.out.println("--------------------------------------------------------");
				System.out.println("Name:  " + tempArray.get(i).getName());
				System.out.println("Age:  " + tempArray.get(i).getAge());
				System.out.println("Gender:  " + tempArray.get(i).getGender());
				System.out.println("Major:  " + tempArray.get(i).getMajor());
				System.out.println("Email:  " + tempArray.get(i).getEmail());
				System.out.println("Hobbies:  " + tempArray.get(i).getHobbies());
				System.out.println("Peeves:  " + tempArray.get(i).getPetPeeves());
				System.out.println("Roommate Preferences:  " + tempArray.get(i).getRoommatePreferences());
				System.out.println("Roomate Dislikes:  " + tempArray.get(i).getRoommateDislikes());
				System.out.println("Would you like to match with this person? (y) (<) (>) (-1)");
				//this loop will not end until the user either ends by inputting a -1 or matches with another profile
				while(true) {
					//will leave the loop if the user inputs a y and returns the profile the user put a y for
					if(choice.equalsIgnoreCase("y"))
						break;
					//will leave the loop if the user inputs a -1 and returns null for the profile to match with
					if(choice.equals("-1"))
						break;
					//If the user tries go past the last index in the arrayList, the program tells the user they hit the end and then says to try again
					if(choice.equals(">") && i >= tempArray.size()-1) {
						System.out.println("Already at last user.");
						System.out.println("Would you like to match with this person? (y) (<) (>) (-1)");
					}
					//If the user tries go past the first index in the arrayList, the program tells the user they hit the beginning and then says to try again
					else if(choice.equals("<") && i <= 0) {
						System.out.println("Already at first user");
						System.out.println("Would you like to match with this person? (y) (<) (>) (-1)");
					}
					//If the user goes to the right, i is increased and the next index is listed for the user to see
					else if(choice.equals(">")) {
						i++;
						System.out.println("--------------------------------------------------------");
						System.out.println("Name:  " + tempArray.get(i).getName());
						System.out.println("Age:  " + tempArray.get(i).getAge());
						System.out.println("Gender:  " + tempArray.get(i).getGender());
						System.out.println("Major:  " + tempArray.get(i).getMajor());
						System.out.println("Email:  " + tempArray.get(i).getEmail());
						System.out.println("Hobbies:  " + tempArray.get(i).getHobbies());
						System.out.println("Peeves:  " + tempArray.get(i).getPetPeeves());
						System.out.println("Roommate Preferences:  " + tempArray.get(i).getRoommatePreferences());
						System.out.println("Roomate Dislikes:  " + tempArray.get(i).getRoommateDislikes());
						System.out.println("Would you like to match with this person? (y) (<) (>) (-1)");
					}
					//If the user goes left, i is increased and the former index is listed for the user to see
					else if(choice.equals("<")) {
						i--;
						System.out.println("--------------------------------------------------------");
						System.out.println("Name:  " + tempArray.get(i).getName());
						System.out.println("Age:  " + tempArray.get(i).getAge());
						System.out.println("Gender:  " + tempArray.get(i).getGender());
						System.out.println("Major:  " + tempArray.get(i).getMajor());
						System.out.println("Email:  " + tempArray.get(i).getEmail());
						System.out.println("Hobbies:  " + tempArray.get(i).getHobbies());
						System.out.println("Peeves:  " + tempArray.get(i).getPetPeeves());
						System.out.println("Roommate Preferences:  " + tempArray.get(i).getRoommatePreferences());
						System.out.println("Roomate Dislikes:  " + tempArray.get(i).getRoommateDislikes());
						System.out.println("Would you like to match with this person? (y) (<) (>) (-1)");
					}
					choice = scanner.nextLine();
				}
				//returns the profile the user picked assuming they entered "y" for a profile
				if(!choice.equals("-1"))
					return tempArray.get(i);
				//returns null if the user entered a -1
				else
					return null;
			}
			//This will say this if not possible matches are avaiable
			else {
				System.out.println("No valid roommates available.");
				return null;
			}
		}
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
		//loops through the available profiles to see if a user name and password are the exact same, and if they are then the user is logged in
		for(int i = 0; i < FileIO.getProfiles().size(); i++) {
			//if the username and password match, the index is returned
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
		System.out.println("5.  See Messages");
		System.out.println("6.  Exit");
		System.out.println("Please enter the corresponding number with what you'd like to do:  ");


	}

	//this method allows the user to edit profile information
	public static void editInformation(Person profile) {
		ArrayList<String> lines = new ArrayList<String>();

		Scanner input = new Scanner(System.in);

		System.out.println("What would you like to change?");

		//Currently changing your name breaks the program, to be fixed at a later date
		//System.out.println("1.  Name");

		System.out.println("2.  Age");
		System.out.println("3.  Gender");
		System.out.println("4.  Year in School");
		System.out.println("5.  Major");
		System.out.println("6.  Hobbies ");
		System.out.println("7.  Pet Peeves");
		System.out.println("8.  Roommate Preferences");
		System.out.println("9.  Roommate Dislikes");
		System.out.println("10.  Dorm Choices");
		//eventually set this up for a string and then check if its an int, if it is run, if it isn't return an error and return to main method
		int choice = input.nextInt();
		input.nextLine();
		//this switch takes the users input and runs a block of code to list what a current value is, and then gives you the option to change it
		switch(choice) {


		//Currently changing your name breaks the program, to be fixed at a later date

		/*
		case 1:  
			System.out.println("Current name is:  " + profile.getName());
			System.out.print("Change name to:  ");
			String name = input.nextLine();
			profile.setName(name);
			lines.add("name:" + profile.getName());
			break;
		 */

		//this case allows the user to input a new age to set their account information too.
		case 2:
			System.out.println("Current age is:  " + profile.getAge());
			System.out.print("Change age to:  ");
			String age = input.nextLine();

			//This loop is our new input validation that ensures the input is a valid age and contains no symbols or letters.

			while(!(age.matches("[0-9]+") && age.length() == 2)) {
				System.out.print("Sorry age cannot contain letters and must be between 10 - 99.  Try again:  ");
				age = input.nextLine();
			}
			int age2 = Integer.parseInt(age);

			//This was the old input validation to check for age, however it didn't account for strings so it's been removed.
			//loops until the user enters a valid value from 1-100
			/*
				while(age2 < 0 || age2 > 100) {
					System.out.println("Not a valid age.  Age range 1-100");
					age = input.nextInt();
				}
			 */

			profile.setAge(age2);
			lines.add("age:" + profile.getAge());
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
				return;
			}
			if(genderEnum != null)
				genderEnum = Gender.valueOf(gender.toLowerCase());
			lines.add("gender:" + profile.getGender());

			/*
			else
				System.out.println("Not a valid option for gender, Use male, female, or other.");
			 */

			break;
		case 4:
			System.out.println("Current Year is :  " + profile.getYearInSchool());
			System.out.print("Change current year to:  ");
			String year = input.nextLine();
			profile.setYearInSchool(year);
			lines.add("yearInSchool:" + profile.getYearInSchool());
			break;
		case 5:
			System.out.println("Current Major:  " + profile.getMajor());
			System.out.println("Change Major to:  ");
			String major = input.nextLine();
			profile.setMajor(major);
			lines.add("major:" + profile.getMajor());
			break;
		case 6:
			System.out.println("Current Hobbies are:  " + profile.getHobbies());
			System.out.print("Change Hobbies to:  ");
			String hobbies = input.nextLine();
			profile.setHobbies(hobbies);
			lines.add("hobbies:" + profile.getHobbies());
			break;
		case 7:
			System.out.println("Current Peeves are:  " + profile.getPetPeeves());
			System.out.print("Change Peeves to:  ");
			String peeves = input.nextLine();
			profile.setPetPeeves(peeves);
			lines.add("petPeeves:" + profile.getPetPeeves());
			break;
		case 8:
			System.out.println("Current Preferences are:  " + profile.getRoommatePreferences());
			System.out.print("Change Preferences to:  ");
			String preferences = input.nextLine();
			profile.setRoommatePreferences(preferences);
			lines.add("roomatePref:" + profile.getRoommatePreferences());
			break;
		case 9:
			System.out.println("Current Dislikes are:  " + profile.getRoommateDislikes());
			System.out.print("Change Dislikes to:  ");
			String dislikes = input.nextLine();
			profile.setRoommateDislikes(dislikes);
			lines.add("roomateDislikes:" + profile.getRoommateDislikes());
			break;
		case 10:
			System.out.print("Current Dorm choices are:  ");

			ArrayList<Dorms> dorms = profile.getDormChoices();

			try {
				for(int i = 0; i < dorms.size(); i++) {
					if(i == dorms.size()-1)
						System.out.println(dorms.get(i));
					else
						System.out.println(dorms.get(i) + ", ");

				}
			}catch(Exception e) {
				System.out.println("It seems you don't have any dorms seletected yet.");
			}
			System.out.print("(Enter one dorm name at a time)/nChange dorms to:  ");
			String dorm = input.nextLine();

			String temp = "dormChoices:";
			for(int i = 0; i < 3; i ++) {
				while(true) {
					try {
						dorms.set(i,Enum.valueOf(Dorms.class, dorm));
						if(i != 2)
							temp += dorm + "/";
						else
							temp += dorm;
					} catch(Exception e) {
						System.out.print("Error.  Not a valid option.");
						i--;
						continue;
					}
					break;
				}
				dorm = input.nextLine();
			}
			lines.add(temp);
		}

		FileIO.write(profile, lines, false);
	}

	//this method will allow the user to remove their profile, then terminate program

	//we should add a "are you sure?" message before they delete their profile eventually
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
		if(tempClubs.size() == 0 ) {
			System.out.println("It seems theres no clubs with that keyword.");
		}
		else {
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
	}

	//takes in all the parameters for the createProfile method in the FileIO class. 
	//Returns a statement saying if account was created or not.
	//if profile is not created, user is notified and program terminates.
	public static void createProfile() {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a Username:  ");
		String username = input.nextLine();
		System.out.print("Enter a Password:  ");
		String password = input.nextLine();
		System.out.print("Enter a Name (first and last):  ");
		String name = input.nextLine();
		System.out.print("Enter a Year in School:  ");
		String yearInSchool = input.nextLine();
		System.out.print("Enter a Gender:  ");
		String gender = input.nextLine();
		Gender genderEnum = null;
		try {
			genderEnum = Gender.valueOf(gender.toLowerCase());
		}catch(Exception e) {
			System.out.println("Not a valid option for gender, Use male, female, or other.");
			System.out.println("TERMINATING PROGRAM.  INCORRECT ANSWER.");
			System.exit(0);
		}
		if(genderEnum != null)
			genderEnum = Gender.valueOf(gender.toLowerCase());
		System.out.print("Enter an age:  ");
		String age = input.nextLine();
		while(!(age.matches("[0-9]+") && age.length() == 2)) {
			System.out.print("Sorry age cannot contain letters and must be between 10 - 99.  Try again:  ");
			age = input.nextLine();
		}
		int age2 = Integer.parseInt(age);




		System.out.print("Enter email:  ");
		String email = input.nextLine();
		if(FileIO.createProfile(username, password, name, yearInSchool, String.valueOf(genderEnum), age2, email)) 
			System.out.println("Profile Created!");
		else {
			System.out.println("Profile already exists or there was an error creating the profile.");
			System.exit(0);
		}
	}

	public static void viewMessages() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Here are your messages.");
		//method needs to be made
		FileIO.viewMessages();
		System.out.println("Enter the name of the person you'd like to see messages from.");
		String choice = "";
		//This loops through the messages that have been sent to you from a certain user, and then it allows the user to return a message
		while(true) {
			choice = scan.nextLine();
			for(int i = 0; i < FileIO.getProfiles().size(); i++) {
				if(choice.equals(FileIO.getProfiles().get(i).getName())) {
					FileIO.viewMessages(FileIO.getProfiles().get(i));
					System.out.println("\nWould you like to send the viewer a new message (y/n)");
					choice = scan.nextLine();
					if(choice.equalsIgnoreCase("y")){
						System.out.println("Enter the message you'd like to send.");
						choice = scan.nextLine();
						FileIO.sendMessage(FileIO.getProfiles().get(loggedIn),FileIO.getProfiles().get(i), choice);
					}
				}
			}
			break;
		}
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
