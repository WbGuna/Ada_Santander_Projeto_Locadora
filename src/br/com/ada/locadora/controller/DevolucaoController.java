package br.com.ada.locadora.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import br.com.ada.locadora.entity.Alugar;
import br.com.ada.locadora.entity.ClienteFisico;
import br.com.ada.locadora.entity.ClienteJuridico;
import br.com.ada.locadora.entity.Pessoa;
import br.com.ada.locadora.service.AlugarService;

public class DevolucaoController {

	private AlugarService alugarService;
	private JFrame mainFrame;
	private JFrame frame;
	private JTable tableAlugueis;
	private DefaultTableModel tableModel;
	private JButton btnDevolver;
	private JFormattedTextField textFieldDataEntrega;

	public DevolucaoController(AlugarService alugarService, JFrame mainFrame) {
		this.alugarService = alugarService;
		this.mainFrame = mainFrame;
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Locadora de Veículos Ada");
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		String[] columnNames = { "ID", "Cliente", "Veículo", "Data do Aluguel", "Entrega Prevista" };
		tableModel = new DefaultTableModel(columnNames, 0);
		tableAlugueis = new JTable(tableModel);
		tableAlugueis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(tableAlugueis);
		scrollPane.setBounds(209, 8,
				frame.getWidth() - scrollPane.getX() - frame.getInsets().right - frame.getInsets().left
						- scrollPane.getInsets().right - scrollPane.getInsets().left
						- scrollPane.getVerticalScrollBar().getWidth(),
				frame.getHeight() - scrollPane.getY() - frame.getInsets().top - frame.getInsets().bottom
						- scrollPane.getInsets().top - scrollPane.getInsets().bottom
						- scrollPane.getHorizontalScrollBar().getHeight());
		frame.getContentPane().add(scrollPane);

		JLabel lblDataEntrega = new JLabel("Data de Entrega:");
		lblDataEntrega.setBounds(10, 11, 100, 14);
		frame.getContentPane().add(lblDataEntrega);

		try {
			MaskFormatter maskFormatter = new MaskFormatter("##/##/####");
			textFieldDataEntrega = new JFormattedTextField(maskFormatter);
			textFieldDataEntrega.setBounds(120, 8, 86, 20);
			frame.getContentPane().add(textFieldDataEntrega);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		btnDevolver = new JButton("Devolver");
		btnDevolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableAlugueis.getSelectedRow();
				if (selectedRow != -1) {
					Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
					String dataEntrega = textFieldDataEntrega.getText();
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"Você tem certeza que deseja devolver este aluguel?", "Aviso", JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_OPTION) {
						Alugar alugar = alugarService.devolver(id, dataEntrega);
						if (alugar != null) {
							Pessoa pessoa = alugar.getPessoa();
							String nomePessoa;
							if (pessoa instanceof ClienteFisico) {
								nomePessoa = ((ClienteFisico) pessoa).getNome();
							} else if (pessoa instanceof ClienteJuridico) {
								nomePessoa = ((ClienteJuridico) pessoa).getRazaoSocial();
							} else {
								nomePessoa = "Desconhecido";
							}
							NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
							String valorTotal = formatter.format(alugar.getValorTotal());
							String desconto = alugar.getDesconto() == null ? "Sem desconto"
									: formatter.format(alugar.getDesconto());
							String valrTotalSemDesconto = formatter
									.format(alugar.getValorTotal().add(alugar.getDesconto()));
							String message = "Cliente: " + nomePessoa + "\nVeículo alugado: "
									+ alugar.getVeiculo().getModelo() + "\nQuantos dias esteve alugado: "
									+ alugar.getQntDiasAluguel() + "\nValor Total: " + valrTotalSemDesconto
									+ "\nValor desconto: " + desconto + "\nValor Total com Desconto: " + valorTotal;
							JOptionPane.showMessageDialog(frame, message);
							updateTable();
						}
					}
				}
			}
		});
		btnDevolver.setBounds(10, 36, 89, 23);
		btnDevolver.setVisible(false);
		frame.getContentPane().add(btnDevolver);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainFrame.setVisible(true);
			}
		});
		btnVoltar.setBounds(109, 36, 89, 23);
		frame.getContentPane().add(btnVoltar);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldDataEntrega.setText("");
			}
		});
		btnLimpar.setBounds(109, 60, 89, 23);
		frame.getContentPane().add(btnLimpar);

		tableAlugueis.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				btnDevolver.setVisible(tableAlugueis.getSelectedRow() != -1);
			}
		});

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

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
}
