/**
 * BattleshipClient.java 
 * 
 * Version: 
 *	   v 1.0 11/21/2016
 *
 * Revisions:
 *     Initial revision 
 */

/**
 * Class BattleShipClient is used to create a player for
 * the game. This class implements ActionListener interface
 * in order to execute it ActionPerformed functions so that
 * we can set specific actions performed on events like button
 * press and screen clicks. 
 *
 * @author		Abhishek Shakwala
 * @author		Saurabh Rewaskar
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.*;

public class BattleshipClient implements ActionListener {

	static String player;
	
	int row = 10, column = 10;
	
	static ArrayList<Integer> shipPointsA = new ArrayList<Integer>();
	
	static ArrayList<Integer> shipPointsB = new ArrayList<Integer>();
	
	static BattleshipServerInterface bsi;
	
	public JFrame clientFrame;
	
	public JLabel clientHeader;
	
	public JLabel clientStatus;
	
	public JPanel clientControl;
	
	public static JTextField playerName;
	
	public static JFrame clientGameFrame;
	
	public static JPanel clientGameControl;
	
	public static JButton createFleetButton;
	
	public static JPanel printPlayboard;
	
	public static JLabel[] printplayboardLabel;
	
	public static JLabel playboardLabel;
	
	public static JFrame playerFrame;
	
	/**
	 * Takes the user input for the type of ship to be created.
	 * 
	 * @param  player  		holds the actual player A or B who is currently active
	 * 				   		for play.
	 * 
	 */
	
	public BattleshipClient(BattleshipServerInterface bssi){
		this.bsi = bssi;
		initializeClientFrame();
	}
	
	/**
	 * 
	 * Used to initialize Client Frame by setting a grid
	 * specified by sizing coordinates
	 * 
	 * @param None
	 * 
	 */
	
	public void initializeClientFrame(){
		clientFrame = new JFrame("Battleship Client");
		clientFrame.setSize(400, 400);
		clientFrame.setLayout(new GridLayout(3, 1));
		clientHeader = new JLabel("", JLabel.CENTER);
		clientStatus = new JLabel("", JLabel.CENTER);
		clientStatus.setSize(350, 100);
		clientFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
		clientControl = new JPanel();
		clientControl.setLayout(new FlowLayout());
		clientFrame.add(clientHeader);
		clientFrame.add(clientControl);
		clientFrame.add(clientStatus);
		clientFrame.setVisible(true);
	}
	
	/**
	 * Here the Client Frame which is set up prompts for
	 * User credentials in order to identify it as a
	 * player of the game. The client is then joined to
	 * the server
	 * 
	 * @param None
	 */
	
	public void clientEventDemo(){
		JLabel caption = new JLabel("Enter player name: ");
		playerName = new JTextField(10);
		JButton joinServer = new JButton("Join Server");
		joinServer.setActionCommand("Join");
		joinServer.addActionListener(this);
		clientControl.add(caption);
		clientControl.add(playerName);
		clientControl.add(joinServer);
		clientFrame.setVisible(true);
	}
	
	/**This function is executed so that
	 * we can set specific actions performed on events like button
	 * press and screen clicks
	 * @param e	: It is the action which is performed
	 * for which we need to specify what should happen
	 * on its execution
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		if(command.equals("Join")){
			try {
				BattleshipClient.startClient(this);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			player = playerName.getText();
			clientFrame.dispose();
		}
		
		if(command.equals("Create")){
			clientGameFrame.dispose();
			ShipGrid sg = new ShipGrid(player, bsi);
			//createFleetButton.setEnabled(false);
		}
	}
	
	/**
	 * This method is used to start the client.
	 * The client then creates respective fleets
	 * by creating a grid of the given setsize
	 * and initialize game control in order to 
	 * switch between the two clients during
	 * gameplay
	 * 
	 * @param c : Client(Player of the game)
	 */
	
	public static void startClient(BattleshipClient c) throws RemoteException {
		// TODO Auto-generated method stub
		c.player = playerName.getText();
		bsi.setPlayboard(c.player, c.row, c.column);
		clientGameFrame = new JFrame("Player " + c.player + " Window");
		clientGameFrame.setSize(300, 300);
		clientGameControl = new JPanel();
		/*shipGrid = new JPanel();
		shipGrid.setSize(200, 200);
		shipGrid.setLayout(new GridLayout(10, 10));
		clientGameFrame.add(shipGrid);*/
		createFleetButton = new JButton("Click here to create Fleet");
		createFleetButton.setLocation(0, 0);
		createFleetButton.setActionCommand("Create");
		createFleetButton.addActionListener(c);
		clientGameControl.add(createFleetButton);
		clientGameFrame.add(clientGameControl);
		clientGameFrame.setVisible(true);
	}
	
	public static ArrayList<Integer> getSetHitShipPoints(String player) throws RemoteException{
		if(player.equals("A")){
			return bsi.getShipPointB();
		} else {
			return bsi.getShipPointA();
		}
	}
	
	/**This method is used to create playboard
	 * for the client. It pops a new window with a grid
	 * where the player has the liberty to choose the
	 * ship locations of his choice and create its
	 * fleet.
	 * 
	 * @param None
	 */
	
	public static void clientBoardGame() throws RemoteException{
		playerFrame = new JFrame("Player " + player + " Window");
		printPlayboard = new JPanel();
		playboardLabel = new JLabel();
		if(BattleshipClient.player.equals("A")){
			shipPointsA = bsi.getShipPointA();
			PlayerGrid pg = new PlayerGrid(player, shipPointsA, bsi);
		} else {
			shipPointsB = bsi.getShipPointB();
			PlayerGrid pg = new PlayerGrid(player, shipPointsB, bsi);
		}
	}
	
	/**
	 * This is the main function which synchronizes the clients
	 * with the server and sets the event demo for the specific
	 * client.
	 */
	
	public static void main(String args[]) throws MalformedURLException, RemoteException, NotBoundException{
		BattleshipServerInterface bssi = (BattleshipServerInterface) Naming.lookup("rmi://" + "localhost" + "/Battleship");
		BattleshipClient c1 = new BattleshipClient(bssi);
		c1.clientEventDemo();
	}
}