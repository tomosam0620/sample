select
	U.USER_ID,
	U.ROLE_ID,
	U.AFFILIATION,
	U.POSITION,
	U.USER_ID,
	J.PROJECT_ID
from user U
left outer join JOIN_PROJECT J on
	U.USER_ID = J.USER_ID
	and J.START_DATE <= /* targetDate */'2017-03-01'
	and J.END_DATE >= /* targetDate */'2017-03-01'
order by
	U.USER_ID asc

