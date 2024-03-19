package org.hw_sorter.service.content;

import lombok.Getter;
import lombok.Setter;
import org.hw_sorter.hw_rdbms.content.Content;
import org.hw_sorter.service.character.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component @Getter @Setter
public class ContentCache {
    private List<Content> contents = new ArrayList<>();

    public void init(List<Content> contents) {
        this.contents.addAll(contents);
    }

    public Content getContent(Long id) {
        return this.contents.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow(EntityNotFoundException::new);
    }
}
