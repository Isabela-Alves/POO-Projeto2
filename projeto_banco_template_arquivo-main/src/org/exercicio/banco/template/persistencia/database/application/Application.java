package org.exercicio.banco.template.persistencia.database.application;

import java.math.BigDecimal;
import java.util.Scanner;

import org.exercicio.banco.template.persistencia.database.model.Cliente;
import org.exercicio.banco.template.persistencia.database.model.ContaCorrente;
import org.exercicio.banco.template.persistencia.database.model.ContaPoupanca;
import org.exercicio.banco.template.persistencia.database.model.IConta;
import org.exercicio.banco.template.persistencia.database.model.RegistroTransacao;
import org.exercicio.banco.template.persistencia.database.util.ConfigLoader;

public class Application {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("Bem-vindo ao banco! O que você gostaria de fazer?");
			System.out.println("1. Cadastrar novo cliente");
			System.out.println("2. Selecionar cliente existente");
			System.out.println("3. Listar clientes cadastrados");
			System.out.println("4. Remover Cliente");
			System.out.println("0. Sair");

			int opcao = scanner.nextInt();
			scanner.nextLine();

			switch (opcao) {
			case 1:
				System.out.println("Digite o nome do cliente:");
				String nome = scanner.nextLine();
				System.out.println("Digite o CPF do cliente:");
				String cpf = scanner.nextLine();
				Cliente cliente = new Cliente(cpf, nome);
				PersistenciaEmArquivo.getInstance().salvarCliente(cliente);
				break;

			case 2:
				System.out.println("Insira o CPF do cliente:");
				cpf = scanner.next();

				cliente = PersistenciaEmArquivo.getInstance().localizarClientePorCPF(cpf);

				if (cliente == null) {
					System.out.println("Cliente não encontrado");
					break;
				}

				System.out.println("Cliente selecionado: " + cliente.getNome());
				System.out.println("\nO que você gostaria de fazer?\n");
				System.out.println("1. Criar nova conta");
				System.out.println("2. Ver informações das contas");
				System.out.println("3. Realizar Deposito");
				System.out.println("4. Realizar Saque");
				System.out.println("5. Realizar Transferencia");
				System.out.println("6. Imprimir extrato da movimentação");
				System.out.println("7. Remover conta");
				System.out.println("8. Consultar balanço entre contas");
				

				opcao = scanner.nextInt();
				scanner.nextLine();

				switch (opcao) {
				case 1:
					System.out.println("Escolha o tipo de conta que deseja criar");
					System.out.println("1. Conta Poupanca");
					System.out.println("2. Conta Corrente");
					
					opcao = scanner.nextInt();
					scanner.nextLine();
					
					switch (opcao) {
					case 1:
						ContaPoupanca conta= new ContaPoupanca();
						cliente.adicionarConta(conta);
						PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliente);
						System.out.println("Conta poupanca criada com sucesso!");
						break;
					case 2:
						ContaCorrente conta1= new ContaCorrente();
						cliente.adicionarConta(conta1);
						PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliente);
						System.out.println("Conta corrente criada com sucesso!");
						break;

					default:
						System.out.println("Opcao inválida.");
						break;
					}
					

				case 2:
					if (cliente.getContas().size() == 0) {
						System.err.println("Nao ha contas associada a este cliente.");
					} else {
						for (IConta c : cliente.getContas()) {
							System.out.println(c);
						}
					}
					break;
				
				case 3:
					if (cliente.getContas().size() == 0) {
						System.err.println("Nao ha contas associada a este cliente.");
					} else {
						for (IConta c : cliente.getContas()) {
							System.out.println(c);
						}
					}
					System.out.println("Em qual conta deseja realizar o deposito?");
					int opcaoContaDeposito = 0;
					double quantia = 0.0;
					opcaoContaDeposito = scanner.nextInt();
					System.out.println("Insira o valor da quantia a ser depositada: ");
					quantia = scanner.nextDouble();
					IConta temp = cliente.localizarContaNumero(opcaoContaDeposito);
					if (temp != null) {
						temp.depositar(new BigDecimal(quantia));
						PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliente);
					}
					break;
				case 4:
					if (cliente.getContas().size() == 0) {
						System.out.println("Nao ha contas associadas a este cliente.");
					} else {
						for(IConta c : cliente.getContas()) {
							System.out.println(c);
						}
					}
					System.out.println("De qual conta deseja sacar?");
					int opcaoContaSaque = 0;
					double quantia1 = 0.0;
					opcaoContaSaque = scanner.nextInt();
					System.out.println("Insira o valor:");
					quantia1 = scanner.nextDouble();
					IConta temp1 = cliente.localizarContaNumero(opcaoContaSaque);
					if (temp1 != null) {
						temp1.sacar(new BigDecimal(quantia1));
						PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliente);
					}
					break;
				
				case 5:
					if (cliente.getContas().size() == 0) {
						System.out.println("Nao ha contas associadas a este cliente.");
					} else {
						for (IConta c : cliente.getContas()) {
							System.out.println(c);
					}
					System.out.println("Digite o número da conta de origem: ");
			        int numeroContaOrigem = scanner.nextInt();
			        IConta contaOrigem = cliente.localizarContaNumero(numeroContaOrigem);

			        System.out.println("Digite o número da conta de destino: ");
			        int numeroContaDestino = scanner.nextInt();
			        IConta contaDestino = cliente.localizarContaNumero(numeroContaDestino);

			        if (contaOrigem == null || contaDestino == null) {
			            System.out.println("Conta de origem ou destino não encontrada.");
			        } else {
			            System.out.println("Digite o valor da transferência: ");
			            double quantia2 = scanner.nextDouble();
			            scanner.nextLine(); 

			            contaOrigem.transferir(contaDestino, new BigDecimal(quantia2));
			            PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliente);
			        }
			    }
					break;
				
				case 6:
					if (cliente.getContas().size() == 0) {
						System.out.println("Nao ah contas associadas a este cliente.");
					} else {
						for (IConta c : cliente.getContas()) {
							System.out.println(c);
						}
					}
					System.out.println("Insira o numero da conta:");
					int numeroConta = scanner.nextInt();
					IConta conta1 = cliente.localizarContaNumero(numeroConta);
				     if (conta1.getTransacoes().size() == 0) {
				       System.out.println("Sem transacao");
				     } else {
				    	 for (RegistroTransacao transacao : conta1.getTransacoes()) {
				    		 System.out.println(transacao);
				    	 }
				     }
				     break;
				case 7:
					if (cliente.getContas().size() == 0) {
						System.err.println("Nao ha contas associada a este cliente.");
					} else {
						for (IConta c : cliente.getContas()) {
							System.out.println(c);
						}
					}
					System.out.println("Insira o numero da conta:");
					int numeroConta1 = scanner.nextInt();
					IConta conta3 = cliente.localizarContaNumero(numeroConta1);
					cliente.removerConta(conta3);
					PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliente);
					break;
					
					
					
				default:
					System.out.println("Opção inválida");
					break;
					
				case 8:
					cliente.balancoEntreContas();
					break;
				}
				break;
			case 3:
				PersistenciaEmArquivo.getInstance().listarCliente();
				break;
			case 4:
				System.out.println("Insira o CPF do cliente:");
				cpf = scanner.next();

				cliente = PersistenciaEmArquivo.getInstance().localizarClientePorCPF(cpf);

				if (cliente == null) {
					System.out.println("Cliente não encontrado");
				} else {
					PersistenciaEmArquivo.getInstance().removerCliente(cliente);
				}
				break;
			case 5:
				System.out.println("Até logo!");
				System.exit(0);

			default:
				System.out.println("Opção inválida");
				break;
			}
		}
	}
}
				
