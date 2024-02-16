import java.awt.*;
import java.util.*;
import java.util.List;

import com.formdev.flatlaf.FlatDarkLaf;
import org.apache.log4j.BasicConfigurator;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;

public class Main{
    public static JDateChooser startDateField;
    public static JDateChooser endDateField;
    public static ChartPanel chartPanel;


    public static void main(String[] args) {

        // Setting configuration
        BasicConfigurator.configure();

        // Set theme for the app
        try {UIManager.setLookAndFeel( new FlatDarkLaf() );}
        catch( Exception e ) { e.printStackTrace();}

        // List of all stocks that DJIA index consists of
        LinkedList<Stock> stocks = new LinkedList<>();
        // Main index
        stocks.add(new Stock("Dow Jones Index", "DJIA", true, null));
        // All stocks
        stocks.add(new Stock("3M Company", "MMM", false, Stock.Type.ConsumerGoods));
        stocks.add(new Stock("American Express Company", "AXP", false, Stock.Type.Financial));
        stocks.add(new Stock("Amgen", "AMGN", false, Stock.Type.HealthCare));
        stocks.add(new Stock("Apple Inc.", "AAPL", false, Stock.Type.Technology));
        stocks.add(new Stock("Boeing Company", "BA", false, Stock.Type.Industrial));
        stocks.add(new Stock("Caterpillar", "CAT", false, Stock.Type.Industrial));
        stocks.add(new Stock("Chevron", "CVX", false, Stock.Type.Industrial ));
        stocks.add(new Stock("Cisco Systems, Inc.", "CSCO", false, Stock.Type.Technology));
        stocks.add(new Stock("Coca-Cola Company", "KO", false, Stock.Type.ConsumerGoods));
        stocks.add(new Stock("Dow Inc.", "DOW", false, Stock.Type.Industrial )); // NOT DOW Jones Index
        stocks.add(new Stock("Goldman Sachs Group, Inc.", "GS", false, Stock.Type.Financial));
        stocks.add(new Stock("Home Depot, Inc.", "HD", false, Stock.Type.ConsumerGoods));
        stocks.add(new Stock("Honeywell", "HON", false, Stock.Type.Technology));
        stocks.add(new Stock("Intel Corporation", "INTC", false, Stock.Type.Technology));
        stocks.add(new Stock("International Business Machines Corporation", "IBM", false, Stock.Type.Technology));
        stocks.add(new Stock("Johnson & Johnson", "JNJ", false,  Stock.Type.HealthCare));
        stocks.add(new Stock("JP Morgan Chase & Co.", "JPM", false, Stock.Type.Financial));
        stocks.add(new Stock("McDonald's Corporation", "MCD", false, Stock.Type.ConsumerGoods));
        stocks.add(new Stock("Merck & Co., Inc.", "MRK", false, Stock.Type.HealthCare));
        stocks.add(new Stock("Microsoft Corporation", "MSFT", false, Stock.Type.Technology));
        stocks.add(new Stock("Nike, Inc.", "NKE", false, Stock.Type.ConsumerGoods));
        stocks.add(new Stock("Procter & Gamble Company", "PG", false, Stock.Type.ConsumerGoods));
        stocks.add(new Stock("Salesforce", "CRM", false, Stock.Type.Technology));
        stocks.add(new Stock("Travelers Companies, Inc.", "TRV", false, Stock.Type.Financial));
        stocks.add(new Stock("UnitedHealth Group Incorporated", "UNH", false, Stock.Type.HealthCare));
        stocks.add(new Stock("Verizon Communications Inc.", "VZ", false, Stock.Type.Technology));
        stocks.add(new Stock("Visa Inc.", "V", false, Stock.Type.Financial));
        stocks.add(new Stock("Walgreens Boots Alliance", "WBA", false, Stock.Type.ConsumerGoods));
        stocks.add(new Stock("Walmart Inc.", "WMT", false, Stock.Type.ConsumerGoods));
        stocks.add(new Stock("Walt Disney Company", "DIS", false, Stock.Type.ConsumerGoods));


        // Init the GUI frame
        JFrame jFrame = new JFrame("Dow Inc.");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // main panel
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        // Create the userPanel for interacting with the app
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        
        /*      Create the field to choose what is displayed on the diagram
        RELEVANT to define it before it's used below in ActionListeners
        It MUST be ADDED as the last component so that altering it with filters
        may be simplified */
        ComboCheckBox comboCheckBox = new ComboCheckBox(stocks);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        // Create the reset button
        JButton resetButton = new JButton("Reset");

        // Action Listener for reset button
        resetButton.addActionListener(e -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_YEAR, -30);
            startDateField.setDate(calendar.getTime());

            // Reset end date to today
            endDateField.setDate(new Date());

            ArrayList<String> arrayDisplayed = new ArrayList<>();
            arrayDisplayed.add("DJIA");
            JFreeChart chart = YahooFetcher.GenerateChart(arrayDisplayed, startDateField.getCalendar(),endDateField.getCalendar());
            chartPanel.setChart(chart);
            chartPanel.revalidate();
        });

        //Create button that will display Graph for set Date
        JButton displayButton= new JButton("Display with new data");

        displayButton.addActionListener(e -> {
            // Add marked stocks to the chart
            ArrayList<String> arrayDisplayed = new ArrayList<>();
            for (Stock stock : comboCheckBox.getStocks()){
                if(stock.isDisplayed()){
                    arrayDisplayed.add(stock.getAbbreviation());
                }
            }

            //
            //Date date_start = startDate.



            JFreeChart chart = YahooFetcher.GenerateChart(
                    arrayDisplayed,
                    startDateField.getCalendar(),
                    endDateField.getCalendar());
            chartPanel.setChart(chart);
            chartPanel.revalidate();
        });

        buttonPanel.add(displayButton);
        buttonPanel.add(resetButton);
        userPanel.add(buttonPanel);

        // Create the start date field
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        Date startDate = calendar.getTime();
        startDateField = new JDateChooser(startDate);
        // Create the end date field
        Date endDate = new Date();
        JDateChooser jDateChooser = new JDateChooser(endDate);
        jDateChooser.setMaxSelectableDate(Calendar.getInstance().getTime());

        endDateField = jDateChooser;
        // add
        userPanel.add(new JLabel("Start Date:"));
        userPanel.add(startDateField);
        userPanel.add(new JLabel("End Date:"));
        userPanel.add(endDateField);


        // FILTERS
        userPanel.add(new JLabel("Filter for enterprises:"));
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton radio1 = new JRadioButton("Technology");
        JRadioButton radio2 = new JRadioButton("Financial");
        JRadioButton radio3 = new JRadioButton("Industrial");
        JRadioButton radio4 = new JRadioButton("Health Care");
        JRadioButton radio5 = new JRadioButton("Consumer Goods");
        JRadioButton radio6 = new JRadioButton("All", true);
        userPanel.add(radio6);
        userPanel.add(radio1);
        userPanel.add(radio2);
        userPanel.add(radio3);
        userPanel.add(radio4);
        userPanel.add(radio5);
        buttonGroup.add(radio1);
        buttonGroup.add(radio2);
        buttonGroup.add(radio3);
        buttonGroup.add(radio4);
        buttonGroup.add(radio5);
        buttonGroup.add(radio6);


        // Add the comboCheckBox panel
        userPanel.add(new JLabel("Visible:"));
        userPanel.add(comboCheckBox.getPanel());


        // Implementation of filters situated below the main function
        radio1.addActionListener(e -> actionListenerImplementation(
                stocks,
                comboCheckBox,
                Stock.Type.Technology,
                userPanel));
        radio2.addActionListener(e -> actionListenerImplementation(
                stocks,
                comboCheckBox,
                Stock.Type.Financial,
                userPanel));
        radio3.addActionListener(e -> actionListenerImplementation(
                stocks,
                comboCheckBox,
                Stock.Type.Industrial,
                userPanel));
        radio4.addActionListener(e -> actionListenerImplementation(
                stocks,
                comboCheckBox,
                Stock.Type.HealthCare,
                userPanel));
        radio5.addActionListener(e -> actionListenerImplementation(
                stocks,
                comboCheckBox,
                Stock.Type.ConsumerGoods,
                userPanel));
        radio6.addActionListener(e -> {
            // So that DJIA does not appear unwanted
            for(Stock stock: stocks){
                if (stock.getAbbreviation().equals("DJIA")){
                    stock.setDisplayed(false);
                }
            }

            comboCheckBox.setStocks(stocks);
            comboCheckBox.setPanel();
            // remove previous select bar
            userPanel.remove(userPanel.getComponentCount()-1);
            // add updated one
            userPanel.add(comboCheckBox.getPanel());
            userPanel.revalidate();
        });


        //GENERATE AND ADD CHART
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.MONTH, -1);
        ArrayList<String> arrayList_ind = new ArrayList<>();
        arrayList_ind.add("DJIA");
        JFreeChart chart2 = YahooFetcher.GenerateChart(arrayList_ind,from,to);
        chartPanel = new ChartPanel(chart2);

        // dodanie
        mainPanel.add(userPanel);
        mainPanel.add(chartPanel);
        jFrame.add(mainPanel, BorderLayout.CENTER);

        // Display the frame
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    private static void actionListenerImplementation(LinkedList<Stock> stocks,
                                              ComboCheckBox comboCheckBox,
                                              Stock.Type type, JPanel userPanel){
        List<Stock> filtered = stocks.stream()
                .filter(stock -> stock.getEnterpriseType() == type)
                .toList();

        for(Stock stock: stocks){
            stock.setDisplayed(filtered.contains(stock));
        }

        comboCheckBox.setStocks(new LinkedList<>(filtered));
        comboCheckBox.setPanel();
        // remove previous select bar
        userPanel.remove(userPanel.getComponentCount()-1);
        // add updated one
        userPanel.add(comboCheckBox.getPanel());
        userPanel.revalidate();
    }

}
