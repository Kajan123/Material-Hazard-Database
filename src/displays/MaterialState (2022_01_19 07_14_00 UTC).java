package displays;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import objects.Materials;
import utils.CustomizationTool;
import utils.SortAToZ;
import utils.SortByRecentlyAdded;
import utils.SortZToA;

public class MaterialState extends State {
	
	// Final Variables
	private static final int SHIFT_AMOUNT = 60;
	private static final int SHIFT_SPEED = 14;

	// JComponents
	private JPanel panel, materialCartPanel, materialListPanel;
	private JScrollPane materialCart, materialList;
	private JTextField searchBar, addOrRemoveAmount, nameField, typeField, brandField, storageField, hyperToPurchaseField, 
		hyperToMSDField, hazardousRatingField; 
	private JTextArea environmentalField, firstAidField, dangersField;
	private JLabel materialImage, addOrRemoveLabel, filterLabel,
		nameLabel, typeLabel, brandLabel, storageLabel, hyperToPurchaseLabel, hyperToMSDLabel, environmentalLabel,
		firstAidLabel, dangersLabel, cartIcon, hazardousRating, cartLabel;
	private JButton backToMaterialSelection, addNewMaterial, addMaterial, removeMaterial, 
		search, sortAZ, sortZA, sortRecentlyAdded, proceedToReport, info, newIcon, save, delete, clear,
		toPurchase, toMSD;
	
	// Arrays/Lists
	public static ArrayList<Materials> materialLibrary;
	private ArrayList<Materials> materialStorage;
	private ArrayList<Materials> materialContainer;
	private ArrayList<JButton> materialButtonContainer;
	private ArrayList<JButton> materialButtonCart;
	private ArrayList<JComponent> shiftUp;
	private ArrayList<JComponent> shiftDown;
	private ArrayList<JComponent> shiftLeft;
	private ArrayList<JComponent> shiftRight;

	// Other fields and variables
	private Materials selectedMaterial;
	private Timer componentForwardTimer, componentBackwardTimer;
	private int moveAmount, inputOrder;
	private String inputImagePath;

	@Override
	public void init() {

		materialContainer = new ArrayList<Materials>();
		materialButtonContainer = new ArrayList<JButton>();
		materialButtonCart = new ArrayList<JButton>();
		materialLibrary = new ArrayList<Materials>();
		materialStorage = new ArrayList<Materials>();
		shiftUp = new ArrayList<JComponent>();
		shiftDown = new ArrayList<JComponent>();
		shiftLeft = new ArrayList<JComponent>();
		shiftRight = new ArrayList<JComponent>();

		selectedMaterial = null;
		
		componentForwardTimer = new Timer(1, this);
		componentBackwardTimer = new Timer(1, this);
		
		moveAmount = 0;
		inputOrder = 0;
		
	}

	@Override
	public void addJComponents() {

		panel = new JPanel(null);
		panel.setBackground(new Color(27, 62, 90));
		panel.setBounds(0, 0, WIDTH, HEIGHT);

		addMainScreenComponents();
		
		addInfoComponents();

		add(panel);

		loadMaterials();
		loadCart();
		addMenuBar();

	}
	
	public void addMainScreenComponents() {
		
		materialCartPanel = new JPanel();
		materialCartPanel.setLayout(new BoxLayout(materialCartPanel, BoxLayout.X_AXIS));
		materialCartPanel.setBounds(0, 0, WIDTH, HEIGHT);
		
		materialCart = new JScrollPane(materialCartPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		materialCart.setBounds(190, 30, 930, 95);
		shiftUp.add(materialCart);
		panel.add(materialCart);
		
		clear = new JButton(new ImageIcon(new ImageIcon("images/clear.png").getImage().getScaledInstance(85, 85, 0)));
		clear.addActionListener(this);
		clear.setBounds(1135, 30, 95, 95);
		shiftUp.add(clear);
		panel.add(clear);

		materialListPanel = new JPanel();
		materialListPanel.setLayout(new BoxLayout(materialListPanel, BoxLayout.Y_AXIS));
		materialListPanel.setBounds(0, 0, WIDTH, HEIGHT);

		materialList = new JScrollPane(materialListPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		materialList.setBounds(40, 225, 400, 400);
		shiftLeft.add(materialList);
		panel.add(materialList);
		
		cartLabel = new JLabel("Material Cart:");
		cartLabel.setFont(new Font("Gill Sans MT Condensed", Font.BOLD, 20));
		cartLabel.setForeground(new Color(68, 225, 212));
		cartLabel.setBounds(40, 40, 200, 20);
		shiftUp.add(cartLabel);
		panel.add(cartLabel);
		
		cartIcon = new JLabel(new ImageIcon(new ImageIcon("images/library.png").getImage().getScaledInstance(50, 50, 0)));
		cartIcon.setBounds(80, 70, 50, 50);
		shiftUp.add(cartIcon);
		panel.add(cartIcon);
		
		searchBar = new JTextField();
		searchBar.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		searchBar.setBounds(40, 150, 340, 50);
		shiftLeft.add(searchBar);
		panel.add(searchBar);

		info = new JButton(new ImageIcon(new ImageIcon("images/info.png").getImage().getScaledInstance(50, 47, 0)));
		info.addActionListener(this);
		info.setBounds(825, 145, 60, 60);
		panel.add(info);
		
		newIcon = new JButton(new ImageIcon(new ImageIcon("images/edit pencil.png").getImage().getScaledInstance(50, 47, 0)));
		newIcon.addActionListener(this);
		newIcon.setBounds(825 - 7*SHIFT_AMOUNT, 145, 60, 60);
		newIcon.setVisible(false);
		panel.add(newIcon);

		materialImage = new JLabel();
		materialImage.setBackground(Color.white);
		materialImage.setOpaque(true);
		materialImage.setBounds(480, 150, 400, 400);
		panel.add(materialImage);
		
		search = new JButton(new ImageIcon(new ImageIcon("images/search.png").getImage().getScaledInstance(50, 47, 0)));
		search.addActionListener(this);
		search.setBounds(385, 145, 60, 60);
		shiftLeft.add(search);
		panel.add(search);

		addOrRemoveLabel = new JLabel("Add Amount(g or ml):");
		addOrRemoveLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 26));
		addOrRemoveLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addOrRemoveLabel.setForeground(new Color(68, 225, 212));
		addOrRemoveLabel.setBounds(930, 155, 300, 50);
		shiftRight.add(addOrRemoveLabel);
		panel.add(addOrRemoveLabel);

		addMaterial = new JButton(
				new ImageIcon(new ImageIcon("images/save amount.png").getImage().getScaledInstance(125, 125, 0)));
		addMaterial.addActionListener(this);
		addMaterial.setBounds(920, 300, 140, 140);
		shiftRight.add(addMaterial);
		panel.add(addMaterial);

		removeMaterial = new JButton(
				new ImageIcon(new ImageIcon("images/removeLibrary.png").getImage().getScaledInstance(125, 125, 0)));
		removeMaterial.addActionListener(this);
		removeMaterial.setBounds(1100, 300, 140, 140);
		shiftRight.add(removeMaterial);
		panel.add(removeMaterial);

		addOrRemoveAmount = new JTextField();
		addOrRemoveAmount.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		addOrRemoveAmount.setBounds(920, 225, 320, 50);
		shiftRight.add(addOrRemoveAmount);
		panel.add(addOrRemoveAmount);

		addNewMaterial = new JButton(
				new ImageIcon(new ImageIcon("images/addNewMaterial.png").getImage().getScaledInstance(387, 60, 0)));
		addNewMaterial.setBounds(40, 650, 400, 75);
		addNewMaterial.addActionListener(this);
		shiftLeft.add(addNewMaterial);
		panel.add(addNewMaterial);

		sortAZ = new JButton(
				new ImageIcon(new ImageIcon("images/sortAZ.png").getImage().getScaledInstance(105, 105, 0)));
		sortAZ.setBounds(480, 610, 120, 120);
		sortAZ.addActionListener(this);
		shiftDown.add(sortAZ);
		panel.add(sortAZ);

		sortZA = new JButton(
				new ImageIcon(new ImageIcon("images/sortZA.png").getImage().getScaledInstance(105, 105, 0)));
		sortZA.setBounds(620, 610, 120, 120);
		sortZA.addActionListener(this);
		shiftDown.add(sortZA);
		panel.add(sortZA);

		sortRecentlyAdded = new JButton(
				new ImageIcon(new ImageIcon("images/sortRecentlyAdded.png").getImage().getScaledInstance(105, 105, 0)));
		sortRecentlyAdded.setBounds(760, 610, 120, 120);
		sortRecentlyAdded.addActionListener(this);
		shiftDown.add(sortRecentlyAdded);
		panel.add(sortRecentlyAdded);

		filterLabel = new JLabel("Filters:");
		filterLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		filterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		filterLabel.setForeground(new Color(68, 225, 212));
		filterLabel.setBounds(480, 565, 400, 50);
		shiftDown.add(filterLabel);
		panel.add(filterLabel);

		proceedToReport = new JButton(
				new ImageIcon(new ImageIcon("images/proceed.png").getImage().getScaledInstance(307, 62, 0)));
		proceedToReport.setBounds(920, 650, 320, 75);
		proceedToReport.addActionListener(this);
		shiftDown.add(proceedToReport);
		panel.add(proceedToReport);
		
		toPurchase = new JButton(
				new ImageIcon(new ImageIcon("images/purchase material.png").getImage().getScaledInstance(307, 62, 0)));
		toPurchase.setBounds(920, 460, 320, 75);
		toPurchase.addActionListener(this);
		shiftDown.add(toPurchase);
		panel.add(toPurchase);
		
		toMSD = new JButton(
				new ImageIcon(new ImageIcon("images/msds link.png").getImage().getScaledInstance(307, 62, 0)));
		toMSD.setBounds(920, 555, 320, 75);
		toMSD.addActionListener(this);
		shiftDown.add(toMSD);
		panel.add(toMSD);
		
	}
	
	private void addInfoComponents() {
		
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
		
		save = new JButton(
				new ImageIcon(new ImageIcon("images/material save.png").getImage().getScaledInstance(170, 65, 0)));
		save.addActionListener(this);
		save.setBounds(60, 590 + SHIFT_AMOUNT*SHIFT_SPEED/2, 180, 75);
		shiftUp.add(save);
		panel.add(save);
		
		delete = new JButton(
				new ImageIcon(new ImageIcon("images/material_delete.png").getImage().getScaledInstance(170, 65, 0)));
		delete.addActionListener(this);
		delete.setBounds(280, 590 + SHIFT_AMOUNT*SHIFT_SPEED/2, 180, 75);
		shiftUp.add(delete);
		panel.add(delete);
		
		backToMaterialSelection = new JButton(
				new ImageIcon(new ImageIcon("images/back to materials.png").getImage().getScaledInstance(390, 62, 0)));
		backToMaterialSelection.setBounds(60, 40 - SHIFT_AMOUNT*SHIFT_SPEED/2, 400, 75);
		backToMaterialSelection.addActionListener(this);
		shiftDown.add(backToMaterialSelection);
		panel.add(backToMaterialSelection);
		
	}

	private void loadMaterials() {

		// loads all the files and puts their name in a list
		File materialFolder = new File("materials");
		for (int i = 0; i < materialFolder.listFiles().length; i++) {

			File material = new File(materialFolder.listFiles()[i].getAbsolutePath());

			String[] information = new String[14];

			Scanner reader = null;

			try {

				reader = new Scanner(material);

				int index = 0;

				while (reader.hasNext()) {

					information[index] = reader.nextLine();
					index++;
				}

				Materials newMaterial = new Materials(information[0], information[1], information[2], information[3],
						information[4], information[5], new JTextArea(information[6]), new JTextArea(information[7]), 
						new JTextArea(information[8]), information[9],
						Integer.parseInt(information[10]), material.getAbsolutePath(), Integer.parseInt(information[11]),
						Integer.parseInt(information[12]), information[13]);
				
				if(Integer.parseInt(information[10]) > inputOrder) {
					inputOrder = Integer.parseInt(information[10])+1;
					
				}
				
				materialContainer.add(newMaterial);
				materialStorage.add(newMaterial);
				

			} catch (FileNotFoundException e) {

				e.printStackTrace();

			}

		}

		for(Materials material: materialContainer)
			
			addMaterialToScrollPanel(material);

		repaint();

	}

	private void loadCart() {
		
		for(Materials material: materialStorage) {
			
			if(DatabaseState.currentProject.getMaterials().containsKey(material.getName())) {
				
				material.setAmount(DatabaseState.currentProject.getMaterials().get(material.getName()));
				
				materialLibrary.add(material);
				
				JButton selectedMaterialButton = new JButton(
						new ImageIcon(material.getIcon().getImage().getScaledInstance(70, 70, 0)));
				selectedMaterialButton.setBounds(0, 0, 75, 75);
				selectedMaterialButton.setMaximumSize(selectedMaterialButton.getSize());
				selectedMaterialButton.setMinimumSize(selectedMaterialButton.getSize());
				selectedMaterialButton.setPreferredSize(selectedMaterialButton.getSize());
				selectedMaterialButton.addActionListener(this);

				materialButtonCart.add(selectedMaterialButton);
				materialCartPanel.add(selectedMaterialButton);

			}
			
		}
		
		materialCartPanel.revalidate();
		materialCartPanel.repaint();
		
	}
	
	private void addMaterialToScrollPanel(Materials material) {

		JButton materialButton = new JButton(material.getName());
		materialButton.setBounds(0, 0, 380, 100);
		materialButton.setMaximumSize(materialButton.getSize());
		materialButton.setMinimumSize(materialButton.getSize());
		materialButton.setPreferredSize(materialButton.getSize());
		materialButton.setIcon(
				new ImageIcon(new ImageIcon("images/buttonBackground.png").getImage().getScaledInstance(380, 100, 0)));
		materialButton.setHorizontalTextPosition(SwingConstants.CENTER);
		materialButton.setForeground(Color.white);
		materialButton.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 28));
		materialButton.setOpaque(true);
		materialButton.setBorderPainted(false);
		materialButton.addActionListener(this);
		materialButtonContainer.add(materialButton);
		materialListPanel.add(materialButton);

	}
	
	
	private void search() {
		
		if (searchBar.getText().length() > 0) {

			ArrayList<Materials> searchedMaterialContainer = new ArrayList<Materials>();

			for (JButton materialButton : materialButtonContainer) {

				materialListPanel.remove(materialButton);

			}

			materialButtonContainer.clear();

			for (Materials material : materialStorage) {

				if (material.getName().toLowerCase().contains(searchBar.getText().toLowerCase())) {

					searchedMaterialContainer.add(material);

				}

			}
			
			materialContainer = searchedMaterialContainer;
			
			for(Materials material: materialContainer) {
				
				addMaterialToScrollPanel(material);
				
			}
			
			materialList.revalidate();
			materialList.repaint();

		} else {

			for (JButton materialButton : materialButtonContainer) {

				materialListPanel.remove(materialButton);

			}
			
			materialButtonContainer.clear();
			
			materialContainer = materialStorage;
			
			for(Materials material: materialContainer) {
				
				addMaterialToScrollPanel(material);
				
			}
			
			materialList.revalidate();
			materialList.repaint();

		}

	}

	@Override
	public void actionPerformed(ActionEvent event) {

		for (int i = 0; i < materialButtonContainer.size(); i++) {

			if (event.getSource() == materialButtonContainer.get(i)) {

				selectedMaterial = materialContainer.get(i);
				addOrRemoveAmount.setText(selectedMaterial.getAmount() + "");
				materialImage.setIcon(
						new ImageIcon(materialContainer.get(i).getIcon().getImage().getScaledInstance(400, 400, 0)));
				repaint();

			}

		}

		for (int i = 0; i < materialLibrary.size(); i++) {

			if (event.getSource() == materialButtonCart.get(i)) {

				selectedMaterial = materialLibrary.get(i);
				addOrRemoveAmount.setText(selectedMaterial.getAmount() + "");
				materialImage.setIcon(
						new ImageIcon(materialLibrary.get(i).getIcon().getImage().getScaledInstance(400, 400, 0)));

				repaint();

			}

		}
		
		if (event.getSource() == componentForwardTimer) {
			
			for(JComponent component: shiftUp) {
				
				component.setBounds(component.getX(), component.getY() - SHIFT_SPEED/2, component.getWidth(), component.getHeight());
				
			}
			for(JComponent component: shiftDown) {
				
				component.setBounds(component.getX(), component.getY() + SHIFT_SPEED/2, component.getWidth(), component.getHeight());
				
			}
			for(JComponent component: shiftLeft) {
				
				component.setBounds(component.getX() - SHIFT_SPEED, component.getY(), component.getWidth(), component.getHeight());
				
			}
			for(JComponent component: shiftRight) {
				
				component.setBounds(component.getX() + SHIFT_SPEED, component.getY(), component.getWidth(), component.getHeight());
				
			}
			
			materialImage.setBounds(materialImage.getX() - 7, materialImage.getY(), materialImage.getWidth(), materialImage.getHeight());
			
			repaint();
			
			moveAmount--;
			
			if(moveAmount == 0) {
				
				componentForwardTimer.stop();
				newIcon.setVisible(true);
				
			}
			
		} else if (event.getSource() == componentBackwardTimer) {
			
			for(JComponent component: shiftUp) {
				
				component.setBounds(component.getX(), component.getY() + SHIFT_SPEED/2, component.getWidth(), component.getHeight());
				
			}
			for(JComponent component: shiftDown) {
				
				component.setBounds(component.getX(), component.getY() - SHIFT_SPEED/2, component.getWidth(), component.getHeight());
				
			}
			for(JComponent component: shiftLeft) {
				
				component.setBounds(component.getX() + SHIFT_SPEED, component.getY(), component.getWidth(), component.getHeight());
				
			}
			for(JComponent component: shiftRight) {
				
				component.setBounds(component.getX() - SHIFT_SPEED, component.getY(), component.getWidth(), component.getHeight());
				
			}
			
			materialImage.setBounds(materialImage.getX() + 7, materialImage.getY(), materialImage.getWidth(), materialImage.getHeight());
			
			repaint();
			
			moveAmount--;
			
			if(moveAmount == 0) {
				
				componentBackwardTimer.stop();
				info.setVisible(true);
				newIcon.setVisible(false);
				
			}
			
		} else if (event.getSource() == addMaterial) {
			
			if (selectedMaterial != null) {
				
				boolean allInteger = true;

				for(int index = 0; index < addOrRemoveAmount.getText().length(); index++) {
					
					if((int)addOrRemoveAmount.getText().charAt(index) < 48 || (int)addOrRemoveAmount.getText().charAt(index) > 57) {
						allInteger = false;
					}
					
				}
				
				if(!allInteger) {

					JOptionPane.showMessageDialog(null,
							"Please enter a valid positive integer!\n\n"
									+ "click 'ok' to continue...",
							"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
					
					return;
					
				}

				if(addOrRemoveAmount.getText().equals("0") || addOrRemoveAmount.getText().length() == 0) {
					
					JOptionPane.showMessageDialog(null,
							"Please enter a valid positive integer!\n\n"
									+ "click 'ok' to continue...",
							"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
					
					return;
					
				}
				
				selectedMaterial.setAmount(Integer.parseInt(addOrRemoveAmount.getText()));
				
				if(!materialLibrary.contains(selectedMaterial)) {
					
					materialLibrary.add(selectedMaterial);
					
					JButton selectedMaterialButton = new JButton(
							new ImageIcon(selectedMaterial.getIcon().getImage().getScaledInstance(70, 70, 0)));
					selectedMaterialButton.setBounds(0, 0, 75, 75);
					selectedMaterialButton.setMaximumSize(selectedMaterialButton.getSize());
					selectedMaterialButton.setMinimumSize(selectedMaterialButton.getSize());
					selectedMaterialButton.setPreferredSize(selectedMaterialButton.getSize());
					selectedMaterialButton.addActionListener(this);

					materialButtonCart.add(selectedMaterialButton);
					materialCartPanel.add(selectedMaterialButton);

					materialCartPanel.revalidate();
					materialCartPanel.repaint();
					
				}

			} else // display message dialogue for invalid input
				JOptionPane.showMessageDialog(null,
						"Please select a material to add!\n\n"
								+ "click 'ok' to continue...",
						"INVALID INPUT", JOptionPane.WARNING_MESSAGE);

		}  else if (event.getSource() == removeMaterial) {
			
			for(int i = 0; i < materialLibrary.size(); i++) {
				
				if(materialLibrary.get(i) == selectedMaterial) {
					
					selectedMaterial.setAmount(0);
					addOrRemoveAmount.setText(selectedMaterial.getAmount() + "");
					materialLibrary.remove(i);
					materialButtonCart.remove(i);
					materialCartPanel.remove(i);

					materialCartPanel.revalidate();
					materialCartPanel.repaint();
					
				}
				
			}
			
		} else if (event.getSource() == sortAZ) {

			for (JButton materialButton : materialButtonContainer) {

				materialListPanel.remove(materialButton);

			}

			materialButtonContainer.clear();

			Collections.sort(materialContainer, new SortAToZ());

			for(Materials material: materialContainer)
				
				addMaterialToScrollPanel(material);

			materialListPanel.revalidate();
			materialListPanel.repaint();

		} else if (event.getSource() == sortZA) {

			for (JButton materialButton : materialButtonContainer) {

				materialListPanel.remove(materialButton);

			}

			materialButtonContainer.clear();

			Collections.sort(materialContainer, new SortZToA());

			for(Materials material: materialContainer)
				
				addMaterialToScrollPanel(material);

			materialListPanel.revalidate();
			materialListPanel.repaint();

		} else if (event.getSource() == sortRecentlyAdded) {

			for (JButton materialButton : materialButtonContainer) {

				materialListPanel.remove(materialButton);

			}

			materialButtonContainer.clear();

			Collections.sort(materialContainer, new SortByRecentlyAdded());

			for(Materials material: materialContainer)
				
				addMaterialToScrollPanel(material);

			materialListPanel.revalidate();
			materialListPanel.repaint();

		} else if (event.getSource() == search) {

			search();

		} else if (event.getSource() == clear) {

			materialCartPanel.removeAll();
			materialButtonCart.clear();
			
			for(Materials material: materialLibrary) {
				
				material.setAmount(0);
				
			}
			
			materialLibrary.clear();
			repaint();
			
		} else if (event.getSource() == info) {

			newIcon.setVisible(false);
			
			if(selectedMaterial == null) {
				
				// display message dialogue for invalid input
				JOptionPane.showMessageDialog(null,
						"Please select a material first!\n\n"
								+ "click 'ok' to continue...",
						"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
				
				return;
				
			}
			
			componentForwardTimer.start();
			moveAmount = SHIFT_AMOUNT;
			info.setVisible(false);
			
			nameField.setText(selectedMaterial.getName());
			typeField.setText(selectedMaterial.getType());
			brandField.setText(selectedMaterial.getBrand());
			storageField.setText(selectedMaterial.getStorageCondition());
			hyperToPurchaseField.setText(selectedMaterial.getHyperlinkToPurchase());
			hyperToMSDField.setText(selectedMaterial.getHyperlinkToMSD());
			environmentalField.setText(selectedMaterial.getEnvironmentalConcerns().getText());
			firstAidField.setText(selectedMaterial.getStabilityAndReactFirstAidMeasures().getText());
			dangersField.setText(selectedMaterial.getDangers().getText());
			hazardousRatingField.setText(selectedMaterial.getDangerRating() + "");

		} else if (event.getSource() == newIcon) {

			// attach a file chooser to the button with file filters
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("jpg files", 
					"jpg", "png");
			chooser.setFileFilter(fileFilter);
			
			// disable select options for other file types besides jpg
			chooser.setAcceptAllFileFilterUsed(false);
			
			// open the file chooser and store the file type as an integer
			int selectedFile = chooser.showOpenDialog(this);

			// check if file is approved by the filter
			if (selectedFile == JFileChooser.APPROVE_OPTION) {

				File inputFile = chooser.getSelectedFile();
				inputImagePath = inputFile.getAbsolutePath();

				// try and catch to see if file being read in exist
				try {

					Image loadedImage = ImageIO.read(inputFile);
					
					// the input image is a resized version of the loaded image to fit the button size
					materialImage.setIcon(
							new ImageIcon(loadedImage.getScaledInstance(400, 400, 0)));
					
					selectedMaterial.setIconPath(inputFile.getAbsolutePath());
					// repaint the panel in case image change didn't update
					materialImage.repaint();

				} catch (IOException error) {

					System.out.println("input file does not exsist");

				}

			} 

		} else if (event.getSource() == addNewMaterial) {

			materialImage.setIcon(null);
			
			componentForwardTimer.start();
			moveAmount = SHIFT_AMOUNT;
			info.setVisible(false);
			
			nameField.setText("");
			typeField.setText("");
			brandField.setText("");
			storageField.setText("");
			hyperToPurchaseField.setText("");
			hyperToMSDField.setText("");
			environmentalField.setText("");
			firstAidField.setText("");
			dangersField.setText("");
			
			selectedMaterial = new Materials("", "", "", "", "", "", 
					environmentalField, firstAidField, dangersField, "images/tempImage.jpg", inputOrder, null, 0, 0, "");

		} else if (event.getSource() == backToMaterialSelection) {

			if(nameField.getText().length() == 0) {
				
				selectedMaterial = null;
				
			}
			
			newIcon.setVisible(false);
			
			componentBackwardTimer.start();
			moveAmount = SHIFT_AMOUNT;

		} else if (event.getSource() == toPurchase) {

			CustomizationTool.openWebPage(hyperToPurchaseField.getText());

		} else if (event.getSource() == toMSD) {

			CustomizationTool.openWebPage(hyperToMSDField.getText());

		} else if(event.getSource() == save) {
			
			if(nameField.getText().length() == 0 || hazardousRatingField.getText().length() == 0) {
				
				// display message dialogue for invalid input
				JOptionPane.showMessageDialog(null,
						"Please validate your input\n"
						+ "The name field and hazardous rating cannot be empty!\n\n"
								+ "click 'ok' to continue...",
						"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
				
				return;
				
			}
			
			for(char character: hazardousRatingField.getText().toCharArray()) {
				
				if((int)character < 48 || (int)character > 57) {
					
					// display message dialogue for invalid input
					JOptionPane.showMessageDialog(null,
							"Environmental Hazardous Rating can only be an integer between 0 - 10\n"
							+ "Revalidate your input!\n\n"
									+ "click 'ok' to continue...",
							"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
					
					return;
					
				}
				
			}
			
			if(Integer.parseInt(hazardousRatingField.getText()) > 10) {
				
				// display message dialogue for invalid input
				JOptionPane.showMessageDialog(null,
						"Environmental Hazardous Rating can only be an integer between 0 - 10\n"
						+ "Revalidate your input!\n\n"
								+ "click 'ok' to continue...",
						"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
				
				return;
				
			}
			
			newIcon.setVisible(false);
			
			File material;
			
			if(selectedMaterial.getPath() == null) {
				material = new File("materials/" + nameField.getText());
			} else {
				material = new File(selectedMaterial.getPath());
			}
			
			try {
				
				selectedMaterial.setName(nameField.getText());
				selectedMaterial.setType(typeField.getText());
				selectedMaterial.setBrand(brandField.getText());
				selectedMaterial.setStorageCondition(storageField.getText());
				selectedMaterial.setHyperlinkToPurchase(hyperToPurchaseField.getText());
				selectedMaterial.setHyperlinkToMSD(hyperToMSDField.getText());
				selectedMaterial.setEnvironmentalConcerns(environmentalField);
				selectedMaterial.setStabilityAndReactFirstAidMeasures(firstAidField);
				selectedMaterial.setDangers(dangersField);
				selectedMaterial.setDangerRating(Integer.parseInt(hazardousRatingField.getText()));
				
				PrintWriter pr = new PrintWriter(material);
				pr.println(selectedMaterial.getName());
				pr.println(selectedMaterial.getType());
				pr.println(selectedMaterial.getBrand());
				pr.println(selectedMaterial.getStorageCondition());
				pr.println(selectedMaterial.getHyperlinkToPurchase());
				pr.println(selectedMaterial.getHyperlinkToMSD());
				pr.println(selectedMaterial.getEnvironmentalConcerns().getText());
				pr.println(selectedMaterial.getStabilityAndReactFirstAidMeasures().getText());
				pr.println(selectedMaterial.getDangers().getText());
				pr.println(selectedMaterial.getIconPath());
				pr.println(selectedMaterial.getAddOrder());
				pr.println(selectedMaterial.getDangerRating());
				pr.println(0);
				pr.println(selectedMaterial.getState());
				
				pr.close();
				
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
				
			}
			
			materialListPanel.removeAll();
			materialButtonContainer.clear();
			materialContainer.clear();
			materialStorage.clear();
			
			loadMaterials();
			
			materialList.revalidate();
			materialList.repaint();
			
			moveAmount = SHIFT_AMOUNT;
			componentBackwardTimer.start();
			
		} else if(event.getSource() == delete) {
			
			newIcon.setVisible(false);
			
			File material = new File(selectedMaterial.getPath());
			material.delete();
			
			materialListPanel.removeAll();
			materialButtonContainer.clear();
			materialContainer.clear();
			materialStorage.clear();
			
			loadMaterials();
			
			materialList.revalidate();
			materialList.repaint();
			
			materialImage.setIcon(null);
			materialImage.repaint();
			selectedMaterial = null;
			
			moveAmount = SHIFT_AMOUNT;
			componentBackwardTimer.start();
			
		} else if(event.getSource() == proceedToReport) {
			
			new ProjectDetailsState();
			this.dispose();
			
		}
		
	} 

}
