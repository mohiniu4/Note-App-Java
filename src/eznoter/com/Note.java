/*package eznoter.com;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Note {
    JFrame noteWindow;
    JPanel notePanel;
    GUI gui = new GUI();
    JTextArea titleText = new JTextArea(1, 40);
    JTextArea mainText = new JTextArea(20, 80);
    NotesOriginator org = new NotesOriginator();
    NotesCaretaker caretk = new NotesCaretaker();
    int save = 0;
    int current = 0;

    public void createNoteWindow() {
        noteWindow = new JFrame("EZ Noter");
        gui.createMenuBar(noteWindow);
        notePanel = new JPanel();
        JPanel myPanel = new JPanel();

        // Create title components
        JLabel titleLabel = new JLabel("Title:");

        // Prevent title label from moving
        titleText.setLineWrap(true);
        titleText.setWrapStyleWord(true);
        titleText.setMaximumSize(new Dimension(Integer.MAX_VALUE, titleText.getPreferredSize().height));

        // Set layout for myPanel
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

        // Add title components to myPanel
        myPanel.add(titleLabel);
        myPanel.add(titleText);

        // Add rigid area for spacing
        myPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Adjust the height as needed

        // Add mainText to myPanel
        myPanel.add(mainText);

        // Add myPanel to notePanel
        notePanel.add(myPanel);

        // Add notePanel to noteWindow
        noteWindow.add(notePanel);

        // Add button to noteWindow to demonstrate getting text from JTextArea
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleText.getText();
                String content = mainText.getText();
                saveNote(title, content); // This method will save the note with title and content
            }
        });
        
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undo(); // Call method to perform undo action
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        buttonPanel.add(undoButton);
        noteWindow.add(buttonPanel, BorderLayout.SOUTH);

        // Set window size and visibility
        noteWindow.setResizable(false);
        noteWindow.setSize(1000, 600);
        noteWindow.setVisible(true);
    }

    // Example method to demonstrate using retrieved text
    private void saveNote(String title, String content) {
        // Code to save note with given title and content
        System.out.println("Title: " + title);
        System.out.println("Content: " + content);
        
        org.setNote(content);
        caretk.addMemento(org.storeInMemento());
        save++;
        current++;
    }
    
    private void undo() {
        // Code to perform undo action
    	if (current >= 1) {
			current--;
			String prevText = org.restoreFromMemento(caretk.getMemento(current));
			mainText.setText(prevText);
		}
    }
}*/

package eznoter.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class Note {
    private JFrame noteWindow;
    private JPanel notePanel;
    JTextArea noteText;
    private UndoManager undoManager = new UndoManager();

    public void createNoteWindow(String initialText) {
        noteWindow = new JFrame("EZ Noter - Note");
        noteWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        noteText = new JTextArea();
        notePanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(noteText);
        notePanel.add(scrollPane, BorderLayout.CENTER);

        noteWindow.add(notePanel);
        noteWindow.setSize(600, 400);

        setupTextArea(initialText);
        createMenuBar();

        noteWindow.setVisible(true);
    }

    private void setupTextArea(String initialText) {
        noteText.setLineWrap(true);
        noteText.setWrapStyleWord(true);
        if (initialText != null) {
            noteText.setText(initialText);
        }
        noteText.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save File");
        JMenuItem openItem = new JMenuItem("Open File");

        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));

        saveItem.addActionListener(e -> saveNote());
        openItem.addActionListener(e -> openNote());

        fileMenu.add(saveItem);
        fileMenu.add(openItem);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");
        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem undoItem = new JMenuItem("Undo");

        copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));

        copyItem.addActionListener(e -> noteText.copy());
        pasteItem.addActionListener(e -> noteText.paste());
        cutItem.addActionListener(e -> noteText.cut());
        undoItem.addActionListener(e -> {
            if (undoManager.canUndo()) undoManager.undo();
        });

        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(cutItem);
        editMenu.add(undoItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        noteWindow.setJMenuBar(menuBar);
    }

    private void saveNote() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files", "txt"));
        if (fileChooser.showSaveDialog(noteWindow) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(noteText.getText());
                JOptionPane.showMessageDialog(noteWindow, "Note saved successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(noteWindow, "Failed to save note.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openNote() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files", "txt"));
        if (fileChooser.showOpenDialog(noteWindow) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                noteText.setText(content.toString());
                JOptionPane.showMessageDialog(noteWindow, "Note loaded successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(noteWindow, "Failed to open note.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


