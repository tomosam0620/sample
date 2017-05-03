package jp.co.orangearch.workmanage.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.validation.BindingResult;

import jp.co.orangearch.workmanage.domain.entity.TransportionExpense;

public interface TransportExpenseService {

	/**
	 * 交通費情報を取得します。
	 * 
	 * @param userId ユーザーID
	 * @param date 日付
	 * @return 交通費情報のリスト
	 */
	List<TransportionExpense> selectTransportionInfo(String userId, LocalDate date);

	/**
	 * 交通費情報を取得します。
	 * 
	 * @param userId ユーザーID
	 * @param date 日付
	 * @param number 交通費通番
	 * @return 交通費情報
	 */
	Optional<TransportionExpense> select(String userId, LocalDate date, Integer number);

	/**
	 * 指定日の交通費を取得します。
	 * 
	 * @param userId ユーザーID
	 * @param date 日付
	 * @return
	 */
	BigDecimal select(String userId, LocalDate date);

	/**
	 * 交通費情報を更新します。
	 * 引数のentityに該当する交通費情報がすでに登録されている場合、更新します。
	 * 引数のentityに該当する交通費情報が登録されていない場合、新規に登録します。
	 * 
	 * @param entity 交通費情報
	 * @param bindingResult エラー情報
	 */
	void update(TransportionExpense entity, BindingResult bindingResult);

	/**
	 * 締め処理用の入力チェックを行います。
	 * 
	 * @param userId ユーザーID
	 * @param from チェック開始日
	 * @param to チェック終了日
	 * @param errors エラー情報
	 * @return チェック結果(true:正常／false:エラー)
	 */
	boolean checkInputForClose(String userId, LocalDate from, LocalDate to, BindingResult errors);

}
