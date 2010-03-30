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
	private double T_min;
	private double beta;
	private int k;

	public SimulatedAnnealing(double T_max, double T_min, double beta, int k) {
		this.T_max = T_max;
		this.T_min = T_min;
		this.beta = beta;
		this.k = k;
	}

	private Random random = new Random(System.currentTimeMillis());

	public State optimize(State g, MathFunction function,
			Specification specification, ReportUnit report) {
		State x = specification.initialize();
		double T = T_max;

		while (T < T_min && !equals_witherror(x, g)) {
			loop_k: for (int i = 0; i < k && !equals_witherror(x, g); i++) {
				State x_linha = perturb(x, specification);
				x_linha.setValue(function.eval(x_linha));
				log.info("para x_linha = "
						+ x_linha.getCoordinate().get(0).getValue() + " func="
						+ x_linha.getValue());

				if (x_linha.getValue() < x.getValue()) {
					x = x_linha;
				} else {
					double p = random.nextDouble();

					double value = 1d / (1d + StrictMath
							.exp((x.getValue() - x_linha.getValue()) / T));
					if (p <= value) {
						x = x_linha;

						report.setFirstBestSoluctionIteraction(i);
					}
				}

				T *= beta;
			}
		}

		if (equals_witherror(x, g)) {
			log.log(Level.INFO, "encontrou! " + x.getValue() + " "
					+ g.getValue());
			report.setBestSoluctionIteraction(0);
		}
		if (function.hasMaximum()) {
			report.setBestSoluctionSoFar(x.getValue());
		} else {
			report.setBestSoluctionSoFar((new BigDecimal(x.getValue()))
					.negate().doubleValue());
		}

		return x;
	}

	@Override
	public String getName() {
		return "Simulated Annealing ("+T_max+", "+T_min+", "+beta+", "+k+")";
	}

}
