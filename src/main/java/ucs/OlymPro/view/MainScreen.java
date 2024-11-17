package ucs.OlymPro.view;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel cardPanel;
	private JLabel lblNewLabel;
	private CardLayout cardLayout;
	Color blue = new Color(235, 250, 250);
	Cadastros cad = new Cadastros(this);
	
	public MainScreen() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 580);
		
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		getContentPane().add(cardPanel);
		
		cad.setBackground(new Color(235, 250, 250));
		cardPanel.add(cad, "Cadastros");
		
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
		btnCadastro.setSize(227, 125);
		btnCadastro.setLocation(130, 139);
		btnCadastro.addActionListener(this);
		contentPane.add(btnCadastro);
		
		
		JButton btnMedalhas = new JButton("Quadro de medalhas");
		btnMedalhas.setBounds(470, 139, 333, 125);
		btnMedalhas.addActionListener(this);
		contentPane.add(btnMedalhas);
		
		JButton btnPrincipal = new JButton("Painel Principal");
		btnPrincipal.setBounds(130, 325, 673, 159);
		contentPane.add(btnPrincipal);
		btnPrincipal.addActionListener(this);
		
		
		cardPanel.add(contentPane, "Main");
		cardLayout.show(cardPanel, "Main");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String teste = "";
		teste = ((JButton) e.getSource()).getText();
		if(teste.equals("Cadastro de Atletas e Equipes")) {
			cardLayout.show(cardPanel, "Cadastros");
		}	
	}
	
	public void menuReturn() {
		cardLayout.show(cardPanel, "Main");
	}
}
