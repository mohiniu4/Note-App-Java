/*package eznoter.com;
import javax.swing.*;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//CLEAN UP CODE SEMANTICS LATERR

public class MenuActionListener implements ActionListener {

	 //for save, undo and redo menu items
	NotesOriginator originator =  new NotesOriginator();
	NotesCaretaker caretaker = new NotesCaretaker();
	Note n = new Note();
	private int saveFiles = 0;
	private int currentNote = 0;
	
	//String myText = n.getText();

	    // Constructor to receive GUI instance
//	public MenuActionListener(JFrame mainFrame) {
//	        this.myFrame = mainFrame;
//	    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String myItem = e.getActionCommand();

		if(myItem.equals("Share")) {
			JFrame shareFrame = new JFrame("Share");

			JLabel shareLabel1 = new JLabel("You can share files with friends!"); //make labels
			shareLabel1.setFont(new Font("Arial", Font.PLAIN, 14)); //edit font style of labels
			shareFrame.add(shareLabel1); //attach labels to window frame

			//resolve window frame size
			shareFrame.setSize(250,250);
			shareFrame.setVisible(true);
		}

		else if(myItem.equals("About")) {
			JFrame aboutFrame = new JFrame("About");
			//make labels
			JLabel aboutLabel1 = new JLabel("This Application was created by: Umanga!");

			//edit font style of labels
			aboutLabel1.setFont(new Font("Arial", Font.PLAIN, 14));

			//attach labels to window frame
			aboutFrame.add(aboutLabel1);

			//resolve window frame size
			aboutFrame.setSize(400,400);
			aboutFrame.setVisible(true);
		}

		else if(myItem.equals("Save To Application")) {
			//String myMainText = n.getMainText();
//			String mainTextArea = myMainText.getText();
//			String titleTextArea = myTitleText.getText();
//			System.out.println("" + mainTextArea + " " + titleTextArea );
			
//			String text = myMainText + " " + myTitleText;
			//originator.setNote(myText);
			caretaker.addMemento(originator.storeInMemento());
			saveFiles++;
			currentNote++;
			System.out.println("Save Files " + saveFiles);
		}

		else if(myItem.equals("Undo")) {
			if (currentNote >= 1) {
				currentNote--;
				//String prevText = originator.restoreFromMemento(caretaker.getMemento(currentNote));
				//n.mainText.setText(prevText);
			}

		}

	}

}*/
package eznoter.com;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuActionListener implements ActionListener {
    private JTextArea textArea;

    public MenuActionListener(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        switch (command) {
            case "Copy":
                clipboard.setContents(new StringSelection(textArea.getSelectedText()), null);
                break;
            case "Paste":
                try {
                    Transferable t = clipboard.getContents(null);
                    if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                        textArea.replaceSelection((String) t.getTransferData(DataFlavor.stringFlavor));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case "Cut":
                clipboard.setContents(new StringSelection(textArea.getSelectedText()), null);
                textArea.replaceSelection("");
                break;
        }
    }
}


