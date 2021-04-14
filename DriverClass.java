package projectpart2;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.swing.*;    // all of the Swing objects
import java.awt.*;       // more windowing components, including Container
import java.io.*;

public class DriverClass {
	public static void main(String[] args) {
		// Enable user input for the value of K. The program should be asking 
		//the user what value of K to use in the command line, and the program should use the 
		//input if it is valid, if not it should print an error.
		System.out.println("Enter a number for the value of K:");
		Scanner userinput = new Scanner(System.in);
		int k = 0;
		boolean run = true;
		try {
			//K must be an odd number to break ties
			k = userinput.nextInt();
		}catch (InputMismatchException e) {
			System.out.println("Error");
			run = false;
		}
		if (run) {
			if (k%2 ==0) {
				System.out.print("Error");		
		}else {
			JFrame myFrame = new JFrame();
			myFrame.setTitle("Data Points");
			myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Container contentPane = myFrame.getContentPane();
			//Instantiate a KNNPredictor object
			KNNPredictor obj = new KNNPredictor(3, 0, 0);
			// Read titanic.csv into an ArrayList of DataPoints as using readData. Make sure to split the 
			//data into train and test using the random number generator.
			ArrayList <DataPoint> data = obj.readData("titanic.csv");
//			for(DataPoint point: data) {
//				System.out.println(point.getIsTest());
//			}
			DecimalFormat decFormat = new DecimalFormat("#%");
			System.out.println("Accuracy:" + (decFormat.format(obj.getAccuracy(data))));
			System.out.println("Precision:" + (decFormat.format(obj.getPrecision(data))));
			JLabel accuracy = new JLabel("Accuracy:" + (decFormat.format(obj.getAccuracy(data))));
			contentPane.add(accuracy);
			JLabel precision = new JLabel("Precision:" + (decFormat.format(obj.getPrecision(data))));
			contentPane.add(precision);

			myFrame.setVisible(true);
			myFrame.setLayout(new GridLayout(2,2));
			myFrame.setSize(200,200);
			
		}
		}
		
		
		userinput.close();

	}
	

}
