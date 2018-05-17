package escalonamento;

import java.util.ArrayList;
import java.util.List;

import processo.Processo;

public class CircularPrioridades implements AlgoritmoEscalonamento{

    static final int Max = 999, Min = 0;
    
	private List<Processo> listaProcessos = new ArrayList<Processo>();

	@Override
	public void adicionaProcesso(Processo processo) {
           listaProcessos.add(processo);
	}

	@Override
	public Processo escalonaProcesso() {
		 int minInicio = Max, maxPrioridade = Min, i = Min, tira = Min ;

         for (Processo processo : listaProcessos) {
             if(processo.getPrioridade()> maxPrioridade) {

                 maxPrioridade = processo.getPrioridade();
                 minInicio = processo.getTempoInicio();
                 tira = i;

             }
             i++;
         }
     
         return listaProcessos.remove(tira);
	}

	@Override
	public void trocaProcessoExecucao() {
		// SJF nao troca processo em execucao
		
	}
	@Override
	public int verificaPrioridade() {
		 int maxPrioridade = Min, i = Min, tira = Min ;

         for (Processo processo : listaProcessos) {
             if(processo.getPrioridade()> maxPrioridade) {

                 maxPrioridade = processo.getPrioridade();
                 tira = i;

             }
             i++;
         }
         if(listaProcessos.isEmpty()) {
        	 return 0;
         }else {
        	 return listaProcessos.get(tira).getPrioridade();
         }
     
         
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


}
