package com.sesu.sudokuSolver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Grid{
	private int maxValue;
	private LinkedList<Cell>emptyCellLst;
	private HashMap<String, HashSet<String>> peers;
	private HashMap<Integer, HashSet<String>> site;
	private HashMap<String, Cell> squares;
	
	public Grid(){
		
	}
	
	public Grid(String inputFile) throws NumberFormatException, IOException{
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		maxValue = Integer.parseInt(br.readLine());
		buildEmptyGrid();
		buildSite();
		buildMapping();
		
		String line;
		String []tmp;
		int rowNumber, colNumber;
		String value;
		while((line=br.readLine())!=null){
			tmp = line.split(",");
			rowNumber = Integer.parseInt(tmp[0]);
			colNumber = Integer.parseInt(tmp[1]);
			value = tmp[2];
			setReadOnlySquare(siteToString(rowNumber,colNumber), value);
		}
		buildEmptyCellLst();
	}

	void buildSite(){
		site = new HashMap<Integer, HashSet<String>>();
		for(int i=1;i<=maxValue;i++){
			site.put(i, new HashSet<String>());
		}
		
		for(int i=1;i<=maxValue;i++){
			for(int j=1;j<=maxValue;j++){
				site.get(getSiteNumber(i, j)).add(siteToString(i, j));
			}
		}
	}
	void buildEmptyGrid(){
		squares = new HashMap<String, Cell>();
		for(int i=1;i<=maxValue;i++){
			for(int j=1;j<=maxValue;j++){
				Cell cell = new Cell(i,j);
				cell.setLocation(i, j);
				squares.put(siteToString(i, j), cell);
			}
		}
	}
	void buildMapping() {
		String tmp;
		peers = new HashMap<String, HashSet<String>>();
		int t_i,t_j;
		for (String key : squares.keySet()) {
			peers.put(key, new  HashSet<String>());
			t_i = Integer.parseInt(key.substring(0,1));
			for(int j=1;j<=maxValue;j++){
				tmp = siteToString(t_i, j);
				peers.get(key).add(tmp);					
			}
			t_j = Integer.parseInt(key.substring(1,2));
			for(int i=1;i<=maxValue;i++){
				tmp = siteToString(i,t_j);
				peers.get(key).add(tmp);					
			}
			peers.get(key).addAll(site.get(getSiteNumber(t_i,t_j)));
			peers.get(key).remove(key);
		}
	}
	
	public String siteToString(int i, int j){
		return ""+i+j;
	}
	void setReadOnlySquare(String location, String value) {
		Cell cell = squares.get(location);
		cell.setReadOnlyCell(value.toString());
		eliminateTheValues(location, value);
	}
	void assignValue(Cell cell, String value){
		assignValue(siteToString(cell.i, cell.j), value);
	}
	void assignValue(String location, String value){
		Cell cell = squares.get(location);
		cell.setCurrentValue(value.toString());
		eliminateTheValues(location, value);
	}
	
	public void eliminateTheValues(String square, String value){
		for(String eachCell: peers.get(square)){
			squares.get(eachCell).removeAvailNumber(value);
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
	/*
	public void displaySite(){
		Cell cell;
		for(int i=1;i<=maxValue;i++){
			System.out.println("SITE: "+i);
			for(int j=1;j<=maxValue;j++){
				cell = squares.get(siteToString(i, j));
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
	*/
	void buildEmptyCellLst(){
		emptyCellLst = new LinkedList<Cell>();
		for(int i=1;i<=9;i++){
			for(int j=1;j<=9;j++){
				Cell cell = squares.get(siteToString(i, j));
				//System.out.println(i+","+j);
				if(cell.getCellType().equals(CellType.EMPTY)){
					emptyCellLst.add(cell);
				}
			}
		}
		//Collections.sort(emptyCellLst);
	}
	
	public LinkedList<Cell> getEmptyCellLst(){
		return emptyCellLst;
	}
	public void display() {
		Cell cell;
		String value;
		for(int i=1;i<=maxValue;i++){
			for(int j=1;j<=maxValue;j++){
				cell = squares.get(siteToString(i, j));
				value = cell.getCurrentValue();
				if(value!=null){
					System.out.print(String.format("%4s", value));
					//System.out.print(String.format(" %4s", value)+"("+cell.getAvailNumber()+")");
				}
				else{
					System.out.print(String.format("%4s","_"));
					//System.out.print(String.format("%4s","_")+"("+cell.getAvailNumber()+")");
				}
			}
			System.out.println();
		}
		System.out.println("--------------------------------------- " + this.emptyCellLst.size());
	}

	public Cell getCell(){
		try{
			Collections.sort(emptyCellLst);
			return emptyCellLst.pop();
		} catch(Exception e){
			return null;
		}
	}
	
	public Boolean isSolved(){
		if(emptyCellLst.size()==0)
			return true;
		else
			return false;
	}
	
	public Grid getCopy(){
		Grid gd = new Grid();
		gd.maxValue = this.maxValue;
		gd.emptyCellLst = new LinkedList<Cell>();
		gd.squares = new HashMap<String, Cell>();
		gd.peers = this.peers;
		gd.site = this.site;
		for(int i=1;i<=maxValue;i++){
			for(int j=1;j<=maxValue;j++){
				gd.squares.put(siteToString(i, j), this.squares.get(siteToString(i, j)).getCopy());
			}
		}
		gd.buildEmptyCellLst();
		return gd;
	}
}
