package com.employeeManagement.serviceImpl;

import com.employeeManagement.dto.AddressDto;
import com.employeeManagement.dto.AddressResponseDto;
import com.employeeManagement.dto.StudentDto;
import com.employeeManagement.dto.StudentResponseDto;
import com.employeeManagement.model.Address;
import com.employeeManagement.model.Student;
import com.employeeManagement.repository.AddressRepository;
import com.employeeManagement.repository.StudentRepository;
import com.employeeManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final StudentRepository studentRepository;
    private final AddressRepository addressRepository;


    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, AddressRepository addressRepository) {
        this.studentRepository = studentRepository;
        this.addressRepository = addressRepository;

    }

    // Generate Student Id No ex=000000001
    public String generateStudentIdNo() {
        String lastId = studentRepository.findMaxStudentId();
        long nextId = (lastId != null ? Integer.parseInt(lastId) : 0) + 1;
        String formatedId = String.format("%08d", nextId);
        return formatedId;
    }

    @Override
    public StudentResponseDto addStudent(StudentDto studentDto, MultipartFile imagePath) {
        // 1️ Create student
        Student student = Student.builder()
                .studentIdNo(generateStudentIdNo())
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .fatherName(studentDto.getFatherName())
                .motherName(studentDto.getMotherName())
                .admissionNumber(studentDto.getAdmissionNumber())
                .dateOfBirth(studentDto.getDateOfBirth())
                .gender(studentDto.getGender())
                .email(studentDto.getEmail())
                .phoneNumber(studentDto.getPhoneNumber())
                .studentClass(studentDto.getStudentClass())
                .admissionDate(studentDto.getAdmissionDate())
                .section(studentDto.getSection())
                .rollNumber(studentDto.getRollNumber())
                .active(studentDto.isActive())
                .build();

        // 2️ Handle file upload
        // photo if provided
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                Path path = Paths.get(uploadDir).toAbsolutePath().normalize();
                // Create directory if not exists
                Files.createDirectories(path);
                // Clean file name
                String fileName = StringUtils.cleanPath(imagePath.getOriginalFilename());
                fileName = fileName.replace(" ", "_"); // optional: replace spaces
                Path targetLocation = Paths.get(uploadDir).resolve(fileName);
                Files.copy(imagePath.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                // Store this relative path in DB
                student.setProfileImagePath("/uploads/" + fileName);

            } catch (Exception e) {
                throw new RuntimeException("Could not store file: " + imagePath.getOriginalFilename(), e);
            }
        }

        // 3️ Save student first
        Student savedStudent = studentRepository.save(student);

        // 4️ Save addresses
        List<Address> addresses = studentDto.getAddresses().stream().map(dto -> {
            Address a = Address.builder()
                    .street(dto.getStreet())
                    .city(dto.getCity())
                    .state(dto.getState())
                    .presentAddress(dto.getPresentAddress())
                    .permanentAddress(dto.getPermanentAddress())
                    .student(savedStudent)
                    .build();
            return a;
        }).toList();

        if (!addresses.isEmpty()) {
            addressRepository.saveAll(addresses);
            savedStudent.setAddresses(addresses);
        }

        // 5️ Map to response DTO
        List<AddressResponseDto> addressResponses = addresses.stream().map(a -> AddressResponseDto.builder()
                .addressId(a.getAddressId())
                .street(a.getStreet())
                .city(a.getCity())
                .state(a.getState())
                .presentAddress(a.getPresentAddress())
                .permanentAddress(a.getPermanentAddress())
                .build()).toList();

        //6 Convert to Response DTO
        return convertStudentToStudentResponseDto(savedStudent);
    }

    @Override
    public Student updateStudent(long id, StudentDto studentDto, MultipartFile imagePath) {
        return null;
    }

    @Override
    public Optional<Student> getStudent(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Student> deleteStudent(long id) {
        return Optional.empty();
    }

    //Convert Student into dto
    public StudentResponseDto convertStudentToStudentResponseDto(Student student) {
        if (student == null) return null;

        // Map addresses
        List<AddressResponseDto> addressResponses = new ArrayList<>();
        if (student.getAddresses() != null) {
            addressResponses = student.getAddresses().stream()
                    .map(address -> AddressResponseDto.builder()
                            .addressId(address.getAddressId())
                            .street(address.getStreet())
                            .city(address.getCity())
                            .state(address.getState())
                            .presentAddress(address.getPresentAddress())
                            .permanentAddress(address.getPermanentAddress())
                            .build())
                    .toList();
        }

        // Build StudentResponseDto
        return StudentResponseDto.builder()
                .id(student.getId())
                .studentIdNo(student.getStudentIdNo())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .fatherName(student.getFatherName())
                .motherName(student.getMotherName())
                .admissionNumber(student.getAdmissionNumber())
                .dateOfBirth(student.getDateOfBirth())
                .gender(student.getGender())
                .email(student.getEmail())
                .phoneNumber(student.getPhoneNumber())
                .studentClass(student.getStudentClass())
                .admissionDate(student.getAdmissionDate())
                .section(student.getSection())
                .rollNumber(student.getRollNumber())
                .profileImagePath(student.getProfileImagePath())
                .active(student.isActive())
                .addresses(addressResponses)
                .build();
    }


}
