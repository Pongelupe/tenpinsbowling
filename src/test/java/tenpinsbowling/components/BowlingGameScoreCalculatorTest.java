package tenpinsbowling.components;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

import tenpinsbowling.models.Frame;

public class BowlingGameScoreCalculatorTest {

	private BowlingGameScoreCalculator target;
	
	@Before
	public void setUp() {
		target = new BowlingGameScoreCalculator();
	}
	
	@Test
	public void testFillAccumutativeScoreGameWithoutScore() {
		var game = gameGenerator(10, Arrays.asList("F", "F"), 0);
		
		target.fillAccumutativeScore(game);
		
		game.forEach(g -> {
			Integer accumulativeScore = g.getAccumulativeScore();
			assertNotNull(accumulativeScore);
			assertEquals(0, accumulativeScore.intValue());
		});
		
	}
	
	@Test
	public void testFillAccumutativeScoreGamePerfectGame() {
		var game = gameGenerator(9, Arrays.asList("X"), 10);
		game.addAll(gameGenerator(1, Arrays.asList("X", "X", "X"), 30, 10));
		
		target.fillAccumutativeScore(game);
		
		AtomicInteger i = new AtomicInteger(0);
		var results = Arrays.asList(30, 60, 90, 120, 150, 180, 210, 240, 270, 300);
		game.forEach(g -> {
			Integer accumulativeScore = g.getAccumulativeScore();
			assertNotNull(accumulativeScore);
			assertEquals(results.get(i.getAndIncrement()) , accumulativeScore);
		});
		
	}
	
	@Test
	public void testFillAccumutativeScoreGameWithoutStrikesOrSpares() {
		var game = gameGenerator(10, Arrays.asList("3", "2"), 5);
		
		target.fillAccumutativeScore(game);
		
		AtomicInteger i = new AtomicInteger(1);
		game.forEach(g -> {
			Integer accumulativeScore = g.getAccumulativeScore();
			assertNotNull(accumulativeScore);
			assertEquals(i.getAndIncrement() * 5, accumulativeScore.intValue());
		});
		
	}
	
	@Test
	public void testFillAccumutativeScoreGameWithoutStrikes() {
		var game = gameGenerator(1, Arrays.asList("5", "5"), 10);
		game.addAll(gameGenerator(9, Arrays.asList("5", "0"), 5));
		
		target.fillAccumutativeScore(game);
		
		AtomicInteger i = new AtomicInteger(0);
		var results = Arrays.asList(15, 20, 25, 30, 35, 40, 45, 50, 55, 60);
		game.forEach(g -> {
			Integer accumulativeScore = g.getAccumulativeScore();
			assertNotNull(accumulativeScore);
			assertEquals(results.get(i.getAndIncrement()) , accumulativeScore);
		});
		
	}
	
	@Test
	public void testFillAccumutativeScoreGameWithAStrike() {
		var game = gameGenerator(1, Arrays.asList("8", "2"), 10);
		game.addAll(gameGenerator(1, Arrays.asList("7", "3"), 10, 1));
		game.addAll(gameGenerator(1, Arrays.asList("3", "4"), 7, 2));
		game.addAll(gameGenerator(1, Arrays.asList("X"), 10, 3));
		game.addAll(gameGenerator(6, Arrays.asList("5", "0"), 5, 4));
		
		target.fillAccumutativeScore(game);
		
		AtomicInteger i = new AtomicInteger(0);
		var results = Arrays.asList(17, 30, 37, 52, 57, 62, 67, 72, 77, 82);
		game.forEach(g -> {
			Integer accumulativeScore = g.getAccumulativeScore();
			assertNotNull(accumulativeScore);
			assertEquals(results.get(i.getAndIncrement()) , accumulativeScore);
		});
		
	}
	
	@Test
	public void testFillAccumutativeScoreGameStevesExample() {
		var game = gameGenerator(1, Arrays.asList("8", "2"), 10);
		game.addAll(gameGenerator(1, Arrays.asList("7", "3"), 10, 1));
		game.addAll(gameGenerator(1, Arrays.asList("3", "4"), 7, 2));
		game.addAll(gameGenerator(1, Arrays.asList("X"), 10, 3));
		game.addAll(gameGenerator(1, Arrays.asList("2", "8"), 10, 4));
		game.addAll(gameGenerator(1, Arrays.asList("X"), 10, 5));
		game.addAll(gameGenerator(1, Arrays.asList("X"), 10, 6));
		game.addAll(gameGenerator(1, Arrays.asList("8", "F"), 8, 7));
		game.addAll(gameGenerator(1, Arrays.asList("X"), 10, 8));
		game.addAll(gameGenerator(1, Arrays.asList("8", "2", "9"), 19, 9));
		
		target.fillAccumutativeScore(game);
		
		AtomicInteger i = new AtomicInteger(0);
		var results = Arrays.asList(17, 30, 37, 57, 77, 105, 123, 131, 151, 170);
		game.forEach(g -> {
			Integer accumulativeScore = g.getAccumulativeScore();
			assertNotNull(accumulativeScore);
			assertEquals(results.get(i.getAndIncrement()) , accumulativeScore);
		});
		
	}
	
	private List<Frame> gameGenerator(int quantity, List<String> pinsKnockedDown, int score, int number) {
		var frames = new ArrayList<Frame>();
		for (int i = 0; i < quantity; i++) {
			frames.add(Frame.builder()
					.number(i + 1 + number)
					.pinsKnockedDown(pinsKnockedDown)
					.score(score)
					.build());
		}
		
		return frames;
	}
	private List<Frame> gameGenerator(int quantity, List<String> pinsKnockedDown, int score) {
	
		return gameGenerator(quantity, pinsKnockedDown, score, 0);
	}

}
