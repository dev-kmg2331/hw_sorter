package org.hw_sorter.hw_rdbms.character;

import jakarta.persistence.*;
import lombok.*;
import org.hw_sorter.hw_rdbms.common.Audit;
import org.hw_sorter.hw_rdbms.content.Content;
import org.hw_sorter.hw_rdbms.user.User;
import org.w3c.dom.CharacterData;

import java.util.HashMap;
import java.util.Map;

@Entity(name = "character_la")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString(exclude = {"contentCheck", "user"})
public class Character extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String name;

    private String classname;

    private int itemLevel;

    private String description;

    private boolean goldGenerate;

    @Enumerated(EnumType.STRING)
    private ClassRole role;


    @ElementCollection
    @CollectionTable(name = "content_check", joinColumns = @JoinColumn(name = "character_id"))
    @MapKeyJoinColumn(name = "content_id")
    @Column(name = "content_check")
    private final Map<Content, Boolean> contentCheck = new HashMap<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public enum ClassRole {
        DPS, SUP
    }

    public void update(String name, String classname, int itemLevel, String description, boolean goldGenerate, ClassRole role) {
        this.name = name;
        this.classname = classname;
        this.itemLevel = itemLevel;
        this.description = description;
        this.goldGenerate = goldGenerate;
        this.role = role;
    }
}
