package cc.mi.core.utils;

import java.util.Calendar;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzUtils {
	private final static String JOB_GROUP_NAME = "QUARTZ_JOBGROUP_NAME";			//任务组
    private final static String TRIGGER_GROUP_NAME = "QUARTZ_TRIGGERGROUP_NAME";	//触发器组
    private static Scheduler sche;
    
    static {
    	SchedulerFactory sf = new StdSchedulerFactory();
		try {
			sche = sf.getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
    }
    
    public static void addJobWithCronExpression(Class<? extends Job> jobClazz,
    					String jobName,
    					String triggerName,
    					String cronExpression) throws SchedulerException {

    	//用于描叙Job实现类及其他的一些静态信息，构建一个作业实例
        JobDetail jobDetail = JobBuilder.newJob(jobClazz)
                .withIdentity(jobName, JOB_GROUP_NAME)
                .build();

        //构建一个触发器，规定触发的规则
        Trigger trigger = TriggerBuilder.newTrigger()		//创建一个新的TriggerBuilder来规范一个触发器
                .withIdentity(triggerName, TRIGGER_GROUP_NAME)//给触发器起一个名字和组名
                .startNow()//立即执行
                .withSchedule(
                		CronScheduleBuilder.cronSchedule(cronExpression)
//                		CronScheduleBuilder.cronSchedule("0/5 * * * * ?")
//                        SimpleScheduleBuilder.simpleSchedule()
//                                .withIntervalInSeconds(seconds)//时间间隔  单位：秒
//                                .repeatForever()//一直执行
                        //CronTrigger使用cron表达式来设置触发时间。CronTrigger创建方式：
                        //CronScheduleBuilder.cronSchedule("0 53 19 ? * *")     [[秒] [分] [小时] [日] [月] [周] [年] 当前为每天下午7点53执行
                )
                .build();//产生触发器
 
        //向Scheduler中添加job任务和trigger触发器
        sche.scheduleJob(jobDetail, trigger);
    }

    public static void addJobAtFixRate(Class<? extends Job> jobClazz,
			String jobName,
			String triggerName,
			int fixRateSeconds,
			int delay) throws SchedulerException {

		//用于描叙Job实现类及其他的一些静态信息，构建一个作业实例
		JobDetail jobDetail = JobBuilder.newJob(jobClazz)
		    .withIdentity(jobName, JOB_GROUP_NAME)
		    .build();
		
		//构建一个触发器，规定触发的规则
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, delay);
		Trigger trigger = TriggerBuilder.newTrigger()		//创建一个新的TriggerBuilder来规范一个触发器
		    .withIdentity(triggerName, TRIGGER_GROUP_NAME)  //给触发器起一个名字和组名
		    .startAt(calendar.getTime())
		    .withSchedule(
	            SimpleScheduleBuilder.repeatSecondlyForever(fixRateSeconds)
		    )
		    .build();//产生触发器

		//向Scheduler中添加job任务和trigger触发器
		sche.scheduleJob(jobDetail, trigger);
	}
    
    public static void addJobToDoOnce(Class<? extends Job> jobClazz,
			String jobName,
			String triggerName,
			int delay) throws SchedulerException {

		//用于描叙Job实现类及其他的一些静态信息，构建一个作业实例
		JobDetail jobDetail = JobBuilder.newJob(jobClazz)
		    .withIdentity(jobName, JOB_GROUP_NAME)
		    .build();

		//构建一个触发器，规定触发的规则
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, delay);
		Trigger trigger = TriggerBuilder.newTrigger()		//创建一个新的TriggerBuilder来规范一个触发器
		    .withIdentity(triggerName, TRIGGER_GROUP_NAME)  //给触发器起一个名字和组名
		    .withSchedule(
	            SimpleScheduleBuilder.repeatSecondlyForTotalCount(1, delay)
		    )
		    .startAt(calendar.getTime())
		    .build();//产生触发器

		//向Scheduler中添加job任务和trigger触发器
		sche.scheduleJob(jobDetail, trigger);
	}
    
    public static void start() throws SchedulerException {
    	sche.start();
    }
}
