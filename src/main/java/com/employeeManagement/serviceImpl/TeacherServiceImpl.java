package com.employeeManagement.serviceImpl;


import com.employeeManagement.dto.TeacherRequestDto;
import com.employeeManagement.dto.TeacherResponseDto;
import com.employeeManagement.enums.Gender;
import com.employeeManagement.model.Teacher;
import com.employeeManagement.model.TeacherBackup;
import com.employeeManagement.repository.TeacherBackupRepository;
import com.employeeManagement.repository.TeacherRepository;
import com.employeeManagement.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    private final TeacherRepository teacherRepository;
    private final TeacherBackupRepository teacherBackupRepository;
    private static final AtomicLong counter = new AtomicLong(1);

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherBackupRepository teacherBackupRepository) {
        this.teacherRepository = teacherRepository;
        this.teacherBackupRepository = teacherBackupRepository;
    }

    // generate teacher id =00000001 ......
    public String generateTeacherId() {
        String lastId = teacherRepository.findMaxTeacherId(); // e.g., "00000003"
        int nextId = (lastId != null ? Integer.parseInt(lastId) : 0) + 1;
        String formattedId = String.format("%08d", nextId);
        return formattedId;
    }

    @Override
    public TeacherResponseDto addTeacher(TeacherRequestDto teacherRequestDto, MultipartFile imagePath) {
        // ===== Validation: unique fields =====
        if (teacherRepository.existsByEmail(teacherRequestDto.getEmail())) {
            throw new RuntimeException("Email already exists: " + teacherRequestDto.getEmail());
        }
        if (teacherRepository.existsByMobile(teacherRequestDto.getMobile())) {
            throw new RuntimeException("Mobile number already exists: " + teacherRequestDto.getMobile());
        }
        if (teacherRepository.existsByNid(teacherRequestDto.getNid())) {
            throw new RuntimeException("NID already exists: " + teacherRequestDto.getNid());
        }

        Teacher teacher = new Teacher();
        // Basic fields
        teacher.setTeacherId(generateTeacherId());
        teacher.setName(teacherRequestDto.getName());
        teacher.setMobile(teacherRequestDto.getMobile());
        teacher.setEmail(teacherRequestDto.getEmail());
        teacher.setNid(teacherRequestDto.getNid());
        teacher.setDateOfBirth(teacherRequestDto.getDateOfBirth());
        teacher.setPresentAddress(teacherRequestDto.getPresentAddress());
        teacher.setPermanentAddress(teacherRequestDto.getPermanentAddress());
        teacher.setGender(teacherRequestDto.getGender());
        teacher.setSkills(teacherRequestDto.getSkills());
        teacher.setHighestEducation(teacherRequestDto.getHighestEducation());

        // photo if provided
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                Path path = Paths.get(uploadDir).toAbsolutePath().normalize();

                // Create directory if not exists
                Files.createDirectories(path);

                // Clean file name
                String fileName = StringUtils.cleanPath(imagePath.getOriginalFilename());

                // Save file to the folder
                Path targetLocation = path.resolve(fileName);
                Files.copy(imagePath.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                // Save file name/path to DB (assuming Teacher has a field `photoPath`)
                teacher.setProfileImagePath(targetLocation.toString());


            } catch (Exception e) {
                throw new RuntimeException("Could not store file: " + imagePath.getOriginalFilename(), e);
            }
        }
        Teacher savedTeacher = teacherRepository.save(teacher);

        return convertToDto(savedTeacher);
    }

    @Override
    public TeacherResponseDto updateTeacher(Long id, TeacherRequestDto teacherRequestDto, MultipartFile imagePath) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher Not Found with :: " + id));
        // update fields
        teacher.setName(teacherRequestDto.getName());
        teacher.setMobile(teacherRequestDto.getMobile());
        teacher.setEmail(teacherRequestDto.getEmail());
        teacher.setNid(teacherRequestDto.getNid());
        teacher.setDateOfBirth(teacherRequestDto.getDateOfBirth());
        teacher.setPresentAddress(teacherRequestDto.getPresentAddress());
        teacher.setPermanentAddress(teacherRequestDto.getPermanentAddress());
        teacher.setGender(teacherRequestDto.getGender());
        teacher.setSkills(teacherRequestDto.getSkills());
        teacher.setHighestEducation(teacherRequestDto.getHighestEducation());

        // Update photo if provided
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                Path path = Paths.get(uploadDir).toAbsolutePath().normalize();

                // Create directory if not exists
                Files.createDirectories(path);

                // Clean file name
                String fileName = StringUtils.cleanPath(imagePath.getOriginalFilename());

                // Save file to the folder
                Path targetLocation = path.resolve(fileName);
                Files.copy(imagePath.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                // Save file name/path to DB (assuming Teacher has a field `photoPath`)
                teacher.setProfileImagePath(targetLocation.toString());


            } catch (Exception e) {
                throw new RuntimeException("Could not store file: " + imagePath.getOriginalFilename(), e);
            }
        }
        Teacher updateTeacher = teacherRepository.save(teacher);
        return convertToDto(updateTeacher);

    }

    @Override
    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new EntityNotFoundException("Teacher not found");
        }
        teacherRepository.deleteById(id);
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher Not Found with :: " + id));
    }

    @Override
    public Page<Teacher> getAllTeachers(int page, int size, String sortBy, String sorDir) {
        Sort sort = sorDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return teacherRepository.findAll(pageable);
    }

    @Override
    public Page<TeacherResponseDto> searchTeacher(String name, String email, String mobile, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return teacherRepository.findAll((root, query, cb) -> {
            var predicate = cb.conjunction();
            if (name != null && !name.isEmpty())
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            if (mobile != null && !mobile.isEmpty())
                predicate = cb.and(predicate, cb.equal(root.get("mobile"), mobile));
            if (email != null && !email.isEmpty())
                predicate = cb.and(predicate, cb.equal(cb.lower(root.get("email")), email.toLowerCase()));
            return predicate;
        }, pageable).map(this::convertToDto);
    }

    // convert Teacher into dto
    private TeacherResponseDto convertToDto(Teacher teacher) {
        return TeacherResponseDto.builder()
                .teacherId(teacher.getTeacherId())
                .name(teacher.getName())
                .mobile(teacher.getMobile())
                .email(teacher.getEmail())
                .nid(teacher.getNid())
                .dateOfBirth(teacher.getDateOfBirth())
                .presentAddress(teacher.getPresentAddress())
                .permanentAddress(teacher.getPermanentAddress())
                .gender(Gender.valueOf(teacher.getGender().name()))
                .highestEducation(teacher.getHighestEducation())
                .skills(teacher.getSkills())
                .profileImagePath(teacher.getProfileImagePath())
                .build();
    }

    // Convert Teacher -> TeacherBackup
    private TeacherBackup convertToBackup(Teacher teacher) {
        return TeacherBackup.builder()
                .teacherId(teacher.getTeacherId())
                .name(teacher.getName())
                .mobile(teacher.getMobile())
                .email(teacher.getEmail())
                .nid(teacher.getNid())
                .dateOfBirth(teacher.getDateOfBirth())
                .presentAddress(teacher.getPresentAddress())
                .permanentAddress(teacher.getPermanentAddress())
                .gender(teacher.getGender().name()) // store enum as String
                .highestEducation(teacher.getHighestEducation())
                .profileImagePath(teacher.getProfileImagePath())
                .skills(new ArrayList<>(teacher.getSkills())) // prevent mutability issues
                .deletedAt(LocalDate.now())
                .build();
    }

    //Soft delete
    @Override
    public Teacher softDeleteTeacher(String teacherId) {
        Teacher teacher = teacherRepository.findByTeacherId(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher Not Found with :: " + teacherId));

        if (teacher.isDeleted() == true) {
            throw new RuntimeException("Teacher with ID " + teacherId + " is already deleted.");
        }

        // Backup record
        TeacherBackup backupEntity = convertToBackup(teacher);
        teacherBackupRepository.save(backupEntity);

        // Mark teacher as soft deleted
        teacher.setDeleted(true);
        teacher.setDeletedAt(LocalDate.now());
        return teacherRepository.save(teacher);
    }



}
