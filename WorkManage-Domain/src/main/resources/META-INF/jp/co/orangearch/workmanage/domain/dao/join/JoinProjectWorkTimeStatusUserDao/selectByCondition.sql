select
	U.USER_ID,
	U.AFFILIATION,
	S.WORK_MONTH,
	J.PROJECT_ID,
	S.STATUS
from user U
inner join JOIN_PROJECT J on
	U.USER_ID = J.USER_ID
	/*%if toDate != null */
	and J.START_DATE <= /* toDate */'2017-05-31'
	/*%end*/
	/*%if fromDate != null */
	and J.END_DATE >= /* fromDate */'2016-01-01'
	/*%end*/
	/*%if projectId != null */
	and J.PROJECT_ID = /* projectId */1
	/*%end*/
inner join WORK_TIME_STATUS S on
	U.USER_ID = S.USER_ID
	and S.WORK_MONTH >= DATE_FORMAT(J.START_DATE, '%Y-%m')
	and S.WORK_MONTH <= DATE_FORMAT(J.END_DATE, '%Y-%m')
	/*%if fromDate != null */
	and S.WORK_MONTH >= DATE_FORMAT(/* fromDate */'2017-01', '%Y-%m')
	/*%end*/
	/*%if toDate != null */
	and S.WORK_MONTH <= DATE_FORMAT(/* toDate */'2017-05', '%Y-%m')
	/*%end*/
where
	/*%if affiliation != null */
	and U.AFFILIATION = /* affiliation */1
	/*%end*/
	/*%if userId != null */
	and U.USER_ID = /* userId */1234567
	/*%end*/
order by
	S.WORK_MONTH desc,
	U.USER_ID asc

