package com.myrestfulprojects.moviehub.service;

import com.myrestfulprojects.moviehub.model.enums.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
public class ExternalApiCastMemberServiceTest {
    @Autowired
    private ExternalApiCastMemberService externalApiCastMemberService;

    @Test
    @Transactional
    @WithMockUser(username = "mymockinguser")
    void shouldThrowEntityNotFoundException_whenInsertingNonExistingStaffMemberImdbId() {
        //given
        String staffMemberImdbId = "nm0000233";
        Department department = Department.STAR;
        String movieImdbId = "tt0232500";

        //when
        //then
        assertThrows(EntityNotFoundException.class,
                () -> externalApiCastMemberService.getStaffMemberFromApi(movieImdbId, staffMemberImdbId, department));
    }
}
