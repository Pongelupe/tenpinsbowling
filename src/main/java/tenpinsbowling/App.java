package tenpinsbowling;

import java.io.IOException;

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
	 * The first must be the name of a input file inside src/main/resources
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String... args) throws Exception {
		var filename = args[0];

		var fileReader = new FileInputReader(filename);

		var turns = fileReader.readAllLines(Turn.class);

		var bowlingGame = new BowlingGame();
		var bowlingGameScoreCalculator = new BowlingGameScoreCalculator();
		var framesPerPlayer = bowlingGame.getFramesPerPlayerFromTurns(turns);
		framesPerPlayer.forEach((e, f) -> bowlingGameScoreCalculator.fillAccumutativeScore(f));

		var resultPrinter = new BowlingGameScoreResultPrinter(framesPerPlayer);

		var print = resultPrinter.preparePrint();

		System.out.println(print);

		fileReader.close();
	}

}
