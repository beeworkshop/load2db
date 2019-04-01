package com.beeworkshop.utils.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.beeworkshop.model.Staff;
import com.beeworkshop.utils.File2List;

public class ScanFile2List implements File2List<Staff> {

	@Override
	public boolean loadFile(final List<Staff> list, final String file) {

		// 先清空列表
		list.clear();

		File aFile = new File(file);
		if (!aFile.exists()) {
			System.out.println("文件" + file + "不存在");
			return false;
		}

		Scanner sc = null;
		try {
			sc = new Scanner(aFile);
			while (sc.hasNextLine()) {
				Staff staff = new Staff();
				String line = sc.nextLine();
				List<String> lineTmp = Arrays.asList(line.split(","));
				Iterator<String> it = lineTmp.iterator();
				staff.setDept(it.hasNext() ? it.next().toString().trim() : "");
				staff.setName(it.hasNext() ? it.next().toString().trim() : "");
				staff.setGender(it.hasNext() ? it.next().toString().trim() : "");
				staff.setAge(it.hasNext() ? Integer.parseInt(it.next().toString()) : 0);
				staff.setStatus(it.hasNext() ? it.next().toString().trim() : "");

				list.add(staff);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			sc.close();
		}

		System.out.println("文件" + file + "载入成功");
		return true;
	}

}
