/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.batch.integration.job;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.bus.MessageBus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dave Syer
 * 
 */
@ContextConfiguration()
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageOrientedStepIntegrationTests {
	
	@Autowired
	private MessageBus bus;

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	@Qualifier("job")
	private Job job;
	
	@After
	public void shutdown() {
		bus.stop();
	}

	@Test
	public void testLaunchJob() throws Exception {
		bus.start();
		JobExecution jobExecution = jobLauncher.run(job, new JobParameters());
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}

}