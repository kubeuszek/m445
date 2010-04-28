package br.compnatural.algorithm;

import java.util.ArrayList;
import java.util.List;

import br.compnatural.State;
import br.compnatural.coordinate.RealCoordinate;
import br.compnatural.experiment.report.ReportUnit;
import br.compnatural.function.MathFunction;
import br.compnatural.specification.RealSpecification;

public class ParticleSwarm extends OptimizationAlgorithm {
	final Boolean lBest ;
	
	private double w1;
	private double w2;
	private double c1;
	private double c2; 
	private int lenPopulation;
	
	public ParticleSwarm(int lenPopulation, double w1, double w2, double c1, double c2){
		this(lenPopulation, w1,w2, c1, c2, Boolean.TRUE);
	}
	
	public ParticleSwarm(int lenPopulation, double w1, double w2, double c1, double c2, Boolean lBest){
		this.lBest = lBest;
		this.w1 = w1;
		this.w2 = w2;
		this.c1 = c1;
		this.c2 = c2;
		this.lenPopulation = lenPopulation;
	}

	public State optimize(int max_it, MathFunction function, RealSpecification specification, ReportUnit report){
		State state;
		List<Particle> population = new ArrayList<Particle>(lenPopulation);
		int it = 0;
		/**
		 * Initialization
		 */
		for (int i = 0; i < lenPopulation; i++) {
			state = initialize(specification);
			state.setValue(function.eval(state));
			
			double[] velocity = new double[state.getCoordinate().size()];
			
			for (int j = 0; j < velocity.length; j++) {
				RealCoordinate coordinate = (RealCoordinate)state.getCoordinate().get(j);
				double max = (coordinate.max - coordinate.min)*0.5d;
				RealCoordinate velCoordinate = new RealCoordinate("v_"+coordinate.name, max * -1, max);
				velocity[j] = specification.initialize(velCoordinate);
			}
			
			Particle particle = new Particle(state, velocity);
			particle.thisBest = state;			
			population.add(particle);
			
			if(state.getValue().equals(function.getMax().getValue()) && report.getBestSoluctionIteraction() == null){
				report.setBestSoluctionIteraction(it + 1);
			}
		}
		
		while (it < max_it) {
			it++;
			
			int index = 0;
			for (Particle particle : population) {
				particle.neigBest = getBestNeighbor(index, population);
				index++;
			}
			
			
		}
		
		return null;
		
		
	}
	
	private State getBestNeighbor(int index, List<Particle> population){
		State best = population.get(index).thisBest;
		if(lBest){
			int lIndex = index -1;
			
			
			if(lIndex > 0){
				if(population.get(lIndex).thisBest.getValue() > best.getValue()){
					best = population.get(lIndex).thisBest;
				}
			}
			
			lIndex = index +1;
			if(lIndex != population.size()){
				if(population.get(lIndex).thisBest.getValue() > best.getValue()){
					best = population.get(lIndex).thisBest;
				}
			}
		}else{
			if(index == 0){
				for (Particle particle : population) {
					if(particle.thisBest.getValue() > best.getValue()){
						best = particle.thisBest;
					}
				}
				
				for (Particle particle : population) {
					particle.thisBest = best;
				}
			}
		}
		return best;
	}
	
	@Override
	public String getName() {
		return "Particle Swarm";
	}
	
	private class Particle{
		protected State state;
		protected double[] velocity;
		
		protected State thisBest;
		protected State neigBest; 
		
		public Particle(State state, double[] velocity){
			this.state = state;
			this.velocity = velocity;
		}
	}

}
