package br.compnatural.experiment.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;



public class ReportManager {
	
	protected static Logger log = Logger.getLogger(ReportManager.class.getName());
	
	public static byte[] generateReport(String reportPath, Map parameters){
		InputStream reportStream = ReportManager.class.getResourceAsStream(reportPath);
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource((List<ReportUnit>)parameters.remove("ds"));
		try {
			return JasperRunManager.runReportToPdf(JasperCompileManager.compileReport(reportStream), parameters,ds);
		} catch (JRException e) {			
			log.log(Level.SEVERE, "erro na geracao do relatorio",e);
			return null;
		}
	}
	
	public static void saveReport(String reportPath, Map parameters, String pdfPath){
		byte[] arquivo = generateReport(reportPath, parameters);
		
		try {
			FileOutputStream writer = new FileOutputStream(new File(pdfPath));
			
			writer.write(arquivo, 0, arquivo.length);
			writer.flush();
			
			writer.close();
			
		} catch (FileNotFoundException e) {
			log.log(Level.SEVERE, "erro na geracao do relatorio",e);
		} catch (IOException e) {
			log.log(Level.SEVERE, "erro na geracao do relatorio",e);
		} 
	}
}
