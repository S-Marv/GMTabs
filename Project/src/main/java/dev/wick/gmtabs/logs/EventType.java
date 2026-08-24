package dev.wick.gmtabs.logs;

public enum EventType {
	START(true),
	CLOSE(true),
	EXCEPTION_RECORDED,

	KEYBOUND_SWAP,
	MOUSECLICK_SWAP,

	EDITOR_OPENED,
	EDITOR_CLOSED,
	EDITOR_CONTENT_TYPE_CHANGED,

	TAB_ADDED,
	TAB_REMOVED,
	TAB_ORDER_CHANGE,
	TAB_EDITED;

	private final boolean excludedFromCounter;

	EventType(){
		this.excludedFromCounter = false;
	}

	EventType(boolean excludeFromCounter){
		this.excludedFromCounter = excludeFromCounter;
	}

	public String getLogText() {
		return name();
	}

	public boolean isExcludedFromCounter() {
		return excludedFromCounter;
	}
}
