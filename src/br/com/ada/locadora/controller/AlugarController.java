package br.com.ada.locadora.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

import br.com.ada.locadora.entity.Alugar;
import br.com.ada.locadora.entity.ClienteFisico;
import br.com.ada.locadora.entity.ClienteJuridico;
import br.com.ada.locadora.entity.Pessoa;
import br.com.ada.locadora.entity.Veiculo;
import br.com.ada.locadora.service.AlugarService;
import br.com.ada.locadora.service.ClienteFisicoService;
import br.com.ada.locadora.service.ClienteJuridicoService;
import br.com.ada.locadora.service.VeiculoService;

@SuppressWarnings("rawtypes")
public class AlugarController {

	private AlugarService alugarService;
	private ClienteFisicoService clienteFisicoService;
	private ClienteJuridicoService clienteJuridicoService;
	private VeiculoService veiculoService;
	private JFrame mainFrame;
	private JFrame frame;
	private JComboBox comboBoxCliente;
	private JComboBox comboBoxVeiculo;
	private JTextField textFieldDataAluguel;
	private JTextField textFieldEntregaPrevista;
	private JTable tableAlugueis;
	private DefaultTableModel tableModel;

	public AlugarController(AlugarService alugarService, ClienteFisicoService clienteFisicoService,
			ClienteJuridicoService clienteJuridicoService, VeiculoService veiculoService, JFrame mainFrame) {
		this.alugarService = alugarService;
		this.clienteFisicoService = clienteFisicoService;
		this.clienteJuridicoService = clienteJuridicoService;
		this.veiculoService = veiculoService;
		this.mainFrame = mainFrame;
		initialize();
	}

	@SuppressWarnings("unchecked")
	private void initialize() {
		frame = new JFrame("Locadora de Veículos Ada");
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(10, 11, 86, 14);
		frame.getContentPane().add(lblCliente);

		comboBoxCliente = new JComboBox();
		comboBoxCliente.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXX");
		comboBoxCliente.setBounds(106, 8, 86, 20);
		frame.getContentPane().add(comboBoxCliente);

		JLabel lblVeiculo = new JLabel("Veículo:");
		lblVeiculo.setBounds(10, 36, 86, 14);
		frame.getContentPane().add(lblVeiculo);

		comboBoxVeiculo = new JComboBox();
		comboBoxVeiculo.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXX");
		comboBoxVeiculo.setBounds(106, 33, 86, 20);
		frame.getContentPane().add(comboBoxVeiculo);

		JLabel lblDataAluguel = new JLabel("Data do Aluguel:");
		lblDataAluguel.setBounds(10, 61, 86, 14);
		frame.getContentPane().add(lblDataAluguel);
		
        try {
			MaskFormatter maskFormatter = new MaskFormatter("##/##/####");
			textFieldDataAluguel = new JFormattedTextField(maskFormatter);
			textFieldDataAluguel.setBounds(106, 58, 86, 20);
			frame.getContentPane().add(textFieldDataAluguel);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JLabel lblEntregaPrevista = new JLabel("Entrega Prevista:");
        lblEntregaPrevista.setBounds(10, 86, 86, 14);
        frame.getContentPane().add(lblEntregaPrevista);

        try {
            MaskFormatter maskFormatter = new MaskFormatter("##/##/####");
            textFieldEntregaPrevista = new JFormattedTextField(maskFormatter);
            textFieldEntregaPrevista.setBounds(106, 83, 86, 20);
            frame.getContentPane().add(textFieldEntregaPrevista);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] columnNames = { "ID", "Cliente", "Veículo", "Data do Aluguel", "Entrega Prevista" };
        tableModel = new DefaultTableModel(columnNames, 0);
        tableAlugueis = new JTable(tableModel);
        tableAlugueis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tableAlugueis);
        scrollPane.setBounds(202, 11,
                frame.getWidth() - scrollPane.getX() - frame.getInsets().right - frame.getInsets().left
                        - scrollPane.getInsets().right - scrollPane.getInsets().left
                        - scrollPane.getVerticalScrollBar().getWidth(),
                frame.getHeight() - scrollPane.getY() - frame.getInsets().top - frame.getInsets().bottom
                        - scrollPane.getInsets().top - scrollPane.getInsets().bottom
                        - scrollPane.getHorizontalScrollBar().getHeight());
        frame.getContentPane().add(scrollPane);

        JButton btnBuscarVeiculosAlugados = new JButton("Buscar Veículos Alugados");
        btnBuscarVeiculosAlugados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	List<Veiculo> todosVeiculos = veiculoService.buscarTodos();
                List<Veiculo> veiculosAlugados = alugarService.buscarVeiculosAlugados(todosVeiculos);
                if (veiculosAlugados.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Veículo não encontrado.");
                } else {
                    showVehicleGrid(veiculosAlugados);
                }
            }
        });
        btnBuscarVeiculosAlugados.setBounds(10, 120, 182, 23);
        frame.getContentPane().add(btnBuscarVeiculosAlugados);

        JButton btnBuscarVeiculosDisponiveis = new JButton("Buscar Veículos Disponíveis");
        btnBuscarVeiculosDisponiveis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	List<Veiculo> todosVeiculos = veiculoService.buscarTodos();
                List<Veiculo> veiculosDisponiveis = alugarService.buscarVeiculosDisponiveis(todosVeiculos);
                if (veiculosDisponiveis.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Veículo não encontrado.");
                } else {
                    showVehicleGrid(veiculosDisponiveis);
                }
            }
        });
        btnBuscarVeiculosDisponiveis.setBounds(10, 150, 182, 23);
        frame.getContentPane().add(btnBuscarVeiculosDisponiveis);

        JButton btnCadastrar = new JButton("Alugar");
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(comboBoxCliente.getSelectedItem() == null || comboBoxVeiculo.getSelectedItem() == null ||
            			textFieldDataAluguel.getText().isEmpty() || textFieldEntregaPrevista.getText().isBlank()) {
            		JOptionPane.showMessageDialog(frame,
							"Por favor preencha todos os campos antes de tentar concluir um novo aluguel de veiculos.");
            	} else {          		
            		Pessoa pessoa = (Pessoa) comboBoxCliente.getSelectedItem();
            		Veiculo veiculo = (Veiculo) comboBoxVeiculo.getSelectedItem();
            		if(veiculo.getEstaAlugado()) {
            			JOptionPane.showMessageDialog(frame,
    							"Veiculo já alugado, escolha outro da lista por favor!");
            		} else {           			
            			String dataAluguel = textFieldDataAluguel.getText();
            			String entregaPrevista = textFieldEntregaPrevista.getText();
            			alugarService.alugar(pessoa, veiculo, dataAluguel, entregaPrevista);
            		}
            		updateTable();
            	}
            }
        });
        btnCadastrar.setBounds(10, 180, 89, 23);
        frame.getContentPane().add(btnCadastrar);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                mainFrame.setVisible(true);
            }
        });
        btnVoltar.setBounds(103, 180, 89, 23);
        frame.getContentPane().add(btnVoltar);

        JButton btnClear = new JButton("Limpar");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (comboBoxCliente.getItemCount() > 0) {
                    comboBoxCliente.setSelectedIndex(0);
                }
                if (comboBoxVeiculo.getItemCount() > 0) {
                    comboBoxVeiculo.setSelectedIndex(0);
                }
                textFieldDataAluguel.setText("");
                textFieldEntregaPrevista.setText("");
            }
        });
        btnClear.setBounds(10, 210, 89, 23);
        frame.getContentPane().add(btnClear);

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

        updateClientes();
        updateVeiculos();
        updateTable();
    }

    @SuppressWarnings("unchecked")
    private void updateClientes() {
        comboBoxCliente.removeAllItems();
        for (ClienteFisico clienteFisico : clienteFisicoService.buscarTodos()) {
            comboBoxCliente.addItem(clienteFisico);
        }
        for (ClienteJuridico clienteJuridico : clienteJuridicoService.buscarTodos()) {
            comboBoxCliente.addItem(clienteJuridico);
        }
    }

    @SuppressWarnings("unchecked")
    private void updateVeiculos() {
        comboBoxVeiculo.removeAllItems();
        for (Veiculo veiculo : veiculoService.buscarTodos()) {
            comboBoxVeiculo.addItem(veiculo);
        }
    }

    private void updateTable() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		tableModel.setRowCount(0);
		for (Alugar alugar : alugarService.buscarTodos()) {
			String dataAluguel = dateFormat.format(alugar.getDataAluguel());
			String entregaPrevista = dateFormat.format(alugar.getEntregaPrevista());
			Object[] rowData = new Object[] { alugar.getIdentificador(), alugar.getPessoa(), alugar.getVeiculo(),
					dataAluguel, entregaPrevista };
			tableModel.addRow(rowData);
		}
	}

	private void showVehicleGrid(List<Veiculo> veiculos) {
		JFrame gridFrame = new JFrame("Veículos");
		gridFrame.setBounds(100, 100, 450, 300);
		gridFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gridFrame.getContentPane().setLayout(null);

		String[] columnNames = { "ID", "Tipo", "Marca", "Modelo", "Ano", "Kilometragem" };
		DefaultTableModel gridTableModel = new DefaultTableModel(columnNames, 0);
		JTable gridTable = new JTable(gridTableModel);
		gridTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane gridScrollPane = new JScrollPane(gridTable);
		gridScrollPane.setBounds(10, 11,
				gridFrame.getWidth() - gridScrollPane.getX() - gridFrame.getInsets().right - gridFrame.getInsets().left
						- gridScrollPane.getInsets().right - gridScrollPane.getInsets().left
						- gridScrollPane.getVerticalScrollBar().getWidth(),
				gridFrame.getHeight() - gridScrollPane.getY() - gridFrame.getInsets().top - gridFrame.getInsets().bottom
						- gridScrollPane.getInsets().top - gridScrollPane.getInsets().bottom
						- gridScrollPane.getHorizontalScrollBar().getHeight());
		gridFrame.getContentPane().add(gridScrollPane);

		for (Veiculo veiculo : veiculos) {
			Object[] rowData = new Object[] { veiculo.getIdentificador(), veiculo.getTipoVeiculo(), veiculo.getMarca(),
					veiculo.getModelo(), veiculo.getAno(), veiculo.getKilometragem() };
			gridTableModel.addRow(rowData);
		}

		gridFrame.setVisible(true);
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
}
