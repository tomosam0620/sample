package jp.co.orangearch.workmanage.service;

import java.util.List;

import jp.co.orangearch.workmanage.domain.entity.Customer;
import jp.co.orangearch.workmanage.domain.entity.Project;

public interface ProjectManageService {

	List<Project> selectAll();
	
	List<Customer> selectCustomers();
	
	int create(Project entity);
}
