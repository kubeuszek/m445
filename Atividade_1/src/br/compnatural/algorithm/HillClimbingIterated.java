package br.compnatural.algorithm;

import br.compnatural.Specification;
import br.compnatural.State;
import br.compnatural.experiment.report.ReportUnit;
import br.compnatural.function.MathFunction;

public class HillClimbingIterated extends HillClimbing {

	public HillClimbingIterated(Boolean standard) {
		super(standard);		
	}

	public State optimize(int n_start, int max_it, State g, MathFunction function, Specification specification, ReportUnit report){
		State best = initialize(specification);
		best.setValue(function.eval(best));
		State initial = best; 
		int t = 0;
		Integer it_first_best = 0;
		
		
		
		while(t < n_start && !equals_witherror( best, g)){
			t++;
			State x = optimize(max_it, g, function, specification, report);
			
			if(x.getValue() > best.getValue()){
				best = x;
				if(0 == it_first_best){
					it_first_best = t;
				}
			}
			
			
		}
		
		report.setInitialState(initial);
		
		report.setFirstBestSoluctionIteraction(it_first_best);
		
		if(t != n_start){
			report.setBestSoluctionIteraction(t);
		}
		report.setBestSoluctionSoFar(best.getValue());
		
		return best;
	}
	
	@Override
	public String getName() {
		return "Iterated "+super.getName();
	}

}
