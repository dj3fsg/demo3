package com.example.demo3;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo3.data.entity.Posts;
import com.example.demo3.data.repository.PostsRepository;
import com.example.demo3.form.PostsForm;
import com.example.demo3.service.AccountUserDetails;

@Controller
public class PostsController {
	@Autowired
	private PostsRepository postsRepository;

	@GetMapping("/posts")
	public String getPosts(Model model) {
		// コメントリスト取得処理を追加
		List<Posts> posts = postsRepository.findAll();
		// 取得したリストをテンプレートに渡す
		model.addAttribute("posts", posts);
		
		return "posts";
	}
	
	// マッピング設定
		@PostMapping("/posts")
		public String create(PostsForm postsForm, @AuthenticationPrincipal AccountUserDetails user) {
			Posts posts = new Posts();
			
			
			 //タイトルとテキストを取得
		    posts.setTitle (postsForm.getTitle());
		    posts.setText(postsForm.getText());
		    //ユーザの取得
		    posts.setName(user.getName());
		    //現在日時を取得
		    posts.setDate(LocalDateTime.now());
		    
		    // データベースに保存
			postsRepository.save(posts);
		    
			// ユーザ一覧画面へリダイレクト
			return "redirect:/posts";
		}
}