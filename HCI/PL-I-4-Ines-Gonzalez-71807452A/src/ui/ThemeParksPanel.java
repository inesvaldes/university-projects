package ui;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.border.EmptyBorder;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import java.awt.Image;
import javax.swing.ImageIcon;

import controller.AppController;
import model.ThemePark;
import model.TicketReservation;
import java.time.LocalDate;
import java.time.ZoneId;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

public class ThemeParksPanel extends JPanel implements Localizable{

    private static final long serialVersionUID = 1L;

    private JPanel panelLeft;
    private JLabel lblSpecialOfferText;
    private JLabel lblParkImg;
    private JComboBox comboBoxParks;
    private JPanel panelRight;
    private JScrollPane scrollPaneInfoPark;
    private JPanel panelFields;
    private JLabel lblAdultTickets;
    private JSpinner spinnerAdultTickets;
    private JLabel lblChildTickets;
    private JSpinner spinnerChildTickets;
    private JLabel lblNumberOfDays;
    private JSpinner spinnerNumberDays;
    private JLabel lblDate;
    private JSpinner spinnerDate;
    private JPanel panelButtons;
    private JButton btnRecommendedAccommodation;
    private JButton btnBook;
    private JButton btnBack;
    private Component verticalStrutAboveText;
    private Component verticalAboveImg;
    private Component verticalStrutAboveCombo;
    private Component verticalStrutNorth;
    private JTextArea textAreaInfoPark;
    private ResourceBundle texts;

    private AppController controller;
    private MainWindow window;

    public ThemeParksPanel(AppController controller, MainWindow window) {
        this.controller = controller;
        this.window = window;

        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout(0, 0));
        add(getPanelLeft(), BorderLayout.WEST);
        add(getPanelRight(), BorderLayout.CENTER);

        initData();
    }

    private void initData() {
        comboBoxParks.removeAllItems();

        for (ThemePark p : controller.getParksForSelectedAccommodation()) {
            comboBoxParks.addItem(p);
        }

        btnRecommendedAccommodation.setEnabled(controller.getAccommodationReservation() == null);
    }

    public void setImageToLabel(JLabel label, String imagePath) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
            Image img = icon.getImage();

            int w = label.getPreferredSize().width;
            int h = label.getPreferredSize().height;

            Image newImg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(newImg));

        } catch (Exception e) {
            System.out.println("Error loading image: " + imagePath);
        }
    }

    private JPanel getPanelLeft() {
        if (panelLeft == null) {
            panelLeft = new JPanel();
            panelLeft.setBorder(new EmptyBorder(20, 20, 20, 10));
            panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
            panelLeft.add(getVerticalStrutNorth());
            panelLeft.add(getLblSpecialOfferText());
            panelLeft.add(getVerticalStrutAboveText());
            panelLeft.add(getLblParkImg());
            panelLeft.add(getVerticalAboveImg());
            panelLeft.add(getComboBoxParks());
            panelLeft.add(getVerticalStrutAboveCombo());
        }
        return panelLeft;
    }

    private JLabel getLblSpecialOfferText() {
        if (lblSpecialOfferText == null) {
            lblSpecialOfferText = new JLabel("");
            lblSpecialOfferText.setToolTipText("");
            lblSpecialOfferText.setPreferredSize(new Dimension(250, 50));
            lblSpecialOfferText.setMinimumSize(new Dimension(100, 20));
            lblSpecialOfferText.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblSpecialOfferText.setHorizontalAlignment(SwingConstants.CENTER);
            lblSpecialOfferText.setForeground(Color.RED);
        }
        return lblSpecialOfferText;
    }

    private JLabel getLblParkImg() {
        if (lblParkImg == null) {
            lblParkImg = new JLabel("");
            lblParkImg.setToolTipText("");
            lblParkImg.setPreferredSize(new Dimension(350, 250));
            lblParkImg.setMinimumSize(new Dimension(150, 150));
            lblParkImg.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        return lblParkImg;
    }

    private JComboBox getComboBoxParks() {
        if (comboBoxParks == null) {
            comboBoxParks = new JComboBox();
            comboBoxParks.setToolTipText("");
            comboBoxParks.setPreferredSize(new Dimension(350, 30));
            comboBoxParks.setAlignmentX(Component.CENTER_ALIGNMENT);

            comboBoxParks.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ThemePark p = (ThemePark) comboBoxParks.getSelectedItem();
                    if (p == null) return;

                    controller.setSelectedPark(p);

                   
                    String path = controller.getParkImagePath(p);
                    setImageToLabel(lblParkImg, path);

                    btnRecommendedAccommodation.setEnabled(controller.getAccommodationReservation() == null);
                    if(texts!=null) {
                    	localize(texts);
                    }
                }
            });
        }
        return comboBoxParks;
    }

    private JPanel getPanelRight() {
        if (panelRight == null) {
            panelRight = new JPanel();
            panelRight.setBorder(new EmptyBorder(20, 10, 20, 20));
            panelRight.setLayout(new BorderLayout(0, 0));
            panelRight.add(getScrollPaneInfoPark(), BorderLayout.NORTH);
            panelRight.add(getPanelFields(), BorderLayout.CENTER);
            panelRight.add(getPanelButtons(), BorderLayout.SOUTH);
        }
        return panelRight;
    }

    private JScrollPane getScrollPaneInfoPark() {
        if (scrollPaneInfoPark == null) {
            scrollPaneInfoPark = new JScrollPane(getTextAreaInfoPark());
            scrollPaneInfoPark.setPreferredSize(new Dimension(250, 200));
        }
        return scrollPaneInfoPark;
    }

    private JTextArea getTextAreaInfoPark() {
        if (textAreaInfoPark == null) {
            textAreaInfoPark = new JTextArea();
            textAreaInfoPark.setEditable(false);
            textAreaInfoPark.setToolTipText("");
            textAreaInfoPark.setWrapStyleWord(true);
            textAreaInfoPark.setLineWrap(true);
        }
        return textAreaInfoPark;
    }

    private JPanel getPanelFields() {
        if (panelFields == null) {
            panelFields = new JPanel();
            GridBagLayout gbl_panelFields = new GridBagLayout();
            gbl_panelFields.columnWidths = new int[] {0, 0, 0};
            gbl_panelFields.rowHeights = new int[] {0, 0, 0, 0};
            gbl_panelFields.columnWeights = new double[] {0.0, 0.0, 1.0};
            gbl_panelFields.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0};
            panelFields.setLayout(gbl_panelFields);

            GridBagConstraints gbc_lblAdultTickets = new GridBagConstraints();
            gbc_lblAdultTickets.anchor = GridBagConstraints.WEST;
            gbc_lblAdultTickets.insets = new Insets(5, 5, 5, 5);
            gbc_lblAdultTickets.gridx = 1;
            gbc_lblAdultTickets.gridy = 0;
            panelFields.add(getLblAdultTickets(), gbc_lblAdultTickets);

            GridBagConstraints gbc_spinnerAdultTickets = new GridBagConstraints();
            gbc_spinnerAdultTickets.insets = new Insets(5, 5, 5, 0);
            gbc_spinnerAdultTickets.gridx = 2;
            gbc_spinnerAdultTickets.gridy = 0;
            panelFields.add(getSpinnerAdultTickets(), gbc_spinnerAdultTickets);

            GridBagConstraints gbc_lblChildTickets = new GridBagConstraints();
            gbc_lblChildTickets.anchor = GridBagConstraints.WEST;
            gbc_lblChildTickets.insets = new Insets(5, 5, 5, 5);
            gbc_lblChildTickets.gridx = 1;
            gbc_lblChildTickets.gridy = 1;
            panelFields.add(getLblChildTickets(), gbc_lblChildTickets);

            GridBagConstraints gbc_spinnerChildTickets = new GridBagConstraints();
            gbc_spinnerChildTickets.insets = new Insets(5, 5, 5, 0);
            gbc_spinnerChildTickets.gridx = 2;
            gbc_spinnerChildTickets.gridy = 1;
            panelFields.add(getSpinnerChildTickets(), gbc_spinnerChildTickets);

            GridBagConstraints gbc_lblNumberOfDays = new GridBagConstraints();
            gbc_lblNumberOfDays.anchor = GridBagConstraints.WEST;
            gbc_lblNumberOfDays.insets = new Insets(5, 5, 5, 5);
            gbc_lblNumberOfDays.gridx = 1;
            gbc_lblNumberOfDays.gridy = 2;
            panelFields.add(getLblNumberOfDays(), gbc_lblNumberOfDays);

            GridBagConstraints gbc_spinnerNumberDays = new GridBagConstraints();
            gbc_spinnerNumberDays.insets = new Insets(5, 5, 5, 0);
            gbc_spinnerNumberDays.gridx = 2;
            gbc_spinnerNumberDays.gridy = 2;
            panelFields.add(getSpinnerNumberDays(), gbc_spinnerNumberDays);

            GridBagConstraints gbc_lblDate = new GridBagConstraints();
            gbc_lblDate.anchor = GridBagConstraints.WEST;
            gbc_lblDate.insets = new Insets(5, 5, 0, 5);
            gbc_lblDate.gridx = 1;
            gbc_lblDate.gridy = 3;
            panelFields.add(getLblDate(), gbc_lblDate);

            GridBagConstraints gbc_spinnerDate = new GridBagConstraints();
            gbc_spinnerDate.insets = new Insets(5, 5, 0, 0);
            gbc_spinnerDate.gridx = 2;
            gbc_spinnerDate.gridy = 3;
            panelFields.add(getSpinnerDate(), gbc_spinnerDate);
        }
        return panelFields;
    }

    private JLabel getLblAdultTickets() {
        if (lblAdultTickets == null) {
            lblAdultTickets = new JLabel("");
            lblAdultTickets.setLabelFor(getSpinnerAdultTickets());
            lblAdultTickets.setDisplayedMnemonic('A');
            lblAdultTickets.setFont(new Font("Tahoma", Font.PLAIN, 15));
        }
        return lblAdultTickets;
    }

    private JSpinner getSpinnerAdultTickets() {
        if (spinnerAdultTickets == null) {
            spinnerAdultTickets = new JSpinner();
            spinnerAdultTickets.setToolTipText("");
            spinnerAdultTickets.setModel(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));
        }
        return spinnerAdultTickets;
    }

    private JLabel getLblChildTickets() {
        if (lblChildTickets == null) {
            lblChildTickets = new JLabel("");
            lblChildTickets.setLabelFor(getSpinnerChildTickets());
            lblChildTickets.setDisplayedMnemonic('C');
            lblChildTickets.setFont(new Font("Tahoma", Font.PLAIN, 15));
        }
        return lblChildTickets;
    }

    private JSpinner getSpinnerChildTickets() {
        if (spinnerChildTickets == null) {
            spinnerChildTickets = new JSpinner();
            spinnerChildTickets.setToolTipText("");
            spinnerChildTickets.setModel(new javax.swing.SpinnerNumberModel(0, 0, 99, 1));
        }
        return spinnerChildTickets;
    }

    private JLabel getLblNumberOfDays() {
        if (lblNumberOfDays == null) {
            lblNumberOfDays = new JLabel("");
            lblNumberOfDays.setLabelFor(getSpinnerNumberDays());
            lblNumberOfDays.setDisplayedMnemonic('N');
            lblNumberOfDays.setFont(new Font("Tahoma", Font.PLAIN, 15));
        }
        return lblNumberOfDays;
    }

    private JSpinner getSpinnerNumberDays() {
        if (spinnerNumberDays == null) {
            spinnerNumberDays = new JSpinner();
            spinnerNumberDays.setToolTipText("");
            spinnerNumberDays.setModel(new javax.swing.SpinnerNumberModel(1, 1, 30, 1));
        }
        return spinnerNumberDays;
    }

    private JLabel getLblDate() {
        if (lblDate == null) {
            lblDate = new JLabel("");
            lblDate.setLabelFor(getSpinnerDate());
            lblDate.setDisplayedMnemonic('D');
            lblDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
        }
        return lblDate;
    }

    private JSpinner getSpinnerDate() {
        if (spinnerDate == null) {
            spinnerDate = new JSpinner();
            spinnerDate.setToolTipText("");
            spinnerDate.setModel(new SpinnerDateModel(new Date(1768172400000L), new Date(1768172400000L), null, Calendar.DAY_OF_MONTH));
            JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerDate, "dd/MM/yyyy");
            spinnerDate.setEditor(editor);
        }
        return spinnerDate;
    }

    private JPanel getPanelButtons() {
        if (panelButtons == null) {
            panelButtons = new JPanel();
            FlowLayout flow = new FlowLayout(FlowLayout.RIGHT);
            panelButtons.setLayout(flow);
            panelButtons.add(getBtnRecommendedAccommodation());
            panelButtons.add(getBtnBook());
            panelButtons.add(getBtnBack());
        }
        return panelButtons;
    }

    private JButton getBtnRecommendedAccommodation() {
        if (btnRecommendedAccommodation == null) {
            btnRecommendedAccommodation = new JButton("");
            btnRecommendedAccommodation.setToolTipText("");
            btnRecommendedAccommodation.setMnemonic(KeyEvent.VK_R);
            btnRecommendedAccommodation.setPreferredSize(new Dimension(120, 40));
            btnRecommendedAccommodation.setFont(new Font("Tahoma", Font.PLAIN, 12));

            btnRecommendedAccommodation.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ThemePark selectedPark = (ThemePark) comboBoxParks.getSelectedItem();
                    int adults = (int) spinnerAdultTickets.getValue();
                    int children = (int) spinnerChildTickets.getValue();
                    int days = (int) spinnerNumberDays.getValue();

                    Date utilDate = (Date) spinnerDate.getValue();
                    LocalDate date = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    controller.setTicketReservation(new TicketReservation(selectedPark, date, adults, children, days));

                    window.switchPanel(new AccommodationsPanel(controller, window), "Accommodations");
                }
            });
        }
        return btnRecommendedAccommodation;
    }

    private JButton getBtnBook() {
        if (btnBook == null) {
            btnBook = new JButton("");
            btnBook.setToolTipText("");
            btnBook.setMnemonic('B');
            btnBook.setPreferredSize(new Dimension(90, 30));
            btnBook.setFont(new Font("Tahoma", Font.PLAIN, 12));

            btnBook.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ThemePark selectedPark = (ThemePark) comboBoxParks.getSelectedItem();
                    int adults = (int) spinnerAdultTickets.getValue();
                    int children = (int) spinnerChildTickets.getValue();
                    int days = (int) spinnerNumberDays.getValue();

                    Date utilDate = (Date) spinnerDate.getValue();
                    LocalDate date = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    controller.setTicketReservation(new TicketReservation(selectedPark, date, adults, children, days));

                    window.switchPanel(new ReservationPanel(controller, window), "Reservation");
                }
            });
        }
        return btnBook;
    }

    private JButton getBtnBack() {
        if (btnBack == null) {
            btnBack = new JButton("");
            btnBack.setToolTipText("");
            btnBack.setMnemonic('k');
            btnBack.setPreferredSize(new Dimension(70, 30));
            btnBack.setFont(new Font("Tahoma", Font.PLAIN, 12));

            btnBack.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    window.switchPanel(new MainMenuPanel(controller, window), "MainMenu");
                }
            });
        }
        return btnBack;
    }

    private Component getVerticalStrutAboveText() {
        if (verticalStrutAboveText == null) {
            verticalStrutAboveText = Box.createVerticalStrut(20);
        }
        return verticalStrutAboveText;
    }

    private Component getVerticalAboveImg() {
        if (verticalAboveImg == null) {
            verticalAboveImg = Box.createVerticalStrut(20);
        }
        return verticalAboveImg;
    }

    private Component getVerticalStrutAboveCombo() {
        if (verticalStrutAboveCombo == null) {
            verticalStrutAboveCombo = Box.createVerticalStrut(20);
            verticalStrutAboveCombo.setPreferredSize(new Dimension(0, 200));
        }
        return verticalStrutAboveCombo;
    }

    private Component getVerticalStrutNorth() {
        if (verticalStrutNorth == null) {
            verticalStrutNorth = Box.createVerticalStrut(20);
        }
        return verticalStrutNorth;
    }
    
    private String translate(String raw, ResourceBundle texts) {
    	if(texts==null) {
        	return raw;
        }
    	for (String key : texts.keySet()) {
            raw = raw.replace(key, texts.getString(key));
        }
        return raw;
    }


   
    public void localize(ResourceBundle texts) {

       this.texts=texts;
        lblSpecialOfferText.setToolTipText(texts.getString("themeparks.specialoffer.tooltip"));
        comboBoxParks.setToolTipText(texts.getString("themeparks.combobox.tooltip"));
        lblParkImg.setToolTipText(texts.getString("themeparks.parkimg.tooltip"));
        textAreaInfoPark.setToolTipText(texts.getString("themeparks.infopark.tooltip"));

        
        lblAdultTickets.setText(texts.getString("themeparks.adulttickets"));
        spinnerAdultTickets.setToolTipText(texts.getString("themeparks.adulttickets.tooltip"));

        lblChildTickets.setText(texts.getString("themeparks.childtickets"));
        spinnerChildTickets.setToolTipText(texts.getString("themeparks.childtickets.tooltip"));

        lblNumberOfDays.setText(texts.getString("themeparks.numberdays"));
        spinnerNumberDays.setToolTipText(texts.getString("themeparks.numberdays.tooltip"));

        lblDate.setText(texts.getString("themeparks.date"));
        spinnerDate.setToolTipText(texts.getString("themeparks.date.tooltip"));

        
        btnRecommendedAccommodation.setText(texts.getString("themeparks.btn.recommended"));
        btnRecommendedAccommodation.setToolTipText(texts.getString("themeparks.btn.recommended.tooltip"));

        btnBook.setText(texts.getString("themeparks.btn.book"));
        btnBook.setToolTipText(texts.getString("themeparks.btn.book.tooltip"));

        btnBack.setText(texts.getString("themeparks.btn.back"));
        btnBack.setToolTipText(texts.getString("themeparks.btn.back.tooltip"));
        
        
        ThemePark p = (ThemePark) comboBoxParks.getSelectedItem();
        if (p != null) {
            if (controller.isParkOnOffer(p)) {
                lblSpecialOfferText.setText(texts.getString("themeparks.offer.yes"));
            } else {
                lblSpecialOfferText.setText(texts.getString("themeparks.offer.no"));
            }
        }
        String raw = controller.getParkInfo(p);
        String translated = translate(raw, texts);
        textAreaInfoPark.setText(translated);
        
    }
}