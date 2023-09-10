package br.com.ada.locadora.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import br.com.ada.locadora.entity.ClienteFisico;
import br.com.ada.locadora.service.ClienteFisicoService;

public class ClienteFisicoController {

	private ClienteFisicoService clienteFisicoService;
	private JFrame mainFrame;
	private JFrame frame;
	private JTextField textFieldNome;
	private JTextField textFieldIdade;
	private JFormattedTextField textFieldNumeroCarteira;
	private JFormattedTextField textFieldCpf;
	private JTable tableClientesFisicos;
	private DefaultTableModel tableModel;
	private JButton btnSalvar;
	private JTextField textFieldBuscaPorNome;

	public ClienteFisicoController(ClienteFisicoService clienteFisicoService, JFrame mainFrame) {
		this.clienteFisicoService = clienteFisicoService;
		this.mainFrame = mainFrame;
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Locadora de Veículo Ada");
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 11, 86, 14);
		frame.getContentPane().add(lblNome);

		textFieldNome = new JTextField();
		textFieldNome.setBounds(106, 8, 86, 20);
		frame.getContentPane().add(textFieldNome);
		textFieldNome.setColumns(10);

		JLabel lblIdade = new JLabel("Idade:");
		lblIdade.setBounds(10, 36, 86, 14);
		frame.getContentPane().add(lblIdade);

		textFieldIdade = new JTextField();
		textFieldIdade.setBounds(106, 33, 86, 20);
		frame.getContentPane().add(textFieldIdade);
		textFieldIdade.setColumns(10);

		JLabel lblNumeroCarteira = new JLabel("Núm Carteira:");
		lblNumeroCarteira.setBounds(10, 61, 86, 14);
		frame.getContentPane().add(lblNumeroCarteira);

		try {
			MaskFormatter maskFormatter = new MaskFormatter("###.###.###-#");
			textFieldNumeroCarteira = new JFormattedTextField(maskFormatter);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		textFieldNumeroCarteira.setBounds(106, 58, 86, 20);
		frame.getContentPane().add(textFieldNumeroCarteira);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(10, 86, 86, 14);
		frame.getContentPane().add(lblCpf);

		try {
			MaskFormatter maskFormatter = new MaskFormatter("###.###.###-##");
			textFieldCpf = new JFormattedTextField(maskFormatter);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		textFieldCpf.setBounds(106, 83, 86, 20);
		frame.getContentPane().add(textFieldCpf);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldNome.getText().isEmpty() || textFieldIdade.getText().isEmpty()
						|| textFieldNumeroCarteira.getText().isEmpty() || textFieldCpf.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame,
							"Por favor preencha todos os campos antes de cadastrar um novo Cliente Físico.");
				} else {
					String nome = textFieldNome.getText();
					Integer idade = Integer.parseInt(textFieldIdade.getText());
					String numeroCarteira = textFieldNumeroCarteira.getText();
					String cpf = textFieldCpf.getText();
					clienteFisicoService.cadastrar(new ClienteFisico(nome, idade, numeroCarteira, cpf));
					updateTable();
				}
			}
		});
		btnCadastrar.setBounds(10, 111, 89, 23);
		frame.getContentPane().add(btnCadastrar);

		JLabel lblBuscaPorNome = new JLabel("Busca por CPF");
		lblBuscaPorNome.setBounds(55, 300, 100, 14);
		frame.getContentPane().add(lblBuscaPorNome);		
		try {
			MaskFormatter maskFormatter = new MaskFormatter("###.###.###-##");
			textFieldBuscaPorNome = new JFormattedTextField(maskFormatter);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		textFieldBuscaPorNome.setBounds(10, 330, 180, 20);
		frame.getContentPane().add(textFieldBuscaPorNome);
		textFieldBuscaPorNome.setColumns(10);

		JButton btnBuscarPorNome = new JButton("Buscar");
		btnBuscarPorNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cpf = textFieldBuscaPorNome.getText();
				List<ClienteFisico> veiculosEncontrados = clienteFisicoService.buscarPorCPF(cpf);
				if (veiculosEncontrados.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Cliente não encontrado.");
				} else {
					showSearchResults(veiculosEncontrados);
				}
			}
		});
		btnBuscarPorNome.setBounds(60, 360, 89, 23);
		frame.getContentPane().add(btnBuscarPorNome);

		String[] columnNames = { "ID", "Nome", "Idade", "Número da Carteira", "CPF" };
		tableModel = new DefaultTableModel(columnNames, 0);
		tableClientesFisicos = new JTable(tableModel);
		tableClientesFisicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(tableClientesFisicos);
		scrollPane.setBounds(202, 11,
				frame.getWidth() - scrollPane.getX() - frame.getInsets().right - frame.getInsets().left
						- scrollPane.getInsets().right - scrollPane.getInsets().left
						- scrollPane.getVerticalScrollBar().getWidth(),
				frame.getHeight() - scrollPane.getY() - frame.getInsets().top - frame.getInsets().bottom
						- scrollPane.getInsets().top - scrollPane.getInsets().bottom
						- scrollPane.getHorizontalScrollBar().getHeight());
		frame.getContentPane().add(scrollPane);

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableClientesFisicos.getSelectedRow();
				if (selectedRow != -1) {
					Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
					ClienteFisico clienteFisico = clienteFisicoService.buscarPorIdentificador(id);
					if (clienteFisico != null) {
						textFieldNome.setText(clienteFisico.getNome());
						textFieldIdade.setText(String.valueOf(clienteFisico.getIdade()));
						textFieldNumeroCarteira.setText(clienteFisico.getNumeroCarteira());
						textFieldCpf.setText(clienteFisico.getCpf());
						btnSalvar.setVisible(true);
					}
				}
			}
		});
		btnEditar.setBounds(10, 145, 89, 23);
		frame.getContentPane().add(btnEditar);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableClientesFisicos.getSelectedRow();
				if (selectedRow != -1) {
					Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
					ClienteFisico clienteFisico = clienteFisicoService.buscarPorIdentificador(id);
					if (clienteFisico != null) {
						String nome = textFieldNome.getText();
						Integer idade = Integer.parseInt(textFieldIdade.getText());
						String numeroCarteira = textFieldNumeroCarteira.getText();
						String cpf = textFieldCpf.getText();
						clienteFisico.setNome(nome);
						clienteFisico.setIdade(idade);
						clienteFisico.setNumeroCarteira(numeroCarteira);
						clienteFisico.setCpf(cpf);
						clienteFisicoService.alterar(clienteFisico);
						updateTable();
						btnSalvar.setVisible(false);
					}
				}
			}
		});
		btnSalvar.setBounds(109, 145, 89, 23);
		btnSalvar.setVisible(false);
		frame.getContentPane().add(btnSalvar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableClientesFisicos.getSelectedRow();
				if (selectedRow != -1) {
					Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
					clienteFisicoService.deletar(id);
					updateTable();
				}
			}
		});
		btnExcluir.setBounds(10, 179, 89, 23);
		frame.getContentPane().add(btnExcluir);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldNome.setText("");
				textFieldIdade.setText("");
				textFieldNumeroCarteira.setText("");
				textFieldCpf.setText("");
				textFieldBuscaPorNome.setText("");
			}
		});
		btnLimpar.setBounds(109, 179, 89, 23);
		frame.getContentPane().add(btnLimpar);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainFrame.setVisible(true);
			}
		});
		btnVoltar.setBounds(109, 111, 89, 23);
		frame.getContentPane().add(btnVoltar);

		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				scrollPane.setSize(
						frame.getWidth() - scrollPane.getX() - frame.getInsets().right - frame.getInsets().left
								- scrollPane.getInsets().right - scrollPane.getInsets().left
								- scrollPane.getVerticalScrollBar().getWidth(),
						frame.getHeight() - scrollPane.getY() - frame.getInsets().top - frame.getInsets().bottom
								- scrollPane.getInsets().top - scrollPane.getInsets().bottom
								- scrollPane.getHorizontalScrollBar().getHeight());
			}
		});

		updateTable();
	}

	private void updateTable() {
		tableModel.setRowCount(0);
		for (ClienteFisico clienteFisico : clienteFisicoService.buscarTodos()) {
			Object[] rowData = new Object[] { clienteFisico.getIdentificador(), clienteFisico.getNome(),
					clienteFisico.getIdade(), clienteFisico.getNumeroCarteira(), clienteFisico.getCpf() };
			tableModel.addRow(rowData);
		}
	}

	private void showSearchResults(List<ClienteFisico> clienteFisico) {
		JFrame searchResultsFrame = new JFrame("Resultados da busca");
		searchResultsFrame.setBounds(100, 100, 450, 300);
		searchResultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		searchResultsFrame.getContentPane().setLayout(null);

		String[] columnNames = { "ID", "Nome", "Idade", "Número da Carteira", "CPF" };
		DefaultTableModel searchResultsTableModel = new DefaultTableModel(columnNames, 0);
		JTable searchResultsTable = new JTable(searchResultsTableModel);
		searchResultsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane searchResultsScrollPane = new JScrollPane(searchResultsTable);
		searchResultsScrollPane.setBounds(10, 11,
				searchResultsFrame.getWidth() - searchResultsScrollPane.getX() - searchResultsFrame.getInsets().right
						- searchResultsFrame.getInsets().left - searchResultsScrollPane.getInsets().right
						- searchResultsScrollPane.getInsets().left
						- searchResultsScrollPane.getVerticalScrollBar().getWidth(),
				searchResultsFrame.getHeight() - searchResultsScrollPane.getY() - searchResultsFrame.getInsets().top
						- searchResultsFrame.getInsets().bottom - searchResultsScrollPane.getInsets().top
						- searchResultsScrollPane.getInsets().bottom
						- searchResultsScrollPane.getHorizontalScrollBar().getHeight());
		searchResultsFrame.getContentPane().add(searchResultsScrollPane);

		for (ClienteFisico cliente : clienteFisico) {
			Object[] rowData = new Object[] { cliente.getIdentificador(), cliente.getNome(), cliente.getIdade(),
					cliente.getNumeroCarteira(), cliente.getCpf() };
			searchResultsTableModel.addRow(rowData);
		}

		searchResultsFrame.setVisible(true);
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

}
