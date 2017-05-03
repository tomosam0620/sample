package jp.co.orangearch.workmanage.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import jp.co.orangearch.workmanage.component.util.DateUtils;
import jp.co.orangearch.workmanage.component.util.DateUtils.DateTimeFormat;
import jp.co.orangearch.workmanage.domain.constant.ClosingState;
import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.dao.TransportionExpenseDao;
import jp.co.orangearch.workmanage.domain.dao.WorkTimeStatusDao;
import jp.co.orangearch.workmanage.domain.entity.TransportionExpense;
import jp.co.orangearch.workmanage.domain.entity.WorkTimeStatus;
import jp.co.orangearch.workmanage.domain.exception.SystemException;
import jp.co.orangearch.workmanage.service.TransportExpenseService;


/**
 * 交通費サービスの実装クラスです。
 *
 * @author t-otsuka
 *
 */
@Service
public class TransportExpenseServiceImpl implements TransportExpenseService {

	@Autowired
	private TransportionExpenseDao transportionExpenseDao;

	@Autowired
	private WorkTimeStatusDao workTimeStatusDao;
	
	@Transactional
	@Override
	public void update(TransportionExpense transportionExpense, BindingResult errors) {
		checkInput(transportionExpense, errors);
		if(errors.hasErrors()){
			return;
		}
		
		Optional<TransportionExpense> entity = transportionExpenseDao.selectById(transportionExpense.getUserId(), transportionExpense.getWorkDate(), transportionExpense.getTransportNumber());

		int result = 1;
		String procName;
		if(entity.isPresent()){
			//update
			transportionExpense.setRegistUser(entity.get().getRegistUser());
			transportionExpense.setRegistDate(entity.get().getRegistDate());
			result = transportionExpenseDao.update(transportionExpense);
			procName = "更新処理";
		}else{
			List<TransportionExpense> regited = transportionExpenseDao.selectByUserIdAndWorkDate(transportionExpense.getUserId(), transportionExpense.getWorkDate(), SelectOptions.get().limit(1));
			Integer num = 0;
			if(regited.size() > 0){
				num = regited.get(0).getTransportNumber();
			}
			transportionExpense.setTransportNumber(num + 1);
			result = transportionExpenseDao.insert(transportionExpense);
			procName = "登録処理";
		}

		if(result != 1){
			throw new SystemException(MessageId.S001, new String[]{procName});
		}
	}
	
	/**
	 * 入力チェックを行います。
	 * 
	 * @param entity 交通費情報
	 * @param errors エラー情報
	 */
	private void checkInput(TransportionExpense entity, BindingResult errors){
		
		// ステータスチェック
		String month = DateUtils.convert(entity.getWorkDate(), DateTimeFormat.UUUU_MM);
		Optional<WorkTimeStatus> statusEntity = workTimeStatusDao.selectById(entity.getUserId(), month);
		if(statusEntity.isPresent()){
			if(ClosingState.CLOSED.equals(statusEntity.get().getStatus())){
				errors.reject(MessageId.M006.getValue());
				return;
			}
		}
		
		//入力チェック(未入力、型チェックはバリデーションでされているから不要)
	}

	@Override
	public List<TransportionExpense> selectTransportionInfo(String userId, LocalDate date) {
		LocalDate fistdate = DateUtils.getFirstDayOfMonth(date);
		LocalDate lastdate = DateUtils.getFinalDayOfMonth(date);
		return transportionExpenseDao.selectByUserIdAndDateRange(userId, fistdate, lastdate);
	}

	
	@Override
	public boolean checkInputForClose(String userId, LocalDate from, LocalDate to, BindingResult errors) {
		//TODO：交通費入力チェック 絞め時にチェックする内容あった？入力時に入力チェックしているからないかも。
		if(errors.hasErrors()){
			return false;
		}
		return true;
	}

	@Override
	public Optional<TransportionExpense> select(String userId, LocalDate date, Integer number) {
		return transportionExpenseDao.selectById(userId, date, number);
	}

	@Override
	public BigDecimal select(String userId, LocalDate date) {
		BigDecimal expense = BigDecimal.ZERO;
		List<TransportionExpense> transportInfos = transportionExpenseDao.selectByUserIdAndWorkDate(userId, date, SelectOptions.get());
		for(TransportionExpense info : transportInfos){
			expense = expense.add(BigDecimal.valueOf(info.getExpense()));
		}
		return expense;
	}

}
