import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.*;

public class ComboCheckBox implements ActionListener {
    // List of stocks currently available for display in the GUI
    private LinkedList<Stock> stocks;
    private JPanel panel;

    public ComboCheckBox(LinkedList<Stock> stocks) {
        this.stocks = stocks;
        this.setPanel();
    }

    public  LinkedList<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(LinkedList<Stock> stocks) {
        this.stocks = stocks;
    }

    public JPanel getPanel() {
        return panel;
    }

    // Before the function was named GetContent
    public void setPanel() {
        CheckComboStore[] stores = new CheckComboStore[stocks.size()];
        int i = 0;
        for (Stock stock: stocks){
            stores[i] = new CheckComboStore(
                    stock.getFullName() + " (" + stock.getAbbreviation() + ")",
                    stock.isDisplayed());
            i++;
        }

        JComboBox<CheckComboStore> combo = new JComboBox<>(stores);
        combo.setRenderer(new CheckComboRenderer());
        combo.addActionListener(this);
        JPanel panel = new JPanel();
        panel.add(combo);
        this.panel = panel;
    }

    public void actionPerformed(ActionEvent e) {
        //todo
        JComboBox cb = (JComboBox) e.getSource();
        CheckComboStore store = (CheckComboStore) cb.getSelectedItem();
        CheckComboRenderer ccr = (CheckComboRenderer) cb.getRenderer();
        if (store != null) {
            ccr.checkBox.setSelected((store.state = !store.state));
            for(Stock stock: stocks){
                String checkboxLabel = stock.getFullName() + " (" + stock.getAbbreviation() + ")";
                if(checkboxLabel.equals(store.id)){
                    stock.setDisplayed(!stock.isDisplayed());
                }
            }
        }

    }


    static class CheckComboRenderer implements ListCellRenderer {
        JCheckBox checkBox;

        public CheckComboRenderer() {
            checkBox = new JCheckBox();
        }

        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            CheckComboStore store = (CheckComboStore) value;
            checkBox.setText(store.id);
            checkBox.setSelected(store.state);
            checkBox.setBackground(isSelected ? Color.red : Color.white);
            checkBox.setForeground(isSelected ? Color.white : Color.black);
            return checkBox;
        }
    }


    // Class to contain information about checkboxes
   static class CheckComboStore {
        // Stock's name
        String id;
        // Whether stock is displayed
        Boolean state;

        public CheckComboStore(String id, Boolean state) {
            this.id = id;
            this.state = state;
        }
    }

}
