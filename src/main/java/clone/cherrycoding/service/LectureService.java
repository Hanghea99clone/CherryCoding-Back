package clone.cherrycoding.service;

import clone.cherrycoding.dto.*;
import clone.cherrycoding.entity.Enroll;
import clone.cherrycoding.entity.Lecture;
import clone.cherrycoding.entity.User;
import clone.cherrycoding.repository.EnrollRepository;
import clone.cherrycoding.repository.LectureRepository;
import clone.cherrycoding.repository.UserRepository;
import clone.cherrycoding.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final UserRepository userRepository;

    @Transactional
    public ResponseDto<List<LectureResponseDto>> getLecture() {
        List<Lecture> lectureList = lectureRepository.findAllByOrderByCreatedAtDesc();
        List<LectureResponseDto> dto = new ArrayList<>();

        for (Lecture lecture : lectureList) {
            dto.add(LectureResponseDto.of(lecture));
        }

        return ResponseDto.success(dto);
    }

    @Transactional
    public ResponseDto<List<CurriculumResponseDto>> getCurriculum(int page, int size, String sortBy, User user) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Lecture> lecturePage = lectureRepository.findAll(pageable);

        List<Lecture> lectureList = lecturePage.getContent();
        List<CurriculumResponseDto> dto = new ArrayList<>();

        for (Lecture lecture : lectureList) {
            CurriculumResponseDto.CurriculumResponseDtoBuilder builder = CurriculumResponseDto.builderOf(lecture);
            boolean isEnrolled = false;
            if (user != null) {
                isEnrolled = enrollRepository.findByLectureIdAndUserId(lecture.getId(), user.getId()).isPresent();
            }
            CurriculumResponseDto responseDto = builder.isEnrolled(isEnrolled)
                    .build();
            dto.add(responseDto);
        }
        return ResponseDto.success(dto);

    }

    @Transactional
    public ResponseDto<DetailResponseDto> getDetail(Long curriculumId, User user) {
        Lecture lecture = lectureRepository.findById(curriculumId).orElseThrow(NullPointerException::new);
        DetailResponseDto dto = DetailResponseDto.of(lecture);
        boolean isEnrolled = false;
        if (user != null) {
            isEnrolled = enrollRepository.findByLectureIdAndUserId(lecture.getId(), user.getId()).isPresent();
        }
        dto.setEnrolled(isEnrolled);

        return ResponseDto.success(dto);
    }

    @Transactional
    public ResponseDto<List<CurriculumResponseDto>> getUserCurriculum(User user) {

        List<Enroll> enrollList = enrollRepository.findAllByUserId(user.getId());

        List<CurriculumResponseDto> dto = new ArrayList<>();
        for (Enroll enroll : enrollList) {
            CurriculumResponseDto.CurriculumResponseDtoBuilder builder = CurriculumResponseDto.builderOf(enroll.getLecture());
            CurriculumResponseDto responseDto = builder.isEnrolled(true)
                    .build();
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

    public ResponseDto<String> update(Long curriculumId, LectureRequestDto requestDto, MultipartFile multipartFile, User user) throws IOException {
        Lecture lecture = lectureRepository.findByIdAndUserId(curriculumId, user.getId()).orElseThrow(NullPointerException::new);
        String imageUrl = s3Uploader.uploadFiles(multipartFile, "images");
        lecture.update(requestDto, imageUrl);

        return ResponseDto.success("강의 수정 성공");
    }

    @Transactional
    public ResponseDto<String> delete(Long curriculumId, User user) {
        Lecture lecture = lectureRepository.findByIdAndUserId(curriculumId, user.getId()).orElseThrow(NullPointerException::new);
        lectureRepository.deleteById(lecture.getId());

        return ResponseDto.success("강의 삭제 성공");
    }
}
