select
	U.USER_ID,
	U.AFFILIATION,
	J.PROJECT_ID,
	U.POSITION
from user U
/*%if projectId != null */
inner join JOIN_PROJECT J on
	J.PROJECT_ID = /* projectId */1
	and 
/*%else*/
left outer join JOIN_PROJECT J on
/*%end*/
	U.USER_ID = J.USER_ID
	and J.START_DATE <= /* toDate */'2017-03-01'
	and J.END_DATE >= /* fromDate */'2017-03-01'
where
	/*%if affiliationCd != null */
	U.AFFILIATION = /* affiliationCd */1
	/*%end*/
	/*%if userId != null */
	and U.USER_ID = /* userId */1
	/*%end*/
order by
	U.AFFILIATION asc,
	J.PROJECT_ID asc,
	U.POSITION asc,
	U.USER_ID asc

