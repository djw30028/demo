package com.michaelw.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
	@Autowired
	private EmployeeRepository repository;

	@RequestMapping("/")
	public String home() {
		Employee p = this.repository.findAll().iterator().next();
		return "How are you , " + p.getName() + "! MYSQL is working fine";
	}
}
