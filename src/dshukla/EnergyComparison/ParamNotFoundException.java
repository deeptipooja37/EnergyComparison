package dshukla.EnergyComparison;

public class ParamNotFoundException extends Exception {

	public ParamNotFoundException() {
		super("\n\nNo input file found, java -jar fizzBuzzSoltion.jar <filepath> \nExample: java -jar fizzBuzzSoltion.jar c:/foldername/file.json\n");
		// TODO Auto-generated constructor stub
	}

}
