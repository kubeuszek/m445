package br.com.mathcunha.recibo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import br.com.mathcunha.recibo.report.ReportManager;


public class ImprimirRecibo {
	
	protected static Logger log = Logger.getLogger(ImprimirRecibo.class
			.getName());
	public static void gerarRecibo(int mes, int year, Salario salario) {
		
		GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
		calendar.set(Calendar.MONTH, mes);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DATE, 1);
		
		GregorianCalendar calendarClone = (GregorianCalendar) calendar.clone();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		calendar.add(Calendar.DATE, -1);
		
		Date dataPagamento = BusinessDayUtil.getNextBusinessDay(calendar.getTime());
		parameters.put("DATA", dataPagamento);
		
		
		calendar.add(Calendar.DATE, 1);
		calendar.add(Calendar.MONTH, -1);
		
		parameters.put("REFERENCIA", calendar.getTime());
		
		parameters.put("SALARIO", "");
		
		parameters.put("ds", salario.getDescontos());

		calendarClone.add(Calendar.MONTH, 1);
		
		parameters.put("FECHAMENTO", calendar.get(Calendar.MONTH)+1+" de "+calendar.get(Calendar.YEAR)+".");
		parameters.put("FECHAMENTO_TRANS", calendarClone.get(Calendar.MONTH)+" de "+calendarClone.get(Calendar.YEAR)+".");
		
		ReportManager.saveReport("/recibo_salario.jrxml", parameters, "./recibo_"+mes+".pdf");
		
		ReportManager.saveReport("/recibo_transporte.jrxml", parameters, "./recibo_transporte"+mes+".pdf");
	}
	
	public static void main(String[] args){
		Salario salario = new Salario();
		
		int i =0;
		salario.setValorBase(new Double(args[i++]));
		
		salario.setValorTransporte(new Double(args[i++]));
		
		salario.setMes(new Integer(args[i++]));
		
		salario.setYear(new Integer(args[i++]));
		
		
		salario.setDescontos(new ArrayList<Desconto>(args.length));
		
		for (; i < args.length; i+=2) {
			salario.getDescontos().add(new Desconto(new Double(args[i])/(double)100, args[i+1], salario));
		}
		
		salario.calcValorLiquido();
		
		gerarRecibo(salario.getMes(), salario.getYear(), salario);
	}

}
