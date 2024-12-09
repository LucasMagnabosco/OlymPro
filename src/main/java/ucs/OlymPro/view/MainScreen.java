package ucs.OlymPro.view;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainScreen extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel cardPanel;
	private JLabel lblNewLabel;
	private CardLayout cardLayout;
	Color blue = new Color(235, 250, 250);
	Cadastros cad = new Cadastros(this);
	MedalBoard medal = new MedalBoard(this);
	Paises paises = new Paises(this);
	
	public MainScreen() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		getContentPane().add(cardPanel);
		

		cardPanel.add(cad, "Cadastros");
		cardPanel.add(medal, "Medalhas");
		cardPanel.add(paises, "Paises");
		
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
		btnCadastro.setBounds(130, 100, 227, 75);
		btnCadastro.addActionListener(this);
		contentPane.add(btnCadastro);
		
		JButton btnMedalhas = new JButton("Quadro de medalhas");
		btnMedalhas.setBounds(370, 100, 227, 75);
		btnMedalhas.addActionListener(this);
		contentPane.add(btnMedalhas);
		
		JButton btnPaises = new JButton("Países e medalhas");
		btnPaises.setBounds(130, 190, 227, 75);
		btnPaises.addActionListener(this);
		contentPane.add(btnPaises);
		
		JButton btnModalidades = new JButton("Gerenciar Modalidades");
		btnModalidades.setBounds(370, 190, 227, 75);
		btnModalidades.addActionListener(this);
		contentPane.add(btnModalidades);
		
		JButton btnEtapas = new JButton("Gerenciar Etapas");
		btnEtapas.setBounds(610, 190, 227, 75);
		btnEtapas.addActionListener(this);
		contentPane.add(btnEtapas);
		
		JPanel rankingPanel = new JPanel();
		rankingPanel.setBounds(130, 325, 673, 159);
		rankingPanel.setBackground(blue);
		rankingPanel.setLayout(new BorderLayout(10, 5));

		String[] colunas = {"Posição", "País", "Ouro", "Prata", "Bronze", "Total"};
		Object[][] dados = new Object[5][6];
		for (int i = 0; i < 5; i++) {
			dados[i][0] = i + 1;
			dados[i][1] = "País " + i;
			dados[i][2] = 0;
			dados[i][3] = 0;
			dados[i][4] = 0;
			dados[i][5] = 0;
		}

		JTable table = new JTable(dados, colunas);
		table.setEnabled(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(25);
		table.getTableHeader().setBackground(blue);

		JLabel titleLabel = new JLabel("Top 5 Países", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

		rankingPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		rankingPanel.add(titleLabel, BorderLayout.NORTH);
		rankingPanel.add(table.getTableHeader(), BorderLayout.CENTER);
		rankingPanel.add(table, BorderLayout.SOUTH);

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
				cardLayout.show(cardPanel, "Cadastros");
				break;
			case "Quadro de medalhas":
				cardLayout.show(cardPanel, "Medalhas");
				break;
			case "Países e medalhas":
				cardLayout.show(cardPanel, "Paises");
				break;
			case "Gerenciar Modalidades":
				// TODO: Implementar tela de gerenciamento de modalidades
				break;
			case "Gerenciar Etapas":
				// TODO: Implementar tela de gerenciamento de etapas
				break;
		}
	}
	
	public void menuReturn() {
		cardLayout.show(cardPanel, "Main");
	}
}
