/**
 * BattleshipServerInterface.java
 * 
 * Version: 
 *	   v 1.0 11/21/2016
 *
 * Revisions:
 *     Initial revision 
 */

/**
 * This the battleship remote interface which declares all the method which a remote 
 * client object can access.
 *
 * @author		Abhishek Shakwala
 * @author		Saurabh Rewaskar
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


public interface BattleshipServerInterface extends Remote{
	
	public void setPlayboard(String player, int row, int column) throws RemoteException;
	
	public void setShipOption() throws RemoteException;
	
	public ArrayList<String> getshipClasses() throws RemoteException;
	
	public ArrayList<String> getshipNames() throws RemoteException;
	
	public String[][] getPlayBoard() throws RemoteException;
	
	public void setCreateShip(String player, String[][] playBoard, int userMinRow,
			int userMinColumn, int userMaxRow, int userMaxColumn) throws RemoteException;
	
	public void setShipLocation(String player, int userMinRow, int userMinColumn,
			int userMaxRow, int userMaxColumn) throws RemoteException;
	
	public List<List<Integer>> getShipLocationA() throws RemoteException;
	
	public void setShipLocationA(List<List<Integer>> shipLocationA) throws RemoteException;
	
	public List<List<Integer>> getShipLocationB() throws RemoteException;
	
	public void setShipLocationB(List<List<Integer>> shipLocationB) throws RemoteException;
	
	public void setShipPointA(ArrayList<Integer> shipPointA) throws RemoteException;
	
	public void setShipPointB(ArrayList<Integer> shipPointB) throws RemoteException;
	
	public ArrayList<Integer> getShipPointA() throws RemoteException;
	
	public ArrayList<Integer> getShipPointB() throws RemoteException;
	
	public List<List<Integer>> getNoShipLocationA() throws RemoteException;
	
	public List<List<Integer>> getNoShipLocationB() throws RemoteException;
	
	public boolean setGameStatus() throws RemoteException;
	
	public boolean getGameStatus() throws RemoteException;
	
	public boolean playGameWith(String player) throws RemoteException;
	
	public int getFLagA() throws RemoteException;
	
	public int getFLagB() throws RemoteException;
	
	public void setFLagA(int flag) throws RemoteException;
	
	public void setFLagB(int flag) throws RemoteException;
	
	public int getPlayerAFlag() throws RemoteException;
	
	public int getPlayerBFlag() throws RemoteException;
	
	public void setPlayerAFlag(int flag) throws RemoteException;
	
	public void setPlayerBFlag(int flag) throws RemoteException;
}
