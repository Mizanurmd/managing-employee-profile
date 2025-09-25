package com.employeeManagement.serviceImpl;

import com.employeeManagement.responseDto.AddressResponseDto;
import com.employeeManagement.dto.StudentDto;
import com.employeeManagement.responseDto.StudentResponseDto;
import com.employeeManagement.model.Address;
import com.employeeManagement.model.Student;
import com.employeeManagement.repository.AddressRepository;
import com.employeeManagement.repository.StudentRepository;
import com.employeeManagement.service.AddressService;
import com.employeeManagement.service.StudentService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final StudentRepository studentRepository;
    private final AddressRepository addressRepository;
    private final AddressService addressService;


    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, AddressRepository addressRepository, AddressService addressService) {
        this.studentRepository = studentRepository;
        this.addressRepository = addressRepository;
        this.addressService = addressService;

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
        // 1️ Create Basic student
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
        List<Address> savedAddresses = studentDto.getAddresses().stream().map(dto -> {
            dto.setStudentId(savedStudent.getId());
            return addressService.saveAddress(dto);
        }).toList();
        savedStudent.setAddresses(savedAddresses);
//

        //5 Convert to Response DTO
        return convertStudentToStudentResponseDto(savedStudent);
    }

    @Override
    public Optional<Student> studentSingleById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public StudentResponseDto updateStudent(long id, StudentDto studentDto, MultipartFile imagePath) {
        Student existingStudent = studentSingleById(id).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        existingStudent.setFirstName(studentDto.getFirstName());
        existingStudent.setLastName(studentDto.getLastName());
        existingStudent.setFatherName(studentDto.getFatherName());
        existingStudent.setMotherName(studentDto.getMotherName());
        existingStudent.setAdmissionNumber(studentDto.getAdmissionNumber());
        existingStudent.setDateOfBirth(studentDto.getDateOfBirth());
        existingStudent.setGender(studentDto.getGender());
        existingStudent.setEmail(studentDto.getEmail());
        existingStudent.setPhoneNumber(studentDto.getPhoneNumber());
        existingStudent.setStudentClass(studentDto.getStudentClass());
        existingStudent.setAdmissionDate(studentDto.getAdmissionDate());
        existingStudent.setSection(studentDto.getSection());
        existingStudent.setRollNumber(studentDto.getRollNumber());
        existingStudent.setActive(studentDto.isActive());
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                Path path = Paths.get(uploadDir).toAbsolutePath().normalize();
                // Create directory if not exists
                Files.createDirectories(path);
                // Clean file name
                String fileName = StringUtils.cleanPath(imagePath.getOriginalFilename());
                fileName = fileName.replace(" ", "_");
                Path targetLocation = Paths.get(uploadDir).resolve(fileName);
                Files.copy(imagePath.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                // Store this relative path in DB
                existingStudent.setProfileImagePath("/uploads/" + fileName);

            } catch (Exception e) {
                throw new RuntimeException("Could not store file: " + imagePath.getOriginalFilename(), e);
            }
        }

        Student updatedStudent = studentRepository.save(existingStudent);

        // Handle address updates (replace old with new)

        List<Address> updatedAddresses = studentDto.getAddresses().stream()
                .map(addressDto -> {
                    // Ensure the AddressDto has addressId
                    if (addressDto.getAddressId() != null) {
                        return addressService.updateAddress(addressDto.getAddressId(), addressDto);
                    } else {
                        // If no addressId, create a new address for this student
                        addressDto.setStudentId(updatedStudent.getId());
                        return addressService.saveAddress(addressDto);
                    }
                })
                .toList();

        return convertStudentToStudentResponseDto(updatedStudent);
    }

    @Override
    public Page<StudentResponseDto> getAllStudents(int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Student> students = studentRepository.findAll(pageable);
        return students.map(this::convertStudentToStudentResponseDto);
    }


    @Override
    public Optional<Student> deleteStudent(long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            studentRepository.deleteById(id);
        }
        return studentOptional;
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
