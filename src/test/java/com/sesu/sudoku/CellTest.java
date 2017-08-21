package com.sesu.sudoku;

import org.junit.Test;

import com.sesu.sudokuSolver.Cell;

public class CellTest {
  @Test
  public void TestFill() {
	  Cell cell = new Cell();
	  cell.fill();
	  assert cell.getCurrentValue()==1;
	  cell.fill();
	  assert cell.getCurrentValue()==2;
  }
  
  @Test
  public void TestUndoFill(){
	  /*
	  Cell cell = new Cell();
	  cell.fill();
	  cell.undoRemove(value);
	  assert cell.getCurrentValue()==null;
	  cell.fill();
	  assert cell.getCurrentValue()==2;
	  */
  }
  
  @Test
  public void TestFillExhaustion(){
	  Cell cell = new Cell();
	  Cell.maxValue = 9;
	  for(int i=1;i<9;i++){
		  cell.fill();
	  }
	  cell.fill();
	  assert cell.getCurrentValue()==9;
	  cell.fill();
	  assert cell.getCurrentValue()==null;
  }
}
