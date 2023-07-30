
import java.io.*;
import java.net.*;

import java.io.IOException;


public class client  {
    Socket socket;
    BufferedReader br;
    PrintWriter out;




    public client() {

        try {

           System.out.println("sending request to server");
           socket = new Socket("localhost", 54321);
           System.out.println("connection done.");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());


           startReading();
           startWriting();


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }




    public void startReading() {
        Runnable r1 = () -> {
            System.out.println("Reader started.");
            while (true) {
                String msg = null;
                try {
                    msg = br.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (msg.equals("exit")) {
                    System.out.println("Server terminated the chat");


                    break;
                }

                System.out.println("Server :" + msg);


            }

        };

        new Thread(r1).start();

    }

    public void startWriting() {

        Runnable r2 = () -> {
            System.out.println("Writer started");
            BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
            try {


                String content = br1.readLine();
                out.println(content);
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        };

        new Thread(r2).start();

    }

    public static void main(String[] args) {
        System.out.println("this is client. ");
        new client();
    }
}



