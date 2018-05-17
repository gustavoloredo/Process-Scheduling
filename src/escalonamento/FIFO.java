package escalonamento;

import java.util.ArrayList;
import java.util.List;

import processo.Processo;

public class FIFO  implements AlgoritmoEscalonamento {
	
	private List<Processo> listaProcessos = new ArrayList<Processo>();

	@Override
	public void adicionaProcesso(Processo processo) {
		listaProcessos.add(processo);
		
	}

	@Override
	public Processo escalonaProcesso() {
		return listaProcessos.remove(0);		
	}

	@Override
	public void trocaProcessoExecucao() {
		// Fifo nao troca processo em execucao
		
	}

	@Override
	public boolean filaVazia() {
		return listaProcessos.isEmpty();
	}

	@Override
	public void imprimeEstadoFila() {
		System.out.println("Lista de Processos Prontos");
		System.out.println("--------------------------");
		for (Processo processo : listaProcessos) {
			processo.imprime();
		}
		System.out.println("--------------------------");
		
	}

	@Override
	public void incrementaTempoEspera() {
		for (Processo processo : listaProcessos) {
			processo.setTempoEspera(processo.getTempoEspera() + 1);
		}		
	}

	@Override
	public int verificaPrioridade() {
		// TODO Auto-generated method stub
		return 0;
	}

}
