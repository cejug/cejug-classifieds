package net.java.dev.cejug.utils;

import java.util.Random;

/**
 * A utility class to create random sequence of characters.
 * 
 * @author Alain Fagot B&#233;arez, Bruno Duarte, Daniel Macedo (<em>original
 *         contributors</em>)
 * @author $Author$
 * @version $Rev$ ($Date$)
 * @see java.util.Random
 */
public class RandomStringGenerator {

	/**
	 * A sequence of forbidden characters.
	 */
	private transient String forbiddenCharacters = null;

	/**
	 * The getter method for the forbidden characters.
	 * 
	 * @return a string containing the forbidden characters.
	 */
	public String getExcludedChars() {
		return forbiddenCharacters;
	}

	/**
	 * Set a new string containing the forbidden characters.
	 * 
	 * @param forbidden
	 *            a string containing the forbidden characters - can be null.
	 */
	public void setExcludedChars(String forbidden) {
		this.forbiddenCharacters = forbidden;
	}

	/**
	 * Characters buffer, used to generate the random strings. Initially empty,
	 * it always growth and keeps the last generated characters available for
	 * the subsequent string generations.
	 */
	private transient static final StringBuffer randomCharsBuffer = new StringBuffer();

	/**
	 * Generates a random string. It uses an heuristic of removing the first
	 * n-characters to produce always a slightly different string without the
	 * need of generating the full array of characters again.
	 * 
	 * @param length
	 *            the length of the string to be generated.
	 * @return a string with random characters.
	 */
	public String generateString(int length) {
		Random random = new Random();
		int currentCapacity = randomCharsBuffer.capacity();
		randomCharsBuffer.delete(0, currentCapacity > 1000 ? 150
				: currentCapacity / 10);
		while (randomCharsBuffer.capacity() < length) {
			char c = (char) (random.nextInt(200));
			if (accept(c)) {
				randomCharsBuffer.append(c);
			}
		}
		return randomCharsBuffer.toString();
	}

	public static void main(String[] args) {
		RandomStringGenerator r = new RandomStringGenerator();
		System.out.println(r.generateString(50));
		System.out.println(r.generateString(50));
	}

	/**
	 * Ignorable or indefined characters should not be included in the generated
	 * string.
	 */
	private boolean accept(char c) {
		if (forbiddenCharacters != null && forbiddenCharacters.indexOf(c) > -1) {
			return false;
		} else if (Character.isDefined(c)) {
			if (Character.isWhitespace(c)) {
				return true;
			}
			if (Character.isIdentifierIgnorable(c)) {
				return false;
			}
			return true;
		}
		return false;
	}
}