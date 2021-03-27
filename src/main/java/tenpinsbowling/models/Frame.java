package tenpinsbowling.models;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Frame {

	private int number;
	
	private String player;
	
	private List<String> pinsKnockedDown;
	
	private int score;
	
	private Integer accumulativeScore;
	
	
	public boolean isStrikeOrSpare() {
		return this.getScore() >= 10;
	}
	
	public boolean isStrikeAndSpare() {
		return this.getScore() == 20;
	}
	
	public boolean isStrike() {
		return pinsKnockedDown
					.stream()
					.anyMatch(p -> p.equals(BowlingScore.STRIKE.getRepresentation()) 
							|| p.equals(String.valueOf(BowlingScore.STRIKE.getValue())));
	}
}
