package wordtracker;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The Word class represents a word and its occurrences in various files.
 */
public class Word implements Comparable<Word>, Serializable {
	private static final long serialVersionUID = 1L;
	private String word;
	private Map<String, Set<Integer>> occurrences;

	/**
	 * Constructs a new Word object with the specified word.
	 *
	 * @param word the word to be tracked
	 */
	public Word(String word) {
		this.word = word;
		occurrences = new HashMap<>();
	}

	/**
	 * Adds an occurrence of the word in the specified file and line number.
	 *
	 * @param fileName the name of the file where the word occurred
	 * @param lineNumber the line number where the word occurred
	 */
	public void addOccurrence(String fileName, int lineNumber) {
		if(occurrences.containsKey(fileName)) {
			occurrences.get(fileName).add(lineNumber);
		} else {
			Set<Integer> lineNumbers = new HashSet<>();
			lineNumbers.add(lineNumber);
			occurrences.put(fileName, lineNumbers);
		}
	}

	/**
	 * Returns a map of occurrences of the word.
	 *
	 * @return a map of occurrences of the word
	 */
	public Map<String, Set<Integer>> getOccurrences() {
		return occurrences;
	}

	/**
	 * Returns the word being tracked.
	 *
	 * @return the word being tracked
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Compares this word with the specified word for order.
	 *
	 * @param o the word to be compared
	 * @return a negative integer, zero, or a positive integer as this word
	 *         is less than, equal to, or greater than the specified word
	 */
	@Override
	public int compareTo(Word o) {
		return this.word.compareTo(o.word);
	}
}