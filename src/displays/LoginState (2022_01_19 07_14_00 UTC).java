package displays;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class LoginState extends State {

	static String finalUsername = "";
		
	private HashMap<String, String> passwords;
	
	// JComponents of this state
	private JTextField usernameEntry, newUsernameEntry;
	private JPasswordField passwordEntry , newPasswordEntry, newPasswordReEntry;
	private JButton login, register, createAccount, back;
	private JLabel passwordLabel, usernameLabel, newUsernameLabel, newPasswordLabel, newPasswordReLabel;
	private JPanel panel;
	
	// ArrayList that contains all the JCompoents in its proper shift orientation
	// used when user switches from login to register
	private ArrayList<JComponent> loginShift;
	private ArrayList<JComponent> registerShift;
	
	// timer used for the animation between the login and registers
	private Timer componentForwardTimer, componentBackwardTimer;
	
	// other variables associated with this screen
	private int moveAmount;
	private boolean isClicked;
	private File passwordFile;
	
	// the init method initializes all variables and calls base methods
	@Override
	public void init() {
		
		passwords = new HashMap<String, String>();
		
		loginShift = new ArrayList<JComponent>();
		registerShift = new ArrayList<JComponent>();
		
		componentForwardTimer = new Timer(1, this);
		componentBackwardTimer = new Timer(1, this);
		
		moveAmount = 0;
		
		// method that reads all the usernames and passwords from the stored password file
		readPasswords();
		
	}

	// method that initializes all JComponents and places them onto the screen
	@Override
	public void addJComponents() {
		
		// a JPanel that places 
		panel = new JPanel(null);
		panel.setBackground(new Color(27, 62, 90));
		panel.setBounds(0, 0, WIDTH, HEIGHT);
		
		//######################## Login Page ########################
		
		usernameEntry = new JTextField();
		usernameEntry.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		usernameEntry.setBounds(WIDTH/2-200, 250, 400, 50);
		loginShift.add(usernameEntry);
		panel.add(usernameEntry);
		
		passwordEntry = new JPasswordField();
		passwordEntry.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		passwordEntry.setBounds(WIDTH/2-200, 350, 400, 50);
		passwordEntry.setEchoChar('*');
		loginShift.add(passwordEntry);
		panel.add(passwordEntry);
		
		usernameLabel = new JLabel("Username: ");
		usernameLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		usernameLabel.setForeground(new Color(68, 225, 212));
		usernameLabel.setBounds(WIDTH/2-200, 200, 400, 50);
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginShift.add(usernameLabel);
		panel.add(usernameLabel);
		
		passwordLabel = new JLabel("Password: ");
		passwordLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		passwordLabel.setForeground(new Color(68, 225, 212));
		passwordLabel.setBounds(WIDTH/2-200, 300, 400, 50);
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginShift.add(passwordLabel);
		panel.add(passwordLabel);

		login = new JButton(new ImageIcon(new ImageIcon("images/login.png").getImage().getScaledInstance(170, 90, 0)));
		login.addActionListener(this);
		login.setBounds(WIDTH/2-200, 450, 180, 100);
		loginShift.add(login);
		panel.add(login);
		
		register = new JButton(new ImageIcon(new ImageIcon("images/register.png").getImage().getScaledInstance(170, 90, 0)));
		register.addActionListener(this);
		register.setBounds(WIDTH/2-200 + 220, 450, 180, 100);
		loginShift.add(register);
		panel.add(register);
		
		//######################## Register Page ########################
		
		newUsernameEntry = new JTextField();
		newUsernameEntry.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		newUsernameEntry.setBounds(WIDTH/2-200 + SHIFT_AMOUNT*SHIFT_SPEED, 200, 400, 50);
		registerShift.add(newUsernameEntry);
		panel.add(newUsernameEntry);
		
		newPasswordEntry = new JPasswordField();
		newPasswordEntry.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		newPasswordEntry.setBounds(WIDTH/2-200 + SHIFT_AMOUNT*SHIFT_SPEED, 300, 400, 50);
		newPasswordEntry.setEchoChar('*');
		registerShift.add(newPasswordEntry);
		panel.add(newPasswordEntry);
		
		newPasswordReEntry = new JPasswordField();
		newPasswordReEntry.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		newPasswordReEntry.setBounds(WIDTH/2-200 + SHIFT_AMOUNT*SHIFT_SPEED, 400, 400, 50);
		newPasswordReEntry.setEchoChar('*');
		registerShift.add(newPasswordReEntry);
		panel.add(newPasswordReEntry);
		
		newUsernameLabel = new JLabel("New Username: ");
		newUsernameLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		newUsernameLabel.setForeground(new Color(68, 225, 212));
		newUsernameLabel.setBounds(WIDTH/2-200 + SHIFT_AMOUNT*SHIFT_SPEED, 150, 400, 50);
		newUsernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registerShift.add(newUsernameLabel);
		panel.add(newUsernameLabel);
		
		newPasswordLabel = new JLabel("New Password: ");
		newPasswordLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		newPasswordLabel.setForeground(new Color(68, 225, 212));
		newPasswordLabel.setBounds(WIDTH/2-200 + SHIFT_AMOUNT*SHIFT_SPEED, 250, 400, 50);
		newPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registerShift.add(newPasswordLabel);
		panel.add(newPasswordLabel);
		
		newPasswordReLabel = new JLabel("Retype Password: ");
		newPasswordReLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		newPasswordReLabel.setForeground(new Color(68, 225, 212));
		newPasswordReLabel.setBounds(WIDTH/2-200 + SHIFT_AMOUNT*SHIFT_SPEED, 350, 400, 50);
		newPasswordReLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registerShift.add(newPasswordReLabel);
		panel.add(newPasswordReLabel);

		createAccount = new JButton(new ImageIcon(new ImageIcon("images/create account.png").getImage().getScaledInstance(170, 65, 0)));
		createAccount.addActionListener(this);
		createAccount.setBounds(WIDTH/2-200 + SHIFT_AMOUNT*SHIFT_SPEED, 500, 180, 75);
		registerShift.add(createAccount);
		panel.add(createAccount);
		
		back = new JButton(new ImageIcon(new ImageIcon("images/back2.png").getImage().getScaledInstance(170, 65, 0)));
		back.addActionListener(this);
		back.setBounds(WIDTH/2-200 + SHIFT_AMOUNT*SHIFT_SPEED + 220, 500, 180, 75);
		registerShift.add(back);
		panel.add(back);
		
		add(panel);
		
	}
	
	private void readPasswords() {
		
		passwordFile = new File("others/passwords");
		Scanner input;
		
		try {
			
			input = new Scanner(passwordFile);
			
			while(input.hasNext()) {
				
				passwords.put(input.next(), input.next());
				
			}
			
		} catch (FileNotFoundException error) {
			
			error.printStackTrace();
			
		}
		
	}
	
	private void savePasswords() {
		
		try {
			
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(passwordFile.getAbsolutePath(), true)));
			
			out.println(newUsernameEntry.getText() + " " + String.valueOf(newPasswordEntry.getPassword()));
			
			out.close();
			
		} catch (IOException error) {
			
			error.printStackTrace();
			
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == componentForwardTimer) {
			
			for(JComponent component: loginShift) {
				
				component.setBounds(component.getX() - SHIFT_SPEED, component.getY(), component.getWidth(), component.getHeight());
				
			}
			for(JComponent component: registerShift) {
				
				component.setBounds(component.getX() - SHIFT_SPEED, component.getY(), component.getWidth(), component.getHeight());
				
			}
			
			repaint();
			
			moveAmount--;
			
			if(moveAmount == 0) {
				
				componentForwardTimer.stop();
				isClicked = false;
				
			}
			
		} else if(event.getSource() == componentBackwardTimer) {
			
			for(JComponent component: loginShift) {
				
				component.setBounds(component.getX() + SHIFT_SPEED, component.getY(), component.getWidth(), component.getHeight());
				
			}
			for(JComponent component: registerShift) {
				
				component.setBounds(component.getX() + SHIFT_SPEED, component.getY(), component.getWidth(), component.getHeight());
				
			}
			
			repaint();
			
			moveAmount--;
			
			if(moveAmount == 0) {
				
				componentBackwardTimer.stop();
				isClicked = false;
				
			}
			
		} else if(event.getSource() == login) {
			
			if(isClicked)
				return;
			
			String actualPassword = passwords.get(usernameEntry.getText());
			
			if(passwords.containsKey(usernameEntry.getText()) && actualPassword.equals(String.valueOf(passwordEntry.getPassword()))) {
				
				finalUsername = usernameEntry.getText();
				
				new DatabaseState();
				this.dispose();
				
			} else {
				
				JOptionPane.showMessageDialog(null,
						"Invalid password or username! \n\n"
								+ "click 'ok' to continue...",
						"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
				
			}
			
		} else if(event.getSource() == register) {
			
			moveAmount = SHIFT_AMOUNT;
			componentForwardTimer.start();
			isClicked = true;
			newUsernameEntry.setText("");
			newPasswordEntry.setText("");
			newPasswordReEntry.setText("");
			
		} else if(event.getSource() == back) {
			
			moveAmount = SHIFT_AMOUNT;
			componentBackwardTimer.start();
			isClicked = true;
			
			passwordEntry.setText("");
			usernameEntry.setText("");
			
		} else if(event.getSource() == createAccount) {
			
			if(isClicked)
				return;
			
			if(passwords.containsKey(newUsernameEntry.getText())) {
				
				JOptionPane.showMessageDialog(null,
						"ACCOUNT TAKEN, please try a different username! \n\n"
								+ "click 'ok' to continue...",
						"FAILED", JOptionPane.WARNING_MESSAGE);
				
				return;
				
			} else if(String.valueOf(newPasswordEntry.getPassword()).length() == 0 || newUsernameEntry.getText().length() == 0) {
				
				JOptionPane.showMessageDialog(null,
						"Username and password cannot be empty! \n\n"
								+ "click 'ok' to continue...",
						"FAILED", JOptionPane.WARNING_MESSAGE);
				
				return;
				
			} else if(!String.valueOf(newPasswordEntry.getPassword()).equals(String.valueOf(newPasswordReEntry.getPassword()))) {
				
				JOptionPane.showMessageDialog(null,
						"Password and retyped password does not match! \n\n"
								+ "click 'ok' to continue...",
						"FAILED", JOptionPane.WARNING_MESSAGE);
				
				return;
				
			}
			
			passwords.put(newUsernameEntry.getText(), String.valueOf(newPasswordEntry.getPassword()));
			File newProject = new File("libraries/" + newUsernameEntry.getText());
			
			try {
				
				newProject.createNewFile();
				
			} catch (IOException e) {
				
				JOptionPane.showMessageDialog(null,
						"File Creation Failed! \n\n"
								+ "click 'ok' to continue...",
						"FAILURE", JOptionPane.WARNING_MESSAGE);
				
				return;
				
			}
			savePasswords();
			
			JOptionPane.showMessageDialog(null,
					"ACCOUNT CREATED! \n\n"
							+ "click 'ok' to continue...",
					"SUCCESS", JOptionPane.INFORMATION_MESSAGE);
			
			moveAmount = SHIFT_AMOUNT;
			componentBackwardTimer.start();
			isClicked = true;
			
			passwordEntry.setText("");
			usernameEntry.setText("");
			
		}
		
	}
	
}
