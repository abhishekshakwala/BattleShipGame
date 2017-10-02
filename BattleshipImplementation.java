/**
 * BattleshipImplementation.java
 * 
 * Version: 
 *	   v 1.0 11/21/2016
 *
 * Revisions:
 *     Initial revision 
 */

/**
 * This the battleship implementation class implements the server remote interface
 * which is BattleshipServerInterface and hits the model for any updation in the 
 * shiplocation or playboard for the respective player. 
 *
 * @author		Abhishek Shakwala
 * @author		Saurabh Rewaskar
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class BattleshipImplementation extends UnicastRemoteObject implements BattleshipServerInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<List<Integer>> hitLocationA = new ArrayList<List<Integer>>();
	
	private List<List<Integer>> hitLocationB = new ArrayList<List<Integer>>();
	
	String[][] playBoardA = new String[10][10];
	
	String[][] playBoardB = new String[10][10];
	
	String[][] playBoard;
	
	boolean gameover = false;
	
	BattleShipModel bm = new BattleShipModel();
	
	List<List<Integer>> shipLocationA = new ArrayList<List<Integer>>();
	
	List<List<Integer>> noShipLocationA = new ArrayList<List<Integer>>();
	
	List<List<Integer>> shipLocationB = new ArrayList<List<Integer>>();
	
	List<List<Integer>> noShipLocationB = new ArrayList<List<Integer>>();

		
	protected BattleshipImplementation() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void setPlayboard(String player, int row, int column) throws RemoteException {
		BattleShipModel.createPlayBoard(player, row, column);
	}
	
	public void setShipOption() throws RemoteException {
		BattleShipModel.shipOption();
	}
	
	public ArrayList<String> getshipClasses() throws RemoteException {
		return BattleShipModel.getshipClasses();
	}
	
	public ArrayList<String> getshipNames() throws RemoteException {
		return BattleShipModel.getshipNames();
	}
	
	public String[][] getPlayBoard() throws RemoteException {
		return BattleShipModel.getPlayBoard();
	}
	
	public void setCreateShip(String player, String[][] playBoard, int userMinRow,
			int userMinColumn, int userMaxRow, int userMaxColumn) throws RemoteException {
		BattleShipModel.setCreateShip(player, playBoard, userMinRow, userMinColumn, userMaxRow, userMaxColumn);
	}
	
	public void setShipLocation(String player, int userMinRow, int userMinColumn,
			int userMaxRow, int userMaxColumn) throws RemoteException {
		BattleShipModel.setShipLocation(player, userMinRow, userMinColumn, userMaxRow, userMaxColumn);
	}
	
	public List<List<Integer>> getShipLocationA() throws RemoteException {
		return BattleShipModel.getShipLocationA();
	} 
	
	public List<List<Integer>> getShipLocationB() throws RemoteException {
		return BattleShipModel.getShipLocationB();
	} 
	
	public List<List<Integer>> getNoShipLocationA() throws RemoteException {
		return BattleShipModel.getNoShipLocationA();
	} 
	
	public List<List<Integer>> getNoShipLocationB() throws RemoteException {
		return BattleShipModel.getNoShipLocationB();
	}

	@Override
	public boolean setGameStatus() throws RemoteException {
		// TODO Auto-generated method stub
		return BattleShipModel.setGameStatus();
	}
	
	@Override
	public boolean getGameStatus() throws RemoteException {
		// TODO Auto-generated method stub
		return BattleShipModel.getGameStatus();
	}
	
	public boolean playGameWith(String player) throws RemoteException {
		int row, column;
		boolean hit = true;
		shipLocationA = BattleShipModel.getShipLocationA();
		shipLocationB = BattleShipModel.getNoShipLocationB();
		playBoard = BattleShipModel.getPlayBoard();
		
		label1: while (hit == true && !(shipLocationA.isEmpty())
				|| !(shipLocationB.isEmpty())) {
			Scanner sc = new Scanner(System.in);
			if (player.equals("A")) {
				System.out.println("Player A Start Attacking");
			} else {
				System.out.println("Player B Start Attacking");
			}
			System.out.println();
			System.out.println("Player " + player + " play your move");
			System.out.println("Enter the row you want to attack:");
			row = sc.nextInt();
			System.out.println("Enter the column you want to attack:");
			column = sc.nextInt();
			if (row > playBoard.length - 1
					|| column > playBoard.length - 1) {
				System.out
						.println("Please enter valid input which is part of playboard.");
				continue label1;
			} else if (player.equals("A")
					&& hitLocationA.contains(Arrays.asList(row, column))) {
				System.out
						.println("You have already hit this location previously. Enter once again.");
				continue label1;
			} else if (player.equals("B")
					&& hitLocationB.contains(Arrays.asList(row, column))) {
				System.out
						.println("You have already hit this location previously. Enter once again.");
				continue label1;
			}
			if (player.equals("A")) {
				if (shipLocationB.contains(Arrays.asList(row, column))) {
					hitLocationA.add(Arrays.asList(row, column));
					playBoardA[row][column] = "X";
					for(int i = 0; i < 10; i++){
						for(int j = 0; j < 10; j++){
							if(playBoardA[i][j] == null){
								playBoardA[i][j] = "-";
							}
							System.out.print(playBoardA[i][j]);
						}
						System.out.println();
					}
					System.out
					.println("You have a hit. Please Continue playing your move..");
					shipLocationB.remove(Arrays.asList(row, column));
					hit = true;
				} else {
					hitLocationA.add(Arrays.asList(row, column));
					playBoardA[row][column] = "X";
					for(int i = 0; i < 10; i++){
						for(int j = 0; j < 10; j++){
							if(playBoardA[i][j] == null){
								playBoardA[i][j] = "-";
							}
							System.out.print(playBoardA[i][j]);
						}
						System.out.println();
					}
					System.out.println("It's a miss.");
					hit = false;
					// player = "B";
					break;
				}
			} else {
				if (shipLocationA.contains(Arrays.asList(row, column))) {
					hitLocationB.add(Arrays.asList(row, column));
					playBoardB[row][column] = "X";
					for(int i = 0; i < 10; i++){
						for(int j = 0; j < 10; j++){
							if(playBoardB[i][j] == null){
								playBoardB[i][j] = "-";
							}
							System.out.print(playBoardB[i][j]);
						}
						System.out.println();
					}
					System.out
							.println("You have a hit. Please Continue playing your move..");
					shipLocationA.remove(Arrays.asList(row, column));
					hit = true;
				} else {
					hitLocationB.add(Arrays.asList(row, column));
					playBoardB[row][column] = "X";
					for(int i = 0; i < 10; i++){
						for(int j = 0; j < 10; j++){
							if(playBoardB[i][j] == null){
								playBoardB[i][j] = "-";
							}
							System.out.print(playBoardB[i][j]);
						}
						System.out.println();
					}
					System.out.println("It's a miss.");
					hit = false;
					// player = "A";
					break;
				}
			}
			if (player.equals("B") && shipLocationA.isEmpty()) {
				System.out.println("Player B WINS!!");
				gameover = true;
			} else if (player.equals("A") && shipLocationB.isEmpty()) {
				System.out.println("Player A WINS!!");
				gameover = true;
			}
			return gameover;
		}
		return gameover;
	} 
	
	public void setShipLocationA(List<List<Integer>> shipLocationA) throws RemoteException{
		BattleShipModel.shipLocationA = shipLocationA;
		bm.setShipLocationA(shipLocationA);
	}
	
	public void setShipLocationB(List<List<Integer>> shipLocationB) throws RemoteException{
		BattleShipModel.shipLocationB= shipLocationB;
		bm.setShipLocationB(shipLocationB);
	}

	@Override
	public void setShipPointA(ArrayList<Integer> shipPointA) throws RemoteException {
		// TODO Auto-generated method stub
		bm.setShipPointA(shipPointA);
	}

	@Override
	public void setShipPointB(ArrayList<Integer> shipPointB) throws RemoteException {
		// TODO Auto-generated method stub
		bm.setShipPointB(shipPointB);
	}

	@Override
	public ArrayList<Integer> getShipPointA() throws RemoteException {
		// TODO Auto-generated method stub
		return BattleShipModel.shipPointA;
	}

	@Override
	public ArrayList<Integer> getShipPointB() throws RemoteException {
		// TODO Auto-generated method stub
		return BattleShipModel.shipPointB;
	}

	@Override
	public int getFLagA() throws RemoteException {
		// TODO Auto-generated method stub
		return BattleShipModel.getFLagA();
	}

	@Override
	public int getFLagB() throws RemoteException {
		// TODO Auto-generated method stub
		return BattleShipModel.getFLagB();
	}

	@Override
	public void setFLagA(int flag) throws RemoteException {
		// TODO Auto-generated method stub
		BattleShipModel.setFLagA(flag);
	}

	@Override
	public void setFLagB(int flag) throws RemoteException {
		// TODO Auto-generated method stub
		BattleShipModel.setFLagB(flag);
	}

	@Override
	public int getPlayerAFlag() throws RemoteException {
		// TODO Auto-generated method stub
		return BattleShipModel.getPlayerAFlag();
	}

	@Override
	public int getPlayerBFlag() throws RemoteException {
		// TODO Auto-generated method stub
		return BattleShipModel.getPlayerBFlag();
	}

	@Override
	public void setPlayerAFlag(int flag) throws RemoteException {
		// TODO Auto-generated method stub
		BattleShipModel.setPlayerAFlag(flag);
	}

	@Override
	public void setPlayerBFlag(int flag) throws RemoteException {
		// TODO Auto-generated method stub
		BattleShipModel.setPlayerBFlag(flag);
	}
}
