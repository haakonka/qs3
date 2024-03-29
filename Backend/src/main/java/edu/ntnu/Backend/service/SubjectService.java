package edu.ntnu.Backend.service;

import edu.ntnu.Backend.model.DAO.SubjectDAO;
import edu.ntnu.Backend.model.DTO.SubjectDTO;
import edu.ntnu.Backend.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * A class meant to use the access-point made to the subject table in the database.
 * This class uses the methods from the
 * {@link edu.ntnu.Backend.repository.SubjectRepository SubjectRepository}
 */
@Service
@Transactional
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    /**
     * A method to save a subjectDAO object to the database.
     * @param subjectDAO the subjectDAO object to be saved.
     */
    public void saveNewSubject(SubjectDAO subjectDAO){
        subjectRepository.save(subjectDAO);
    }

    /**
     * A method to remove a subject from the database.
     * @param subjectDAO the subject to be removed.
     */
    public void removeSubject(SubjectDAO subjectDAO){
        subjectRepository.delete(findSubjectBySubjectId(subjectDAO.getSubjectCode(),subjectDAO.getSchoolYear()));
    }

    /**
     * A method to find a subject given the subject code and school year.
     * @param subjectCode the subject code of the subject.
     * @param schoolYear the school year for this subject.
     * @return returns the subject matching the given information.
     */
    public SubjectDAO findSubjectBySubjectId(String subjectCode, int schoolYear) {
        System.out.println("Finding the subject matching with the subject code: " + subjectCode + " and school year: " + schoolYear);
        return subjectRepository.findSubjectDAOBySubjectCodeAndSchoolYear(subjectCode,schoolYear);
    }

    /**
     * A method to change the status of the queue in a subject.
     * @param subjectCode the subject code of the subject.
     * @param schoolYear the school year for this subject.
     * @return returns false if the subject code is null or the school year is before NTNU, and true if not.
     */
    public Boolean changeStatusOfQue(String subjectCode, int schoolYear) {
        //Checks to see if the subject code is null or the school year is before NTNU.
        if(subjectCode == null || schoolYear <= 1759) {
            return false;
        }
        System.out.println("Trying to update the que status of a subject");
        SubjectDAO subject = subjectRepository.findSubjectDAOBySubjectCodeAndSchoolYear(subjectCode,schoolYear);
        if(subject.getStatusQue() == 1) {
            subject.setStatusQue(0);
        } else if(subject.getStatusQue() == 0) {
            subject.setStatusQue(1);
        }
        subjectRepository.save(subject);
        System.out.println("The status is changed");
        return true;
    }
}
