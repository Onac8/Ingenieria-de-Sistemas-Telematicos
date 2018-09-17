package urjc.ist.jms.pubsubexample;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PubSubSender {

	private static final String factoryName = "Factoria_Subscripcion";
	private static final String topicName = "Topic_Prueba";

	public static void main(String[] args) {
		try {

			InitialContext jndi = new InitialContext();
			TopicConnectionFactory factory = 
					(TopicConnectionFactory)jndi.lookup(factoryName); //Obtenemos el objeto administrado, la factoria de conexion creada por el proveedor JMS, en este caso Glassfish
			Topic topic = (Topic)jndi.lookup(topicName); //Obtenmos el destino, creado por la administracion de Glassfish, en este caso es un Topic, por tanto utilizaremos el modelo de mensajeria Pub/Sub

			TopicConnection connection = factory.createTopicConnection(); //Mediante la factoria creamos la conexion 
			TopicSession session =  //Creamos la sesion a partir de la conexion, donde la hacemos no_transancional. Lo que habra que asentir los mensajes. Estos se asentiran cuando el consumidor procese el mensaje
					connection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			TopicPublisher publisher = session.createPublisher(topic);
			//Creamos un productor de mensajes, en este modelo lo tratamos como un publicador de mensajes, donde le pasamos el destino 
			TextMessage msg = session.createTextMessage(); //A partir de sesion creamos un mensaje
			msg.setText("Probando TextMessage");
			publisher.publish(msg);
			for(int i = 0; i < 10; i++){
				msg.setText("Message " + i + " to " + topicName);
				publisher.publish(msg);
				System.err.println("Enviado mensaje " + i + " a " + topicName);
				Thread.sleep(10000);
			}
			System.err.println("\nSending message to close connection...");
			// Enviar mensaje de cierre al receptor
			msg.setText("CLOSE");
			publisher.publish(msg);
			connection.close();
			System.err.println("\nClosing publisher...");

		} catch (NamingException ex) {
			ex.printStackTrace();
		} catch (JMSException ex) {
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} 
	}

}
