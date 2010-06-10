package br.compnatural.rna;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
	
	protected static Logger log = Logger.getLogger(Util.class.getName());
	
	public static double randomValueInRange(Random random, double min,
			double max) {
		double value;
		double range = Math.abs(max - min);

		value = (random.nextDouble() * range) + min;

		return value;
	}

	@SuppressWarnings("unchecked")
	public static Object createObject(String className) {
		Object object = null;
		try {
			Class classDefinition = Class.forName(className);
			object = classDefinition.newInstance();
		} catch (InstantiationException e) {
			log.log(Level.SEVERE, "erro", e);
		} catch (IllegalAccessException e) {
			log.log(Level.SEVERE, "erro", e);
		} catch (ClassNotFoundException e) {
			log.log(Level.SEVERE, "erro", e);
		}
		return object;
	}
}
