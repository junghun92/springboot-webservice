package com.study.book.domain.posts;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {
	
	@Autowired
	PostsRepository postsRepository;
	
	@AfterEach
	public void cleanup() {
		postsRepository.deleteAll();
	} 
	
	@Test
	public void findAll() {
		//given
        String title = "테스트 게시글";
        String content = "테스트 본문";
		
		postsRepository.save(Posts.builder()
				.title(title)
				.content(content)
				.author("jojoid@gmail.com")
				.build());
		
		//when
		List<Posts> postsList = postsRepository.findAll();
		
		//then
		Posts posts = postsList.get(0);
		Assertions.assertThat(posts.getTitle()).isEqualTo(title);
		Assertions.assertThat(posts.getContent()).isEqualTo(content);
	}
	
	@Test
	public void baseTimeEntitySave() {
		//given
		LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
		postsRepository.save(Posts.builder()
				.title("title")
				.content("content")
				.author("author")
				.build());
		//when
		List<Posts> postsList = postsRepository.findAll();
		
		//then
		Posts posts = postsList.get(0);
		
		System.out.println("================");
		System.out.println("createDate: "+posts.getCreDateTime());
		System.out.println("modifiedDate: "+posts.getModifiedDate());
		
		Assertions.assertThat(posts.getCreDateTime()).isAfter(now);
		Assertions.assertThat(posts.getModifiedDate()).isAfter(now);
	}
}
