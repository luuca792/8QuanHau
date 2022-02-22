package baiToan8QuanHau;

import java.util.Scanner;

public class EightQueensSolutionMain {

	private static Scanner sc = new Scanner(System.in);
	public static boolean isSafe(int bo[][], int r, int c) {
		for (int i=0; i<8; i++)
			if (bo[i][c]==1) return false;
		for (int i=r, j=c; i>=0 && j>=0; i--, j--) {
			if (bo[i][j]==1) return false;
		}
		for (int i=r, j=c; i>=0 && j<8; i--, j++) {
			if (bo[i][j]==1) return false;
		}
		return true;
	}
	public static void print(int bo[][]) {
		for (int i=0; i<8; i++) {
			for(int j=0; j<8; j++)
				System.out.print(bo[i][j]);
			System.out.println();
		}
		System.out.println();
	}
	public static void nQueen(int bo[][], int r) {
		if (r==8) {
			print(bo);
			sc.nextLine();
			return;
		}
		for (int i=0; i<8; i++) {
			if (isSafe(bo,r,i)) {
				bo[r][i]=1;
				System.out.println(r+"-"+i);
				nQueen(bo,r+1);
				bo[r][i]=0;
			}
			
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] bo = new int[8][8];
		for (int i=0; i<8; i++)
			for (int j=0; j<8; j++)
					bo[i][j]=0;
		nQueen(bo,0);

	}

}
