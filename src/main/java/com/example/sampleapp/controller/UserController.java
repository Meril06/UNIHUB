package com.example.sampleapp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sampleapp.model.Assignment;
import com.example.sampleapp.model.Attendance;
import com.example.sampleapp.model.Scholarship;
import com.example.sampleapp.model.Student;
import com.example.sampleapp.model.Teacher;
import com.example.sampleapp.repository.AssignmentRepository;
import com.example.sampleapp.repository.AttendanceRepository;
import com.example.sampleapp.repository.ScholarshipRepository;
import com.example.sampleapp.repository.StudentRepository;
import com.example.sampleapp.repository.TeacherRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired private StudentRepository     studentRepo;
    @Autowired private TeacherRepository     teacherRepo;
    @Autowired private AttendanceRepository  attendanceRepo;
    @Autowired private AssignmentRepository  assignmentRepo;
    @Autowired private ScholarshipRepository scholarshipRepo;

    // ─── HOME ────────────────────────────────────────────────────────────────
    @GetMapping("/")
    public String home() { return "login"; }

    // ─── STUDENT LOGIN ───────────────────────────────────────────────────────
    @GetMapping("/student-login")
    public String studentLoginPage() { return "student-login"; }

    @PostMapping("/student-login")
    public String studentLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session, Model model) {
        Optional<Student> student = studentRepo.findByUsernameAndPassword(username, password);
        if (student.isPresent()) {
            session.setAttribute("student", student.get());
            return "redirect:/student-dashboard";
        }
        model.addAttribute("error", "Invalid username or password.");
        return "student-login";
    }

    // ─── STUDENT REGISTER ────────────────────────────────────────────────────
    @GetMapping("/student-register")
    public String studentRegisterPage() { return "student-register"; }

    @PostMapping("/student-register")
    public String studentRegister(@RequestParam String name,
                                  @RequestParam String username,
                                  @RequestParam String email,
                                  @RequestParam String password,
                                  @RequestParam String department,
                                  @RequestParam int year,
                                  Model model) {
        if (studentRepo.existsByUsername(username)) {
            model.addAttribute("error", "Username '" + username + "' is already taken.");
            return "student-register";
        }
        Student s = new Student();
        s.setName(name); s.setUsername(username); s.setEmail(email);
        s.setPassword(password); s.setDepartment(department); s.setYear(year);
        studentRepo.save(s);
        model.addAttribute("success", "Account created! You can now log in.");
        return "student-register";
    }

    // ─── STUDENT DASHBOARD ───────────────────────────────────────────────────
    @GetMapping("/student-dashboard")
    public String studentDashboard(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) return "redirect:/student-login";

        List<Attendance>  attendance   = attendanceRepo.findByStudent(student);
        List<Assignment>  assignments  = assignmentRepo.findByStudent(student);
        List<Scholarship> scholarships = scholarshipRepo.findAll();

        long total   = attendance.size();
        long present = attendance.stream().filter(a -> "Present".equals(a.getStatus())).count();
        double pct   = total > 0 ? (present * 100.0 / total) : 0;

        LocalDate today = LocalDate.now();
        long overdue = assignments.stream()
            .filter(a -> "Pending".equals(a.getStatus()) && a.getDeadline() != null && a.getDeadline().isBefore(today))
            .count();

        model.addAttribute("student",       student);
        model.addAttribute("attendance",    attendance);
        model.addAttribute("assignments",   assignments);
        model.addAttribute("scholarships",  scholarships);
        model.addAttribute("attendancePct", String.format("%.1f", pct));
        model.addAttribute("shortageAlert", pct < 75);
        model.addAttribute("overdueCount",  overdue);
        model.addAttribute("today",         today);

        return "student-dashboard";
    }

    // ─── STUDENT: ADD ASSIGNMENT ─────────────────────────────────────────────
    @PostMapping("/student/assignment/add")
    public String studentAddAssignment(@RequestParam String title,
                                       @RequestParam String subject,
                                       @RequestParam String deadline,
                                       HttpSession session,
                                       RedirectAttributes ra) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) return "redirect:/student-login";

        Assignment a = new Assignment();
        a.setStudent(student);
        a.setTitle(title);
        a.setSubject(subject);
        a.setDeadline(LocalDate.parse(deadline));
        a.setStatus("Pending");
        assignmentRepo.save(a);
        ra.addFlashAttribute("assignMsg", "Assignment added.");
        return "redirect:/student-dashboard";
    }

    // ─── STUDENT: MARK ASSIGNMENT DONE ───────────────────────────────────────
    @PostMapping("/student/assignment/done/{id}")
    public String studentMarkDone(@PathVariable int id,
                                  HttpSession session,
                                  RedirectAttributes ra) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) return "redirect:/student-login";

        Optional<Assignment> opt = assignmentRepo.findById(id);
        if (opt.isPresent() && opt.get().getStudent().getId() == student.getId()) {
            Assignment a = opt.get();
            a.setStatus("Completed");
            assignmentRepo.save(a);
        }
        return "redirect:/student-dashboard";
    }

    // ─── STUDENT: DELETE ASSIGNMENT ──────────────────────────────────────────
    @PostMapping("/student/assignment/delete/{id}")
    public String studentDeleteAssignment(@PathVariable int id,
                                          HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) return "redirect:/student-login";

        Optional<Assignment> opt = assignmentRepo.findById(id);
        if (opt.isPresent() && opt.get().getStudent().getId() == student.getId()) {
            assignmentRepo.deleteById(id);
        }
        return "redirect:/student-dashboard";
    }

    // ─── TEACHER LOGIN ───────────────────────────────────────────────────────
    @GetMapping("/teacher-login")
    public String teacherLoginPage() { return "teacher-login"; }

    @PostMapping("/teacher-login")
    public String teacherLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session, Model model) {
        Optional<Teacher> teacher = teacherRepo.findByUsernameAndPassword(username, password);
        if (teacher.isPresent()) {
            session.setAttribute("teacher", teacher.get());
            return "redirect:/teacher-dashboard";
        }
        model.addAttribute("error", "Invalid username or password.");
        return "teacher-login";
    }

    // ─── TEACHER REGISTER ────────────────────────────────────────────────────
    @GetMapping("/teacher-register")
    public String teacherRegisterPage() { return "teacher-register"; }

    @PostMapping("/teacher-register")
    public String teacherRegister(@RequestParam String name,
                                  @RequestParam String username,
                                  @RequestParam String email,
                                  @RequestParam String password,
                                  @RequestParam String department,
                                  Model model) {
        if (teacherRepo.existsByUsername(username)) {
            model.addAttribute("error", "Username '" + username + "' is already taken.");
            return "teacher-register";
        }
        Teacher t = new Teacher();
        t.setName(name); t.setUsername(username); t.setEmail(email);
        t.setPassword(password); t.setDepartment(department);
        teacherRepo.save(t);
        model.addAttribute("success", "Teacher account created! You can now log in.");
        return "teacher-register";
    }

    // ─── TEACHER DASHBOARD ───────────────────────────────────────────────────
    @GetMapping("/teacher-dashboard")
    public String teacherDashboard(HttpSession session, Model model) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (teacher == null) return "redirect:/teacher-login";

        List<Student>    students    = studentRepo.findAll();
        List<Attendance> attendance  = attendanceRepo.findAll();
        List<Assignment> assignments = assignmentRepo.findAll();

        model.addAttribute("teacher",     teacher);
        model.addAttribute("students",    students);
        model.addAttribute("attendance",  attendance);
        model.addAttribute("assignments", assignments);
        model.addAttribute("today",       LocalDate.now().toString());

        return "teacher-dashboard";
    }

    // ─── TEACHER: ENROLL NEW STUDENT ─────────────────────────────────────────
    @PostMapping("/teacher/student/enroll")
    public String enrollStudent(@RequestParam String name,
                                @RequestParam String username,
                                @RequestParam String email,
                                @RequestParam String password,
                                @RequestParam String department,
                                @RequestParam int year,
                                HttpSession session,
                                RedirectAttributes ra) {
        if (session.getAttribute("teacher") == null) return "redirect:/teacher-login";

        if (studentRepo.existsByUsername(username)) {
            ra.addFlashAttribute("enrollError", "Username '" + username + "' is already taken.");
        } else {
            Student s = new Student();
            s.setName(name); s.setUsername(username); s.setEmail(email);
            s.setPassword(password); s.setDepartment(department); s.setYear(year);
            studentRepo.save(s);
            ra.addFlashAttribute("enrollSuccess", "Student '" + name + "' enrolled successfully.");
        }
        return "redirect:/teacher-dashboard";
    }

    // ─── TEACHER: ADD ASSIGNMENT FOR STUDENT ─────────────────────────────────
    @PostMapping("/teacher/assignment/add")
    public String teacherAddAssignment(@RequestParam int studentId,
                                       @RequestParam String title,
                                       @RequestParam String subject,
                                       @RequestParam String deadline,
                                       HttpSession session,
                                       RedirectAttributes ra) {
        if (session.getAttribute("teacher") == null) return "redirect:/teacher-login";

        Optional<Student> studentOpt = studentRepo.findById(studentId);
        if (studentOpt.isPresent()) {
            Assignment a = new Assignment();
            a.setStudent(studentOpt.get());
            a.setTitle(title);
            a.setSubject(subject);
            a.setDeadline(LocalDate.parse(deadline));
            a.setStatus("Pending");
            assignmentRepo.save(a);
            ra.addFlashAttribute("assignSuccess", "Assignment assigned to " + studentOpt.get().getName());
        }
        return "redirect:/teacher-dashboard";
    }

    // ─── TEACHER: MARK ASSIGNMENT COMPLETE ───────────────────────────────────
    @PostMapping("/teacher/assignment/complete/{id}")
    public String teacherMarkComplete(@PathVariable int id, HttpSession session) {
        if (session.getAttribute("teacher") == null) return "redirect:/teacher-login";

        Optional<Assignment> opt = assignmentRepo.findById(id);
        opt.ifPresent(a -> { a.setStatus("Completed"); assignmentRepo.save(a); });
        return "redirect:/teacher-dashboard";
    }

    // ─── TEACHER: ADD ATTENDANCE ─────────────────────────────────────────────
    @PostMapping("/teacher/attendance/add")
    public String teacherAddAttendance(@RequestParam int studentId,
                                       @RequestParam String subject,
                                       @RequestParam String date,
                                       @RequestParam String status,
                                       HttpSession session,
                                       RedirectAttributes ra) {
        if (session.getAttribute("teacher") == null) return "redirect:/teacher-login";

        Optional<Student> studentOpt = studentRepo.findById(studentId);
        if (studentOpt.isPresent()) {
            Attendance att = new Attendance();
            att.setStudent(studentOpt.get());
            att.setSubject(subject);
            att.setDate(LocalDate.parse(date));
            att.setStatus(status);
            attendanceRepo.save(att);
            ra.addFlashAttribute("attendSuccess", "Attendance marked for " + studentOpt.get().getName());
        }
        return "redirect:/teacher-dashboard";
    }

    // ─── TEACHER: UPDATE EXISTING ATTENDANCE ─────────────────────────────────
    @PostMapping("/teacher/attendance/update/{id}")
    public String teacherUpdateAttendance(@PathVariable int id,
                                          @RequestParam String status,
                                          HttpSession session) {
        if (session.getAttribute("teacher") == null) return "redirect:/teacher-login";

        Optional<Attendance> opt = attendanceRepo.findById(id);
        opt.ifPresent(a -> { a.setStatus(status); attendanceRepo.save(a); });
        return "redirect:/teacher-dashboard";
    }

    // ─── LOGOUT ──────────────────────────────────────────────────────────────
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}