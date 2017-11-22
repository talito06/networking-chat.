import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class newMessage extends JFrame implements ActionListener{

	private JPanel panel;
	private JTextField textField;
	private JTextArea textArea;
    private JButton Close;
    private JButton send;
    private JScrollPane scrollPane;
    private InetAddress address = null;
    private int port;
    private static DatagramSocket mySocket = null;
    private String message; 
    private static sendMessage sendReceive;
    private JFrame mainWindow;
  

	public void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					newMessage frame = new newMessage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setInstance(DatagramSocket socket, String text, sendMessage newSend, JFrame startChat) {
		mySocket = socket;
		message = text;
		sendReceive = newSend;
		mainWindow = startChat;
	}


	
	public void setIpAndPort(InetAddress add, int port) {
		address = add;
		this.port = port;
		
	}
	
	
	 /// Create the frame.
	 
	public newMessage() {
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(panel);
		panel.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setEnabled(false);
		textArea.setEditable(false);
		textArea.setBounds(25, 29, 407, 153);
		panel.add(textArea);
		
		Close = new JButton("Close");
		Close.setBounds(35, 242, 117, 29);
		panel.add(Close);
		Close.addActionListener(this);
		
		send = new JButton("sendMessage");
		send.setBounds(274, 242, 117, 29);
		panel.add(send);
		send.addActionListener(this);
		
		textField = new JTextField();
		textField.setBounds(25, 194, 300, 36);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewChat = new JButton("New Chat");
		btnNewChat.setBounds(350, 199, 100, 29);
		panel.add(btnNewChat);
		btnNewChat.addActionListener(this);
		
		scrollPane = new JScrollPane(textArea, 
				   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(25, 29, 300, 153);
		panel.add(scrollPane);
		
	}

	public void addToTextArea(String text) {
		if (textArea.getText().trim().length() == 0) {
			textArea.append(text);
		} else {
		textArea.append("\n" + text);
		}
	}
	
	
	
	
	@Override 
	public void actionPerformed(ActionEvent e) {
		
	   /**
	    * Here  case  sendMessage is 
	    * 
	    * case close" when i press close is close the gui.
	    * case new chat it opens a new window so i can sent another message
	    */
		
		JButton btnClicked = (JButton) e.getSource();
		String button = btnClicked.getText();
		 
	
		switch (button) {
	     case "sendMessage":
			 String text = textField.getText();
		    textField.setText("");
			 sendReceive.messageSend(text, address, port);
			 addToTextArea("MY message: " + text);
			 
	
	  break;
			
	 case "Close":
	//	 JOptionPane.showMessageDialog(null, "BYE"); so another windows opens when
		 // i press close and says bye
		setVisible(false);
			break;
			
	 case "New Chat":
		 mainWindow.setVisible(true);
	//	 break;
			
	
		}				
		
	 }
	}

