package proxy.policy;

import java.net.URL;
import java.util.Date;

public class EndPointInfo {

	/**
	 * Nome do servi�o.
	 */
	private String serviceName;
	
	/**
	 * Nome da opera��o do servi�o. 
	 */
	private String operationName;
	
	/**
	 * Quantidade de elementos do XML SOAP do servi�o invocado.
	 */
	private Integer nodeNumber;

	/**
	 * tempo de resposta para o servi�o.
	 */
	private Long timeResponse;
	
	/**
	 * URL do servi�o desejado.
	 */
	private URL url;
	
	/**
	 * Data em que a informa��o do EndPoint foi obtida.
	 */
	private Long dateEndPontInfo;
	
	public EndPointInfo() {
		
	}
	
	/**
	 * Construtor que cria informa��es sobre um EndPoint.
	 * @param service Nome do Servi�o
	 * @param operation Opera��o do Servi�o
	 * @param nodes Quantidade de N�s do servi�o
	 * @param url URL do servi�o
	 * @param time Tempo de resposta do servi�o 
	 * @param date Data de invoca��o do servi�o
	 */
	public EndPointInfo(String service, String operation, Integer nodes, URL url, Long time, Long date) {
		this.serviceName = service;
		this.operationName = operation;
		this.nodeNumber = nodes;
		this.timeResponse = time;
		this.url = url;
		this.dateEndPontInfo = date;
	}
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public Integer getNodeNumber() {
		return nodeNumber;
	}

	public void setNodeNumber(Integer nodeNumber) {
		this.nodeNumber = nodeNumber;
	}
	
	public URL getUrl() {
		return url;
	}
	
	public void setUrl(URL url) {
		this.url = url;
	}
	
	public Long getTimeResponse() {
		return timeResponse;
	}
	
	public void setTimeResponse(Long timeResponse) {
		this.timeResponse = timeResponse;
	}
	
	public Long getDateEndPontInfo() {
		return dateEndPontInfo;
	}
	
	public void setDateEndPontInfo(Long dateEndPontInfo) {
		this.dateEndPontInfo = dateEndPontInfo;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof EndPointInfo)) {
			return false;
		} else {
			EndPointInfo epi = (EndPointInfo) obj;
			if (epi.getServiceName().equals(this.serviceName) 
				&& epi.getOperationName().equals(this.operationName)
				&& epi.getNodeNumber().equals(this.nodeNumber)
				&& epi.getTimeResponse().equals(this.timeResponse)
				&& epi.getUrl().equals(this.url)
				&& epi.getDateEndPontInfo().equals(this.dateEndPontInfo)) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		
		result = 37 * result + (this.serviceName.hashCode() 
				+ this.operationName.hashCode() + this.nodeNumber.intValue()
				+ this.timeResponse.hashCode() + this.url.hashCode()
				+ this.dateEndPontInfo.intValue());
		return result;
	}
}