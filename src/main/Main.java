package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import escalonamento.AlgoritmoEscalonamento;
import escalonamento.FIFO;
import processo.Processo;
import util.Estado;

public class Main {
	
	public static Processo CPU = null;
		
	public static void main(String [] args) {
		Scanner scan = new Scanner(System.in);
		HashMap<Integer, Processo> processos = criaProcessos();
		AlgoritmoEscalonamento ae;
		String algoritmo = scan.nextLine();
		List<Processo> listaProcessosTerminados = new ArrayList<Processo>();
		
		switch(algoritmo) {
			
		case "SJF":
			ae = new FIFO();
			break;
		case "FIFO":
			ae = new FIFO();
			break;
		case "Circular":
			ae = new FIFO();
			break;
		case "Prioridade":
			ae = new FIFO();
			break;
		case "PrioCircular":
			ae = new FIFO();
			break;
		default:
			ae = new FIFO();
			
		}
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
				processo.setTempoExecucao(processo.getTempoExecucao() + 1);
				if (processo.getTempoExecucao() == processo.getTempoEntradaSaida()) {
					CPU = null;
					System.out.println("##### " + i + "  Processo Entrada/Saída: " + processo.getNome());
					ae.adicionaProcesso(processo);
				}
				
				if (processo.getDuracao() == processo.getTempoExecucao()) {
					CPU = null;
					System.out.println("##### " + i + "  Processo Terminado: " + processo.getNome());
					listaProcessosTerminados.add(processo);
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
		
		
		
	}

	private static HashMap<Integer, Processo> criaProcessos() {
		HashMap<Integer, Processo> processos = new HashMap<Integer, Processo>();
		Processo processo = new Processo();
		processo.setNome("P1");
		processo.setDuracao(10);
		processo.setPrioridade(10);
		processo.setEstado(Estado.PRONTO);
		processo.setTempoInicio(3);		
//		processo.setTempoEntradaSaida(6);		
		processos.put(processo.getTempoInicio(), processo);
		
		processo = new Processo();
		processo.setNome("P2");
		processo.setDuracao(15);
		processo.setPrioridade(10);
		processo.setEstado(Estado.PRONTO);
		processo.setTempoInicio(6);		
		processos.put(processo.getTempoInicio(), processo);
		
		processo = new Processo();
		processo.setNome("P3");
		processo.setDuracao(8);
		processo.setPrioridade(10);
		processo.setEstado(Estado.PRONTO);
		processo.setTempoInicio(15);		
		processos.put(processo.getTempoInicio(), processo);
		
		processo = new Processo();
		processo.setNome("P4");
		processo.setDuracao(20);
		processo.setPrioridade(10);
		processo.setEstado(Estado.PRONTO);
		processo.setTempoInicio(18);		
		processos.put(processo.getTempoInicio(), processo);
		
		processo = new Processo();
		processo.setNome("P5");
		processo.setDuracao(10);
		processo.setPrioridade(10);
		processo.setEstado(Estado.PRONTO);
		processo.setTempoInicio(22);	
		processos.put(processo.getTempoInicio(), processo);
		
		return processos;
	}

}
