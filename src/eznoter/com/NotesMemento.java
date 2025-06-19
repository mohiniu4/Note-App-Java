package eznoter.com;

public class NotesMemento {
	private String Note;

	public NotesMemento(String saveNote) {
		// TODO Auto-generated constructor stub
		Note = saveNote;
	}
	
	public String getSavedNote() {
		return Note;
	}

}
