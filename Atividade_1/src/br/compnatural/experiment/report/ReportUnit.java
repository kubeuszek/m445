package br.compnatural.experiment.report;

import br.compnatural.Experiment;
import br.compnatural.State;
import br.compnatural.function.MathFunction;

public class ReportUnit {
	private Integer firstBestSoluctionIteraction;
	private Double bestSoluctionSoFar;
	private Integer bestSoluctionIteraction;
	private Integer totalIteraction;
	private State initialState;
	private Long time;
	
	private Experiment.AlgorithmWrapper algorithm;
	private MathFunction function;

	public Integer getFirstBestSoluctionIteraction() {
		return firstBestSoluctionIteraction;
	}

	public void setFirstBestSoluctionIteraction(
			Integer firstBestSoluctionIteraction) {
		this.firstBestSoluctionIteraction = firstBestSoluctionIteraction;
	}

	public Double getBestSoluctionSoFar() {
		return bestSoluctionSoFar;
	}

	public void setBestSoluctionSoFar(Double bestSoluctionSoFar) {
		this.bestSoluctionSoFar = bestSoluctionSoFar;
	}

	public Integer getBestSoluctionIteraction() {
		return bestSoluctionIteraction;
	}

	public void setBestSoluctionIteraction(Integer bestSoluctionIteraction) {
		this.bestSoluctionIteraction = bestSoluctionIteraction;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String toString() {
		return firstBestSoluctionIteraction + ";" + bestSoluctionSoFar + ";"
				+ bestSoluctionIteraction + ";" + time;
	}

	public void setTotalIteraction(Integer totalIteraction) {
		this.totalIteraction = totalIteraction;
	}

	public Integer getTotalIteraction() {
		return totalIteraction;
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

	public State getInitialState() {
		return initialState;
	}

	public void setAlgorithm(Experiment.AlgorithmWrapper algorithm) {
		this.algorithm = algorithm;
	}

	public Experiment.AlgorithmWrapper getAlgorithm() {
		return algorithm;
	}

	public void setFunction(MathFunction function) {
		this.function = function;
	}

	public MathFunction getFunction() {
		return function;
	}
}
