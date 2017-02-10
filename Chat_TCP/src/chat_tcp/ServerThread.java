package chat_tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread
{
	Socket connection	= null;
	Server server		= null;
	String clientName	= null;
	
	public ServerThread(Server server, Socket connection)
	{
		this.connection = connection;
		this.server = server;
		start();
	}
	
	@Override
	public void run()
	{
		super.run();	
		
		try
		{
			// Get incoming data
			DataInputStream inData = new DataInputStream(connection.getInputStream());
				
			// Get name from client
			clientName = inData.readUTF();
			System.out.println("[" + clientName + "]" + " connected...");
			
			while(connection.isConnected())
			{
				// Print messages
				String inputDataStr = inData.readUTF();
				System.out.println(inputDataStr);

				// Output incoming data to all clients:
				for(Socket c : server.connections)
				{
					if(c.isConnected())
					{
						//System.out.println("Envio a puerto: " + c.getPort());
						DataOutputStream outData = new DataOutputStream(c.getOutputStream());
						outData.writeUTF(inputDataStr);
					}
				}
			}
			server.notifyConnectionClose(connection);
		}
		catch(IOException ex)	// Connection closed
		{
			server.notifyConnectionClose(connection);
			//System.out.println("Error en ServerThread al mandar mensages");
			//ex.printStackTrace();
			
		}
		
		System.out.println("[" + clientName + "] disconnected...");
	}
}