package chat_tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public final class Server
{
    // Server Object
    ServerSocket server;
    
	// Conections
	public List<Socket> connections = null;

    public Server(int port)
    {
        // Initzialized Streams List
        connections = new ArrayList<>();
		
        try
        {
            server = new ServerSocket(port);
			
			while(true)
			{
				AcceptConnection();
			}
        }
        catch(IOException e)
        {
            System.out.println("Error en el servidor");
            e.printStackTrace(System.out);
        }
    }
    
	public void notifyConnectionClose(Socket s)
	{
		connections.remove(s);
		try
		{
			s.close();
		}
		catch(Exception ex)
		{
			System.out.println("Conexión cerrada por el cliente.");
		}
	}
	
    public ServerThread AcceptConnection() throws IOException
    {
        // Get socket
        Socket connection = server.accept();
        connections.add(connection);
		System.out.println("Added new connection...");
		return new ServerThread(this, connection);
    }
}