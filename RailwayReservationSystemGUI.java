package oasis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RailwayReservationSystemGUI extends JFrame {
    private JPanel loginPanel;
    private JPanel mainPanel;

    private JTextField nameField;
    private JTextField mobileNumberField;
    private JTextField otpField;
    private JTextArea userDetailsArea;
    private JComboBox<String> trainComboBox;
    private JComboBox<String> classComboBox;
    private JTextField sourceField;
    private JTextField destinationField;
    private JTextField dateField;
    

    private static final int FIRST_CLASS_CAPACITY = 50;
    private static final int SECOND_CLASS_CAPACITY = 100;
    private static int firstClassSeatsBooked = 0;
    private static int secondClassSeatsBooked = 0;
    private int otp; // To store the generated OTP for verification

    public RailwayReservationSystemGUI() {
    	Color lightPink = new Color(255, 182, 193);
        UIManager.put("Panel.background", lightPink);
        UIManager.put("Button.background", lightPink);
        UIManager.put("ComboBox.background", lightPink);
        UIManager.put("TextArea.background", lightPink);
        UIManager.put("TextField.background", lightPink);

        setVisible(true);
        setTitle("Railway Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,250);
        setLocationRelativeTo(null);

        createLoginPanel();
        createMainPanel();

        setVisible(true);
    }

    private void createLoginPanel() {
        loginPanel = new JPanel(new GridLayout(7, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel mobileNumberLabel = new JLabel("Mobile Number:");
        mobileNumberField = new JTextField();

        JButton generateOTPButton = new JButton("Generate OTP");
        JLabel otpLabel = new JLabel("Enter OTP:");
        otpField = new JTextField();
        otpField.setEditable(false);

        JButton verifyOTPButton = new JButton("Verify OTP");
        verifyOTPButton.setEnabled(false); // Enabled after OTP is generated

        generateOTPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateOTP();
                generateOTPButton.setEnabled(false);
                verifyOTPButton.setEnabled(true);
                otpField.setEditable(true);
            }
        });

        verifyOTPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyOTP();
            }
        });

        loginPanel.add(nameLabel);
        loginPanel.add(nameField);
        loginPanel.add(mobileNumberLabel);
        loginPanel.add(mobileNumberField);
        loginPanel.add(new JLabel()); // Empty label for spacing
        loginPanel.add(generateOTPButton);
        loginPanel.add(otpLabel);
        loginPanel.add(otpField);
        loginPanel.add(new JLabel()); // Empty label for spacing
        loginPanel.add(verifyOTPButton);
        loginPanel.add(new JLabel()); // Empty label for spacing
        loginPanel.add(new JLabel()); // Empty label for spacing

        add(loginPanel, BorderLayout.NORTH);
    }

    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());

        JPanel userDetailsPanel = new JPanel(new BorderLayout());
        userDetailsArea = new JTextArea();
        userDetailsArea.setEditable(false);
        userDetailsPanel.add(new JScrollPane(userDetailsArea), BorderLayout.CENTER);

        JPanel reservationPanel = new JPanel(new GridLayout(6, 2));

        JLabel trainLabel = new JLabel("Select Train:");
        String[] trainNames = {
                "Rajdhani Express",
                "Marvel Express",
                "Avantika Express"
        };
        trainComboBox = new JComboBox<>(trainNames);

        JLabel classLabel = new JLabel("Select Class:");
        String[] classOptions = {"First Class", "Second Class"};
        classComboBox = new JComboBox<>(classOptions);

        JLabel sourceLabel = new JLabel("Source:");
        sourceField = new JTextField();

        JLabel destinationLabel = new JLabel("Destination:");
        destinationField = new JTextField();

        JLabel dateLabel = new JLabel("Date (dd/mm/yyyy):");
        dateField = new JTextField();

        JButton bookTicketButton = new JButton("Book Ticket");
        bookTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookTicket();
            }
        });

        reservationPanel.add(trainLabel);
        reservationPanel.add(trainComboBox);
        reservationPanel.add(classLabel);
        reservationPanel.add(classComboBox);
        reservationPanel.add(sourceLabel);
        reservationPanel.add(sourceField);
        reservationPanel.add(destinationLabel);
        reservationPanel.add(destinationField);
        reservationPanel.add(dateLabel);
        reservationPanel.add(dateField);
        reservationPanel.add(new JLabel()); // Empty label for spacing
        reservationPanel.add(bookTicketButton);

        mainPanel.add(userDetailsPanel, BorderLayout.NORTH);
        mainPanel.add(reservationPanel, BorderLayout.CENTER);
        

        add(mainPanel, BorderLayout.CENTER);
        mainPanel.setVisible(false); // Initially hide mainPanel until OTP is verified
    }

    private void generateOTP() {
        String mobileNumber = mobileNumberField.getText();
        if (mobileNumber.matches("\\d{10}")) {
            otp = generateRandomOTP();
            otpField.setText(Integer.toString(otp));
        } else {
            JOptionPane.showMessageDialog(this, "Invalid mobile number. Please enter a 10-digit mobile number.");
        }
    }

    private int generateRandomOTP() {
        Random random = new Random();
        return 100_000 + random.nextInt(900_000);
    }

    private void verifyOTP() {
        try {
            int enteredOTP = Integer.parseInt(otpField.getText());
            if (enteredOTP == otp) {
                JOptionPane.showMessageDialog(this, "OTP verified. Login successful!");
                displayUserDetails();
                mainPanel.setVisible(true);
                loginPanel.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid OTP. Login failed.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid OTP.");
        }
    }

    private void displayUserDetails() {
        String name = nameField.getText();
        String mobileNumber = mobileNumberField.getText();
        userDetailsArea.setText("Name: " + name + "\nMobile Number: " + mobileNumber);
    }

    private void bookTicket() {
        // Implementation of ticket booking logic after successful login and OTP verification
        String selectedTrain = trainComboBox.getSelectedItem().toString();
        String selectedClass = classComboBox.getSelectedItem().toString();
        String source = sourceField.getText();
        String destination = destinationField.getText();
        String date = dateField.getText();

        // Further implementation to book the ticket and display the ticket number
        // For simplicity, I'll just display a message for now
        String ticketNumber = generateRandomTicketNumber();
        JOptionPane.showMessageDialog(this, "Ticket booked successfully!\n" +
                "Ticket Number: " + ticketNumber + "\n" +
                "Train: " + selectedTrain + "\n" +
                "Class: " + selectedClass + "\n" +
                "Source: " + source + "\n" +
                "Destination: " + destination + "\n" +
                "Date: " + date);
    }

    private String generateRandomTicketNumber() {
        Random random = new Random();
        return "TICKET" + random.nextInt(1000000);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RailwayReservationSystemGUI();
            }
        });
    }
}
