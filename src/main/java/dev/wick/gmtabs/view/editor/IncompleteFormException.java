package dev.wick.gmtabs.view.editor;

import java.util.HashSet;
import java.util.Set;

public class IncompleteFormException extends RuntimeException{
	private static final String BASE_MESSAGE = "Form is incomplete. Missing the following properties: ";

	static IncompleteFormException create(EditorForm incompleteForm){
		final boolean isMissingKeybind = incompleteForm.getKeybinding().get()==null;
		final boolean isMissingContentPath = incompleteForm.getSelectedPath().isEmpty().get();
		HashSet<VitalProperty> missingProperties = new HashSet<>();
		if(isMissingKeybind) missingProperties.add(VitalProperty.KEYBIND);
		if (isMissingContentPath) missingProperties.add(VitalProperty.PATH);
		return new IncompleteFormException(missingProperties);
	}

	private final Set<VitalProperty> missingProperties;

	private IncompleteFormException(Set<VitalProperty> missingProperties){
		super(BASE_MESSAGE + missingProperties.toString());
		this.missingProperties = missingProperties;
	}

	Set<VitalProperty> getMissingProperties() {
		return missingProperties;
	}

	enum VitalProperty {
		KEYBIND,
		PATH;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}
}