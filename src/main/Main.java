package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		
	public static void main(String [] args) throws IOException, InterruptedException {
		Scanner scan = new Scanner(System.in);
		boolean quantum = false;
		int maiorPrioridade = 0;
		int algoritmo = Integer.MIN_VALUE;
		HashMap<String,AlgoritmoEscalonamento> aeArray = new HashMap<String,AlgoritmoEscalonamento>();
		HashMap<String,String> tempo = new HashMap<String,String>();
		System.out.println("Bem vindo ao simulador de processos! \n"
				+ "Digite qual metodo de escalonamento deseja selecionar: \n"
				+ "1 - SJF\n"
				+ "2 - Fifo\n"
				+ "3 - Circular\n"
				+ "4 - Prioridade\n"
				+ "5 - Circular com Prioridade\n"
				+ "Qualquer outro numero - Rodara todos para realizar a comparação");
		do {
			while(!scan.hasNextInt()) {
				scan.next();
				System.out.println("Digite um numero valido\n");
			}
			algoritmo = scan.nextInt();
		}while(algoritmo == Integer.MIN_VALUE);
		AlgoritmoEscalonamento ae;
		switch(algoritmo) {
		case 1:
			aeArray.put("SJF",new SJF());
			break;
		case 2:
			aeArray.put("FIFO",new FIFO());
			break;
		case 3:
			aeArray.put("Circular",new Circular());
			quantum = true;
			break;
		case 4:
			aeArray.put("Prioridades",new Prioridades());
			break;
		case 5:
			aeArray.put("CircularPrioridades",new CircularPrioridades());
			quantum = true;
			break;
		default:
			aeArray.put("Fifo",new FIFO());
			aeArray.put("SJF",new SJF());
			aeArray.put("Circular",new Circular());
			aeArray.put("Prioridades",new Prioridades());
			aeArray.put("CircularPrioridades",new CircularPrioridades());
		}
		for(Map.Entry<String,AlgoritmoEscalonamento> pair : aeArray.entrySet()) {
			ae = pair.getValue();
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
					if(algoritmo == 5 || algoritmo == 4) {
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
					if(algoritmo !=5 && algoritmo != 4){
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
			tempo.put(pair.getKey(),"Tempo Médio: " + (somaEspera / listaProcessosTerminados.size()));
		}
		String temposMedios= "";
				for(Map.Entry<String,String> pair:tempo.entrySet()) {
					temposMedios +="Algoritmo: "+ pair.getKey()+ "->"+pair.getValue()+"\n"; 
				}
				System.out.println(temposMedios);
				scan.close();
		executeInTerminal("clear;"
				+ " echo -e"+ " '"+temposMedios+ " ' ");
		
		
	}
	public static int executeInTerminal(String command) throws IOException, InterruptedException {
		 final String[] wrappedCommand;
		 boolean isMac = true, isWindows = false, isLinux = false;
		 
		    if (isWindows) {
		        wrappedCommand = new String[]{ "cmd", "/c", "start", "/wait", "cmd.exe", "/K", command };
		    }
		    else if (isLinux) {
		        wrappedCommand = new String[]{ "xterm", "-e", "bash", "-c", command};
		    }
		    else if (isMac) {
		        wrappedCommand = new String[]{"osascript",
		                "-e", "tell application \"Terminal\" to activate",
		                "-e", "tell application \"Terminal\" to do script \"" + command + ";exit\""};
		    }
		    else {
		        throw new RuntimeException("Unsupported OS ☹");
		    }
		    Process process = new ProcessBuilder(wrappedCommand)
		            .redirectErrorStream(true)
		            .start();
		    return process.waitFor();
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
