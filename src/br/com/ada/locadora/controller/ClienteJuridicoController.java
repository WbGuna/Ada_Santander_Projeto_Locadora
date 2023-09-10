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

import br.com.ada.locadora.entity.ClienteJuridico;
import br.com.ada.locadora.service.ClienteJuridicoService;

public class ClienteJuridicoController {

	private ClienteJuridicoService clienteJuridicoService;
	private JFrame mainFrame;
	private JFrame frame;
	private JTextField textFieldCnpj;
	private JTextField textFieldRazaoSocial;
	private JTextField textFieldNomeFuncionario;
	private JTextField textFieldNumeroCarteiraFuncionario;
	private JTable tableClientesJuridicos;
	private DefaultTableModel tableModel;
	private JButton btnAtualizar;
	private JTextField textFieldBuscaPorNome;

	public ClienteJuridicoController(ClienteJuridicoService clienteJuridicoService, JFrame mainFrame) {
		this.clienteJuridicoService = clienteJuridicoService;
		this.mainFrame = mainFrame;
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Locadora de Veículo Ada");
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblCnpj = new JLabel("CNPJ:");
		lblCnpj.setBounds(10, 11, 86, 14);
		frame.getContentPane().add(lblCnpj);

		try {
			MaskFormatter maskFormatter = new MaskFormatter("###.###.###/###-#");
			textFieldCnpj = new JFormattedTextField(maskFormatter);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		textFieldCnpj.setBounds(106, 8, 86, 20);
		frame.getContentPane().add(textFieldCnpj);
		textFieldCnpj.setColumns(10);

		JLabel lblRazaoSocial = new JLabel("Razão Social:");
		lblRazaoSocial.setBounds(10, 36, 86, 14);
		frame.getContentPane().add(lblRazaoSocial);

		textFieldRazaoSocial = new JTextField();
		textFieldRazaoSocial.setBounds(106, 33, 86, 20);
		frame.getContentPane().add(textFieldRazaoSocial);
		textFieldRazaoSocial.setColumns(10);

		JLabel lblNomeFuncionario = new JLabel("Nome Funcionário:");
		lblNomeFuncionario.setBounds(10, 61, 86, 14);
		frame.getContentPane().add(lblNomeFuncionario);

		textFieldNomeFuncionario = new JTextField();
		textFieldNomeFuncionario.setBounds(106, 58, 86, 20);
		frame.getContentPane().add(textFieldNomeFuncionario);
		textFieldNomeFuncionario.setColumns(10);

		JLabel lblNumeroCarteiraFuncionario = new JLabel("Número Carteira Funcionário:");
		lblNumeroCarteiraFuncionario.setBounds(10, 86, 86, 14);
		frame.getContentPane().add(lblNumeroCarteiraFuncionario);

		try {
			MaskFormatter maskFormatter = new MaskFormatter("###.###.###-#");
			textFieldNumeroCarteiraFuncionario = new JFormattedTextField(maskFormatter);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		textFieldNumeroCarteiraFuncionario.setBounds(106, 83, 86, 20);
		frame.getContentPane().add(textFieldNumeroCarteiraFuncionario);
		textFieldNumeroCarteiraFuncionario.setColumns(10);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldCnpj.getText().isEmpty() || textFieldRazaoSocial.getText().isEmpty()
						|| textFieldNomeFuncionario.getText().isEmpty()
						|| textFieldNumeroCarteiraFuncionario.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame,
							"Por favor preencha todos os campos antes de cadastrar um novo Cliente Jurídico.");
				} else {
					String cnpj = textFieldCnpj.getText();
					String razaoSocial = textFieldRazaoSocial.getText();
					String nomeFuncionario = textFieldNomeFuncionario.getText();
					String numeroCarteiraFuncionario = textFieldNumeroCarteiraFuncionario.getText();
					ClienteJuridico clienteJuridico = new ClienteJuridico(razaoSocial, nomeFuncionario,
							numeroCarteiraFuncionario, cnpj);
					clienteJuridicoService.cadastrar(clienteJuridico);
					updateTable();
				}
			}
		});
		btnCadastrar.setBounds(10, 111, 89, 23);
		frame.getContentPane().add(btnCadastrar);

		JLabel lblBuscaPorNome = new JLabel("Busca por CNPJ");
		lblBuscaPorNome.setBounds(55, 300, 100, 14);
		frame.getContentPane().add(lblBuscaPorNome);
		try {
			MaskFormatter maskFormatter = new MaskFormatter("###.###.###/###-#");
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
				String cnpj = textFieldBuscaPorNome.getText();
				List<ClienteJuridico> veiculosEncontrados = clienteJuridicoService.buscarPorCNPJ(cnpj);
				if (veiculosEncontrados.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Cliente não encontrado.");
				} else {
					showSearchResults(veiculosEncontrados);
				}
			}
		});
		btnBuscarPorNome.setBounds(60, 360, 89, 23);
		frame.getContentPane().add(btnBuscarPorNome);

		String[] columnNames = { "ID", "CNPJ", "Razão Social", "Nome Funcionário", "Número Carteira Funcionário" };
		tableModel = new DefaultTableModel(columnNames, 0);
		tableClientesJuridicos = new JTable(tableModel);
		tableClientesJuridicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(tableClientesJuridicos);
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
				int selectedRow = tableClientesJuridicos.getSelectedRow();
				if (selectedRow != -1) {
					Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
					ClienteJuridico clienteJuridico = clienteJuridicoService.buscarPorIdentificador(id);
					if (clienteJuridico != null) {
						textFieldCnpj.setText(clienteJuridico.getCnpj());
						textFieldRazaoSocial.setText(clienteJuridico.getRazaoSocial());
						textFieldNomeFuncionario.setText(clienteJuridico.getNomeFuncionario());
						textFieldNumeroCarteiraFuncionario.setText(clienteJuridico.getNumeroCarteiraFuncionario());
						btnAtualizar.setVisible(true);
					}
				}
			}
		});
		btnEditar.setBounds(10, 145, 89, 23);
		frame.getContentPane().add(btnEditar);

		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableClientesJuridicos.getSelectedRow();
				if (selectedRow != -1) {
					Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
					ClienteJuridico clienteJuridico = clienteJuridicoService.buscarPorIdentificador(id);
					if (clienteJuridico != null) {
						String cnpj = textFieldCnpj.getText();
						String razaoSocial = textFieldRazaoSocial.getText();
						String nomeFuncionario = textFieldNomeFuncionario.getText();
						String numeroCarteiraFuncionario = textFieldNumeroCarteiraFuncionario.getText();
						clienteJuridico.setCnpj(cnpj);
						clienteJuridico.setRazaoSocial(razaoSocial);
						clienteJuridico.setNomeFuncionario(nomeFuncionario);
						clienteJuridico.setNumeroCarteiraFuncionario(numeroCarteiraFuncionario);
						clienteJuridicoService.alterar(clienteJuridico);
						updateTable();
						btnAtualizar.setVisible(false);
					}
				}
			}
		});
		btnAtualizar.setBounds(109, 145, 89, 23);
		btnAtualizar.setVisible(false);
		frame.getContentPane().add(btnAtualizar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableClientesJuridicos.getSelectedRow();
				if (selectedRow != -1) {
					Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"Você tem certeza que deseja excluir este cliente jurídico?", "Aviso",
							JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_OPTION) {
						clienteJuridicoService.deletar(id);
						updateTable();
					}
				}
			}
		});
		btnExcluir.setBounds(10, 179, 89, 23);
		frame.getContentPane().add(btnExcluir);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldCnpj.setText("");
				textFieldRazaoSocial.setText("");
				textFieldNomeFuncionario.setText("");
				textFieldNumeroCarteiraFuncionario.setText("");
				textFieldBuscaPorNome.setText("");
			}
		});
		btnLimpar.setBounds(109, 179, 89, 23);
		frame.getContentPane().add(btnLimpar);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose(); // fecha a janela atual
				mainFrame.setVisible(true); // exibe a janela principal novamente
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
		for (ClienteJuridico clienteJuridico : clienteJuridicoService.buscarTodos()) {
			Object[] rowData = new Object[] { clienteJuridico.getIdentificador(), clienteJuridico.getCnpj(),
					clienteJuridico.getRazaoSocial(), clienteJuridico.getNomeFuncionario(),
					clienteJuridico.getNumeroCarteiraFuncionario() };
			tableModel.addRow(rowData);
		}
	}

	private void showSearchResults(List<ClienteJuridico> clienteJuridico) {
		JFrame searchResultsFrame = new JFrame("Resultados da busca");
		searchResultsFrame.setBounds(100, 100, 450, 300);
		searchResultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		searchResultsFrame.getContentPane().setLayout(null);

		String[] columnNames = { "ID", "CNPJ", "Razão Social", "Nome Funcionário", "Número Carteira Funcionario" };
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

		for (ClienteJuridico cliente : clienteJuridico) {
			Object[] rowData = new Object[] { cliente.getIdentificador(), cliente.getCnpj(), cliente.getRazaoSocial(),
					cliente.getNomeFuncionario(), cliente.getNumeroCarteiraFuncionario() };
			searchResultsTableModel.addRow(rowData);
		}

		searchResultsFrame.setVisible(true);
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
}
