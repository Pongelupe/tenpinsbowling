package tenpinsbowling.models;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This enum represents the some the scores possibilities in a game of bowling
 * 
 * @author pongelupe
 *
 */
@AllArgsConstructor
@Getter
public enum BowlingScore {

	STRIKE("X", 10), SPARE("/", 10), FOUL("F", 0), INVALID(null, -1);

	private final String representation;

	private int value;

	public static int scoreFromPinsKnockedDown(String pinsKnockedDown) {
		var score = 0;
		try {
			score = Integer.parseInt(pinsKnockedDown);
		} catch (NumberFormatException e) {
			score = Arrays.asList(BowlingScore.values())
					.stream()
					.filter(s -> s.representation.equals(pinsKnockedDown))
					.findFirst()
					.orElse(INVALID)
					.getValue();
		}

		return score;
	}

}
