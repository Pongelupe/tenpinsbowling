package tenpinsbowling;

import java.io.IOException;

import tenpinsbowling.components.BowlingGame;
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
		bowlingGame.getFramesPerPlayerFromTurns(turns);
		
		fileReader.close();
	}

}
