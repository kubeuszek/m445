package br.compnatural.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import br.compnatural.experiment.report.ReportGraphInfo;
import br.compnatural.experiment.report.ReportManager;
import br.compnatural.experiment.report.ReportUnit;
import junit.framework.TestCase;

public class ReportManagerTest extends TestCase {

	public void testSaveReportGrafico() {
		List<ReportGraphInfo> ds = new ArrayList<ReportGraphInfo>();
		
		int i = 0;
		
		ds.add(new ReportGraphInfo(4d, 5d, ++i));
		ds.add(new ReportGraphInfo(4d, 7d, ++i));
		ds.add(new ReportGraphInfo(5d, 7d, ++i));
		ds.add(new ReportGraphInfo(5d, 8d, ++i));
		ds.add(new ReportGraphInfo(4d, 8d, ++i));
		ds.add(new ReportGraphInfo(5d, 5d, ++i));
		ds.add(new ReportGraphInfo(5d, 7d, ++i));
		ds.add(new ReportGraphInfo(5d, 7d, ++i));
		ds.add(new ReportGraphInfo(4d, 5d, ++i));
		ds.add(new ReportGraphInfo(4d, 5d, ++i));
		
		Map parameters = new HashMap();
		parameters.put("nome", "teste");
		parameters.put("ds", ds);
		
		ReportManager.saveReport("/grafico.jrxml", parameters,
		"chart.pdf");
		assertEquals(true, true);
	}
	
	public void testSaveReport() {
		List<ReportGraphInfo> ds = new ArrayList<ReportGraphInfo>();
		
		List<ReportUnit> dsReportUnit = new ArrayList<ReportUnit>();
		int i = 0;
		
		dsReportUnit.add(new ReportUnit());
		dsReportUnit.get(dsReportUnit.size()-1).setTotalIteraction(1d);
		dsReportUnit.add(new ReportUnit());
		dsReportUnit.get(dsReportUnit.size()-1).setTotalIteraction(1d);
		dsReportUnit.add(new ReportUnit());
		dsReportUnit.get(dsReportUnit.size()-1).setTotalIteraction(1d);
		
		
		ds.add(new ReportGraphInfo(4d, 5d, ++i));
		ds.add(new ReportGraphInfo(4d, 7d, ++i));
		ds.add(new ReportGraphInfo(5d, 7d, ++i));
		ds.add(new ReportGraphInfo(5d, 8d, ++i));
		ds.add(new ReportGraphInfo(4d, 8d, ++i));
		ds.add(new ReportGraphInfo(5d, 5d, ++i));
		ds.add(new ReportGraphInfo(5d, 7d, ++i));
		ds.add(new ReportGraphInfo(5d, 7d, ++i));
		ds.add(new ReportGraphInfo(4d, 5d, ++i));
		ds.add(new ReportGraphInfo(4d, 5d, ++i));
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource((List<ReportGraphInfo>)ds);
		
		dsReportUnit.get(dsReportUnit.size()-1).setReportGraphInfo(dataSource);
		
		ds = new ArrayList<ReportGraphInfo>();
		
		dsReportUnit.add(new ReportUnit());
		dsReportUnit.get(dsReportUnit.size()-1).setTotalIteraction(2d);
		dsReportUnit.add(new ReportUnit());
		dsReportUnit.get(dsReportUnit.size()-1).setTotalIteraction(2d);
		dsReportUnit.add(new ReportUnit());
		dsReportUnit.get(dsReportUnit.size()-1).setTotalIteraction(2d);
		
		
		
		ds.add(new ReportGraphInfo(3d, 3d, ++i));
		ds.add(new ReportGraphInfo(3d, 5d, ++i));
		ds.add(new ReportGraphInfo(4d, 5d, ++i));
		ds.add(new ReportGraphInfo(4d, 6d, ++i));
		ds.add(new ReportGraphInfo(3d, 6d, ++i));
		ds.add(new ReportGraphInfo(4d, 3d, ++i));
		ds.add(new ReportGraphInfo(4d, 5d, ++i));
		ds.add(new ReportGraphInfo(4d, 5d, ++i));
		ds.add(new ReportGraphInfo(3d, 3d, ++i));
		ds.add(new ReportGraphInfo(3d, 3d, ++i));
		
		dataSource = new JRBeanCollectionDataSource((List<ReportGraphInfo>)ds);
		
		dsReportUnit.get(dsReportUnit.size()-1).setReportGraphInfo(dataSource);
		
		
		Map parameters = new HashMap();
		parameters.put("nome", "teste");
		parameters.put("ds", dsReportUnit);
		
		ReportManager.saveReport("/otimizacao_grafico.jrxml", parameters,
		"chart_completo.pdf");
		assertEquals(true, true);
	}

}
