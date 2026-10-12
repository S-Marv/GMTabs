package dev.wick.gmtabs.tab;

import java.io.Serializable;

public record TabContent(boolean pathIsFile, String path) implements Serializable {

	public TabContent {
		if (path!=null && !pathIsFile && !path.isEmpty() && !path.startsWith("http")){
			path = "https://" + path;
		}
	}

	@Override
	public String toString() {
		return "TabContent{" +
				"pathIsFile=" + pathIsFile +
				", path='" + path + '\'' +
				'}';
	}
}
