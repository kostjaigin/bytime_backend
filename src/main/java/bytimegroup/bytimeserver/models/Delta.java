package bytimegroup.bytimeserver.models;

/**
 * @author Konstantin Igin
 * Structure of this class is inspired by trello tech blog
 * tech.trello.com/syncing-changes
 * Delta class describes a change of one model object field
 */
public class Delta {

	private String field;
	private String oldValue;
	private String newValue;
	
	public Delta(String field, String oldValue, String newValue) {
		this.field = field;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	
	
}
