package CollegeConnect;
import java.util.ArrayList;
import java.util.Scanner;

public class Runner {
	public static void main(String[] args) {
		
		
		Scanner input = new Scanner(System.in);
		//the loggedIn integer is set to -1 as arrays have an index at 0.  
		int loggedIn = -1;
		ArrayList<Person> profiles = FileIO.getProfiles();
		ArrayList<Club> clubs = FileIO.getClubs();
		
		//asks user is they have an existing file.  if not, they can make one and then sign in.  If they do, then it skips to sign in.
		System.out.println("Do you have an account? (y/n)");
		String hasAccount = input.nextLine();
		if(hasAccount == "n") {
			createProfile();
		}
			
		//This while loop runs the logIn() method and stops looping if the returned index is greater than -1.
		while(loggedIn < 0) {
		loggedIn = logIn(profiles);
			if(loggedIn < 0)
				System.out.println("Incorrect Username or Password!/n");
		}
		
		int choice = 0;
		//this while loop will continue to loop through the main method until you input a valid variable.  Entering 5 terminates the program.
		while(choice != 5) {
			mainMenu();
			choice = input.nextInt();
			if(choice > 0 && choice < 5) {
				switch(choice) {
				case 1:
					matchPeople(profiles.get(loggedIn));
					break;
				case 2:
					editInformation(profiles.get(loggedIn));
					break;
				case 3:
					findClub(clubs);
					break;
				case 4:
					deleteProfile(profiles.get(loggedIn));
					break;
				}
			}
			else if(choice == 5) {
			}
			else
			{
				System.out.println("Incorrect Input.  Try again!");
			}
		}
		
		
		input.close();
	}
	
	public static void matchPeople(Person profile){
		
		//ran out of time, will finish Sunday or Monday	
		
		
		
	}
	
	
	
	
	/*This method takes the ArrayList of person objects from the FileIO class and asks for the User to enter a username or password
	Then method then scans through each person object to see if a username and password match the inputed Strings
	If they do, the method returns the index of the profile logged in as the integer "loggedIn"
	 */
	public static int logIn(ArrayList<Person> profiles) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter your Username:  ");
		String username = input.nextLine();
		System.out.println("Enter your Password:  ");
		String password = input.nextLine();
		int index;
		for(int i = 0; i < profiles.size(); i++) {
			if(username.equals(profiles.get(i).getUsername()) && (password.equals(profiles.get(i).getPassword()))){
				index = i;
			}
		}
		input.close();
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
			profile.setGender(gender);
			break;
		case 4:
			System.out.println("Current Year is :  " + profile.getYearInSchool());
			System.out.print("Change current year to:  ");
			int year = input.nextInt();
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
			System.out.println("Current Preferences are:  " + profile.getRoomatePreferences());
			System.out.print("Change Preferences to:  ");
			String preferences = input.nextLine();
			profile.setRoomatePreferences(preferences);
			break;
		case 9:
			System.out.println("Current Dislikes are:  " + profile.getRoomateDislikes());
			System.out.print("Change Dislikes to:  ");
			String dislikes = input.nextLine();
			profile.setRoomateDislikes(dislikes);
			break;
		case 10:
			System.out.print("Current Dorm choices are:  ");
			//should this arraylist be casted as a <Dorm> arraylist?  doesn't work if I do that.
			ArrayList dorms = profile.getDormChoices();
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
				dorms.set(i,dorm);
				dorm = input.nextLine();
			}
			break;
		}
		input.close();
	}
	
	//this method will allow the user to remove their profile, then terminate program
	public static void deleteProfile(Person profile) {
		FileIO.deleteProfile(profile);
		System.out.println("Account Deleted!");
		System.exit(0);
	}
	
	//for now this just checks for one keyword, can change it to check for more later
	public static void findClub(ArrayList<Club> clubs) {
		//makes a temporary club ArrayList and then adds any clubs with the keyword the user enters into that arrayList
		ArrayList<Club> tempClubs = new ArrayList<Club>();
		Scanner input = new Scanner(System.in);
		System.out.println("Enter a Keyword in the club you're looking for!");
		String keyWord = input.nextLine();
		for(int i = 0; i < clubs.size(); i++) {
			if(clubs.get(i).hasKeyword(keyWord))
				tempClubs.add(clubs.get(i));
		}
		//prints out all clubs with the keyword the user entered, and then allows the user to pick which club to look at in detail
		System.out.println("Here are all the clubs with your keyword!  Enter the value of the club you'd like to look into.");
		for(int i = 0; i < tempClubs.size(); i++) {
			System.out.println((i+1) + ".  " + tempClubs.get(i).getName());
		}
		int choice = input.nextInt();
		if(choice < tempClubs.size() && choice > 0) {
			System.out.println("Club name:  " + tempClubs.get(choice-1).getName() + "\n");
			System.out.println("Club Description:  " + tempClubs.get(choice-1).getDescription() + "\n");
			System.out.println("Number of members:  " + tempClubs.get(choice-1).getNumMembers() + "\n");
			System.out.println("Fees:  $" + tempClubs.get(choice-1).getFees() + "\n");
			System.out.println("Location:  " + tempClubs.get(choice-1).getLocation() + "\n");
			System.out.println("Meeting Time:  " + tempClubs.get(choice-1).getMeetingTime() + "\n");
			System.out.println("Club President:  " + tempClubs.get(choice-1).getPresident() + "\n");
			System.out.println("Presidents Email:  " + tempClubs.get(choice-1).getPresEmail() + "\n");
			System.out.println("Presidents Phone Number:  " + tempClubs.get(choice-1).getPresPhone() + "\n");		
		}
		else
		{
			System.out.println("Invalid answer.");
		}
		
		input.close();
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
		System.out.print("Enter email:  ");
		String email = input.nextLine();
		if(FileIO.createProfile(username, password, name, yearInSchool, gender, age, email)) 
			System.out.println("Profile Created!");
		else
			System.out.println("Profile not Created!");
		System.exit(0);
	}
	
	public static void privateMessage() {
		//WIP, will work on with matt
	}
}
