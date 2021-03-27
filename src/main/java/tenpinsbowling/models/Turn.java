package tenpinsbowling.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tenpinsbowling.components.FromString;

@NoArgsConstructor
@Getter
@ToString
public class Turn implements FromString {

	private String playerName;
	
	private String score;

	@Override
	public FromString fromString(String... args) {
		this.playerName = args[0];
		this.score = args[1];
		
		return this;
	}
	
}
