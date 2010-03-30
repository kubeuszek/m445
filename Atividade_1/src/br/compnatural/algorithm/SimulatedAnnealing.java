package br.compnatural.algorithm;

import java.math.BigDecimal;
import java.util.Random;
import java.util.logging.Level;

import br.compnatural.Specification;
import br.compnatural.State;
import br.compnatural.experiment.report.ReportUnit;
import br.compnatural.function.MathFunction;

public class SimulatedAnnealing extends OptimizationAlgorithm {

	private double T_max;
	private Random random = new Random(System.currentTimeMillis());

	private double T_min;
	private double beta;
	private int k;

	public SimulatedAnnealing(double T_max, double T_min, double beta, int k) {
		this.T_max = T_max;
		this.T_min = T_min;
		this.beta = beta;
		this.k = k;
	}

	public State optimize(State g, MathFunction function,
			Specification specification, ReportUnit report) {
		State x = specification.initialize();
		x.setValue(function.eval(x));
		
		State best = x;

		double T = T_max;

		while (T >= T_min && !equals_witherror(x, g)) {
			for (int i = 0; i < k && !equals_witherror(x, g); i++) {
				State x_linha = perturb(x, specification);
				x_linha.setValue(function.eval(x_linha));

				log.info("para x_linha = "
						+ x_linha.getCoordinate().get(0).getValue() + " func="
						+ x_linha.getValue());

				if (x_linha.getValue() < x.getValue()) {
					x = x_linha;
					report.setFirstBestSoluctionIteraction(i);
				} else {
					double p = random.nextDouble();
					double value = StrictMath.exp((x.getValue() - x_linha.getValue()) / T);
					if (p <= value) {
						x = x_linha;
						report.setFirstBestSoluctionIteraction(i);
					}
				}
				if(best.getValue() > x.getValue()){
					best = x;
				}
			}
			T *= beta;
		}

		if (equals_witherror(x, g)) {
			log.log(Level.INFO, "encontrou! " + x.getValue() + " "
					+ g.getValue());
			report.setBestSoluctionIteraction(0);
		}
		if (!function.hasMaximum()) {
			report.setBestSoluctionSoFar(best.getValue());
		} else {
			report.setBestSoluctionSoFar((new BigDecimal(best.getValue()))
					.negate().doubleValue());
		}

		return x;
	}

	@Override
	public String getName() {
		return "Simulated Annealing (" + T_max + ", " + T_min + ", " + beta
				+ ", " + k + ")";
	}

	public double getT_max() {
		return T_max;
	}

	public double getT_min() {
		return T_min;
	}

	public double getBeta() {
		return beta;
	}

	public int getK() {
		return k;
	}

}
