package tenpinsbowling.components;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import tenpinsbowling.models.Frame;

public class BowlingGameScoreResultPrinterTest {
	
	private BowlingGameScoreResultPrinter target;

	@Test
	public void testPrintOnePlayer() {
		var game = new HashMap<String, List<Frame>>();
		game.put("Jeff", frameGenerator("Jeff"));
		
		target = new BowlingGameScoreResultPrinter(game);
		
		var print = target.preparePrint();
		assertEquals("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n"
				+ "Jeff\n"
				+ "Pinfalls\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\n"
				+ "Score\t\t6\t\t12\t\t18\t\t24\t\t30\t\t36\t\t42\t\t48\t\t54\t\t60\n", print);
	}
	
	@Test
	public void testPrintTwoPlayers() {
		var game = new HashMap<String, List<Frame>>();
		game.put("Jeff", frameGenerator("Jeff"));
		game.put("John", frameGenerator("John"));
		
		target = new BowlingGameScoreResultPrinter(game);
		
		var print = target.preparePrint();
		assertEquals("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n"
				+ "Jeff\n"
				+ "Pinfalls\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\n"
				+ "Score\t\t6\t\t12\t\t18\t\t24\t\t30\t\t36\t\t42\t\t48\t\t54\t\t60\n"
				+ "John\n"
				+ "Pinfalls\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\n"
				+ "Score\t\t6\t\t12\t\t18\t\t24\t\t30\t\t36\t\t42\t\t48\t\t54\t\t60\n", print);
	}
	
	private List<Frame> frameGenerator(String player) {
		var frames = new ArrayList<Frame>();

		for (int i = 0; i < 10; i++) {
			frames.add(Frame.builder()
					.number(i + 1)
					.score(6)
					.pinsKnockedDown(Arrays.asList("3", "3"))
					.accumulativeScore(6 * (i + 1))
					.player(player)
					.build());
		}

		return frames;
	}

}
