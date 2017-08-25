package com.sesu.sudoku;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;

import org.junit.Test;

import com.sesu.sudokuSolver.Cell;
import com.sesu.sudokuSolver.Grid;

import junit.framework.Assert;

public class CellTest {
  @Test
  public void Test(){
	  Cell cell_1 = new Cell(1,1);
	  Assert.assertEquals(cell_1.getAvailNumber().length(),9);
	  cell_1.setCurrentValue("9");
	  cell_1.removeAvailNumber("9");
	  Assert.assertEquals(cell_1.getAvailNumber().length(),8);
  }
  
  @Test
  public void Test_2() throws NumberFormatException, IOException{
	  Grid gd = new Grid("/Users/spr/Drive_Personal/SourceCode/GitRepo/input.txt");
	  gd.getCell().setCurrentValue("6");
	  gd.display();
	  Grid gd1 = gd.getCopy();
	  gd1.display();
	  Assert.assertEquals(gd.siteToString(2, 1), "21");
	  gd.display();
  }
}
