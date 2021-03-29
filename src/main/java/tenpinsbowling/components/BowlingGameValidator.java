package tenpinsbowling.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import tenpinsbowling.exceptions.BowlingException;
import tenpinsbowling.models.BowlingScore;
import tenpinsbowling.models.Frame;

public class BowlingGameValidator {

	private static final String ROLLS = "{{ROLLS}}";

	private static final String PLAYER = "{{PLAYER}}";

	private static final String FRAMES = "{{FRAMES}";

	
	private static final String WRONG_QUANTITY_OF_FRAMES_MESSAGE = "The match must have " + FRAMES + " frames for each player";
	
	private static final String INVALID_SCORE_MESSAGE = PLAYER + " has an invalid score at frame number " + FRAMES + " with the roll(s): " + ROLLS;

	
	private static final Function<String, BowlingException> BOWLING_EXCEPTION_GENERATOR = BowlingException::new;
		
	public void validate(Map<String, List<Frame>> game, int framesQuantity) {
		var errors = new ArrayList<String>();
		
		hasWrongQuantityOfFrames(game, framesQuantity, errors);
		hasInvalidScore(game, errors);
		
		if (!errors.isEmpty()) {
			throw BOWLING_EXCEPTION_GENERATOR.apply(errors
				.stream()
				.collect(Collectors.joining("\n")));
		}
		
	}

	private void hasWrongQuantityOfFrames(Map<String, List<Frame>> game, int framesQuantity, List<String> errors) {
		if (game
			.entrySet()
			.stream()
			.noneMatch(e -> e.getValue().size() == framesQuantity)) {
			errors.add(WRONG_QUANTITY_OF_FRAMES_MESSAGE.replace(FRAMES, String.valueOf(framesQuantity)));
		}
	}
	
	private void hasInvalidScore(Map<String, List<Frame>> game, ArrayList<String> errors) {
		game
			.entrySet()
			.stream()
			.map(Entry<String, List<Frame>>::getValue)
			.flatMap(List<Frame>::stream)
			.filter(f -> f.getPinsKnockedDown()
						.stream()
						.mapToInt(BowlingScore::scoreFromPinsKnockedDown)
						.anyMatch(i -> i == BowlingScore.INVALID.getValue() || 
						i > BowlingScore.STRIKE.getValue()))
			.findFirst()
			.ifPresent(f ->
				errors.add(INVALID_SCORE_MESSAGE.replace(FRAMES, String.valueOf(f.getNumber()))
						.replace(PLAYER, f.getPlayer())
						.replace(ROLLS, f.getPinsKnockedDown().stream().collect(Collectors.joining(", ")))));
	}


}
