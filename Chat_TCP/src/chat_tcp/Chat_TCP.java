package chat_tcp;

import javax.swing.JOptionPane;

public class Chat_TCP
{
    public static void main(String[] args)
    {
        if(args[0].equals("s"))
		{
			new Server(10023);
		}
		else if (args[0].equals("c"))
		{
			String name = JOptionPane.showInputDialog("Introduce tu nombre:");
			if(name != null)
			{
				if(!name.equals(""))
				{
					Client client = new Client(name, 10023);
					client.setBounds(0,0,540,400);
					client.setVisible(true);
					new Thread(client).start();
				}
			}
		}
    }
}