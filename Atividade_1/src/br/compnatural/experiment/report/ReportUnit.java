package br.compnatural.experiment.report;

import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.compnatural.Experiment;
import br.compnatural.State;
import br.compnatural.function.MathFunction;

public class ReportUnit {
	private Integer firstBestSoluctionIteraction;
	private Double bestSoluctionSoFar;
	private Integer bestSoluctionIteraction;
	private Double totalIteraction;
	private State initialState;
	private Long time;
	
	private Double initialAverage;
	private Double finalAverage;
	
	private Double initialSD;
	private Double finalDS;
	
	private List<ReportGraphInfo> reportGraphInfos;

	private JRBeanCollectionDataSource reportGraphInfo;
	
	public Double getInitialAverage() {
		return initialAverage;
	}

	public void setInitialAverage(Double initialAverage) {
		this.initialAverage = initialAverage;
	}

	public Double getFinalAverage() {
		return finalAverage;
	}

	public void setFinalAverage(Double finalAverage) {
		this.finalAverage = finalAverage;
	}

	public Double getInitialSD() {
		return initialSD;
	}

	public void setInitialSD(Double initialSD) {
		this.initialSD = initialSD;
	}

	public Double getFinalDS() {
		return finalDS;
	}

	public void setFinalDS(Double finalDS) {
		this.finalDS = finalDS;
	}

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

	public void setTotalIteraction(Double totalIteraction) {
		this.totalIteraction = totalIteraction;
	}

	public Double getTotalIteraction() {
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

	public void setReportGraphInfo(JRBeanCollectionDataSource reportGraphInfo) {
		this.reportGraphInfo = reportGraphInfo;
	}

	public JRBeanCollectionDataSource getReportGraphInfo() {
		return reportGraphInfo;
	}
	
	
	public List<ReportGraphInfo> getReportGraphInfos() {
		return reportGraphInfos;
	}

	public void setReportGraphInfos(List<ReportGraphInfo> reportGraphInfos) {
		this.reportGraphInfos = reportGraphInfos;
	}
}
