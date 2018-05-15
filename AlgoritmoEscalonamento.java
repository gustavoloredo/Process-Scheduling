package escalonamento;

import processo.Processo;

public interface AlgoritmoEscalonamento {
	
	public void adicionaProcesso(Processo processo);
	
	public Processo escalonaProcesso();
	
	public void trocaProcessoExecucao();
	
	public boolean filaVazia();
	
	public void imprimeEstadoFila();
	
	public void incrementaTempoEspera();

}
