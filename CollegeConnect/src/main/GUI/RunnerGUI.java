package main.GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Dorms;
import main.FileIO;
import main.Gender;
/**
 * 
 * @author Matthew Betanski
 *
 */
public class RunnerGUI {

	public static int index = -1; //Index of the currently logged in user, -1 if not logged in yet
	private int WIDTH = 900; private int LENGTH = 950;

	//Creates the JFrame frame, adds the Tabs JTabbedPane in
	private RunnerGUI(){
		FileIO.startUp();
		FileIO.readPersons();
		FileIO.readClubs();
		JFrame frame = new JFrame("College Connect");
		
		frame.setMinimumSize(new Dimension(WIDTH,LENGTH));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setResizable(false);
		//frame.add(createLoginPanel());
		frame.add(new Tabs());
		frame.setVisible(true);
	}
	//Creates a new RunnerGUI object
	public static void main(String[] args) {
		new RunnerGUI();
	}

	//Sets the index of the currently logged in user
	public static void setIndex(int newIndex) {
		index = newIndex;
	}
	//Gets the index of the currently logged in user
	public static int getIndex() {
		return index;
	}
}

//JTabbedPane that holds all the JPanels for the GUI interface
final class Tabs extends JTabbedPane{
	public Tabs() {

		add(new LoginPanel(),"Login");
		add(new CreateProfilePanel(),"Create Profile");
		add(new DeleteProfilePanel(),"Delete Profile");
		add(new DisplayProfilePanel(),"Display Profile");
		add(new EditProfilePanel(),"Edit Profile");
		add(new FindRoommatePanel(),"Find Roommate");
		add(new FindClubsPanel(),"Find Clubs");
		UpdateThread updateThread = new UpdateThread();
		addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if(getSelectedIndex() == 3) {
					((DisplayProfilePanel)getComponentAt(3)).populate();
				}
				if(getSelectedIndex() == 5)
					((FindRoommatePanel)getComponentAt(5)).displayMatches();

			}

		});
		hideTabs();
	}

	//Hides tabs so that the user cannot access certain features without logging in
	public void hideTabs() {
		setEnabledAt(2,false);
		setEnabledAt(3,false);
		setEnabledAt(4,false);
		setEnabledAt(5,false);
		setEnabledAt(6,false);

	}
	//Shows tabs once user logs in
	public void showTabs() {
		setEnabledAt(2,true);
		setEnabledAt(3,true);
		setEnabledAt(4,true);
		setEnabledAt(5,true);
		setEnabledAt(6,true);
		setEnabledAt(0,false);
		setEnabledAt(1,false);
		setSelectedIndex(3);
		((DisplayProfilePanel)getComponentAt(3)).populate();


	}
	//Sets the tab at a current location
	public void setTab(int tab) {
		setSelectedIndex(tab);
	}

	//Populates the JLabels in the users DisplayProfilePanel
	public void populate() {
		((DisplayProfilePanel)getComponentAt(3)).populate();
	}
	
	//New thread to update time-sensitive components like messages
	class UpdateThread extends Thread{
		public void run() {
			Timer timer = new Timer(); 
			timer.schedule( new TimerTask() 
			{ 
				public void run() { 
					((FindRoommatePanel)getComponentAt(5)).updateMessages();
				} 
			}, 0, 3*(1000*1));

		}
		UpdateThread(){
			run();
		}
	}
	//JPanel that holds the features that allows a user to log in
	final class LoginPanel extends JPanel{
		int index = -1;
		JTextField usernameField; JPasswordField passwordField;
		public LoginPanel() {
			setBounds(0,0,540,540);
			add(createUsernamePanel());
			add(createPasswordPanel());
			JButton loginButton = new JButton("Login");
			loginButton.setBounds(100,100,100,100);
			loginButton.addActionListener(new ActionListener() {

				@Override
				//When the user clicks the loginButton, the program searches through all profiles to find which profile has the matching username and password
				//If it finds a matching profile, the index is set to that location in the arrayList
				//Might make problems later for concurrence, in case other profiles are added/deleted during runtime after logging in
				//Right now the program creates the profileList only at runtime, so this shouldnt be an issue
				//If the backend system were to be based off one list for all instances of the program, this would be an issue though
				//To fix it, I could save the users log in information instead, and when you call RunnerGUI.getIndex(), it would loop through all profiles and return the index of the user that is currently logged in
				//ALternative option would be to create a instance variable of Person type that is for the currenty logged in user, and just use that instead
				//This would probably be the simplest fix, but would require the most rewriting because I would have to modify every line that I call FileIO.getProfiles().get(RunnerGUI.getIndex())
				public void actionPerformed(ActionEvent e) {
					for(int i = 0; i < FileIO.getProfiles().size(); i++) {
						if(usernameField.getText().equals(FileIO.getProfiles().get(i).getUsername()) && String.valueOf(passwordField.getPassword()).equals(FileIO.getProfiles().get(i).getPassword())) {
							RunnerGUI.setIndex(i);
						}
					}
					if(RunnerGUI.getIndex() != -1) {
						showTabs();

					}
					else
						System.out.println("Incorrect Username or Password, please try again.");

				}

			});
			add(loginButton);
			setLayout(null);
			
		}
		//A JPanel that houses a JLabel and and a JField
		//I dont really remember why I did this, because I remember making a class that pretty much does the same thing, called JTextInputPanel
		//If I get time, I'll replace this part, but its low-priority right now
		private JPanel createUsernamePanel() {
			JPanel usernamePanel = new JPanel();
			JLabel usernameLabel = new JLabel("Username: ");
			usernameField = new JTextField();
			usernamePanel.setLayout(null);
			usernamePanel.add(usernameField);
			usernamePanel.add(usernameLabel);
			usernameField.setBounds(125,10,125,30);
			usernameLabel.setBounds(0,0,100,50);
			usernamePanel.setBounds(0,0,250,50);
			return usernamePanel;
		}

		//JLabel and JPasswordField in a JPanel
		//Also replace if I get time
		//Replace with JPasswordInputPanel
		private JPanel createPasswordPanel() {
			JPanel passwordPanel = new JPanel();
			JLabel passwordLabel = new JLabel("Password: ");
			passwordField = new JPasswordField();
			passwordPanel.setLayout(null);
			passwordPanel.add(passwordField);
			passwordPanel.add(passwordLabel);
			passwordField.setBounds(125,10,125,30);
			passwordLabel.setBounds(0,0,100,50);
			passwordPanel.setBounds(0,50,250,50);
			return passwordPanel;
		}
	}

	//JPanel to create a new user
	final class CreateProfilePanel extends JPanel{
		public CreateProfilePanel() {
			setLayout(null);
			setBounds(0,0,540,540);
			JButton createProfileButton = new JButton("Create Profile");
			JTextInputPanel username = new JTextInputPanel("Username: ");
			JPasswordInputPanel password = new JPasswordInputPanel("Password: ");
			JTextInputPanel name = new JTextInputPanel("Full Name: ");
			JTextInputPanel age = new JTextInputPanel("Age: ");
			JTextInputPanel email = new JTextInputPanel("Email: ");
			JTextInputPanel yearInSchool = new JTextInputPanel("Year in School: ");
			String[] options = {"Male","Female","Other"};
			JComboInputPanel gender = new JComboInputPanel("Gender: ", options);
			username.setBounds(0,0,250,50);
			password.setBounds(0,50,250,50);
			name.setBounds(0,100,250,50);
			age.setBounds(0,150,250,50);
			email.setBounds(0,200,250,50);
			yearInSchool.setBounds(0, 250, 250, 50);
			gender.setBounds(0,300,250,50);
			createProfileButton.setBounds(0,350,250,50);
			add(username);
			add(password);
			add(name);
			add(age);
			add(email);
			add(yearInSchool);
			add(gender);
			add(createProfileButton);
			//Takes the information entered by the user and creates a new profile
			//If successful, creates a JOptionPane saying "Profile Created"
			//If fails, creates JOptionPane saying "Error Occurred, try again."
			createProfileButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						if(FileIO.createProfile(username.getText(), password.getText(), name.getText(), yearInSchool.getText(), gender.getText(), Integer.valueOf(age.getText()), email.getText())) {
							JOptionPane.showMessageDialog(null, "Profile Created");
							FileIO.readPersons();
						}
						else
							JOptionPane.showMessageDialog(null, "Error occured, try again.");
					}catch(Exception exception) {
						JOptionPane.showMessageDialog(null, "Error occured, try again.");
					}

				}

			});


		}
	}
	//JPanel to display information about a user
	final class DisplayProfilePanel extends JPanel{
		//Current way is just a ton of JLabels
		//Will probably switch to just using one JTextArea so users can scroll so that managing the size of each field isnt a pain
		JLabel name,age,gender,yearInSchool,major,hobbies,petPeeves,roommatePreferences,roommateDislikes,dormChoices, matchName;
		public DisplayProfilePanel() {
			setLayout(null);
			name = new JLabel();
			age = new JLabel();
			gender = new JLabel();
			yearInSchool = new JLabel();
			major = new JLabel();
			hobbies = new JLabel();
			petPeeves = new JLabel();
			roommatePreferences = new JLabel();
			roommateDislikes = new JLabel();
			dormChoices = new JLabel();
			matchName = new JLabel();
			JScrollPane scrollHobbies = new JScrollPane(hobbies,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			JScrollPane scrollPetPeeves = new JScrollPane(petPeeves,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			JScrollPane scrollRoommatePreferences = new JScrollPane(roommatePreferences,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			JScrollPane scrollRoommateDislikes = new JScrollPane(roommateDislikes,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			add(name);
			add(age);
			add(gender);
			add(yearInSchool);
			add(major);
			add(scrollHobbies);
			add(scrollPetPeeves);
			add(scrollRoommateDislikes);
			add(scrollRoommatePreferences);
			add(dormChoices);
			add(matchName);
			name.setBounds(0,0,300,50);
			age.setBounds(0,50,300,50);
			gender.setBounds(0,100,300,50);
			yearInSchool.setBounds(0,150,300,50);
			major.setBounds(0,200,300,50);
			scrollPetPeeves.setBounds(0,250,300,50);
			scrollHobbies.setBounds(0,300,300,50);
			scrollRoommatePreferences.setBounds(0,350,300,50);
			scrollRoommateDislikes.setBounds(0,400,300,50);
			dormChoices.setBounds(0,450,300,50);
			matchName.setBounds(0,500,300,50);

		}
		//Populates the JLabels in this panel with the information of the currently logged in user
		public void populate() {
			name.setText("Name: " + FileIO.getProfiles().get(RunnerGUI.getIndex()).getName());
			age.setText("Age: " + String.valueOf(FileIO.getProfiles().get(RunnerGUI.getIndex()).getAge()));
			gender.setText("Gender: " + String.valueOf(FileIO.getProfiles().get(RunnerGUI.getIndex()).getGender()));
			yearInSchool.setText("Year In School: " + FileIO.getProfiles().get(RunnerGUI.getIndex()).getYearInSchool());
			major.setText("Major: " + FileIO.getProfiles().get(RunnerGUI.getIndex()).getMajor());
			hobbies.setText("Hobbies: " + FileIO.getProfiles().get(RunnerGUI.getIndex()).getHobbies());
			petPeeves.setText("Pet Peeves: " + FileIO.getProfiles().get(RunnerGUI.getIndex()).getPetPeeves());
			roommatePreferences.setText("Roommate Preferences: " + FileIO.getProfiles().get(RunnerGUI.getIndex()).getRoommatePreferences());
			roommateDislikes.setText("Roommate Dislikes: " + FileIO.getProfiles().get(RunnerGUI.getIndex()).getRoommateDislikes());
			dormChoices.setText("Dorm Choices: " + String.valueOf(FileIO.getProfiles().get(RunnerGUI.getIndex()).getDormChoices()));
			matchName.setText("Match: " + String.valueOf(FileIO.getProfiles().get(RunnerGUI.getIndex()).getMatchName()));
		}
		//Populates the JLabels in this panel with the information of the user at the index index of the profileList
		public void populateUser(int index) {
			name.setText("Name: " + FileIO.getProfiles().get(index).getName());
			age.setText("Age: " + String.valueOf(FileIO.getProfiles().get(index).getAge()));
			gender.setText("Gender: " + String.valueOf(FileIO.getProfiles().get(index).getGender()));
			yearInSchool.setText("Year In School: " + FileIO.getProfiles().get(index).getYearInSchool());
			major.setText("Major: " + FileIO.getProfiles().get(index).getMajor());
			hobbies.setText("Hobbies: " + FileIO.getProfiles().get(index).getHobbies());
			petPeeves.setText("Pet Peeves: " + FileIO.getProfiles().get(index).getPetPeeves());
			roommatePreferences.setText("Roommate Preferences: " + FileIO.getProfiles().get(index).getRoommatePreferences());
			roommateDislikes.setText("Roommate Dislikes: " + FileIO.getProfiles().get(index).getRoommateDislikes());
			dormChoices.setText("Dorm Choices: " + String.valueOf(FileIO.getProfiles().get(index).getDormChoices()));
		}
	}

	final class DeleteProfilePanel extends JPanel{

		public DeleteProfilePanel() {
			setLayout(null);
			setBounds(0,0,540,540);
			JButton delProfileButton = new JButton("Delete Profile");
			delProfileButton.setBounds(0,0,250,250);
			add(delProfileButton);
			delProfileButton.addActionListener(new ActionListener() {
				int count = 0;
				@Override
				public void actionPerformed(ActionEvent e) {
					if(count == 0) {
						delProfileButton.setText("Are you sure?");
						count++;
						return;
					}
					if(count == 1) {
						//For some dumb reason throws an IndexOutOfBoundsException AFTER deleting the profile at that index
						//I dont get it because it should only run this line once when you delete your profile, and so the index should be valid for that call
						//But hopefully this makes it so the program works fine
						try {
							FileIO.deleteProfile(FileIO.getProfiles().get(RunnerGUI.getIndex()));
							hideTabs();
							setTab(0);
						}catch(IndexOutOfBoundsException exception) {

						}
					}


				}

			});
		}
	}
	//Panel that allows a user to edit their information
	final class EditProfilePanel extends JPanel{
		public EditProfilePanel() {
			setLayout(null);
			setBounds(0,0,540,540);
			//List of all field a user can edit
			String[] options = {"Age","Gender","Year in School","Major","Email","Hobbies","Pet Peeves","Roommate Preferences","Roommate Dislikes","Dorm Choices"};
			//Map for reflection that maps the values in options above to the method names for the Person class
			Map<String, String> methodMap = new HashMap<String,String>();
			methodMap.put("Age", "setAge");
			methodMap.put("Gender", "setGender");
			methodMap.put("Year in School", "setYearInSchool");
			methodMap.put("Major", "setMajor");
			methodMap.put("Email", "setEmail");
			methodMap.put("Hobbies", "setHobbies");
			methodMap.put("Pet Peeves", "setPetPeeves");
			methodMap.put("Roommate Preferences", "setRoommatePreferences");
			methodMap.put("Roommate Dislikes", "setRoommateDislikes");
			methodMap.put("Dorm Choices", "setDormChoices");
			//Map for reflection that maps the values in options above to the parameter types for the methods for Person class
			Map<String, Class<?>> parameterMap = new HashMap();
			parameterMap.put("Age", Integer.class);
			parameterMap.put("Gender", Gender.class);
			parameterMap.put("Year in School", String.class);
			parameterMap.put("Major", String.class);
			parameterMap.put("Email", String.class);
			parameterMap.put("Hobbies", String.class);
			parameterMap.put("Pet Peeves", String.class);
			parameterMap.put("Roommate Preferences", String.class);
			parameterMap.put("Roommate Dislikes", String.class);
			parameterMap.put("Dorm Choices", Dorms.class);
			JComboBox choices = new JComboBox(options);
			add(choices);
			choices.setBounds(0,0,150,150);
			JButton editButton = new JButton("Edit Field");
			add(editButton);
			editButton.setBounds(0,200,100,100);
			//When a user presses the edit button, they will be taken to a new JPanel where they can see the previous value for that field and enter a new value
			editButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JPanel editPanel = new JPanel();
					choices.setEnabled(false);
					editButton.setEnabled(false);
					choices.setVisible(false);
					editButton.setVisible(false);
					add(editPanel);
					editPanel.setBounds(0,0,540,540);
					editPanel.setLayout(null);
					JLabel currentField = new JLabel();
					JButton confirmButton = new JButton("Confirm");
					JTextField textEntry = new JTextField();
					//Switch statement for determining what field a user wants to edit
					switch((String)choices.getSelectedItem()) {
					case "Age":
						currentField.setText("Current Age:" + FileIO.getProfiles().get(RunnerGUI.getIndex()).getAge());
						//Prevents user from entering non-digit characters when trying to change age
						textEntry.addKeyListener(new KeyAdapter() {
							public void keyTyped(KeyEvent e) {
								char c= e.getKeyChar();
								if(!Character.isDigit(c) || c==KeyEvent.VK_BACK_SPACE || c==KeyEvent.VK_DELETE) {
									e.consume();
								}
							}
						});
						break;
					case "Gender":
						currentField.setText("Current Gender:" + FileIO.getProfiles().get(RunnerGUI.getIndex()).getGender());
						break;
					case "Year In School":
						currentField.setText("Current Year in School:" + FileIO.getProfiles().get(RunnerGUI.getIndex()).getYearInSchool());
						break;
					case "Major":
						currentField.setText("Current Major:" + FileIO.getProfiles().get(RunnerGUI.getIndex()).getMajor());
						break;
					case "Email":
						currentField.setText("Current Email:" + FileIO.getProfiles().get(RunnerGUI.getIndex()).getEmail());
						break;
					case "Hobbies":
						currentField.setText("Current Hobbies:" + FileIO.getProfiles().get(RunnerGUI.getIndex()).getHobbies());
						break;
					case "Pet Peeves":
						currentField.setText("Current Pet Peeves:" + FileIO.getProfiles().get(RunnerGUI.getIndex()).getPetPeeves());
						break;
					case "Roommate Preferences":
						currentField.setText("Current Roommate Preferences:" + FileIO.getProfiles().get(RunnerGUI.getIndex()).getRoommatePreferences());
						break;
					case "Roommate Dislikes":
						currentField.setText("Current Roommate Dislikes:" + FileIO.getProfiles().get(RunnerGUI.getIndex()).getRoommateDislikes());
						break;
					case "Dorm Choices":
						currentField.setText("Current Dorm Choices:" + FileIO.getProfiles().get(RunnerGUI.getIndex()).getDormChoices());
						break;
					}
					//Allows user to edit gender and dormOptions using a predefined list of options
					String[] genderOptions = {"Male","Female","Other"};
					JComboBox genderChoices = new JComboBox(genderOptions);
					String[] dormOptions = {"Bridgeway","Cooper","Dobson","McGregor","Melcher","Morrow","Pickard","Porter","Rountree","Southwest","Wilgus"};
					JComboBox<String> dormChoices1 = new JComboBox<String>(dormOptions);
					JComboBox<String> dormChoices2 = new JComboBox<String>(dormOptions);
					JComboBox<String> dormChoices3 = new JComboBox<String>(dormOptions);
					editPanel.add(genderChoices);
					editPanel.add(dormChoices3);
					editPanel.add(dormChoices2);
					editPanel.add(dormChoices1);
					editPanel.add(currentField);
					editPanel.add(confirmButton);
					editPanel.add(textEntry);
					currentField.setBounds(0,0,400,200);
					confirmButton.setBounds(0,200,200,200);
					//Reflection for any fields that are plain String values
					if((parameterMap.get((String)choices.getSelectedItem()) == String.class) || (parameterMap.get((String)choices.getSelectedItem()) == Integer.class))
						textEntry.setBounds(200,200,200,200);
					else {
						//Reflection for gender or dorms
						if(parameterMap.get((String)choices.getSelectedItem()) == Gender.class)
							genderChoices.setBounds(200,200,100,50);
						else {
							dormChoices1.setBounds(200,200,100,50);
							dormChoices2.setBounds(200,250,100,50);
							dormChoices3.setBounds(200,300,100,50);
						}
					}
					//Allows a user to confirm their choice so they dont edit any information by mistake
					confirmButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							boolean canExit = true;
							try {
								if(parameterMap.get((String)choices.getSelectedItem()) == Integer.class) {
									int i = Integer.valueOf(textEntry.getText());
									if(i <= 0 || i >= 120) {
										JOptionPane.showMessageDialog(null, "Age must be between 1 and 119");
										canExit = false;
									}
									else
										FileIO.getProfiles().get(RunnerGUI.getIndex()).setAge(Integer.valueOf(textEntry.getText()));
								}
								//Uses Reflection to access the different methods that modify String Instance Variables
								if(parameterMap.get((String)choices.getSelectedItem()) == String.class)
									FileIO.getProfiles().get(RunnerGUI.getIndex()).getClass().getMethod(methodMap.get((String)choices.getSelectedItem()),String.class).invoke(FileIO.getProfiles().get(RunnerGUI.getIndex()),
											textEntry.getText());
								else {
									try {

										if(parameterMap.get((String)choices.getSelectedItem()) == Dorms.class) {
											if( ((String)dormChoices1.getSelectedItem()).equals((String)dormChoices2.getSelectedItem()) || (((String)dormChoices1.getSelectedItem()).equals((String)dormChoices3.getSelectedItem())) || ((String)dormChoices2.getSelectedItem()).equals((String)dormChoices3.getSelectedItem())){
												JOptionPane.showMessageDialog(null, "Cannot use each dorm more than once");
											}
											else {
												ArrayList<Dorms> dormList = new ArrayList<Dorms>();
												dormList.add(Dorms.valueOf((String)dormChoices1.getSelectedItem()));
												dormList.add(Dorms.valueOf((String)dormChoices2.getSelectedItem()));
												dormList.add(Dorms.valueOf((String)dormChoices3.getSelectedItem()));
												FileIO.getProfiles().get(RunnerGUI.getIndex()).setDormChoices(dormList);
											}
										}
										else {
											FileIO.getProfiles().get(RunnerGUI.getIndex()).setGender(Gender.valueOf((String)genderChoices.getSelectedItem()));
										}
									}catch(Exception error) {
										if(parameterMap.get((String)choices.getSelectedItem()) == Gender.class)
											JOptionPane.showMessageDialog(null, "Valid options are: Male, Female, Other");
										if(parameterMap.get((String)choices.getSelectedItem()) == Dorms.class)
											JOptionPane.showMessageDialog(null, "Valid options are: Bridgeway, Cooper, Dobson, McGregor, Melcher, Morrow, Pickard, Porter, Rountree, Southwest, Wilgus");
									}
								}
								//Writes the users changes to their text file
								FileIO.write(FileIO.getProfiles().get(RunnerGUI.getIndex()), null, true);
								if(canExit){
									populate();
									editPanel.setVisible(false);
									choices.setEnabled(true);
									editButton.setEnabled(true);
									choices.setVisible(true);
									editButton.setVisible(true);
								}

							}
							catch(Exception exception){
								exception.printStackTrace();
							}


						}
					});
				}
			});
		}
	}
	//JPanel to find a roommate
	final class FindRoommatePanel extends JPanel{
		int currentIndex = -1;
		DisplayProfilePanel roomatePanel;
		JButton matchButton;
		JTextArea messages;
		public FindRoommatePanel(){
			roomatePanel = new DisplayProfilePanel();
			add(roomatePanel);
			//JButton populateButton = new JButton("Search");
			JButton rightButton = new JButton(">");
			JButton leftButton = new JButton("<");
			JButton matchButton = new JButton("Match");
			JButton sendMessage = new JButton("Send");
			JTextField messageBox = new JTextField();
			messages = new JTextArea();
			messages.setEditable(false);
			JLabel sendMessageLabel = new JLabel("Enter Message Below:");
			setLayout(null);
			roomatePanel.setVisible(false);
			roomatePanel.setBounds(0,0,300,540);
			//add(populateButton);
			add(leftButton);
			add(rightButton);
			add(matchButton);
			add(sendMessage);
			add(messageBox);
			add(messages);
			add(sendMessageLabel);
			sendMessageLabel.setBounds(0,650,300,100);
			messageBox.setBounds(0,750,300,100);
			rightButton.setBounds(200,550,100,100);
			leftButton.setBounds(0,550,100,100);
			messages.setBounds(300, 0, 540, 540);
			sendMessage.setBounds(300,750,100,100);
			//populateButton.setBounds(100,550,100,100);
			matchButton.setBounds(100,550,100,100);
			//matchButton.setVisible(false);
			
			//Moves left in the profileList
			leftButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i = currentIndex-1; i >= 0; i--) {
						if(!(FileIO.getProfiles().get(i).getName().equals(FileIO.getProfiles().get(RunnerGUI.getIndex()).getName())) && FileIO.getProfiles().get(i).getGender() == FileIO.getProfiles().get(RunnerGUI.getIndex()).getGender()) {
							currentIndex = i;
							roomatePanel.populateUser(currentIndex);
							messages.setText(FileIO.getMessage(FileIO.getProfiles().get(RunnerGUI.getIndex()), FileIO.getProfiles().get(currentIndex)));
							break;
						}
					}

				}

			});
			
			//Moves right in the profileList
			rightButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i = currentIndex+1; i < FileIO.getProfiles().size(); i++) {

						if(!(FileIO.getProfiles().get(i).getName().equals(FileIO.getProfiles().get(RunnerGUI.getIndex()).getName())) && FileIO.getProfiles().get(i).getGender() == FileIO.getProfiles().get(RunnerGUI.getIndex()).getGender()) {
							currentIndex = i;
							roomatePanel.populateUser(currentIndex);
							messages.setText(FileIO.getMessage(FileIO.getProfiles().get(RunnerGUI.getIndex()), FileIO.getProfiles().get(currentIndex)));
							break;
						}
					}

				}

			});
			
			//Sets the matchName field in the logged in user to be the name of the currently displayed user
			//matchName will have more functionality later
			matchButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					FileIO.getProfiles().get(RunnerGUI.getIndex()).setMatchName(FileIO.getProfiles().get(currentIndex).getName());
				}

			});
			
			//Sends a message to the currently displayedUser
			sendMessage.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					FileIO.sendMessage(FileIO.getProfiles().get(RunnerGUI.getIndex()), FileIO.getProfiles().get(currentIndex), String.valueOf(messageBox.getText()));
					messages.setText(FileIO.getMessage(FileIO.getProfiles().get(RunnerGUI.getIndex()), FileIO.getProfiles().get(currentIndex)));
				}

			});
		}
		//Displays potential roommates
		public void displayMatches() {
			for(int i = 0; i < FileIO.getProfiles().size(); i++) {
				if(!(FileIO.getProfiles().get(i).getName().equals(FileIO.getProfiles().get(RunnerGUI.getIndex()).getName())) && FileIO.getProfiles().get(i).getGender() == FileIO.getProfiles().get(RunnerGUI.getIndex()).getGender()) {
					currentIndex = i;
					roomatePanel.populateUser(currentIndex);
					//matchButton.setVisible(true);
					roomatePanel.setVisible(true);
					messages.setText(FileIO.getMessage(FileIO.getProfiles().get(RunnerGUI.getIndex()), FileIO.getProfiles().get(currentIndex)));
					break;
				}
			}
		}
		//Updates the messages JTextArea
		public void updateMessages() {
			try {
				messages.setText(FileIO.getMessage(FileIO.getProfiles().get(RunnerGUI.getIndex()), FileIO.getProfiles().get(currentIndex)));
			}catch(Exception e) {

			}
		}
	}
	
	//Allows a user to find clubs
	final class FindClubsPanel extends JPanel{
		int currentIndex = -1;
		String searchType = "";
		FindClubsPanel(){
			setLayout(null);
			ClubPanel clubPanel = new ClubPanel();
			clubPanel.setVisible(false);
			JTextInputPanel keywordEntry = new JTextInputPanel("Enter Keyword:  ");
			JTextInputPanel nameEntry = new JTextInputPanel("Enter Club Name: ");
			JButton keywordSearch = new JButton("Search");
			JButton nameSearch = new JButton("Search");
			JButton leftButton = new JButton("<");
			JButton rightButton = new JButton(">");
			add(keywordEntry);
			add(nameEntry);
			add(keywordSearch);
			add(nameSearch);
			add(leftButton);
			add(rightButton);
			keywordEntry.setBounds(0,0,300,100);
			nameEntry.setBounds(0,100,300,100);
			keywordSearch.setBounds(300,0,100,100);
			nameSearch.setBounds(300,100,100,100);
			rightButton.setBounds(150,200,100,100);
			leftButton.setBounds(50,200,100,100);
			add(clubPanel);
			clubPanel.setBounds(0, 300, 540, 540);
			//Search by name
			nameSearch.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					searchType = "name";
					if(nameEntry.getText().equals("")) {
						return;
					}
					for(int i = 0; i < FileIO.getClubs().size(); i++) {
						if(FileIO.getClubs().get(i).getName().toLowerCase().contains(nameEntry.getText())) {
							clubPanel.populate(i);
							currentIndex = i;
							clubPanel.setVisible(true);
							break;
						}
					}

				}});
			
			//Search by keyword
			keywordSearch.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					searchType = "keyword";
					for(int i = 0; i < FileIO.getClubs().size(); i++) {
						if(FileIO.getClubs().get(i).hasKeyword(keywordEntry.getText())) {
							clubPanel.populate(i);
							currentIndex = i;
							clubPanel.setVisible(true);
							break;
						}
					}
				}

			});
			//Moves left in list
			leftButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i = currentIndex-1; i >= 0; i--) {
						if(searchType.equals("keyword")) {
							if(FileIO.getClubs().get(i).hasKeyword(keywordEntry.getText())) {
								clubPanel.populate(i);
								currentIndex = i;
								break;
							}
						}else if(searchType.equals("name")) {
							if(FileIO.getClubs().get(i).getName().toLowerCase().contains(nameEntry.getText())) {
								clubPanel.populate(i);
								currentIndex = i;
								break;
							}
						}
					}

				}

			});
			//Moves right in list
			rightButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i = currentIndex+1; i < FileIO.getClubs().size(); i++) {
						if(searchType.equals("keyword")) {
							if(FileIO.getClubs().get(i).hasKeyword(keywordEntry.getText())) {
								clubPanel.populate(i);
								currentIndex = i;
								break;
							}
						}else if(searchType.equals("name")) {
							if(FileIO.getClubs().get(i).getName().toLowerCase().contains(nameEntry.getText())) {
								clubPanel.populate(i);
								currentIndex = i;
								break;
							}
						}
					}

				}

			});
		}
	}
	//Panel to display club info
	final class ClubPanel extends JPanel {
		JTextArea clubInfo;
		JLabel name,numMembers,location,meetingTime,fees,description,president,presPhone,presEmail,keywords;
		ClubPanel(){
			setLayout(null);
			clubInfo = new JTextArea();
			clubInfo.setEditable(false);
			name = new JLabel();
			numMembers = new JLabel();
			location = new JLabel();
			meetingTime = new JLabel();
			fees = new JLabel();
			description = new JLabel();
			president = new JLabel();
			presPhone = new JLabel();
			presEmail = new JLabel();
			keywords = new JLabel();
			JScrollPane scrollDescription = new JScrollPane(description,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			JScrollPane scrollKeywords = new JScrollPane(keywords,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			add(name);
			add(numMembers);
			add(location);
			add(meetingTime);
			add(fees);
			add(scrollDescription);
			add(keywords);
			add(president);
			add(presPhone);
			add(presEmail);
			name.setBounds(0,0,300,50);
			numMembers.setBounds(0,50,300,50);
			location.setBounds(0,100,300,50);
			meetingTime.setBounds(0,150,300,50);
			fees.setBounds(0,200,300,50);
			president.setBounds(0,300,300,50);
			scrollDescription.setBounds(0,250,300,50);
			presPhone.setBounds(0,350,300,50);
			presEmail.setBounds(0,400,300,50);
			scrollKeywords.setBounds(0,450,300,50);
		}
		//populates the panel
		public void populate(int index) {
			name.setText("Name: " + FileIO.getClubs().get(index).getName());
			numMembers.setText("Number of Members: " + String.valueOf(FileIO.getClubs().get(index).getNumMembers()));
			location.setText("Location: " + String.valueOf(FileIO.getClubs().get(index).getLocation()));
			meetingTime.setText("Meeting Time: " + FileIO.getClubs().get(index).getMeetingTime());
			fees.setText("Fees: " + FileIO.getClubs().get(index).getFees());
			description.setText("Description: " + FileIO.getClubs().get(index).getDescription());
			president.setText("President: " + FileIO.getClubs().get(index).getPresident());
			presPhone.setText("President Phone Number: " + FileIO.getClubs().get(index).getPresPhone());
			presEmail.setText("President Email: " + FileIO.getClubs().get(index).getPresEmail());
			keywords.setText("Keywords: " + String.valueOf(FileIO.getClubs().get(index).getKeyWords()));
		}
	}

	//JPanel to help streamline prompting for information entry
	final class JTextInputPanel extends JPanel{
		JTextField textField;
		JLabel textLabel;
		public JTextInputPanel(String text) {
			textField = new JTextField();
			add(textField);
			textLabel = new JLabel(text);
			add(textLabel);
			setLayout(null);
			textLabel.setBounds(0,0,100,50);
			textField.setBounds(125,10,125,30);
		}

		String getText(){
			return textField.getText();
		}
		void setText(String text) {
			textField.setText(text);
			textLabel.setText(text);
		}
	}
	//JPanel to help streamline prompting for password entry
	final class JPasswordInputPanel extends JPanel{
		JPasswordField textField;
		JLabel textLabel;
		public JPasswordInputPanel(String text) {
			textField = new JPasswordField();
			add(textField);
			textLabel = new JLabel(text);
			add(textLabel);
			setLayout(null);
			textLabel.setBounds(0,0,100,50);
			textField.setBounds(125,10,125,30);
		}
		//Gets the currently entered password
		String getText(){
			return String.valueOf(textField.getPassword());
		}
		//Sets the text of the JLabel and JTextField
		void setText(String text) {
			textField.setText(text);
			textLabel.setText(text);
		}
	}
	//JPanel to help streamline prompting for information from a set list of values
	final class JComboInputPanel extends JPanel{
		JComboBox comboBox;
		JLabel textLabel;
		public JComboInputPanel(String text, String[] options){
			comboBox = new JComboBox(options);
			add(comboBox);
			textLabel = new JLabel(text);
			add(textLabel);
			setLayout(null);
			textLabel.setBounds(0,0,100,50);
			comboBox.setBounds(125,10,125,30);
		}
		//Gets the currently selected item
		String getText() {
			return (String) comboBox.getSelectedItem();
		}
		//Sets text
		void setText(String text) {
			textLabel.setText(text);
		}
		//Sets options
		void setOptions(String[] options) {
			comboBox = new JComboBox(options);
		}
	}

}



