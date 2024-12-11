package ucs.OlymPro.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import ucs.OlymPro.controller.DataController;
import ucs.OlymPro.exceptions.ExcecaoEspacoVazio;
import ucs.OlymPro.exceptions.ExcecaoObjetoJaCadastrado;
import ucs.OlymPro.model.Pais;

public class Paises extends JPanel implements ActionListener{

	private static final long serialVersionUID = 38L;

	// Cores do sistema
	Color blue = new Color(235, 250, 250);
	Color darkBlue = new Color(61, 90, 128);
	Color lightBlue = new Color(152, 193, 217);
	Color lighterBlue = new Color(224, 236, 244);
	Color white = new Color(238, 238, 238);
	
	JPanel header = new JPanel();
	MainScreen ms;
	DataController data = new DataController();
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnAdicionar, btnRemover, btnVoltar;
	private JScrollPane scrollPane;
	private JButton btnAdicionarMedalha;
	private JButton btnRemoverMedalha;

	public Paises(MainScreen menu) {
		this.ms = menu;
		setBounds(100, 100, 1000, 600);
		setBackground(blue);
		setLayout(null);
		
		String frase = "Paises";
		Header h = new Header(frase);
		header = h.getHeader(this);
		add(header);

		String[] colunas = { "País", "Ouro", "Prata", "Bronze", "Total" };
		tableModel = new DefaultTableModel(colunas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(tableModel);
		table.setBackground(white);
		table.setGridColor(lightBlue);
		table.getTableHeader().setBackground(new Color(200, 230, 230));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
		table.setRowSorter(sorter);

		sorter.setComparator(tableModel.getColumnCount() - 1, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				if (o1 instanceof Integer && o2 instanceof Integer) {
					return ((Integer) o2).compareTo((Integer) o1); // Ordem decrescente
				}
				// Caso os valores não sejam Integer, retorna 0
				return 0;
			}
		});

		sorter.toggleSortOrder(tableModel.getColumnCount() - 1);

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 100, 900, 350);
		scrollPane.setBorder(BorderFactory.createLineBorder(darkBlue));
		scrollPane.getViewport().setBackground(blue);
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
		
		btnAdicionarMedalha = new JButton("Adicionar Medalha");
		btnAdicionarMedalha.setBounds(210, 480, 150, 30);
		btnAdicionarMedalha.addActionListener(this);
		add(btnAdicionarMedalha);
		
		btnRemoverMedalha = new JButton("Remover Medalha");
		btnRemoverMedalha.setBounds(570, 480, 150, 30);
		btnRemoverMedalha.addActionListener(this);
		add(btnRemoverMedalha);
	
		atualizarTabela(); 
	}
	
	public void atualizarTabela() {
		tableModel.setRowCount(0);
		
		Object[][] dados = data.getCountriesData();
		
		if (dados != null) {
			for (Object[] linha : dados) {
				tableModel.addRow(linha);
			}
		}
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
		} else if (e.getSource() == btnAdicionar) {
			String nomePais = JOptionPane.showInputDialog(this, "Digite o nome do país:");
			try {
				data.registerCountry(nomePais);
				atualizarTabela();
			} catch (ExcecaoEspacoVazio | ExcecaoObjetoJaCadastrado e1) {
					JOptionPane.showMessageDialog(null, "Erro ao adicionar pais: " + e1.getMessage());
				}
		} else if (e.getSource() == btnRemover) {
			// Implementar lógica para remover país
		} else if (e.getSource() == btnVoltar) {
			ms.menuReturn();
		} else if (e.getSource() == btnAdicionarMedalha) {
			adicionarMedalha();
		} else if (e.getSource() == btnRemoverMedalha) {
			removerMedalha();
		}
	}

	private void adicionarMedalha() {
		String[] tiposMedalha = {"Ouro", "Prata", "Bronze"};
		
		try {
			// Pegar o país selecionado na tabela
			int linhaSelecionada = table.getSelectedRow();
			if (linhaSelecionada == -1) {
				JOptionPane.showMessageDialog(null, "Por favor, selecione um país na tabela.");
				return;
			}
			
			String paisSelecionado = (String) table.getValueAt(linhaSelecionada, 0);
			
			JComboBox<String> medalhaCombo = new JComboBox<>(tiposMedalha);
			
			Object[] message = {
				"País: " + paisSelecionado,
				"Tipo de Medalha:", medalhaCombo
			};

			int option = JOptionPane.showConfirmDialog(null, message, "Adicionar Medalha", 
				JOptionPane.OK_CANCEL_OPTION);

			if (option == JOptionPane.OK_OPTION) {
				String tipoMedalha = (String) medalhaCombo.getSelectedItem();
				data.adicionarMedalha(paisSelecionado, tipoMedalha);
				atualizarTabela(); // Método para atualizar a tabela
				JOptionPane.showMessageDialog(null, "Medalha adicionada com sucesso!");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Erro ao adicionar medalha: " + ex.getMessage());
		}
	}

	private void removerMedalha() {
		String[] tiposMedalha = {"Ouro", "Prata", "Bronze"};
		
		try {
			// Pegar o país selecionado na tabela
			int linhaSelecionada = table.getSelectedRow();
			if (linhaSelecionada == -1) {
				JOptionPane.showMessageDialog(null, "Por favor, selecione um país na tabela.");
				return;
			}
			String paisSelecionado = (String) table.getValueAt(linhaSelecionada, 0);

			
			JComboBox<String> medalhaCombo = new JComboBox<>(tiposMedalha);
			
			Object[] message = {
				"País: " + paisSelecionado,
				"Tipo de Medalha:", medalhaCombo
			};

			int option = JOptionPane.showConfirmDialog(null, message, "Remover Medalha", 
				JOptionPane.OK_CANCEL_OPTION);

			if (option == JOptionPane.OK_OPTION) {
				String tipoMedalha = (String) medalhaCombo.getSelectedItem();
				data.removerMedalha(paisSelecionado, tipoMedalha);
				atualizarTabela(); // Método para atualizar a tabela
				JOptionPane.showMessageDialog(null, "Medalha removida com sucesso!");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Erro ao remover medalha: " + ex.getMessage());
		}
	}
}
