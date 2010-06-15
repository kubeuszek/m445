package br.compnatural.rna;

import java.util.Formatter;

public class RnaResult {
	private double weight_ini;
	private double weight_fim;
	private double eqm;
	private int it;
	private double alfa;
	private int matches;
	private int samples;
	private int error;
	

	public RnaResult(double weight_ini, double weight_fim, int it, double alfa,
			int matchs, int samples, int error, double eqm) {
		this.weight_ini = weight_ini;
		this.weight_fim = weight_fim;
		this.it = it;
		this.alfa = alfa;
		this.matches = matchs;
		this.samples = samples;
		this.error = error;
		this.eqm = eqm;
	}

	public double getWeight_ini() {
		return weight_ini;
	}

	public void setWeight_ini(double weightIni) {
		weight_ini = weightIni;
	}

	public double getWeight_fim() {
		return weight_fim;
	}

	public void setWeight_fim(double weightFim) {
		weight_fim = weightFim;
	}

	public int getIt() {
		return it;
	}

	public void setIt(int it) {
		this.it = it;
	}

	public double getAlfa() {
		return alfa;
	}

	public void setAlfa(double alfa) {
		this.alfa = alfa;
	}

	public int getMatchs() {
		return matches;
	}

	public void setMatchs(int matchs) {
		this.matches = matchs;
	}

	public int getSamples() {
		return samples;
	}

	public void setSamples(int samples) {
		this.samples = samples;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}
	
	@Override
	public String toString(){
		
		Formatter format = new Formatter() ;
		//format.format("Peso [%1.2f,%1.2f], alfa=%1.2f, iteracoes=%d, erro=%d. %d acertos %n", weight_ini, weight_fim, alfa, it, error, matches);
		format.format("[%1.2f,%1.2f] %1.2f %d %d %d %1.4f %n", weight_ini, weight_fim, alfa, it, error, matches, eqm);
		
		return format.toString();
	}
}
