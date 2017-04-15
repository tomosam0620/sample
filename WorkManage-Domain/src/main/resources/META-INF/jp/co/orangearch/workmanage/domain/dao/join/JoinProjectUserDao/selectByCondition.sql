select
	U.USER_ID,
	U.ROLE_ID,
	U.AFFILIATION,
	U.POSITION,
	U.USER_ID,
	J.PROJECT_ID
from user U
/*%if projectId != null */
inner join JOIN_PROJECT J on
	J.PROJECT_ID = /* projectId */1
	and 
/*%else*/
left outer join JOIN_PROJECT J on
/*%end*/
	U.USER_ID = J.USER_ID
	and J.START_DATE <= /* targetDate */'2017-03-01'
	and J.END_DATE >= /* targetDate */'2017-03-01'
where
	/*%if roleId != null */
	U.ROLE_ID = /* roleId */1
	/*%end*/
	/*%if affiliation != null */
	and U.AFFILIATION = /* affiliation */1
	/*%end*/
	/*%if position != null */
	and U.POSITION = /* position */1
	/*%end*/
order by
	U.USER_ID asc

