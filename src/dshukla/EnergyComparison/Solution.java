package dshukla.EnergyComparison;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class Solution {

	static EnergyComparisonSystem ecSystem ;
	static Scanner scanner;
	public static void main(String[] args) {
		FileInputOutputHandler io = new FileInputOutputHandler();
		Object plans = io.readJSONFile(args);
		
		ecSystem = new EnergyComparisonSystem(plans);
		
		scanner = new Scanner(new InputStreamReader(System.in));
		while(true){
				String input = scanner.nextLine();
				processInput(input);
		}
	}

	private static void processInput(String input) {
		String command = input.split(" ")[0];
		
		switch (command) {
		case "price":
			processPriceCommand(input);
			break;
		case "usage":
			processUsageCommand(input);
			break;
		case "exit":
			scanner.close();
			System.exit(0);
			break;

		default:
			break;
		}
	}

	private static void processUsageCommand(String input) {
		ecSystem.processUsageCommand(input);
	}

	private static void processPriceCommand(String input) {
		int ANNUAL_USAGE = Integer.parseInt(input.split(" ")[1]);
		ecSystem.processPriceCommand(ANNUAL_USAGE);
	}
}
