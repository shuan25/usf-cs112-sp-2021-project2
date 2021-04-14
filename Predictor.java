package projectpart2;

import java.util.ArrayList;
import projectpart2.DataPoint;
public abstract class Predictor {
	public abstract ArrayList<DataPoint> readData(String filename);
	public abstract String test(DataPoint data);
	public abstract double getAccuracy(ArrayList<DataPoint> data);
	public abstract double getPrecision(ArrayList<DataPoint> data);

}
