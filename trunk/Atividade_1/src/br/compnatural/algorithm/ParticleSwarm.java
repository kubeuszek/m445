package br.compnatural.algorithm;

import java.util.ArrayList;
import java.util.List;

import br.compnatural.State;
import br.compnatural.coordinate.Coordinate;
import br.compnatural.coordinate.RealCoordinate;
import br.compnatural.experiment.report.ReportUnit;
import br.compnatural.function.MathFunction;
import br.compnatural.specification.RealSpecification;

public class ParticleSwarm extends OptimizationAlgorithm {
	final Boolean lBest ;
	
	public final double w1;
	public final double w2;
	public final double c1;
	public final double c2; 
	public final int lenPopulation;
	
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
			double[] velocityMax = new double[state.getCoordinate().size()];
			double[] velocityMin = new double[state.getCoordinate().size()];
			
			for (int j = 0; j < velocity.length; j++) {
				RealCoordinate coordinate = (RealCoordinate)state.getCoordinate().get(j);
				double max = (coordinate.max - coordinate.min)*0.5d;
				
				velocityMax[j] = max;
				velocityMin[j] = max * -1d;
				
				RealCoordinate velCoordinate = new RealCoordinate("v_"+coordinate.name, velocityMin[j], velocityMax[j]);
				velocity[j] = specification.initialize(velCoordinate);
				
			}
			
			Particle particle = new Particle(state, velocity);
			particle.thisBest = state;
			particle.velocityMax = velocityMax;
			particle.velocityMin = velocityMin;
			population.add(particle);
			
			if(state.getValue().equals(function.getMax().getValue()) && report.getBestSoluctionIteraction() == null){
				report.setBestSoluctionIteraction(it + 1);
			}
		}
		
		while (it < max_it) {
			it++;
			
			Double w = null;
			if(w1 != w2){
				w = w1 - ((double)it * (w1 - w2))/(double)max_it;
			}
			
			int index = 0;
			for (Particle particle : population) {
				particle.neigBest = getBestNeighbor(index, population);

				particle.velocity = nextVelocity(particle, it, max_it, w);

				RealCoordinate coordinate;
				for (int i = 0; i < particle.velocity.length; i++) {
					coordinate = (RealCoordinate)particle.state.getCoordinate().get(i);
					
					coordinate.setValue(coordinate.getValue() + particle.velocity[i] );
				}

				particle.state.setValue(function.eval(particle.state));
				if(particle.state.getValue().equals(function.getMax().getValue()) && report.getBestSoluctionIteraction() == null){
					report.setBestSoluctionIteraction(it);
				}

				index++;
			}
		}
		
		return null;
		
		
	}
	
	private double[] nextVelocity(Particle particle, int it, int max_it, Double w){
		
		double[] velocity = new double[particle.velocity.length];
		
		double[] cognitivo = new double[velocity.length];
		double[] social = new double[velocity.length];
		
		RealCoordinate coordinateX;
		RealCoordinate coordinateCog;
		RealCoordinate coordinateSoc;
		for (int i = 0; i < velocity.length; i++) {
			coordinateX = ((RealCoordinate)particle.state.getCoordinate().get(i));
			coordinateCog = ((RealCoordinate)particle.thisBest.getCoordinate().get(i));
			coordinateSoc = ((RealCoordinate)particle.neigBest.getCoordinate().get(i));
			
			cognitivo[i] = coordinateCog.getValue() - coordinateX.getValue();
			social[i] = coordinateSoc.getValue() - coordinateX.getValue();
			
			if(w == null){
				velocity[i] = w1 * particle.velocity[i];
			}else{
				velocity[i] = w.doubleValue() * particle.velocity[i];
			}
			
			velocity[i] = velocity[i] + (c1 *cognitivo[i]) + (c2 * social[i]);
			
			if(velocity[i] > particle.velocityMax[i]){
				velocity[i] = particle.velocityMax[i];
			}else if((particle.velocityMin[i]) > velocity[i] ){
				velocity[i] = particle.velocityMin[i]; 
			}
		}
		
		return velocity;
	}
	
	private State getBestNeighbor(int index, List<Particle> population){
		State best = population.get(index).thisBest;
		if(lBest){
			int lIndex = index -1;
			
			
			if(lIndex >= 0){
				if(population.get(lIndex).thisBest.getValue() > best.getValue()){
					best = population.get(lIndex).thisBest;
				}
			}
			
			lIndex = index +1;
			if(lIndex < population.size()){
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
		protected double[] velocityMax;
		protected double[] velocityMin;
		
		protected State thisBest;
		protected State neigBest; 
		
		public Particle(State state, double[] velocity){
			this.state = state;
			this.velocity = velocity;
		}
	}

}