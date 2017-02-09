
package chat_tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamsUtils
{
	static void print(InputStream is) throws IOException
	{
		if (is != null)
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null)
			{
				System.out.println(line);
			}
		}
	}
}