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
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		Date dataPagamento = BusinessDayUtil.getNextBusinessDay(calendar.getTime());
		
		parameters.put("DATA", dataPagamento);
		
		calendar.add(Calendar.MONTH, -1);
		
		parameters.put("SALARIO", "");
		
		parameters.put("ds", salario.getDescontos());

		
		parameters.put("FECHAMENTO", "Recebi da Sra. Marlene de Alencar Dutra a importância de"+ "VALOR" +", correspondente ao salário líquido do mês "+calendar.get(Calendar.MONTH)+1+" de "+calendar.get(Calendar.YEAR)+".");
		
		ReportManager.saveReport("/recibo_salario.jrxml", parameters, "/home/mathcunha/Desktop/recibo"+mes+".pdf");
	}
	
	public static void main(String[] args){
		Salario salario = new Salario();
		
		salario.setValorBase(new Double(args[0]));
		
		salario.setDescontos(new ArrayList<Desconto>(args.length));
		
		for (int i = 1; i < args.length; i++) {
			salario.getDescontos().add(new Desconto(new Double(args[i]), salario));
		}
		
		gerarRecibo(1, salario);
	}

}
