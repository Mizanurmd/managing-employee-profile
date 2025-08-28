package com.employeeManagement.serviceImpl;

import com.employeeManagement.dto.TeacherRequestDto;
import com.employeeManagement.dto.TeacherResponseDto;
import com.employeeManagement.enums.Gender;
import com.employeeManagement.model.Teacher;
import com.employeeManagement.repository.TeacherRepository;
import com.employeeManagement.service.TeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private static final AtomicLong counter = new AtomicLong(1);

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    // generate teacher id =00000001 ......
    public String generateTeacherId() {
        return String.format("%08d", counter.getAndIncrement());
    }

    @Override
    public TeacherResponseDto addTeacher(TeacherRequestDto teacherRequestDto,String imagePath) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherRequestDto, teacher);
        teacher.setTeacherId(generateTeacherId());
        teacher.setProfileImagePath(imagePath);

        Teacher savedTeacher = teacherRepository.save(teacher);

        return convertToDto(savedTeacher);
    }

    @Override
    public TeacherResponseDto updateTeacher(Long id, TeacherRequestDto teacherRequestDto) {
        return null;
    }

    @Override
    public TeacherResponseDto deleteTeacher(Long id) {
        return null;
    }

    @Override
    public TeacherResponseDto getTeacherById(Long id) {
        return null;
    }

    @Override
    public Page<TeacherResponseDto> getAllTeachers(int page, int size, String sortBy, String order) {
        return null;
    }

    @Override
    public Page<TeacherResponseDto> searchTeacher(String name, String email, String mobile, int page, int size) {
        return null;
    }

    // convert Teacher into dto
    private TeacherResponseDto convertToDto(Teacher teacher) {
        return TeacherResponseDto.builder()
                .teacherId(teacher.getTeacherId())
                .name(teacher.getName())
                .mobile(teacher.getMobile())
                .email(teacher.getEmail())
                .nid(teacher.getNid())
                .gender(Gender.valueOf(teacher.getGender().name()))
                .highestEducation(teacher.getHighestEducation())
                .skills(teacher.getSkills())
                .profileImagePath(teacher.getProfileImagePath())
                .build();
    }
}
