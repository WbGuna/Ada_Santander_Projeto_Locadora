package br.com.ada.locadora.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import br.com.ada.locadora.entity.Veiculo;
import br.com.ada.locadora.enuns.TipoVeiculo;
import br.com.ada.locadora.service.VeiculoService;

@SuppressWarnings("rawtypes")
public class VeiculoController {

	private VeiculoService veiculoService;
	private JFrame mainFrame;
	private JFrame frame;
	private JTextField textFieldMarca;
	private JTextField textFieldModelo;
	private JTextField textFieldAno;
	private JTextField textFieldKilometragem;	
	private JComboBox comboBoxTipoVeiculo;
	private JTable tableVeiculos;
	private DefaultTableModel tableModel;
	private JButton btnSalvar;
	private JTextField textFieldBuscaPorNome;

	public VeiculoController(VeiculoService veiculoService, JFrame mainFrame) {
		this.veiculoService = veiculoService;
		this.mainFrame = mainFrame;
		initialize();
	}

	@SuppressWarnings({ "unchecked"})
	private void initialize() {
		frame = new JFrame("Locadora de Veículo Ada");
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblTipoVeiculo = new JLabel("Tipo do Veículo:");
		lblTipoVeiculo.setBounds(10, 11, 86, 14);
		frame.getContentPane().add(lblTipoVeiculo);

		comboBoxTipoVeiculo = new JComboBox(TipoVeiculo.values());
		comboBoxTipoVeiculo.setBounds(106, 8, 86, 20);
		frame.getContentPane().add(comboBoxTipoVeiculo);

		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setBounds(10, 36, 86, 14);
		frame.getContentPane().add(lblMarca);

		textFieldMarca = new JTextField();
		textFieldMarca.setBounds(106, 33, 86, 20);
		frame.getContentPane().add(textFieldMarca);
		textFieldMarca.setColumns(10);

		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setBounds(10, 61, 86, 14);
		frame.getContentPane().add(lblModelo);

		textFieldModelo = new JTextField();
		textFieldModelo.setBounds(106, 58, 86, 20);
		frame.getContentPane().add(textFieldModelo);
		textFieldModelo.setColumns(10);

		JLabel lblAno = new JLabel("Ano:");
		lblAno.setBounds(10, 86, 86, 14);
		frame.getContentPane().add(lblAno);

		textFieldAno = new JTextField();
		textFieldAno.setBounds(106, 83, 86, 20);
		frame.getContentPane().add(textFieldAno);
		textFieldAno.setColumns(10);

		JLabel lblKilometragem = new JLabel("Kilometragem:");
		lblKilometragem.setBounds(10, 111, 86, 14);
		frame.getContentPane().add(lblKilometragem);

		textFieldKilometragem = new JTextField();
		textFieldKilometragem.setBounds(106, 108, 86, 20);
		frame.getContentPane().add(textFieldKilometragem);
		textFieldKilometragem.setColumns(10);

		JLabel lblBuscaPorNome = new JLabel("Busca por nome");
		lblBuscaPorNome.setBounds(55, 300, 100, 14);
		frame.getContentPane().add(lblBuscaPorNome);

		textFieldBuscaPorNome = new JTextField();
		textFieldBuscaPorNome.setBounds(10, 330, 180, 20);
		frame.getContentPane().add(textFieldBuscaPorNome);
		textFieldBuscaPorNome.setColumns(10);

		JButton btnBuscarPorNome = new JButton("Buscar");
		btnBuscarPorNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = textFieldBuscaPorNome.getText();
				List<Veiculo> veiculosEncontrados = veiculoService.buscarPorNome(nome);
				if (veiculosEncontrados.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Veículo não encontrado.");
				} else {
					showSearchResults(veiculosEncontrados);
				}
			}
		});
		btnBuscarPorNome.setBounds(60, 360, 89, 23);
		frame.getContentPane().add(btnBuscarPorNome);

		String[] columnNames = { "ID", "Tipo", "Marca", "Modelo", "Ano", "Kilometragem" };
		tableModel = new DefaultTableModel(columnNames, 0);
		tableVeiculos = new JTable(tableModel);
		tableVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(tableVeiculos);
		scrollPane.setBounds(202, 11,
				frame.getWidth() - scrollPane.getX() - frame.getInsets().right - frame.getInsets().left
						- scrollPane.getInsets().right - scrollPane.getInsets().left
						- scrollPane.getVerticalScrollBar().getWidth(),
				frame.getHeight() - scrollPane.getY() - frame.getInsets().top - frame.getInsets().bottom
						- scrollPane.getInsets().top - scrollPane.getInsets().bottom
						- scrollPane.getHorizontalScrollBar().getHeight());
		frame.getContentPane().add(scrollPane);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBoxTipoVeiculo.getSelectedItem() == null || textFieldMarca.getText().isEmpty()
						|| textFieldModelo.getText().isEmpty() || textFieldAno.getText().isEmpty()
						|| textFieldKilometragem.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame,
							"Por favor preencha todos os campos antes de cadastrar um novo veículo.");
				} else {
					TipoVeiculo tipoVeiculo = (TipoVeiculo) comboBoxTipoVeiculo.getSelectedItem();
					String marca = textFieldMarca.getText();
					String modelo = textFieldModelo.getText();
					Integer ano = Integer.parseInt(textFieldAno.getText());
					Double kilometragem = Double.parseDouble(textFieldKilometragem.getText());
					Veiculo veiculo = new Veiculo(tipoVeiculo, marca, modelo, ano, kilometragem);
					veiculoService.cadastrar(veiculo);
					updateTable();
				}
			}
		});
		btnCadastrar.setBounds(10, 161, 89, 23);
		frame.getContentPane().add(btnCadastrar);

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableVeiculos.getSelectedRow();
				if (selectedRow != -1) {
					Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
					Veiculo veiculo = veiculoService.buscarPorIdentificador(id);
					if (veiculo != null) {
						comboBoxTipoVeiculo.setSelectedItem(veiculo.getTipoVeiculo());
						textFieldMarca.setText(veiculo.getMarca());
						textFieldModelo.setText(veiculo.getModelo());
						textFieldAno.setText(String.valueOf(veiculo.getAno()));
						textFieldKilometragem.setText(String.valueOf(veiculo.getKilometragem()));
						btnSalvar.setVisible(true);
					}
				}
			}
		});
		btnEditar.setBounds(10, 195, 89, 23);
		frame.getContentPane().add(btnEditar);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableVeiculos.getSelectedRow();
				if (selectedRow != -1) {
					Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
					Veiculo veiculo = veiculoService.buscarPorIdentificador(id);
					if (veiculo != null) {
						TipoVeiculo tipoVeiculo = (TipoVeiculo) comboBoxTipoVeiculo.getSelectedItem();
						String marca = textFieldMarca.getText();
						String modelo = textFieldModelo.getText();
						Integer ano = Integer.parseInt(textFieldAno.getText());
						Double kilometragem = Double.parseDouble(textFieldKilometragem.getText());
						veiculo.setTipoVeiculo(tipoVeiculo);
						veiculo.setMarca(marca);
						veiculo.setModelo(modelo);
						veiculo.setAno(ano);
						veiculo.setKilometragem(kilometragem);
						veiculoService.alterar(veiculo);
						updateTable();
						btnSalvar.setVisible(false);
					}
				}
			}
		});
		btnSalvar.setBounds(109, 195, 89, 23);
		btnSalvar.setVisible(false);
		frame.getContentPane().add(btnSalvar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableVeiculos.getSelectedRow();
				if (selectedRow != -1) {
					Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
					veiculoService.deletar(id);
					updateTable();
				}
			}
		});
		btnExcluir.setBounds(10, 229, 89, 23);
		frame.getContentPane().add(btnExcluir);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxTipoVeiculo.setSelectedIndex(-1);
				textFieldMarca.setText("");
				textFieldModelo.setText("");
				textFieldAno.setText("");
				textFieldKilometragem.setText("");
				textFieldBuscaPorNome.setText("");
			}
		});
		btnLimpar.setBounds(109, 229, 89, 23);
		frame.getContentPane().add(btnLimpar);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose(); // fecha a janela atual
				mainFrame.setVisible(true); // exibe a janela principal novamente
			}
		});
		btnVoltar.setBounds(109, 161, 89, 23);
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
		updateTable(veiculoService.buscarTodos());
	}

	private void updateTable(List<Veiculo> veiculos) {
		tableModel.setRowCount(0);
		for (Veiculo veiculo : veiculos) {
			Object[] rowData = new Object[] { veiculo.getIdentificador(), veiculo.getTipoVeiculo(), veiculo.getMarca(),
					veiculo.getModelo(), veiculo.getAno(), veiculo.getKilometragem() };
			tableModel.addRow(rowData);
		}
	}

	private void showSearchResults(List<Veiculo> veiculos) {
		JFrame searchResultsFrame = new JFrame("Resultados da busca");
		searchResultsFrame.setBounds(100, 100, 450, 300);
		searchResultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		searchResultsFrame.getContentPane().setLayout(null);

		String[] columnNames = { "ID", "Tipo", "Marca", "Modelo", "Ano", "Kilometragem" };
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

		for (Veiculo veiculo : veiculos) {
			Object[] rowData = new Object[] { veiculo.getIdentificador(), veiculo.getTipoVeiculo(), veiculo.getMarca(),
					veiculo.getModelo(), veiculo.getAno(), veiculo.getKilometragem() };
			searchResultsTableModel.addRow(rowData);
		}

		searchResultsFrame.setVisible(true);
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

}
