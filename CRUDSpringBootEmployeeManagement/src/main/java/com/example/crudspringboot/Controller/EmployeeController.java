package com.example.crudspringboot.Controller;

import com.example.crudspringboot.Service.EmployeeNotFoundException;
import com.example.crudspringboot.Service.EmployeeService;
import com.example.crudspringboot.Model.Employee;
import com.example.crudspringboot.Repository.EmployeeRepository;
import com.example.crudspringboot.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String showHomePage() {
        return "Homepage";
    }

    @GetMapping("/employees")
    public String showEmployeeList(Model model) {
        List<Employee> listEmployee = employeeService.listAll();
        model.addAttribute("listEmployees", listEmployee);
        return "employees";
    }

    @GetMapping("/employees/new")
    public String showNewForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("pageTitle","Add New User");
        return "employee_form";
    }

    @PostMapping("/employees/save")
    public String saveEmployees(Employee employee, RedirectAttributes redirectAttributes) {
        employeeService.save(employee);
        redirectAttributes.addFlashAttribute("message","The employee has been saved successfully.");
        return "redirect:/employees";
    }

    @GetMapping("/employees/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Employee employee = employeeService.get(id);
            model.addAttribute("employee", employee);
            model.addAttribute("pageTitle","Edit Employee (ID: " + id + ")");
            redirectAttributes.addFlashAttribute("message","The employee with ID: " + id + "has been updated successfully.");
            return "employee_form";
        } catch (EmployeeNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());
            return "redirect:/employees";
        }
    }

    @GetMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The employee with ID: " + id + " has been deleted successfully.");
        } catch (EmployeeNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/employees";
    }
}
