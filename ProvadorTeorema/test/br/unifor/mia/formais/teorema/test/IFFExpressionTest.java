package br.unifor.mia.formais.teorema.test;

import br.unifor.mia.formais.teorema.expression.Expression;
import br.unifor.mia.formais.teorema.expression.IFFExpression;
import br.unifor.mia.formais.teorema.expression.Variable;
import junit.framework.TestCase;

public class IFFExpressionTest extends TestCase {

	public void testEvaluate() {
		Variable V = new Variable(true,"V");
		Variable F = new Variable(false,"F");
		
		
		Expression and1 = new IFFExpression(V, V);
		Expression and2 = new IFFExpression(V, F);
		Expression and3 = new IFFExpression(F, V);
		Expression and4 = new IFFExpression(F, F);
		
		assertEquals(Boolean.TRUE, and1.evaluate());
		assertEquals(Boolean.FALSE, and2.evaluate());
		assertEquals(Boolean.FALSE, and3.evaluate());
		assertEquals(Boolean.TRUE, and4.evaluate());
		
	}

	public void testSetValue() {
		Variable V = new Variable(true,"V");
		Variable F = new Variable(false,"F");
		
		Variable A1 = new Variable(null,"A1");
		Variable A2 = new Variable(null,"A2");
		Variable A3 = new Variable(null,"A3");
		Variable A4 = new Variable(null,"A4");
		Variable A5 = new Variable(null,"A5");
		Variable A6 = new Variable(null,"A6");
		Variable A7 = new Variable(null,"A7");
		Variable A8 = new Variable(null,"A8");
		
		
		
		Expression and1 = new IFFExpression(V, A1);
		and1.setValue(Boolean.TRUE);
		Expression and2 = new IFFExpression(V, A2);
		and2.setValue(Boolean.FALSE);
		Expression and3 = new IFFExpression(F, A3);
		and3.setValue(Boolean.TRUE);
		Expression and4 = new IFFExpression(F, A4);
		and4.setValue(Boolean.FALSE);
		
		Expression and5 = new IFFExpression(A5, V);
		and5.setValue(Boolean.TRUE);
		Expression and6 = new IFFExpression(A6, V);
		and6.setValue(Boolean.FALSE);
		Expression and7 = new IFFExpression(A7, F);
		and7.setValue(Boolean.TRUE);
		Expression and8 = new IFFExpression(A8, F);
		and8.setValue(Boolean.FALSE);
		
		assertEquals(Boolean.TRUE, A1.evaluate());
		assertEquals(Boolean.FALSE, A2.evaluate());
		assertEquals(Boolean.FALSE, A3.evaluate());
		assertEquals(Boolean.TRUE, A4.evaluate());
		
		assertEquals(Boolean.TRUE, A5.evaluate());
		assertEquals(Boolean.FALSE, A6.evaluate());
		assertEquals(Boolean.FALSE, A7.evaluate());
		assertEquals(Boolean.TRUE, A8.evaluate());
	}
}
