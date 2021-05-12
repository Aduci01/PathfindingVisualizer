package ui;

import javax.swing.*;
import core.Constants;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;

	public Window() {
		setTitle("Visualizer - Adam Csukas");
	}

	public void Init() {
		setSize(Constants.width, Constants.height);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(null);

		setResizable(false);
		setVisible(true);
	}
}