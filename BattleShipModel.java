/**
 * BattleShipModel.java 
 * 
 * Version: 
 *	   v 1.0 11/21/2016
 *
 * Revisions:
 *     Initial revision 
 */

/**
 * Once the user enters and validates the input then the control comes to Ship
 * class where createShip function is called to create the ship at the location 
 * specified by the player on the respective playBoard. 
 *
 * @author		Abhishek Shakwala
 * @author		Saurabh Rewaskar
 */

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BattleShipModel {
	
	/**
	 * Creates the ship on the playboard at the user specified location.
	 * 
	 * @param  player  		holds the actual player A or B who is currently active
	 * 				   		for play.
	 * @param  playBoard   	gives the playboard of respective player while entering
	 * 						the ship locations.
	 * @param  userMinRow		minimum location of the ship row wise.
	 * @param  userMaxRow		maximum location of the ship row wise.
	 * @param  userMinColumn	minimum location of the ship column wise.
	 * @param  userMaxColumn	maximum location of the ship column wise.
	 */
	static ArrayList<String> shipClasses = new ArrayList<String>();
	static ArrayList<String> shipNames = new ArrayList<String>();
	static List<List<Integer>> shipLocationA = new ArrayList<List<Integer>>();
	static List<List<Integer>> noShipLocationA = new ArrayList<List<Integer>>();
	static List<List<Integer>> shipLocationB = new ArrayList<List<Integer>>();
	static List<List<Integer>> noShipLocationB = new ArrayList<List<Integer>>();
	static ArrayList<Integer> shipPointA = new ArrayList<Integer>();
	static ArrayList<Integer> shipPointB = new ArrayList<Integer>();
	static String[][] playBoard;
	static boolean gameStatus = false;
	static int playFlagA = 0;
	static int playFlagB = 0;
	static int playerFlagA = 1;
	static int playerFlagB = 1;
	
	public static void createPlayBoard(String player, int row, int column){
		if (player.equals("A")) {
			System.out.println("Player A battle field");
			playBoard = new String[row][column];
			for (int i = 0; i < playBoard.length; i++) {
				for (int j = 0; j < playBoard.length; j++) {
					playBoard[i][j] = "-";
					System.out.print(playBoard[i][j]);
				}
				System.out.println();
			}
		} else if (player.equals("B")) {
			System.out.println("Player B battle field");
			playBoard = new String[row][column];
			for (int i = 0; i < playBoard.length; i++) {
				for (int j = 0; j < playBoard.length; j++) {
					playBoard[i][j] = "-";
					System.out.print(playBoard[i][j]);
				}
				System.out.println();
			}
		}
	}
	
	public static void shipOption(){
		shipNames.add("Destroyer");
		shipNames.add("Cruiser");
		shipNames.add("Battleship");
		shipNames.add("Carrier");
		shipClasses.add("Press 1 for Destroyer of Size 2");
		shipClasses.add("Press 2 for Cruiser of Size 3");
		shipClasses.add("Press 3 for Battleship of Size 4");
		shipClasses.add("Press 4 for Carrier of Size 5");
	}
	
	/*public static void createFleet(String[][] playBoard, String player){
		shipNames.add("Destroyer");
		shipNames.add("Cruiser");
		shipNames.add("Battleship");
		shipNames.add("Carrier");
		shipClasses.add("Press 1 for Destroyer of Size 2");
		shipClasses.add("Press 2 for Cruiser of Size 3");
		shipClasses.add("Press 3 for Battleship of Size 4");
		shipClasses.add("Press 4 for Carrier of Size 5");
	}*/
	
	public static ArrayList<String> getshipClasses(){
		return shipClasses;
	}
	
	public static ArrayList<String> getshipNames(){
		return shipNames;
	}
	
	/**
	 * Stores the players's location of ship on the playboard as well as the 
	 * locations where the player cannot place the ship.
	 * 
	 * @param  player  		holds the actual player A or B who is currently active
	 * 				   		for play.
	 * @param  userMinRow		minimum location of the ship row wise.
	 * @param  userMaxRow		maximum location of the ship row wise.
	 * @param  userMinColumn	minimum location of the ship column wise.
	 * @param  userMaxColumn	maximum location of the ship column wise.
	 */
	
	public static void setCreateShip(String player, String[][] playBoard, int userMinRow,
			int userMinColumn, int userMaxRow, int userMaxColumn) {
		if (userMinRow == userMaxRow) {
			for (int j = userMinColumn; j < userMaxColumn; j++) {
				if (player.equals("A")) {
					playBoard[userMaxRow][j] = "S";
				} else {
					playBoard[userMaxRow][j] = "S";
				}
			}
		} else if (userMinColumn == userMaxColumn) {
			for (int j = userMinRow; j < userMaxRow; j++) {
				if (player.equals("A")) {
					playBoard[j][userMaxColumn] = "S";
				} else {
					playBoard[j][userMaxColumn] = "S";
				}
			}
		}
		BattleShipModel.playBoard = playBoard;
		//this.playBoard = playBoard;
	}
	
	public static String[][] getPlayBoard(){
		return playBoard;
	}
	
	/**
	 * Stores the players's location of ship on the playboard as well as the 
	 * locations where the player cannot place the ship.
	 * 
	 * @param  player  		holds the actual player A or B who is currently active
	 * 				   		for play.
	 * @param  userMinRow		minimum location of the ship row wise.
	 * @param  userMaxRow		maximum location of the ship row wise.
	 * @param  userMinColumn	minimum location of the ship column wise.
	 * @param  userMaxColumn	maximum location of the ship column wise.
	 */
	
	public static void setShipLocation(String player, int userMinRow, int userMinColumn,
			int userMaxRow, int userMaxColumn){

		if (player.equals("A")) {
			if (userMinRow == userMaxRow) {
				for (int j = userMinColumn; j < userMaxColumn; j++) {
					shipLocationA.add(Arrays.asList(userMaxRow, j));
					noShipLocationA.add(Arrays.asList(userMaxRow + 1, j));
					noShipLocationA.add(Arrays.asList(userMaxRow - 1, j));
				}
				noShipLocationA.add(Arrays
						.asList(userMaxRow, userMinColumn - 1));
				noShipLocationA.add(Arrays.asList(userMaxRow, userMaxColumn));
				noShipLocationA.add(Arrays.asList(userMaxRow - 1,
						userMinColumn - 1));
				noShipLocationA.add(Arrays.asList(userMaxRow + 1,
						userMinColumn - 1));
				noShipLocationA.add(Arrays
						.asList(userMaxRow - 1, userMaxColumn));
				noShipLocationA.add(Arrays
						.asList(userMaxRow + 1, userMaxColumn));
			} else if (userMinColumn == userMaxColumn) {
				for (int j = userMinRow; j < userMaxRow; j++) {
					shipLocationA.add(Arrays.asList(j, userMaxColumn));
					noShipLocationA.add(Arrays.asList(j, userMaxColumn + 1));
					noShipLocationA.add(Arrays.asList(j, userMaxColumn - 1));
				}
				noShipLocationA.add(Arrays
						.asList(userMinRow - 1, userMaxColumn));
				noShipLocationA.add(Arrays
						.asList(userMaxRow + 1, userMaxColumn));
				noShipLocationA.add(Arrays.asList(userMinRow - 1,
						userMinColumn - 1));
				noShipLocationA.add(Arrays.asList(userMaxRow + 1,
						userMinColumn - 1));
				noShipLocationA.add(Arrays.asList(userMinRow - 1,
						userMinColumn + 1));
				noShipLocationA.add(Arrays.asList(userMaxRow + 1,
						userMaxColumn + 1));
			}
		} else {
			if (userMinRow == userMaxRow) {
				for (int j = userMinColumn; j < userMaxColumn; j++) {
					shipLocationB.add(Arrays.asList(userMaxRow, j));
					noShipLocationB.add(Arrays.asList(userMaxRow + 1, j));
					noShipLocationB.add(Arrays.asList(userMaxRow - 1, j));
				}
				noShipLocationB.add(Arrays
						.asList(userMaxRow, userMinColumn - 1));
				noShipLocationB.add(Arrays.asList(userMaxRow, userMaxColumn));
				noShipLocationB.add(Arrays.asList(userMaxRow - 1,
						userMinColumn - 1));
				noShipLocationB.add(Arrays.asList(userMaxRow + 1,
						userMinColumn - 1));
				noShipLocationB.add(Arrays
						.asList(userMaxRow - 1, userMaxColumn));
				noShipLocationB.add(Arrays
						.asList(userMaxRow + 1, userMaxColumn));
			} else if (userMinColumn == userMaxColumn) {
				for (int j = userMinRow; j < userMaxRow; j++) {
					shipLocationB.add(Arrays.asList(j, userMaxColumn));
					noShipLocationB.add(Arrays.asList(j, userMaxColumn + 1));
					noShipLocationB.add(Arrays.asList(j, userMaxColumn - 1));
				}
				noShipLocationB.add(Arrays
						.asList(userMinRow - 1, userMaxColumn));
				noShipLocationB.add(Arrays
						.asList(userMaxRow + 1, userMaxColumn));
				noShipLocationB.add(Arrays.asList(userMinRow - 1,
						userMinColumn - 1));
				noShipLocationB.add(Arrays.asList(userMaxRow + 1,
						userMinColumn - 1));
				noShipLocationB.add(Arrays.asList(userMinRow - 1,
						userMinColumn + 1));
				noShipLocationB.add(Arrays.asList(userMaxRow + 1,
						userMaxColumn + 1));
			}
		}

		/*
		 * for (int i = 0; i < shipLocationA.size(); i++){
		 * System.out.println("ShipRowLocation for player A: " +
		 * shipLocationA.get(i)); } for (int i = 0; i < noShipLocationA.size();
		 * i++){ System.out.println("No ShipRowLocation for player A: " +
		 * noShipLocationA.get(i)); }
		 */
	
	}
	
	public static List<List<Integer>> getShipLocationA(){
		return shipLocationA;
	} 
	
	public void setShipLocationA(List<List<Integer>> shipLocationA){
		this.shipLocationA = shipLocationA;
	} 
	
	public static List<List<Integer>> getShipLocationB(){
		return shipLocationB;
	} 
	
	public static List<List<Integer>> getNoShipLocationA(){
		return noShipLocationA;
	}
	
	public static List<List<Integer>> getNoShipLocationB(){
		return noShipLocationB;
	}
	
	public void setShipLocationB(List<List<Integer>> shipLocationB){
		this.shipLocationB = shipLocationB;
	}

	public static boolean setGameStatus() {
		// TODO Auto-generated method stub
		return gameStatus;
	} 
	
	public static boolean getGameStatus() {
		// TODO Auto-generated method stub
		return gameStatus;
	} 
	
	public static void setShipPointA(ArrayList<Integer> shipPoint) {
		// TODO Auto-generated method stub
		shipPointA = shipPoint;
	}
	
	public static void setShipPointB(ArrayList<Integer> shipPoint) {
		// TODO Auto-generated method stub
		shipPointB = shipPoint;
	}
	
	public ArrayList<Integer> getShipPointA() {
		return shipPointA;
	}
	
	public ArrayList<Integer> getShipPointB() {
		return shipPointB;
	}
	
	public static int getFLagA(){
		return playFlagA;
	}
	
	public static int getFLagB(){
		return playFlagB;
	}
	
	public static void setFLagA(int flag){
		playFlagA = flag;
	}
	
	public static void setFLagB(int flag){
		playFlagB = flag;
	}
	
	public static int getPlayerAFlag(){
		// TODO Auto-generated method stub
		return playerFlagA;
	}

	public static int getPlayerBFlag(){
		// TODO Auto-generated method stub
		return playerFlagB;
	}

	public static void setPlayerAFlag(int flag){
		// TODO Auto-generated method stub
		playerFlagA = flag;
	}

	public static void setPlayerBFlag(int flag){
		// TODO Auto-generated method stub
		playerFlagB = flag;
	}
}
