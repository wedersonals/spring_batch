package br.wals.servicereaderjob.reader;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.wals.servicereaderjob.domain.ResponseUser;
import br.wals.servicereaderjob.domain.User;

@Component
public class UserReader implements ItemReader<User> {

	private RestTemplate restTemplate = new RestTemplate();
	private int page = 1;
	private List<User> users = new ArrayList<>();
	private int userIndex = 0;
	private int total = 0;
	
	@Value("${chunkSize}")
	private int chunkSize;
	
	@Value("${pageSize}")
	private int pageSize;
	
	@Value("${limit}")
	private int limit;
		
	@Override
	public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		User user;
		
		if (userIndex < users.size())
			user = users.get(userIndex);
		else
			user = null;
		
		userIndex++;
		return user;
	}
	
	private List<User> fetchUserDataFromAPI() {
		ResponseEntity<ResponseUser> response = restTemplate.getForEntity(String.format("https://gorest.co.in/public/v1/users?page=%d", page), 
				ResponseUser.class);
		return response.getBody().getData();
	}
	
	@BeforeChunk
	private void beforeChunk(ChunkContext context) {
		for (int i = 0; i < chunkSize; i += pageSize) {
			if (isLimit())
				return;
			
			users.addAll(fetchUserDataFromAPI());
			total += pageSize;
			System.out.println(String.format("Total: %d, Usuários: %d", total, users.size()));
			page++;
		}
			
	}
	
	private boolean isLimit() {
		return limit == 0 ? false : total >= limit;
	}
	
	@AfterChunk
	private void afterChunk(ChunkContext context) {
		System.out.println("Fim do chunk - total lidos: " + total);
		userIndex = 0;
		users = new ArrayList<>();
	}

}
