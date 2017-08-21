package com.sesu.sudokuSolver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Grid implements GridIf {
	private int maxValue;
	private Cell [][]grid;
	private int numberOfEmptyCells;
	private LinkedList<Cell> [] site;
	private LinkedList<Cell>emptyCellLst;
	
	public Grid(String inputFile) throws NumberFormatException, IOException{
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		maxValue = Integer.parseInt(br.readLine());
		Cell.maxValue =maxValue;
		numberOfEmptyCells = maxValue*maxValue;
		site = (LinkedList<Cell>[])new LinkedList[maxValue+1];
		buildEmptyGrid();
		buildSite();
		
		String line;
		String []tmp;
		int rowNumber, colNumber, value;
		while((line=br.readLine())!=null){
			tmp = line.split(",");
			rowNumber = Integer.parseInt(tmp[0]);
			colNumber = Integer.parseInt(tmp[1]);
			value = Integer.parseInt(tmp[2]);
			addCell(rowNumber,colNumber, value);
			numberOfEmptyCells -= 1;
		}
		buildEmptyCellLst();
	}

	void buildEmptyGrid(){
		Cell tmp;
		grid = new Cell[maxValue+1][];
		for(int i=1;i<=maxValue;i++){
			grid[i] =  new Cell[maxValue+1];
			for(int j=1;j<=maxValue;j++){
				tmp = new Cell();
				tmp.setLocation(i, j);
				grid[i][j] = tmp;
			}
		}
	}
	void addCell(int i, int j, Integer value) {
		Cell cell = grid[i][j];
		cell.setReadOnlyCell(value);
		eliminateTheValues(i, j, value);
	}

	public Integer fillCell(Cell cell) {
		Integer retValue = cell.fill();
		if(cell.getCurrentValue()!=null){
			eliminateTheValues(cell.i, cell.j, cell.getCurrentValue());
		}
		return retValue;
	}
	
	private void eliminateTheValues(int rowNumber, int colNumber, int value){
		removeValueInRow(rowNumber, value);
		removeValueInColumn(colNumber, value);
		removeValueInGrid(getSiteNumber(rowNumber, colNumber), value);
	}
	public void undoFillCell(Cell cell) {
		if(cell.getCurrentValue()!=null){
			undoRemoveValueInRow(cell.i, cell.getCurrentValue());
			undoRemoveValueInColumn(cell.j, cell.getCurrentValue());
			undoRemoveValueInGrid(getSiteNumber(cell.i, cell.j), cell.getCurrentValue());
		}
	}

	int getSiteNumber(int i, int j){
		if(j>=1&&j<=3){
			if(i>=1&&i<=3){
				return 1;
			} else if(i>=4&&i<=6){
				return 4;
			} else if(i>=7&&i<=9){
				return 7;
			}
		} else if(j>=4&&j<=6){
			if(i>=1&&i<=3){
				return 2;
			} else if(i>=4&&i<=6){
				return 5;
			} else if(i>=7&&i<=9){
				return 8;
			}
		} else if(j>=7&&j<=9){
			if(i>=1&&i<=3){
				return 3;
			} else if(i>=4&&i<=6){
				return 6;
			} else if(i>=7&&i<=9){
				return 9;
			}
		}
		return -1;
	}
	void buildSite() {
		int siteId;
		for(int i=1;i<=maxValue;i++){
			for(int j=1;j<=maxValue;j++){
				siteId = getSiteNumber(i, j);
				if(site[siteId]==null){
					site[siteId] = new LinkedList<Cell>();
				}
				site[siteId].add(grid[i][j]);
			}
		}
	}
	
	public void displaySite(){
		for(int i=1;i<=maxValue;i++){
			System.out.println("SITE: "+i);
			for(Cell cell: site[i]){
				System.out.print(cell.getCurrentValue()+" ");
			}
			System.out.println("");
		}
	}

	class Iter implements Iterable<Cell>{
		public Iterator<Cell> iterator() {			
			return null;
		}
		class LowestElementCount implements Iterator<Cell>{
			List<Cell> lst;
			public LowestElementCount() {
				lst = new LinkedList<Cell>();
				for(int i=1;i<=9;i++){
					for(int j=1;j<=9;j++){
						if(grid[i][j].getCellType().equals(CellType.EMPTY)){
							lst.add(grid[i][j]);
						}
					}
				}
				Collections.sort(lst);
			}
			public boolean hasNext() {
				return !lst.isEmpty();
			}
			public Cell next() {
				return null;
			}

			public void remove() {
				// TODO Auto-generated method stub
				
			}
		}
		
	}
	
	void buildEmptyCellLst(){
		emptyCellLst = new LinkedList<Cell>();
		for(int i=1;i<=9;i++){
			for(int j=1;j<=9;j++){
				if(grid[i][j].getCellType().equals(CellType.EMPTY)){
					emptyCellLst.add(grid[i][j]);
				}
			}
		}
		//Collections.sort(emptyCellLst);
	}

	private Cell popFromEmptyLst(){
		return emptyCellLst.pop();
	}
	
	public void pushToEmptyLst(Cell cell){
		emptyCellLst.addFirst(cell);
	}
	public void display() {
		Integer tmp;
		for(int i=1;i<=maxValue;i++){
			for(int j=1;j<=maxValue;j++){
				tmp = grid[i][j].getCurrentValue();
				if(tmp!=null)
					System.out.print(String.format("%4d", tmp));
				else
					System.out.print(String.format("%4s","_"));
			}
			System.out.println();
		}
		System.out.println("-------------");
	}

	int getNumberOfEmptyCells() {
		return numberOfEmptyCells;
	}

	public void removeValueInRow(int rowNumber, Integer value) {
		for(int j=1;j<=9;j++){
			//System.out.println("i="+rowNumber+", j="+j);
			grid[rowNumber][j].removeAvailNumber(value);
		}
	}
	public void undoRemoveValueInRow(int rowNumber, Integer value) {
		Cell cell;
		for(int j=1;j<=9;j++){
			cell = grid[rowNumber][j];
			if(j!=cell.j){
				cell.undoRemove(value);
			}
		}
	}

	public void removeValueInColumn(int colNumber, Integer value) {
		for(int i=1;i<=9;i++){
			grid[i][colNumber].removeAvailNumber(value);
		}
	}
	public void undoRemoveValueInColumn(int colNumber, Integer value) {
		Cell cell;
		for(int i=1;i<=9;i++){
			cell = grid[i][colNumber];
			cell.undoRemove(value);
		}
	}

	public void removeValueInGrid(int gridId, Integer value) {
		for(Cell cell:site[gridId]){
			cell.removeAvailNumber(value);
		}
	}
	public void undoRemoveValueInGrid(int gridId, Integer value) {
		for(Cell cell:site[gridId]){
			cell.undoRemove(value);
		}
	}

	public Cell getNextCell() {
		return popFromEmptyLst();
	}
	public Boolean isSolved(){
		if(emptyCellLst.size()==0)
			return true;
		else
			return false;
	}
	
}