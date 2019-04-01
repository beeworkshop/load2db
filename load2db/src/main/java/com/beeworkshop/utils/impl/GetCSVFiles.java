package com.beeworkshop.utils.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.beeworkshop.utils.GetFiles;

public class GetCSVFiles implements GetFiles {

	@Override
	public List<String> listDir(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			System.out.println("目录" + dir + "不存在");
			return null;
		} else if (file.isFile()) {
			System.out.println(dir + "是文件");
			return null;
		}

		List<String> list = new ArrayList<String>();
		File[] arrayFiles = file.listFiles();
		for (File f : arrayFiles) {
			if (f.isFile() && f.getName().endsWith(".csv")) {
				list.add(f.toString());
			}
		}

		return list;
	}

}
