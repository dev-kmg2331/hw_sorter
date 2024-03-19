package org.hw_sorter.hw_rdbms.user;

import jakarta.persistence.*;
import lombok.*;
import org.hw_sorter.hw_rdbms.character.Character;
import org.hw_sorter.hw_rdbms.common.Audit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@ToString(exclude = "characters")
@Getter
public class User extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String username;

    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Character> characters = new ArrayList<>();
}
