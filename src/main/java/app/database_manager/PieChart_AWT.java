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

Nb d'hospitalisations par service : ok
Nb de maladie contractée par service ? 

Salaire par infirmier ? 
Nombre de chambre dans un service ? 

*/
 
public class PieChart_AWT extends ApplicationFrame 
{
   
    /**
     *
     */
    private static final long serialVersionUID = -7145470278665113308L;

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
         "Hospitalisations par service",   // chart title 
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
      PieChart_AWT demo = new PieChart_AWT("JABARI LEMESLE LAHDIRI");  
      demo.setSize(560,360);    
      RefineryUtilities.centerFrameOnScreen(demo);    
      demo.setVisible(true); 
    }

}

/*
Généraliser cette fonction en faisant un graphe personnalisé.
On envoie tout ce qu'on veut en paramètre et ça lance la fonction et ça crée le graphe
*/