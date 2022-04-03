package edu.ntnu.Backend.Service;

import edu.ntnu.Backend.model.DAO.SubjectDAO;
import edu.ntnu.Backend.repository.SubjectRepository;
import edu.ntnu.Backend.service.SubjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SubjectServiceTest {
    SubjectDAO alquid = new SubjectDAO("alquid",2001,"Flight lessions for beginners",0);
    SubjectDAO idatt2075 = new SubjectDAO("idatt2075",2075,"Future tech for noobs",1);
    ArrayList<SubjectDAO> subjects = new ArrayList<>();
    SubjectService subjectService;
    @Mock
    SubjectRepository subjectRepository;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
        subjects.add(alquid);
        subjects.add(idatt2075);

        subjectService = new SubjectService(subjectRepository);

        Mockito.when(subjectRepository.findSubjectDAOBySubjectCodeAndSchoolYear("alquid",2001)).thenReturn(alquid);
        Mockito.when(subjectRepository.save(alquid)).thenReturn(null);
    }

    @Test
    void findSubjectBySubjectId() {
        Assertions.assertEquals(subjectService.findSubjectBySubjectId("alquid",2001), alquid);
        Assertions.assertNotEquals(subjectService.findSubjectBySubjectId("alquid",2001),idatt2075);
    }

    @Test
    void changeStatusOfQue() {
        Assertions.assertTrue(subjectService.changeStatusOfQue("alquid",2001));
        Assertions.assertFalse(subjectService.changeStatusOfQue("",0));
    }
}
