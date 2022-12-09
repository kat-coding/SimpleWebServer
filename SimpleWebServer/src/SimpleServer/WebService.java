package SimpleServer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Katherine Watkins
 * SDEV 301
 * Individual #4
 *
 * WebService object with performService method
 */


//test file path:
//"C:\Users\katst\Downloads\simple_page.html"

public class WebService {
    private Socket client;
    private String command;

    /**
     *
     * @param client client is the client socket
     * @param command command is the HTTP request,
     *                this program will only handle GET requests
     */
    public WebService(Socket client, String command){
        this.client = client;
        this.command = command;
    }

    /**
     * performService will create a scanner for client input
     * then it will read in: the command (GET), a file path, and protocol (HTTP/1.1)
     * Next it will create and output stream, determine if the file was found
     * Finally it will send the HTML document to the client or send a 404 Not Found error
     * @throws IOException
     */
    public void perfromService() throws IOException {
        Scanner readFromClient = new Scanner(client.getInputStream());
        String command = readFromClient.next();
        String path = readFromClient.next();
        String protocol = readFromClient.next();


        OutputStream clientOut = client.getOutputStream();

        try {
            Scanner file = new Scanner(new File(path));
            if(command.equals("GET")){
                clientOut.write("HTTP/1.1 200 OK\r\n".getBytes());
                clientOut.write("\r\n".getBytes());

                while(file.hasNextLine()){
                    String a = file.nextLine();
                    System.out.println(a);
                    clientOut.write(a.getBytes());
                }

                clientOut.flush();
                System.out.println("Client connection closed");
                clientOut.close();
                readFromClient.close();
            }else{
                System.out.println("Wrong Request Type");
            }

        }catch(FileNotFoundException e){
            System.out.println("File not found: " + path);
            clientOut.write("HTTP/1.1 404 Not Found\r\n".getBytes());
            clientOut.write("\r\n".getBytes());
            clientOut.write(("<b>404 Not Found: " + path + "</b>\r\n").getBytes());

            clientOut.flush();
            System.out.println("Client connection closed");
            clientOut.close();
            readFromClient.close();
        }
    }

}
