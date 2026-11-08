import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SeatSelection extends JDialog {

    private final List<String> availableSeats;   // shared list owned by Flight
    private final String flightNo;// Declaration of flight number
   
    private final String passenger;// Declaration of passenger name

    private JComboBox<String> Avseats;
    private JLabel seatsLeftLabel;//It wil diplay number of seats left
    private boolean updating = false;            // blocks events caused by our own changes
    private String bookedSeat = null;

    //CLASS OF SEAT SELECTION ON THE TYPE OF FLIGHT YOU CHOSE
    public SeatSelection(java.awt.Frame parent, String flightNo, String passenger,
            List<String> availableSeats) {
        super(parent, "Select Seat - " + flightNo, true);
        this.flightNo = flightNo;
        this.passenger = passenger;
        this.availableSeats = availableSeats;
        buildUI();
    }

    private void buildUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

      JLabel header = new JLabel("Flight " + flightNo + " - Passenger: " + passenger);
content.add(header, BorderLayout.NORTH);
        // Fill the combo box with a loop - nothing is added by hand
        Avseats = new JComboBox<>();
        updating = true;
        for (String seat : availableSeats) {
            Avseats.addItem(seat);
        }
        Avseats.setSelectedIndex(-1);            // nothing selected at the start
        updating = false;
        Avseats.addActionListener(ev -> avseatsActionPerformed());
        content.add(Avseats, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout(5, 5));
        seatsLeftLabel = new JLabel();
        updateSeatsLeft();
        bottom.add(seatsLeftLabel, BorderLayout.NORTH);

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(ev -> dispose());
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttons.add(cancel);
        bottom.add(buttons, BorderLayout.SOUTH);
        content.add(bottom, BorderLayout.SOUTH);

        setContentPane(content);
        content.setPreferredSize(new Dimension(320, 190));
        pack();
        setLocationRelativeTo(getOwner());
    }

    private void avseatsActionPerformed() {
        if (updating) {
            return;                             
        }
        Object selected = Avseats.getSelectedItem();
        if (selected == null) {
            return;
        }
        String seat = selected.toString();

        if (!availableSeats.contains(seat)) {  
            updating = true;
            Avseats.removeItem(seat);
            Avseats.setSelectedIndex(-1);
            updating = false;
            JOptionPane.showMessageDialog(this, seat + " has just been taken. Please choose another seat.");
            return;
        }
         //CORNFIRMAION OF YOU WANT THIS SEAT OR NOT THAT YOU ARE BOOKING
        int choice = JOptionPane.showConfirmDialog(this,
                "Book " + seat + " on " + flightNo + " for " + passenger + "?",
                "Confirm Seat", JOptionPane.YES_NO_OPTION);

        updating = true;
        try {
            if (choice == JOptionPane.YES_OPTION) {
                Avseats.removeItem(seat);        // seat disappears from the list
                availableSeats.remove(seat);     // ...and from the flight's seats
                bookedSeat = seat;
                Avseats.setSelectedIndex(-1);
                updateSeatsLeft();
                dispose();
            } else {
                Avseats.setSelectedIndex(-1);    // keep the seat available
            }
        } finally {
            updating = false;
        }
    }
    //UPDATING THE NUMBER OF SEATS THAT ARE LEFT AFTER BOOKING HAS TAKEN PLACE
    private void updateSeatsLeft() {
        seatsLeftLabel.setText(availableSeats.size() + " seats left on " + flightNo);
    }

    
    public String getBookedSeat() {
        return bookedSeat;
    }

    private static String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
