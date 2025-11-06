package com.ManShirtShop.service.auth.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ManShirtShop.common.jwt.JwtUtil;
import com.ManShirtShop.dto.auth.LoginRequest;
import com.ManShirtShop.dto.auth.LoginResponse;
import com.ManShirtShop.entities.Customer;
import com.ManShirtShop.entities.Employee;
import com.ManShirtShop.repository.CustomerRepository;
import com.ManShirtShop.repository.EmployeeRepository;
import com.ManShirtShop.service.auth.AuthService;

import java.util.Arrays;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Tìm kiếm trong bảng Customer trước
        Optional<Customer> customerOpt = customerRepository.findByEmail(email);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            // Kiểm tra password và status (0 = active)
            if (customer.getPassword().equals(password) && customer.getStatus() == 0) {
                String token = jwtUtil.generateToken(email, "customer", customer.getId());
                String roleName = "ROLE_" + (customer.getRole() != null ? customer.getRole().getName() : "customer");
                return new LoginResponse(token, jwtUtil.getExpirationTime(), Arrays.asList(roleName));
            }
        }

        // Nếu không tìm thấy customer, tìm trong bảng Employee
        Optional<Employee> employeeOpt = employeeRepository.findByEmail(email);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            // Kiểm tra password và status (0 = active)
            if (employee.getPassword().equals(password) && employee.getStatus() == 0) {
                String token = jwtUtil.generateToken(email, "employee", employee.getId());
                String roleName = "ROLE_" + (employee.getRole() != null ? employee.getRole().getName() : "employee");
                return new LoginResponse(token, jwtUtil.getExpirationTime(), Arrays.asList(roleName));
            }
        }

        // Nếu không tìm thấy hoặc sai thông tin đăng nhập
        throw new RuntimeException("Invalid email or password");
    }
}