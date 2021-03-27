package tenpinsbowling.components;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import tenpinsbowling.models.BowlingScore;
import tenpinsbowling.models.Frame;

/**
 * This class is responsible to format the output of a game of bowling
 * 
 * @author pongelupe
 *
 */
@AllArgsConstructor
public class BowlingGameScoreResultPrinter {

	private static final String BREAK_LINE = "\n";
	private static final String EMPTY_STRING = "";
	
	private final Map<String, List<Frame>> framesPerPlayer;
	
	public String preparePrint() {
		var frame = prepareFrame();
		
		var players = framesPerPlayer
			.entrySet()
			.stream()
			.sorted((arg0, arg1) -> arg0.getKey().compareTo(arg1.getKey()))
			.map(e -> {
				var playerName = e.getKey();
				var pinfalls = preparePinfalls(e);
				var score = prepareScore(e);
				
				return playerName + pinfalls + score;
			})
			.collect(Collectors.joining(BREAK_LINE));
		
		return frame + players;
	}

	private String preparePinfalls(Entry<String, List<Frame>> e) {
		return e.getValue()
		.stream()
		.map(f -> {
			var pinsKnockedDown = f.getPinsKnockedDown();
			if (f.isStrikeOrSpare()) {
				if (f.isStrike()) {
					handleStrike(f, pinsKnockedDown);
				} else {
					pinsKnockedDown.remove(1);
					pinsKnockedDown.add(BowlingScore.SPARE.getRepresentation());							
				}
			}
			
			return pinsKnockedDown;
		})
		.flatMap(List<String>::stream)
		.map(s -> s.equals(String.valueOf(BowlingScore.STRIKE.getValue())) 
				? BowlingScore.STRIKE.getRepresentation() : s)
		.collect(Collectors.joining("\t", "\nPinfalls\t", BREAK_LINE));
	}

	private void handleStrike(Frame f, List<String> pinsKnockedDown) {
		if (pinsKnockedDown.size() == 1) {
			pinsKnockedDown.add(0, EMPTY_STRING);
		} else if (f.isStrikeAndSpare()) {
			if (pinsKnockedDown.get(0).equals(String.valueOf(BowlingScore.STRIKE.getValue()))) {
				pinsKnockedDown.remove(2);									
				pinsKnockedDown.add(BowlingScore.SPARE.getRepresentation());							
			} else {
				pinsKnockedDown.remove(1);									
				pinsKnockedDown.add(1, BowlingScore.SPARE.getRepresentation());																
			}
		}
	}

	private String prepareFrame() {
		return framesPerPlayer
			.entrySet()
			.stream()
			.findAny()
			.map(Entry<String, List<Frame>>::getValue)
			.orElseThrow()
			.stream()
			.map(Frame::getNumber)
			.map(String::valueOf)
			.collect(Collectors.joining("\t\t", "Frame\t\t", BREAK_LINE));
	}
	
	private String prepareScore(Entry<String, List<Frame>> e) {
		return e.getValue()
				.stream()
				.map(Frame::getAccumulativeScore)
				.map(String::valueOf)
				.collect(Collectors.joining("\t\t", "Score\t\t", EMPTY_STRING));
	}

}
