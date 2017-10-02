/**
 * BattleshipServer.java 
 * 
 * Version: 
 *	   v 1.0 11/21/2016
 *
 * Revisions:
 *     Initial revision 
 */

/**
 * This the battleship server class which performs all the updation of playboard 
 * and shiplocations. 
 *
 * @author		Abhishek Shakwala
 * @author		Saurabh Rewaskar
 */

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.Naming;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BattleshipServer implements ActionListener {
	
	private JFrame battleFrame;
	
	private JLabel header;
	
	private JLabel status;
	
	private JPanel control;
	
	/*
	 * This is the constructor of the class which calls another method for 
	 * initializing the server screen.
	 * 
	 */
	
	public BattleshipServer(){
		initializeServerFrame();
	}
	
	/*
	 * Starts the server screen when the server is initialized or started.
	 */
	public void initializeServerFrame(){
		battleFrame = new JFrame("Battleship Server");
		battleFrame.setSize(400, 400);
		battleFrame.setLayout(new GridLayout(3, 1));
		header = new JLabel("", JLabel.CENTER);
		status = new JLabel("", JLabel.CENTER);
		status.setSize(350, 100);
		battleFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
		
		control = new JPanel();
		control.setLayout(new FlowLayout());
		battleFrame.add(header);
		battleFrame.add(control);
		battleFrame.add(status);
		battleFrame.setVisible(true);
	}
	
	/*
	 * In this function button is created and has been binded with the action listener.
	 */
	
	public void eventDemo(){
		header.setText("Please Start server for the players to play the Battleship game.");
		JButton startServer = new JButton("Start Server");
		startServer.setActionCommand("Start");
		startServer.addActionListener(this);
		control.add(startServer);
		battleFrame.setVisible(true);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		if(command.equals("Start")){
			BattleshipServer.startServer();
			status.setText("Server is started now. You can now begain playing the game.");
		}
	} 
	
	/*
	 * startServer function starts the server and binds the object of the 
	 * server to a particular name.
	 * 
	 */
	
	public static void startServer(){
		try{
			BattleshipServerInterface bsi = new BattleshipImplementation();
			Naming.rebind("Battleship", bsi);
			bsi.setShipOption();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		BattleshipServer s = new BattleshipServer();
		s.eventDemo();
	}
}
