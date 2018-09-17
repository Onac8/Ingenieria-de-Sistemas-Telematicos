package urjc.ist.jms.pubsubexample;

import java.util.Objects;
import javax.jms.*;
import java.util.Date;

public class PubSubAsyncReceiver implements Runnable, MessageListener{

	private TopicConnection connection;
	private Topic topic;
	private boolean stopFlag;

	public PubSubAsyncReceiver(TopicConnection con, Topic topic){
		this.connection = con; //asignamos la conexion creada(para ello antes de crear un objeto de este tipo obtenemos la factoria, y creamos la conexion
		this.topic = topic; //Le pasamos el destino creado a traves del recurso administrado, en este caso seria un topico 
	}

	@Override
	public void run(){
		try {   
			TopicSession session = 
					connection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			TopicSubscriber subscriber = session.createSubscriber(topic);
			subscriber.setMessageListener(this);
			connection.start();
			System.out.println("Thread " + Thread.currentThread().getId() + " subscribed!");
			
			
			Thread.sleep(10000);
			System.out.println("Paramos conexion");
			connection.stop();
			Thread.sleep(30000);
			connection.start();
			
			while (!stopFlag) {
				Thread.sleep(1000);
			}
			System.err.println("TRACE: Return Thread");
			return;
			
		} catch (JMSException ex) {
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	
	@Override
	public void onMessage(Message msg) {
		try {
			TextMessage m = (TextMessage)msg;
			
			if (Objects.equals(m.getText(), "CLOSE")){
				System.out.println("No more messages. Closing now!");
				stopFlag = true; //Enable condition to stop current Thread
				
			} else {
				Date fecha = new Date(); 
				System.out.println("Listener, Thread " + 
						Thread.currentThread().getId() +
						" message received: " + m.getText() + fecha.getTime());
			}
			
		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}

}
