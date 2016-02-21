package pl.marboz.myproject.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.marboz.myproject.dto.GitHubUserDTO;

import java.util.concurrent.Future;

/**
 * Created by Marcin Bozek on 2016-02-20.
 */
@Service
public class GitHubLookupService {

    private RestTemplate restTemplate = new RestTemplate();

    @Async
    public Future<GitHubUserDTO> findGitHubUser(String user) {
        GitHubUserDTO gitHubUserDTO = restTemplate.getForObject("https://api.github.com/users/" + user, GitHubUserDTO.class);

        return new AsyncResult<GitHubUserDTO>(gitHubUserDTO);
    }
}
