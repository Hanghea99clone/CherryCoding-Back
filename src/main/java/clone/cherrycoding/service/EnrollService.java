package clone.cherrycoding.service;

import clone.cherrycoding.dto.ResponseDto;
import clone.cherrycoding.entity.Enroll;
import clone.cherrycoding.entity.Lecture;
import clone.cherrycoding.entity.User;
import clone.cherrycoding.exception.CustomException;
import clone.cherrycoding.exception.ErrorCode;
import clone.cherrycoding.repository.EnrollRepository;
import clone.cherrycoding.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class EnrollService {

    private final LectureRepository lectureRepository;
    private final EnrollRepository enrollRepository;

    @Transactional
    public ResponseDto<String> enroll(Long curriculumId, User user) {
        Lecture lecture = lectureRepository.findById(curriculumId).
                orElseThrow(()-> new CustomException(ErrorCode.NotFoundLecture));;

        if (enrollRepository.findByLectureIdAndUserId(curriculumId, user.getId()).isPresent()) {
            enrollRepository.deleteEnrollByLectureIdAndUserId(curriculumId, user.getId());
            lecture.enroll(lecture.getEnrollCnt() - 1);
            return ResponseDto.success("수강 취소 완료");
        }

        enrollRepository.save(new Enroll(user, lecture));
        lecture.enroll(lecture.getEnrollCnt() + 1);
        return ResponseDto.success("수강 신청 완료");
    }
}
