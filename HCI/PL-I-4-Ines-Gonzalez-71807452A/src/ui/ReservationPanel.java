package ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.AppController;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

public class ReservationPanel extends JPanel implements Localizable {

    private static final long serialVersionUID = 1L;
    private JLabel lblComments;
    private JTextField textFieldComments;
    private JButton btnModify;
    private JButton btnRemove;
    private JButton btnContinue;
    private JLabel lblTicketsAcc;
    private JScrollPane scrollPane;
    private JList<String> listSummary;
    private DefaultListModel<String> model;
    private AppController controller;
    private MainWindow window;
    private ResourceBundle texts;
    

    public ReservationPanel(AppController controller, MainWindow window) {
        setPreferredSize(new Dimension(800, 600));
        setLayout(null);
        add(getLblComments());
        add(getTextFieldComments());
        add(getBtnModify());
        add(getBtnRemove());
        add(getBtnContinue());
        add(getLblTicketsAcc());
        add(getScrollPane());
        this.controller = controller;
        this.window = window;
        fillSummary();
        updateContinueButtonState();
    }

    private void fillSummary() {
    	DefaultListModel<String> rawModel = controller.getReservationSummary();
    	DefaultListModel<String> translated = new DefaultListModel<>(); 
    	if (texts != null) { 
    		for (int i = 0; i < rawModel.size(); i++) {
    			translated.addElement(translate(rawModel.get(i), texts));
    		} 
    		listSummary.setModel(translated); 
    	} else {
    		listSummary.setModel(rawModel);
    	}
        if (controller.getTicketReservation() == null || controller.getAccommodationReservation() == null) {
            btnRemove.setEnabled(false);
        }
    }

    private JLabel getLblComments() {
        if (lblComments == null) {
            lblComments = new JLabel("");
            lblComments.setLabelFor(getTextFieldComments());
            lblComments.setDisplayedMnemonic('O');
            lblComments.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblComments.setBounds(28, 265, 300, 32);
        }
        return lblComments;
    }

    private JTextField getTextFieldComments() {
        if (textFieldComments == null) {
            textFieldComments = new JTextField();
            textFieldComments.setToolTipText("");
            textFieldComments.setBounds(28, 307, 442, 95);
            textFieldComments.setColumns(10);
        }
        return textFieldComments;
    }

    private JButton getBtnModify() {
        if (btnModify == null) {
            btnModify = new JButton("");
            btnModify.setToolTipText("");
            btnModify.setMnemonic('M');
            btnModify.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    String selected = listSummary.getSelectedValue();
                    if (selected == null) return;

                    if (selected.startsWith(texts.getString("summary.tickets"))) { 
                        window.switchPanel(new ThemeParksPanel(controller, window), "ThemeParks");

                    } else if (selected.startsWith(texts.getString("summary.accommodation"))) { 
                        window.switchPanel(new AccommodationsPanel(controller, window), "Accommodations");
                    }
                }
            });
            btnModify.setFont(new Font("Tahoma", Font.PLAIN, 15));
            btnModify.setBounds(523, 52, 113, 54);
        }
        return btnModify;
    }

    private JButton getBtnRemove() {
        if (btnRemove == null) {
            btnRemove = new JButton("");
            btnRemove.setToolTipText("");
            btnRemove.setMnemonic('R');
            btnRemove.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selected = listSummary.getSelectedValue();
                    if (selected == null) return;

                    if (selected.startsWith(texts.getString("summary.tickets"))) {

                        controller.removeTicketReservation();
                        controller.setSelectedPark(null);
                    } else if (selected.startsWith(texts.getString("summary.accommodation"))) { 
                        controller.removeAccommodationReservation();
                        controller.setSelectedAccommodation(null);
                    }
                    window.switchPanel(new ReservationPanel(controller, window), "Reservation");
                }
            });
            btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 15));
            btnRemove.setBounds(523, 134, 113, 54);
        }
        return btnRemove;
    }

    private JButton getBtnContinue() {
        if (btnContinue == null) {
            btnContinue = new JButton("");
            btnContinue.setToolTipText("");
            btnContinue.setMnemonic('C');
            btnContinue.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    window.switchPanel(new CustomerInformationPanel(controller, window), "CustomerInfo");
                }
            });
            btnContinue.setFont(new Font("Tahoma", Font.PLAIN, 15));
            btnContinue.setBounds(540, 348, 113, 54);
        }
        return btnContinue;
    }

    private JLabel getLblTicketsAcc() {
        if (lblTicketsAcc == null) {
            lblTicketsAcc = new JLabel("");
            lblTicketsAcc.setLabelFor(getScrollPane());
            lblTicketsAcc.setDisplayedMnemonic('T');
            lblTicketsAcc.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblTicketsAcc.setBounds(28, 10, 400, 32);
        }
        return lblTicketsAcc;
    }

    private JScrollPane getScrollPane() {
        if (scrollPane == null) {
            scrollPane = new JScrollPane();
            scrollPane.setBounds(28, 52, 442, 188);
            scrollPane.setViewportView(getListSummary());
        }
        return scrollPane;
    }

    private JList<String> getListSummary() {
        if (listSummary == null) {
            listSummary = new JList<String>();
            listSummary.setToolTipText("");
        }
        return listSummary;
    }

    private void updateContinueButtonState() {
        boolean hasReservations =
                controller.getTicketReservation() != null ||
                controller.getAccommodationReservation() != null;

        btnContinue.setEnabled(hasReservations);
    }
    private String translate(String raw, ResourceBundle texts) {
        for (String key : texts.keySet()) {
            raw = raw.replace(key, texts.getString(key));
        }
        return raw;
    }


    @Override
    public void localize(ResourceBundle texts) {
        
    	this.texts=texts;
        lblComments.setText(texts.getString("reservation.comments"));
        lblComments.setToolTipText(texts.getString("reservation.comments.tooltip"));

        lblTicketsAcc.setText(texts.getString("reservation.summary.title"));
        lblTicketsAcc.setToolTipText(texts.getString("reservation.summary.tooltip"));

        textFieldComments.setToolTipText(texts.getString("reservation.comments.tooltip"));

        btnModify.setText(texts.getString("reservation.btn.modify"));
        btnModify.setToolTipText(texts.getString("reservation.btn.modify.tooltip"));

        btnRemove.setText(texts.getString("reservation.btn.remove"));
        btnRemove.setToolTipText(texts.getString("reservation.btn.remove.tooltip"));

        btnContinue.setText(texts.getString("reservation.btn.continue"));
        btnContinue.setToolTipText(texts.getString("reservation.btn.continue.tooltip"));

        listSummary.setToolTipText(texts.getString("reservation.summary.list.tooltip"));
        fillSummary();
    }
}
