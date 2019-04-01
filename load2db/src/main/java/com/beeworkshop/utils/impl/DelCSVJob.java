package com.beeworkshop.utils.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DelCSVJob implements Job {

	@Override
	public synchronized void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("CSV文件开始删除...");

		List<String> list = new GetCSVFiles().listDir(Constants.BACK_DIR);
		if (list == null) {
			System.err.println("备份目录提取失败");
			return;
		} else if (list.size() == 0) {
			System.out.println("暂无可删文件");
		} else {
			System.out.println("备份目录" + Constants.BACK_DIR + "下的CSV文件：");
			for (String file : list) {
				if (FileUtils.deleteQuietly(new File(file))) {
					System.out.println(file + "被删除");
				}
			}
		}

		System.out.println("CSV文件备份删除处理完毕");
	}

}
