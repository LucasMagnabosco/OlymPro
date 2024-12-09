package ucs.OlymPro.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MedalBoard extends JPanel implements ActionListener{

	MainScreen ms;
	private static final long serialVersionUID = 1L;
	private JTable tabelaFutebol;
	private JTable tabelaVolei;
	private JTable tabelaNatacao;
	private JPanel painelTabela;
	private JComboBox<String> seletorEsporte;
	Color blue = new Color(235, 250, 250);
	JPanel header = new JPanel();

	
	public MedalBoard(MainScreen menu) {
		this.ms = menu;
		setBounds(100, 100, 1000, 600);
		setBackground(blue);
		setLayout(null);

		// Inicializar as tabelas
		tabelaFutebol = criarTabela("Futebol");
		tabelaVolei = criarTabela("Vôlei");
		tabelaNatacao = criarTabela("Natação");
		
		// Adicionar alguns dados de teste para visualização
		DefaultTableModel modeloFutebol = (DefaultTableModel) tabelaFutebol.getModel();
		modeloFutebol.addRow(new Object[]{"Brasil", 2, 1, 0, 3});
		modeloFutebol.addRow(new Object[]{"Argentina", 1, 2, 1, 4});
		
		String frase = "Medalhas";
		Header h = new Header(frase);
		header = h.getHeader(this);
		

		JPanel painelSuperior = new JPanel();
		painelSuperior.setBounds(10, 10, 980, 40);
		painelSuperior.setBackground(blue);
		String[] esportes = {"Futebol", "Vôlei", "Natação"};
		seletorEsporte = new JComboBox<>(esportes);
		seletorEsporte.addActionListener(e -> atualizarTabelaVisivel());
		painelSuperior.add(new JLabel("Selecione o esporte: "));
		painelSuperior.add(seletorEsporte);
		

		painelTabela = new JPanel(new BorderLayout());
		int tabelaLargura = 686;
		int margemLateral = 157;
		painelTabela.setBounds(margemLateral, 60, tabelaLargura, 480);
		painelTabela.setBackground(blue);
		
		// Primeira chamada manual para mostrar a tabela inicial
		atualizarTabelaVisivel();
		
		add(painelSuperior);
		add(painelTabela);
		add(header);

	}
	
	private void atualizarTabelaVisivel() {
		painelTabela.removeAll();
		String esporteSelecionado = (String) seletorEsporte.getSelectedItem();
		
		JTable tabelaAtual = null;
		switch(esporteSelecionado) {
			case "Futebol":
				tabelaAtual = tabelaFutebol;
				break;
			case "Vôlei":
				tabelaAtual = tabelaVolei;
				break;
			case "Natação":
				tabelaAtual = tabelaNatacao;
				break;
		}
		
		if (tabelaAtual != null) {
			JScrollPane scrollPane = new JScrollPane(tabelaAtual);
			scrollPane.setBackground(new Color(242, 252, 252));
			painelTabela.add(scrollPane, BorderLayout.CENTER);
		}
		
		painelTabela.revalidate();
		painelTabela.repaint();
	}
	
	private JTable criarTabela(String esporte) {
		String[] colunas = {"País", "Ouro", "Prata", "Bronze", "Total"};
		DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		Color backgroundPrimary = new Color(242, 252, 252);
		Color backgroundAlternate = new Color(225, 240, 240);
		Color headerColor = new Color(200, 230, 230);
		
		JTable tabela = new JTable(modelo);
		tabela.getTableHeader().setReorderingAllowed(false);
		tabela.setBackground(backgroundPrimary);
	
		tabela.setBackground(backgroundPrimary);
		tabela.setForeground(Color.BLACK);
		tabela.setGridColor(headerColor);
		tabela.setSelectionBackground(new Color(185, 220, 220));
		tabela.setSelectionForeground(Color.BLACK);
		
		// Configurar cores do cabeçalho
		tabela.getTableHeader().setBackground(headerColor);
		tabela.getTableHeader().setForeground(Color.BLACK);
		
		// Configurar renderizador personalizado para as células
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);
				if (!isSelected) {
					c.setBackground(row % 2 == 0 ? backgroundPrimary : backgroundAlternate);
				}
				setHorizontalAlignment(JLabel.CENTER);
				return c;
			}
		};
		
		// Aplicar o renderizador a todas as colunas
		for (int i = 0; i < tabela.getColumnCount(); i++) {
			tabela.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		
		return tabela;
	}
	

	public void adicionarPais(String esporte, String pais, int ouro, int prata, int bronze) {
		JTable tabela = null;
		switch(esporte.toLowerCase()) {
			case "futebol":
				tabela = tabelaFutebol;
				break;
			case "vôlei":
				tabela = tabelaVolei;
				break;
			case "natação":
				tabela = tabelaNatacao;
				break;
		}
		
		if (tabela != null) {
			DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
			modelo.addRow(new Object[]{pais, ouro, prata, bronze, ouro + prata + bronze});
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
		}
	}

}
