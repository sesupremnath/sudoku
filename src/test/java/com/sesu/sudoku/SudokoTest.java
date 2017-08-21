package com.sesu.sudoku;

import java.io.IOException;
import java.util.LinkedList;

import org.junit.Test;

import com.sesu.sudokuSolver.Sudoku;

public class SudokoTest {
	@Test
	public void Test_1() throws NumberFormatException, IOException{
		Sudoku su = new Sudoku("/Users/spr/Drive_Personal/SourceCode/GitRepo/input.txt");
		su.solve();
	}
	@Test
	public void Test_2(){
		LinkedList<Integer> lst = new LinkedList<Integer>();
		lst.add(12);
		lst.add(13);
		lst.add(14);
		lst.pop();
		lst.addFirst(12);
		System.out.println(lst.pop());
	}
}
