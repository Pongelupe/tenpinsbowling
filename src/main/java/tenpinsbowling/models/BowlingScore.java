package tenpinsbowling.models;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BowlingScore {
	
	STRIKE("X", 10), SPARE("/", 10), FOUL("F", 0);
	
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
					.orElse(FOUL)
					.getValue();
		}
		
		return score;
	}
	
}
