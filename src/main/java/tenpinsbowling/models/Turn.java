package tenpinsbowling.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tenpinsbowling.components.FromString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Turn implements FromString {

	private String playerName;
	
	private String pinsKnockedDown;

	@Override
	public FromString fromString(String... args) {
		this.playerName = args[0];
		this.pinsKnockedDown = args[1];
		
		return this;
	}
	
}
