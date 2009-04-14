package proxy.policy;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ServiceBroker {
	
	private Hashtable<String, List<URL>> servicos;
	
	private ReplicatedServiceProxy lReplicatedServiceProxy ;
	
	public ServiceBroker() {
		servicos = new Hashtable<String, List<URL>>();
		List<URL> lista = new ArrayList<URL>();
		
		try {
			lista.add(new URL("http://127.0.0.1:8080"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		servicos.put("services", lista);
		
		//Cria um servi�o de escolha com 5 considera��es no hit�rico e com uma 
		//toler�ncia de servi�os dos ultimos 3 segundos
		lReplicatedServiceProxy = new HistoryServiceProxy("Operacao","Somar", 5,30000L);
		((HistoryServiceProxy) lReplicatedServiceProxy).resetEndPointInfo(lista);
	}

	public URL chooseEndpoint(EndPointInfo endPointInfo) {
		lReplicatedServiceProxy.addEndPointInfo(endPointInfo);
		
		URL endPoint = lReplicatedServiceProxy.chooseEndPoint();
		
		return endPoint;
	}
	
	public void addEndPointInfo(EndPointInfo endPointInfo) {
		lReplicatedServiceProxy.addEndPointInfo(endPointInfo);
	}

}
