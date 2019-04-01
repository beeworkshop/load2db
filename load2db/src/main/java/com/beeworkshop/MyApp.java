package com.beeworkshop;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;

import com.beeworkshop.model.Staff;
import com.beeworkshop.service.DBService;
import com.beeworkshop.utils.Task;
import com.beeworkshop.utils.impl.Constants;
import com.beeworkshop.utils.impl.GetCSVFiles;
import com.beeworkshop.utils.impl.Load2MySQL;

public class MyApp {

	private static Logger logger = Logger.getLogger(MyApp.class);

	private static String dirIsValid(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			System.out.println("目录" + dir + "不存在");
			return "NoExit";
		} else if (file.isFile()) {
			System.out.println(dir + "是文件，不是目录");
			return "IsFile";
		}
		return "OK";
	}

	public static void main(String[] args) throws Exception {

		logger.info("log info");

		logger.debug("log debug");

		logger.error("log error");

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);

		System.out.println("请输入CSV源文件目录：");
		String csvDir = scan.nextLine();
		List<String> ls = new GetCSVFiles().listDir(csvDir);
		if (ls == null) {
			System.err.println("提取CSV目录失败");
			System.exit(1);
		} else {
			System.out.println("目录" + csvDir + "下的CSV文件：");
			for (String f : ls) {
				System.out.println(f);
			}
		}

		if ("NoExit".equals(dirIsValid(Constants.BACK_DIR))) {
			File d = new File(Constants.BACK_DIR);
			if (!d.mkdirs()) {
				System.err.println("备份目录创建失败");
				System.exit(1);
			} else {
				System.out.println("备份目录创建成功");
			}
		}

		// 产生任务的调度（单例）
		Task task = new Load2MySQL();
		if (task.doTask(csvDir)) {
			System.out.println("任务调度启用成功");
		}

		while (scan.hasNextLine()) {
			System.out.println("========================");
			System.out.println("1. 显示入库内容（输入 1）");
			System.out.println("2. 退出应用（输入 2）");
			System.out.println("========================");
			int cmd = scan.nextInt();
			if (cmd == 1) {
				// 显示入库表
				System.out.println("*****************************");

				DBService dbService = new DBService();
				List<Staff> staffs = dbService.listStaff();
				for (Staff staff : staffs) {
					System.out.println(staff);
				}

				System.out.println("*****************************");
			} else if (cmd == 2) {
				// 特别注意：接口的多态仅限于接口的抽象方法！！！！！
				// 对于类实现的非接口抽象方法不会体现多态的效果。使用时需要进行类型的强制转换。
				Scheduler sc = ((Load2MySQL) task).getScheduler();
				// 终止Task
				sc.pauseAll();

				int i = 0;
				boolean flag = false;

				do {
					List<JobExecutionContext> jobs = sc.getCurrentlyExecutingJobs();
					flag = false;

					for (JobExecutionContext job : jobs) {
						if ("jobLoading".equals(job.getTrigger().getJobKey().getName())) {
							// LoadingJob还在执行
							flag = true;
							break;
						}
					}

					Thread.sleep(10000); // 休眠10秒

					if (++i >= 8)
						break;

				} while (flag);

//				System.out.println("i:" + i);
				System.out.println("再见");
				System.exit(0);
			}
		}
	}

}
