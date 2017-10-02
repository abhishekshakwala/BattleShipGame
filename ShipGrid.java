/**
 * ShipGrid.java 
 * 
 * Version: 
 *	   v 1.0 11/21/2016
 *
 * Revisions:
 *     Initial revision 
 */

/**
 * This the ShipGrid class which performs creating of ships 
 * on the playboard. 
 *
 * @author		Abhishek Shakwala
 * @author		Saurabh Rewaskar
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

public class ShipGrid extends JFrame implements MouseListener, ActionListener{
	
	JFrame shipFrame = new JFrame();
	JLabel fleetPanel = new JLabel();
	JPanel fleet = new JPanel();
	JLabel shipPanel[];
	JButton createFleet = new JButton("Create Fleet");
	ArrayList<Integer> shipLocation = new ArrayList<Integer>();
	List<List<Integer>> shipLocationA = new ArrayList<List<Integer>>();
	
	public static JPanel printPlayboard;
	
	public static JLabel[] printplayboardLabel;
	
	public static JLabel playboardLabel;
	
	public static JFrame playerFrame;
	
	static ArrayList<Integer> shipPointsA = new ArrayList<Integer>();
	
	static ArrayList<Integer> shipPointsB = new ArrayList<Integer>();
	
	static BattleshipServerInterface bsi;
	
	int userMinRow, userMinColumn, userMaxRow, userMaxColumn, rowDiff, colDiff;
	int shipX, shipY;
	static String player;
	
	/*
	 * It is the constructor of the class which creates and prints the 
	 * playboard to perform the playboard operations.
	 * 
	 * @param	player	player name is given.
	 * 
	 * @param	bsi		remote object of Remote Interface     
	 */
	
	public ShipGrid(String player, BattleshipServerInterface bsi){

	        super("Create Fleet Player " + player);
	        this.player = player;
	        this.bsi = bsi;
	        setSize(600,600);
	        setLayout(new GridLayout(3, 1));
	        fleet.setSize(500, 500);
	        fleetPanel.setSize(300, 300);
	        fleet.setLayout(new FlowLayout());
	        setResizable(true);
	        fleetPanel.setLayout(new GridLayout(10,10));
	        fleetPanel.addMouseListener(this);
	        setVisible(true);
	        createFleet.setLocation(100, 400);
	        createFleet.addActionListener(this);
	        createFleet.setActionCommand("Create");
	        shipPanel = new JLabel[100];

	        for (int i = 0; i < shipPanel.length; i++){
	        	String cellName = Integer.toString(i);
	            shipPanel[i]=new JLabel("");
	            shipPanel[i].setText(cellName);
	            shipPanel[i].setOpaque(true);
	            shipPanel[i].setBackground(Color.white);
	            shipPanel[i].setBorder(BorderFactory.createLineBorder(Color.black));
	            shipPanel[i].addMouseListener(this);
	        }
	        
	        //Add all the grid cells (JLabels)
	        
	        for (int i = 0; i < shipPanel.length; i++){
	        	fleetPanel.add(shipPanel[i]);
	        }
	        //fleetPanel.add(createFleet);
	        fleet.add(fleetPanel);
	        fleet.add(createFleet);
	        add(fleetPanel);
	        add(fleet);
	    }
	    
		/*
		 * (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
	
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String command = e.getActionCommand();
			if(command.equals("Create")){
				for(int i = 0; i < shipLocation.size(); i++){
				System.out.println(shipLocation.get(i));
				}
				try {
					if(player.equals("A")){
						bsi.setShipPointA(shipLocation);
						clientBoardGame();
					} else {
						bsi.setShipPointB(shipLocation);
						clientBoardGame();
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		}
		
		/*
		 * clientBoardGame method prints the actual created fleet or playboard 
		 * by the respective player.
		 * 
		 */
		public static void clientBoardGame() throws RemoteException{
			playerFrame = new JFrame("Player " + player + " Window");
			printPlayboard = new JPanel();
			playboardLabel = new JLabel();
			if(player.equals("A")){
				shipPointsA = bsi.getShipPointA();
				bsi.setFLagA(1);
				PlayerGrid pg = new PlayerGrid(player, shipPointsA, bsi);
			} else {
				shipPointsB = bsi.getShipPointB();
				bsi.setFLagB(1);
				PlayerGrid pg = new PlayerGrid(player, shipPointsB, bsi);
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
	    @Override
	    public void mouseClicked(MouseEvent e) {
	        JLabel clickedPanel = (JLabel) e.getSource();
    
	        if (clickedPanel.getBackground()==Color.white){
	            clickedPanel.setBackground(Color.blue);
	            shipLocation.add(Integer.parseInt(clickedPanel.getText()));
	        } else { 
	            clickedPanel.setBackground(Color.white);
	            int index = shipLocation.indexOf(Integer.parseInt(clickedPanel.getText()));
	            shipLocation.remove(index);
	        }
	    }

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
}
