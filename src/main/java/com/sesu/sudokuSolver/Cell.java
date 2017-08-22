package com.sesu.sudokuSolver;

import java.util.LinkedList;
import java.util.TreeSet;

enum CellType{
	EMPTY,
	READONLY,
	FILLED
}

public class Cell implements Comparable<Cell>{
	public static int maxValue;
	private TreeSet<Integer> availableNumbers;
	private TreeSet<Integer>exclusionLst;
	private Integer currentValue; 
	private CellType cellType;
	public int i,j;
	public int newHashCode;
	
	public Cell(){
		availableNumbers = new TreeSet<Integer>();
		for(int i=1;i<=maxValue;i++){
			availableNumbers.add(i);
		}
		exclusionLst = new TreeSet<Integer>();
		cellType = CellType.EMPTY;
	}
	
	public void setLocation(int i, int j){
		this.i = i;
		this.j = j;
		newHashCode = 10*i+j;
	}
	 
	public void setReadOnlyCell(Integer value){
		cellType = CellType.READONLY;
		setCurrentValue(value);
		availableNumbers.clear();
	}
	public void setCurrentValue(Integer value){
		currentValue = value;
	}
	public Integer getCurrentValue() {
		return currentValue;
	}
	
	public int compareTo(Cell o){
		Integer th = this.availableNumbers.size();
		Integer oh = o.availableNumbers.size();
		return th.compareTo(oh);
	}
	public CellType getCellType() {
		return cellType;
	}
	public TreeSet<Integer> getAvailableNumbers(){
		return (TreeSet<Integer>)availableNumbers.clone();
	}
	public void resetCell(){
		currentValue = null;
		exclusionLst = new TreeSet<Integer>();
	}
	public void putAvailNumber(Integer value){
		availableNumbers.add(value);
		/*
		if(!exclusionLst.contains(value)){
			
		}
		*/
	}
	public boolean removeAvailNumber(Integer value){
		/*
		if(availableNumbers.remove(value)){
			exclusionLst.add(value);
		}
		*/
		return availableNumbers.remove(value);
	}
	public Boolean isAvailNumberExhausted(){
		return availableNumbers.isEmpty();
	}
	public int hashCode(){
		return newHashCode;
	}
}
