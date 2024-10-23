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
	
	public MainScreen() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 580);
		
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		getContentPane().add(cardPanel);
		
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		JPanel header = new JPanel();
		header.setBorder(new EmptyBorder(0, 0, 0, 0));
		
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
		contentPane.add(btnCadastro);
		
		cardPanel.add(contentPane, "Main");
		
		JButton btnMedalhas = new JButton("Quadro de medalhas");
		btnMedalhas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnMedalhas.setBounds(470, 139, 333, 125);
		contentPane.add(btnMedalhas);
		
		JButton btnPrincipal = new JButton("Painel Principal");
		btnPrincipal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPrincipal.setBounds(130, 325, 673, 159);
		contentPane.add(btnPrincipal);
		cardLayout.show(cardPanel, "Main");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
