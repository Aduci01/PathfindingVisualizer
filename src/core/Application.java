package core;
import javafx.util.Pair;
import mazeGeneration.BinaryTreeMaze;
import mazeGeneration.RandomWalls;
import mazeGeneration.RecursiveBacktracking;
import mazeGeneration.RecursiveDivision;
import ui.Window;

/**
 * Frame class for the program
 *
 */
public class Application {
	public static Application _instance;

	Window window;
	GameView gameView;

	/**
	 * Delay time in millisecs, used for visualization and animation
	 */
	public static int ms = 75;

	/**
	 * If an algorithm is ongoing, it is true
	 */
	public static boolean waitingForAlgorithm = false;

	/**
	 * Called from Main.main(), initializes the main Views and creates the window
	 */
	public void Start() {
		_instance = this;

		window = new Window();

		gameView = new GameView();
		window.add(gameView);
		
		window.Init();
	}

	public GameView GetGameView(){
		return gameView;
	}
}
