package xyz.needpainkiller.schedule.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import xyz.needpainkiller.api.team.model.TeamEntity;
import xyz.needpainkiller.base.team.TeamService;
import xyz.needpainkiller.base.team.model.Team;

@Slf4j
public final class UpdateTeamPathJob extends QuartzJobBean implements InterruptableJob {


    private volatile boolean isJobInterrupted = false;

    private volatile Thread currThread;

    @Autowired
    private TeamService<? extends Team> teamService;

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        isJobInterrupted = true;
        if (currThread != null) {
            log.debug("interrupting - {}", currThread.getName());
            currThread.interrupt();
        }
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        if (isJobInterrupted) {
            log.debug("isJobInterrupted - {}", currThread.getName());
            return;
        }
        JobKey jobKey = context.getJobDetail().getKey();
        currThread = Thread.currentThread();
        teamService.updateAllTeamPath();
    }
}
