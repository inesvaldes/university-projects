package ui;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;

import controller.AppController;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

public class ReservationSummaryPanel extends JPanel implements Localizable {

    private static final long serialVersionUID = 1L;
    private JLabel lblTitle;
    private JScrollPane scrollPane;
    private JTextArea textAreaSummary;
    private JButton btnConfirm;
    private JButton btnCancel;
    private AppController controller;
    private MainWindow window;
    private ResourceBundle texts;

   

    public ReservationSummaryPanel(AppController controller, MainWindow window) {
        setPreferredSize(new Dimension(800, 600));
        setLayout(null);
        add(getLblTitle());
        add(getScrollPane());
        add(getBtnConfirm());
        add(getBtnCancel());
        this.controller = controller;
        this.window = window;
        fillSummary();
    }

    private void fillSummary() {
        String raw = controller.getBookingSummaryText();   
        if (texts != null) {
            raw = translate(raw, texts);                  
        }
        textAreaSummary.setText(raw);
    }


    private JLabel getLblTitle() {
        if (lblTitle == null) {
            lblTitle = new JLabel("");
            lblTitle.setLabelFor(getScrollPane());
            lblTitle.setDisplayedMnemonic('R');
            lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblTitle.setBounds(33, 22, 302, 35);
        }
        return lblTitle;
    }

    private JScrollPane getScrollPane() {
        if (scrollPane == null) {
            scrollPane = new JScrollPane();
            scrollPane.setBounds(33, 67, 585, 296);
            scrollPane.setViewportView(getTextAreaSummary());
        }
        return scrollPane;
    }

    private JTextArea getTextAreaSummary() {
        if (textAreaSummary == null) {
            textAreaSummary = new JTextArea();
            textAreaSummary.setEditable(false);
            textAreaSummary.setToolTipText("");
        }
        return textAreaSummary;
    }

    private JButton getBtnConfirm() {
        if (btnConfirm == null) {
            btnConfirm = new JButton("");
            btnConfirm.setToolTipText("");
            btnConfirm.setMnemonic('C');
            btnConfirm.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    window.switchPanel(new MiniGamePanel(controller, window), "MiniGame");
                }
            });
            btnConfirm.setBounds(351, 373, 139, 47);
        }
        return btnConfirm;
    }

    private JButton getBtnCancel() {
        if (btnCancel == null) {
            btnCancel = new JButton("");
            btnCancel.setToolTipText("");
            btnCancel.setMnemonic('l');
            btnCancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    controller.resetAllReservations();
                    window.switchPanel(new MainMenuPanel(controller, window), "MainMenu");
                }
            });
            btnCancel.setBounds(510, 373, 131, 47);
        }
        return btnCancel;
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
        lblTitle.setText(texts.getString("summary.title"));
        lblTitle.setToolTipText(texts.getString("summary.title.tooltip"));

        textAreaSummary.setToolTipText(texts.getString("summary.text.tooltip"));

        btnConfirm.setText(texts.getString("summary.btn.confirm"));
        btnConfirm.setToolTipText(texts.getString("summary.btn.confirm.tooltip"));

        btnCancel.setText(texts.getString("summary.btn.cancel"));
        btnCancel.setToolTipText(texts.getString("summary.btn.cancel.tooltip"));
        fillSummary();
    }
}
