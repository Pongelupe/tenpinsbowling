package tenpinsbowling.components;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import tenpinsbowling.exceptions.BowlingException;
import tenpinsbowling.models.Frame;

public class BowlingGameValidatorTest {

	private static final int FRAMES_QUANTITY = 10;

	private BowlingGameValidator target;

	private Map<String, List<Frame>> game;

	@Before
	public void setUp() {
		target = new BowlingGameValidator();
		game = new HashMap<String, List<Frame>>();
	}

	@Test
	public void testGameWithValidGame() {
		game.put("Jeff", frameGenerator(FRAMES_QUANTITY));
		target.validate(game, FRAMES_QUANTITY);
	}

	@Test
	public void testGameWithInvalidGameWithWrongQuantityOfFrames() {
		try {
			game.put("Jeff", frameGenerator(4));
			target.validate(game, FRAMES_QUANTITY);
			
			fail();
		} catch (BowlingException e) {
			assertEquals("The match must have 10 frames for each player", e.getMessage());
		}
	}
	
	@Test
	public void testGameWithInvalidGameWithWrongScore() {
		try {
			var frames = frameGenerator(10);
			var fifthFrame = frames.get(4);
			fifthFrame.setPinsKnockedDown(Arrays.asList("-9", "15"));
			game.put("Jeff", frames);
			
			target.validate(game, FRAMES_QUANTITY);

			fail();
		} catch (BowlingException e) {
			assertEquals("Jeff has an invalid score at frame number 5 with the roll(s): -9, 15", e.getMessage());
		}
	}

	private List<Frame> frameGenerator(int quantity) {
		var frames = new ArrayList<Frame>();

		for (int i = 0; i < quantity; i++) {
			frames.add(Frame.builder()
					.number(i + 1)
					.score(6)
					.pinsKnockedDown(Arrays.asList("3", "3"))
					.player("Jeff")
					.build());
		}

		return frames;
	}

}
