/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_tcp;

import javax.swing.JOptionPane;

/**
 *
 * @author Ofelia
 */
public class Chat_TCP
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        if(args[0].equals("s"))
		{
			new Server(10023);
		}
		else
		{
			String name = JOptionPane.showInputDialog("Introduce tu nombre:");
			
			Client client = new Client(name, 10023);
			client.setBounds(0,0,540,400);
			client.setVisible(true);
			new Thread(client).start();
		}
    }
}