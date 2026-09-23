import javax.swing.*;
import java.awt.*;

public class Controller {

    private JDialog dialog;
    private JButton btnup, btndown, btnok;
    private JButton btnLeftPlus, btnLeftMinus, btnRightMinus, btnRightPlus;

    public Controller() {
        dialog = new JDialog();
        dialog.setTitle("NIM Controller");
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(null);
        dialog.setAlwaysOnTop(true);
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Color.BLACK);

        btnup = new JButton("/\\");
        btndown = new JButton("\\/");
        btnok = new JButton("ENTER");
        btnLeftPlus = new JButton("<\n+");
        btnLeftMinus = new JButton("<\n-");
        btnRightMinus = new JButton(">\n-");
        btnRightPlus = new JButton(">\n+");

        Font consoleFont = new Font("Monospaced", Font.BOLD, 16);
        JButton[] buttons = {btnup, btndown, btnok, btnLeftPlus, btnLeftMinus, btnRightMinus, btnRightPlus};

        for (JButton btn : buttons) {
            btn.setBackground(new Color(30, 30, 30));
            btn.setForeground(Color.WHITE);
            btn.setFont(consoleFont);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));
        }

        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.add(btnLeftPlus);
        leftPanel.add(btnLeftMinus);

        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        rightPanel.add(btnRightPlus);
        rightPanel.add(btnRightMinus);

        dialog.add(btnup, BorderLayout.NORTH);
        dialog.add(btndown, BorderLayout.SOUTH);
        dialog.add(leftPanel, BorderLayout.WEST);
        dialog.add(rightPanel, BorderLayout.EAST);
        dialog.add(btnok, BorderLayout.CENTER);
    }

    public JDialog getDialog() { return dialog; }
    public JButton getBtnup() { return btnup; }
    public JButton getBtndown() { return btndown; }
    public JButton getBtnok() { return btnok; }

    public JButton getBtnLeftPlus() { return btnLeftPlus; }
    public JButton getBtnLeftMinus() { return btnLeftMinus; }
    public JButton getBtnRightMinus() { return btnRightMinus; }
    public JButton getBtnRightPlus() { return btnRightPlus; }
}