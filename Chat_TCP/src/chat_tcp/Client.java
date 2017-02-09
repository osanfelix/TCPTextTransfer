
package chat_tcp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class Client extends JFrame implements ActionListener, Runnable
{
	
	JTextField message = new JTextField();
	JScrollPane scrollpanel;
	JTextArea textArea = null;
	JButton sendButton = new JButton("SEND");
	JButton exitButton = new JButton("EXIT");
	
	
	Socket connection = null;
	String name = null;
	DataOutputStream outData = null;
	DataInputStream inData = null;
		
	boolean exit = false;
	public Client(String name, int port)
	{
		// GUI issues
		super("CLIENTE CHAT");
		setLayout(null);
		
		message.setBounds(10, 10, 400, 30);add(message);
		
		textArea = new JTextArea();
		scrollpanel = new JScrollPane(textArea);
		scrollpanel.setBounds(10, 50, 400, 300);add(scrollpanel);
		textArea.setEditable(false);
		
		exitButton.setBounds(420, 50, 100,30);add(exitButton);
		exitButton.addActionListener(this);
		
		sendButton.setBounds(420, 10, 100,30);add(sendButton);
		sendButton.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		
		this.name = name;
		try
		{
			// Get Connection
			connection = new Socket("localhost", port);
			
			// Outgoing data to server
			outData =  new DataOutputStream(connection.getOutputStream());
			
			// First of all send name to server
			outData.writeUTF(name);
			
			inData = new DataInputStream(connection.getInputStream());

		}
		catch (IOException e) {
			System.out.println("Error al crear el cliente");
			e.printStackTrace(System.out);
		}
	}

	
	@Override
	public void run()							// get msges from server
	{
		while(!exit)
		{
			try
			{
				String inStr = inData.readUTF();
				textArea.setText(textArea.getText() + "\n" + inStr);
			}
			catch (IOException e)
			{
				System.out.println("Error al recibir mesajes");
				e.printStackTrace(System.out);
		}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)	// Send msgs to server
	{
		if(e.getSource() == sendButton)
		{
			try
			{
				
				String text = "[" + name + "]: " + message.getText();
				message.setText("");
				outData.writeUTF(text);
				
				
			}
			catch (IOException ex)
			{
				System.out.println("Error al enviar el mensaje");
				ex.printStackTrace(System.out);
			}
		}
		else if(e.getSource() == exitButton)
		{
			try
			{
				exit = true;
				outData.writeUTF("exit");
				outData.close();
				connection.close();
				dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
				}
				catch (IOException ex)
				{
					System.out.println("Error al cerrar el cliente");
					ex.printStackTrace(System.out);
				}
		}
	}
}