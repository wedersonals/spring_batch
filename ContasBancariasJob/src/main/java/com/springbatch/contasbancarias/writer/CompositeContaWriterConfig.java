package com.springbatch.contasbancarias.writer;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.contasbancarias.dominio.Conta;

@Configuration
public class CompositeContaWriterConfig {

	@Bean
	public CompositeItemWriter<Conta> compositeItemWriter(
			FlatFileItemWriter<Conta> flatFileItemWriter,
			JdbcBatchItemWriter<Conta> jdbcBatchItemWriter) {
		return new CompositeItemWriterBuilder<Conta>()
				.delegates(flatFileItemWriter, jdbcBatchItemWriter)
				.build();
	}
}
