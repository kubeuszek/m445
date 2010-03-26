/**
 * 
 */
package br.compnatural.function;

import br.compnatural.State;

/**
 * @author mathcunha
 *
 */
public interface MathFunction {
	public Double eval(State state);
	
	public String getName();
	
	public State getMax();
}
