package appDomain;

import wordtracker.WordTracker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AppDriver {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("Usage: java AppDriver <inputFile> <option> [-f <outputFile>]");
			System.err.println("Options: -pf (report), -pl (file report), -po (detailed report)");
			return;
		}

		File inputFile = new File(args[0]);
		String option = args[1];
		String outputFile = null;

		if (args.length == 4 && args[2].equals("-f")) {
			outputFile = args[3];
		}

		WordTracker tracker = new WordTracker(inputFile);
		tracker.processFile(inputFile);

		List<String> report = null;
		switch (option) {
			case "-pf":
				report = tracker.getFileReport();
				break;
			case "-pl":
				report = tracker.getReport();
				break;
			case "-po":
				report = tracker.getDetailedReport();
				break;
			default:
				System.err.println("Invalid option: " + option);
				return;
		}

		if (outputFile != null) {
			File outFile = new File(outputFile);
			try {
				if (!outFile.exists()) {
					outFile.createNewFile();
				}
				try (FileWriter writer = new FileWriter(outFile)) {
					for (String line : report) {
						writer.write(line + System.lineSeparator());
					}
				}
			} catch (IOException e) {
				System.err.println("Error writing to file: " + outputFile);
				e.printStackTrace();
			}
		}
		report.forEach(System.out::println);
	}
}