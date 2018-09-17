package urjc.ist.jms.pubsubexample;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PubSubAsyncPrueba {

	private static final String NOMBRE_FACTORIA = "Factoria_Subscripcion";
	private static final String NOMBRE_SUBSCRIPCION = "Topic_Prueba";
	
	public static void main (String[] args){
		try{
			InitialContext jndi = new InitialContext();
			System.out.println("EMPIEZO A MIRAR LOS RECURSOS ");
			TopicConnectionFactory factoria = 
					(TopicConnectionFactory) jndi.lookup(NOMBRE_FACTORIA);
			TopicConnection conexion = factoria.createTopicConnection();
			Topic topic = (Topic) jndi.lookup(NOMBRE_SUBSCRIPCION);
			
			
			
			
			PubSubAsyncReceiver asinCliente = new PubSubAsyncReceiver(conexion, topic);
			//Como la clase asincCliente implementa una clase Runnable, separamos el trabajo del trabajador donde el trabajador es el siguiente
			Thread hiloAsin = new Thread(asinCliente);
			
			
			System.err.println("Empieza a ejecutarse un Subscriptor");
			hiloAsin.start();
			hiloAsin.join();
			conexion.close();
	
		
		}catch (NamingException ex){
			System.err.println("No Existe Recurso con este nombre");
		}catch (JMSException jex){
			System.err.println("Ha habido un error a la hora de crear la conexion");
			jex.printStackTrace();
		}catch (InterruptedException iex){
			System.err.println("La ejecucion del hilo no ha acabado como deberia");
			iex.printStackTrace();
		}
	}
}
