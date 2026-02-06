package ui;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
import model.Accommodation;
import model.AccommodationReservation;
import model.ThemePark;

import java.time.LocalDate;
import java.time.ZoneId;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;
import java.util.ResourceBundle;

public class AccommodationsPanel extends JPanel implements Localizable {

    private static final long serialVersionUID = 1L;

    private JPanel panelLeft;
    private JLabel lblAccImg;
    private JComboBox comboBoxAcc;
    private JPanel panelRight;
    private JScrollPane scrollPaneInfoAcc;
    private JPanel panelFields;
    private JLabel lblEntryDate;
    private JSpinner spinnerEntryDate;
    private JLabel lblNumberNights;
    private JSpinner spinnerNumberNights;
    private JLabel lblNumberOfPeople;
    private JSpinner spinnerNumberPeople;
    private JLabel lblNumberOfRooms;
    private JSpinner spinnerNumberRooms;
    private JPanel panelButtons;
    private JButton btnRecommendedPark;
    private JButton btnBook;
    private JButton btnBack;
    private Component verticalAboveImg;
    private Component verticalStrutAboveCombo;
    private Component verticalStrutNorth;
    private JTextArea textAreaInfoAcc;

    private AppController controller;
    private MainWindow window;
    private JPanel panelCheckBoxes;
    private JCheckBox chckbxHotel;
    private JCheckBox chckbxApartment;
    private JCheckBox chckbxAparthotel;
    private Component verticalStrutAboveCheckBoxes;
    private JPanel panelFilterCathegory;
    private JLabel lblFilterStars;
    private JComboBox comboBoxCathegory;
    private Component verticalStrutAboveFilter;
    private ResourceBundle texts;


    public AccommodationsPanel(AppController controller, MainWindow window) {
        this.controller = controller;
        this.window = window;

        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout(0, 0));
        add(getPanelLeft(), BorderLayout.WEST);
        add(getPanelRight(), BorderLayout.CENTER);

        initData();
    }

    private void initData() {
        comboBoxAcc.removeAllItems();
        for (Accommodation a : controller.getAccommodationsForSelectedPark()) {
            comboBoxAcc.addItem(a);
        }

        btnRecommendedPark.setEnabled(controller.getTicketReservation() == null);
    }

    private void applyFilters() {
        comboBoxAcc.removeAllItems();

        for (Accommodation a : controller.getAccommodationsForSelectedPark()) {

            boolean typeMatches = false;

            if (chckbxHotel.isSelected() && a.getType().equalsIgnoreCase("HO")) typeMatches = true;
            if (chckbxApartment.isSelected() && a.getType().equalsIgnoreCase("AP")) typeMatches = true;
            if (chckbxAparthotel.isSelected() && a.getType().equalsIgnoreCase("AH")) typeMatches = true;

            if (!chckbxHotel.isSelected() && !chckbxApartment.isSelected() && !chckbxAparthotel.isSelected())
                typeMatches = true;

            if (!typeMatches) continue;

            String selectedCat = (String) comboBoxCathegory.getSelectedItem();
            if (selectedCat == null) return;
            if (!selectedCat.equals(texts.getString("acco.filter.category.all"))) {
                int cat = Integer.parseInt(selectedCat);
                if (a.getCategory() != cat) continue;
            }

            comboBoxAcc.addItem(a);
        }
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
            panelLeft.add(getLblAccImg());
            panelLeft.add(getVerticalAboveImg());
            panelLeft.add(getPanelCheckBoxes());
            panelLeft.add(getVerticalStrutAboveCheckBoxes());
            panelLeft.add(getPanelFilterCathegory());
            panelLeft.add(getVerticalStrutAboveFilter());
            panelLeft.add(getComboBoxAcc());
            panelLeft.add(getVerticalStrutAboveCombo());
        }
        return panelLeft;
    }

    private JLabel getLblAccImg() {
        if (lblAccImg == null) {
            lblAccImg = new JLabel("");
            lblAccImg.setToolTipText("");
            lblAccImg.setPreferredSize(new Dimension(350, 200));
            lblAccImg.setMinimumSize(new Dimension(150, 150));
            lblAccImg.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        return lblAccImg;
    }

    private JComboBox getComboBoxAcc() {
        if (comboBoxAcc == null) {
            comboBoxAcc = new JComboBox();
            comboBoxAcc.setToolTipText("");
            comboBoxAcc.setPreferredSize(new Dimension(350, 30));
            comboBoxAcc.setAlignmentX(Component.CENTER_ALIGNMENT);

            comboBoxAcc.addActionListener(e -> {
                Accommodation a = (Accommodation) comboBoxAcc.getSelectedItem();
                if (a == null) return;

                controller.setSelectedAccommodation(a);
                String imgPath = controller.getAccommodationImagePath(a);
                setImageToLabel(lblAccImg, imgPath);

                btnRecommendedPark.setEnabled(controller.getTicketReservation() == null);
                if (texts != null) { 
                	localize(texts); 
                }
            });
        }
        return comboBoxAcc;
    }

    private JPanel getPanelRight() {
        if (panelRight == null) {
            panelRight = new JPanel();
            panelRight.setBorder(new EmptyBorder(20, 10, 20, 20));
            panelRight.setLayout(new BorderLayout(0, 0));
            panelRight.add(getScrollPaneInfoAcc(), BorderLayout.NORTH);
            panelRight.add(getPanelFields(), BorderLayout.CENTER);
            panelRight.add(getPanelButtons(), BorderLayout.SOUTH);
        }
        return panelRight;
    }

    private JScrollPane getScrollPaneInfoAcc() {
        if (scrollPaneInfoAcc == null) {
            scrollPaneInfoAcc = new JScrollPane(getTextAreaInfoAcc());
            scrollPaneInfoAcc.setPreferredSize(new Dimension(250, 200));
        }
        return scrollPaneInfoAcc;
    }

    private JTextArea getTextAreaInfoAcc() {
        if (textAreaInfoAcc == null) {
            textAreaInfoAcc = new JTextArea();
            textAreaInfoAcc.setEditable(false);
            textAreaInfoAcc.setToolTipText("");
            textAreaInfoAcc.setWrapStyleWord(true);
            textAreaInfoAcc.setLineWrap(true);
        }
        return textAreaInfoAcc;
    }

    private JPanel getPanelFields() {
        if (panelFields == null) {
            panelFields = new JPanel();
            GridBagLayout gbl = new GridBagLayout();
            gbl.columnWeights = new double[]{1.0, 0.0};
            panelFields.setLayout(gbl);

            GridBagConstraints gbc_lblEntryDate = new GridBagConstraints();
            gbc_lblEntryDate.insets = new Insets(5, 5, 5, 5);
            gbc_lblEntryDate.anchor = GridBagConstraints.WEST;
            gbc_lblEntryDate.gridx = 0;
            gbc_lblEntryDate.gridy = 0;
            panelFields.add(getLblEntryDate(), gbc_lblEntryDate);

            GridBagConstraints gbc_spinnerEntryDate = new GridBagConstraints();
            gbc_spinnerEntryDate.insets = new Insets(5, 5, 5, 5);
            gbc_spinnerEntryDate.anchor = GridBagConstraints.WEST;
            gbc_spinnerEntryDate.gridx = 1;
            gbc_spinnerEntryDate.gridy = 0;
            panelFields.add(getSpinnerEntryDate(), gbc_spinnerEntryDate);

            GridBagConstraints gbc_lblNumberNights = new GridBagConstraints();
            gbc_lblNumberNights.insets = new Insets(5, 5, 5, 5);
            gbc_lblNumberNights.anchor = GridBagConstraints.WEST;
            gbc_lblNumberNights.gridx = 0;
            gbc_lblNumberNights.gridy = 1;
            panelFields.add(getLblNumberNights(), gbc_lblNumberNights);

            GridBagConstraints gbc_spinnerNumberNights = new GridBagConstraints();
            gbc_spinnerNumberNights.insets = new Insets(5, 5, 5, 5);
            gbc_spinnerNumberNights.anchor = GridBagConstraints.WEST;
            gbc_spinnerNumberNights.gridx = 1;
            gbc_spinnerNumberNights.gridy = 1;
            panelFields.add(getSpinnerNumberNights(), gbc_spinnerNumberNights);

            GridBagConstraints gbc_lblNumberOfPeople = new GridBagConstraints();
            gbc_lblNumberOfPeople.insets = new Insets(5, 5, 5, 5);
            gbc_lblNumberOfPeople.anchor = GridBagConstraints.WEST;
            gbc_lblNumberOfPeople.gridx = 0;
            gbc_lblNumberOfPeople.gridy = 2;
            panelFields.add(getLblNumberOfPeople(), gbc_lblNumberOfPeople);

            GridBagConstraints gbc_spinnerNumberPeople = new GridBagConstraints();
            gbc_spinnerNumberPeople.insets = new Insets(5, 5, 5, 5);
            gbc_spinnerNumberPeople.anchor = GridBagConstraints.WEST;
            gbc_spinnerNumberPeople.gridx = 1;
            gbc_spinnerNumberPeople.gridy = 2;
            panelFields.add(getSpinnerNumberPeople(), gbc_spinnerNumberPeople);

            GridBagConstraints gbc_lblNumberOfRooms = new GridBagConstraints();
            gbc_lblNumberOfRooms.insets = new Insets(5, 5, 5, 5);
            gbc_lblNumberOfRooms.anchor = GridBagConstraints.WEST;
            gbc_lblNumberOfRooms.gridx = 0;
            gbc_lblNumberOfRooms.gridy = 3;
            panelFields.add(getLblNumberOfRooms(), gbc_lblNumberOfRooms);

            GridBagConstraints gbc_spinnerNumberRooms = new GridBagConstraints();
            gbc_spinnerNumberRooms.insets = new Insets(5, 5, 5, 5);
            gbc_spinnerNumberRooms.anchor = GridBagConstraints.WEST;
            gbc_spinnerNumberRooms.gridx = 1;
            gbc_spinnerNumberRooms.gridy = 3;
            panelFields.add(getSpinnerNumberRooms(), gbc_spinnerNumberRooms);
        }
        return panelFields;
    }

    private JLabel getLblEntryDate() {
        if (lblEntryDate == null) {
            lblEntryDate = new JLabel("");
            lblEntryDate.setDisplayedMnemonic('E');
            lblEntryDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
        }
        return lblEntryDate;
    }

    private JSpinner getSpinnerEntryDate() {
        if (spinnerEntryDate == null) {
            spinnerEntryDate = new JSpinner();
            spinnerEntryDate.setToolTipText("");
            spinnerEntryDate.setModel(new SpinnerDateModel(new Date(1768246842744L), new Date(1768246842744L), null, Calendar.DAY_OF_MONTH));
            JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerEntryDate, "dd/MM/yyyy");
            spinnerEntryDate.setEditor(editor);
        }
        return spinnerEntryDate;
    }

    private JLabel getLblNumberNights() {
        if (lblNumberNights == null) {
            lblNumberNights = new JLabel("");
            lblNumberNights.setDisplayedMnemonic('n');
            lblNumberNights.setFont(new Font("Tahoma", Font.PLAIN, 15));
        }
        return lblNumberNights;
    }

    private JSpinner getSpinnerNumberNights() {
        if (spinnerNumberNights == null) {
            spinnerNumberNights = new JSpinner();
            spinnerNumberNights.setToolTipText("");
            spinnerNumberNights.setModel(new SpinnerNumberModel(1, 1, 30, 1));
        }
        return spinnerNumberNights;
    }

    private JLabel getLblNumberOfPeople() {
        if (lblNumberOfPeople == null) {
            lblNumberOfPeople = new JLabel("");
            lblNumberOfPeople.setDisplayedMnemonic('p');
            lblNumberOfPeople.setFont(new Font("Tahoma", Font.PLAIN, 15));
        }
        return lblNumberOfPeople;
    }

    private JSpinner getSpinnerNumberPeople() {
        if (spinnerNumberPeople == null) {
            spinnerNumberPeople = new JSpinner();
            spinnerNumberPeople.setToolTipText("");
            spinnerNumberPeople.setModel(new SpinnerNumberModel(1, 1, 20, 1));
        }
        return spinnerNumberPeople;
    }

    private JLabel getLblNumberOfRooms() {
        if (lblNumberOfRooms == null) {
            lblNumberOfRooms = new JLabel("");
            lblNumberOfRooms.setDisplayedMnemonic('r');
            lblNumberOfRooms.setFont(new Font("Tahoma", Font.PLAIN, 15));
        }
        return lblNumberOfRooms;
    }

    private JSpinner getSpinnerNumberRooms() {
        if (spinnerNumberRooms == null) {
            spinnerNumberRooms = new JSpinner();
            spinnerNumberRooms.setToolTipText("");
            spinnerNumberRooms.setModel(new SpinnerNumberModel(1, 1, 10, 1));
        }
        return spinnerNumberRooms;
    }

    private JPanel getPanelButtons() {
        if (panelButtons == null) {
            panelButtons = new JPanel();
            panelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
            panelButtons.add(getBtnRecommendedPark());
            panelButtons.add(getBtnBook());
            panelButtons.add(getBtnBack());
        }
        return panelButtons;
    }

    private JButton getBtnRecommendedPark() {
        if (btnRecommendedPark == null) {
            btnRecommendedPark = new JButton("");
            btnRecommendedPark.setToolTipText("");
            btnRecommendedPark.setMnemonic('R');
            btnRecommendedPark.setPreferredSize(new Dimension(120, 40));
            btnRecommendedPark.setFont(new Font("Tahoma", Font.PLAIN, 12));

            btnRecommendedPark.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!checkOccupancy()) return;

                    AccommodationReservation r = buildReservation();
                    controller.setAccommodationReservation(r);
                    window.switchPanel(new ThemeParksPanel(controller, window), "ThemeParks");
                }
            });
        }
        return btnRecommendedPark;
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
                    if (!checkOccupancy()) return;

                    AccommodationReservation r = buildReservation();
                    controller.setAccommodationReservation(r);

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

    private boolean checkOccupancy() {
        int people = (int) spinnerNumberPeople.getValue();
        int rooms = (int) spinnerNumberRooms.getValue();

        Accommodation acc = controller.getSelectedAccommodation();
        boolean ok = controller.isValidAccommodationCapacity(acc, people, rooms);
       
        
        if (!ok) {
            JOptionPane.showMessageDialog(
                this,
                texts.getString("acco.error.capacity") + "\n" +
                texts.getString("acco.error.max") + (acc.getMaxOccupancy() * rooms) + "\n" +
                texts.getString("acco.error.increase"),
                texts.getString("acco.error.title"),
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        return true;
    }

    private AccommodationReservation buildReservation() {
        int people = (int) spinnerNumberPeople.getValue();
        int rooms = (int) spinnerNumberRooms.getValue();
        int nights = (int) spinnerNumberNights.getValue();

        Date utilDate = (Date) spinnerEntryDate.getValue();
        LocalDate date = utilDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        Accommodation acc = controller.getSelectedAccommodation();

        return new AccommodationReservation(acc, date, nights, people);
    }

    private JPanel getPanelCheckBoxes() {
        if (panelCheckBoxes == null) {
            panelCheckBoxes = new JPanel();
            panelCheckBoxes.setPreferredSize(new Dimension(10, 30));
            panelCheckBoxes.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            panelCheckBoxes.add(getChckbxHotel());
            panelCheckBoxes.add(getChckbxApartment());
            panelCheckBoxes.add(getChckbxAparthotel());
        }
        return panelCheckBoxes;
    }

    private JCheckBox getChckbxHotel() {
        if (chckbxHotel == null) {
            chckbxHotel = new JCheckBox("");
            chckbxHotel.setToolTipText("");
            chckbxHotel.setMnemonic('H');
            chckbxHotel.addActionListener(e -> applyFilters());
        }
        return chckbxHotel;
    }

    private JCheckBox getChckbxApartment() {
        if (chckbxApartment == null) {
            chckbxApartment = new JCheckBox("");
            chckbxApartment.setToolTipText("");
            chckbxApartment.setMnemonic('A');
            chckbxApartment.addActionListener(e -> applyFilters());
        }
        return chckbxApartment;
    }

    private JCheckBox getChckbxAparthotel() {
        if (chckbxAparthotel == null) {
            chckbxAparthotel = new JCheckBox("");
            chckbxAparthotel.setToolTipText("");
            chckbxAparthotel.setMnemonic('p');
            chckbxAparthotel.addActionListener(e -> applyFilters());
        }
        return chckbxAparthotel;
    }

    private JPanel getPanelFilterCathegory() {
        if (panelFilterCathegory == null) {
            panelFilterCathegory = new JPanel();
            panelFilterCathegory.add(getLblFilterStars());
            panelFilterCathegory.add(getComboBoxCathegory());
        }
        return panelFilterCathegory;
    }

    private JLabel getLblFilterStars() {
        if (lblFilterStars == null) {
            lblFilterStars = new JLabel("");
            lblFilterStars.setLabelFor(getComboBoxCathegory());
            lblFilterStars.setDisplayedMnemonic('F');
        }
        return lblFilterStars;
    }

    private JComboBox getComboBoxCathegory() {
        if (comboBoxCathegory == null) {
            comboBoxCathegory = new JComboBox();
            comboBoxCathegory.setToolTipText("");
            comboBoxCathegory.setPreferredSize(new Dimension(75, 21));
            comboBoxCathegory.addActionListener(e -> applyFilters());
        }
        return comboBoxCathegory;
    }

    private Component getVerticalStrutNorth() {
        if (verticalStrutNorth == null) {
            verticalStrutNorth = Box.createVerticalStrut(20);
        }
        return verticalStrutNorth;
    }

    private Component getVerticalAboveImg() {
        if (verticalAboveImg == null) {
            verticalAboveImg = Box.createVerticalStrut(20);
        }
        return verticalAboveImg;
    }

    private Component getVerticalStrutAboveCheckBoxes() {
        if (verticalStrutAboveCheckBoxes == null) {
            verticalStrutAboveCheckBoxes = Box.createVerticalStrut(10);
        }
        return verticalStrutAboveCheckBoxes;
    }

    private Component getVerticalStrutAboveFilter() {
        if (verticalStrutAboveFilter == null) {
            verticalStrutAboveFilter = Box.createVerticalStrut(10);
        }
        return verticalStrutAboveFilter;
    }

    private Component getVerticalStrutAboveCombo() {
        if (verticalStrutAboveCombo == null) {
            verticalStrutAboveCombo = Box.createVerticalStrut(200);
        }
        return verticalStrutAboveCombo;
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


    
    @Override
    public void localize(ResourceBundle texts) {

        this.texts=texts;
        lblAccImg.setToolTipText(texts.getString("acco.img.tooltip"));
        comboBoxAcc.setToolTipText(texts.getString("acco.combobox.tooltip"));
        textAreaInfoAcc.setToolTipText(texts.getString("acco.info.tooltip"));

        
        chckbxHotel.setText(texts.getString("acco.filter.hotel"));
        chckbxHotel.setToolTipText(texts.getString("acco.filter.hotel.tooltip"));

        chckbxApartment.setText(texts.getString("acco.filter.apartment"));
        chckbxApartment.setToolTipText(texts.getString("acco.filter.apartment.tooltip"));

        chckbxAparthotel.setText(texts.getString("acco.filter.aparthotel"));
        chckbxAparthotel.setToolTipText(texts.getString("acco.filter.aparthotel.tooltip"));

        
        lblFilterStars.setText(texts.getString("acco.filter.category"));
        comboBoxCathegory.setToolTipText(texts.getString("acco.filter.category.tooltip"));

        comboBoxCathegory.removeAllItems();
        comboBoxCathegory.addItem(texts.getString("acco.filter.category.all"));
        comboBoxCathegory.addItem("1");
        comboBoxCathegory.addItem("2");
        comboBoxCathegory.addItem("3");
        comboBoxCathegory.addItem("4");
        comboBoxCathegory.addItem("5");

        
        lblEntryDate.setText(texts.getString("acco.entrydate"));
        spinnerEntryDate.setToolTipText(texts.getString("acco.entrydate.tooltip"));

        lblNumberNights.setText(texts.getString("acco.nights"));
        spinnerNumberNights.setToolTipText(texts.getString("acco.nights.tooltip"));

        lblNumberOfPeople.setText(texts.getString("acco.people"));
        spinnerNumberPeople.setToolTipText(texts.getString("acco.people.tooltip"));

        lblNumberOfRooms.setText(texts.getString("acco.rooms"));
        spinnerNumberRooms.setToolTipText(texts.getString("acco.rooms.tooltip"));

       
        btnRecommendedPark.setText(texts.getString("acco.btn.recommended"));
        btnRecommendedPark.setToolTipText(texts.getString("acco.btn.recommended.tooltip"));

        btnBook.setText(texts.getString("acco.btn.book"));
        btnBook.setToolTipText(texts.getString("acco.btn.book.tooltip"));

        btnBack.setText(texts.getString("acco.btn.back"));
        
        btnBack.setToolTipText(texts.getString("acco.btn.back.tooltip"));
        Accommodation a = controller.getSelectedAccommodation();
        if (a != null) {
            String raw = controller.getAccommodationInfo(a);
            String translated = translate(raw, texts);
            String parkName = controller.getParkNameByCode(a.getParkCode());
            translated += parkName;
            textAreaInfoAcc.setText(translated);
        }

    }
}
