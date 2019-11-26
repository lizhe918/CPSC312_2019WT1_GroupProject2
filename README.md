# CPSC312_2019WT1_GroupProject2: Sudoku #

## Introduction

Sudoku is a logic-based, combinatorial number-placement puzzle.

The objective is to fill a 9×9 grid with digits so that each column, each row, and each of the nine 3×3 subgrids that compose the grid contain all of the digits from 1 to 9.

This project intends to construct a Sudoku solver to help player solve the sudoko game. The player can construct and fill in a sudoku by repetitively putting numbers to certain coordinator. The solver consumes the input from player, and computes the solution. Player can ask the computer for hints or solution.

A hint is to give the first empty cell in the present sudoku while a solution is present the solution computed by the solver directed on the screen. Player can do the sudoku and give his/her intermediate step to the solver to check if he/she is on the right track, which means if that number is placed on that position, then the solver will check whether there is one possible solution. When there is no solution, the solver will report to the player that there is something wrong in the Sudoku.

-------------------------------------------------

##Improvement from Project1

Since we also did the Sudoku for Project1, we make some improvements for our Project2.

Algorithm: We improve our algorithm, still using brute-force, and the speed of running complex Sudokus (even the input is none) has been improved. Comparing with Haskell, we think that Prolog is not as good as Haskell to implement our project, because Haskell highly depends on natural recusion which makes the logic much easier to understand. Also Haskell has the I/O library which makes it convenient to interact with the user while Prolog can only be input with the entire query.

GUI: We now build a GUI using Java as our frontend and our Prolog functions as our backend. Instead of typing the value and positions by hand in the terminal, we can ask the user to input the value on the textfield which also gives user a clear output than just returning a list in the terminal.

-------------------------------------------------

## Contributors

Yifei Chen, Zhe Li, Yixin Wang
