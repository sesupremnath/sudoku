package com.sesu.sudokuSolver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

enum CellType{
	EMPTY,
	READONLY,
	FILLED
}

public class Cell implements Comparable<Cell>{
	public static int maxValue;
	private LinkedList<Integer> availableNumbers, usedNumbers;
	private Integer currentValue; 
	private CellType cellType;
	//private Integer siteId;
	public int i,j;
	
	public Cell(){
		availableNumbers = new LinkedList<Integer>();
		usedNumbers = new LinkedList<Integer>();
		for(int i=1;i<=maxValue;i++){
			availableNumbers.add(i);
		}
		cellType = CellType.EMPTY;
	}
	
	public void setLocation(int i, int j){
		this.i = i;
		this.j = j;
	}
	public void setReadOnlyCell(Integer value){
		cellType = CellType.READONLY;
		currentValue = value;
	}
	public Integer fill(){
		if(availableNumbers.isEmpty()){
			return null;
		} else {
			currentValue = availableNumbers.pop();
			usedNumbers.add(currentValue);
			return currentValue;
		}
	}
	public void resetAvailLst(){
		availableNumbers = (LinkedList<Integer>)usedNumbers.clone();
		usedNumbers = usedNumbers = new LinkedList<Integer>();
		currentValue = null;
	}
	public Integer getCurrentValue() {
		return currentValue;
	}
	/*
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public Integer getSiteId() {
		return siteId;
	}
	*/
	public Boolean removeAvailNumber(Integer value){
		return availableNumbers.removeFirstOccurrence(value);
	}
	public void undoRemove(Integer value){
		if(!availableNumbers.contains(value)){
			availableNumbers.add(value);
		}
	}
	public int compareTo(Cell o){
		Integer th = this.availableNumbers.size();
		Integer oh = o.availableNumbers.size();
		return th.compareTo(oh);
	}

	public Boolean isAvailNumberExhausted(){
		return availableNumbers.isEmpty();
	}
	public CellType getCellType() {
		return cellType;
	}
}
