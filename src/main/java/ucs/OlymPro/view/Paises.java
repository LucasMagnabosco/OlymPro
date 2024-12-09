package ucs.OlymPro.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.BorderFactory;

public class Paises extends JPanel implements ActionListener{

	private static final long serialVersionUID = 38L;

	Color blue = new Color(235, 250, 250);
	JPanel header = new JPanel();
	MainScreen ms;
	
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnAdicionar, btnRemover, btnVoltar;
	private JScrollPane scrollPane;

	public Paises(MainScreen menu) {
		this.ms = menu;
		setBounds(100, 100, 1000, 600);
		setBackground(blue);
		setLayout(null);
		

		String[] colunas = {"País", "Ouro", "Prata", "Bronze", "Total"};
		tableModel = new DefaultTableModel(colunas, 0);
		table = new JTable(tableModel);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 50, 900, 400);
		add(scrollPane);
		
		// Configuração dos botões
		btnAdicionar = new JButton("Adicionar País");
		btnAdicionar.setBounds(50, 480, 150, 30);
		btnAdicionar.addActionListener(this);
		add(btnAdicionar);
		
		
		btnRemover = new JButton("Remover País");
		btnRemover.setBounds(390, 480, 150, 30);
		btnRemover.addActionListener(this);
		add(btnRemover);
		
		btnVoltar = new JButton("Return");
		btnVoltar.setBounds(800, 480, 150, 30);
		btnVoltar.addActionListener(this);
		add(btnVoltar);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String teste = "";
		if (e.getSource() instanceof JButton) {
			teste = ((JButton) e.getSource()).getText();
		} else if (e.getSource() instanceof JComboBox) {
			teste = (String) ((JComboBox) e.getSource()).getSelectedItem();
		}
		if (teste.equals("Return")) {
			ms.menuReturn();
		}else if (e.getSource() == btnAdicionar) {
			// Implementar lógica para adicionar país
		} else if (e.getSource() == btnRemover) {
			// Implementar lógica para remover país
		} else if (e.getSource() == btnVoltar) {
			ms.menuReturn();
		}
	}
}
