package mj.song.microservices.core.management.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagementInfo {

    private String title;

    private User user;

    private List<Post> posts;
}
