package com.sesu.sudokuSolver;

import java.io.IOException;

public class Sudoku {
	Grid gd;
	Integer cellValue;
	public Sudoku(String inputFile) throws NumberFormatException, IOException {
		gd = new Grid(inputFile);
	}
	public void solve(){
		//gd.displaySite();
		if(solve(gd)){
			gd.display();
		} else {
			System.out.println("Unable to solve :(");
		}
	}
	private Boolean solve(Grid gd){
		if(gd.isSolved())
			return true;
		Cell currentCell = gd.getNextCell();
		while(currentCell!=null && !currentCell.isAvailNumberExhausted()){
			gd.fillCell(currentCell);
			gd.display();
			if(solve(gd)){
				return true;
			}
			gd.undoFillCell(currentCell);
		}
		if(currentCell.getCurrentValue()!=null){
			gd.display();
		}
		if(currentCell.isAvailNumberExhausted()){
			currentCell.resetAvailLst();
		}
		gd.pushToEmptyLst(currentCell);
		return false;
	}
}
