"# EnergyComparison" 

To compile and build the project : mvn install 


To run the application with default plan.json file run the command : mvn exec:java -D"exec.mainClass"="dshukla.EnergyComparison.Solution" 


To run the application with other plan.json file run the command :  mvn exec:java -D"exec.mainClass"="dshukla.EnergyComparison.Solution" -Dexec.args="file_path_toplan.json"
