package clone.cherrycoding.service;

import clone.cherrycoding.dto.CurriculumResponseDto;
import clone.cherrycoding.dto.LectureRequestDto;
import clone.cherrycoding.dto.LectureResponseDto;
import clone.cherrycoding.dto.ResponseDto;
import clone.cherrycoding.entity.Lecture;
import clone.cherrycoding.entity.User;
import clone.cherrycoding.repository.EnrollRepository;
import clone.cherrycoding.repository.LectureRepository;
import clone.cherrycoding.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final EnrollRepository enrollRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseDto<List<LectureResponseDto>> getLecture() {
        List<Lecture> lectureList = lectureRepository.findAllByOrderByModifiedAtDesc();
        List<LectureResponseDto> dto = new ArrayList<>();

        for (Lecture lecture : lectureList) {
            dto.add(LectureResponseDto.of(lecture));
        }

        return ResponseDto.success(dto);
    }

    @Transactional
    public ResponseDto<List<CurriculumResponseDto>> getCurriculum(User user) {
        List<Lecture> lectureList = lectureRepository.findAllByOrderByModifiedAtDesc();
        List<CurriculumResponseDto> dto = new ArrayList<>();

        for (Lecture lecture : lectureList) {
            CurriculumResponseDto responseDto = CurriculumResponseDto.of(lecture);
            if (user != null) {
                responseDto.setEnrolled(enrollRepository.findByLectureIdAndUserId(lecture.getId(), user.getId()).isPresent());
            }
            dto.add(responseDto);
        }
        return ResponseDto.success(dto);

    }

    @Transactional
    public ResponseDto<String> add(LectureRequestDto requestDto, MultipartFile multipartFile, User user) throws IOException {
        String imageUrl = s3Uploader.uploadFiles(multipartFile, "images");
        lectureRepository.save(new Lecture(requestDto, imageUrl, user));

        return ResponseDto.success("강의 등록 성공");
    }

    @Transactional
    public ResponseDto<String> update(Long curriculumId, LectureRequestDto requestDto, MultipartFile multipartFile, User user) throws IOException {
        Lecture lecture = lectureRepository.findByIdAndUserId(curriculumId, user.getId()).orElseThrow(NullPointerException::new);
        String imageUrl = s3Uploader.uploadFiles(multipartFile, "images");
        lecture.update(requestDto, imageUrl);

        return ResponseDto.success("강의 수정 성공");
    }

    @Transactional
    public ResponseDto<String> delete(Long curriculumId, User user) {
        lectureRepository.findByIdAndUserId(curriculumId, user.getId()).orElseThrow(NullPointerException::new);
        lectureRepository.deleteById(curriculumId);

        return ResponseDto.success("강의 삭제 성공");
    }
}
