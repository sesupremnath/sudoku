package com.sesu.sudokuSolver;

import java.io.IOException;
import java.util.LinkedList;

public class Sudoku {
	Grid gd;
	Integer cellValue;
	LinkedList<Cell> tmp = new LinkedList<Cell>(); 
	public Sudoku(String inputFile) throws NumberFormatException, IOException {
		gd = new Grid(inputFile);
	}
	public void solve(){
		//gd.displaySite();
		if(solve(gd,0)){
			gd.display();
		} else {
			System.out.println("Unable to solve :(");
		}
	}
	private Boolean solve(Grid gd, int index){
		if(gd.isSolved())
			return true;
		Cell currentCell = gd.getCell(index);
		for(Integer num: currentCell.getAvailableNumbers()){
			currentCell.setCurrentValue(num);
			tmp.clear();
			tmp.addAll(gd.eliminateTheValues(currentCell));
			gd.display();
			if(solve(gd,index+1)){
				return true;
			} else {
				gd.undoElimination(currentCell, tmp);
			}
		}
		currentCell.resetCell();
		return false;
	}
	public static void main(String []args) throws NumberFormatException, IOException{
		Sudoku su = new Sudoku("/Users/spr/Drive_Personal/SourceCode/GitRepo/input.txt");
		su.solve();
	}
}
