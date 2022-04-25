package com.springbatch.demonstrativoorcamentario.writer;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.stereotype.Component;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;

@Component
public class DemonstrativoOrcamentarioRodape implements FlatFileFooterCallback {

	private double totalGeral = 0.0;
	
	@Override
	public void writeFooter(Writer writer) throws IOException {
		writer.write("\n");
		writer.write(String.format("\t\t\t\t\t\t\t  Total: %s\n", 
				NumberFormat.getCurrencyInstance().format(totalGeral)));
		writer.write(String.format("\t\t\t\t\t\t\t  Código de Autenticação: %s\n", "fkyew6868fewjfhjjewf"));
	}
	
	@BeforeWrite
	public void beforeWrite(List<GrupoLancamento> grupos) {
//		totalGeral = grupos.stream().mapToDouble(x -> x.getTotal()).sum();
		for (GrupoLancamento grupoLancamento : grupos) {
			totalGeral += grupoLancamento.getTotal();
		}
	}
	
	@AfterChunk
	public void afterChunck(ChunkContext context) {
		totalGeral = 0.0;
	}

}
