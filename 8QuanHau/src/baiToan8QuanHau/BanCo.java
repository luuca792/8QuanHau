package baiToan8QuanHau;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

public class BanCo implements ActionListener{
	
	JFrame frame = new JFrame();
	Scanner sc = new Scanner(System.in);
//TITLE PANEL
	JPanel titlePanel = new JPanel();
	JLabel titleText;
//BUTTON PANEL
	JPanel buttonPanel = new JPanel();
	JButton[][] buttons = new JButton[8][8];
//OPTION PANEL
	JPanel optionPanel = new JPanel();
	JComboBox colorCbb;
	JButton clearButton;
	JButton quitButton;
//OTHER
	int timer = 5;
	
	int[][] banCo = new int[8][8];	
	ImageIcon queen = new ImageIcon("queen.png");
	
	Color blue = new Color(0x4b7399), green = new Color(0x769656); 
	
	Color mauBanCo = blue; //Xanh da troi
	Color mauDanhLoi = new Color(0xdf0d0d);
	Color mauTrang = new Color(0xeeeed2);
	Color mauHoanThanh = new Color(0x329d27);
	OKhongHopLe oKhongHopLe = new OKhongHopLe();
	
	JButton autoSolve, nextButton;

	public BanCo() {
		for (int i=0; i<8; i++)
			for (int j=0; j<8; j++) {
				banCo[i][j]=0;
			}
		frame.setSize(800,680);
		
		
		
		//TITLE PANEL SETTING
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setBackground(Color.blue);
		titlePanel.setPreferredSize(new Dimension(1,30));
		
		titleText = new JLabel();
		titleText.setBackground(new Color(0xbebdb9));
		titleText.setForeground(Color.black);
		titleText.setFont(new Font("Arial",Font.BOLD,15));
		titleText.setHorizontalAlignment(JLabel.CENTER);
		titleText.setText("Tro Choi 8 Quan Hau");
		titleText.setOpaque(true);
		
		titlePanel.add(titleText);
		//BUTTON PANEL SETTING
		buttonPanel.setLayout(new GridLayout(8,8));
		for(int i=0;i<8;i++) {
			for (int j=0;j<8;j++) {
				buttons[i][j] = new JButton();
				buttonPanel.add(buttons[i][j]);
				buttons[i][j].setFocusable(false);
				if ((i+j)%2==0) buttons[i][j].setBackground(mauTrang);
				else buttons[i][j].setBackground(mauBanCo);
				
				buttons[i][j].addActionListener(this);
				
			}
		}	
		//OPTION PANEL SETTING
		optionPanel.setPreferredSize(new Dimension(150,1));
		optionPanel.setLayout(new FlowLayout(FlowLayout.LEADING,20,30));
		clearButton = new JButton("Clear");
		clearButton.setFocusable(false);
		clearButton.setBackground(mauBanCo);
		clearButton.setForeground(mauTrang);
		clearButton.setPreferredSize(new Dimension(100,30));
		clearButton.addActionListener(this);
		
		String[] color = {"blue", "green"};
		colorCbb = new JComboBox(color);
		colorCbb.setBackground(mauBanCo);
		colorCbb.setForeground(mauTrang);
		colorCbb.setPreferredSize(new Dimension(100,30));
		colorCbb.addActionListener(this);
		
		autoSolve = new JButton("Auto Solve");
		autoSolve.setPreferredSize(new Dimension(100,30));
		autoSolve.setBackground(mauBanCo);
		autoSolve.setForeground(mauTrang);
		autoSolve.setFocusable(false);
		autoSolve.addActionListener(this);
		
		nextButton = new JButton("Next");
		nextButton.setPreferredSize(new Dimension(100,30));
		nextButton.setBackground(mauBanCo);
		nextButton.setForeground(mauTrang);
		nextButton.setFocusable(false);
		nextButton.setEnabled(false);
		
		quitButton = new JButton("Quit");
		quitButton.setPreferredSize(new Dimension(100,30));
		quitButton.setBackground(mauBanCo);
		quitButton.setForeground(mauTrang);
		quitButton.setFocusable(false);
		quitButton.setEnabled(false);
		
		optionPanel.add(clearButton);
		optionPanel.add(colorCbb);
		optionPanel.add(autoSolve);
		optionPanel.add(nextButton);
		//optionPanel.add(quitButton);
		//FRAME SETTING
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(titlePanel,BorderLayout.NORTH);
		frame.add(buttonPanel, BorderLayout.CENTER);
		frame.add(optionPanel, BorderLayout.EAST);
		frame.setTitle("Tro Choi 8 Quan Hau");
		frame.setResizable(false);
		frame.setVisible(true);
		//frame.pack();
		
	}
	public void clearBanCo(int banCo[][]) {
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				if (banCo[i][j]==1) {
					banCo[i][j]=0;
					buttons[i][j].setIcon(null);
				}
		ToMau(banCo);
	}
	public boolean HopLe(int banCo[][]) {
		int sum=0;
		int i=0,j=0;
		
		//Duyet cac hang ngang
		for (i=0; i<8; i++) {
			for (j=0; j<8; j++)
				sum += banCo[i][j];
			if (sum>1) {
				//System.out.println("Sai hang ngang");
				return false;
			}
			sum=0;
		}
		//Duyet cac hang doc
		for (i=0; i<8; i++) {
			for (j=0; j<8; j++)
				sum += banCo[j][i];
			if (sum>1) {
				//System.out.println("Sai hang doc");
				return false;
			}
			sum=0;
		}
		
		//Duyet duong cheo chinh
		for (int k=0; k<8; k++) {
			i = k;
			j = 7;
			while (i >= 0) { 
				sum += banCo[i][j];
				i--;
				j--;
			}
			if (sum>1) {
				//System.out.println("Sai cheo trai sang");
				return false;
			}
			sum=0;
		}
		for (int k=6; k>=0; k--) {
			i=7;
			j=k;
			while (j >= 0 ) {
				sum += banCo[i][j];
				i--;
				j--;
			}
			if (sum>1) {
				//System.out.println("Sai cheo trai sang");
				return false;
			}
			sum=0;
		}
		//==========================
		//Duyet duong cheo phu
		for (int k=0; k<8; k++) {
			i = k;
			j = 0;
			while (i >= 0) {
				sum += banCo[i][j];
				i--;
				j++;
			}
			if (sum>1) {
				System.out.println("Sai cheo phai sang");
				return false;
			}
			sum=0;
		}
		
		sum=0;
		for (int k=1; k<8; k++) {
			i=7;
			j=k;
			while (j <= 7) {
				sum += banCo[i][j];
				i--;
				j++;
			}
			if (sum>1) {
				System.out.println("Sai cheo phai sang");
				return false;
			}
			sum=0;
		}
		//==========================
		return true;
	}
	public int TongQuanHau(int banCo[][]) {
		int sum=0;
		for (int i=0; i<8; i++)
			for (int j=0; j<8; j++) {
				if (banCo[i][j]==1) sum++;
			}
		return sum;
	}	
	public void InMaTran(int banCo[][]) {
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++)
				System.out.print(banCo[i][j]+"");
			System.out.println("");
		}
		System.out.println("");
	}
	public void ToMau(int banCo[][]) {
		int sum=0;
		//To mau hang ngang
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++)
				sum += banCo[i][j];
			if (sum>1) {
				for (int k=0; k<8; k++)
					if (banCo[i][k]==1) {
						if (!oKhongHopLe.inIt(i, k)) {
							oKhongHopLe.add(i, k);
						}
					}
			}
			sum=0;
		}
		//To mau hang doc
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++)
				sum += banCo[j][i];
			if (sum>1) {
				for (int k=0; k<8; k++) {
					if (banCo[k][i]==1)
						if (!oKhongHopLe.inIt(k, i))
							oKhongHopLe.add(k, i);
				}
			}
			sum=0;
		}
		//To mau duong cheo phu
		for (int k=0; k<8; k++) {
			int i = k;
			int j = 0;
			while (i >= 0) {
				sum += banCo[i][j];
				i--; j++;
			}
			i++; //Cong du
			j--; //Cong du
			if (sum>1) {
				while (i <= k) {
					if (banCo[i][j]==1) {
						if (!oKhongHopLe.inIt(i, j)) oKhongHopLe.add(i, j);
					}
					i++; j--;
				}
			}
			sum=0;
		}
		for (int k=1; k<8; k++) {
			int i=7;
			int j=k;
			while (j <= 7) {
				sum += banCo[i][j];
				i--; j++;
			}
			i++; //Cong du
			j--; //Cong du
			if (sum>1) {
				while (j>=k) {
					if (banCo[i][j]==1){
						if (!oKhongHopLe.inIt(i, j)) oKhongHopLe.add(i, j);
					}
					i++; j--;
				}
			}
			sum=0;
		}
		//To mau duong cheo chinh
		for (int k=0; k<8; k++) {
			int i = k;
			int j = 7;
			while (i >= 0) { 
				sum += banCo[i][j];
				i--; j--;
			}
			i++; j++;
			if (sum>1) {
				while (i <=k ) {
					if (banCo[i][j]==1) {
						if (!oKhongHopLe.inIt(i, j)) oKhongHopLe.add(i, j);
					}
					i++; j++;
				}
			}
			sum=0;
		}
		for (int k=6; k>=0; k--) {
			int i=7;
			int j=k;
			while (j >= 0 ) {
				sum += banCo[i][j];
				i--; j--;
			}
			i++; j++;
			if (sum>1) {
				while (j <=k ) {
					if (banCo[i][j]==1) {
						if (!oKhongHopLe.inIt(i, j)) oKhongHopLe.add(i, j);	
					}
					i++; j++;
				}
			}
			sum=0;
		}
		//Xoa mau tat ca cac o
		for (int i=0; i<8; i++)
			for(int j=0; j<8; j++) {
				if ((i+j)%2==0) buttons[i][j].setBackground(mauTrang);
				else buttons[i][j].setBackground(mauBanCo);
			}
		if (oKhongHopLe.getTop()==0 && TongQuanHau(banCo)==8) {
			for (int i=0; i<8; i++)
				for(int j=0; j<8; j++)
					if (banCo[i][j]==1) buttons[i][j].setBackground(mauHoanThanh);
		}
		else {
			for (int i=0; i<oKhongHopLe.getTop(); i++) {
//				System.out.println("YES");
				int x = oKhongHopLe.getOCo(i).GetX();
				int y = oKhongHopLe.getOCo(i).GetY();
				if ( OHopLe(banCo, x, y) == true) {
					if ((x+y)%2==0) buttons[x][y].setBackground(mauTrang);
					else buttons[x][y].setBackground(mauBanCo);
					oKhongHopLe.remove(x, y);
					i--;
				}
				else 
					buttons[x][y].setBackground(mauDanhLoi);
			}	
		}
	}
	public boolean OHopLe(int banCo[][], int x, int y) {
		if (banCo[x][y]==0) return true;
		//Ngang
		int sum=0;
		for (int j=0; j<8; j++)
			sum += banCo[x][j];
		if (sum>1) return false;
		
		//Doc
		sum=0;
		for (int i=0; i<8; i++)
			sum += banCo[i][y];
		if (sum>1) return false;
		
		
		//Cheo phu
		sum=0;
		int i=x, j=y;
		while (i>=0 && j<=7) {
			sum += banCo[i][j];
			i--;
			j++;
		}
		i=x+1; j=y-1;
		while ( j>=0 && i<=7 ) {
			sum += banCo[i][j];
			i++;
			j--;
		}
		if (sum>1) {
			return false;
		}
		//Cheo chinh
		sum=0;
		i=x; j=y;
		while ( i>=0 && j>=0 ) {
			sum += banCo[i][j];
			i--;
			j--;
		}
		i=x+1; j=y+1;
		while ( i<=7 && j<=7 ) {
			sum += banCo[i][j];
			i++;
			j++;
		}
		if (sum>1) {
			return false;
		}
		
		return true;
	}
	public Color getColor(String name) {
		switch (name) {
		case "green": return green;
		case "blue" : return blue;
		default: return blue;
		}
	}
	public static boolean isSafe(int banCo[][], int r, int c) {
		for (int i=0; i<8; i++)
			if (banCo[i][c]==1) return false;
		for (int i=r, j=c; i>=0 && j>=0; i--, j--) {
			if (banCo[i][j]==1) return false;
		}
		for (int i=r, j=c; i>=0 && j<8; i--, j++) {
			if (banCo[i][j]==1) return false;
		}
		return true;
	}
	
	private int solution=0;
	public void nQueen(int banCo[][], int r) throws InterruptedException {
		if (r==8) {
//			print(banCo);
			for (int i=0; i<8; i++)
				for (int j=0; j<8; j++)
					if (banCo[i][j]==1) buttons[i][j].setBackground(mauHoanThanh);
			solution++;
			titleText.setText("Solution: "+solution+"");
//			Thread.sleep(100);
			nextButton.setEnabled(true);
			while (!nextButton.getModel().isPressed()) {
				Thread.sleep(1);
			}
			
			return;
			
		}
		for (int i=0; i<8; i++) {
			if (isSafe(banCo,r,i)) {
				banCo[r][i]=1;
				buttons[r][i].setIcon(queen);
				Thread.sleep(timer);
								
				nQueen(banCo,r+1);
				nextButton.setEnabled(false);
				for (int i1=0; i1<8; i1++)
					for (int j=0; j<8; j++)
						if (banCo[i1][j]==1) {
							if ((i1+j)%2==0) buttons[i1][j].setBackground(mauTrang);
							else buttons[i1][j].setBackground(mauBanCo);
						}
				banCo[r][i]=0;
				buttons[r][i].setIcon(null);
				Thread.sleep(timer);
			}
			
		}
	}
	public static void print(int bo[][]) {
		for (int i=0; i<8; i++) {
			for(int j=0; j<8; j++)
				System.out.print(bo[i][j]);
			System.out.println();
		}
		System.out.println();
	}
	boolean auto_solving = false;
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==clearButton) {
			clearBanCo(banCo);
		}
		if (e.getSource()==autoSolve) {
		  SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

				@Override
				protected Void doInBackground() throws Exception {
					clearBanCo(banCo);
					autoSolve.setEnabled(false);
					clearButton.setEnabled(false);
					quitButton.setEnabled(true);
					auto_solving=true;
					nQueen(banCo,0);
					clearButton.setEnabled(true);
					autoSolve.setEnabled(true);
					auto_solving=false;
					solution=0;
					return null;
				}
			};
			worker.execute();
		}
		if (e.getSource()==colorCbb) {
			mauBanCo = getColor(colorCbb.getSelectedItem().toString());
			ToMau(banCo);
		}
		for (int i=0; i<8; i++)
			for (int j=0; j<8; j++) {
				if (auto_solving==false && e.getSource()==buttons[i][j]) {
					if (banCo[i][j]==1) { //Day la mot thao tac huy bo co
						banCo[i][j]=0;
						buttons[i][j].setIcon(null);
					}
					else { //Day la mot thao tac danh co
						if (TongQuanHau(banCo)<8) {
							banCo[i][j]=1;
							buttons[i][j].setIcon(queen);		
						}
					}
				}
				ToMau(banCo);
//				oKhongHopLe.print();
		
			}
	}
}
//SOLUTION: 1-7-5-8-2-4-6-3