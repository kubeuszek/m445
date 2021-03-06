package br.compnatural.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.compnatural.State;
import br.compnatural.coordinate.Coordinate;
import br.compnatural.coordinate.RealCoordinate;

public class RealSpecification extends Specification {

	/**
	 * Mutation Probability
	 */
	public Float pm;

	Logger log = Logger.getLogger(RealSpecification.class.getName());

	Random random = new Random(System.currentTimeMillis());

	Random random2 = new Random(System.currentTimeMillis());

	public static final long error = 1000000;

	public final Boolean crossOverTwoPoint;
	public final Boolean perturbGaussian;

	List<RealCoordinate> coordinates = new ArrayList<RealCoordinate>();

	public RealSpecification() {
		this(Boolean.FALSE, Boolean.FALSE);
	}

	public RealSpecification(Boolean crossOverTwoPoint, Boolean perturbGaussian) {
		this.crossOverTwoPoint = crossOverTwoPoint;
		this.perturbGaussian = perturbGaussian;
	}

	public void addCoordinate(String name, double min, double max) {
		coordinates.add(new RealCoordinate(name, min, max));
	}

	public State initialize() {

		RealCoordinate coordinate;
		List<Coordinate> lCoordinates = new ArrayList<Coordinate>(coordinates
				.size());
		for (int i = 0; i < coordinates.size(); ++i) {
			coordinate = new RealCoordinate(((RealCoordinate) coordinates
					.get(i)).name, ((RealCoordinate) coordinates.get(i)).min,
					((RealCoordinate) coordinates.get(i)).max);

			coordinate.setValue(initialize(coordinate));

			if (!isInRange(coordinate, coordinate.getValue())) {
				i--;
				log
						.log(Level.SEVERE, coordinate
								+ " - valor fora do intervalo "
								+ coordinate.getValue());
			}

			lCoordinates.add((Coordinate) coordinate);
		}

		return new State(lCoordinates, Boolean.FALSE);
	}

	public double initialize(RealCoordinate coordinate) {
		double value;
		double range = Math.abs((coordinate.max - coordinate.min));

		value = (random.nextDouble() * range) + coordinate.min;

		return value;
	}

	public boolean isInRange(RealCoordinate coordinate, double value) {

		double diff = value - coordinate.min;
		double diff2 = coordinate.max - value;

		log.fine(coordinate + " -- " + diff + ", " + diff2 + ", " + value);

		diff *= error;
		diff2 *= error;

		// log.info(diff +", "+diff2 + ", "+value);

		if ((int) diff < 0) {
			return false;
		}

		if ((int) diff2 < 0) {
			return false;
		}

		return true;
	}
	
	public State perturb(State pState, Integer index) {
		if (pm != null && random2.nextFloat() <= pm.floatValue()) {			

			State retorno = new State(pState, Boolean.FALSE);
			RealCoordinate coordinate = (RealCoordinate) retorno
					.getCoordinate().get(index);

			if (!perturbGaussian) {
				double min = coordinate.getValue() - coordinate.delta;
				double max = coordinate.getValue() + coordinate.delta;
				double aux;

				if (min > max) {
					aux = max;
					max = min;
					min = aux;
				}

				if (min < coordinate.min) {
					min = coordinate.min;
				}

				if (max > coordinate.max) {
					max = coordinate.max;
				}

				RealCoordinate vizinho = new RealCoordinate("v_"
						+ coordinate.name, min, max);
				vizinho.setValue(coordinate.value);

				log.fine(vizinho.toString() + " delta " + coordinate.delta);

				double value = initialize(vizinho);

				while (!isInRange(vizinho, value)) {
					value = initialize(vizinho);
					log.log(Level.SEVERE, vizinho
							+ " - valor fora do intervalo "
							+ vizinho.getValue());
				}

				coordinate.setValue(value);

				
			} else {
				coordinate.setValue(coordinate.value + random2.nextGaussian());
				
				if(coordinate.getValue() > coordinate.max){
					coordinate.setValue(coordinate.max);
				}else if(coordinate.getValue() < coordinate.min){
					coordinate.setValue(coordinate.min);
				}				
			}
			return retorno;
		} else {
			return pState;
		}
	}

	public State perturb(State pState) {
		int index = 0;

		index = random2.nextInt(pState.getCoordinate().size());
		return this.perturb(pState, index);
	}

	@Override
	public State[] recombination(State male, State female, Boolean crossOver) {
		List<Coordinate> coordinateFemale = female.getCoordinate();
		List<Coordinate> coordinateMale = female.getCoordinate();

		int indice_min = coordinateFemale.size() + 1;
		int indice_max = coordinateFemale.size() + 1;

		if (crossOver) {
			indice_min = random.nextInt(coordinateFemale.size());

			if (crossOverTwoPoint) {
				indice_max = random.nextInt(coordinateFemale.size());
				if (indice_min > indice_max) {
					int aux = indice_max;
					indice_max = indice_min;
					indice_min = aux;
				}
			}
		}

		State s1 = initialize();
		State s2 = initialize();

		for (int i = 0; i < coordinateFemale.size(); i++) {
			if (i < indice_max && i > indice_min) {
				s2.getCoordinate().set(i, coordinateFemale.get(i));
				s1.getCoordinate().set(i, coordinateMale.get(i));
			} else {
				s1.getCoordinate().set(i, coordinateFemale.get(i));
				s2.getCoordinate().set(i, coordinateMale.get(i));
			}
		}

		return new State[] { s1, s2 };
	}
}
