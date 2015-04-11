
package tcpserver;

import java.io.*;
import java.net.*;

public class TCPserver extends Thread {

    public static void main(String[] args) throws Exception {
            
        //run the program
        runServer();            
    }
    
    public static void runServer() throws Exception {
        String clientName;
        String modifiedName;
        String line;
        Boolean run = true;
        Boolean isOnList = false;
        
        ServerSocket welcomeSocket = new ServerSocket(6789);
        
           while (run)
            {
                Socket connectionSocket = welcomeSocket.accept();
                BufferedReader inFromClient = new BufferedReader(
                        new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(
                    connectionSocket.getOutputStream());
                
                clientName = inFromClient.readLine();
                modifiedName = clientName.toLowerCase();
                System.out.println("I got " + clientName + " from the client");                
                File input = new File("list.txt");                
                
                BufferedReader fileReader = new BufferedReader(new FileReader(input));
                
                while ((line = fileReader.readLine()) != null)
                {
                    System.out.println("line has " + line);                    
                    System.out.println("Now it should be " + modifiedName);
                    if (line.contains(modifiedName))
                    {
                        isOnList = true;
                    }                    
                 }
                
                if (isOnList)
                {
                    System.out.println("I received " + clientName);
                    outToClient.writeBytes("Congrats " + clientName + "! You ARE on the list!" + '\n');                    
                    //set isOnList back to false to be ready for the next name
                    isOnList = false;
                }
                else
                {
                    System.out.println("I received " + clientName);
                    outToClient.writeBytes("Sorry " + clientName + ", you are not on the list!" + '\n');
                    //run = false;
                }             
                               
                
            } 
       
    }
        
}
