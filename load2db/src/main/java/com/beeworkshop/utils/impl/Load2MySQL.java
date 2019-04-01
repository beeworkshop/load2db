package com.beeworkshop.utils.impl;

import java.util.ArrayList;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.beeworkshop.model.Staff;
import com.beeworkshop.service.DBService;
import com.beeworkshop.utils.File2List;
import com.beeworkshop.utils.Task;

public class Load2MySQL implements Task {
	private File2List<Staff> file2List;
	private List<Staff> list;
	private DBService dbService;
	private static final String CRON_LOADING = "*/30 * * * * ?";
	private static final String CRON_DEL_CSV = "0 */1 * * * ?";
	private Scheduler scheduler;

	public Load2MySQL() {
		file2List = new ScanFile2List();
		list = new ArrayList<Staff>();
		dbService = new DBService();
	}

	@Override
	public boolean doTask(String csvDir) {
		// @formatter:off
		// 构建Job
		JobKey jobKeyLoading = new JobKey("jobLoading", "groupDB");
		JobDetail jobLoading = JobBuilder
				.newJob(LoadingJob.class) //关联具体执行体
				.withIdentity(jobKeyLoading)
				.build();
		
		//向Job传递参数
		jobLoading.getJobDataMap().put("CsvDir",csvDir);
		jobLoading.getJobDataMap().put("ScanFile2List",file2List);
		jobLoading.getJobDataMap().put("Staffs",list);
		jobLoading.getJobDataMap().put("DBService",dbService);

		JobKey jobKeyDelCSV = new JobKey("jobDelCSV", "groupDB");
		JobDetail jobDelCSV = JobBuilder
				.newJob(DelCSVJob.class)  //关联具体执行体
				.withIdentity(jobKeyDelCSV)
				.build();
		
		//向Job传递参数
		//jobDelCSV.getJobDataMap().put("BakDir",bakDir);

		// 构建Trigger
		Trigger tgrLoading = TriggerBuilder
				.newTrigger()
				.withIdentity("triggerLoading", "groupDB")
				.withSchedule(CronScheduleBuilder
						.cronSchedule(CRON_LOADING))
				.build();

		Trigger tgrDelCSV = TriggerBuilder
				.newTrigger()
				.withIdentity("triggerDelCSV", "groupDB")
				.withSchedule(CronScheduleBuilder
						.cronSchedule(CRON_DEL_CSV))
				.build();
		// @formatter:on

		try {
			// 绑定Job和Trigger
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			// 调度两个Jobs
			scheduler.scheduleJob(jobLoading, tgrLoading);
			scheduler.scheduleJob(jobDelCSV, tgrDelCSV);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}
}
