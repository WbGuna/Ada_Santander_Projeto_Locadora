package br.com.ada.locadora;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import br.com.ada.locadora.controller.AlugarController;
import br.com.ada.locadora.controller.ClienteFisicoController;
import br.com.ada.locadora.controller.ClienteJuridicoController;
import br.com.ada.locadora.controller.DevolucaoController;
import br.com.ada.locadora.controller.VeiculoController;
import br.com.ada.locadora.repository.AlugarRepository;
import br.com.ada.locadora.repository.VeiculoRepository;
import br.com.ada.locadora.service.AlugarService;
import br.com.ada.locadora.service.ClienteFisicoService;
import br.com.ada.locadora.service.ClienteJuridicoService;
import br.com.ada.locadora.service.VeiculoService;

public class Principal {

	private static JFrame frame;

	public static void main(String[] args) {
		VeiculoRepository veiculoRepository = new VeiculoRepository();
		AlugarRepository alugarRepository = new AlugarRepository(veiculoRepository);

		AlugarService alugarService = new AlugarService(alugarRepository, veiculoRepository);
		ClienteFisicoService clienteFisicoService = new ClienteFisicoService();
		ClienteJuridicoService clienteJuridicoService = new ClienteJuridicoService();
		VeiculoService veiculoService = new VeiculoService();

		frame = new JFrame("Locadora de Veículo Ada");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnAlugar = new JButton("Alugar");
		btnAlugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AlugarController alugarController = new AlugarController(alugarService, clienteFisicoService,
						clienteJuridicoService, veiculoService, frame);
				alugarController.setVisible(true);
			}
		});
		btnAlugar.setBounds(120, 80, 89, 23);
		frame.getContentPane().add(btnAlugar);

		JButton btnClienteFisico = new JButton("Cliente Físico");
		btnClienteFisico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClienteFisicoController clienteFisicoController = new ClienteFisicoController(clienteFisicoService,
						frame);
				clienteFisicoController.setVisible(true);
			}
		});
		btnClienteFisico.setBounds(10, 45, 150, 23);
		frame.getContentPane().add(btnClienteFisico);

		JButton btnClienteJuridico = new JButton("Cliente Jurídico");
		btnClienteJuridico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClienteJuridicoController clienteJuridicoController = new ClienteJuridicoController(
						clienteJuridicoService, frame);
				clienteJuridicoController.setVisible(true);
			}
		});
		btnClienteJuridico.setBounds(280, 45, 150, 23);
		frame.getContentPane().add(btnClienteJuridico);

		JButton btnVeiculo = new JButton("Veículo");
		btnVeiculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VeiculoController veiculoController = new VeiculoController(veiculoService, frame);
				veiculoController.setVisible(true);
			}
		});
		btnVeiculo.setBounds(175, 45, 89, 23);
		frame.getContentPane().add(btnVeiculo);

		JButton btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0); 
			}
		});
		btnFechar.setBounds(170, 110, 89, 23); 
		frame.getContentPane().add(btnFechar);

		JButton btnDevolucao = new JButton("Devolução");
		btnDevolucao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DevolucaoController devolucao = new DevolucaoController(alugarService, frame); 
				devolucao.setVisible(true);
				frame.setVisible(false); 
			}
		});
		btnDevolucao.setBounds(230, 80, 89, 23); 
		frame.getContentPane().add(btnDevolucao);

		JLabel lblProjetoAda = new JLabel("Projeto Ada Santander OOP-2 Locadora de Veículos");
		lblProjetoAda.setBounds(60, 160, 414, 14); 
		frame.getContentPane().add(lblProjetoAda);

		frame.setVisible(true);
	}

	public static void showMainFrame() {
		frame.setVisible(true); 
	}

}
