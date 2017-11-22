import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class sendMessage {
	
	private static InetAddress myAddress = null;
	
	private static int otherPort;
	private static int sourcePort;
	private static InetAddress sourceAddress = null;
	private static newMessage[] windows;
	private static int index;
	DatagramSocket mySocket = null;  /// static
	
 public sendMessage() {
	
 }
	
	
 public sendMessage(DatagramSocket socket) {
	
		mySocket = socket;
	
 }
	
 
		
	public void messageSend(String msg, InetAddress otherAddress, int port) {

		try {
			myAddress = InetAddress.getLocalHost();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		System.out.println("My Address = " + myAddress.getHostAddress());
		
	
		byte[] buffer = new byte[100];

		
			String message = msg;
			buffer = message.getBytes();
			
			try {
				DatagramPacket packet = new DatagramPacket(buffer, 
														   message.length(),
														   otherAddress,
														   port);
        
         
         
         
				System.out.println("Sending message = " + message);
				
				mySocket.send(packet);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		
	}
	
	
	

	
	
}
