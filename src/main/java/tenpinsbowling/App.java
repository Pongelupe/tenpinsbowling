package tenpinsbowling;

import java.io.IOException;

import tenpinsbowling.components.BowlingGame;
import tenpinsbowling.components.BowlingGameScoreCalculator;
import tenpinsbowling.components.FileInputReader;
import tenpinsbowling.models.Turn;

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
		var framesPerPlayer= bowlingGame.getFramesPerPlayerFromTurns(turns);
		
		var bowlingGameScoreCalculator = new BowlingGameScoreCalculator();
		
		framesPerPlayer
			.forEach((e, f) -> {
				System.out.println(e);
				bowlingGameScoreCalculator.fillAccumutativeScore(f);
				f.forEach(a -> System.out.println(a.getNumber() + ": " + a.getAccumulativeScore()));
				
				System.out.println("\n\n\n");
			});
		
		
		
		fileReader.close();
	}

}
