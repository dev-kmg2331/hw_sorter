package org.hw_sorter.hw_rdbms.character;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hw_sorter.hw_rdbms.common.Audit;
import org.hw_sorter.hw_rdbms.user.User;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    private String name;

    private String itemLevel;

    private String description;

    private boolean goldGeneration;

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
