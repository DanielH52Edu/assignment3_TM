package wordtracker;

import implementations.BSTree;
import implementations.BSTreeNode;
import utilities.Iterator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This WordTracker class processes a file and tracks the occurrences of words in the file.
 * It also produces a detailed report.
 * Previous trees are saved to a file and loaded if the file exists.
 */
public class WordTracker {

	/**
	 * The binary search tree of words.
	 */
	private BSTree<Word> bst;

	/**
	 * Constructs a WordTracker object and processes the given file.
	 *
	 * @param file the file to be processed
	 */
	public WordTracker(File file) {
		bst = new BSTree<>();
		loadFromFile();
		processFile(file);
		serializeToFile();
	}

	/**
	 * Loads the binary search tree from a serialized file if it exists.
	 */
	private void loadFromFile() {
		File file = new File("repository.ser");
		if (file.exists()) {
			try (FileInputStream fileIn = new FileInputStream(file);
					 ObjectInputStream in = new ObjectInputStream(fileIn)) {
				bst = (BSTree<Word>) in.readObject();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Processes the given file, adding words to the binary search tree.
	 *
	 * @param file the file to be processed
	 */
	public void processFile(File file) {
		try (Scanner scanner = new Scanner(file)) {
			int lineNumber = 1;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] words = line.split("\\s+");
				for (String wordStr : words) {
					// This is kinda gross, but it'll do ~ Dan
					wordStr = wordStr.replaceAll("^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", "");
					if (!wordStr.isEmpty()) {
						Word word = new Word(wordStr);
						BSTreeNode<Word> wordNode = bst.search(word);
						if (wordNode != null) {
							Word existingWord = wordNode.getElement();
							existingWord.addOccurrence(file.getName(), lineNumber);
						} else {
							word.addOccurrence(file.getName(), lineNumber);
							bst.add(word);
						}
					}
				}
				lineNumber++;
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + file.getName());
		}
	}

	/**
	 * Serializes the binary search tree to a file.
	 */
	private void serializeToFile() {
		String fileName = "repository.ser";
		try (FileOutputStream fileOut = new FileOutputStream(fileName);
				 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(bst);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates a report of all words and their occurrences.
	 *
	 * @return a list of strings representing the report
	 */
	public List<String> getReport() {
		List<String> report = new ArrayList<>();
		Iterator<Word> iterator = bst.inorderIterator();
		while (iterator.hasNext()) {
			Word word = iterator.next();
			final String[] line = {"Key : === " + word.getWord() + " ==="};
			word.getOccurrences().forEach((fileName, lines) -> {
				line[0] = line[0] + " found in file: " + fileName + " on lines: " + lines;
			});
			report.add(line[0]);
		}
		return report;
	}

	/**
	 * Generates a report of all words and the files they are found in.
	 *
	 * @return a list of strings representing the file report
	 */
	public List<String> getFileReport() {
		List<String> report = new ArrayList<>();
		Iterator<Word> iterator = bst.inorderIterator();
		while (iterator.hasNext()) {
			Word word = iterator.next();
			final String[] line = {"Key : === " + word.getWord() + " === found in files: "};
			word.getOccurrences().forEach((fileName, lines) -> {
				line[0] = line[0] + fileName + ", ";
			});
			report.add(line.toString());
		}
		return report;
	}

	/**
	 * Generates a detailed report of all words, their occurrences, and the number of entries.
	 *
	 * @return a list of strings representing the detailed report
	 */
	public List<String> getDetailedReport() {
		List<String> report = new ArrayList<>();
		Iterator<Word> iterator = bst.inorderIterator();
		while (iterator.hasNext()) {
			Word word = iterator.next();
			final String[] line = {"Key : === " + word.getWord() + " === number of entries: "};
			word.getOccurrences().forEach((fileName, lines) -> {
				line[0] = line[0] + " " + lines.size() + " found in file: " + fileName + " on lines: " + lines;
			});
			report.add(line[0].toString());
		}
		return report;
	}
}