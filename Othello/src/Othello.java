

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Othello extends JFrame implements ActionListener {
	private static final int DRAW = 1;
	private static final int  INCOMPLETE = 2;
	private static final int INVALIDMOVE = 3;
	private static final int COMPLETE = 4;
	
	private int board[][];
	final static int player1Symbol = 1;
	final static int player2Symbol = 2;
	private int totalCount=4;
	
	private boolean player1Turn=true;
	int boardStatus=INCOMPLETE;
	
	
	private static final long serialVersionUID = 1L;
	;
	JPanel[] row=new JPanel[10];
	JButton[][] button=new JButton[8][8];
	JButton reset=new JButton();
	JLabel statusBoard;
	Dimension cellDimension=new Dimension(50,50);
	Dimension resetDimension=new Dimension(130,50);//length and height
	Font font =new Font("Times New Roman",Font.BOLD,20);
	private ImageIcon blackPiece;
	private ImageIcon whitePiece;
	
	public Othello() {
		super("Othello");
		setDesign();
		setSize(500,550);
		setResizable(false);//we cannot resize the window we are creating
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridLayout grid=new GridLayout(10,8);//10 rows 8 columns
		setLayout(grid);
		whitePiece=new ImageIcon(getClass().getResource("whitePiece.png"));
		blackPiece=new ImageIcon(getClass().getResource("blackPiece.png"));

		FlowLayout f1=new FlowLayout(FlowLayout.CENTER);//center aligned
		//it's also a type of layout
		FlowLayout f2=new FlowLayout(FlowLayout.CENTER,1,1);
		//horizontal and vertical gap

		//initilizing Jpanel
		for(int i=0;i<10;i++) {
			row[i]=new JPanel();
			row[i].setLayout(f2);
		}
		
		Color othelloBoard = new Color(34,139,34);
		
		//initializing button
		
		
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				button[i][j]=new JButton();
				//button[i][j].setText("");
				button[i][j].setBackground(othelloBoard);
				button[i][j].setPreferredSize(cellDimension);
				button[i][j].addActionListener(this);//
				row[i].add(button[i][j]);
				
			}
			add(row[i]);
		}
		statusBoard=new JLabel("player 1( black ) Turn");
		//statusBoard.setText("player 1 is black ");
		statusBoard.setVisible(true);
		row[8].add(statusBoard);
		add(row[8]);
		
		reset.setPreferredSize(resetDimension);
		reset.setText("Reset");
		reset.setBackground(Color.white);
		reset.setFont(font);
		reset.addActionListener(this);
		row[9].add(reset);
		add(row[9]);
		
		button[3][3].setIcon(blackPiece);
		button[3][4].setIcon(whitePiece);
		button[4][3].setIcon(whitePiece);
		button[4][4].setIcon(blackPiece);
		
		setVisible(true);//Makes JFrame visible
		//make othello board
		
		makeOthelloBoard();
		

		validate();



	}


	private void setDesign() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.numbusLookAndFeel");


		}catch(Exception e) {

		}

	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Othello();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton=(JButton) e.getSource();
		
		if(clickedButton==reset) {
			
			reset();
			
			return;
		}
		
		if(boardStatus!=INCOMPLETE) {
			return;
			
		}
		int x=0,y=0;
		boolean firstLopBreak=false;
		

		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(clickedButton==button[i][j]) {
					x=i;
					y=j;
					firstLopBreak=true;
					break;
				}
				
			}
			if(firstLopBreak) {
				break;
			}
		}
		
		if(player1Turn) {
			
			 boardStatus =move(player1Symbol,x,y);
			
		}
		else {
			 boardStatus =move(player2Symbol,x,y);
			
			
		}
		if(boardStatus==INVALIDMOVE) {
			statusBoard.setText("Invalid Move !! plaese try Again");
			if(player1Turn) {
				statusBoard.setText("Invalid Move !! plaese try Again Player 1 (Black)");
			}
			else {
				statusBoard.setText("Invalid Move !! plaese try Again Player 2 (White)");
				
			}
			boardStatus=INCOMPLETE;
			return;
			
		}
		if(boardStatus==COMPLETE) {
			
			int countPlayer1=0;
			int countPlayer2=0;
			for(int i=0;i<8;i++) {
				for(int j=0;j<8;j++) {
					if(board[i][j]==1) {
						countPlayer1++;
					}
					if(board[i][j]==2) {
						countPlayer2++;
					}
				}
			}
			
			if(countPlayer1>countPlayer2) {
				//
				statusBoard.setText("Player 1 Wins");
			}
			else if(countPlayer1<countPlayer2) {
				statusBoard.setText("Player 2 Wins");
			}
			else {
				statusBoard.setText("It's a Draw");
			}
			return;
		}
		
		player1Turn=!player1Turn;
		
		if(player1Turn) {
			statusBoard.setText("Player 1 (Black) Turn");
		}
		else {
			statusBoard.setText("Player 2 (White) Turn");
			
		}
		

	}
	
	private void reset() {
		
		boardStatus=INCOMPLETE;
		
		
		statusBoard.setText("Player 1(Black) Turn");
		player1Turn=true;
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				board[i][j]=0;
				button[i][j].setIcon(null);
			}
		}
		
		
		board[3][3] = player1Symbol;
		board[3][4] = player2Symbol;
		board[4][3] = player2Symbol;
		board[4][4] = player1Symbol;
		
		button[3][3].setIcon(blackPiece);
		button[3][4].setIcon(whitePiece);
		button[4][3].setIcon(whitePiece);
		button[4][4].setIcon(blackPiece);
		
		
		
		
	}


	public int move(int symbol, int x, int y){
		//check if it's within bound and also an empty cell
		if(x<0 ||x>=8 || y<0 || y>=8 || board[x][y]!=0) {
			//invalid move
			return INVALIDMOVE;
		}
		boolean valid=false;
		//int[][] a = {{-1, -1}, {-1, 0}, {0, -1}, {0, 1}, {1, 0}, {1, 1}, {-1, 1}, {1, -1}};
		int xdir[]= {-1,-1,0,0,1,1,-1,1};
		int ydir[]= {-1,0,-1,1,0,1,1,-1}; 
		for(int i=0;i<8;i++){
			//int count=0;
			int xStep=xdir[i];
			int yStep=ydir[i];
			int newX=x+xStep;
			int newY=y+yStep;
			boolean foundSymbol=false;
			if(isSafe(newX,newY)==false || board[newX][newY]==0 || board[newX][newY]==symbol) {
				//no need to go further
				continue;
			}
			//look for conversion
			while(isSafe(newX,newY) && board[newX][newY]!=0) {
				//within range
				//not empty
				//check if its opposite symbol 
				//count++;
				if(board[newX][newY]==symbol) {
					foundSymbol=true;
					break;

				}
				
				newX=newX+xStep;
				newY=newY+yStep;

			}
			if(foundSymbol==true) {
				//make the changes
				//valid play;
				valid=true;
				
				newX=x+xStep;
				newY=y+yStep;
				while(board[newX][newY]!=symbol) {
					board[newX][newY]=symbol;
					//Also change the button symbol
					if(symbol==player1Symbol) {
						button[newX][newY].setIcon(blackPiece);
						
					}
					if(symbol==player2Symbol) {
						button[newX][newY].setIcon(whitePiece);
						
					}
					
					newX=newX+xStep;
					newY=newY+yStep;
					
					//count--;
					
				}
			}



		}
		if(valid==false) {
			return INVALIDMOVE;
		}
		if(valid==true) {
			board[x][y]=symbol;
			//print();
			if(symbol==player1Symbol) {
				button[x][y].setIcon(blackPiece);
				
			}
			if(symbol==player2Symbol) {
				button[x][y].setIcon(whitePiece);
				
			}
			totalCount++;
			
			
		}
		if(totalCount<64) {
			boardStatus= INCOMPLETE;
		}
		if(totalCount==64) {
			boardStatus= COMPLETE;
		}
		return boardStatus;
		
		
	

	}
	
	private void makeOthelloBoard() {
		board = new int[8][8];
		board[3][3] = player1Symbol;
		board[3][4] = player2Symbol;
		board[4][3] = player2Symbol;
		board[4][4] = player1Symbol;
	}
	public static boolean isSafe(int x, int y) {
		return x >= 0 && y >= 0 && x < 8 && y < 8;
	}
	
	private void print() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

}
