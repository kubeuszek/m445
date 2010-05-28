package br.compnatural.specification;

import java.util.List;

import org.apache.commons.math.stat.descriptive.moment.StandardDeviation;

import br.compnatural.State;

public abstract class Specification {
	public abstract State initialize();
	
	public abstract State perturb(State pState);
	
	public abstract State[] recombination(State male, State female, Boolean crossOver);

	public static double getStandardDeviation(List<State> population) {
		double value;

		StandardDeviation standardDeviation = new StandardDeviation();

		double[] values = new double[population.size()];

		int i = 0;
		for (State state : population) {
			values[i] = state.getValue();
			i++;
		}

		value = standardDeviation.evaluate(values);
		return value;
	}

	public static double getAverage(List<State> population) {
		double value = 0;

		for (State state : population) {
			value += state.getValue();			
		}
		
		value = value / (double)population.size();

		return value;
	}
}
