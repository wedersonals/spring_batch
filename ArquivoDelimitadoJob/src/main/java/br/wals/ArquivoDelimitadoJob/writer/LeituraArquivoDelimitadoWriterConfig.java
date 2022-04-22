package br.wals.ArquivoDelimitadoJob.writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import br.wals.ArquivoDelimitadoJob.dominio.Cliente;

@Configuration
public class LeituraArquivoDelimitadoWriterConfig {

	@StepScope
	@Bean
	public FlatFileItemWriter<Cliente> escritaArquivoDelimitadoWriter(
			@Value("#{jobParameters['arquivoClientesSaida']}") Resource arquivoClientesSaida) {
		return new FlatFileItemWriterBuilder<Cliente>()
				.name("escritaArquivoDelimitadoWriter")
				.resource(arquivoClientesSaida)
				.delimited()
				.delimiter("|")
				.names("nome", "sobrenome", "idade", "email")
				.build();
	}
}
