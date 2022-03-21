import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Calculator extends JFrame implements  ActionListener{
	private static final long serialversionUID =1L;
	JPanel[] row=new JPanel[5];
	JButton[] button=new JButton[19];
	String[] buttonString= {"7","8","9","+",
			"4","5","6","-",
			"1","2","3","*",
			".","/","C","^2",
			"+/-","=","0"};
	int[] dimW= {430,70,150,140};
	int[] dimH= {50,60};
	Dimension displayDimension=new Dimension(dimW[0],dimH[0]);//for the display text
	Dimension regularDimension=new Dimension(dimW[1],dimH[1]);
	Dimension rColumnDimension=new Dimension(dimW[2],dimH[1]);
	Dimension ZeroButtonDimension=new Dimension(dimW[3],dimH[1]);
	boolean[] function=new boolean[4];
	double[] temporary= {0,0};
	JTextArea display= new JTextArea(2,25);//2 rows and 25 columns
	Font font =new Font("Times New Roman",Font.BOLD,20);

	Calculator(){
		super("Calculator");//name of the window we are creating
		setDesign();
		setSize(500,350);
		setResizable(false);//we cannot resize the window we are creating
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridLayout grid=new GridLayout(5,5);//5 rows 5 columns
		setLayout(grid);

		//calculator logic

		for(int i=0; i<4;i++) {
	
			function[i]=false;
		}
		

		FlowLayout f1=new FlowLayout(FlowLayout.CENTER);//center aligned
		//it's also a type of layout
		FlowLayout f2=new FlowLayout(FlowLayout.CENTER,1,1);
		//horizontal and vertical gap
		
		//initilizing Jpanel
				for(int i=0;i<5;i++) {
					row[i]=new JPanel();
				}

		//row[0]-->the display part-->center aligned and no horizontal and vvertical gap
		row[0].setLayout(f1);
		//rest rows have some gaps
		for(int i=1;i<5;i++) {
			row[i].setLayout(f2);
		}
		//setting up buttons
		for(int i=0;i<19;i++) {
			button[i]=new JButton();
			button[i].setText(buttonString[i]);
			button[i].setBackground(Color.white);
			button[i].setFont(font);
			button[i].addActionListener(this);//"this"  refers to calculator class object


		}
		//setting up textview
		display.setFont(font);
		display.setEditable(false);//cannot be edited from user side
		display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		display.setPreferredSize(displayDimension);

		//setting dimension of all buttons

		for(int i=0;i<14;i++) {
			button[i].setPreferredSize(regularDimension);
		}

		for(int i=14;i<18;i++) {
			button[i].setPreferredSize(rColumnDimension);
		}
		//zero has special dimension
		button[18].setPreferredSize(ZeroButtonDimension);
		//adding all components to jframe
		row[0].add(display);//adding TextView to JPanel
		add(row[0]);//adding JPanel to JFrame

		//adding all the button to JPanel

		//row[1]-> 7,8,9,+,C
		for(int i=0;i<4;i++) {
			row[1].add(button[i]);
		}
		row[1].add(button[14]);
		add(row[1]);//adding JPanel to JFrame

		//row2->4,5,6,-,?
		for(int i=4;i<8;i++) 
			row[2].add(button[i]);
		row[2].add(button[15]);
		add(row[2]);//adding JPanel to JFrame


		//row3->1,2,3,*,+/-
		for(int i=8;i<12;i++) 
			row[3].add(button[i]);
		row[3].add(button[16]);
		add(row[3]);//adding JPanel to JFrame


		//row4-->0,.,/,=
		row[4].add(button[18]);//for zero
		for(int i=12;i<14;i++) 
			row[4].add(button[i]);
		row[4].add(button[17]);//for "="
		add(row[4]);//adding JPanel to JFrame

		setVisible(true);//Makes JFrame visible

	}

	public void clear() {
		try {
			display.setText("");
			for(int i=0;i<4;i++) {
				function[i]=false;
			}
			for(int i=0;i<2;i++) {
				temporary[i]=0;
			}


		}catch(NullPointerException e) {

		}

	}

	public void getSqrt() {
		try {
			double value=Math.sqrt(Double.parseDouble(display.getText()));
			display.setText(Double.toString(value));
		}catch(NumberFormatException e) {

		}
	}
	public void getPosNeg() {
		try {
			Double value=Double.parseDouble(display.getText());
			if(value!=0) {
				value=value*(-1);
				display.setText(Double.toString(value));
			}
		}catch(NumberFormatException e) {

		}

	}
	
	public void getResult() {
		double result=0;
		temporary[1]=Double.parseDouble(display.getText());
		try {
			if(function[2]==true) {
				result=temporary[0]*temporary[1];
			}
			if(function[3]==true) {
					result=temporary[0]/temporary[1];
			}
			 if(function[0]==true) {
			
					result=temporary[0]+temporary[1];
			 }
			if(function[1]==true) {
			result=temporary[0]-temporary[1];
			}
			display.setText(Double.toString(result));
			for(int i=0;i<4;i++) {
				function[i]=false;
			}
				
		}catch(NumberFormatException e) {
			
		}
	}
	public void setDesign() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.numbusLookAndFeel");
			
			
		}catch(Exception e) {
			
		}
	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==button[0]) {
			display.append("7");
		}
		if(ae.getSource()==button[1]) {
			display.append("8");
		}
		if(ae.getSource()==button[2]) {
			display.append("9");
		}
		if(ae.getSource()==button[3]) {
			//funtion[0]->add
			temporary[0]=Double.parseDouble(display.getText());
			function[0]=true;
			display.setText("");
		}
		
		if(ae.getSource()==button[4]) {
			display.append("4");
		}
		if(ae.getSource()==button[5]) {
			display.append("5");
		}
		if(ae.getSource()==button[6]) {
			display.append("6");
		}
		if(ae.getSource()==button[7]) {
			//funtion[1]->"-"
			temporary[0]=Double.parseDouble(display.getText());
			function[1]=true;
			display.setText("");
		}

		if(ae.getSource()==button[8]) {
			display.append("1");
		}
		if(ae.getSource()==button[9]) {
			display.append("2");
		}
		if(ae.getSource()==button[10]) {
			display.append("3");
		}
		if(ae.getSource()==button[11]) {
			//funtion[2]->"*"
			temporary[0]=Double.parseDouble(display.getText());
			function[2]=true;
			display.setText("");
		}
		if(ae.getSource()==button[12]) {
			display.append(".");
		}
		
		if(ae.getSource()==button[13]) {
			//function[3]-->"/"
			temporary[0]=Double.parseDouble(display.getText());
			function[3]=true;
			display.setText("");
		}
		if(ae.getSource()==button[14]) {
			//clear button
		clear();
			
		}
		if(ae.getSource()==button[15]) {
			//square root button
			getSqrt();
		}
		if(ae.getSource()==button[16]) {
			//"+/-" sign
			getPosNeg();
		}
		
		if(ae.getSource()==button[17]) {
			//equal to button 
			getResult();
		}
		if(ae.getSource()==button[18]) {
			//zero button
			display.append("0");
		}
		

	}
	
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable(){
	            public void run() {
	                new Calculator();
	               // System.out.println(function[0]);
	            }
	        });
	    }





}