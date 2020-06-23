package kr.ac.ks.app.controller;

import kr.ac.ks.app.domain.Student;
import kr.ac.ks.app.repository.StudentRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students/new")
    public String showStudentForm(Model model) {
        model.addAttribute("studentForm", new StudentForm());
        return "students/studentForm";
    }

    @PostMapping("/students/new")
    public String createStudent(@Valid StudentForm studentForm, BindingResult result) {
        if (result.hasErrors()) {
            return "students/studentForm";
        }

        Student student = new Student();
        student.setName(studentForm.getName());
        student.setEmail(studentForm.getEmail());
        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/students")
    public String list(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "students/studentList";
    }

    @GetMapping("students/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id){
        studentRepository.deleteById(id);
        return "redirect:/students";
    }

    @GetMapping("students/{id}")
    public String updateShowStudent(@PathVariable("id") Long id, Model model) {
        Student student = studentRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("Invalid student id:" + id));
        StudentForm studentForm = new StudentForm();
        studentForm.setName(student.getName());
        studentForm.setEmail(student.getEmail());
        model.addAttribute("studentForm",studentForm);

        return "students/studentForm";
    }

    @PostMapping("students/{id}")
    public String updateStudent(@Valid StudentForm studentForm, @PathVariable("id") Long id, BindingResult result)  {
        if (result.hasErrors()) {
            return "students/studentForm";
        }
        Student student = studentRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("Invalid student id:" + id));
        student.setName(studentForm.getName());
        student.setEmail(studentForm.getEmail());
        studentRepository.save(student);
        return "redirect:/students";
    }

}
