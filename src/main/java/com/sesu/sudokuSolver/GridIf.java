package com.sesu.sudokuSolver;

public interface GridIf {
	Integer fillCell(Cell cell);
	Cell getNextCell();
	void display();
}
