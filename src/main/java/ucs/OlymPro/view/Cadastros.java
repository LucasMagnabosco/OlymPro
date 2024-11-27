package ucs.OlymPro.view;


import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ucs.OlymPro.controller.DataController;
import ucs.OlymPro.exceptions.ExcecaoEspacoVazio;
import ucs.OlymPro.exceptions.ExcecaoNotNumber;
import ucs.OlymPro.exceptions.ExcecaoObjetoJaCadastrado;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class Cadastros extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	MainScreen ms;
	DataController data = new DataController();
	JButton btnTeam, btnAtl;
	JPanel header = new JPanel();
	Color blue = new Color(235, 250, 250);
	private JTextField txtName;
	private JTextField txtCountry;
	private JTextField txtAge;
	JTable table_team;
	JTable table_atleta;
	ImageIcon icon = new ImageIcon(Header.class.getResource("/img/cancel.png"));
	
	public Cadastros(MainScreen menu) {
		this.ms = menu;
		setBounds(0, 0, 960, 580);
		setLayout(null);
		
		String frase = "Cadastros";
		Header h = new Header(frase);
		header = h.getHeader(this);
		add(header);
		
		Object[][] rows = data.athleteToArray();
		String[] columns = {"Atletas"};
		table_atleta = new JTable();
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
		table_team = new JTable();
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
		rolagem2.setBounds(500, 244, 297, 260);
		add(rolagem2);
		
		btnAtl = new JButton("Cadastrar atleta");
		btnAtl.setBounds(179, 123, 240, 75);
		btnAtl.addActionListener(this);
		add(btnAtl);
		
		btnTeam = new JButton("Cadastrar equipe");
		btnTeam.setBounds(529, 123, 240, 75);
		btnTeam.addActionListener(this);
		add(btnTeam);
	
		
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
		} else if(teste.equals("Cadastrar atleta")) {
			atlRegister();
		}
	}
	
	public void atlRegister() {
		btnAtl.setVisible(false);
		
		JPanel atlPanel = new JPanel();
		atlPanel.setBounds(140, 103, 340, 130);
		atlPanel.setLayout(null);
		atlPanel.setBackground(blue);
		add(atlPanel);
		
		JLabel lblName = new JLabel("Nome");
		lblName.setBounds(10, 11, 46, 14);
		atlPanel.add(lblName);
		
		JLabel lblCountry = new JLabel("Nacionalidade");
		lblCountry.setBounds(10, 61, 80, 14);
		atlPanel.add(lblCountry);
		
		JLabel lblAge = new JLabel("Idade");
		lblAge.setBounds(160, 11, 46, 14);
		atlPanel.add(lblAge);
		
		txtName = new JTextField();
		txtName.setBounds(10, 29, 137, 20);
		txtName.setColumns(10);
		atlPanel.add(txtName);
		
		txtCountry = new JTextField();
		txtCountry.setBounds(10, 86, 129, 20);
		txtCountry.setColumns(10);
		atlPanel.add(txtCountry);
		
		txtAge = new JTextField();
		txtAge.setBounds(160, 29, 40, 20);
		txtAge.setColumns(10);
		atlPanel.add(txtAge);
		
		JButton btnRegisterAtl = new JButton("Cadastrar");
		btnRegisterAtl.setVerticalAlignment(SwingConstants.BOTTOM);
		btnRegisterAtl.setBounds(197, 81, 106, 30);
		btnRegisterAtl.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	try {
					data.registerAthlete(txtName.getText(), txtCountry.getText(), txtAge.getText());
					updateAthlete();
				} catch (ExcecaoEspacoVazio | ExcecaoNotNumber | ExcecaoObjetoJaCadastrado e1) {
					e1.printStackTrace();
				}
		    }
		});
		atlPanel.add(btnRegisterAtl);
		
		Image newImg = icon.getImage().getScaledInstance(26, 22, Image.SCALE_SMOOTH);
		JButton btnCancel = new JButton("");
		btnCancel.setBounds(162, 81, 34, 30);
		btnCancel.setIcon(new ImageIcon(newImg));
		btnCancel.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
				atlPanel.setVisible(false);
		    	btnAtl.setVisible(true);

		    }
		});
		atlPanel.add(btnCancel);
		
	}
	public void updateTeam() {
		DefaultTableModel model = (DefaultTableModel) table_team.getModel();
		String[] columns = {"Equipes"};
		model.setDataVector(data.teamToArray(), columns);
		model.fireTableDataChanged();
	}
	public void updateAthlete() {
		DefaultTableModel model = (DefaultTableModel) table_atleta.getModel();
		String[] columns = {"Pilotos", "Equipe"};
		model.setDataVector(data.athleteToArray(), columns);
		model.fireTableDataChanged();
	}
}
