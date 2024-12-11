package ucs.OlymPro.view;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ucs.OlymPro.controller.DataController;

public class MainScreen extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel cardPanel;
	private JLabel lblNewLabel;
	private CardLayout cardLayout;
	DataController data = new DataController();
	Color blue = new Color(235, 250, 250);
	Cadastros cad = new Cadastros(this);
	Paises paises = new Paises(this);
	Etapas etapas = new Etapas(this);
	
	public MainScreen() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		getContentPane().add(cardPanel);
		

		cardPanel.add(cad, "Cadastros");
		cardPanel.add(paises, "Paises");
		cardPanel.add(etapas, "Etapas");
		
		contentPane = new JPanel();
		contentPane.setBackground(blue);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		JPanel header = new JPanel();
		header.setBorder(new EmptyBorder(0, 0, 0, 0));
		header.setBackground(new Color(235, 250, 250));
		header.setBounds(10, 0, 924, 65);
		header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
		
		lblNewLabel = new JLabel("OlymPro");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 26));
		header.add(lblNewLabel);
		
		contentPane.add(header);
		
		JButton btnCadastro = new JButton("Cadastro de Atletas e Equipes");
		btnCadastro.setBounds(150, 100, 227, 75);
		btnCadastro.addActionListener(this);
		contentPane.add(btnCadastro);
		
		JButton btnModalidades = new JButton("Gerenciar Modalidades e Etapas");
		btnModalidades.setBounds(387, 100, 227, 75);
		btnModalidades.addActionListener(this);
		contentPane.add(btnModalidades);
		
		JButton btnPaises = new JButton("Países e medalhas");
		btnPaises.setBounds(624, 100, 227, 75);
		btnPaises.addActionListener(this);
		contentPane.add(btnPaises);
		

		JPanel rankingPanel = new JPanel();
		rankingPanel.setBounds(150, 325, 673, 200);
		rankingPanel.setBackground(blue);
		rankingPanel.setLayout(new BorderLayout());

		String[] colunas = {"Posição", "País", "Ouro", "Prata", "Bronze", "Total"};
		Object[][] dados = data.getTop5Paises();

		JTable table = new JTable(dados, colunas);
		table.setEnabled(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(25);
		table.setPreferredScrollableViewportSize(new Dimension(500, 125));
		
		table.getTableHeader().setBackground(blue);
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		table.getTableHeader().setForeground(Color.BLACK);
		table.getTableHeader().setOpaque(true);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBackground(blue);
		scrollPane.getViewport().setBackground(blue);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JLabel titleLabel = new JLabel("Top 5 Países", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

		rankingPanel.add(titleLabel, BorderLayout.NORTH);
		rankingPanel.add(scrollPane, BorderLayout.CENTER);

		contentPane.add(rankingPanel);
		
		cardPanel.add(contentPane, "Main");
		cardLayout.show(cardPanel, "Main");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String teste = "";
		teste = ((JButton) e.getSource()).getText();
		switch(teste) {
			case "Cadastro de Atletas e Equipes":
				cad.updateAthlete();
				cad.updateTeam();
				cardLayout.show(cardPanel, "Cadastros");
				break;
			case "Países e medalhas":
				paises.atualizarTabela();
				cardLayout.show(cardPanel, "Paises");
				break;
			case "Gerenciar Modalidades e Etapas":
			cardLayout.show(cardPanel, "Etapas");
				break;
		}
	}
	
	public void menuReturn() {
		cardLayout.show(cardPanel, "Main");
	}
}
