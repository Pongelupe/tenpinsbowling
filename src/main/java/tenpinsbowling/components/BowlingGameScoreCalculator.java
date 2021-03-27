package tenpinsbowling.components;

import java.util.List;

import tenpinsbowling.models.BowlingScore;
import tenpinsbowling.models.Frame;

public class BowlingGameScoreCalculator {

	public void fillAccumutativeScore(List<Frame> frames) {
		var partialSum = 0;
		for (var frame : frames) {
			if (frame.isStrikeOrSpare()) {
				partialSum += 10
						+ BowlingScore.scoreFromPinsKnockedDown(findNextBall(frame.getNumber(), frames, 0));
				if (frame.isStrike()) {
					partialSum += BowlingScore.scoreFromPinsKnockedDown(findNextBall(frame.getNumber(), frames, 1));
				}

			} else {
				partialSum += frame.getScore();
			}
			frame.setAccumulativeScore(partialSum);
		}
	}

	private String findNextBall(int number, List<Frame> frames, int skip) {
		try {
		List<String> pinsKnockedDown = frames.get(number).getPinsKnockedDown();
		return skip < pinsKnockedDown.size() ? pinsKnockedDown.get(skip) : findNextBall(number + 1, frames, skip - 1);}
		catch (Exception e) {
			return frames.get(frames.size() - 1).getPinsKnockedDown().get(skip == 0 ? 2 : 1);
		}
	}

}
