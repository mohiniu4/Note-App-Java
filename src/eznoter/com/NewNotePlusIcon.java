package eznoter.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NewNotePlusIcon {
    public void PlusIcon(JFrame mainFrame) {
        ImageIcon plusIcon = new ImageIcon("plus icon.png");

        Image resizedImage = plusIcon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon resizedPlusIcon = new ImageIcon(resizedImage);

        JLabel plusLabel = new JLabel(resizedPlusIcon);

        JPanel myIconPanel = new JPanel();
        myIconPanel.setPreferredSize(new Dimension(45, 45));
        myIconPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        myIconPanel.add(plusLabel);

        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(myIconPanel, BorderLayout.SOUTH);

        plusLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        plusLabel.setToolTipText("Create New Note");

        plusLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Note newNote = new Note();
                newNote.createNoteWindow("");
            }
        });
    }
}