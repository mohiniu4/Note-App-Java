package eznoter.com;

public class NotesOriginator {
	private String orgNote; //note from originator

	public void setNote(String newNote) {
		System.out.println("From Originator: Current Version of Note\n"+newNote+ "\n");
	    this.orgNote = newNote; 
	}
	
	// Creates a new Memento with a new note
	public NotesMemento storeInMemento() { 
		    System.out.println("From Originator: Saving to Memento");
		    return new NotesMemento(orgNote); 
		}
	
	// Gets the article currently stored in memento
	public String restoreFromMemento(NotesMemento memento) {			   
			orgNote = memento.getSavedNote(); 	       
			System.out.println("From Originator: Previous Note Saved in Memento\n"+orgNote + "\n");
			return orgNote;	   
		}
}
