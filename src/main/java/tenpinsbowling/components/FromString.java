package tenpinsbowling.components;

/**
 * This interface is called by {@link FileInputReader} to fill properties from a
 * file line.
 * 
 * @author pongelupe
 *
 */
@FunctionalInterface
public interface FromString {

	public FromString fromString(String... strings);

}
