package br.compnatural.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import br.compnatural.State;
import br.compnatural.coordinate.BinaryCoordinate;
import br.compnatural.coordinate.Coordinate;

public class BinarySpecification implements Specification {

	Logger log = Logger.getLogger(BinarySpecification.class.getName());
	Random random = new Random(System.currentTimeMillis());
	public final BinaryCoordinate coordinate;
	/**
	 * Mutation Probability
	 */
	public final float pm;

	public BinarySpecification(int length, float pm) {
		coordinate = new BinaryCoordinate("x", length);
		this.pm = pm;
	}

	public State initialize() {

		BinaryCoordinate lCoordinate = new BinaryCoordinate(coordinate.name,
				coordinate.length);
		initialize(lCoordinate);

		List<Coordinate> lCoordinates = new ArrayList<Coordinate>(1);
		lCoordinates.add((Coordinate) lCoordinate);

		return new State(lCoordinates, Boolean.TRUE);
	}

	public void initialize(BinaryCoordinate coordinate) {
		for (int i = 0; i < coordinate.getValue().length; i++) {
			coordinate.getValue()[i] = random.nextBoolean();
		}
	}

	@Override
	public State perturb(State pState) {
		if (random.nextFloat() <= pm) {
			BinaryCoordinate coordinate = ((BinaryCoordinate) pState
					.getCoordinate().get(0));
			State s1 = initialize();
			for (int i = 0; i < coordinate.length; i++) {
				((BinaryCoordinate) s1.getCoordinate().get(0)).getValue()[i] = coordinate
						.getValue()[i];
			}

			int indice = random.nextInt(coordinate.length);
			((BinaryCoordinate) s1.getCoordinate().get(0)).getValue()[indice] = !coordinate
					.getValue()[indice];
			log.info("Aconteceu uma mutacao no indice "+indice);
			return s1;
		} else {
			return pState;
		}
	}

	@Override
	public State[] recombination(State male, State female, Boolean crossOver) {
		BinaryCoordinate coordinateFemale = (BinaryCoordinate) female
				.getCoordinate().get(0);
		BinaryCoordinate coordinateMale = (BinaryCoordinate) male
				.getCoordinate().get(0);
		int indice = coordinateFemale.length + 1;
		if (crossOver) {
			indice = random.nextInt(coordinateFemale.length);
		}

		State s1 = initialize();
		State s2 = initialize();

		for (int i = 0; i < coordinateFemale.length; i++) {
			if (i < indice) {
				((BinaryCoordinate) s1.getCoordinate().get(0)).getValue()[i] = coordinateFemale
						.getValue()[i];
				((BinaryCoordinate) s2.getCoordinate().get(0)).getValue()[i] = coordinateMale
						.getValue()[i];
			} else {
				((BinaryCoordinate) s2.getCoordinate().get(0)).getValue()[i] = coordinateFemale
						.getValue()[i];
				((BinaryCoordinate) s1.getCoordinate().get(0)).getValue()[i] = coordinateMale
						.getValue()[i];
			}
		}

		return new State[] { s1, s2 };
	}
}
