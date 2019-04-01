package com.beeworkshop.utils.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.beeworkshop.model.Staff;
import com.beeworkshop.service.DBService;
import com.beeworkshop.utils.File2List;

public class LoadingJob implements Job {

	@Override
	public synchronized void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("CSV文件开始入库...");

		// 传入参数
		JobDataMap data = context.getJobDetail().getJobDataMap();
		// System.out.println("CSV源目录" + data.get("CsvDir"));

		File2List<Staff> scanFile2List = (ScanFile2List) data.get("ScanFile2List");

		DBService dbService = (DBService) data.get("DBService");

		@SuppressWarnings("unchecked")
		List<Staff> staffs = (ArrayList<Staff>) data.get("Staffs");

		// 将一个目录转换为一个ArrayList。元素为单个文件。
		List<String> list = new GetCSVFiles().listDir(data.get("CsvDir").toString());
		if (list == null) {
			System.err.println("提取CSV目录文件失败");
		} else if (list.size() == 0) {
			System.out.println("暂无可处理的CSV文件");
		} else {
			for (String file : list) {
				// 将一个文件转换成一个ArrayList<Staff>
				if (scanFile2List.loadFile(staffs, file)) {
					// 一个文件一组List<Staff> staffs。文件一行一个Staff对象。
					// 针对单个文件做批量入库

//					System.out.println("入库前的staffs为空指针吗？ " + staffs);
//					System.out.println("入库前的staffs的内容为空吗？ " + staffs.size());
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>准备入库 ");

					int staffNo = dbService.insertBatch(staffs);
					System.out.println("文件" + file + "入库" + staffNo + "条记录");

					// 备份文件file
					try {
						File srcFile = new File(file);
						String fileName = srcFile.getName();
						FileUtils.moveFile(srcFile, new File(Constants.BACK_DIR + "/" + fileName));
					} catch (IOException e) {
						System.err.println("文件" + file + "备份异常");
						e.printStackTrace();
					}
				}
			}
		}

		System.out.println("入库完毕");
	}

}
