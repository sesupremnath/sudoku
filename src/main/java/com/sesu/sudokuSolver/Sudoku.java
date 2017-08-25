package com.sesu.sudokuSolver;

import java.io.IOException;
import java.util.LinkedList;

public class Sudoku {
	Grid gd;
	Integer cellValue;
	public Sudoku(String inputFile) throws NumberFormatException, IOException {
		gd = new Grid(inputFile);
	}
	public void display(){
		gd.display();
	}

	public void solve(){
		//gd.displaySite();
		if(!solve(gd)){
			System.out.println("Unable to solve :(");
		}
	}

	private Boolean solve(Grid gd){
		if(gd.isSolved()){
			gd.display();
			return true;
		}
		Cell cell = gd.getCell();
		Grid gd1;
		if(cell!=null){
			//System.out.println("Cell : "+cell.i+","+cell.j);
			for(int i=0;i<cell.getAvailNumber().length();i++){
				gd1 = gd.getCopy();
				gd1.assignValue(gd.siteToString(cell.i, cell.j),String.valueOf(cell.getAvailNumber().charAt(i)));
				//gd1.display();
				if(solve(gd1)){
					return true;
				}
			}
		}
		//gd.display();
		return false;
	}
	
	public static void main(String []args) throws NumberFormatException, IOException{
		Sudoku su = new Sudoku("/Users/spr/Drive_Personal/SourceCode/GitRepo/hardest.txt");
		su.solve();
	}
}
