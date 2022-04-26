package baiToan8QuanHau;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import java.util.*;

public class BanCoBackUp implements ActionListener{
	
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
	JComboBox iconCbb;
	JButton clearButton;
	JButton quitButton;
	JButton autoSolve;
	JButton nextButton;
	JButton stopButton;
	JLabel psLabel;
	
//WELCOME PANEL
	JPanel welcomePanel = new JPanel();
//MAIN PANEl
	JPanel mainPanel = new JPanel();
	JButton startButton;
//CONTAINER PANEL
	JPanel contPanel = new JPanel();
//CARD LAYOUT
	CardLayout card = new CardLayout();
//OTHER
	
	private int timer = 10;
	private int solution=0;
	private int[][] banCo = new int[8][8];
	private boolean stop=false;
	private boolean auto_solving = false;
	private int stt=0;
	
	ImageIcon cat_queen = new ImageIcon("cat_queen.png");
	ImageIcon beluga_queen = new ImageIcon("beluga_queen.png");
	ImageIcon queen = new ImageIcon("queen.png");
	ImageIcon icon = queen;
	
	Color blue = new Color(0x4b7399), green = new Color(0x769656);
	Color brown = new Color(0xb58863);
	
	Color mauBanCo = blue; //Xanh da troi
	Color mauDanhLoi = new Color(0xdf0d0d);
	Color mauTrang = new Color(0xeeeed2);
	Color mauHoanThanh = new Color(0x329d27);
	OKhongHopLe oKhongHopLe = new OKhongHopLe(); //Luu danh sach cac o khong hop le (dung de to mau)
	
	List<OCo> oKhongHopLe2 = new ArrayList<OCo>();
	  
	List<OCo> dsQuanHau = new ArrayList<OCo>();
	int dapAn[][][] = new int[92][8][8];
	
	
	public BanCoBackUp() {
		khoiTaoBanCo(banCo);
		frame.setSize(800,680);
		try {
			getDapAn(banCo,0);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//inDapAn(dapAn);
		
		
		//CREATE NEW FONT
		Font customFont = null;
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("customFont.otf")).deriveFont(32f);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(customFont);
		//================
				
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
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		for(int i=0;i<8;i++) {
			for (int j=0;j<8;j++) {
				buttons[i][j] = new JButton();
				buttonPanel.add(buttons[i][j]);
				buttons[i][j].setFocusable(false);
				if ((i+j)%2==0) buttons[i][j].setBackground(mauTrang);
				else buttons[i][j].setBackground(mauBanCo);
				
				buttons[i][j].addActionListener(this);
				buttons[i][j].setBorder(BorderFactory.createEmptyBorder());
				
			}
		}	
		//OPTION PANEL SETTING
		optionPanel.setPreferredSize(new Dimension(150,1));
		optionPanel.setLayout(new FlowLayout(FlowLayout.LEADING,20,10));
		clearButton = new JButton("Clear");
		clearButton.setFocusable(false);
		clearButton.setBackground(mauBanCo);
		clearButton.setForeground(mauTrang);
		clearButton.setPreferredSize(new Dimension(100,30));
		clearButton.addActionListener(this);
		
		JLabel colorLabel = new JLabel("Board color:");
		String[] color = {"blue", "green"};
		colorCbb = new JComboBox(color);
		colorCbb.setBackground(mauBanCo);
		colorCbb.setForeground(mauTrang);
		colorCbb.setPreferredSize(new Dimension(100,30));
		colorCbb.addActionListener(this);
		
		JLabel iconLabel = new JLabel("Piece icon:");
		String[] icon_list = {"Queen", "Cat", "Beluga"};
		iconCbb = new JComboBox(icon_list);
		iconCbb.setBackground(mauBanCo);
		iconCbb.setForeground(mauTrang);
		iconCbb.setPreferredSize(new Dimension(100,30));
		iconCbb.addActionListener(this);
		
		
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
		quitButton.setBackground(mauDanhLoi);
		quitButton.setForeground(mauTrang);
		quitButton.setFocusable(false);
		quitButton.addActionListener(this);
		
		stopButton = new JButton("Stop");
		stopButton.setPreferredSize(new Dimension(100,30));
		stopButton.setBackground(mauBanCo);
		stopButton.setForeground(mauTrang);
		stopButton.setFocusable(false);
		stopButton.addActionListener(this);
		stopButton.setEnabled(false);
		
		psLabel = new JLabel();
		int temp_solution = checkSolution(banCo);
		psLabel.setText("Possible Solution: "+temp_solution);
		
		optionPanel.add(clearButton);
		optionPanel.add(colorLabel);
		optionPanel.add(colorCbb);
		optionPanel.add(iconLabel);
		optionPanel.add(iconCbb);
		optionPanel.add(autoSolve);
		optionPanel.add(nextButton);
		optionPanel.add(stopButton);
		optionPanel.add(quitButton);
		optionPanel.add(psLabel);
		
		
		//WELCOME PANEL SETTING
		welcomePanel.setBackground(Color.yellow);
		welcomePanel.setLayout(null);
		JLabel bg = new JLabel();
		ImageIcon background = new ImageIcon("bg.png");
		bg.setIcon(background);
		bg.setText("Hello");
		bg.setForeground(Color.white);
		bg.setBounds(0, 0, 800, 680);
		
		startButton = new JButton("START");
		startButton.setBackground(blue);
		startButton.setBorder(BorderFactory.createEmptyBorder());
		startButton.setOpaque(false);
		startButton.setFocusable(false);
		startButton.setPreferredSize(new Dimension(100,50));
		startButton.setForeground(Color.white);
		startButton.setFont(customFont);
		startButton.addActionListener(this);
		
		JPanel startOptionPanel = new JPanel();
		startOptionPanel.setBounds(0,450,800,100);
		startOptionPanel.setOpaque(false);
		startOptionPanel.add(startButton);
		bg.add(startOptionPanel);
		
		
		welcomePanel.add(bg);
		welcomePanel.setVisible(true);
		
		//MAIN PANEL SETTING
		mainPanel.setBackground(blue);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(titlePanel,BorderLayout.NORTH);
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.add(optionPanel, BorderLayout.EAST);
		
		//CONTAINER PANEL SETTING
		contPanel.setLayout(card);
		contPanel.add(welcomePanel,"1");
		contPanel.add(mainPanel, "2");
		card.show(contPanel, "1");
		
		
		//FRAME SETTING
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(contPanel);
		frame.setIconImage(queen.getImage());
		frame.setTitle("Tro Choi 8 Quan Hau");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		//frame.pack();

		
	}
	public void khoiTaoBanCo(int banCo[][]) {
		for (int i=0; i<8; i++)
			for (int j=0; j<8; j++) {
				banCo[i][j]=0;
			}
	}
	public void clearBanCo(int banCo[][]) {
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				if (banCo[i][j]==1) {
					banCo[i][j]=0;
					buttons[i][j].setIcon(null);
				}
		ToMau(banCo);
		dsQuanHau.clear();
		psLabel.setText("Possible Solution: "+checkSolution(banCo));
	}
	public boolean HopLe(int banCo[][]) {
		int sum=0;
		int i=0,j=0;
		
		//Duyet cac hang ngang
		for (i=0; i<8; i++) {
			for (j=0; j<8; j++)
				sum += banCo[i][j];
			if (sum>1) return false;
			sum=0;
		}
		//Duyet cac hang doc
		for (i=0; i<8; i++) {
			for (j=0; j<8; j++)
				sum += banCo[j][i];
			if (sum>1) return false;
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
			if (sum>1) return false;
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
			if (sum>1) return false;
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
			if (sum>1) return false;
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
	public void inDapAn(int dapAn[][][]) {
		for (int k=0; k<92; k++) {
			for (int i=0; i<8; i++) {
				for (int j=0; j<8; j++) {
					System.out.print(dapAn[k][i][j]);
				}
			System.out.println("");
			}
			System.out.println("");
		}
	}
	
	
	public void nQueen(int banCo[][], int r) {
		if (r==8) return;	
		for (int i=0; i<8; i++) {
			if (isSafe(banCo,r,i)) {
				banCo[r][i]=1;
				nQueen(banCo,r+1);
				banCo[r][i]=0;
				buttons[r][i].setIcon(null);
			}
			
		}
	}
	
	
	public void getDapAn(int banCo[][], int r) throws InterruptedException {
		if (r==8) {
			for (int i=0; i<8; i++) {
				for (int j=0; j<8; j++)
					dapAn[stt][i][j]=banCo[i][j];
			}
			stt++;
			return;	
		}
		for (int i=0; i<8; i++) {
			if (isSafe(banCo,r,i)) {
				banCo[r][i]=1;
				getDapAn(banCo,r+1);
				banCo[r][i]=0;
			}
			
		}
	}
	public int checkSolution(int banCo[][]) {
		int solution=0;
		boolean possible=true;
		for (int k=0; k<92; k++) {//Duyet qua cac dap an
			possible = true;
			//System.out.println(k);
			for (int i=0; i<dsQuanHau.size(); i++)//Duyet qua cac quan hau tren ban co
				if (dapAn[k][dsQuanHau.get(i).GetX()][dsQuanHau.get(i).GetY()]==0) possible=false;
			if (possible) solution++;
		}
		return solution;
	}
	public static void print(int bo[][]) {
		for (int i=0; i<8; i++) {
			for(int j=0; j<8; j++)
				System.out.print(bo[i][j]);
			System.out.println();
		}
		System.out.println();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==stopButton) stop=true;
		if (e.getSource()==startButton) card.show(contPanel, "2");
		if (e.getSource()==quitButton) {
			clearBanCo(banCo);
			if (auto_solving) stop=true;
			card.show(contPanel, "1");
		}
		if (e.getSource()==clearButton) clearBanCo(banCo);
		if (e.getSource()==autoSolve) {
		  SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

				@Override
				protected Void doInBackground() throws Exception {
					clearBanCo(banCo);
					autoSolve.setEnabled(false);
					clearButton.setEnabled(false);
					quitButton.setEnabled(true);
					auto_solving=true;
					stopButton.setEnabled(true);
					dsQuanHau.clear();
					iconCbb.setEnabled(false);
					psLabel.setText("Possible Solution: "+checkSolution(banCo));
					nQueen(banCo,0);
					clearButton.setEnabled(true);
					autoSolve.setEnabled(true);
					auto_solving=false;
					solution=0;
					stop=false;
					stopButton.setEnabled(false);
					titleText.setText("Tro Choi 8 Quan Hau");
					iconCbb.setEnabled(true);
					
					return null;
				}
			};
			worker.execute();
		}
		if (e.getSource()==colorCbb) {
			mauBanCo = getColor(colorCbb.getSelectedItem().toString());
			ToMau(banCo);
		}
		if (e.getSource()==iconCbb) {
			switch (iconCbb.getSelectedIndex()) {
			case 0: icon = queen; break;
			case 1: icon = cat_queen; break;
			case 2: icon = beluga_queen; break;
			//default: icon = queen;
			} 
			for (int i=0; i<dsQuanHau.size(); i++)
				buttons[dsQuanHau.get(i).GetX()][dsQuanHau.get(i).GetY()].setIcon(icon);
			System.out.println(iconCbb.getSelectedIndex());
		}
		if (!auto_solving) {
			for (int i=0; i<8; i++)
				for (int j=0; j<8; j++) {
					if (e.getSource()==buttons[i][j]) {
						if (banCo[i][j]==1) { //Day la mot thao tac huy bo co
							banCo[i][j]=0;
							buttons[i][j].setIcon(null);
							OCo x = new OCo(i,j);
							for (int k=0; k<dsQuanHau.size();k++)
								if (dsQuanHau.get(k).isEqual(x)) {
//									System.out.println("YES");
									dsQuanHau.remove(k);
								}
							psLabel.setText("Possible Solution: "+checkSolution(banCo));
							
//							print(banCo);
//							System.out.println(checkSolution(banCo));
//							for (int k=0; k<dsQuanHau.size();k++)
//								System.out.print("["+dsQuanHau.get(k).GetX()+", "+dsQuanHau.get(k).GetY()+"] ");
//							System.out.println();
						}
						else { //Day la mot thao tac danh co
							if (TongQuanHau(banCo)<8) {
								banCo[i][j]=1;
								buttons[i][j].setIcon(icon);
								dsQuanHau.add((new OCo(i,j)));
								psLabel.setText("Possible Solution: "+checkSolution(banCo));
//								print(banCo);
								//System.out.println(checkSolution(banCo));
//								for (int k=0; k<dsQuanHau.size();k++)
//									System.out.print("["+dsQuanHau.get(k).GetX()+", "+dsQuanHau.get(k).GetY()+"] ");
//								System.out.println();
//								psLabel.setText("Possible: "+psSolution);
							}
						}
					}
					ToMau(banCo);
				}
		}
	}
}
//SOLUTION: 1-7-5-8-2-4-6-3