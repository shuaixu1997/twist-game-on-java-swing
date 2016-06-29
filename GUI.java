package code;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class GUI {
	private Model _game;
	private JPanel invPanel,guePanel,topPanel,top2,top3,supertop,suboftop;
	//private JTextField _finaltext;
	private JButton clear,_bangbang,_bangbang2;
	private JTextField _bingofield,_scoreboard,_newboard;
	private JButton[] inv,gue,refer;
	private String _trueword;
	private HashSet<String> temphash;
	private int flag =0,lefttime= 60, score= 0,superflag=0;
	private Timer timebegin;
	public GUI(){
		String filename = "CROSSWD.TXT";
		_game = new Model(filename);
		inv = new JButton[7];
		gue = new JButton[7];
		refer = new JButton[100];
		_bingofield = new JTextField();
		_bangbang = new JButton();
		_bangbang2 = new JButton("Resign:(");
		_scoreboard = new JTextField();
		_newboard = new JTextField();
		suboftop = new JPanel();
		//refer
		
		
		//window
		JFrame window = new JFrame("twist game");
		window.setLayout(new GridLayout(6,1));
		window.setBounds(50, 100, 500, 960);

		//supertop
		supertop = new JPanel();
		supertop.setBackground(Color.cyan);
		supertop.setLayout(new GridLayout(3,1));
		window.add(supertop);
		_bingofield.setBounds(20, 20, 50, 50);
		_bingofield.setBackground(Color.cyan);
		_scoreboard.setBackground(Color.white);
		_newboard.setBackground(Color.white);
		_bangbang2.setBackground(Color.lightGray);
		_bingofield.setLayout(new GridLayout(1,3));
		_bangbang.setBackground(Color.ORANGE);
		_bangbang.setText("Click Here to Challenge the Time Mode !!");
		supertop.add(_bangbang);
		supertop.add(_bingofield);
		supertop.add(suboftop);
		suboftop.setLayout(new GridLayout(1,3));
		suboftop.add(_newboard);
		suboftop.add(_scoreboard);
		suboftop.add(_bangbang2);
		_bingofield.setFont(new Font("Courier",Font.BOLD,24));
		_scoreboard.setFont(new Font("Courier",Font.BOLD,24));
		_newboard.setFont(new Font("Courier",Font.BOLD,24));
		
		//bangbang2
		_bangbang2.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {
			boolean flag1 =false;

				if (! refer[0].getText().equals("")){
					flag1=true;
				}

			//System.out.println(flag1);
			if (flag1 ==true){
				for (int k=0;k<temphash.size();k++){
					refer[k].setBackground(Color.WHITE);
				}
				cleanwords();
				_bingofield.setText("CLICK 'NEW GAME' TO RESTART");
				topPanel.repaint();
				top2.repaint();
				top3.repaint();
				if (superflag==1){
					timebegin.stop();
					superflag=0;
				}
			}
			
		}});
		//bangbang
		
		ActionListener aa = new ActionListener() { @Override public void actionPerformed(ActionEvent e) {
			
			score= flag;
			_scoreboard.setText("SCORE:"+score);
			_newboard.setText("TIME:"+lefttime);
			lefttime--;
			if (lefttime<0){
				_bingofield.setText("GAME OVER! YOUR SCORE:"+score);
				cleanwords();
				for (int k=0;k<temphash.size();k++){
					refer[k].setBackground(Color.WHITE);
				}
				topPanel.repaint();
				top2.repaint();
				top3.repaint();
				if (superflag==1){
					timebegin.stop();
					superflag=0;
				}
				
			}
		}};

		_bangbang.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {
			
			if (superflag==1){
				timebegin.stop();
				superflag=0;
			}
			cleanwords();
			findwords();
			makethemwhite();
			comeon();
			ArrayList<Character> temparray = new ArrayList<Character>();
			temparray = _game.getList(_trueword);
			showwords(temparray);
			//topPanel.remove(_finaltext);
			
			timebegin = new Timer(1000, aa);
			timebegin.start();
			superflag=1;
			//System.out.println(_game.getList());
		} });
		
		
		//top
		topPanel = new JPanel();
		topPanel.setBackground(Color.GREEN);
		topPanel.setSize(100, 100);
		window.add(topPanel);
		top2 = new JPanel();
		top2.setBackground(Color.GREEN);
		top3 = new JPanel();
		top3.setBackground(Color.GREEN);
		window.add(top2);
		window.add(top3);
		topPanel.setLayout(new GridLayout(6,5));
		top2.setLayout(new GridLayout(6,5));
		top3.setLayout(new GridLayout(6,5));
		for (int i=0;i<30;i++){
			refer[i]=new JButton();
			setButtonProperties(refer[i]);
			
			topPanel.add(refer[i]);
		}
		for (int i=30;i<60;i++){
			refer[i]=new JButton();
			setButtonProperties(refer[i]);
			
			top2.add(refer[i]);
		}
		for (int i=60;i<90;i++){
			refer[i]=new JButton();
			setButtonProperties(refer[i]);
			
			top3.add(refer[i]);
		}
		
		
		//topPanel.add(_finaltext);
		
		
		
		//mid
		JPanel midPanel = new JPanel();
		//midPanel.setBackground(Color.YELLOW);
		midPanel.setLayout(new GridLayout (1,2,20,20));
		midPanel.setSize(100, 100);
		midPanel.setBackground(Color.RED);
		window.add(midPanel);
		
		//base
		JPanel basePanel = new JPanel();
		basePanel.setBackground(Color.BLUE);
		basePanel.setLayout(new GridLayout (1,3));
		basePanel.setSize(100, 100);
		window.add(basePanel);
		
		//inventory:invPanel
		invPanel = new JPanel();
		invPanel.setSize(40, 40);
		invPanel.setBackground(Color.YELLOW);
		invPanel.setLayout(new GridLayout(2,4,8,10));
		midPanel.add(invPanel);

		//guess:guePanel
		guePanel = new JPanel();
		guePanel.setSize(40, 40);
		guePanel.setBackground(Color.BLUE);
		guePanel.setLayout(new GridLayout(2,4,8,10));
		midPanel.add(guePanel);
		
		
		updateinv("");
		
		//newgame_button
		
		JButton newgame = new JButton("NewGame");
		newgame.setSize(40,40);
		basePanel.add(newgame);
		newgame.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {
			if (superflag==1){
				timebegin.stop();
				superflag=0;
			}
			cleanwords();
			findwords();
			makethemwhite();
			comeon();
			ArrayList<Character> temparray = new ArrayList<Character>();
			temparray = _game.getList(_trueword);
			showwords(temparray);
			//topPanel.remove(_finaltext);
			
			

			//System.out.println(_game.getList());
		} });
		//shuffle_button
		JButton shuffle = new JButton("Shuffle");
		shuffle.setSize(40, 40);
		basePanel.add(shuffle);
		shuffle.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {
			
			letusshuffle();
			
		} });
		
		//clear_button
		clear = new JButton("Clear");
		clear.setSize(40, 40);
		basePanel.add(clear);
		clear.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {
			
			cleanwords();
			
		} });
		
		
		//submit_button
		JButton submit = new JButton("Submit");
		submit.setSize(40, 40);
		basePanel.add(submit);
		submit.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {
			
			finalwords();
			cleanwords();
			
		} });
		
		
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window.pack();
	
		/*
		
		
		this is a large amount copy-codes for inventory/guess ActionListener
		
		
		*/
		inv[1].addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {setgue(inv[1].getText());} });
		inv[2].addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {setgue(inv[2].getText());} });
		inv[3].addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {setgue(inv[3].getText());} });
		inv[4].addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {setgue(inv[4].getText());} });
		inv[5].addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {setgue(inv[5].getText());} });
		inv[6].addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {setgue(inv[6].getText());} });
		inv[0].addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {setgue(inv[0].getText());} });
		gue[1].addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {setbackgue(gue[1].getText());} });
		gue[2].addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {setbackgue(gue[2].getText());} });
		gue[3].addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {setbackgue(gue[3].getText());} });
		gue[4].addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {setbackgue(gue[4].getText());} });
		gue[5].addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {setbackgue(gue[5].getText());} });
		gue[6].addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {setbackgue(gue[6].getText());} });
		gue[0].addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {setbackgue(gue[0].getText());} });
	
	}
	public void updateinv(String s){
		
		for (int i=0;i<7;i++){
			inv[i]= new JButton();
			setButtonPropertiesoriginal(inv[i]);
			invPanel.add(inv[i]);
			gue[i]= new JButton();
			setButtonPropertiesoriginal(gue[i]);
			guePanel.add(gue[i]);
		}
	}
	
		//System.out.println("111");
		//inv[2].addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {
		//gue[2].setText(inv[2].getText());} }
	
	
	public void showwords(ArrayList<Character> list){
		
		for (int i=0;i<7;i++){
			inv[i].setText(list.get(i).toString());
			
		}
		letusshuffle();
	}
	public void setButtonPropertiesoriginal(JButton button){
		button.setFont(new Font("Courier",Font.BOLD,44));
		button.setBackground((Color.WHITE));
		button.setForeground((Color.BLACK));
		button.setOpaque(true);
		button.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.DARK_GRAY,Color.LIGHT_GRAY));
		
	}
	public void setButtonProperties(JButton button){
		//button.setFont(new Font("Courier",Font.BOLD,44));
		button.setBackground((Color.GREEN));
		button.setForeground((Color.BLACK));
		button.setOpaque(true);
		button.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.DARK_GRAY,Color.LIGHT_GRAY));
		
	}
	public void setButtonProperties2(JButton button){
		//button.setFont(new Font("Courier",Font.BOLD,44));
		button.setBackground((Color.BLACK));
		button.setForeground((Color.BLACK));
		button.setOpaque(true);
		button.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.DARK_GRAY,Color.LIGHT_GRAY));
		
	}
	public void cleanwords(){
		for (int i=0;i<7;i++){
			if (!(gue[i].getText()=="")){
				for (int j=0;j<7;j++){
					if (inv[j].getText()==""){
						inv[j].setText(gue[i].getText());
						break;
					}
				}
				gue[i].setText("");
			}
			
		}
	}
	public void letusshuffle(){
		ArrayList<String> tempset = new ArrayList<String>();
		for (int i=0;i<7;i++){
			tempset.add(inv[i].getText());
		}
		Collections.shuffle(tempset);
		for (int i=0;i<7;i++){
			String s = tempset.get(i);
			inv[i].setText(s);
		}
	}
	public void setgue(String s){
		int i=0;
		while (!(gue[i].getText() == "")){
			i++;
		}
		gue[i].setText(s);
		for (int j=0;j<7;j++){
			if (inv[j].getText() == s){
				inv[j].setText("");
				break;
			}
		}
			
	}
	public void finalwords(){
		String s ="";
		int temp=flag;
		//boolean find = false;
		int i=0;
		while (i<7 && !(gue[i].getText() == "")){
			s=s+ gue[i].getText();
			i++;
		}
		//System.out.println(s);
		for (int j=0;j<temphash.size();j++){
			if (s.equals(refer[j].getText())&& refer[j].getBackground().equals(Color.BLACK)){
				flag++;
				_bingofield.setText("BINGO!!    "+flag+"/"+temphash.size() +" FOUND");
				refer[j].setBackground(Color.WHITE);
				topPanel.repaint();
				top2.repaint();
				top3.repaint();
			}
		}
		if (flag == temp){
				_bingofield.setText("PLEASE TRY AGAIN:( ");
			}else temp=flag;
		if (flag == temphash.size()){
			_bingofield.setText("YOU WIN !!!! ");
			cleanwords();
		}
		//System.out.println(s);
		//topPanel.add(_finaltext);
		//_finaltext.setText(s);
	}
	public void setbackgue(String s){
		int i=0;
		if (!(s =="")) {
			while (!(inv[i].getText() == "")){
				i++;
			}
			inv[i].setText(s);
			for (int j=0;j<7;j++){
				if (gue[j].getText()==s){
					gue[j].setText("");
					break;
				}
			}
			for (int k=0;k<6;k++){
				if (gue[k].getText()==""){
					for (int l=k;l<6;l++){
						gue[l].setText(gue[l+1].getText());
					}
				}
			}
		}
		
	
	
	}
	public void findwords(){
		int flag=100;
		while (flag>90){
			_trueword = _game.gettheword();
			temphash = _game.possibleWords(_trueword);
			flag=temphash.size();
		}
		
			
		
		Iterator<String> theIterator = temphash.iterator();
		int j =0;
		for (int i=0;i<90;i++){
			refer[i].setText("");
		}
		while (theIterator.hasNext()){
			refer[j].setText(theIterator.next());
			j++;
		}
		//System.out.println(_trueword);
		//System.out.println(temphash.size());

	}
	
	public void comeon(){
		

		
		if (temphash.size()<30) {
			for (int i=0;i<temphash.size();i++){
				setButtonProperties2(refer[i]);
			}
			
			
		}else if (temphash.size()<60){
			for (int i=0;i<temphash.size();i++){
				setButtonProperties2(refer[i]);
			}

			
		}else if (temphash.size()<90){
			for (int i=0;i<temphash.size();i++){
				setButtonProperties2(refer[i]);
			}
		}else if (temphash.size() >=90){
			for (int i=0;i<90;i++){
				setButtonProperties2(refer[i]);
			}
		}
		topPanel.repaint();
		top2.repaint();
		top3.repaint();
	}
	public void makethemwhite(){
		
		_bingofield.setText("");
		_newboard.setText("");
		_scoreboard.setText("");
		flag= 0;
		lefttime =60;
		for (int i=0;i<30;i++){
			setButtonProperties(refer[i]);
		}
			

		for (int i=30;i<60;i++){
			setButtonProperties(refer[i]);
		}

		for (int i=60;i<90;i++){
			setButtonProperties(refer[i]);
		}
		topPanel.repaint();
		top2.repaint();
		top3.repaint();
	}
}
