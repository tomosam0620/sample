package jp.co.orangearch.workmanage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.orangearch.workmanage.domain.dao.CustomerDao;
import jp.co.orangearch.workmanage.domain.dao.ProjectDao;
import jp.co.orangearch.workmanage.domain.entity.Customer;
import jp.co.orangearch.workmanage.domain.entity.Project;
import jp.co.orangearch.workmanage.service.ProjectManageService;

@Service
public class ProjectManageServiceImpl implements ProjectManageService {

	@Autowired
	ProjectDao projectDao;

	@Autowired
	CustomerDao customerDao;

	@Override
	public List<Project> selectAll() {
		return projectDao.selectAll();
	}

	@Override
	public List<Customer> selectCustomers() {
		return customerDao.selectAll();
	}

	@Transactional
	@Override
	public int create(Project entity) {
		return projectDao.insert(entity);
	}

}
