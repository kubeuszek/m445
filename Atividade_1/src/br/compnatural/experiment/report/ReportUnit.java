package br.compnatural.experiment.report;

public class ReportUnit {
	private Integer firstBestSoluctionIteraction;
	private Double bestSoluctionSoFar;
	private Integer bestSoluctionIteraction;
	private Long time;

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
}
