package main.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

import main.FileIO;

public class RunnerGUI {
	public static int index = -1;
	private int WIDTH = 540; private int LENGTH = 540;
	private RunnerGUI(){
		JFrame frame = new JFrame("College Connect");
		frame.setMinimumSize(new Dimension(WIDTH,LENGTH));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);
		//frame.add(createLoginPanel());
		frame.add(new Tabs());
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new RunnerGUI();
	}

	private JPanel createLoginPanel() {
		JPanel logInPanel =  new JPanel();
		logInPanel.setLayout(null);
		logInPanel.setBackground(Color.blue);
		logInPanel.setBounds(0,0,WIDTH,LENGTH);

		JTextField loginText = new JTextField("Login:");
		loginText.setEditable(false);
		loginText.setBounds(0,0,100,100);

		logInPanel.add(loginText);
		return logInPanel;
	}
	
	public static void setIndex(int newIndex) {
		index = newIndex;
	}
	public static int getIndex() {
		return index;
	}
}

final class Tabs extends JTabbedPane{
	public Tabs() {
		setBounds(0,0,540,540);
		add(new LoginPanel(),"Login");
		add(new ProfilePanel(), "Profile");
		hideTabs();
	}
	public void hideTabs() {
		setEnabledAt(1,false);
	}
	public void showTabs() {
		setEnabledAt(1,true);
	}
	final class LoginPanel extends JPanel{
		int index = -1;
		JTextField usernameField; JTextField passwordField;
		public LoginPanel() {
			setBackground(Color.red);
			setBounds(0,0,540,540);
			/*
			JLabel usernameLabel = new JLabel("Username: ");
			JPanel usernamePanel = new JPanel();
			JTextField usernameField = new JTextField();
			usernamePanel.setLayout(null);
			usernamePanel.add(usernameField);
			usernamePanel.add(usernameLabel);
			usernameField.setBounds(125,10,125,30);
			usernameLabel.setBounds(0,0,100,50);
			usernamePanel.setBounds(0,0,250,50);
			 */
			add(createUsernamePanel());
			add(createPasswordPanel());
			JButton loginButton = new JButton("Login");
			loginButton.setBounds(100,100,100,100);
			loginButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(FileIO.getProfiles().size() == 0)
						FileIO.readPersons();
					for(int i = 0; i < FileIO.getProfiles().size(); i++) {
						if(usernameField.getText().equals(FileIO.getProfiles().get(i).getUsername()) && passwordField.getText().equals(FileIO.getProfiles().get(i).getPassword())) {
							RunnerGUI.setIndex(i);
						}
					}
					if(RunnerGUI.getIndex() != -1) {
						System.out.println("Logged In!");
						showTabs();
						
					}
					else
						System.out.println("Incorrect Username or Password, please try again.");
					
				}
				
			});
			add(loginButton);
			setLayout(null);
		}
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

		private JPanel createPasswordPanel() {
			JPanel passwordPanel = new JPanel();
			JLabel passwordLabel = new JLabel("Password: ");
			passwordField = new JTextField();
			passwordPanel.setLayout(null);
			passwordPanel.add(passwordField);
			passwordPanel.add(passwordLabel);
			passwordField.setBounds(125,10,125,30);
			passwordLabel.setBounds(0,0,100,50);
			passwordPanel.setBounds(0,50,250,50);
			return passwordPanel;
		}
	}

	final class ProfilePanel extends JPanel{
		public ProfilePanel() {
			setBackground(Color.blue);
			setBounds(0,0,540,540);
			JButton displayInfoButton = new JButton("displayInfo");
			displayInfoButton.setBounds(0,0,100,100);
			displayInfoButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(FileIO.getProfiles().get(RunnerGUI.getIndex()).getName());
					
				}
				
			});
			add(displayInfoButton);
			setLayout(null);	
		}
	}
}



