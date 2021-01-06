/* 
 * All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */
 
package com.hxtx.udp.cron;

import com.hxtx.udp.client.UdpClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.Instant;

/**
 * <p>
 * This is just a simple job that gets fired off many times by example 1
 * </p>
 * 
 * @author Bill Kratzer
 */
public class SimpleJob2 implements Job {


    /**
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
    public SimpleJob2() {
    }

    /**
     * <p>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a
     * <code>{@link org.quartz.Trigger}</code> fires that is associated with
     * the <code>Job</code>.
     * </p>
     * 
     * @throws JobExecutionException
     *             if there is an exception while executing the job.
     */
    @Override
    public void execute(JobExecutionContext context)  throws JobExecutionException {
        try {
            UdpClient udpClient = new UdpClient();
            udpClient.connect("10.10.2.46",7000);
            udpClient.sendMessage("job2测试发送"+ Instant.now().toEpochMilli());
            System.out.println("执行job2");
            udpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
