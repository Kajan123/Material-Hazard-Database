package displays;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class StartState extends State implements MouseListener {
	private JPanel panel;
	private JLabel titleLabel;
	private JLabel getStart;

	@Override
	public void init() {
	
		
	}

	@Override
	public void addJComponents() {
		
		panel = new JPanel(null);
		panel.setBackground(new Color(27, 62, 90));
		panel.setBounds(0, 0, WIDTH, HEIGHT);
		
		getStart = new JLabel("TAP ANYWHERE TO START");
		getStart.setBounds(0, 0, 1280, 750);
		getStart.setForeground(Color.WHITE);
		getStart.setFont(new Font("Gill Sans MT Condensed", Font.ITALIC, 50));
		getStart.addMouseListener(this);
		panel.add(getStart);
		getStart.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel = new JLabel(
				new ImageIcon(new ImageIcon("images/menu background.jpg").getImage().getScaledInstance(1280, 800, 0)));
		titleLabel.setBounds(0, 0, 1280, 800);
		panel.add(titleLabel);
		
		add(panel);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent event) {
		
		if(event.getSource() == getStart) {
			
			new LoginState();
			this.dispose();
			
		}
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		
	}

	@Override
	public void mouseExited(MouseEvent event) {
		
	}

	

}
