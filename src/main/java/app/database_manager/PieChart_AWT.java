package app.database_manager;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/*
Idées de graphes : 
Nb d'hospitalisations par service
Nb de maladie contractée par service ? 
Salaire par employé ? 
Nombre de chambre dans un service ? 

*/
 
public class PieChart_AWT extends ApplicationFrame 
{
   
   public PieChart_AWT(String title) 
   {
      super(title); 
      setContentPane(createDemoPanel());
   }
   
   private static PieDataset createDataset() 
   {
      DefaultPieDataset dataset = new DefaultPieDataset();
      dataset.setValue("Pediatrie",new Double(20));  
      dataset.setValue("Urgences",new Double(20));   
      dataset.setValue("Maternite",new Double(100));    
      dataset.setValue("Radiologie",new Double(10));  
      return dataset;         
   }
   
   private static JFreeChart createChart(PieDataset dataset) 
   {
      JFreeChart chart = ChartFactory.createPieChart(      
         "Hospitalisatoions par service",   // chart title 
         dataset,          // data    
         true,             // include legend   
         true, 
         false);

      return chart;
   }
   
   public static JPanel createDemoPanel() 
   {
      JFreeChart chart = createChart(createDataset());  
      return new ChartPanel(chart); 
   }

   public static void main(String[ ] args) 
   {
      PieChart_AWT demo = new PieChart_AWT("Hospitalisations par service");  
      demo.setSize(560,360);    
      RefineryUtilities.centerFrameOnScreen(demo);    
      demo.setVisible(true); 
    }

}