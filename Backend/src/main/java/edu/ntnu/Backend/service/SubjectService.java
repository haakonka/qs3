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

    public void changeStatusOfQue(String subjectCode, int schoolYear) {
        System.out.println("Trying to update the que status of a subject");
        SubjectDAO subject = subjectRepository.findSubjectDAOBySubjectCodeAndSchoolYear(subjectCode,schoolYear);
        if(subject.getStatusQue() == 1) {
            subject.setStatusQue(0);
        } else if(subject.getStatusQue() == 0) {
            subject.setStatusQue(1);
        }
        subjectRepository.save(subject);
        System.out.println("The status is changed");
    }
}
