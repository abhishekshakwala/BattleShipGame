/**
 * PlayerGrid.java 
 * 
 * Version: 
 *	   v 1.0 11/21/2016
 *
 * Revisions:
 *     Initial revision 
 */

/**
 * This the PlayerGrid class which performs creating of ships 
 * on the playboard and actual playing the battleship game. 
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

public class PlayerGrid extends JFrame implements MouseListener, ActionListener{
	JFrame shipFrame = new JFrame();
	JLabel fleetPanel1 = new JLabel();
	JLabel playerFleetLabel;
	JLabel playerHittingLabel;
	JPanel fleet1 = new JPanel();
	JLabel shipPanel1[];
	
	JLabel fleetPanel2 = new JLabel();
	JPanel fleet2 = new JPanel();
	JLabel shipPanel2[];
	int flag = 1;
	int flagA = 0;
	int flagB = 0;
	int playerAFlag = 0;
	int playerBFlag = 0;
	ArrayList<Integer> shipLocation = new ArrayList<Integer>();
	List<List<Integer>> shipLocationA = new ArrayList<List<Integer>>();
	static ArrayList<Integer> shipPointsA = new ArrayList<Integer>();
	
	static ArrayList<Integer> shipPointsB = new ArrayList<Integer>();
	
	static ArrayList<Integer> hitShipPoint = new ArrayList<Integer>();
	
	static ArrayList<Integer> modelHitShipPoint = new ArrayList<Integer>();
	
	static ArrayList<Integer> checkShipPoints = new ArrayList<Integer>();
	
	BattleshipServerInterface bsi;
	
	int userMinRow, userMinColumn, userMaxRow, userMaxColumn, rowDiff, colDiff;
	int shipX, shipY;
	String player;
	
	    public PlayerGrid(String player, ArrayList<Integer> shipPoints, BattleshipServerInterface bsi) throws RemoteException{

	        super("Player " + player + " playing window");
	        this.player = player;
	        this.bsi = bsi;
	        setLayout(new GridLayout(5, 1));
	        setSize(600,800);
	        fleet1.setSize(400, 400);
	        fleetPanel1.setSize(300, 300);
	        fleet1.setLayout(new FlowLayout());
	        playerFleetLabel = new JLabel("Player " +player+ " you have following fleet created: ");
	        setResizable(true);
	        fleetPanel1.setLayout(new GridLayout(10,10));
	        setVisible(true);
	        shipPanel1 = new JLabel[100];

	        for (int i = 0; i < shipPanel1.length; i++){
	        	String cellName = Integer.toString(i);
	            shipPanel1[i]=new JLabel("");
	            shipPanel1[i].setText(cellName);
	            shipPanel1[i].setOpaque(true);
	            if((!(shipPoints.isEmpty()) && shipPoints.contains(Integer.parseInt(cellName)))){
	            	shipPanel1[i].setBackground(Color.blue);
	            } else {
	            	shipPanel1[i].setBackground(Color.white);
	            }
	            shipPanel1[i].setBorder(BorderFactory.createLineBorder(Color.black));
	        }
	        
	        //Add all the grid cells (JLabels)
	        
	        for (int i = 0; i < shipPanel1.length; i++){
	        	fleetPanel1.add(shipPanel1[i]);
	        }
	        //fleetPanel.add(createFleet);
	        fleet1.add(fleetPanel1);
	        add(playerFleetLabel);
	        add(fleetPanel1);
	        add(fleet1);
	        
	        /*-----------------Player's grid starts here-----------------*/
	        
	        fleet2.setSize(400, 400);
	        //fleet2.setLocation(350, 400);
	        fleetPanel2.setSize(300, 300);
	        fleet2.setLayout(new FlowLayout());
	        playerHittingLabel = new JLabel("Player " +player+ " Hitting playboard: ", JLabel.LEFT);
	        fleetPanel2.setLayout(new GridLayout(10,10));
	        shipPanel2 = new JLabel[100];

	        for (int i = 0; i < shipPanel2.length; i++){
	        	String cellName = Integer.toString(i);
	            shipPanel2[i]=new JLabel("");
	            shipPanel2[i].setText(cellName);
	            shipPanel2[i].setOpaque(true);
	            shipPanel2[i].setBackground(Color.white);
	            shipPanel2[i].setBorder(BorderFactory.createLineBorder(Color.black));
	            shipPanel2[i].addMouseListener(this);
	        }
	        
	        //Add all the grid cells (JLabels)
	        
	        for (int i = 0; i < shipPanel2.length; i++){
	        	fleetPanel2.add(shipPanel2[i]);
	        }
	        //fleetPanel.add(createFleet);
	        fleet2.add(fleetPanel2);
	        add(playerHittingLabel);
	        add(fleetPanel2);
	        add(fleet2);	  
	    }	    
	    
	    public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String command = e.getActionCommand();
			if(command.equals("Check")){
				try {
					if(player.equals("A")){
						modelHitShipPoint = bsi.getShipPointB();
					} else {
						modelHitShipPoint = bsi.getShipPointA();
					}
					if(modelHitShipPoint.contains(hitShipPoint.get(0))){
						
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	    @Override
	    public void mouseClicked(MouseEvent e) {
	        JLabel clickedPanel = (JLabel) e.getSource();
	        try{
	        	flagA = bsi.getFLagA();
	        	flagB = bsi.getFLagB();
	        } catch(RemoteException re){
	        	re.printStackTrace();
	        }
	        if(flagA == 1 && flagB == 1){
	        	flagA++;
	        	flagB++;
	        if(flag == 1){
	        	flag++;
	        if(player.equals("A")){
	        	try {
					checkShipPoints = bsi.getShipPointB();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				try {
					checkShipPoints = bsi.getShipPointA();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	        }
	        if (clickedPanel.getBackground()==Color.white){
	        	
	            try {
	            playerAFlag = bsi.getPlayerAFlag();
	            playerBFlag = bsi.getPlayerBFlag();

	            	if(playerAFlag == 1 && player.equals("A")){
	            		clickedPanel.setEnabled(false);
	    	            clickedPanel.setBackground(Color.blue);
	    	            hitShipPoint.add(Integer.parseInt(clickedPanel.getText()));
	            		modelHitShipPoint = bsi.getShipPointB();
	        		} else if(playerBFlag == 1 && player.equals("B")){
	        			clickedPanel.setEnabled(false);
	    	            clickedPanel.setBackground(Color.blue);
	    	            hitShipPoint.add(Integer.parseInt(clickedPanel.getText()));
	        			modelHitShipPoint = bsi.getShipPointA();
	        		}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(!(modelHitShipPoint.isEmpty()) && !(hitShipPoint.isEmpty()) && modelHitShipPoint.contains(hitShipPoint.get(0))){
					clickedPanel.setBackground(Color.red);
					int index = hitShipPoint.indexOf(Integer.parseInt(clickedPanel.getText()));
					hitShipPoint.remove(index);
					int ind = checkShipPoints.indexOf(Integer.parseInt(clickedPanel.getText()));
					checkShipPoints.remove(ind);
					String message = "You have a Hit Continue playing!! :)";
					JOptionPane.showMessageDialog(null, message);
					if(player.equals("A")){
						try {
							bsi.setPlayerAFlag(1);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else if(player.equals("B")){
						try {
							bsi.setPlayerBFlag(1);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} else if(!(modelHitShipPoint.isEmpty()) && !(hitShipPoint.isEmpty())){
					int index = hitShipPoint.indexOf(Integer.parseInt(clickedPanel.getText()));
		            hitShipPoint.remove(index);
		            String message = "You Missed the target!! :(";
					JOptionPane.showMessageDialog(null, message);
					if(player.equals("A")){
						try {
							bsi.setPlayerAFlag(0);
							bsi.setPlayerBFlag(1);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else if(player.equals("B")){
						try {
							bsi.setPlayerAFlag(1);
							bsi.setPlayerBFlag(0);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} else {
					String message = "You cannot play because you had missed the target in your last chance.";
					JOptionPane.showMessageDialog(null, message);
				}
				if(checkShipPoints.isEmpty()){
					if(player.equals("A")){
						String msg = "Player " + player + " WINS";
						JOptionPane.showMessageDialog(null, msg);
						dispose();
					} else {
						String msg = "Player " + player + " WINS";
						JOptionPane.showMessageDialog(null, msg);
						dispose();
					}
				}
	        }

	        } else {
	        	String message = "Game has not yet started. Another player is not ready!!";
	        	JOptionPane.showMessageDialog(null, message);
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
