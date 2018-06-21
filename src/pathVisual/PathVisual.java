package pathVisual;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import pathLogic.Map;
import pathLogic.Path;
import pathLogic.PathWay;

public class PathVisual extends Application {

	private static final int[][] map2 = { { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0 }, { 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0 },
			{ 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0 }, { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };

	private static final int[][] map6 = { { 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 }, { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 }, };

	private static final int ROWS = 100, COLUMNS = 100;
	private static final double WIDTH = 900, HEIGHT = 900;

	private int startX = 0;
	private int startY = 0;
	private int endX = 10;
	private int endY = 10;

	private Group root;

	private Path shortestPath;

	private int[][] map;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Map Visualizer");

		root = new Group();
		Scene mapScene = new Scene(root, WIDTH, HEIGHT, Color.rgb(255, 255, 255));
		
		Map tempMap = new Map(ROWS, COLUMNS);

		map = tempMap.getMap();

		startX = tempMap.startRow;
		startY = tempMap.startCol;
		endX = tempMap.endRow;
		endY = tempMap.endCol;

		long start = System.currentTimeMillis();
		
		PathWay pathway = new PathWay(map, startX, startY, endX, endY, true);
		shortestPath = pathway.getFastestPath();
		
		System.out.println(System.currentTimeMillis()-start);

		//setGrid();
		setTiles();

		System.out.println(PathWay.count);

		stage.setScene(mapScene);

		stage.show();
	}

	private void setGrid() {
		for (int i = 0; i <= ROWS; i++) {
			Line line = new Line();
			line.setStartX(i * (WIDTH / ROWS));
			line.setEndX(i * (WIDTH / ROWS));
			line.setStartY(0);
			line.setEndY(HEIGHT);
			root.getChildren().add(line);
		}
		for (int i = 0; i <= COLUMNS; i++) {
			Line line = new Line();
			line.setStartX(0);
			line.setEndX(HEIGHT);
			line.setStartY(i * (HEIGHT / COLUMNS));
			line.setEndY(i * (HEIGHT / COLUMNS));
			root.getChildren().add(line);
		}
	}

	public void setTiles() {
		// add blocks
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (map[i][j] == 1) {
					setTile(i, j, Color.BLACK);
				}
			}
		}

		// add pathway
		while (shortestPath != null) {
			setTile(shortestPath.getRow(), shortestPath.getColumn(), Color.AQUAMARINE);
			shortestPath = shortestPath.previous;
		}

		// add starting point
		setTile(startX, startY, Color.GREEN);

		// add endingPoint
		setTile(endX, endY, Color.DARKRED);
	}

	public void setTile(int row, int column, Color color) {
		// tile width
		double width = WIDTH / ROWS;
		// tile height
		double height = HEIGHT / COLUMNS;

		Rectangle tile = new Rectangle(row * width , column * height , width , height );

		tile.setFill(color);

		root.getChildren().add(tile);
	}

}
