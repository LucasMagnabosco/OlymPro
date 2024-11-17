package ucs.OlymPro.view;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ucs.OlymPro.controller.DataController;


public class Cadastros extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	MainScreen ms;
	DataController data = new DataController();
	JPanel header = new JPanel();
	Color blue = new Color(235, 250, 250);
	
	public Cadastros(MainScreen menu) {
		this.ms = menu;
		setBounds(0, 0, 960, 580);
		setLayout(null);
		
		String frase = "Cadastros";
		Header h = new Header(frase);
		header = h.getHeader(this);
		add(header);
		
		Object[][] rows = data.athelteToArray();
		String[] columns = {"Atletas"};
		JTable table_atleta = new JTable();
		table_atleta.setModel(new DefaultTableModel(rows, columns));
		table_atleta.getColumnModel().getColumn(0).setPreferredWidth(109);
		table_atleta.setShowVerticalLines(false);
		table_atleta.setFont(new Font("Arial", Font.PLAIN, 11));
		table_atleta.setBounds(140, 295, 209, 232);
		table_atleta.setBackground(new Color(195, 196, 199));
		table_atleta.getTableHeader().setBackground(new Color(195, 196, 199));
		table_atleta.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		JScrollPane rolagem1 = new JScrollPane(table_atleta);
		rolagem1.setBackground(new Color(195, 196, 199));
		rolagem1.getViewport().setBackground(new Color(195, 196, 199));
		rolagem1.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		rolagem1.setBounds(150, 244, 297, 258);
		rolagem1.getVerticalScrollBar().setBackground(Color.GRAY);
		add(rolagem1);
		
		Object[][] rows2 = data.teamToArray();
		String[] columns2 = {"Equipes"};
		JTable table_team = new JTable();
		table_team.setModel(new DefaultTableModel(rows2, columns2));
		table_team.setShowVerticalLines(false);
		table_team.setBounds(611, 295, 209, 232);
		table_team.setBackground(new Color(195, 196, 199));
		table_team.setFont(new Font("Arial", Font.PLAIN, 11));
		table_team.getTableHeader().setBackground(new Color(195, 196, 199));
		table_team.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		JScrollPane rolagem2 = new JScrollPane(table_team);
		rolagem2.getViewport().setBackground(new Color(195, 196, 199));
		rolagem2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		rolagem2.setBounds(562, 244, 297, 260);
		add(rolagem2);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		String teste = "";
		if(e.getSource() instanceof JButton) {
			teste = ((JButton) e.getSource()).getText();
		}else if(e.getSource() instanceof JComboBox) {
			teste = (String) ((JComboBox) e.getSource()).getSelectedItem();
		}
		if(teste.equals("Return")) {
			ms.menuReturn();
		}
	}
}
