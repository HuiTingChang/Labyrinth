import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.net.ssl.SSLSocketFactory;

import de.fhac.mazenet.server.generated.*;
import de.fhac.mazenet.server.config.Settings;
import de.fhac.mazenet.server.networking.MazeComMessageFactory;
import de.fhac.mazenet.server.networking.XmlInStream;
import de.fhac.mazenet.server.networking.XmlOutStream;

public class Player 
{
	private Socket clientSocket=null;
	private XmlOutStream outToServer = null;
	private XmlInStream inFromServer = null;
	private MazeComMessageFactory mazeComMessageFactory=null;
	private MazeCom currentState=null;
	
	public Socket getSocket()
	{
		return clientSocket;
	}
	
	public MazeCom getCurrentState()
	{
		return currentState;
	}
	
	public void setCurrentState(MazeCom mc)
	{
		currentState = mc;
	}
	
	public void establishConnection(int port)
	{
		try 
		{
			clientSocket = new Socket("localhost", port);
		} 
		catch (IOException e) 
		{			
			e.printStackTrace();
		}
		
		// equivalent to XMLSerialisation
		mazeComMessageFactory = new MazeComMessageFactory();

	}
	
	public void enableStreams()
	{
		try 
		{
			outToServer = new XmlOutStream(clientSocket.getOutputStream());
		} 
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		try 
		{
			inFromServer = new XmlInStream(clientSocket.getInputStream());
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	}
	
	public XmlOutStream getOutputStream()
	{
		return this.outToServer;
	}
	
	public XmlInStream getInputStream()
	{
		return this.inFromServer;
	}
	
	public MazeComMessageFactory getMessageFactory()
	{
		return this.mazeComMessageFactory;
	}
	
	public void LoginToGame()
	{
		MazeCom loginReply = null;
		
		// To Server
		MazeCom mc = mazeComMessageFactory.createLoginMessage("Nuno");
		this.outToServer.write(mc);
		
		// From Server
		try 
		{
			loginReply = this.inFromServer.readMazeCom();
			if(loginReply != null && loginReply.getMcType()==MazeComType.LOGINREPLY)
			{
				System.out.println("Login erfolgreich!! ID from Server: " + 
						loginReply.getLoginReplyMessage().getNewID());
								
			}
		} 
		catch (UnmarshalException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}
	
	public void closeConnection()
	{
		try 
	    {
	    	this.inFromServer.close();
	    	this.outToServer.close();
			this.clientSocket.close();
			System.out.println("Client finished the connection successfully!!!");
		} 
	    catch (IOException e) 
	    {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		Player p= new Player();
		System.out.println("client starts");
		
		// encrypted
		
//		//setup truststore to verify server-certificate
//		System.setProperty("javax.net.ssl.trustStore", "truststore.jks");
//		System.setProperty("javax.net.ssl.trustStorePassword", "geheim2");
//	    
//		
//		
//	 
//	    //create SSLSocket
//		SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
//	    try 
//	    {
//			Socket sslSocket = ssf.createSocket("localhost", 6789);
//		} 
//	    catch (UnknownHostException e) 
//	    {
//			e.printStackTrace();
//		} 
//	    catch (IOException e) 
//	    {
//			e.printStackTrace();
//		}
		
		// establish connection
		p.establishConnection(Settings.PORT);
		System.out.println("Client IP-Address: " + p.getSocket().getInetAddress() +
				", Port: " + p.getSocket().getLocalPort());
		
		
		// enable inputstream and outputstream
		p.enableStreams();
		
		// proceed Login
		p.LoginToGame();
		
		// start playing
		MazeCom answer=null;
		while(true)
	    {
	    	// From Server
	    	try 
			{
				answer = p.getInputStream().readMazeCom();
			} 
			catch (UnmarshalException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    	if(answer!=null && answer.getMcType()==MazeComType.DISCONNECT)
	    	{
	    		System.out.println("Game Over!! Player disconnected");
	    		break;
	    	}
	    	if(answer!=null && answer.getMcType()==MazeComType.WIN)
	    	{
	    		System.out.println("Game Over!!\nThe Winner is: " + 
	    				answer.getWinMessage().getWinner().getValue() + 
	    				" with ID = " + answer.getWinMessage().getWinner().getValue());
	    		break;
	    	}
	    	
	    	if(answer!=null && answer.getMcType()==MazeComType.AWAITMOVE)
	    	{
	    		System.out.println("Server awaits move...");
	    		p.setCurrentState(answer);
	    	}
	    	else if(answer!=null && answer.getMcType()==MazeComType.ACCEPT)
	    	{
	    		if(answer.getAcceptMessage().getErrorCode()==ErrorType.NOERROR)
	    		{
	    			System.out.println("Move accepted...");
	    			continue;
	    		}
	    		else if(answer.getAcceptMessage().getErrorCode()==ErrorType.ILLEGAL_MOVE)
	    		{
	    			System.out.println("Illegal move...");
	    		}
	    	}
	    	else if(answer==null)
	    	{
	    		System.out.println("Server null... Connection lost");
	    		break;
	    	}
	    	
	    	// To Server
	    	MazeCom mc = null;
	    	if(answer.getMcType()==MazeComType.AWAITMOVE)
	    	{
	    		//TODO
	    		
	    		// KA-Algorithm
	    		
	    		// create object to send to server
	    		mc = p.getMessageFactory().createMoveMessage(answer.getId(), 
	    			null, null, null);
	    		
	    	}
	    	else
	    	{
	    		// ungueltiger Zug
	    		
	    	}
	    	// Move an der Server schicken
	    	p.getOutputStream().write(mc);
	    }
	    
	    
	    // close connection
	    p.closeConnection();
	}
}
