package org.hw_sorter.hw_rdbms.content;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String itemLevel;

    private String gold;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private int requiredPersonNum;

    private enum Difficulty {
        NORMAL, HARD, HELL, THE_FIRST
    }
}
