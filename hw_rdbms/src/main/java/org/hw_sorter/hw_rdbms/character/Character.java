package org.hw_sorter.hw_rdbms.character;

import jakarta.persistence.*;
import lombok.*;
import org.hw_sorter.hw_rdbms.common.Audit;
import org.hw_sorter.hw_rdbms.user.User;

import java.util.List;

@Entity(name = "character_la")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString(exclude = {"characterContents", "user"})
public class Character extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String name;

    private String itemLevel;

    private String description;

    private boolean goldGenerate;

    private int contentCount;

    @Enumerated(EnumType.STRING)
    private ClassRole role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "character")
    private List<CharacterContent> characterContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public enum ClassRole {
        DPS, SUP
    }
}
