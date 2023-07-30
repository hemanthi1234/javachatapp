import javax.swing.*;

import java.io.*;
import java.net.*;

import java.io.IOException;

public class server  {

    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;


public server() {
    try {
        server = new ServerSocket(54321);
        System.out.println("Server is ready to accept connection");
        System.out.println("waiting.");
        socket = server.accept();

        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out = new PrintWriter(socket.getOutputStream());



        startReading();
        startWriting();


    } catch (IOException e) {
        throw new RuntimeException(e);
    }

}


    public void  startReading()
    {
        Runnable r1=()->{
            System.out.println("Reader started.");
            while(true){
                String msg= null;
                try {
                    msg = br.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (msg.equals("exit")) {
                    System.out.println("Client terminated the chat");

                    break;
                }

                System.out.println("Client :"+msg);

            }

        };

        new Thread(r1).start();

    }

    public void startWriting()
    {
     
        Runnable r2=()->{
            System.out.println("Writer started");
            BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));
            try {
                String content=br1.readLine();
                out.println(content);
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        };

        new Thread(r2).start();

    }


    public static void main(String[] args) {
        System.out.println("SERVER....Your server is starting!");
        new server();
}
}
