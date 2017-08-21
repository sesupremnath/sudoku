package com.sesu.sudoku;

import java.io.IOException;

import org.junit.Test;

import com.sesu.sudokuSolver.Grid;

public class GridTest {
	@Test
	public void Test_1() throws NumberFormatException, IOException{
		Grid gd = new Grid("/Users/spr/Drive_Personal/SourceCode/GitRepo/input.txt");
		gd.display();
	}
}
