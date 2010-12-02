package br.com.mathcunha.recibo.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportManager {

	protected static Logger log = Logger.getLogger(ReportManager.class
			.getName());

	public static byte[] generateReport(String reportPath, Map parameters) {
		InputStream reportStream = ReportManager.class
				.getResourceAsStream(reportPath);

		Locale locale = new Locale("pt", "BR");
		parameters.put(JRParameter.REPORT_LOCALE, locale);
		

		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource((Collection)parameters.get("ds"));
		try {
			return JasperRunManager.runReportToPdf(
					JasperCompileManager.compileReport(reportStream),
					parameters, ds);
		} catch (JRException e) {
			log.log(Level.SEVERE, "erro na geracao do relatorio", e);
			return null;
		}
	}

	public static void saveReport(String reportPath, Map parameters,
			String pdfPath) {
		byte[] arquivo = generateReport(reportPath, parameters);

		try {
			FileOutputStream writer = new FileOutputStream(new File(pdfPath));

			writer.write(arquivo, 0, arquivo.length);
			writer.flush();

			writer.close();

		} catch (FileNotFoundException e) {
			log.log(Level.SEVERE, "erro na geracao do relatorio", e);
		} catch (IOException e) {
			log.log(Level.SEVERE, "erro na geracao do relatorio", e);
		}
	}
}