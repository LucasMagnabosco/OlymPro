package ucs.OlymPro.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.JOptionPane;
import java.awt.FlowLayout;

public class Cadastros extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	JPanel actionPanel;
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
		setBounds(100, 100, 1000, 600);
		setBackground(blue);
		setLayout(null);

		String frase = "Cadastros";
		Header h = new Header(frase);
		header = h.getHeader(this);
		add(header);

		// Tabela de Atletas
		String[] columnsAtleta = { "Atletas", "Equipe" };
		table_atleta = createTable(data.athleteToArray(), columnsAtleta, true);
		JScrollPane rolagem1 = createScrollPane(table_atleta, 150, 244, 297, 258);
		rolagem1.setBackground(new Color(195, 196, 199));
		rolagem1.getVerticalScrollBar().setBackground(Color.GRAY);
		add(rolagem1);

		// Tabela de Equipes
		String[] columnsTeam = { "Equipes", "Esporte" };
		table_team = createTable(data.teamToArray(), columnsTeam, false);
		JScrollPane rolagem2 = createScrollPane(table_team, 500, 244, 297, 260);
		rolagem2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		add(rolagem2);

		btnAtl = new JButton("Cadastrar atleta");
		btnAtl.setBounds(179, 123, 240, 75);
		btnAtl.addActionListener(this);
		add(btnAtl);

		btnTeam = new JButton("Cadastrar equipe");
		btnTeam.setBounds(529, 123, 240, 75);
		btnTeam.addActionListener(this);
		add(btnTeam);

		actionPanel = new JPanel();
		actionPanel.setBackground(blue);
		actionPanel.setBounds(150, 510, 647, 40);
		actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		add(actionPanel);

		JButton btnDeleteAthlete = new JButton("Deletar Atleta");
		btnDeleteAthlete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table_atleta.getSelectedRow();
				if (row != -1) {
					String athleteName = (String) table_atleta.getValueAt(row, 0);
					String teamName = (String) table_atleta.getValueAt(row, 1);
					
					if (teamName != null && !teamName.trim().isEmpty()) {
						int option = JOptionPane.showConfirmDialog(
							null,
							"O atleta ainda pertence à equipe " + teamName
						);
					}else {
						try {
							data.deleteAthlete(athleteName);
							updateAthlete();
							updateTeam();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Erro ao deletar atleta: " + ex.getMessage());
						}
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um atleta primeiro!");
				}
			}
		});
		actionPanel.add(btnDeleteAthlete);

		JButton btnDeleteTeam = new JButton("Deletar Equipe");
		btnDeleteTeam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table_team.getSelectedRow();
				if (row != -1) {
					String teamName = (String) table_team.getValueAt(row, 0);
					
					// Verifica se a equipe tem atletas
					boolean hasAthletes = false;
					for (int i = 0; i < table_atleta.getRowCount(); i++) {
						String athleteTeam = (String) table_atleta.getValueAt(i, 1);
						if (teamName.equals(athleteTeam)) {
							hasAthletes = true;
							break;
						}
					}
					
					if(hasAthletes) {
					JOptionPane.showConfirmDialog(
						null,						
						"Esta equipe ainda possui atletas vinculados. Modifique as relações primeiro."
						);

					}else{
						try {
							data.deleteTeam(teamName);
							updateTeam();
							updateAthlete();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Erro ao deletar equipe: " + ex.getMessage());
						}
					}
					
					
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma equipe primeiro!");
				}
			}
		});
		actionPanel.add(btnDeleteTeam);

		JButton btnRemoveRelation = new JButton("Remover Relação");
		btnRemoveRelation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int athleteRow = table_atleta.getSelectedRow();
				int teamRow = table_team.getSelectedRow();

				if (athleteRow != -1 && teamRow != -1) {
					String athleteName = (String) table_atleta.getValueAt(athleteRow, 0);
					String teamName = (String) table_team.getValueAt(teamRow, 0);
					try {
						data.deleteRelation(teamName, athleteName);
						updateAthlete();
						updateTeam();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Erro ao remover relação: " + ex.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um atleta e uma equipe primeiro!");
				}
			}
		});
		actionPanel.add(btnRemoveRelation);

		JButton btnAddRelation = new JButton("Adicionar à Equipe");
		btnAddRelation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int athleteRow = table_atleta.getSelectedRow();
				int teamRow = table_team.getSelectedRow();
				
				if (athleteRow != -1 && teamRow != -1) {
					String athleteName = (String) table_atleta.getValueAt(athleteRow, 0);
					String teamName = (String) table_team.getValueAt(teamRow, 0);
					try {
						data.addAthleteToTeam(teamName, athleteName);
						updateAthlete();
						updateTeam();
						JOptionPane.showMessageDialog(null, "Atleta adicionado à equipe com sucesso!");
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Erro ao adicionar atleta à equipe: " + ex.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um atleta e uma equipe primeiro!");
				}
			}
		});
		actionPanel.add(btnAddRelation);
	}

	public void actionPerformed(ActionEvent e) {
		String teste = "";
		if (e.getSource() instanceof JButton) {
			teste = ((JButton) e.getSource()).getText();
		} else if (e.getSource() instanceof JComboBox) {
			teste = (String) ((JComboBox) e.getSource()).getSelectedItem();
		}
		if (teste.equals("Return")) {
			ms.menuReturn();
		} else if (teste.equals("Cadastrar atleta")) {
			btnAtl.setVisible(false);
			atlRegister();
		} else if (teste.equals("Cadastrar equipe")) {
			btnTeam.setVisible(false);
			teamRegister();
		} else if (teste.equals("Deletar Atleta")) {
			int row = table_atleta.getSelectedRow();
			if (row != -1) {
				String athleteName = (String) table_atleta.getValueAt(row, 0);
				String teamName = (String) table_atleta.getValueAt(row, 1);
				
					try {
					data.deleteAthlete(athleteName);
					updateAthlete();
					updateTeam();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao deletar atleta: " + ex.getMessage());
				}
				
			} else {
				JOptionPane.showMessageDialog(null, "Selecione um atleta primeiro!");
			}
		} else if (teste.equals("Deletar Equipe")) {
			int row = table_team.getSelectedRow();
			if (row != -1) {
				String teamName = (String) table_team.getValueAt(row, 0);
				
				// Verifica se a equipe tem atletas
				boolean hasAthletes = false;
				for (int i = 0; i < table_atleta.getRowCount(); i++) {
					String athleteTeam = (String) table_atleta.getValueAt(i, 1);
					if (teamName.equals(athleteTeam)) {
						hasAthletes = true;
						break;
					}
				}
				
				if (hasAthletes) {
					int option = JOptionPane.showConfirmDialog(
						null,
						"Esta equipe ainda possui atletas vinculados. Deseja remover todas as relações e deletar a equipe?",
						"Confirmação",
						JOptionPane.YES_NO_OPTION
					);
					
					if (option != JOptionPane.YES_OPTION) {
						return;
					}
				}
				
				try {
					data.deleteTeam(teamName);
					updateTeam();
					updateAthlete();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao deletar equipe: " + ex.getMessage());
				}
			} else {
				JOptionPane.showMessageDialog(null, "Selecione uma equipe primeiro!");
			}
		}
	}

	public void atlRegister() {
		JPanel atlPanel = new JPanel();
		atlPanel.setBounds(140, 100, 340, 130);
		
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

		JComboBox<String> cbCountry = new JComboBox<>(data.getCountriesArray());
		cbCountry.setBounds(10, 86, 129, 20);
		atlPanel.add(cbCountry);

		txtAge = new JTextField();
		txtAge.setBounds(160, 29, 40, 20);
		txtAge.setColumns(10);
		atlPanel.add(txtAge);

		// Adicionar checkbox para atleta coletivo
		JCheckBox chkColetivo = new JCheckBox("Atleta Individual");
		chkColetivo.setBounds(160, 61, 120, 20);
		chkColetivo.setBackground(blue);
		atlPanel.add(chkColetivo);

		JButton btnRegisterAtl = new JButton("Cadastrar");
		btnRegisterAtl.setVerticalAlignment(SwingConstants.BOTTOM);
		btnRegisterAtl.setBounds(197, 81, 106, 30);
		btnRegisterAtl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					data.registerAthlete(txtName.getText(), 
									   (String)cbCountry.getSelectedItem(), 
									   txtAge.getText(),
									   chkColetivo.isSelected());
					updateAthlete();
					atlPanel.setVisible(false);
					btnAtl.setVisible(true);
				} catch (ExcecaoEspacoVazio | ExcecaoNotNumber | ExcecaoObjetoJaCadastrado e1) {
					JOptionPane.showMessageDialog(null, "Erro ao cadastrar atleta: " + e1.getMessage());
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
		
		revalidate();
		repaint();
	}

	public void teamRegister() {
		JPanel atlPanel = new JPanel();
		atlPanel.setBounds(500, 100, 340, 130);
		atlPanel.setLayout(null);
		atlPanel.setBackground(blue);
		add(atlPanel);

		JLabel lblName = new JLabel("Nome");
		lblName.setBounds(10, 11, 46, 14);
		atlPanel.add(lblName);

		JLabel lblCountry = new JLabel("País");
		lblCountry.setBounds(10, 61, 80, 14);
		atlPanel.add(lblCountry);

		txtName = new JTextField();
		txtName.setBounds(10, 29, 137, 20);
		txtName.setColumns(10);
		atlPanel.add(txtName);

		JComboBox<String> cbCountry = new JComboBox<>(data.getCountriesArray());
		cbCountry.setBounds(10, 86, 129, 20);
		atlPanel.add(cbCountry);

		// Adicionar seleção de tipo de equipe
		JLabel lblType = new JLabel("Tipo");
		lblType.setBounds(160, 11, 46, 14);
		atlPanel.add(lblType);

		String[] tiposEquipe = {"Futebol", "Vôlei"};
		JComboBox<String> cbType = new JComboBox<>(tiposEquipe);
		cbType.setBounds(160, 29, 100, 20);
		atlPanel.add(cbType);

		JButton btnRegisterAtl = new JButton("Cadastrar");
		btnRegisterAtl.setVerticalAlignment(SwingConstants.BOTTOM);
		btnRegisterAtl.setBounds(197, 81, 106, 30);
		btnRegisterAtl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					boolean isVolei = cbType.getSelectedIndex() == 1; // true para Volei, false para Futebol
					data.registerTeam(txtName.getText(), 
									(String)cbCountry.getSelectedItem(),
									isVolei);
					updateTeam();
					revalidate();
					repaint();
				} catch (ExcecaoEspacoVazio | ExcecaoObjetoJaCadastrado e1) {
					JOptionPane.showMessageDialog(null, "Erro ao cadastrar equipe: " + e1.getMessage());
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
				btnTeam.setVisible(true);
			}
		});
		atlPanel.add(btnCancel);

		revalidate();
		repaint();
	}

	public void updateTeam() {
		DefaultTableModel model = (DefaultTableModel) table_team.getModel();
		String[] columns = { "Equipes", "Esporte"};

		model.setDataVector(data.teamToArray(), columns);
		model.fireTableDataChanged();
	}

	public void updateAthlete() {
		DefaultTableModel model = (DefaultTableModel) table_atleta.getModel();
		String[] columns = { "Atletas", "Equipe" };
		model.setDataVector(data.athleteToArray(), columns);
		model.fireTableDataChanged();
	}

	private JTable createTable(Object[][] rows, String[] columns, boolean hasTeam) {
		DefaultTableModel model = new DefaultTableModel(rows, columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Torna todas as células não editáveis
			}
		};
		
		JTable table = new JTable(model);
		table.setShowVerticalLines(false);
		table.setFont(new Font("Arial", Font.PLAIN, 11));
		table.setGridColor(new Color(200, 230, 230));
		table.getTableHeader().setBackground(new Color(200, 230, 230));
		
		if (hasTeam) {
			table.getColumnModel().getColumn(0).setPreferredWidth(109);
		}else {
			table.getColumnModel().getColumn(0).setPreferredWidth(200);
		}
		
		return table;
	}

	private JScrollPane createScrollPane(JTable table, int x, int y, int width, int height) {
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(new Color(242, 252, 252));
		scrollPane.setBounds(x, y, width, height);
		
		return scrollPane;
	}
}
