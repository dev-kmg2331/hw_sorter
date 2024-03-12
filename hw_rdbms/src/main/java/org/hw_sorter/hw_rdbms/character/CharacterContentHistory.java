package org.hw_sorter.hw_rdbms.character;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hw_sorter.hw_rdbms.common.Audit;
import org.hw_sorter.hw_rdbms.content.Content;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CharacterContentHistory extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    public CharacterContentHistory(Character character, Content content) {
        this.character = character;
        this.content = content;
    }
}
