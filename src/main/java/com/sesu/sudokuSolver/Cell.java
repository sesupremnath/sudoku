package com.sesu.sudokuSolver;

import java.util.LinkedList;
import java.util.TreeSet;

enum CellType{
	EMPTY,
	READONLY,
	FILLED
}

public class Cell implements Comparable<Cell>{
	private String availableNumbers;
	private String currentValue; 
	private CellType cellType;
	public int i,j;
	public int newHashCode;
	
	public Cell(){
		availableNumbers = "123456789";
		cellType = CellType.EMPTY;
	}
	public Cell(int i, int j){
		this();
		setLocation(i, j);
	}
	public Cell(Cell cell){
		this.availableNumbers = cell.availableNumbers;
		this.currentValue = cell.currentValue;
		this.cellType = cell.cellType;
		this.i = cell.i;
		this.j = cell.j;
		this.newHashCode = cell.newHashCode;
	}
	public void setLocation(int i, int j){
		this.i = i;
		this.j = j;
		newHashCode = 10*i+j;
	}
	 
	public void setReadOnlyCell(String value){
		cellType = CellType.READONLY;
		setCurrentValue(value);
		availableNumbers = "";
	}
	public void setCurrentValue(String value){
		currentValue = value;
		cellType = CellType.FILLED;
	}
	public String getCurrentValue() {
		return currentValue;
	}
	
	public int compareTo(Cell o){
		Integer th = this.availableNumbers.length();
		Integer oh = o.availableNumbers.length();
		return th.compareTo(oh);
	}
	
	public CellType getCellType() {
		return cellType;
	}
	public void removeAvailNumber(String value){
		availableNumbers = availableNumbers.replace(value, "");
	}
	
	public int hashCode(){
		return newHashCode;
	}
	
	public boolean equals(Cell cell) {
		return this.hashCode() == cell.hashCode();
	}
	public String getAvailNumber(){
		return availableNumbers;
	}
	
	public Cell getCopy(){
		Cell cell = new Cell();
		cell.i = this.i;
		cell.j = this.j;
		cell.availableNumbers = this.availableNumbers;
		cell.cellType = this.cellType;
		cell.currentValue = this.currentValue;
		cell.newHashCode = this.newHashCode;
		return cell;
	}
}
