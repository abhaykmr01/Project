package TicTacToe;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TicTacToe  extends JFrame implements  ActionListener{
	/**
	 * 
	 */
	private char board[][];
	private int boardSize=3;
	private char p1Symbol, p2Symbol;
	private int count;//count of no. of cells that are filled
	private static final char EMPTY=' ';
	public static final int PLAYER1WINS=1;
	public static final int PLAYER2WINS=2;
	public static final int DRAW = 3;
	public static final int  INCOMPLETE = 4;
	public static final int INVALIDMOVE = 5;
	private static final long serialVersionUID = 1L;
	//private Player player1,player2;
	
	//private int numPlayers;
	boolean player1Turn=true;
	int status=INCOMPLETE;
	JPanel[] row=new JPanel[5];
	JButton[] button=new JButton[9];
	JButton reset=new JButton();
	JLabel statusBoard;
	
	int[] dimW= {430,70,150,140};
	int[] dimH= {50,60};
	Dimension displayDimension=new Dimension(dimW[0],dimH[0]);//for the display text
	Dimension cellDimension=new Dimension(dimW[1],dimH[1]);

	Dimension resetDimension=new Dimension(dimW[3],dimH[1]);
	JTextArea display= new JTextArea(2,25);//2 rows and 25 columns
	Font font =new Font("Times New Roman",Font.BOLD,20);

	public TicTacToe() {
		super("TicTacToe");//name of the window we are creating
		setDesign();
		setSize(500,350);
		setResizable(false);//we cannot resize the window we are creating
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridLayout grid=new GridLayout(5,5);//5 rows 5 columns
		setLayout(grid);

		FlowLayout f1=new FlowLayout(FlowLayout.CENTER);//center aligned
		//it's also a type of layout
		FlowLayout f2=new FlowLayout(FlowLayout.CENTER,1,1);
		//horizontal and vertical gap

		//initilizing Jpanel
		for(int i=0;i<5;i++) {
			row[i]=new JPanel();
		}

		//row[0]-->the display part-->center aligned and no horizontal and vertical gap
		row[0].setLayout(f1);
		//rest rows have some gaps
		for(int i=1;i<5;i++) {
			row[i].setLayout(f2);
		}
		statusBoard=new JLabel("Winner is ");
		statusBoard.setVisible(false);
		//setting up buttons
		for(int i=0;i<9;i++) {
			button[i]=new JButton();
			button[i].setText("");
			button[i].setBackground(Color.white);
			button[i].setFont(font);
			button[i].addActionListener(this);//"this"  refers to calculator class object


		}
		//for reset button
		reset.setPreferredSize(resetDimension);
		reset.setText("Reset");
		reset.setBackground(Color.white);
		reset.setFont(font);
		reset.addActionListener(this);//"this"  refers to calculator class object
		//setting up textview
		display.setFont(font);
		display.setEditable(false);//cannot be edited from user side
		display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		display.setPreferredSize(displayDimension);
		//setting dimension of all buttons

		for(int i=0;i<9;i++) {
			button[i].setPreferredSize(cellDimension);
		}
		
		
		row[0].add(statusBoard);//adding TextView to JPanel
		add(row[0]);//adding JPanel to JFrame

		//adding all the button to JPanel

		
		
		add(row[1]);
		int countOfCell=0;
		for(int i=1;i<4;i++) {
			for(int j=0;j<3;j++) {
				row[i].add(button[countOfCell]);
				countOfCell++;
			}
			add(row[i]);
		}
		//adding reset button
		row[4].add(reset);
		add(row[4]);
		setVisible(true);//Makes JFrame visible
		
		//Create the board
			setBoard('X','O');
				


	}
	public void setBoard(char p1symbol ,char p2Symbol) {
		board=new char[boardSize][boardSize];
		for(int i=0;i<boardSize;i++) {
			for(int j=0;j<boardSize;j++) {
				board[i][j]=EMPTY;
			}
		}
		p1Symbol=p1symbol;
		p2Symbol=p2Symbol;

	}
	public int move(char symbol, int x, int y) {
		// check if move is valid or not i.e. coordinates is within range;
//		if(x<0 || x>= boardSize ||y<0 || y>=boardSize || board[x][y]!=EMPTY ) {
//			//Invalid Move
//			return INVALIDMOVE;
//		}
		//make the move
		board[x][y]=symbol;
		count++;
		//winning condition
		//row
		if(board[x][0] == board[x][1] && board[x][0] == board[x][2]) {
			//someone won
			//if symbol is p1ymbol then player1 wins otherwise its player2 wins
			
			return symbol == p1Symbol ?PLAYER1WINS : PLAYER2WINS;
		}
		//column
		if(board[0][y]==board[1][y] && board[0][y]== board[2][y]) {
			
			return symbol == p1Symbol ?PLAYER1WINS : PLAYER2WINS;

		}
		//left diagonal
		if(board[0][0]!=EMPTY && board[0][0]==board[1][1] && board[0][0]==board[2][2]) {
			return symbol == p1Symbol ?PLAYER1WINS : PLAYER2WINS;

		}
		//right diagonal

		if(board[0][2]!=EMPTY && board[0][2]==board[1][1] && board[0][2]==board[2][0]) {
			
			return symbol == p1Symbol ?PLAYER1WINS : PLAYER2WINS;

		}
		//if no one won
		//when count will becomes exactly as total number of cells 
		if(count==boardSize * boardSize) {
			return DRAW;
		}

		return INCOMPLETE;
	}
	public void reset() {
		count=0;
		for(int i=0;i<boardSize;i++) {
			for(int j=0;j<boardSize;j++) {
				board[i][j]=EMPTY;
			}
		}
		
	}



	private void setDesign() {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.numbusLookAndFeel");
			
			
		}catch(Exception e) {
			
		}
	

	}
	public void print() {
		System.out.println("------------------");
		for(int i=0;i<boardSize;i++) {
			for(int j=0;j<boardSize;j++) {
				System.out.print("| "+board[i][j]+" |");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("--------------------");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton=(JButton) e.getSource();
		if(clickedButton==reset) {
			
			reset();
			status=INCOMPLETE;
			display.setText("");
			
			statusBoard.setVisible(false);
			for(int i=0;i<9;i++) {
				button[i].setText("");;
			}
			player1Turn=true;
			return;
		}
		
		if(status!=INCOMPLETE) {
			return;
			
		}
		
		
		
		int x=0,y=0;

		//check if it's already filled
		if(clickedButton.getText()!="") {
			return;
		}
		//find the index of button
		int countOfCell=0;

		boolean firstLoopBreak=false;
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(clickedButton==button[countOfCell]) {
					//break
					x=i;
					y=j;
					firstLoopBreak=true;
					break;
					
					
				}
				
				countOfCell++;
			}
			if(firstLoopBreak) {
				break;
				
			}
			
		}
		
		if(player1Turn) {
			
			button[countOfCell].setText("X");
			status=move('X',x,y);
			//this will also tell the status of the game
			//1->p1 wins
			//2->p2 wins
			//3->draw
			//4->incomplete
			//5->invalid
			
		

	}
		else {
			button[countOfCell].setText("O");
			status=move('O',x,y);
		
			
		}
		player1Turn=!player1Turn;
		//print();
		if(status == PLAYER1WINS) {
			//System.out.println("Player 1 - "+player1.getName()+" wins");
			display.append("playerX wins");
			statusBoard.setText("playerX wins");
			statusBoard.setVisible(true);
			
		}
		if(status == PLAYER2WINS) {

			//System.out.println("Player 2 - "+player2.getName()+" wins");
			display.setText("playerO wins");
			statusBoard.setText("playerO wins");
			statusBoard.setVisible(true);
		}
		if(status==DRAW) {
			//System.out.println("It's a Draw");
			display.setText("It's adraw");
			statusBoard.setText("it's a Draw");
			statusBoard.setVisible(true);
		}
	}


	



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TicTacToe();
		


	}



}
