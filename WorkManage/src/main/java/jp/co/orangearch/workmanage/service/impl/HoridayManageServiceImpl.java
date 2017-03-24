package jp.co.orangearch.workmanage.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.orangearch.workmanage.component.CsvComponent;
import jp.co.orangearch.workmanage.component.util.DateUtils;
import jp.co.orangearch.workmanage.component.util.DateUtils.DateTimeFormat;
import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.dao.HoridayDao;
import jp.co.orangearch.workmanage.domain.entity.Horiday;
import jp.co.orangearch.workmanage.domain.exception.CsvHandleException;
import jp.co.orangearch.workmanage.domain.exception.SystemException;
import jp.co.orangearch.workmanage.service.HoridayCsvBean;
import jp.co.orangearch.workmanage.service.HoridayManageService;

@Service
public class HoridayManageServiceImpl implements HoridayManageService {

	@Autowired
	CsvComponent csvComponent;

	@Autowired
	HoridayDao horidayDao;

	@Override
	public List<Horiday> selectAll() {
		return horidayDao.selectAll();
	}

	@Override
	public List<HoridayCsvBean> read(InputStream stream) throws CsvHandleException{

		List<HoridayCsvBean> beans = new ArrayList<HoridayCsvBean>();
		try{
			beans = csvComponent.toBean(HoridayCsvBean.class, stream, "MS932", true);
		}catch(IOException e){
			throw new SystemException(MessageId.S003, e);
		}

		return beans;
	}

	@Override
	@Transactional
	public void update(List<HoridayCsvBean> lines) {
		for(HoridayCsvBean bean : lines){
			LocalDate targetDate = DateUtils.convertToLocalDate(bean.getDate(), DateTimeFormat.YYYY_M_D);
			Horiday e = horidayDao.selectById(targetDate);
			if(e == null){
				Horiday entity = new Horiday();
				entity.setDate(DateUtils.convertToLocalDate(bean.getDate(), DateTimeFormat.YYYY_M_D));
				entity.setHoridayName(bean.getHoridayName());
				entity.setVersion(0);
				horidayDao.insert(entity);
			}else{
				Horiday entity = new Horiday();
				entity.setDate(DateUtils.convertToLocalDate(bean.getDate(), DateTimeFormat.YYYY_M_D));
				entity.setHoridayName(bean.getHoridayName());
				entity.setRegistDate(e.getRegistDate());
				entity.setRegistUser(e.getRegistUser());
				entity.setVersion(e.getVersion());
				horidayDao.update(entity);
			}
		}
	}

}
