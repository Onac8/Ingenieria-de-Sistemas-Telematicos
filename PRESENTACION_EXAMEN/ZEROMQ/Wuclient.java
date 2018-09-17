package zeroMQ;

import java.util.StringTokenizer;
import org.zeromq.ZMQ;
import java.util.Date;

//
//  Weather update client in Java
//  Connects SUB socket to tcp://localhost:5556
//  Collects weather updates and finds avg temp in zipcode
//
public class Wuclient {

    public static void main (String[] args) {
        ZMQ.Context context = ZMQ.context(1); //Preparamos el contexto

        //  Socket to talk to server
        System.out.println("Collecting updates from weather server");
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB); //Creamos un socket suscriptor
        subscriber.connect("tcp://localhost:5556");//Nos ponemos en escucha en el puerto donde el editor actualizara los datos

        //  Subscribe to zipcode, default is NYC, 10001
        String filter = (args.length > 0) ? args[0] : "10001 ";
        System.out.println(filter);
        subscriber.subscribe(filter.getBytes()); //Nos subscribimos al ZipCode(Codigo Postal) que le pasemos como argumento o al por defecto que este 
        //caso es el de Nueva York que es 1001

        //  Process 100 updates
        int update_nbr;
        long total_temp = 0;
        for (update_nbr = 0; update_nbr < 100; update_nbr++) {
            //  Use trim to remove the tailing '0' character
            String string = subscriber.recvStr(0).trim();

            StringTokenizer sscanf = new StringTokenizer(string, " ");
            int zipcode = Integer.valueOf(sscanf.nextToken());
            int temperature = Integer.valueOf(sscanf.nextToken());
            int relhumidity = Integer.valueOf(sscanf.nextToken());

            total_temp += temperature;

            Date fecha = new Date();
            System.out.println("Recibido " + fecha);
        }
        System.out.println("Average temperature for zipcode '"
                + filter + "' was " + (int) (total_temp / update_nbr));
        
        subscriber.close();
        context.term();
    }
}
