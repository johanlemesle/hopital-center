package app.database_manager;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

@WebServlet(name = "DoChart", urlPatterns = { "/DoChart" })
public class DoChart extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 7719994402332011409L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("image/png");

        OutputStream outputStream = response.getOutputStream();

        JFreeChart chart = getChart();
        int width = 500;
        int height = 350;

        ChartUtils.writeChartAsPNG(outputStream, chart, width, height);
    }

    public JFreeChart getChart() {

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Croatia", 22);
        dataset.setValue("Bohemia", 34);
        dataset.setValue("Bulgaria", 18);
        dataset.setValue("Spain", 5);
        dataset.setValue("Others", 21);

        JFreeChart chart = ChartFactory.createPieChart("Popular destinations", (PieDataset) dataset, true, false,
                false);

        chart.setBorderVisible(false);

        return chart;
    }
}