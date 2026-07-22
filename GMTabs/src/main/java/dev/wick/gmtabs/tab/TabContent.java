package dev.wick.gmtabs.tab;

import java.io.Serializable;

public record TabContent(boolean pathIsFile, String path) implements Serializable {
	@Override
	public String toString() {
		return "TabContent{" +
				"pathIsFile=" + pathIsFile +
				", path='" + path + '\'' +
				'}';
	}
}
