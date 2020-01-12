package app.database_manager;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 

public class BarChart extends ApplicationFrame {
   
   public BarChart( String applicationTitle , String chartTitle ) {
      super( applicationTitle );        
      JFreeChart barChart = ChartFactory.createBarChart(
         chartTitle,           
         "Infirmiers",            
         "Salaire",            
         createDataset(),          
         PlotOrientation.VERTICAL,           
         true, true, false);
         
      ChartPanel chartPanel = new ChartPanel( barChart );        
      chartPanel.setPreferredSize(new java.awt.Dimension(560,367));        
      setContentPane( chartPanel ); 
   }
   
//    private CategoryDataset createDataset( ) {
//       final String fiat = "FIAT";        
//       final String audi = "AUDI";        
//       final String ford = "FORD";   

//       final String speed = "Speed";        
//       final String millage = "Millage";        
//       final String userrating = "User Rating";        
//       final String safety = "safety";  

//       final DefaultCategoryDataset dataset = 
//       new DefaultCategoryDataset( );  

//       dataset.addValue( 1.0 , fiat , speed );        
//       dataset.addValue( 3.0 , fiat , userrating );        
//       dataset.addValue( 5.0 , fiat , millage ); 
//       dataset.addValue( 5.0 , fiat , safety );           

//       dataset.addValue( 5.0 , audi , speed );        
//       dataset.addValue( 6.0 , audi , userrating );       
//       dataset.addValue( 10.0 , audi , millage );        
//       dataset.addValue( 4.0 , audi , safety );

//       dataset.addValue( 4.0 , ford , speed );        
//       dataset.addValue( 2.0 , ford , userrating );        
//       dataset.addValue( 3.0 , ford , millage );        
//       dataset.addValue( 6.0 , ford , safety );               

//       return dataset; 
//    }
   
   public static void main( String[ ] args ) {
      BarChart chart = new BarChart("JABARI LEMESLE LAHDIRI", 
         "Salaire par infirmiers");
      chart.pack( );        
      RefineryUtilities.centerFrameOnScreen( chart );        
      chart.setVisible( true ); 
   }
}