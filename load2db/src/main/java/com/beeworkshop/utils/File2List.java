package com.beeworkshop.utils;

import java.util.List;

public interface File2List<T> {
	boolean loadFile(final List<T> list, final String file);
}
