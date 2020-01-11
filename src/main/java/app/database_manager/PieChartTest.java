package app.database_manager;

import java.util.ArrayList;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xml.DatasetTags;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import app.Hopital;

/*

Idées de graphes : 

Nb d'hospitalisations par service : ok
Nb de maladie contractée par service ? 

Salaire par infirmier ? 
Nombre de chambre dans un service ? 

*/
 
public class PieChartTest extends ApplicationFrame 
{
   
    private static final long serialVersionUID = -7145470278665113308L;

    public PieChartTest(String title, PieDataset dataset1) // Constructeur
    {
        super(title);
     setContentPane(createDemoPanel(dataset1));
     }
   

   
   public static JFreeChart createChart(PieDataset dataset) 
   {
      JFreeChart chart = ChartFactory.createPieChart(      
        "Hospitalisation par service", // chart title
         dataset,          // data    
         true,             // include legend   
         true, 
         false);

      return chart;
   }
   
   public JPanel createDemoPanel(PieDataset dataset) 
   {
      JFreeChart chart = createChart(dataset);  
      return new ChartPanel(chart); 
   }

   public static void main(String[ ] args) 
   {

    PieDataset dataset1 = new DefaultPieDataset();
    PieChartTest demo = new PieChartTest("JABARI LEMESLE LAHDIRI", dataset1);
    demo.setSize(560,360);    
    RefineryUtilities.centerFrameOnScreen(demo);    
    demo.setVisible(true); 

    }

    //Construction du tableau de data

    // public static ArrayList<DefaultPieDataset> createDataTab(Array)
    // {
    //     ArrayList<DefaultPieDataset> dataTab = new ArrayList<>();

    //     return dataTab;
    // }

    //    public DefaultPieDataset createDataset() 
//    {
//       DefaultPieDataset dataset = new DefaultPieDataset();    
//        dataset.setValue("Pediatrie",new Double(20));  
//        dataset.setValue("Urgences",new Double(20));   
//        dataset.setValue("Maternite",new Double(100));    
//        dataset.setValue("Radiologie",new Double(10));  
//       return dataset;         
//    }



}
