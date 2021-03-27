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
	
	private int accumulativeScore;
}
