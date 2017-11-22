

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.*;
import javax.swing.JPanel;



public class MyMessage extends JFrame implements ActionListener{

	private JFrame StartChat;
	private JTextField port;
	private JTextField IPField;
	private JTextField MSG;
	private JLabel ipAddress;
	private JLabel chat;
	static DatagramSocket socket = null;
	private static sendMessage SendReceive;
	private static Windows array;
	private static int sourcePort;
	private static InetAddress sourceAddress = null;

	
	
	
	
	public static void main(String[] args) {
		
		
		array = new Windows();
		
		
	      InetAddress myAddress = null;
			try {
				myAddress = InetAddress.getLocalHost();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
			
			
			
			try {
				/**
				 * Here is my port number in order to communicate with me
				 * the other person needs  plus my is address
				 */
					socket = new DatagramSocket(32000, myAddress);
					} catch (Exception e) {
						e.printStackTrace();
						System.exit(-1);
					}
			
			SendReceive = new sendMessage(socket);
			Thread receiveThread = new Thread(new Runnable () {
				public void run() {
					
					receiveMethod();
				}
			});
			receiveThread.setName(" Thread  received");
			receiveThread.start();
		
			
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyMessage window = new MyMessage();
					window.StartChat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
         /**
          * My receive method 
          */
	public static void receiveMethod() {

		byte[] inBuffer = new byte[1000];

		DatagramPacket inPacket = new DatagramPacket(inBuffer, inBuffer.length);


		do {
			for ( int i = 0 ; i < inBuffer.length ; i++ ) {
				inBuffer[i] = ' ';
			}

			try {
				// this thread will block in the receive call
				// until a message is received
				System.out.println("Waiting for message");
				socket.receive(inPacket);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}

			sourcePort = inPacket.getPort();
			sourceAddress = inPacket.getAddress();
			String message = new String(inPacket.getData());



			System.out.println("Received message = " + message);


			if((array.getWindows()[0] != null && !(array.isInArray("Port: " + sourcePort + "    IP Address: " + sourceAddress.getHostAddress()))) ){ 
				newMessage MyMessage = new newMessage();
				MyMessage.setVisible(true);
				MyMessage.setTitle("Port: " + sourcePort + "    IP Address: " + sourceAddress.getHostAddress());
				MyMessage.addToTextArea("Sender: " + message);
				array.addWindow(MyMessage);

			} else {
				int index = array.getWindowIndex();
				array.getWindows()[index].addToTextArea("Sender: " + message);
			}


		} while(true);
	}


	public MyMessage() {
		getContentPane().setBackground(Color.RED);
		initialize();
	}

	
	// Initialize the contents of the frame.
	 
	private void initialize() {
		StartChat = new JFrame();
		StartChat.getContentPane().setForeground(Color.LIGHT_GRAY);
		StartChat.setTitle("Start Message");
		StartChat.setBounds(100, 100, 600, 400);
		StartChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		StartChat.getContentPane().setLayout(null);
		

		
		
		
		IPField = new JTextField();
		IPField.setToolTipText("enters Ip address");
		IPField.setBounds(128, 103, 180, 30);
		StartChat.getContentPane().add(IPField);
		IPField.setColumns(10);
		
		
		port = new JTextField();
		port.setToolTipText("Enter port number");
		port.setBounds(128, 65, 100, 30);
		StartChat.getContentPane().add(port);
		port.setColumns(10);
		
		MSG = new JTextField();
		MSG.setToolTipText("start message");
		MSG.setBounds(128, 139, 251, 30);
		StartChat.getContentPane().add(MSG);
		MSG.setColumns(10);
		
		
		JButton startButton =  new JButton("Communicate/other");
		startButton.setForeground(Color.RED);
		
		startButton.addActionListener(this);
		startButton.setBounds(55, 228, 159, 50);
		StartChat.getContentPane().add(startButton);
		
		JButton close= new JButton("Close");
		close.setForeground(Color.RED);
	
		close.setBounds(258, 233, 121, 40);
		StartChat.getContentPane().add(close);
		close.addActionListener(this);
		
		JLabel port = new JLabel("Port number");
		port.setForeground(Color.RED);
		
		port.setHorizontalAlignment(SwingConstants.LEFT);
		port.setBounds(42, 70, 137, 16);
		StartChat.getContentPane().add(port);
		
		ipAddress = new JLabel("IP address");
		ipAddress.setForeground(Color.RED);
		ipAddress.setHorizontalAlignment(SwingConstants.LEFT);
		
		ipAddress.setBounds(42, 108, 132, 16);
		StartChat.getContentPane().add(ipAddress);
		
		chat = new JLabel("my message");
		chat.setForeground(Color.RED);
		chat.setHorizontalAlignment(SwingConstants.LEFT);
		
		chat.setBounds(42, 144, 105, 16);
		StartChat.getContentPane().add(chat);
	    
	    JPanel panel = new JPanel();
	    panel.setBounds(77, 179, 10, 10);
	    StartChat.getContentPane().add(panel);
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		
	///JButton btnClicked = (JButton) e.getSource();
		// for the switch to work
		
		String btn=e.getActionCommand();
		InetAddress otherHost = null;
		int portNumber;
		
	
		if(btn.equalsIgnoreCase("Communicate/other")){
			try {
				otherHost = InetAddress.getByName(IPField.getText());
				IPField.setText("");
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				
				System.out.println(otherHost.getHostAddress());
				
				portNumber = Integer.parseInt(port.getText());
				port.setText("");
				 
				String text = MSG.getText();
				MSG.setText("");
				
				 newMessage MyMessage = new newMessage();
				
				 MyMessage.setInstance(socket, text, SendReceive,  StartChat);
				 MyMessage.setTitle("Port: " + portNumber + "    IP Address: " + otherHost.getHostAddress());
				 MyMessage.setIpAndPort(otherHost, portNumber);
				 MyMessage.setVisible(true);
				 
				MyMessage.addToTextArea("you: " + text);
				StartChat.setVisible(false);
				SendReceive.messageSend(text, otherHost, portNumber);
				
				array.addWindow(MyMessage);
			
		}else if(btn.equalsIgnoreCase("Close")){
			System.exit(0);
			
		
			
			

			
			
		/**	switch (btnClicked.getText()) {
			case "Start":
				
				try {
					
				otherHost = InetAddress.getByName(IPField.getText());
				IPField.setText("");
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				
				System.out.println(otherHost.getHostAddress());
				
				portNumber = Integer.parseInt(port.getText());
				port.setText("");
				 
				String text = MSG.getText();
				MSG.setText("");
				
				 newMessage myMessage = new newMessage();////  just to test
				 //myChat = new newChat();
				 
				 myMessage.setInstance(socket, text, SendReceive, StartChat);
				 myMessage.setTitle("Port: " + portNumber + "    IP Address: " + otherHost.getHostAddress());
				 myMessage.setIpAndPort(otherHost, portNumber);
				 myMessage.setVisible(true);
				 

				 myMessage.addToTextArea("You: " + text);
				 StartChat.setVisible(false);
				SendReceive.messageSend(text, otherHost, portNumber);
				
				array.addWindow(myMessage);
			
				break;
			    case "Close":
			    	
		////		JOptionPane.showMessageDialog(null, "BYE");
			///	System.exit(JFrame.EXIT_ON_CLOSE);
				System.exit(0);
				break;
		*/
			}
		}
		
		

		
	}

