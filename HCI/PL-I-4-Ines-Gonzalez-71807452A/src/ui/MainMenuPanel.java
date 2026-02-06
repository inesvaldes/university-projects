package ui;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.AppController;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.border.LineBorder;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

public class MainMenuPanel extends JPanel implements Localizable{

    private static final long serialVersionUID = 1L;
    private JLabel lblLogo;
    private JLabel lblPromotion;
    private JLabel lblName;
    private JLabel lblParksImg;
    private JLabel lblAccoImg;
    private JButton btnThemeParks;
    private JButton btnAccommodations;

    private AppController controller;
    private MainWindow window;
    private JLabel lblPromotionPark;

    public MainMenuPanel(AppController controller, MainWindow window) {
        setPreferredSize(new Dimension(800, 600));

        this.controller = controller;
        this.window = window;

        setLayout(null);
        setBorder(new EmptyBorder(5, 5, 5, 5));

        add(getLblLogo());
        add(getLblPromotion());
        add(getLblName());
        add(getLblParksImg());
        add(getLblAccoImg());
        add(getBtnThemeParks());
        add(getBtnAccommodations());
        add(getLblPromotionPark());
        initialize();
    }

    private void initialize() {
        controller.resetSelections();
        controller.resetAllReservations();
        lblPromotionPark.setText(controller.getOfferPark().getName());
    }

    private JLabel getLblLogo() {
        if (lblLogo == null) {
            lblLogo = new JLabel("");
            lblLogo.setBounds(21, 10, 149, 129);
            setImageToLabel(lblLogo, "/img/logo.png");
        }
        return lblLogo;
    }

    private JLabel getLblPromotion() {
        if (lblPromotion == null) {
            lblPromotion = new JLabel("");
            lblPromotion.setLabelFor(getLblPromotionPark());
            lblPromotion.setDisplayedMnemonic('S');
            lblPromotion.setForeground(new Color(255, 0, 0));
            lblPromotion.setFont(new Font("Times New Roman", Font.BOLD, 16));
            lblPromotion.setHorizontalAlignment(SwingConstants.CENTER);
            lblPromotion.setBounds(180, 10, 277, 92);
        }
        return lblPromotion;
    }

    private JLabel getLblName() {
        if (lblName == null) {
            lblName = new JLabel("");
            lblName.setVerticalAlignment(SwingConstants.BOTTOM);
            lblName.setForeground(new Color(0, 128, 255));
            lblName.setBackground(new Color(193, 224, 255));
            lblName.setHorizontalAlignment(SwingConstants.CENTER);
            lblName.setFont(new Font("Yu Gothic", Font.BOLD, 40));
            lblName.setBounds(34, 149, 609, 95);
            lblName.setOpaque(true);
        }
        return lblName;
    }

    private JLabel getLblParksImg() {
        if (lblParksImg == null) {
            lblParksImg = new JLabel("");
            lblParksImg.setBounds(120, 257, 197, 120);
            setImageToLabel(lblParksImg, "/img/themepark.png");
        }
        return lblParksImg;
    }

    private JLabel getLblAccoImg() {
        if (lblAccoImg == null) {
            lblAccoImg = new JLabel("");
            lblAccoImg.setBounds(374, 257, 197, 120);
            setImageToLabel(lblAccoImg, "/img/acc.png");
        }
        return lblAccoImg;
    }

    private JButton getBtnThemeParks() {
        if (btnThemeParks == null) {
            btnThemeParks = new JButton();
            btnThemeParks.setMnemonic(KeyEvent.VK_T);
            btnThemeParks.addActionListener(e -> {
                window.switchPanel(new ThemeParksPanel(controller, window), "ThemeParks");
            });
            btnThemeParks.setBounds(130, 388, 168, 30);
        }
        return btnThemeParks;
    }

    private JButton getBtnAccommodations() {
        if (btnAccommodations == null) {
            btnAccommodations = new JButton();
            btnAccommodations.setMnemonic(KeyEvent.VK_A);
            btnAccommodations.addActionListener(e -> {
                window.switchPanel(new AccommodationsPanel(controller, window), "Accommodations");
            });
            btnAccommodations.setBounds(384, 387, 168, 30);
        }
        return btnAccommodations;
    }

    private void setImageToLabel(JLabel label, String imagePath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(newImg));
    }

    private JLabel getLblPromotionPark() {
        if (lblPromotionPark == null) {
            lblPromotionPark = new JLabel("");
            lblPromotionPark.setBorder(new LineBorder(new Color(255, 0, 0), 4));
            lblPromotionPark.setHorizontalAlignment(SwingConstants.CENTER);
            lblPromotionPark.setFont(new Font("Tahoma", Font.PLAIN, 15));
            lblPromotionPark.setBounds(467, 34, 188, 48);
        }
        return lblPromotionPark;
    }

   
    public void localize(ResourceBundle texts) {

        lblLogo.setToolTipText(texts.getString("mainmenu.logo.tooltip"));

        lblPromotion.setText(texts.getString("mainmenu.promotion.html"));
        lblPromotion.setToolTipText(texts.getString("mainmenu.promotion.tooltip"));

        lblName.setText(texts.getString("mainmenu.name"));

        lblPromotionPark.setToolTipText(texts.getString("mainmenu.promotionpark.tooltip"));

        btnThemeParks.setText(texts.getString("mainmenu.button.themeparks"));
        btnThemeParks.setToolTipText(texts.getString("mainmenu.button.themeparks.tooltip"));

        btnAccommodations.setText(texts.getString("mainmenu.button.accommodations"));
        btnAccommodations.setToolTipText(texts.getString("mainmenu.button.accommodations.tooltip"));
    }
}

