package edu.ntnu.Backend.service;

import edu.ntnu.Backend.model.DAO.SubjectDAO;
import edu.ntnu.Backend.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public SubjectDAO findSubjectBySubjectId(String subjectCode, int schoolYear) {
        System.out.println("Finding the subject matching with the subject code: " + subjectCode + " and school year: " + schoolYear);
        return subjectRepository.findSubjectDAOBySubjectCodeAndSchoolYear(subjectCode,schoolYear);
    }
}
