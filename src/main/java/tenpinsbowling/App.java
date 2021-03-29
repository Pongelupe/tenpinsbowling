package tenpinsbowling;

import java.io.IOException;
import java.util.List;

import tenpinsbowling.components.BowlingGame;
import tenpinsbowling.components.BowlingGameScoreCalculator;
import tenpinsbowling.components.BowlingGameScoreResultPrinter;
import tenpinsbowling.components.FileInputReader;
import tenpinsbowling.models.Turn;

/**
 * Driver class
 *
 * @author pongelupe
 *
 */
public class App {

	/**
	 *
	 * The path to the input file must be the first argument
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String... args) {
		try {
			var turns = getTurnsFromFile(getFilePath(args));

			var bowlingGame = new BowlingGame();
			var bowlingGameScoreCalculator = new BowlingGameScoreCalculator();
			var framesPerPlayer = bowlingGame.play(turns);
			framesPerPlayer.forEach((e, f) -> bowlingGameScoreCalculator.fillAccumutativeScore(f));

			var resultPrinter = new BowlingGameScoreResultPrinter(framesPerPlayer);

			var print = resultPrinter.preparePrint();

			System.out.println(print);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private static String getFilePath(String[] args) {
		if (args.length == 0) {
			throw new IllegalArgumentException("The file path must be informed");
		}
		return args[0];
	}

	private static List<Turn> getTurnsFromFile(String path) {
		try (var fileReader = new FileInputReader(path)) {
			return fileReader.readAllLines(Turn.class);
		} catch (IOException e) {
			throw new IllegalArgumentException("The file informed does not exist");
		}
	}

}
