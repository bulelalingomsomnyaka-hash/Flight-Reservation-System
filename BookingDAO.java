import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class BookingDAO {

   public static boolean saveBooking(String username, String name, String flightNo, String from,
                                  String to, String seat, int price,
                                  String date, String dep, String arr, String gate) {
    String sql = "INSERT INTO bookings (username, passenger, flight_no, from_city, to_city, seat, price, "
               + "travel_date, dep_time, arr_time, gate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, username);
        ps.setString(2, name);
        ps.setString(3, flightNo);
        ps.setString(4, from);
        ps.setString(5, to);
        ps.setString(6, seat);
        ps.setInt(7, price);
        ps.setString(8, date);
        ps.setString(9, dep);
        ps.setString(10, arr);
        ps.setString(11, gate);
        ps.executeUpdate();
        return true;
    } catch (SQLException ex) {
        System.out.println("saveBooking error: " + ex.getMessage());
        return false;
    }
}
    public static ArrayList<String> getBookingsForUser(String username) {
    ArrayList<String> list = new ArrayList<>();
    String sql = "SELECT passenger, flight_no, from_city, to_city, seat, "
               + "travel_date, dep_time, arr_time, gate "
               + "FROM bookings WHERE username = ? ORDER BY id";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, username);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(buildTicket(
                        rs.getString("passenger"), rs.getString("flight_no"),
                        rs.getString("from_city"), rs.getString("to_city"),
                        rs.getString("seat"), rs.getString("travel_date"),
                        rs.getString("dep_time"), rs.getString("arr_time"),
                        rs.getString("gate")));
            }
        }
    } catch (SQLException ex) {
        System.out.println("getBookingsForUser error: " + ex.getMessage());
    }
    return list;
}
    
    public static ArrayList<String> getBookedSeats(String flightNo) {
        ArrayList<String> booked = new ArrayList<>();
        String sql = "SELECT seat FROM bookings WHERE flight_no = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, flightNo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    booked.add(rs.getString("seat"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("getBookedSeats error: " + ex.getMessage());
        }
        return booked;
    }

    public static boolean validateLogin(String username, String password) {
        String sql = "SELECT id FROM users WHERE username = ? AND password = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            System.out.println("validateLogin error: " + ex.getMessage());
            return false;
        }
    }

    /** Returns "OK", "EXISTS" (username taken) or "ERROR". */
    public static String registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return "OK";
        } catch (SQLIntegrityConstraintViolationException ex) {
            return "EXISTS";
        } catch (SQLException ex) {
            System.out.println("registerUser error: " + ex.getMessage());
            return "ERROR";
        }
    }
    
    public static String buildTicket(String name, String flightNo, String from, String to,
                                 String seat, String date, String dep, String arr, String gate) {
    return "Name: " + name + "\nFlight Number: " + flightNo + "\nFrom: " + from + " \nTo: " + to
            + " \nSeat Number: " + seat + " \nDate: " + date + " \n"
            + "Depature time: " + dep + " \nArrival time: " + arr + " \nGate Number: " + gate;
}
    
}