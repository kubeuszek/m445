package proxy.policy;

import java.net.MalformedURLException;
import java.net.URL;

public class ServiceBroker {
	
	public ServiceBroker() throws MalformedURLException{

	}

	public EndPoint chooseEndpoint(EndPointInfo endPointInfo) {
		
		//Cria um servi�o de escolha com 5 considera��es no hit�rico e com uma 
		//toler�ncia de servi�os dos ultimos 3 segundos
		ReplicatedServiceProxy lReplicatedServiceProxy = new ReplicatedServiceProxy_Impl(5,3L);
		
		lReplicatedServiceProxy.addEndPointInfo(endPointInfo);
		
		URL endPoint = lReplicatedServiceProxy.chooseEndPoint();
		
		return new EndPoint(endPoint.getHost(), endPoint.getPort());
	}

}
