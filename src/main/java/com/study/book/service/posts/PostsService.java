package com.study.book.service.posts;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.book.domain.posts.Posts;
import com.study.book.domain.posts.PostsRepository;
import com.study.book.web.dto.PostsListResponseDto;
import com.study.book.web.dto.PostsResponseDto;
import com.study.book.web.dto.PostsSaveRequestDto;
import com.study.book.web.dto.PostsUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostsService {
	private final PostsRepository postsRepository;
	
	@Transactional
	public Long save(PostsSaveRequestDto requestDto) {
		return postsRepository.save(requestDto.toEntity()).getId();
	}
	
	@Transactional
	public Long update(Long id, PostsUpdateRequestDto requestDto) {
		Posts posts = postsRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id="+id)); 
		
		posts.update(requestDto.getTitle(), requestDto.getContent());
		
		return id;
	}
	
	public PostsResponseDto findById(Long id) {
		Posts entity = postsRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id="+id));
		
		return new PostsResponseDto(entity);
	}
	
	@Transactional(readOnly = true)
	public List<PostsListResponseDto> findAllDesc(){
		System.out.println("==========="+postsRepository.findAllDesc());
		return postsRepository.findAllDesc().stream()
				.map(PostsListResponseDto::new)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void delete(Long id) {
		Posts posts = postsRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id="+id));
		
		postsRepository.delete(posts);
	}
	
}
