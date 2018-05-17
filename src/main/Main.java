package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import escalonamento.AlgoritmoEscalonamento;
import escalonamento.Circular;
import escalonamento.CircularPrioridades;
import escalonamento.FIFO;
import escalonamento.SJF;
import escalonamento.Prioridades;
import processo.Processo;
import util.Estado;

public class Main {
	
	public static Processo CPU = null;
		
	public static void main(String [] args) {
		Scanner scan = new Scanner(System.in);
		boolean quantum = false;
		int maiorPrioridade = 0;
		String algoritmo = scan.nextLine();
		AlgoritmoEscalonamento ae;
		
		switch(algoritmo) {
			
		case "SJF":
			ae = new SJF();
			break;
		case "FIFO":
			ae = new FIFO();
			break;
		case "Circular":
			ae = new Circular();
			quantum = true;
			break;
		case "Prioridade":
			ae = new Prioridades();
			break;
		case "PrioCircular":
			ae = new CircularPrioridades();
			quantum = true;
			break;
		default:
			ae = new FIFO();
			
		}
		HashMap<Integer, Processo> processos = criaProcessos(quantum);
		List<Processo> listaProcessosTerminados = new ArrayList<Processo>();
		for (int i = 1; i < 100 ; i++) {
			
			if (processos.get(i) != null) {
				Processo processo = processos.get(i);
				ae.adicionaProcesso(processo);
				System.out.println("##### " + i + " Criando novo processo: " + processo.getNome());
			}
			
			if (CPU == null && !ae.filaVazia()) {
				Processo processo = ae.escalonaProcesso();
				processo.setEstado(Estado.EXECUCAO);
				System.out.println("##### " + i + "  Escalonando processo: " + processo.getNome());
				CPU = processo;
			}
			
			if (CPU != null) {
				Processo processo = CPU;
				maiorPrioridade = ae.verificaPrioridade();
				if(algoritmo == "PrioCircular" || algoritmo == "Prioridade") {
					maiorPrioridade = ae.verificaPrioridade();
				}
				processo.setTempoExecucao(processo.getTempoExecucao() + 1);
				if(maiorPrioridade == processo.getPrioridade() ) {
					if (processo.getTempoExecucao() == processo.getTempoEntradaSaida()) {
						CPU = null;
						System.out.println("##### " + i + "  Processo Entrada/Saída: " + processo.getNome());
						ae.adicionaProcesso(processo);
					}				
				}
				if(false){
					if (processo.getTempoExecucao() == processo.getTempoEntradaSaida() ) {
						CPU = null;
						System.out.println("##### " + i + "  Processo Entrada/Saída: " + processo.getNome());
						ae.adicionaProcesso(processo);
					}		
					
				}
				if (processo.getDuracao() == processo.getTempoExecucao()) {
					CPU = null;
					System.out.println("##### " + i + "  Processo Terminado: " + processo.getNome());
					listaProcessosTerminados.add(processo);
				}
				
				if(maiorPrioridade > processo.getPrioridade() ) {
					System.out.println("#### " +i+ " maior prioridade: "+ maiorPrioridade);
					System.out.println(processo.getPrioridade());
					CPU = null;
					System.out.println("##### " + i + "  Processo Entrada/Saída: " + processo.getNome());
					ae.adicionaProcesso(processo);
				}
			}
			
			if (i % 10 == 0) {
				ae.imprimeEstadoFila();
			}
			
			ae.incrementaTempoEspera();
			
		}
		
		
		System.out.println("\n\n\n#### Tempos de espera: ");
		int somaEspera = 0;
		for (Processo processo : listaProcessosTerminados) {
			System.out.println(processo.getTempoEspera());
			somaEspera += processo.getTempoEspera();
		}
		System.out.println("Tempo Médio: " + (somaEspera / listaProcessosTerminados.size()));

		System.out.println("Tempo Médio: " + (listaProcessosTerminados.size()));
		
		
		
	}

	private static HashMap<Integer, Processo> criaProcessos(boolean quantum) {
		HashMap<Integer, Processo> processos = new HashMap<Integer, Processo>();
		Processo processo = new Processo();
		if(quantum) {
			processo.setTempoEntradaSaida(6);
		}
		processo.setNome("P1");
		processo.setDuracao(10);
		processo.setPrioridade(10);
		processo.setEstado(Estado.PRONTO);
		processo.setTempoInicio(3);		
		processos.put(processo.getTempoInicio(), processo);
		
		processo = new Processo();
		if(quantum) {
			processo.setTempoEntradaSaida(6);
		}
		processo.setNome("P2");
		processo.setDuracao(15);
		processo.setPrioridade(9);
		processo.setEstado(Estado.PRONTO);
		processo.setTempoInicio(6);		
		processos.put(processo.getTempoInicio(), processo);
		
		processo = new Processo();
		if(quantum) {
			processo.setTempoEntradaSaida(6);
		}
		processo.setNome("P3");
		processo.setDuracao(8);
		processo.setPrioridade(8);
		processo.setEstado(Estado.PRONTO);
		processo.setTempoInicio(15);		
		processos.put(processo.getTempoInicio(), processo);
		
		processo = new Processo();
		if(quantum) {
			processo.setTempoEntradaSaida(6);
		}
		processo.setNome("P4");
		processo.setDuracao(20);
		processo.setPrioridade(10);
		processo.setEstado(Estado.PRONTO);
		processo.setTempoInicio(18);		
		processos.put(processo.getTempoInicio(), processo);
		
		processo = new Processo();
		if(quantum) {
			processo.setTempoEntradaSaida(6);
		}
		processo.setNome("P5");
		processo.setDuracao(10);
		processo.setPrioridade(12);
		processo.setEstado(Estado.PRONTO);
		processo.setTempoInicio(22);	
		processos.put(processo.getTempoInicio(), processo);
		
		return processos;
	}

}
