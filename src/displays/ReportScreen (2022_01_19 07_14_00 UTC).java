package displays;

import javax.swing.*;
import objects.Materials;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ReportScreen extends State {
	
	private JPanel panel, reportPanel, impactPanel;
	
	private JScrollPane report;

	private JLabel materialImageTitle, materialNameTitle, materialRatingTitle, materialColourTitle;

	private static final int SHIFT_AMOUNT = 60;
	private static final int SHIFT_SPEED = 14;
	
	private Timer componentForwardTimer, componentBackwardTimer;
	private int moveAmount;
	
	private JButton wasteReduction, exit, save;
	private JLabel title, projectImpact, projectRating, rating;
	private JTextArea impactDescription;
	
	private JTextArea environmentalField, firstAidField, dangersField;
	private JTextField nameField, typeField, brandField, storageField, hyperToPurchaseField, 
	hyperToMSDField, hazardousRatingField; 
	private JLabel nameLabel, typeLabel, brandLabel, storageLabel, hyperToPurchaseLabel, hyperToMSDLabel, environmentalLabel,
	hazardousRating, firstAidLabel, dangersLabel;

	private JButton[] materialImages;
	private JLabel[] materialNames;
	private JLabel[] materialRatings;
	private JLabel[] materialColours;
	
	private String settingString;
	public String projectColour;
	public int totalRating;
	public int totalToxic;
	public int totalNegative;
	public int totalDamage;
	
	
	private String testImpact;
	private ArrayList<Materials> testObject;
	private String testString;
	private String testString2;
	
	//Movement variables
	private ArrayList<JComponent> shiftUp;
	private ArrayList<JComponent> shiftDown;
	private ArrayList<JComponent> shiftLeft;
	private ArrayList<JComponent> shiftRight;

	public ReportScreen() {
		
	}

	@Override
	public void init() {
		/*
		MaterialState.materialLibrary = new ArrayList<Materials>();
=======

		testObject = new ArrayList<Materials>();
>>>>>>> refs/remotes/origin/master
		testString = new String ("images/groceryBags.png");
		testString2 = new String ("images/cigarettes.png");
		testImpact = new String ("This is a test to see if this message will fit within the boundaries of the designated JLabel, maybe it will maybe it won't but I just need this to be long so I'm just typing random thigns right now happy new year");
		
		MaterialState.materialLibrary.add(new Materials (("Grocery Bag"), testString, testString, testString, testString, testString, null, null, null, testString, getDefaultCloseOperation(), testString, 3, getDefaultCloseOperation(), testString));
		MaterialState.materialLibrary.add(new Materials (("Cigarettes"), testString, testString, testString, testString, testString, null, null, null, testString2, getDefaultCloseOperation(), testString2, 5, getDefaultCloseOperation(), testString));
		MaterialState.materialLibrary.add(new Materials (("Cigarettes"), testString, testString, testString, testString, testString, null, null, null, testString2, getDefaultCloseOperation(), testString2, 1, getDefaultCloseOperation(), testString));
		MaterialState.materialLibrary.add(new Materials (("Cigarettes"), testString, testString, testString, testString, testString, null, null, null, testString2, getDefaultCloseOperation(), testString2, 2, getDefaultCloseOperation(), testString));
		
<<<<<<< HEAD
		materialImages = new JLabel[MaterialState.materialLibrary.size()];
		materialNames = new JLabel[MaterialState.materialLibrary.size()];
		materialRatings = new JLabel[MaterialState.materialLibrary.size()];
		materialColours = new JLabel[MaterialState.materialLibrary.size()];
		*/
		materialImages = new JButton[MaterialState.materialLibrary.size()];
		materialNames = new JLabel[MaterialState.materialLibrary.size()];
		materialRatings = new JLabel[MaterialState.materialLibrary.size()];
		materialColours = new JLabel[MaterialState.materialLibrary.size()];
		
		shiftUp = new ArrayList<JComponent>();
		shiftDown = new ArrayList<JComponent>();
		shiftLeft = new ArrayList<JComponent>();
		shiftRight = new ArrayList<JComponent>();
	
		totalRating = 0;
		totalToxic = 5;
		totalNegative = 1;
		totalDamage = 5;
		
		moveAmount = 0;
	}
	

	@Override
	public void addJComponents() {
		panel = new JPanel(null);
		panel.setBackground(new Color(27, 62, 90));
		panel.setBounds(0, 0, WIDTH, HEIGHT);
		
		reportPanel = new JPanel();
		reportPanel.setBounds(0, 0, WIDTH, HEIGHT);
		reportPanel.setLayout(new GridLayout(4, 4, 0, 0));
		panel.add(reportPanel);
		materialImageTitle = new JLabel();
		materialImageTitle.setText("Material Image");
		materialImageTitle.setOpaque(true);
		materialImageTitle.setHorizontalAlignment(SwingConstants.CENTER);
		materialImageTitle.setBackground(Color.WHITE);
		materialImageTitle.setBounds(10, 0, 219, 50);
		reportPanel.add(materialImageTitle);
		
		for (int totalImage = 0; totalImage < MaterialState.materialLibrary.size(); totalImage++){
	
			materialImages[totalImage] = new JButton();
			materialImages[totalImage].setBounds(11, 60 + totalImage * 165, 219, 157);
			materialImages[totalImage].setHorizontalAlignment(SwingConstants.CENTER);
			reportPanel.add(materialImages[totalImage]);
			settingString = MaterialState.materialLibrary.get(totalImage).getIconPath();
			materialImages[totalImage].setIcon(new ImageIcon (new ImageIcon (settingString).getImage().getScaledInstance(219, 157, 0)));
			materialImages[totalImage].setOpaque(true);
			materialImages[totalImage].setBackground(Color.white);
		}

		materialNameTitle = new JLabel();
		materialNameTitle.setText("Material Name");
		materialNameTitle.setOpaque(true);
		materialNameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		materialNameTitle.setBackground(Color.WHITE);
		materialNameTitle.setBounds(240, 0, 159, 50);
		reportPanel.add(materialNameTitle);
		
		materialNames = new JLabel[MaterialState.materialLibrary.size()];
		for (int totalNames = 0; totalNames < MaterialState.materialLibrary.size(); totalNames++){
			settingString = MaterialState.materialLibrary.get(totalNames).getName();
			materialNames[totalNames] = new JLabel(settingString);
			materialNames[totalNames].setBounds(240, 60 + totalNames * 165, 159, 157);
			materialNames[totalNames].setFont(new Font(null, Font.ITALIC, 20));
			materialNames[totalNames].setHorizontalAlignment(SwingConstants.CENTER);
			reportPanel.add(materialNames[totalNames]);
			materialNames[totalNames].setOpaque(true);
			materialNames[totalNames].setBackground(Color.white);
			}
		
		materialRatingTitle = new JLabel();
		materialRatingTitle.setText("Material Rating");
		materialRatingTitle.setOpaque(true);
		materialRatingTitle.setHorizontalAlignment(SwingConstants.CENTER);
		materialRatingTitle.setBackground(Color.WHITE);
		materialRatingTitle.setBounds(408, 0, 124, 50);
		reportPanel.add(materialRatingTitle);
		
		materialRatings = new JLabel[MaterialState.materialLibrary.size()];
		for (int totalRatings = 0; totalRatings < MaterialState.materialLibrary.size(); totalRatings++){
			settingString = Integer.toString(MaterialState.materialLibrary.get(totalRatings).getDangerRating());
			materialRatings[totalRatings] = new JLabel(settingString);
			materialRatings[totalRatings].setBounds(408, 60 + totalRatings * 165, 124, 157);
			materialRatings[totalRatings].setFont(new Font(null, Font.ITALIC, 30));
			materialRatings[totalRatings].setHorizontalAlignment(SwingConstants.CENTER);
			reportPanel.add(materialRatings[totalRatings]);
			materialRatings[totalRatings].setOpaque(true);
			materialRatings[totalRatings].setBackground(Color.white);
			}
		
		materialColourTitle = new JLabel();
		materialColourTitle.setHorizontalAlignment(SwingConstants.CENTER);
		materialColourTitle.setText("Material Colour");
		materialColourTitle.setOpaque(true);
		materialColourTitle.setBackground(Color.WHITE);
		materialColourTitle.setBounds(543, 0, 256, 50);
		reportPanel.add(materialColourTitle);
		//Adding the colours for each material they chose
		materialColours = new JLabel[MaterialState.materialLibrary.size()];
		for (int totalColours = 0; totalColours < MaterialState.materialLibrary.size(); totalColours++){
			materialColours[totalColours] = new JLabel();
			settingString = Integer.toString(MaterialState.materialLibrary.get(totalColours).getDangerRating());
			if (Integer.valueOf(settingString) == 1)
				materialColours[totalColours].setIcon(new ImageIcon ("images/colour1.png"));
			else if (Integer.valueOf(settingString) == 2)
				materialColours[totalColours].setIcon(new ImageIcon ("images/colour2.png"));
			else if (Integer.valueOf(settingString) == 3)
				materialColours[totalColours].setIcon(new ImageIcon ("images/colour3.png"));
			else if (Integer.valueOf(settingString) == 4)
				materialColours[totalColours].setIcon(new ImageIcon ("images/colour4.png"));
			else if (Integer.valueOf(settingString) == 5)
				materialColours[totalColours].setIcon(new ImageIcon ("images/colour5.png"));
			
			materialColours[totalColours].setBounds(543, 60 + totalColours * 165, 256, 157);
			materialColours[totalColours].setFont(new Font(null, Font.ITALIC, 30));
			materialColours[totalColours].setHorizontalAlignment(SwingConstants.CENTER);

			reportPanel.add(materialColours[totalColours]);
			materialColours[totalColours].setBackground(Color.white);
		}
		
		report = new JScrollPane(reportPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		report.setBounds(27, 58, 824, 530);
		panel.add(report);
		
		wasteReduction = new JButton();
		wasteReduction.setText("Possible Alternatives");
		wasteReduction.setIcon(	new ImageIcon(new ImageIcon("images/possibleAlternatives.png").getImage().getScaledInstance(262, 42, 0)));
		wasteReduction.addActionListener(this);
		wasteReduction.setBounds(943, 710, 251, 42);
		panel.add(wasteReduction);
		
		exit = new JButton();
		exit.setIcon(new ImageIcon(new ImageIcon("images/exitProject.png").getImage().getScaledInstance(334, 102, 0)));
		exit.setText("Exit Project");
		exit.addActionListener(this);
		exit.setBounds(76, 599, 323, 102);
		panel.add(exit);
	
		getContentPane().add(panel);
		
		save = new JButton("Save Project");
		save.setIcon(new ImageIcon(new ImageIcon("images/saveProject.png").getImage().getScaledInstance(334, 102, 0)));
		save.setBounds(466, 599, 323, 102);
		panel.add(save);
		
		title = new JLabel("Project Report");
		title.setFont(new Font("times new roman", Font.ITALIC, 20));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(new Color(68, 225, 212));
		title.setBounds(344, 22, 174, 25);
		panel.add(title);
		
		impactPanel = new JPanel();
		impactPanel.setBounds(886, 58, 365, 530);
		panel.add(impactPanel);

		impactDescription = new JTextArea();
		impactDescription.setText(testImpact);
		impactDescription.setLineWrap(true);
		impactDescription.setWrapStyleWord(true);
		impactDescription.setBounds(10, 10, 345, 530);
		impactPanel.add(impactDescription);
		
		projectImpact = new JLabel("Project Impact");
		projectImpact.setFont(new Font("times new roman", Font.ITALIC, 20));
		projectImpact.setForeground(new Color(68, 225, 212));
		projectImpact.setBounds(999, 21, 151, 27);
		panel.add(projectImpact);
		
		projectRating = new JLabel("Project Colour:");
		projectRating.setForeground(new Color(68, 225, 212));
		projectRating.setFont(new Font("times new roman", Font.ITALIC, 20));
		projectRating.setBounds(935, 625, 151, 36);
		panel.add(projectRating);
		
		
		for (int i = 0; i < MaterialState.materialLibrary.size(); i++){
			totalRating = totalRating + Integer.valueOf(MaterialState.materialLibrary.get(i).getDangerRating());
		rating = new JLabel();
		rating.setHorizontalAlignment(SwingConstants.CENTER);
		rating.setForeground(new Color(68, 225, 212));
		rating.setFont(new Font("times new roman", Font.ITALIC, 50));
		
		//Determining the overall project colour
		if (Math.round(totalRating / MaterialState.materialLibrary.size()) == 1)
			projectColour = ("images/colour1.png");
		else if (Math.round(totalRating / MaterialState.materialLibrary.size()) == 2)
			projectColour = ("images/colour2.png");
		else if (Math.round(totalRating / MaterialState.materialLibrary.size()) == 3)
			projectColour = ("images/colour3.png");
		else if (Math.round(totalRating / MaterialState.materialLibrary.size()) == 4)
			projectColour = ("images/colour4.png");
		else if (Math.round(totalRating / MaterialState.materialLibrary.size()) == 5)
			projectColour = ("images/colour5.png");
		
		rating.setIcon(new ImageIcon (projectColour));
		rating.setBounds(1072, 615, 122, 62);
		rating.setOpaque(true);
		panel.add(rating);
		}
		
		addinfoComponents();
	}
	
	private void addinfoComponents() {
		nameLabel = new JLabel("Name: ");
		nameLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		nameLabel.setForeground(new Color(68, 225, 212));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 50, 350, 50);
		shiftLeft.add(nameLabel);
		panel.add(nameLabel);
		
		nameField = new JTextField();
		nameField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		nameField.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 100, 350, 50);
		shiftLeft.add(nameField);
		panel.add(nameField);
		
		typeLabel = new JLabel("Material Type: ");
		typeLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		typeLabel.setForeground(new Color(68, 225, 212));
		typeLabel.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 150, 350, 50);
		typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(typeLabel);
		panel.add(typeLabel);
		
		typeField = new JTextField();
		typeField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		typeField.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 200, 350, 50);
		shiftLeft.add(typeField);
		panel.add(typeField);
		
		brandLabel = new JLabel("Brand: ");
		brandLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		brandLabel.setForeground(new Color(68, 225, 212));
		brandLabel.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 250, 350, 50);
		brandLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(brandLabel);
		panel.add(brandLabel);
		
		brandField = new JTextField();
		brandField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		brandField.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 300, 350, 50);
		shiftLeft.add(brandField);
		panel.add(brandField);
		
		storageLabel = new JLabel("Storage Condition: ");
		storageLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		storageLabel.setForeground(new Color(68, 225, 212));
		storageLabel.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 350, 350, 50);
		storageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(storageLabel);
		panel.add(storageLabel);
		
		storageField = new JTextField();
		storageField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		storageField.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 400, 350, 50);
		shiftLeft.add(storageField);
		panel.add(storageField);
		
		hyperToPurchaseLabel = new JLabel("Purchase Link: ");
		hyperToPurchaseLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		hyperToPurchaseLabel.setForeground(new Color(68, 225, 212));
		hyperToPurchaseLabel.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 450, 350, 50);
		hyperToPurchaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(hyperToPurchaseLabel);
		panel.add(hyperToPurchaseLabel);
		
		hyperToPurchaseField = new JTextField();
		hyperToPurchaseField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		hyperToPurchaseField.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 500, 350, 50);
		shiftLeft.add(hyperToPurchaseField);
		panel.add(hyperToPurchaseField);
		
		hyperToMSDLabel = new JLabel("MSD Link: ");
		hyperToMSDLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		hyperToMSDLabel.setForeground(new Color(68, 225, 212));
		hyperToMSDLabel.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 550, 350, 50);
		hyperToMSDLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(hyperToMSDLabel);
		panel.add(hyperToMSDLabel);
		
		hyperToMSDField = new JTextField();
		hyperToMSDField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		hyperToMSDField.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 600, 350, 50);
		shiftLeft.add(hyperToMSDField);
		panel.add(hyperToMSDField);
		
		hazardousRating = new JLabel("Environmental Hazard Rating (0-10):");
		hazardousRating.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		hazardousRating.setForeground(new Color(68, 225, 212));
		hazardousRating.setBounds(500 + SHIFT_AMOUNT*SHIFT_SPEED, 675, 500, 50);
		hazardousRating.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(hazardousRating);
		panel.add(hazardousRating);
		
		hazardousRatingField = new JTextField();
		hazardousRatingField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		hazardousRatingField.setBounds(500 + 500 + SHIFT_AMOUNT*SHIFT_SPEED, 675, 50, 50);
		hazardousRatingField.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent event) { 
		    	
		    	// limit text field to 3 characters
		        if (hazardousRatingField.getText().length() >= 2) 
		        	// remove excess characters
		        	event.consume(); 
		        
		    }  
		});
		shiftLeft.add(hazardousRatingField);
		panel.add(hazardousRatingField);
		
		environmentalLabel = new JLabel("Environmental Concerns");
		environmentalLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		environmentalLabel.setForeground(new Color(68, 225, 212));
		environmentalLabel.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 50, 350, 50);
		environmentalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(environmentalLabel);
		panel.add(environmentalLabel);
		
		environmentalField = new JTextArea();
		environmentalField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 20));
		environmentalField.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 100, 350, 150);
		environmentalField.setLineWrap(true);
		environmentalField.setWrapStyleWord(true);
		shiftLeft.add(environmentalField);
		JScrollPane environmentalFieldScroll = new JScrollPane(environmentalField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		environmentalFieldScroll.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 100, 350, 150);
		shiftLeft.add(environmentalFieldScroll);
		panel.add(environmentalFieldScroll); 

		firstAidLabel = new JLabel("Stability And React First Aid Measures");
		firstAidLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 18));
		firstAidLabel.setForeground(new Color(68, 225, 212));
		firstAidLabel.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 250, 350, 50);
		firstAidLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(firstAidLabel);
		panel.add(firstAidLabel);
		
		firstAidField = new JTextArea();
		firstAidField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 20));
		firstAidField.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 300, 350, 150);
		firstAidField.setLineWrap(true);
		firstAidField.setWrapStyleWord(true);
		shiftLeft.add(firstAidField);
		JScrollPane firstAidFieldScroll = new JScrollPane(firstAidField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		firstAidFieldScroll.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 300, 350, 150);
		shiftLeft.add(firstAidFieldScroll);
		panel.add(firstAidFieldScroll); 
		
		dangersLabel = new JLabel("Dangers And Health Impacts");
		dangersLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 24));
		dangersLabel.setForeground(new Color(68, 225, 212));
		dangersLabel.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 450, 350, 50);
		dangersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shiftLeft.add(dangersLabel);
		panel.add(dangersLabel);
		
		dangersField = new JTextArea();
		dangersField.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 20));
		dangersField.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 500, 350, 150);
		dangersField.setLineWrap(true);
		dangersField.setWrapStyleWord(true);
		shiftLeft.add(dangersField);
		JScrollPane dangersFieldScroll = new JScrollPane(dangersField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dangersFieldScroll.setBounds(880 + SHIFT_AMOUNT*SHIFT_SPEED, 500, 350, 150);
		shiftLeft.add(dangersFieldScroll);
		panel.add(dangersFieldScroll);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {

		if(event.getSource() == exit) {
			//Closes the screen and opens DatabaseState
			new DatabaseState();
			this.dispose();
		}
		
		if(event.getSource() == wasteReduction) {

			new PossibleAlternatives();
			this.dispose();
			
		}
		
		if(event.getSource() == save) {
			//Saves the project to the database
			new DatabaseState();
			this.dispose();
			
		}
	}

}
