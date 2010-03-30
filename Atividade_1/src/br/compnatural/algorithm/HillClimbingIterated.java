package br.compnatural.algorithm;

import java.math.BigDecimal;

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
				if(!equals_witherror( x, g)){
					it_first_best = ((t-1)*max_it)+report.getFirstBestSoluctionIteraction();
				}
			}
			
			
		}
		
		report.setInitialState(initial);
		
		if(it_first_best == 0){
			it_first_best = report.getFirstBestSoluctionIteraction();
		}
		
		report.setFirstBestSoluctionIteraction(it_first_best);
		
		if(t != n_start){
			report.setBestSoluctionIteraction(((t-1)*max_it)+report.getBestSoluctionIteraction());
		}
		
		if(function.hasMaximum()){
			report.setBestSoluctionSoFar(best.getValue());
		}else{
			report.setBestSoluctionSoFar((new BigDecimal(best.getValue())).negate().doubleValue());
		}
		
		return best;
	}
	
	@Override
	public String getName() {
		return "Iterated "+super.getName();
	}

}
