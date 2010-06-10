package br.compnatural;

import java.util.List;

import br.compnatural.algorithm.OptimizationAlgorithm;
import br.compnatural.function.MathFunction;

public class Experiment {
	
	public Experiment(String name){
		this.name = name;
	}

	protected String name;
	
	List<AlgorithmWrapper> algorithms;
	
	
	public List<AlgorithmWrapper> getAlgorithms() {
		return algorithms;
	}

	public void setAlgorithms(List<AlgorithmWrapper> algorithms) {
		this.algorithms = algorithms;
	}

	public String getName() {
		return name;
	}

	public static class AlgorithmWrapper{
		
		private OptimizationAlgorithm optimizationAlgorithm;
		private List<MathFunction> functionUnid;
		
		public AlgorithmWrapper(OptimizationAlgorithm algorithm, List<MathFunction> functionUnid){
			this.optimizationAlgorithm = algorithm;
			this.setFunctionUnid(functionUnid);
		}
		
		public OptimizationAlgorithm getOptimizationAlgorithm() {
			return optimizationAlgorithm;
		}

		public void setFunctionUnid(List<MathFunction> functionUnid) {
			this.functionUnid = functionUnid;
		}

		public List<MathFunction> getFunctionUnid() {
			return functionUnid;
		}
	}
	
}
