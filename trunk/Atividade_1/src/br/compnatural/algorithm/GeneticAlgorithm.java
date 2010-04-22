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
	
	/**
	 * Mutation Probability
	 */
	public final float pm;
	
	public GeneticAlgorithm(float pc, float pm){
		this.pc = pc;
		this.pm = pm;
	}
	
	public State optimize(int lenPopulation, int lenGeneration, State g, MathFunction function, Specification specification, ReportUnit report){
		List<State> population = new ArrayList<State>(lenPopulation);
		random = new Random(System.currentTimeMillis());
		
		State state ;
		/**
		 * Initialization
		 */
		for (int i = 0; i < lenPopulation; i++) {
			state = initialize(specification);
			state.setValue(function.eval(state));
			population.add(state);
		}
		
		int generation = 0;
		while (generation < lenGeneration) {
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
				
				population.add(children[0]);
				population.add(children[1]);
			}
			
			/**
			 * Mutation
			 */
			for (int i = 0; i < population.size(); i++) {
				if(random.nextFloat() <= pm){
					population.set(i, specification.perturb(population.get(i)));
				}
			}
			
			/**
			 * Substitution
			 */
			Collections.sort(population);
			population = population.subList(0, lenPopulation);
		}
		
		
		
		return population.get(0);
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
		RoulleteWheel(List<State> population){
			roullete = new LinkedList<State>();
			for (State state : population) {
				for (int i = 0; i < state.getValue().intValue(); i++) {
					roullete.add(state);
				}
			}
		}
	}
}