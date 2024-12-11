package ucs.OlymPro.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ucs.OlymPro.controller.DataController;
import ucs.OlymPro.controller.ResultController;
import ucs.OlymPro.model.Athlete;
import ucs.OlymPro.model.Equipe;

public class Etapas extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	MainScreen ms;
	JPanel header = new JPanel();
	Color blue = new Color(235, 250, 250);
	DataController data = new DataController();
	ResultController result = new ResultController();
	
	private JComboBox<String> modalidadeCombo;
	private JComboBox<String> etapasCombo;
	private JButton criarEtapaBtn;
	private JButton distribuirMedalhasBtn;
	private JPanel contentPanel;
	private JTable tabelaPartidas;
	private DefaultTableModel modeloTabela;
	private JPanel painelTabela;
	
	public Etapas(MainScreen menu) {
		this.ms = menu;
		setBounds(100, 100, 1000, 600);
		setBackground(blue);
		setLayout(null);

		// Header
		String frase = "Etapas";
		Header h = new Header(frase);
		header = h.getHeader(this);
		add(header);
		
		// Painel de conteúdo
		contentPanel = new JPanel();
		contentPanel.setBounds(50, 100, 900, 450);
		contentPanel.setBackground(blue);
		contentPanel.setLayout(null);
		add(contentPanel);
		
		// Combo de modalidades
		JLabel modalidadeLabel = new JLabel("Modalidade:");
		modalidadeLabel.setBounds(20, 20, 100, 25);
		contentPanel.add(modalidadeLabel);
		
		modalidadeCombo = new JComboBox<>(data.getModalidadeArray());
		modalidadeCombo.setBounds(120, 20, 200, 25);
		modalidadeCombo.addActionListener(this);
		contentPanel.add(modalidadeCombo);
		
		// Novo combo box para etapas
		JLabel etapasLabel = new JLabel("Etapa:");
		etapasLabel.setBounds(20, 60, 100, 25);
		contentPanel.add(etapasLabel);
		
		etapasCombo = new JComboBox<>(new String[]{"Final"});
		etapasCombo.setBounds(120, 60, 200, 25);
		contentPanel.add(etapasCombo);
		
		// Botões
		criarEtapaBtn = new JButton("Inserir Etapa");
		criarEtapaBtn.setBounds(145, 100, 150, 30);
		criarEtapaBtn.addActionListener(this);
		contentPanel.add(criarEtapaBtn);
		
		distribuirMedalhasBtn = new JButton("Distribuir Medalhas");
		distribuirMedalhasBtn.setBounds(145, 140, 150, 30);
		distribuirMedalhasBtn.addActionListener(this);
		contentPanel.add(distribuirMedalhasBtn);
		
		// Painel para a tabela de partidas
		painelTabela = new JPanel();
		painelTabela.setBounds(350, 20, 450, 400);
		painelTabela.setBackground(blue);
		painelTabela.setLayout(null);
		contentPanel.add(painelTabela);
		
		// Configuração da tabela
		String[] colunas = {"Atleta", "Tempo", "País"};
		modeloTabela = new DefaultTableModel(colunas, 0);
		tabelaPartidas = new JTable(modeloTabela);
		
		JScrollPane scrollPane = new JScrollPane(tabelaPartidas);
		scrollPane.setBounds(0, 0, 450, 400);
		painelTabela.add(scrollPane);
		atualizarTabela("Natação");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String teste = "";
		if (e.getSource() instanceof JButton) {
			teste = ((JButton) e.getSource()).getText();
		}
		if (teste.equals("Return")) {
			ms.menuReturn();
		}
		if (e.getSource() == modalidadeCombo) {
			String modalidade = (String) modalidadeCombo.getSelectedItem();
			atualizarTabela(modalidade);
			
			if (modalidade.equals("Natação")) {
				etapasCombo.removeAllItems();
				etapasCombo.addItem("Final");
			} else {
				etapasCombo.removeAllItems();
				etapasCombo.addItem("Quartas");
				etapasCombo.addItem("Semifinal");
				etapasCombo.addItem("Final");
				etapasCombo.addItem("Terceiro lugar");
			}
		}
		if (e.getSource() == criarEtapaBtn) {
			String modalidade = (String) modalidadeCombo.getSelectedItem();
			String etapa = (String) etapasCombo.getSelectedItem();
			if(modalidade.equals("Natação")) {
				cadastrarResultadoIndividual();
			}else {
				cadastrarEtapaColetiva(modalidade, etapa);

			}
		}
		if (e.getSource() == distribuirMedalhasBtn) {
			String modalidade = (String) modalidadeCombo.getSelectedItem();
			if (modalidade.equals("Natação")) {
				try {
					result.distribuirMedalhasNatacao();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				try {
					result.distribuirMedalhasEquipes(modalidade);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	private void atualizarTabela(String modalidade) {
		painelTabela.removeAll();
		
		String[] colunas;
		if (modalidade.equals("Natação")) {
			colunas = new String[]{"Atleta", "Tempo", "País"};
		} else {
			colunas = new String[]{"Equipe 1", "Placar 1", "X", "Placar 2", "Equipe 2", "Data", "Fase"};
		}
		
		modeloTabela = new DefaultTableModel(colunas, 0);
		tabelaPartidas = new JTable(modeloTabela);
		
		// Aplicando as mesmas cores da classe Paises
		Color darkBlue = new Color(61, 90, 128);
		Color lightBlue = new Color(152, 193, 217);
		Color white = new Color(238, 238, 238);
		
		tabelaPartidas.setBackground(white);
		tabelaPartidas.setGridColor(lightBlue);
		tabelaPartidas.getTableHeader().setBackground(new Color(200, 230, 230));
		tabelaPartidas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		
		JScrollPane scrollPane = new JScrollPane(tabelaPartidas);
		scrollPane.setBounds(0, 0, 450, 400);
		scrollPane.setBorder(BorderFactory.createLineBorder(darkBlue));
		scrollPane.getViewport().setBackground(blue);
		
		// Exemplo de dados (substitua pela integração com seu DataController)
		if (modalidade.equals("Natação")) {
			Object[][] partidas = result.getResultadosData();
			if(partidas != null) {
				for(Object[] p : partidas) {
					modeloTabela.addRow(p);
				}	
			}
		} else if(modalidade.equals("Vôlei")) {
			Object[][] partidas = result.getPartidasVolei();
			if(partidas != null) {
				for(Object[] p : partidas) {
					modeloTabela.addRow(p);
				}	
			}
		} else {
			Object[][] partidas = result.getPartidasFutebol();
			if(partidas != null) {
				for(Object[] p : partidas) {
					modeloTabela.addRow(p);
				}	
			}
		}
		
		painelTabela.add(scrollPane);
		
		tabelaPartidas.revalidate();
		tabelaPartidas.repaint();
		painelTabela.revalidate();
		painelTabela.repaint();
		contentPanel.revalidate();
		contentPanel.repaint();
	}
	
	private void cadastrarEtapaColetiva(String esporte, String etapa) {
		try {
			// Criar JOptionPane personalizado para coletar dados
			JComboBox<Equipe> time1Combo = new JComboBox<>(data.getEquipesPorModalidade(esporte).toArray(new Equipe[0]));
			JComboBox<Equipe> time2Combo = new JComboBox<>(data.getEquipesPorModalidade(esporte).toArray(new Equipe[0]));
			
			JTextField placar1Field = new JTextField(5);
			JTextField placar2Field = new JTextField(5);
			JTextField data = new JTextField(5);
			
			
			Object[] message = {
				"Time 1:", time1Combo,
				"Placar Time 1:", placar1Field,
				"Time 2:", time2Combo,
				"Placar Time 2:", placar2Field,
				"Data:", data
			};

			int option = JOptionPane.showConfirmDialog(null, message, "Cadastrar Partida", 
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				Equipe time1 = (Equipe) time1Combo.getSelectedItem();
				Equipe time2 = (Equipe) time2Combo.getSelectedItem();
				int placar1 = Integer.parseInt(placar1Field.getText());
				int placar2 = Integer.parseInt(placar2Field.getText());
				String dataPartida = data.getText();
				
				// Aqui você pode chamar o método do controller para salvar a partida
				result.cadastrarPartida(esporte, etapa, time1, time2, placar1, placar2, dataPartida);
				
				// Atualiza a tabela após cadastrar
				atualizarTabela(esporte);
				
				JOptionPane.showMessageDialog(null, "Partida cadastrada com sucesso!");
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Por favor, insira valores válidos para os placares!");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar partida: " + ex.getMessage());
		}
	}
	
	private void cadastrarResultadoIndividual() {
		try {
			// Criar ComboBox com os atletas da modalidade Natação
			JComboBox<Athlete> atletaCombo = new JComboBox<>(data.getAtletasInd().toArray(new Athlete[0]));
			JTextField tempoField = new JTextField(10);

			Object[] message = {
				"Atleta:", atletaCombo,
				"Tempo (MM:SS.dd):", tempoField
			};

			int option = JOptionPane.showConfirmDialog(null, message, "Cadastrar Resultado", 
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				Athlete atleta = (Athlete) atletaCombo.getSelectedItem();
				String tempo = tempoField.getText();
				String fase = (String) etapasCombo.getSelectedItem();
				
				// Validação do tempo inserido
				if (!tempo.matches("\\d{2}:\\d{2}\\.\\d{2}")) {
					throw new IllegalArgumentException("Formato de tempo inválido. Use MM:SS.dd");
				}
				
				// Validação do atleta selecionado
				if (atleta == null) {
					throw new IllegalArgumentException("Por favor, selecione um atleta");
				}
				
				// Chama o controller para salvar o resultado
				result.cadastrarResultadoIndividual(atleta, "400m", tempo, fase);
				
					atualizarTabela("Natação");
					JOptionPane.showMessageDialog(null, "Resultado cadastrado com sucesso!");
			
			}
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar resultado: " + ex.getMessage());
		}
	}
	
}
