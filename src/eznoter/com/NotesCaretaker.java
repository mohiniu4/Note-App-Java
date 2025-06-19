package eznoter.com;
import java.util.ArrayList;

public class NotesCaretaker {

		// Where all mementos are saved
		ArrayList<NotesMemento> savedNotes = new ArrayList<NotesMemento>();

		// Adds memento to the ArrayList	
		public void addMemento(NotesMemento memento) { 
			savedNotes.add(memento); 
			}
	   
		// Gets the memento requested from the ArrayList
		public NotesMemento getMemento(int index) { 
			return savedNotes.get(index); 
			}
}
