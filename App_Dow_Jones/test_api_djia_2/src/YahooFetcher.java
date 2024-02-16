import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.util.*;
import java.util.List;

public class YahooFetcher {

    public static JFreeChart GenerateChart(ArrayList<String> indicies, Calendar from_date, Calendar to_date){
        //function generates an object JFreeChart based on index and date

        try {
            TimeSeriesCollection dataset = new TimeSeriesCollection();

            for (String abb_ind: indicies){
                Stock stock = YahooFinance.get(abb_ind, from_date, to_date, Interval.DAILY);
                List<HistoricalQuote> historystock = stock.getHistory();
                TimeSeries series = new TimeSeries(abb_ind);
                for (HistoricalQuote quote : historystock) {
                    Date x = quote.getDate().getTime();
                    double y = quote.getClose().doubleValue();
                    series.add(new Millisecond(x), y);
                }
                dataset.addSeries(series);
            }

            return ChartFactory.createTimeSeriesChart(
                    "Stock Index", // chart title
                    "Date", // x-axis label
                    "Value", // y-axis label
                    dataset, // data set
                    true, // include legend
                    true, // tooltips
                    false // urls
            );
        } catch (Exception e) {
            System.out.println("Blad" + e.getMessage());
        }
        return null;
    }

}