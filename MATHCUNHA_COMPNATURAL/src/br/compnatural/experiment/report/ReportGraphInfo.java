package br.compnatural.experiment.report;

public class ReportGraphInfo {
	private Double avg_population;
	private Double best_particle;
	private Integer generation;
	
	public ReportGraphInfo(Double avg_population, Double best_particle, Integer generation){
		setAvg_population(avg_population);
		setBest_particle(best_particle);
		setGeneration(generation);
	}

	public Double getAvg_population() {
		return avg_population;
	}

	public void setAvg_population(Double avgPopulation) {
		avg_population = avgPopulation;
	}

	public Double getBest_particle() {
		return best_particle;
	}

	public void setBest_particle(Double bestParticle) {
		best_particle = bestParticle;
	}

	public Integer getGeneration() {
		return generation;
	}

	public void setGeneration(Integer generation) {
		this.generation = generation;
	}  
	
}
