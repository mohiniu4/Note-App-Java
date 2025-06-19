/*package eznoter.com;

import javax.swing.*;

public class GUI {
	private JFrame window;
    private JMenuBar myMenuBar;
    private JMenu fileMenu;
    private JMenuItem share;
    private JMenuItem about;
    private JMenu saveMenu;
    private JMenuItem saveToApp;
    private JMenu editMenu;
    private JMenuItem undo;
    private JMenuItem redo;
    private JMenuItem cut;
    private JMenuItem copy;
    private JMenuItem paste;
    NewNotePlusIcon myPlusIcon; //adds the plus icon at the bottom of the page
    //Note note = new Note(); // Assume Note class exists to manage text content

    public void StartWindow() {
        createWindow();
        myPlusIcon = new NewNotePlusIcon();
        myPlusIcon.PlusIcon(window);
        createMenuBar(window);
        window.setVisible(true);
    }

    private void createWindow() {
        window = new JFrame("EZ Noter");
        window.setSize(1000, 600);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void createMenuBar(JFrame window) {
    	MenuActionListener myMenuActionListener =  new MenuActionListener(); 
        myMenuBar = new JMenuBar();

        // File Menu
        fileMenu = new JMenu("File");
        share = new JMenuItem("Share");
        about = new JMenuItem("About");
        fileMenu.add(share);
        fileMenu.add(about);
        share.addActionListener(myMenuActionListener);
		about.addActionListener(myMenuActionListener);

        // Edit Menu
        editMenu = new JMenu("Edit");
        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");
        copy = new JMenuItem("Copy");
        cut = new JMenuItem("Cut");
        paste = new JMenuItem("Paste");
        editMenu.add(undo);
        editMenu.add(redo);
        editMenu.add(copy);
        editMenu.add(cut);
        editMenu.add(paste);
        undo.addActionListener(myMenuActionListener);

        // Save menu
        saveMenu = new JMenu("Save");
        saveToApp = new JMenuItem("Save To Application");
        saveMenu.add(saveToApp);
        saveToApp.addActionListener(myMenuActionListener);

        // Add all JMenu items to the JMenuBar
        myMenuBar.add(fileMenu);
        myMenuBar.add(editMenu);
        myMenuBar.add(saveMenu);

        window.setJMenuBar(myMenuBar);
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

    public class GUI {
        JFrame window;
        JTextArea textArea = new JTextArea();
        JMenuBar myMenuBar;
        NewNotePlusIcon myPlusIcon;
        UndoManager undoManager = new UndoManager();

        public void StartWindow() {
            createWindow();
            createMenuBar();
            setupTextArea();
            myPlusIcon = new NewNotePlusIcon();
            myPlusIcon.PlusIcon(window);
            window.setVisible(true);
        }

        private void createWindow() {
            window = new JFrame("EZ Noter");
            window.setSize(1000, 600);
            window.setResizable(false);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        private void setupTextArea() {
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
                public void undoableEditHappened(UndoableEditEvent e) {
                    undoManager.addEdit(e.getEdit());
                }
            });
            JScrollPane scrollPane = new JScrollPane(textArea);
            window.add(scrollPane);
        }

        private void createMenuBar() {
            myMenuBar = new JMenuBar();

            JMenu fileMenu = new JMenu("File");
            JMenuItem newItem = new JMenuItem("New Note");
            JMenuItem saveItem = new JMenuItem("Save Note");

            newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
            saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

            newItem.addActionListener(e -> {
                Note newNote = new Note();
                newNote.createNoteWindow("");
            });


            saveItem.addActionListener(e -> saveTextToTxtFile());

            fileMenu.add(newItem);
            fileMenu.add(saveItem);

            JMenu editMenu = new JMenu("Edit");
            JMenuItem copyItem = new JMenuItem("Copy");
            JMenuItem pasteItem = new JMenuItem("Paste");
            JMenuItem cutItem = new JMenuItem("Cut");
            JMenuItem undoItem = new JMenuItem("Undo");

            copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
            pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
            cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
            undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));

            copyItem.addActionListener(e -> textArea.copy());
            pasteItem.addActionListener(e -> textArea.paste());
            cutItem.addActionListener(e -> textArea.cut());
            undoItem.addActionListener(e -> {
                if (undoManager.canUndo()) undoManager.undo();
            });

            editMenu.add(copyItem);
            editMenu.add(pasteItem);
            editMenu.add(cutItem);
            editMenu.add(undoItem);

            myMenuBar.add(fileMenu);
            myMenuBar.add(editMenu);

            window.setJMenuBar(myMenuBar);
        }

        private void saveTextToTxtFile() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files", "txt"));
            if (fileChooser.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().toLowerCase().endsWith(".txt")) {
                    file = new File(file.getAbsolutePath() + ".txt");
                }
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(textArea.getText());
                    JOptionPane.showMessageDialog(window, "Text saved successfully.");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(window, "Failed to save.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

      


        
       
    }


 
