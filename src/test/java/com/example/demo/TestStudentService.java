package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.example.demo.model.Student;
import com.example.demo.repo.StudentRepository;
import com.example.demo.service.StudentService;

@ExtendWith(MockitoExtension.class)
public class TestStudentService {
	
	@Mock
    private StudentRepository studentRepository;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        this.studentService = new StudentService(this.studentRepository);
    }

    // POSITIVE TEST CASES
    @Test
    void testCreateStudent() {
        // Given
        Student student = new Student("Varsha", "Rani", "varsha@gmail.com");
        // When
        when(studentRepository.save(student)).thenReturn(student);
        Student createdStudent = studentService.createStudent(student);
        // Then
        assertNotNull(createdStudent);
        assertEquals(student.getId(), createdStudent.getId());
        assertEquals(student.getFname(), createdStudent.getFname());
        assertEquals(student.getLname(), createdStudent.getLname());
    }

    @Test
    void testGetAllStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Varsha","Rani", "varsha@gmail.com"));
        students.add(new Student("Prachi", "Singh", "prachi@gmail.com"));
        when(studentRepository.findAll()).thenReturn(students);
        List<Student> retrievedStudents = studentService.getAllStudents();
        assertEquals(2, retrievedStudents.size());
    }

    @Test
    void testUpdateStudent() {
        Student existingStudent = new Student("Varsha", "Rani", "varsha@gmail.com");
        Student updatedStudent = new Student("Prachi", "Singh", "prachi@gmail.com");
        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(updatedStudent)).thenReturn(updatedStudent);
        Student result = studentService.updateStudent(1L, updatedStudent);
        assertNotNull(result);
        assertEquals("Singh", result.getLname());
    }

    @Test
    void testDeleteStudent() {
        long studentId = 1L;
        doNothing().when(studentRepository).deleteById(studentId);
        assertDoesNotThrow(() -> studentService.deleteStudent(studentId));
    }

    @Test
    void testGetStudentById_Exists() {
        long studentId = 1L;
        Student student = new Student("Varsha", "Rani", "varsha@gmail.com");
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        Student retrievedStudent = studentService.getStudentById(studentId);
        assertNotNull(retrievedStudent);
        assertEquals("Varsha", retrievedStudent.getFname());
        assertEquals("Rani", retrievedStudent.getLname());
    }

    @Test
    void testGetStudentById_NotFound() {
        long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        assertNull(studentService.getStudentById(studentId));
    }
    
    // NEGATIVE TEST CASES
    @Test
    void testUpdateNonExistentStudent() {
        long nonExistentStudentId = 1000L;
        Student updatedStudent = new Student("Varsha", "Rani", "varsha@gmail.com");
        when(studentRepository.findById(nonExistentStudentId)).thenReturn(Optional.empty());
        Student result = studentService.updateStudent(nonExistentStudentId, updatedStudent);
        assertNull(result);
    }

    @Test
    void testDeleteNonExistentStudent() {
        long nonExistentStudentId = 1000L;
        doThrow(EmptyResultDataAccessException.class).when(studentRepository).deleteById(nonExistentStudentId);
        assertDoesNotThrow(() -> studentService.deleteStudent(nonExistentStudentId));
    }
    
    @Test
    void testGetNonExistentStudent() {
        long nonExistentStudentId = 1000L;
        when(studentRepository.findById(nonExistentStudentId)).thenReturn(Optional.empty());
        Student result = studentService.getStudentById(nonExistentStudentId);
        assertNull(result);
    }

    @Test
    void testCreateStudentWithNullData() {
        Student studentWithNullData = new Student(null, null, null);
        when(studentRepository.save(studentWithNullData)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DataIntegrityViolationException.class, () -> studentService.createStudent(studentWithNullData));
    }
    
}
