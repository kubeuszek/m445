package br.compnatural.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import br.compnatural.State;
import br.compnatural.experiment.report.ReportUnit;
import br.compnatural.function.MathFunction;
import br.compnatural.specification.Specification;

public class GeneticAlgorithm extends OptimizationAlgorithm {

	private Random random;
	
	
	/**
	 * Crossover Probability
	 */
	public final float pc;
	public final Boolean subsDeterministc;
	
	public GeneticAlgorithm(float pc){
		this(pc, Boolean.TRUE);
	}
	
	public GeneticAlgorithm(float pc, Boolean subsDeterministc){
		this.pc = pc;
		this.subsDeterministc = subsDeterministc;
	}
	
	public State optimize(int lenPopulation, int lenGeneration, State g, MathFunction function, Specification specification, ReportUnit report){
		List<State> population = new ArrayList<State>(lenPopulation);
		random = new Random(System.currentTimeMillis());
		int generation = 0;
		State state ;
		report.setBestSoluctionIteraction(null);
		Double bestValue = null;
		int bestGeneration = -1;
		/**
		 * Initialization
		 */
		for (int i = 0; i < lenPopulation; i++) {
			state = initialize(specification);
			state.setValue(function.eval(state));
			population.add(state);
			
			if(state.getValue().equals(g.getValue()) && report.getBestSoluctionIteraction() == null){
				report.setBestSoluctionIteraction(generation + 1);
			}
		}
		
		apagar: while (generation < lenGeneration) {
			generation++;
			/**
			 * Selection
			 */
			population = organizeCouples(population, new RoulleteWheel(population));
			
			/**
			 * Recombination
			 */
			for (int i = 0; i < lenPopulation; i++) {
				State[] children = specification.recombination(population.get(i), population.get(++i), random.nextFloat() <= pc);
				children[0].setValue(function.eval(children[0]));
				children[1].setValue(function.eval(children[1]));
				population.add(children[0]);
				population.add(children[1]);
			}
			
			/**
			 * Mutation
			 */
			for (int i = 0; i < population.size(); i++) {
				population.set(i, specification.perturb(population.get(i)));
				population.get(i).setValue (function.eval(population.get(i)));
			}
			
			/**
			 * Substitution
			 */
			population = substitution(lenPopulation, population);
			
			if(bestValue != null){
				if(bestValue < population.get(0).getValue()){
					bestValue = new Double(population.get(0).getValue());
					bestGeneration = generation;
				}
			}else{
				bestGeneration = generation;
				bestValue = new Double(population.get(0).getValue());
			}
			
			for (State lState : population) {
				if(lState.getValue().equals(g.getValue()) && report.getBestSoluctionIteraction() == null){
					report.setBestSoluctionIteraction(generation);
					//break apagar;
				}
			}
		}
		
		State best;
		if(report.getBestSoluctionIteraction() == null){
			report.setBestSoluctionIteraction(0);
			best = population.get(0);			
		}else{
			best = g;
			report.setBestSoluctionIteraction(1);
		}
		
		report.setBestSoluctionSoFar(best.getValue());
		report.setFirstBestSoluctionIteraction(bestGeneration);
		
		return best;
	}

	private List<State> substitution(int lenPopulation, List<State> population) {
		if(subsDeterministc){
			Collections.sort(population, new State.InverseOrderState());
			population = population.subList(0, lenPopulation);
			return population;
		}else{
			return organizeCouples(population, new RoulleteWheel(population));
		}
		
	}
	
	public List<State> organizeCouples(List<State> population, RoulleteWheel roullete){
		List<State> retorno = new ArrayList<State>(population.size());
		
		for (int i = 0; i < population.size(); i++) {
			retorno.add(roullete.roullete.get(random.nextInt(roullete.roullete.size())));
		}
		
		return retorno;
	}
	
	@Override
	public String getName() {		
		return "Genetic Algorithm";
	}
	
	public static class RoulleteWheel{
		List<State> roullete;
		int worst = 0;
		RoulleteWheel(List<State> population){
			for (State state : population) {
				int lMax = state.getValue().intValue();
				if(worst > lMax){
					worst = lMax;
				}
			}
			
			worst =  1+ Math.abs(worst);
			
			roullete = new LinkedList<State>();
			for (State state : population) {
				int lMax = state.getValue().intValue() + worst;
				for (int i = 0; i < lMax; i++) {
					roullete.add(state);
				}
			}
		}
	}
}
