package com.cholnhial.ireviewmovies.repository;

import com.cholnhial.ireviewmovies.model.Role;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

@DataJpaTest
@NoArgsConstructor
public class ITRoleRepository {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void whenFindAll_thenReturn2RolesWithUniqueNames () {
        List<Role> roles = roleRepository.findAll();
        assertThat(roles, hasSize(2));
        assertThat(roles.stream().map(r -> r.getRole().name()).collect(Collectors.toList()),
                hasItems("ROLE_ADMIN", "ROLE_USER"));
    }

}
