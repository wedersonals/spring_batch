package br.wals.ArquivoDelimitadoJob.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class LeituraArquivoDelimitadoJobConfig {
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Bean
	public Job arquivoLarguraFixaJob(Step leituraArquivoDelimitadoStep) {
		return jobBuilderFactory
				.get("arquivoLarguraFixaJob")
				.start(leituraArquivoDelimitadoStep)
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
