package tictactoeV2;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TicTacToe extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static int columns, rows;
	private ButtonWithCoordinates[][] board = new ButtonWithCoordinates[rows][columns];
	private Character lastSymbol = 'O';
	private static TicTacToe frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			rows = columns = Integer.parseInt(JOptionPane.showInputDialog(null, "Set number of rows|columns:"));
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "Please input a number greater than 0!");
			System.exit(-1);
		}
		
				
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new TicTacToe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TicTacToe() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(rows, columns));
		setContentPane(contentPane);

		for (int i = 0; i < rows; ++i)
			for (int j = 0; j < columns; ++j) {
				ButtonWithCoordinates newButton = new ButtonWithCoordinates(i,
						j, "");

				board[i][j] = newButton;

				newButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.format("You clicked button %d, %d \n",
								newButton.getXCoordinate(),
								newButton.getYCoordinate());

						pressButton(newButton);

					}
				});
				contentPane.add(newButton);

			}

	}

	private void pressButton(ButtonWithCoordinates newButton) {

		if (newButton.getText() == "") {
			if (lastSymbol == 'O') {
				newButton.setText("X");
				lastSymbol = 'X';
			} else {
				newButton.setText("O");
				lastSymbol = 'O';
			}
			if (checkForWin(lastSymbol, newButton.getXCoordinate(),
					newButton.getYCoordinate())) {
				String playAgain;

				if (lastSymbol == 'X') {
					 frame.setVisible(false);
					playAgain = JOptionPane
							.showInputDialog("First player won!\n Would you like to play again? Yes/No");
				} else {
					 frame.setVisible(false);
					playAgain = JOptionPane
							.showInputDialog("Second player won!\n Would you like to play again? Yes/No");
				}

				if (playAgain.toLowerCase().equals("yes")) {
					initBoard();
					frame.setVisible(true);
				} else
					System.exit(0);
			}
		}
	}

	private void initBoard() {

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				board[i][j].setText("");
		lastSymbol = 'O';

	}

	public Boolean checkForWin(Character lastSymbol, int x, int y) {

		Boolean ok;

		// checkForWin row
		ok = true;
		for (int i = 0; i < columns; i++) {
			if (board[x][i].getText().equals(Character.toString(lastSymbol)))
				continue;
			else
				ok = false;
		}
		if (ok)
			return true;

		// checkForWin column
		ok = true;
		for (int i = 0; i < rows; i++) {
			if (board[i][y].getText().equals(Character.toString(lastSymbol)))
				continue;
			else
				ok = false;
		}
		if (ok)
			return true;

		// diagonala principala x = y
		if (x == y) { 
			ok = true;
			for (int i = 0; i < rows; i++) {
				if (board[i][i].getText()
						.equals(Character.toString(lastSymbol)))
					continue;
				else
					ok = false;
			}
			if (ok)
				return true;
		}

		// x+y = n+1-2, diagonala secundara
		if (x + y == rows - 1) { 
			ok = true;
			for (int i = 0; i < rows; i++) {
				if (board[i][columns - i - 1].getText().equals(
						Character.toString(lastSymbol)))
					continue;
				else
					ok = false;
			}
			if (ok)
				return true;

		}

		return false;
	}

}
