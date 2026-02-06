package ui;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controller.AppController;
import model.Customer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

public class CustomerInformationPanel extends JPanel implements Localizable {

    private static final long serialVersionUID = 1L;
    private JButton btnFinish;
    private JLabel lblFirstName;
    private JLabel lblLastName;
    private JLabel lblIDNumber;
    private JTextField textFieldFirstName;
    private JTextField textFieldLastName;
    private JTextField textFieldIdNumber;
    private JLabel lblTitle;
    private AppController controller;
    private MainWindow window;
    private ResourceBundle texts;

    public CustomerInformationPanel(AppController controller, MainWindow window) {
        setPreferredSize(new Dimension(800, 600));
        setLayout(null);
        add(getBtnFinish());
        add(getLblFirstName());
        add(getLblLastName());
        add(getLblIDNumber());
        add(getTextFieldFirstName());
        add(getTextFieldLastName());
        add(getTextFieldIdNumber());
        add(getLblTitle());
        this.controller = controller;
        this.window = window;
    }

    private JButton getBtnFinish() {
        if (btnFinish == null) {
            btnFinish = new JButton("");
            btnFinish.setToolTipText("");
            btnFinish.setMnemonic('F');
            btnFinish.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    finishBooking();
                }
            });
            btnFinish.setFont(new Font("Tahoma", Font.PLAIN, 15));
            btnFinish.setBounds(522, 359, 132, 52);
        }
        return btnFinish;
    }

    private void finishBooking() {

        String firstName = textFieldFirstName.getText().trim();
        String lastName = textFieldLastName.getText().trim();
        String id = textFieldIdNumber.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, texts.getString("customer.error.empty"),texts.getString("customer.error.title"), 
            		JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!isOnlyLetters(firstName)) {
            JOptionPane.showMessageDialog(this, texts.getString("customer.error.firstname"),texts.getString("customer.error.title"), 
            		JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!isOnlyLetters(lastName)) {
            JOptionPane.showMessageDialog(this, texts.getString("customer.error.lastname"),texts.getString("customer.error.title"),
            		JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!isValidDNI(id)) {
            JOptionPane.showMessageDialog(this, texts.getString("customer.error.id"),texts.getString("customer.error.title"), 
            		JOptionPane.ERROR_MESSAGE);
            return;
        }

        Customer c = new Customer(firstName, lastName, id);
        controller.setCustomer(c);

        controller.createBooking();

        window.switchPanel(new ReservationSummaryPanel(controller, window), "ReservationSummary");
    }

    private boolean isOnlyLetters(String text) {
        return text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }

    private boolean isValidDNI(String dni) {
        return dni.matches("\\d{8}[A-Za-z]");
    }

    private JLabel getLblFirstName() {
        if (lblFirstName == null) {
            lblFirstName = new JLabel("");
            lblFirstName.setLabelFor(getTextFieldFirstName());
            lblFirstName.setDisplayedMnemonic('n');
            lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 20));
            lblFirstName.setBounds(70, 99, 149, 22);
        }
        return lblFirstName;
    }

    private JLabel getLblLastName() {
        if (lblLastName == null) {
            lblLastName = new JLabel("");
            lblLastName.setLabelFor(lblLastName);
            lblLastName.setDisplayedMnemonic('L');
            lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 20));
            lblLastName.setBounds(70, 180, 149, 22);
        }
        return lblLastName;
    }

    private JLabel getLblIDNumber() {
        if (lblIDNumber == null) {
            lblIDNumber = new JLabel("");
            lblIDNumber.setLabelFor(getTextFieldIdNumber());
            lblIDNumber.setDisplayedMnemonic('I');
            lblIDNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
            lblIDNumber.setBounds(70, 276, 149, 22);
        }
        return lblIDNumber;
    }

    private JTextField getTextFieldFirstName() {
        if (textFieldFirstName == null) {
            textFieldFirstName = new JTextField();
            textFieldFirstName.setToolTipText("");
            textFieldFirstName.setBounds(248, 92, 275, 45);
            textFieldFirstName.setColumns(10);
        }
        return textFieldFirstName;
    }

    private JTextField getTextFieldLastName() {
        if (textFieldLastName == null) {
            textFieldLastName = new JTextField();
            textFieldLastName.setToolTipText("");
            textFieldLastName.setColumns(10);
            textFieldLastName.setBounds(248, 173, 275, 45);
        }
        return textFieldLastName;
    }

    private JTextField getTextFieldIdNumber() {
        if (textFieldIdNumber == null) {
            textFieldIdNumber = new JTextField();
            textFieldIdNumber.setToolTipText("");
            textFieldIdNumber.setColumns(10);
            textFieldIdNumber.setBounds(248, 269, 275, 45);
        }
        return textFieldIdNumber;
    }

    private JLabel getLblTitle() {
        if (lblTitle == null) {
            lblTitle = new JLabel("");
            lblTitle.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
            lblTitle.setBounds(30, 31, 300, 22);
        }
        return lblTitle;
    }

    @Override
    public void localize(ResourceBundle texts) {
        this.texts = texts;

        lblTitle.setText(texts.getString("customer.title"));

        lblFirstName.setText(texts.getString("customer.firstname"));
        lblFirstName.setToolTipText(texts.getString("customer.firstname.tooltip"));

        lblLastName.setText(texts.getString("customer.lastname"));
        lblLastName.setToolTipText(texts.getString("customer.lastname.tooltip"));

        lblIDNumber.setText(texts.getString("customer.id"));
        lblIDNumber.setToolTipText(texts.getString("customer.id.tooltip"));

        textFieldFirstName.setToolTipText(texts.getString("customer.firstname.tooltip"));
        textFieldLastName.setToolTipText(texts.getString("customer.lastname.tooltip"));
        textFieldIdNumber.setToolTipText(texts.getString("customer.id.tooltip"));

        btnFinish.setText(texts.getString("customer.btn.finish"));
        btnFinish.setToolTipText(texts.getString("customer.btn.finish.tooltip"));
    }
}
