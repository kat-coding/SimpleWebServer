package SimpleServer;

import java.io.IOException;
import java.net.*;

/**
 * @author Katherine Watkins
 * SDEV 301
 * Individual #4
 * Simple Web Server will create the new serverSocket at the determined SERVER_PORT
 * it will accept the client socket and create a new WebService object
 * Finally it calls the performService method of the WebService class.
 */
public class SimpleWebServer {
        public static final int SERVER_PORT = 8090;

        public static void main(String[] args) throws IOException {
            try(ServerSocket server = new ServerSocket(SERVER_PORT, 0, InetAddress.getLoopbackAddress())){
                System.out.println("Server Starting...");

                //create client socket
                Socket client = server.accept();
                System.out.println("Accepted client");

                WebService ws = new WebService(client, "GET");
                ws.perfromService();
            }
        }

}
