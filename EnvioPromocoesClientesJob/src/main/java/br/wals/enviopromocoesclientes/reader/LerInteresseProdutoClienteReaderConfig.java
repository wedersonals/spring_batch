package br.wals.enviopromocoesclientes.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import br.wals.enviopromocoesclientes.dominio.Cliente;
import br.wals.enviopromocoesclientes.dominio.InteresseProdutoCliente;
import br.wals.enviopromocoesclientes.dominio.Produto;

@Configuration
public class LerInteresseProdutoClienteReaderConfig {
	
	@Bean
	public JdbcCursorItemReader<InteresseProdutoCliente> lerInteresseProdutoClienteReader(
			@Qualifier("appDataSource") DataSource dataSource) {
		return new JdbcCursorItemReaderBuilder<InteresseProdutoCliente>()
				.name("lerInteresseProdutoClienteReader")
				.dataSource(dataSource)
				.sql("select"
						+ " c.id as cliente_id, c.nome as cliente_nome, c.email as cliente_email,"
						+ " p.id as produto_id, p.nome as produto_nome,"
						+ " p.descricao as produto_descricao, p.preco as produto_preco"
						+ " from interesse_produto_cliente i"
						+ " inner join cliente c on (i.cliente = c.id)"
						+ " inner join produto p on (i.produto = p.id)")
				.rowMapper(rowMapper())
				.build();
	}

	private RowMapper<InteresseProdutoCliente> rowMapper() {
		return new RowMapper<InteresseProdutoCliente>() {

			@Override
			public InteresseProdutoCliente mapRow(ResultSet rs, int rowNum) throws SQLException {
				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("cliente_id"));
				cliente.setNome(rs.getString("cliente_nome"));
				cliente.setEmail(rs.getString("cliente_email"));
				
				Produto produto = new Produto();
				produto.setId(rs.getInt("produto_id"));
				produto.setNome(rs.getString("produto_nome"));
				produto.setDescricao(rs.getString("produto_descricao"));
				produto.setPreco(rs.getDouble("produto_preco"));
				
				InteresseProdutoCliente interesseProdutoCliente = new InteresseProdutoCliente();				
				interesseProdutoCliente.setCliente(cliente);
				interesseProdutoCliente.setProduto(produto);
				return interesseProdutoCliente;
			}
		};
	}

}
