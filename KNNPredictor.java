package projectpart2;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import projectpart2.DataPoint;
import java.util.Random;
import java.util.Locale;
public class KNNPredictor extends Predictor {
	private int K = 3;
	private int numsurvived = 0;
	private int numnotsurvived=0;
	private ArrayList<DataPoint> dataSet = new ArrayList<DataPoint>(); // create a new Arraylist
	public KNNPredictor(int kParam, int nParam, int nParam2) {
		this.K = kParam;
		this.numsurvived = nParam;
		this.numnotsurvived = nParam2;
	}
	private List<String> getRecordFromLine(String line) {
		List<String> values = new ArrayList<String>();
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(",");
			while (rowScanner.hasNext()) {
				values.add(rowScanner.next());
			}
		}
		return values;
	}
		
		public ArrayList<DataPoint> readData(String filename) {
			try  {
				Scanner scanner = new Scanner(new File(filename));
				scanner.useLocale(Locale.US);
				
				scanner.nextLine();
				while (scanner.hasNextLine()) {
					List<String> records = getRecordFromLine(scanner.nextLine());
					
					if (!records.get(5).isEmpty() && records.size() == 7) {
						Random rand = new Random();
						double randNum = rand.nextDouble();
						boolean survived = false;
						 //90% of the data is reserved for training
						if (randNum < 0.9 && records.get(1).equals("1")) {
							// Set the type of DataPoint as “train” and put into the Collection
							// 1 = survived
							survived = true;
							numsurvived +=1;
							//System.out.println("tested randNum");
						} else if (records.get(1).equals("1")) {
							numnotsurvived +=1;	
							//System.out.println("numnotsurvived");
						}else {
							// Set the type of DataPoint as “test” and put into the Collection
							numsurvived+=1;
							survived = true;
							//System.out.println("numsurvived");
						}
						
						//System.out.println(survived);
						DataPoint data = new DataPoint(records.get(1), survived, Double.parseDouble(records.get(5)), Double.parseDouble(records.get(6)));
						dataSet.add(data);
					
					}

					//System.out.println(records.get(1));

				}
				
			}catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
			System.out.println("Number of Passengers Survived:" + numsurvived);
			System.out.println("Number of Training DataPoint:" + numnotsurvived);
			//Create new local ArrayList copy over elements from dataSet to local ArrayList then return local ArrayList
			ArrayList<DataPoint> datas = new ArrayList<DataPoint>();
			Collections.copy(dataSet, datas);
			
			return dataSet;
	}
		 private double getDistance(DataPoint p1, DataPoint p2) {
			 double x1 = p1.getF1();
			 double x2 = p2.getF1();
			 double y2 =p2.getF2();
			 double y1 = p1.getF2();
			 return Math.sqrt((y2 - y1) * (y2-y1) + (x2-x1) * (x2-x1));
		 }
		 public String test(DataPoint data) {
			 Double[][] Array2D = new Double[numsurvived + numnotsurvived][2];
			 int survived = 0;
			 int notSurvived = 0;
			 if(!data.getIsTest()) {
				 return ("Data is not a test DataPoint");
			 }else {
				 //System.out.println("Data is a test method");
				 // Compute the distance between the input point versus every train point in the 
				 //data set and store the distance and the label into a 2D Array, {distance, label}.
				 //System.out.println(numsurvived+numnotsurvived);
				 for(int i =0; i<(numsurvived + numnotsurvived); i++) {
					 double distance = getDistance(data, dataSet.get(i));
					 
					 Array2D[i][0] = distance;
					 Array2D[i][1] = Double.parseDouble(dataSet.get(i).getLabel());
				 }
				 java.util.Arrays.sort(Array2D, new java.util.Comparator<Double[]>() {
					 public int compare(Double[]a,Double[]b){
						 return a[0].compareTo(b[0]);
					 }
				 });
				 for (int i = 0; i < K; i++) {
					 if (Array2D[i][1] == 1.0) {
						 survived++;
					 }else {
						 notSurvived++;
					 }
					 
				 }
			 }
			 if (survived > notSurvived) {
				 return "1";
			 }
			return "0";
			 
		 }
		 public double getAccuracy(ArrayList<DataPoint> data) {
			 double truePositive = 0.0;
			 double falsePositive = 0.0;
			 double trueNegative = 0.0;
			 double falseNegative = 0.0;
			 //System.out.println(data.size());
			 
			 for (int i = 0; i < data.size(); i++) {
				 String label = test(data.get(i)); 
				 System.out.println("Label " + label);
				 if (label.equals("1") && data.get(i).getLabel().equals("1")) {
					 truePositive+= 1.0;
				 }
				 else if (label.equals("1") && data.get(i).getLabel().equals("0")) {
					 falsePositive+=1.0;
				 }
				 else if (label.equals("0") && data.get(i).getLabel().equals("1")) {
					 falseNegative+=1.0;	 
				 }
				 else if (label.equals("0") && data.get(i).getLabel().equals("0")) {
					 trueNegative+=1.0;
				 }
				 
				 
				 
		}	
			 double result = (truePositive+trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative);
			 return (result);
			 
		 }
		 
		 public double getPrecision(ArrayList<DataPoint> data) {
			 double truePositive = 0;
			 double falsePositive = 0;
			 double trueNegative = 0;
			 double falseNegative = 0;
			 
			 for (int i = 0; i < data.size(); i++) {
				 String label = test(data.get(i));
				 if (label.equals("1") && data.get(i).getLabel().equals("1")) {
					 truePositive+= 1;
				 }
				 else if (label.equals("1") && data.get(i).getLabel().equals("0")) {
					 falsePositive+=1;
				 }
				 else if (label.equals("0") && data.get(i).getLabel().equals("1")) {
					 falseNegative+=1;	 
				 }
				 else if (label.equals("0") && data.get(i).getLabel().equals("0")) {
					 trueNegative+=1;
				 }		
		 }
			 double result2 = (truePositive) / (truePositive + falseNegative);
			 return (result2);
		 }

}

	
		 
		 // private DataAnalys analyze(ArrayList<DataPoint> data) {
		 // create data analysis result
		 // for loop through every point in ArrayList data
		 	// check if the point is not a test data point then continue
		 	// get the point label, read the label from actual data set
		 	// predict the the label from the training data
		 	// set the ArrayList data member at the to to this ArrayList data
		 
			
	


