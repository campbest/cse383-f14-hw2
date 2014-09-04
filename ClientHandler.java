import java.io.*;
import java.net.*;
import java.util.logging.Logger;
import java.util.*;

/*
Scott campbell

cse383 - f14
Homework 2- simple AWS Dynamo DB server

Client sends greeting
Server retrieves data from aws and sends back to client


This class interacts with client
*/

public class ClientHandler extends java.lang.Thread {
	Socket clientSock = null;
	Logger LOG = null;
	DataOutputStream dos = null;
	InputStream is = null;
	URLItems urlItems;

	public ClientHandler(Socket s,Logger l) {
		LOG=l;
		clientSock = s;
		LOG.info(s.getRemoteSocketAddress() + " accept");
	}

	/*
	main entry point
	*/
	public void run() {
		try {
			//setup
			is = clientSock.getInputStream();
			dos = new DataOutputStream(clientSock.getOutputStream());
			clientSock.setSoTimeout(5000);

			getValidateGreeting();
			urlItems = new URLItems();	//URLItems holds data from aws
			ArrayList<URLItem> items = urlItems.getAllItems(); //get data from aws
			sendData(items);
			LOG.info(clientSock.getRemoteSocketAddress() + " disconnect");
		} catch (IOException err) {
			LOG.severe(clientSock.getRemoteSocketAddress() + " error - " + err);
		}
		try {
			dos.writeInt(0);
			clientSock.close();
		} catch (IOException err) {
		}
	}

	/*
	greeting is length + 30 bytes of data - invalid greeting throws an exception
	*/
	public void getValidateGreeting() throws IOException {
		int greetLength = is.read();
		if (greetLength != 30) 
			throw new IOException("Invalid greeting bad length");
		for (int i=0;i<10;i++) {
			if (is.read() != 0x10 || is.read() != 0x20 ||  is.read()!=0x30)
				throw new IOException("Invalid greeting - bad sequence i=" + i);
		}
		return;
	}

	/*
	send data to client
	*/
	public void sendData(ArrayList<URLItem> items) throws IOException {
		for(URLItem i: items) {
			dos.writeInt(3);	//send three values;
			dos.writeUTF("Name");
			dos.writeUTF(i.getName());
			dos.writeUTF("key");
			dos.writeUTF(i.getKey());
			dos.writeUTF("url");
			dos.writeUTF(i.getUrl());
		}
		dos.flush();
	}


}
