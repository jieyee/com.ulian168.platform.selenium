package com.ulian168.platform.selenium.web.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.ulian168.platform.selenium.web.model.WebActionModel;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
@Component
public class WebUITestCaseTaskQueue {
    private BlockingQueue<WebActionModel> taskQueue = new LinkedBlockingQueue<WebActionModel>();
    private List<String> scheduleQueue = new ArrayList<String>();
    
    public void addTask(final WebActionModel model) {
        taskQueue.add(model);
    }
    
    public void addTasks(final List<WebActionModel> list) {
        taskQueue.addAll(list);
    }
    
    public WebActionModel getTask() throws Exception {
        return taskQueue.poll(10, TimeUnit.SECONDS);
    }
    
    public void addSchedule(final String caseName) {
        scheduleQueue.add(caseName);
    }
    
    public void removeAllSchedule() {
        scheduleQueue = null;
        scheduleQueue = new ArrayList<String>();
    }
    
    public boolean removeSchedule(final String caseName) {
        boolean remove = scheduleQueue.remove(caseName);
        return remove;
    }
    
    public List<String> getScheduleList() {
        return scheduleQueue;
    }
}
