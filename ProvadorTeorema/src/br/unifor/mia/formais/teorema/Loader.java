package br.unifor.mia.formais.teorema;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.unifor.mia.formais.teorema.display.DisplayBinaryTree;
import br.unifor.mia.formais.teorema.expression.AndExpression;
import br.unifor.mia.formais.teorema.expression.Expression;
import br.unifor.mia.formais.teorema.expression.IFFExpression;
import br.unifor.mia.formais.teorema.expression.ImpExpression;
import br.unifor.mia.formais.teorema.expression.NotExpression;
import br.unifor.mia.formais.teorema.expression.OrExpression;
import br.unifor.mia.formais.teorema.expression.Variable;

public class Loader {

	private static final char connective_IMP = '>';
	private static final char connective_NOT = '!';
	private static final char connective_AND = '&';
	private static final char connective_OR = '|';
	private static final char connective_IFF = '#';
	private static Logger log = Logger.getLogger(Loader.class.getName());
	private File source;
	
	private int id = 0;

	public Loader(File file) {
		source = file;
	}
	
	public void run1() {
		Map<String, Variable> variables = new HashMap<String, Variable>(); 
		List<Expression> expressions = new ArrayList<Expression>();
		List<Expression> hypothesis = new ArrayList<Expression>();
		
		Expression conclusion = loadFile(variables, expressions, hypothesis);
		
		Expression[] args = new Expression[hypothesis.size()];
		
		int i =0;
		for (Expression expression : hypothesis) {
			args[i++] = expression;
		}
		
		Tableaux lTableaux = new Tableaux(conclusion, args);
		
		DisplayBinaryTree.display(lTableaux.tree());
		
	}

	public void run() {
		Map<String, Variable> variables = new HashMap<String, Variable>(); 
		List<Expression> expressions = new ArrayList<Expression>();
		List<Expression> hypothesis = new ArrayList<Expression>();
		
		Expression conclusion = loadFile(variables, expressions, hypothesis);
		
		Expression[] args = new Expression[hypothesis.size()];
		
		int i =0;
		for (Expression expression : hypothesis) {
			args[i++] = expression;
		}
		
		Tableaux lTableaux = new Tableaux(conclusion, args);
		
		lTableaux.mountHypothesis(0).setId(id++);
		
		Expression[] args1 = new Expression[expressions.size()+variables.values().size()];
		i =0;
		for (Expression expression : expressions) {
			args1[i++] = expression;
		}
		
		for (Expression expression : variables.values()) {
			args1[i++] = expression;
		}
		
		lTableaux.resolve(args1);
	}

	private Expression  loadFile(Map<String, Variable> variables,
			List<Expression> expressions,
			List<Expression> hypothesis) {
		Expression conclusion = null;
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(source));
			int i = 0;
			String linha = null;

			try{
				while (buffer.ready()) {
					i++;
					linha = buffer.readLine();
					conclusion = fromString(linha, variables, expressions);
					
					hypothesis.add(conclusion);
				}
				hypothesis.remove(hypothesis.size()-1);
			}catch(EmptyStackException e){
				log.log(Level.SEVERE,"Parênteses em excesso "+mensagemErro(i, linha),e);
				throw e;
			}catch(ClassCastException e){
				log.log(Level.SEVERE,"Conectivo mal posicionado "+mensagemErro(i, linha),e);
				throw e;
			}catch(RuntimeException e){
				log.log(Level.SEVERE,e.getMessage()+" "+mensagemErro(i, linha),e);
				throw e;
			}

		} catch (FileNotFoundException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
		
		return conclusion;
	}

	private String mensagemErro(int i, String linha) {
		return "Erro na linha "+i+", expressão "+linha+" - ";
	}

	public Expression fromString(String strExpression,
			Map<String, Variable> variables, List<Expression> expressions) {
		
		Stack stack = new Stack();
		String variable = "";
		
		for (char character : strExpression.toCharArray()) {
			if ('(' == character) {
				setVariable(stack, variable, variables);
				variable = "";
				stack.push(character);
			} else {				
				if (connective_OR == character || connective_AND == character || connective_NOT == character
						|| connective_IMP == character || connective_IFF == character) {
					
					setVariable(stack, variable, variables);
					variable = "";
					
					stack.push(character);
				} else if (')' == character) {				
					setVariable(stack, variable, variables);
					variable = "";
					
					Character right = new Character('(');
					Object item;
					Expression first = null;
					Expression second = null;
					Character connective = null;
					item = stack.pop();
					while(!right.equals(item)){
						if(second == null)
							second = (Expression)item;
						else if(connective == null){						
							connective = (Character) item;
							if(connective.equals(connective_NOT)){
								throw new RuntimeException("A negação deve estar na forma !(VAR)");
							}
						}else {
							first = (Expression)item;
						}
						item = stack.pop();
					}				
					if (right.equals(item)){
						if(connective == null){
							Character not = new Character(connective_NOT); 
							if(stack.size() > 0 && not.equals(stack.peek() )){							
								connective = (Character) stack.pop();
							}
						}
						if(connective != null){
							if(connective.charValue() == connective_OR){
								stack.push(new OrExpression(first, second));
								expressions.add((Expression)stack.peek());
								
								((Expression)stack.peek()).setId(id++);
							}else if(connective.charValue() == connective_AND){
								stack.push(new AndExpression(first, second));
								expressions.add((Expression)stack.peek());
								
								((Expression)stack.peek()).setId(id++);
							}else if(connective.charValue() == connective_IMP){
								stack.push(new ImpExpression(first, second));
								expressions.add((Expression)stack.peek());
								
								((Expression)stack.peek()).setId(id++);
							}else if(connective.charValue() == connective_NOT){
								stack.push(new NotExpression(second));
								expressions.add((Expression)stack.peek());
								
								((Expression)stack.peek()).setId(id++);
							}else if(connective.charValue() == connective_IFF){
								stack.push(new IFFExpression(first, second));
								expressions.add((Expression)stack.peek());
								
								((Expression)stack.peek()).setId(id++);
							}
						}else if(second != null){
							stack.push(second);
						}
					}
					
				} else {
					variable += character;
				}
			}
		}
		setVariable(stack, variable, variables);
		if(stack.size()  != 1){
			throw new RuntimeException("Parênteses em excesso "+mensagemErro(-1, strExpression));
		}
		
		return (Expression)stack.pop();
	}

	private void setVariable(Stack stack, String variable, Map<String, Variable> variables) {
		
		if (variable.trim().length() > 0) {
			Variable newVariable = new Variable(null, variable.trim());
			Variable lVariable = variables.get(newVariable.getName());
			
			if(lVariable == null){
				newVariable.setId(id++);
				stack.push(newVariable);
				variables.put(newVariable.getName(), newVariable);
			}else{
				stack.push(lVariable);
			}
		}		
	}
}
