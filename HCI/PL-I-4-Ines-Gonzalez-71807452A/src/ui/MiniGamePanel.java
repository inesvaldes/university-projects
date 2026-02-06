package ui;

import javax.swing.JPanel;

import controller.AppController;
import javax.swing.JLabel;

import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JButton;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.util.ResourceBundle;

public class MiniGamePanel extends JPanel implements Localizable {

    private static final long serialVersionUID = 1L;

    private AppController controller;
    private MainWindow window;
    private JLabel lblExplanation;
    private JPanel panelButtons;
    private JLabel lblConfirmation;
    private JLabel lblTickImage;
    private JLabel lblTitle;
    private JLabel lblResult;
    private JButton btnClose;
    private RevealCell revealCellListener;
    private ResourceBundle texts;
    

    public MiniGamePanel(AppController controller, MainWindow window) {
        setPreferredSize(new Dimension(800, 600));
        this.controller = controller;
        this.window = window;
        setLayout(null);
        add(getLblExplanation());
        add(getPanelButtons());
        add(getLblConfirmation());
        add(getLblTickImage());
        add(getLblTitle());
        add(getLblResult());
        add(getBtnClose());
        revealCellListener = new RevealCell();
        initialize();
    }

    private void initialize() {
        controller.startMiniGame();
        for (int i = 0; i < controller.getMiniGameCellCount(); i++) {
            JButton btn = new JButton();
            btn.setIcon(scaleIcon(controller.getMiniGameImagePath(i), 80, 80));
            btn.putClientProperty("index", i);
            btn.addActionListener(revealCellListener);
            panelButtons.add(btn);
        }
    }

    private void onCellClicked(int index) {
        if (controller.hasPlayedMiniGame()) return;

        boolean won = controller.revealCell(index);
        Component[] components = panelButtons.getComponents();

        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JButton btn) {
                String path = controller.getMiniGameImagePath(i);
                btn.setIcon(scaleIcon(path, btn.getWidth(), btn.getHeight()));
            }
        }

        if (won) {
            controller.getBooking().setGiftVoucher(true);

            lblResult.setText(texts.getString("minigame.win"));

        } else {
            lblResult.setText(texts.getString("minigame.lose"));
        }
    }

    private ImageIcon scaleIcon(String path, int width, int height) {
        Image img = new ImageIcon(getClass().getResource(path)).getImage();
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private class RevealCell implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            int index = (int) btn.getClientProperty("index");
            onCellClicked(index);
        }
    }

    private JLabel getLblExplanation() {
        if (lblExplanation == null) {
            lblExplanation = new JLabel("");
            lblExplanation.setToolTipText("");
            lblExplanation.setFont(new Font("Tahoma", Font.PLAIN, 15));
            lblExplanation.setHorizontalAlignment(SwingConstants.CENTER);
            lblExplanation.setBounds(114, 32, 464, 65);
        }
        return lblExplanation;
    }

    private JPanel getPanelButtons() {
        if (panelButtons == null) {
            panelButtons = new JPanel();
            panelButtons.setToolTipText("");
            panelButtons.setBounds(39, 123, 593, 135);
            panelButtons.setLayout(new GridLayout(1, 0, 0, 0));
        }
        return panelButtons;
    }

    private JLabel getLblConfirmation() {
        if (lblConfirmation == null) {
            lblConfirmation = new JLabel("");
            lblConfirmation.setToolTipText("");
            lblConfirmation.setFont(new Font("Tahoma", Font.PLAIN, 15));
            lblConfirmation.setHorizontalAlignment(SwingConstants.CENTER);
            lblConfirmation.setBounds(28, 286, 201, 74);
        }
        return lblConfirmation;
    }

    private JLabel getLblTickImage() {
        if (lblTickImage == null) {
            lblTickImage = new JLabel("");
            lblTickImage.setBounds(89, 370, 57, 51);
            setImageToLabel(lblTickImage, "/img/tick.png");
        }
        return lblTickImage;
    }

    public void setImageToLabel(JLabel label, String imagePath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(newImg));
    }

    private JLabel getLblTitle() {
        if (lblTitle == null) {
            lblTitle = new JLabel("");
            lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
            lblTitle.setLabelFor(getLblResult());
            lblTitle.setDisplayedMnemonic('R');
            lblTitle.setFont(new Font("Tahoma", Font.BOLD, 13));
            lblTitle.setBounds(302, 277, 141, 27);
        }
        return lblTitle;
    }

    private JLabel getLblResult() {
        if (lblResult == null) {
            lblResult = new JLabel("");
            lblResult.setToolTipText("");
            lblResult.setHorizontalAlignment(SwingConstants.CENTER);
            lblResult.setBorder(new LineBorder(new Color(0, 128, 255), 2));
            lblResult.setBounds(259, 314, 227, 51);
        }
        return lblResult;
    }

    private JButton getBtnClose() {
        if (btnClose == null) {
            btnClose = new JButton("");
            btnClose.setToolTipText("");
            btnClose.setMnemonic('C');
            btnClose.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    controller.saveCurrentBooking();
                    controller.resetAllReservations();
                    controller.resetMiniGame();
                    window.switchPanel(new MainMenuPanel(controller, window), "MainMenu");
                }
            });
            btnClose.setBounds(533, 370, 123, 51);
        }
        return btnClose;
    }

    @Override
    public void localize(ResourceBundle texts) {
        this.texts = texts;

        lblExplanation.setText(texts.getString("minigame.explanation"));
        lblExplanation.setToolTipText(texts.getString("minigame.explanation.tooltip"));

        panelButtons.setToolTipText(texts.getString("minigame.buttons.tooltip"));

        lblConfirmation.setText(texts.getString("minigame.confirmation"));
        lblConfirmation.setToolTipText(texts.getString("minigame.confirmation.tooltip"));

        lblTitle.setText(texts.getString("minigame.result.title"));

        lblResult.setToolTipText(texts.getString("minigame.result.tooltip"));

        btnClose.setText(texts.getString("minigame.btn.close"));
        btnClose.setToolTipText(texts.getString("minigame.btn.close.tooltip"));
    }
}
