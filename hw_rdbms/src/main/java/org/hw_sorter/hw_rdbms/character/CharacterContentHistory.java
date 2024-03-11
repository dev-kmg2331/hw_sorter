package org.hw_sorter.hw_rdbms.character;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hw_sorter.hw_rdbms.common.Audit;
import org.hw_sorter.hw_rdbms.content.Content;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CharacterContentHistory extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

}
