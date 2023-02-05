package displays;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;

public class ProjectDetailsState extends State {

	//JComponents
	private JPanel panel;
	private JTextArea textbox;
	private JSlider EISlider;
	private JButton reportButton;
	private JLabel aboutLabel, EILabel;
	Hashtable<Integer, JLabel> labelTable;


	static final int EI_MIN = 1;
	static final int EI_MAX = 5;
	static final int EI_INIT = 3;

	@Override
	public void init() {
		
		
	}

	@Override
	public void addJComponents() {
		panel = new JPanel(null);
		panel.setBackground(new Color(27, 62, 90));
		panel.setBounds(0, 0, WIDTH, HEIGHT);
		
		aboutLabel = new JLabel("Description of Project");
		aboutLabel.setBounds(450, 65, 400, 50);
		aboutLabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		aboutLabel.setForeground(new Color(68, 225, 212));
		panel.add(aboutLabel);

		textbox = new JTextArea();
		textbox.setBounds(390, 125, 500, 200);
		panel.add(textbox);
		
		EILabel = new JLabel("Environmental Impact Rating");
		EILabel.setBounds(400, 390, 500, 50);
		EILabel.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		EILabel.setForeground(new Color(68, 225, 212));
		panel.add(EILabel);

		//Environmental Impact Rating Slider
		EISlider = new JSlider(JSlider.HORIZONTAL,EI_MIN, EI_MAX, EI_INIT);
		EISlider.setBounds(390, 450, 500, 150);
		EISlider.setBackground(new Color(27, 62, 90));
		EISlider.setFont(new Font("Gill Sans MT Condensed", Font.PLAIN, 40));
		EISlider.setMajorTickSpacing(1);
		EISlider.setPaintTicks(true);
		EISlider.setPaintLabels(true);	
		panel.add(EISlider);

		//add labels to the slider
		labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(1, new JLabel("Low"));
		labelTable.put(5, new JLabel("High"));
		EISlider.setLabelTable(labelTable);

		reportButton = new JButton(new ImageIcon(new ImageIcon("images/proceedToReport.png").getImage().getScaledInstance(307, 62, 0)));
		reportButton.setBounds(920, 650, 320, 75);
		reportButton.addActionListener(this);
		panel.add(reportButton);

		add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		new ReportScreen();

	}

}
