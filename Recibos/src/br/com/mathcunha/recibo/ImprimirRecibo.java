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
	public static void gerarRecibo(int mes, Salario salario) {
		
		GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
		calendar.set(Calendar.MONTH, mes);
		calendar.set(Calendar.DATE, 1);
		
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		calendar.add(Calendar.DATE, -1);
		
		Date dataPagamento = BusinessDayUtil.getNextBusinessDay(calendar.getTime());
		parameters.put("DATA", dataPagamento);
		
		
		calendar.add(Calendar.DATE, 1);
		calendar.add(Calendar.MONTH, -1);
		
		parameters.put("REFERENCIA", calendar.getTime());
		
		parameters.put("SALARIO", "");
		
		parameters.put("ds", salario.getDescontos());

		
		parameters.put("FECHAMENTO", calendar.get(Calendar.MONTH)+1+" de "+calendar.get(Calendar.YEAR)+".");
		
		ReportManager.saveReport("/recibo_salario.jrxml", parameters, "/home/mathcunha/Desktop/recibo_"+mes+".pdf");
		
		ReportManager.saveReport("/recibo_transporte.jrxml", parameters, "/home/mathcunha/Desktop/recibo_transporte"+mes+".pdf");
	}
	
	public static void main(String[] args){
		Salario salario = new Salario();
		
		salario.setValorBase(new Double(args[0]));
		
		salario.setValorTransporte(new Double(args[1]));
		
		salario.setMes(new Integer(args[2]));
		
		salario.setDescontos(new ArrayList<Desconto>(args.length));
		
		for (int i = 3; i < args.length; i+=2) {
			salario.getDescontos().add(new Desconto(new Double(args[i])/(double)100, args[i+1], salario));
		}
		
		salario.calcValorLiquido();
		
		gerarRecibo(salario.getMes(), salario);
	}

}
