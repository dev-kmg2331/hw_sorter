package org.hw_sorter.hw_rdbms.content;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Builder
@ToString
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int itemLevel;

    private String gold;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private int requiredPersonNum;

    public enum Difficulty {
        NORMAL, HARD, HELL, THE_FIRST
    }
}
