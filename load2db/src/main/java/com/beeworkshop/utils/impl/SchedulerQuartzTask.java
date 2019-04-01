package com.beeworkshop.utils.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.beeworkshop.utils.Task;

public class SchedulerQuartzTask implements Task, Job {

	@Override
	public boolean doTask(String csvDir) {
		try {
			// 构建Job
			JobDetail job = JobBuilder.newJob(SchedulerQuartzTask.class).withIdentity("job1", "group1").build();
			// 构建Trigger
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("job1", "group1")
					.withSchedule(CronScheduleBuilder.cronSchedule("*/10 * * * * ?")).build();
			// 绑定Job和Trigger
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sr;

			sr = sf.getScheduler();
			sr.scheduleJob(job, trigger);
			sr.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 作业业务逻辑
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("当前时间为：" + sf.format(date));
		System.out.println("开始作业");
		System.out.println("......喂，你在哪里，快快回答我......");

		System.out.println("完成作业");
		System.out.println("=========================");
	}
}
