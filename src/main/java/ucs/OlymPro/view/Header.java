package ucs.OlymPro.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class Header extends JPanel{
	
	private static final long serialVersionUID = 258L;
	
	JPanel header = new JPanel();
	JButton btnReturn = new JButton("Return");
	Color blue = new Color(235, 250, 250);
	
	public Header(String frase) {
		header.setBorder(new EmptyBorder(0, 0, 0, 0));
		header.setBackground(blue);
		header.setBounds(10, 11, 924, 65);
		header.setLayout(null);
		
		ImageIcon icon = new ImageIcon(Header.class.getResource("/img/return_icon.png"));
	    Image img = icon.getImage();
	    Image newImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btnReturn.setBackground(blue);
		btnReturn.setForeground(blue);
		btnReturn.setIcon(new ImageIcon(newImg));
		btnReturn.setBounds(10, 14, 38, 40);
		btnReturn.setBorderPainted(false);
		btnReturn.setFocusPainted(false);
		btnReturn.setContentAreaFilled(false);
		btnReturn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		header.add(btnReturn);
		JLabel lblNewLabel = new JLabel(frase);
		lblNewLabel.setBounds(378, 14, 170, 31);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 26));
		header.add(lblNewLabel);
		
	}
	
	public JPanel getHeader(ActionListener e) {
		btnReturn.addActionListener(e);
		return header;
	}
	
	
}