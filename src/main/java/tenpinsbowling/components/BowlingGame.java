package tenpinsbowling.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import tenpinsbowling.models.BowlingScore;
import tenpinsbowling.models.Frame;
import tenpinsbowling.models.Turn;

public class BowlingGame {

	private static final int DEFAULT_FRAMES_QUANTITY = 10;
	
	@Getter
	private final int framesQuantity;
	
	public BowlingGame() {
		this(DEFAULT_FRAMES_QUANTITY);
	}

	public BowlingGame(int defaultFramesQuantity) {
		framesQuantity = defaultFramesQuantity;
	}
	
	public Map<String, List<Frame>> getFramesPerPlayerFromTurns(List<Turn> turns) {
		return turns.stream()
			.collect(Collectors.groupingBy(Turn::getPlayerName))
			.entrySet()
			.stream()
			.map(e -> {
				var framesPerPlayer = new HashMap<String, List<Frame>>();
				var frames = new ArrayList<Frame>();
				
				var frameNumber = 1;
				var tempPinsKnockedDown = new ArrayList<String>();
				var tempScore = 0;
				var turnNumbers = 1; 
				
				for (Turn turn : e.getValue()) {
					var pinsKnockedDown = turn.getPinsKnockedDown();
					tempPinsKnockedDown.add(pinsKnockedDown);
					
					var turnValue = BowlingScore.scoreFromPinsKnockedDown(pinsKnockedDown);
					
					if (hasTurnFinished(tempScore, turnNumbers, turnValue, frameNumber)) {
						frames.add(Frame.builder()
							.number(frameNumber++)
							.pinsKnockedDown(new ArrayList<>(tempPinsKnockedDown))
							.player(e.getKey())
							.score(tempScore + turnValue)
							.build());
						
						turnNumbers = 1;
						tempScore = 0;
						tempPinsKnockedDown.clear();
						
					} else {
						turnNumbers++;
						tempScore += turnValue;
					}
					
				}
				
				
				framesPerPlayer.put(e.getKey(), frames);
				return framesPerPlayer;
			})
			.reduce(new HashMap<>(), (acc, el) -> {
				acc.putAll(el);
				return acc;
			});
	}
	
	/**
	 * This method defines if a turn has finished.
	 * 
	 * For all turns but the last, the turn is finished if the player has done two rolls
	 * (represented by {@link turnNumbers}) or if the player has scored a strike
	 * (represented by {@link turnValue} equals 10).
	 * 
	 * For the last turn, the turn is finished if the player has done three rolls (granted by a strike or a spare) or
	 * if the player has done two rolls without a strike or spare.
	 * 
	 * @param tempScore
	 * @param turnNumbers
	 * @param turnValue
	 * @param frameNumber
	 * @return
	 */
	private boolean hasTurnFinished(int tempScore, int turnNumbers, int turnValue, int frameNumber) {
		var isLastTurn = frameNumber == framesQuantity;
		return isLastTurn ? turnNumbers == 3 ||  (turnNumbers == 2 && (tempScore + turnValue < 10)) :
			turnNumbers == 2 || (turnValue == 10);
	}

}
