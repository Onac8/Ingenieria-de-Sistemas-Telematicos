package zeroMQ;

import java.util.Random;
import org.zeromq.ZMQ;
import java.util.Date;

//
//  Weather update server in Java
//  Binds PUB socket to tcp://*:5556
//  Publishes random weather updates
//
public class Wuserver {

    public static void main (String[] args) throws Exception {
        //  Prepare our context and publisher
        ZMQ.Context context = ZMQ.context(1); //Preparamos el contexto ZeroMQ, es decir, el servicio

        ZMQ.Socket publisher = context.socket(ZMQ.PUB); //Creamo Un socket publisher, parte estable del patron de comunicacion
        publisher.bind("tcp://*:5556");  //Elegimos el protocolo de transporte TCP
        publisher.bind("ipc://weather");

        //  Initialize random number generator
        int mensajei = 0;
        Random srandom = new Random(System.currentTimeMillis());
        while (!Thread.currentThread ().isInterrupted ()) {
            //  Get values that will fool the boss
            int zipcode, temperature, relhumidity;
            //zipcode = 10000 + srandom.nextInt(10000) ;
            zipcode = 10001;
            temperature = srandom.nextInt(215) - 80 + 1;
            relhumidity = srandom.nextInt(50) + 10 + 1;

            //  Send message to all subscribers
            String update = String.format("%05d %d %d", zipcode, temperature, relhumidity);
            Date fecha = new Date();
            System.out.println("Se envia el mensaje numero " + mensajei++ + " del CP " + zipcode + ", enviado " + fecha );
            publisher.send(update, 0);
        }

        publisher.close ();
        context.term ();
    }
}
