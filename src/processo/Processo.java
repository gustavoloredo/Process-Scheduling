package processo;

import util.Estado;

public class Processo {
	
	private String nome;
	private int duracao;
	private int prioridade;
	private Estado estado;
	private int tempoInicio;
	private int tempoEspera;
	private int tempoExecucao;
	private int tempoEntradaSaida;
	
	
	public int getTempoEntradaSaida() {
		return tempoEntradaSaida;
	}
	public void setTempoEntradaSaida(int tempoEntradaSaida) {
		this.tempoEntradaSaida = tempoEntradaSaida;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getDuracao() {
		return duracao;
	}
	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}
	public int getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}
	public int getTempoInicio() {
		return tempoInicio;
	}
	public void setTempoInicio(int tempoInicio) {
		this.tempoInicio = tempoInicio;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public void imprime() {
		System.out.println("Nome: " + nome + " Execução: " + duracao);
		
	}
	public int getTempoEspera() {
		return tempoEspera;
	}
	public void setTempoEspera(int tempoEspera) {
		this.tempoEspera = tempoEspera;
	}
	public int getTempoExecucao() {
		return tempoExecucao;
	}
	public void setTempoExecucao(int tempoExecucao) {
		this.tempoExecucao = tempoExecucao;
	}
	
}
