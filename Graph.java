package projectPart3;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Graph extends JPanel {
	
    private static final long serialVersionUID = 1L;
    private int labelPadding = 40;
    private Color lineColor = new Color(255, 255, 254);

    // TODO: Add point colors for each type of data point
    private Color pointColor = new Color(255, 0, 255);
    private Color blue = new Color(0, 0, 255);
    private Color cyan = new Color(0, 255, 255);
    private Color yellow = new Color(255, 255,0);
    private Color red = new Color(255, 0, 0);

    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);

    // TODO: Change point width as needed
    private static int pointWidth = 10;

    // Number of grids and the padding width
    private int numXGridLines = 6;
    private int numYGridLines = 6;
    private int padding = 40;

    private ArrayList<DataPoint> data;

    // TODO: Add a private KNNPredictor variable
    private static KNNPredictor obj;
    
    	
	/**
	 * Constructor method
	 */
    public Graph(int K, String fileName) {
        // Generate random data point
    	data = new ArrayList<DataPoint>();
        Random random = new Random();
        int maxDataPoints = 20;
        // Max value of f1 and f2 that is arbitrarily set
        int maxF1 = 8;
        int maxF2 = 300;

        // Generates random DataPoints
        obj = new KNNPredictor(3,0,0);
        
        obj.readData(fileName);
        
        this.data = obj.readData(fileName);
        
        // TODO: Remove the above logic where random data is generated
        // TODO: instantiate the KNNPredictor variable
        // TODO: Run readData using input filename to split the data to test and training
        // TODO: Set this.data as the output of readData
    }
        
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double minF1 = getMinF1Data();
        double maxF1 = getMaxF1Data();
        double minF2 = getMinF2Data();
        double maxF2 = getMaxF2Data();

        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - 
        		labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLUE);

        double yGridRatio = (maxF2 - minF2) / numYGridLines;
        for (int i = 0; i < numYGridLines + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 -
            		labelPadding)) / numYGridLines + padding + labelPadding);
            int y1 = y0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = String.format("%.2f", (minF2 + (i * yGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 6, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        double xGridRatio = (maxF1 - minF1) / numXGridLines;
        for (int i = 0; i < numXGridLines + 1; i++) {
            int y0 = getHeight() - padding - labelPadding;
            int y1 = y0 - pointWidth;
            int x0 = i * (getWidth() - padding * 2 - labelPadding) / (numXGridLines) + padding + labelPadding;
            int x1 = x0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                g2.setColor(Color.BLACK);
                String xLabel = String.format("%.2f", (minF1 + (i * xGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(xLabel);
                g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // Draw the main axis
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() -
        		padding, getHeight() - padding - labelPadding);

        // Draw the points
        paintPoints(g2, minF1, maxF1, minF2, maxF2);
    }

    private void paintPoints(Graphics2D g2, double minF1, double maxF1, double minF2, double maxF2) {
        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        double xScale = ((double) getWidth() - (3 * padding) - labelPadding) /(maxF1 - minF1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxF2 - minF2);
        g2.setStroke(oldStroke);
        for (int i = 0; i < data.size(); i++) {
            int x1 = (int) ((data.get(i).getF1() - minF1) * xScale + padding + labelPadding);
            int y1 = (int) ((maxF2 - data.get(i).getF2()) * yScale + padding);
            int x = x1 - pointWidth / 2;
            int y = y1 - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;

            // TODO: Depending on the type of data and how it is tested, change color here.
            // You need to test your data here using the model to obtain the test value 
            // and compare against the true label.
            // // Note that depending on how you implemented "test" method, you may need to 
            // modify KNNPredictor to store the output from readData.
            // You can also optimize further to compute accuracy and precision in a single
            // iteration.
            String label = obj.test(data.get(i));
            if (i % 2 == 0) {
                g2.setColor(pointColor);
            } else {
                g2.setColor(red);
            }
			 if (label.equals("1") && data.get(i).getLabel().equals("1")) {
				 g2.setColor(blue);
			 }
			 else if (label.equals("1") && data.get(i).getLabel().equals("0")) {
				 g2.setColor(cyan);
			 }
			 else if (label.equals("0") && data.get(i).getLabel().equals("1")) {
				 g2.setColor(yellow);	 
			 }
			 else if (label.equals("0") && data.get(i).getLabel().equals("0")) {
				 g2.setColor(red);
			 }	
         
            
            g2.fillOval(x, y, ovalW, ovalH);
        }

    }

    /*
     * @Return the min values
     */
    private double getMinF1Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getF1());
        }
        return minData;
    }

    private double getMinF2Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getF2());
        }
        return minData;
    }


    /*
     * @Return the max values;
     */
    private double getMaxF1Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getF1());
        }
        return maxData;
    }

    private double getMaxF2Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getF2());
        }
        return maxData;
    }

    /* Mutator */
    public void setData(ArrayList<DataPoint> data) {
        this.data = data;
        invalidate();
        this.repaint();
    }

    /* Accessor */
    public List<DataPoint> getData() {
        return data;
    }

//    /*  Run createAndShowGui in the main method, where we create the frame too and pack it in the panel*/
//    private static void createAndShowGui(List<DataPoint> testData, List<DataPoint> trainData) {
//
//
//	    /* Main panel */
//        Graph mainPanel = new Graph(7, "titanic.csv");
//
//        // Feel free to change the size of the panel
//        mainPanel.setPreferredSize(new Dimension(700, 600));
//
//        /* creating the frame */
//        JFrame frame = new JFrame("CS 112 Lab Part 3");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(mainPanel);
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
    
      
//    /* The main method runs createAndShowGui*/
//    public static void main(String[] args) {
//      SwingUtilities.invokeLater(new Runnable() {
//         public void run() {
//            // Generate random data point
//            List<DataPoint> data = new ArrayList<>();
//            Random random = new Random();
//            int maxDataPoints = 20;
//            // Max value of f1 and f2 that is arbitrarily set
//            int maxF1 = 8;
//            int maxF2 = 300;

//            // Generates random DataPoints
//            for (int i = 0; i < maxDataPoints; i++) {
//                double f1 = (random.nextDouble() * maxF1);
//                double f2 = (random.nextDouble() * maxF2);
//                data.add(new DataPoint("Random", true, f1, f2));
//            }
            
//            // TODO: Change the above logic retrieve the data from titanic.csv
//            // Split the data to test and training
//            obj = new KNNPredictor(3, 0, 0);
//            ArrayList <DataPoint> graph = obj.readData("titanic.csv");
//            List<DataPoint> trainData =  graph.subList(0, 1000);
//            List<DataPoint> testData = graph.subList(1000, graph.size());
//
//            // TODO: Pass in the testData and trainData separately 
//            // Be careful with the order of the variables.
//            createAndShowGui(testData, trainData);
            
    private static void createAndShowGui(int K, String fileName) {


	    /* Main panel */
        Graph mainPanel = new Graph(K, fileName);

        // Feel free to change the size of the panel
        mainPanel.setPreferredSize(new Dimension(600, 700));

        /* creating the frame */
        JFrame frame = new JFrame("CS 112 Lab Part 3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        //KNNPredictor obj = new KNNPredictor(3, 0, 0);
		// Read titanic.csv into an ArrayList of DataPoints as using readData. Make sure to split the 
		//data into train and test using the random number generator.
		//ArrayList <DataPoint> data = obj.readData("titanic.csv");
//		for(DataPoint point: data) {
//			System.out.println(point.getIsTest());
        DecimalFormat decFormat = new DecimalFormat("#%");
		System.out.println("Accuracy:" + (decFormat.format(obj.getAccuracy(mainPanel.data))));
		System.out.println("Precision:" + (decFormat.format(obj.getPrecision(mainPanel.data))));

		JLabel accuracy = new JLabel("Accuracy:" + (decFormat.format(obj.getAccuracy(mainPanel.data))));
		frame.add(accuracy);
		JLabel precision = new JLabel("Precision:" + (decFormat.format(obj.getPrecision(mainPanel.data))));
		frame.add(precision);
        frame.pack();
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(new GridLayout(3,3));
        frame.setSize(800,800);
        //extra credit 
        JPanel point = new JPanel();
        JLabel label = new JLabel();
        point.add(label);
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        MouseListenerUpdated coord = new MouseListenerUpdated(label);
        mainPanel.addMouseListener(coord);
        
        //Project Part 4
        //frame.setLayout(new BorderLayout());
        
//		TODO: 1. Below the graph, first add a JLabel that states 
//		“Choose the majority value” and place it in the center. 
	//	l = new JLabel();
//	    l.setText("Choose the majority value");
//		l.setHorizontalAlignment(JLabel.CENTER);
   
	    JPanel p = new JPanel(new BorderLayout());
	    JLabel majvalue = new JLabel("Choose the majority value", JLabel.CENTER);
        
        majvalue.setHorizontalAlignment(JLabel.CENTER);
        majvalue.setAlignmentX(Component.CENTER_ALIGNMENT);
        majvalue.setVerticalAlignment(SwingConstants.CENTER);
        
        //majvalue.setBorder(BorderFactory.createLineBorder(Color.black));
        //majvalue.setVerticalTextPosition(JLabel.SOUTH);
        Border border = BorderFactory.createLineBorder(Color.ORANGE);
        majvalue.setBorder(border);
        frame.add(majvalue, BorderLayout.SOUTH);

        //p.add(mainPanel, BorderLayout.SOUTH);
        //p.add(majvalue, JLabel.CENTER);
	    p.add(mainPanel, BorderLayout.NORTH);
	    //p.add(new JLabel("Choose the majority value", JLabel.CENTER));
	    //frame.add(p);
        JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL,
                2, 25, 5);
        framesPerSecond.setMajorTickSpacing(5);
        framesPerSecond.setMinorTickSpacing(1);
        framesPerSecond.setPaintTicks(true);
        framesPerSecond.setPaintLabels(true);
        framesPerSecond.setSnapToTicks(true);
        frame.add(framesPerSecond);
        //JButton runtest = new JButton("Run Test");
       // frame.add(runtest);
        JPanel panel = new JPanel();
	    panel.add(framesPerSecond, new JLabel(BorderLayout.SOUTH));
	    frame.add(panel);
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    
//	    TODO: 3.Create a JButton below that says “Run Test”
	    JPanel southPanel = new JPanel(new BorderLayout());
	    JButton b = new JButton("Run Test");  
//	    b.setBounds(50,100,95,30); 
	    southPanel.add(b, BorderLayout.CENTER);
	    mainPanel.add(southPanel, BorderLayout.CENTER);
//	    frame.add(b);  
//	    frame.setLayout(null); 
	    frame.add(p);
	   

        
     

        
        

        

//        JPanel variable = new JPanel(null);
//        JLabel majvalue2 = new JLabel("Choose the majority value");
//        frame.add(majvalue2);
//        Dimension size = majvalue2.getPreferredSize();
//        majvalue2.setBounds(100, 100, size.width, size.height);
//
//           frame.setSize(300, 200);
//           frame.setVisible(true);

    }
//    public void actionPerformed(ActionEvent e) {
//        if ("disable".equals(e.getActionCommand())) {
//            b2.setEnabled(false);
//            b1.setEnabled(false);
//            b3.setEnabled(true);
//        } else {
//            b2.setEnabled(true);
//            b1.setEnabled(true);
//            b3.setEnabled(false);
//        }
//    }
	public static void main(String[] args) {
	     int K = 7; // A value of K selected    
	        String fileName = ("titanic.csv"); // TODO: Change this to titanic.csv
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGui(K, fileName);
	            }
	        });
	}
}

 



