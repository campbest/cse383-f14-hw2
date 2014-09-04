import java.io.*;
import java.net.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;

/*
Scott campbell

cse383 - f14
Homework 2- simple AWS Dynamo DB server

Client sends greeting
Server retrieves data from aws and sends back to client


Main Entry Class - setups server and calls handler
*/
public class HW2 {
	int port = 2998;

	public final static Logger LOG = Logger.getLogger(HW2.class.getName());  //logger
	ServerSocket serverSocket = null;

	/*
	JRE Entry Point
	*/
	public static void main(String a[]) {
		new HW2().Main();
	}

	/*
	create a logger file
	*/
	public void setupLog() {

		try {
			//remove default console logger
			//LOG.setUseParentHandlers(false);

			//setup log
			FileHandler fh = new FileHandler("hw2.log");
			LoggerFormatter lf = new LoggerFormatter();
			fh.setFormatter(lf);
			LOG.addHandler(fh);
	
		} catch (IOException err) {
			System.out.println("Could not open log");
			return;
		}

	}

	/*
	Major class - does the work
	*/
	public void Main() {
		setupLog();
		LOG.info("Server Init");
		try {
			serverSocket = new ServerSocket(port);
		}  catch (IOException err) {
			System.out.println("Unable to start server " + err);
			return;
		}

		LOG.info("Server running");
		while(true) {
			try {
				Socket s = serverSocket.accept();
				LOG.info("Client Accept");
				new ClientHandler(s,LOG).start();
			} catch (IOException err) {
			}
		}
	}
}

				
