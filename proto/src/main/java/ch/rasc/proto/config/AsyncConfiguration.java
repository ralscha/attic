package ch.rasc.proto.config;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration extends AsyncConfigurerSupport {

	@Autowired
	private AsyncProperties properties;

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		if (this.properties.getCorePoolSize() != null) {
			executor.setCorePoolSize(this.properties.getCorePoolSize());
		}

		if (this.properties.getMaxPoolSize() != null) {
			executor.setMaxPoolSize(this.properties.getMaxPoolSize());
		}

		if (this.properties.getQueueCapacity() != null) {
			executor.setQueueCapacity(this.properties.getQueueCapacity());
		}

		if (this.properties.getThreadNamePrefix() != null) {
			executor.setThreadNamePrefix(this.properties.getThreadNamePrefix());
		}

		if (this.properties.getAllowCoreThreadTimeOut() != null) {
			executor.setAllowCoreThreadTimeOut(this.properties
					.getAllowCoreThreadTimeOut());
		}

		if (this.properties.getWaitForTasksToCompleteOnShutdown() != null) {
			executor.setWaitForTasksToCompleteOnShutdown(this.properties
					.getWaitForTasksToCompleteOnShutdown());
		}

		if (this.properties.getAwaitTerminationSeconds() != null) {
			executor.setAwaitTerminationSeconds(this.properties
					.getAwaitTerminationSeconds());
		}

		if (this.properties.getKeepAliveSeconds() != null) {
			executor.setKeepAliveSeconds(this.properties.getKeepAliveSeconds());
		}

		executor.initialize();
		return executor;
	}

}
