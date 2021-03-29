package tenpinsbowling.components;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import tenpinsbowling.models.Turn;

public class BowlingGameTest {

	private BowlingGame target;

	private Turn john3Pins;

	private Turn johnFoul;

	private Turn jeff4Pins;

	private Turn carlStrike;

	@Before
	public void setUp() {
		target = new BowlingGame();

		john3Pins = Turn.builder().playerName("John").pinsKnockedDown("3").build();
		johnFoul = Turn.builder().playerName("John").pinsKnockedDown("F").build();
		jeff4Pins = Turn.builder().playerName("Jeff").pinsKnockedDown("4").build();
		carlStrike = Turn.builder().playerName("Carl").pinsKnockedDown("X").build();
	}

	@Test
	public void testGetFramesPerPlayerFromTurnssWithOnePlayer() {
		var turns = Arrays.asList(john3Pins, john3Pins, john3Pins, john3Pins, john3Pins, john3Pins, john3Pins,
				john3Pins, john3Pins, john3Pins, john3Pins, john3Pins, john3Pins, john3Pins, john3Pins, john3Pins,
				john3Pins, john3Pins, john3Pins, john3Pins);

		var framesPerPlayer = target.play(turns);
		assertEquals(1, framesPerPlayer.size());

		var johnsFrames = framesPerPlayer.get("John");
		assertEquals(10, johnsFrames.size());
		var frame = johnsFrames.get(0);

		assertEquals(1, frame.getNumber());
		assertEquals("John", frame.getPlayer());
		assertEquals(Arrays.asList("3", "3"), frame.getPinsKnockedDown());
		assertEquals(6, frame.getScore());
	}

	@Test
	public void testGetFramesPerPlayerFromTurnssWithOnePlayerWithStrike() {
		var turns = Arrays.asList(carlStrike, carlStrike, carlStrike, carlStrike, carlStrike, carlStrike, carlStrike,
				carlStrike, carlStrike, carlStrike, carlStrike, carlStrike);

		var framesPerPlayer = target.play(turns);
		assertEquals(1, framesPerPlayer.size());

		var johnsFrames = framesPerPlayer.get("Carl");
		assertEquals(10, johnsFrames.size());
		var frame = johnsFrames.get(0);

		assertEquals(1, frame.getNumber());
		assertEquals("Carl", frame.getPlayer());
		assertEquals(Arrays.asList("X"), frame.getPinsKnockedDown());
		assertEquals(10, frame.getScore());
	}

	@Test
	public void testGetFramesPerPlayerFromTurnssWithFoulOnePlayer() {
		var turns = Arrays.asList(john3Pins, johnFoul, john3Pins, johnFoul, john3Pins, johnFoul, john3Pins, johnFoul,
				john3Pins, johnFoul, john3Pins, johnFoul, john3Pins, johnFoul, john3Pins, johnFoul, john3Pins, johnFoul,
				john3Pins, johnFoul);

		var framesPerPlayer = target.play(turns);
		assertEquals(1, framesPerPlayer.size());

		var johnsFrames = framesPerPlayer.get("John");
		assertEquals(10, johnsFrames.size());
		var frame = johnsFrames.get(0);

		assertEquals(1, frame.getNumber());
		assertEquals("John", frame.getPlayer());
		assertEquals(Arrays.asList("3", "F"), frame.getPinsKnockedDown());
		assertEquals(3, frame.getScore());
	}

	@Test
	public void testGetFramesPerPlayerFromTurnssWithTwoPlayers() {
		var turns = Arrays.asList(john3Pins, john3Pins, jeff4Pins, jeff4Pins, john3Pins, john3Pins, jeff4Pins,
				jeff4Pins, john3Pins, john3Pins, jeff4Pins, jeff4Pins, john3Pins, john3Pins, jeff4Pins, jeff4Pins,
				john3Pins, john3Pins, jeff4Pins, jeff4Pins, john3Pins, john3Pins, jeff4Pins, jeff4Pins, john3Pins,
				john3Pins, jeff4Pins, jeff4Pins, john3Pins, john3Pins, jeff4Pins, jeff4Pins, john3Pins, john3Pins,
				jeff4Pins, jeff4Pins, john3Pins, john3Pins, jeff4Pins, jeff4Pins);

		var framesPerPlayer = target.play(turns);
		assertEquals(2, framesPerPlayer.size());

		var johnsFrames = framesPerPlayer.get("John");
		assertEquals(10, johnsFrames.size());
		var frameJohn = johnsFrames.get(0);

		assertEquals(1, frameJohn.getNumber());
		assertEquals("John", frameJohn.getPlayer());
		assertEquals(Arrays.asList("3", "3"), frameJohn.getPinsKnockedDown());
		assertEquals(6, frameJohn.getScore());

		var jeffsFrames = framesPerPlayer.get("Jeff");
		assertEquals(10, jeffsFrames.size());
		var frameJeff = jeffsFrames.get(0);

		assertEquals(1, frameJeff.getNumber());
		assertEquals("Jeff", frameJeff.getPlayer());
		assertEquals(Arrays.asList("4", "4"), frameJeff.getPinsKnockedDown());
		assertEquals(8, frameJeff.getScore());
	}

	@Test
	public void testGetFramesPerPlayerFromTurnssWithPerfectGame() {
		var turns = Arrays.asList(carlStrike, carlStrike, carlStrike, carlStrike, carlStrike, carlStrike, carlStrike,
				carlStrike, carlStrike, carlStrike, carlStrike, carlStrike); // Twelve strikes

		var framesPerPlayer = target.play(turns);
		assertEquals(1, framesPerPlayer.size());

		var carlsFrames = framesPerPlayer.get("Carl");
		assertEquals(10, carlsFrames.size());

		carlsFrames.forEach(f -> {
			assertEquals(f.getNumber() < 10 ? 10 : 30, f.getScore());
			assertEquals(f.getNumber() < 10 ? Arrays.asList("X") : Arrays.asList("X", "X", "X"),
					f.getPinsKnockedDown());

		});
	}

	@Test
	public void testGetFramesPerPlayerFromTurnssWithZeroScoreGame() {
		var turns = Arrays.asList(johnFoul, johnFoul, johnFoul, johnFoul, johnFoul, johnFoul, johnFoul, johnFoul,
				johnFoul, johnFoul, johnFoul, johnFoul, johnFoul, johnFoul, johnFoul, johnFoul, johnFoul, johnFoul,
				johnFoul, johnFoul); // Twenty fouls

		var framesPerPlayer = target.play(turns);
		assertEquals(1, framesPerPlayer.size());

		var johnsFrames = framesPerPlayer.get("John");
		assertEquals(10, johnsFrames.size());

		johnsFrames.forEach(f -> {
			assertEquals(0, f.getScore());
			assertEquals(Arrays.asList("F", "F"), f.getPinsKnockedDown());
		});
	}

}
