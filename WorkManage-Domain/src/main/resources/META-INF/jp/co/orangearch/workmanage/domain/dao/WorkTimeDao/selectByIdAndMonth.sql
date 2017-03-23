select *
from WORK_TIME
where USER_ID = /* userId */1
and WORK_DATE >= /* from_date */'2017-04-01'
and WORK_DATE <= /* to_date */'2017-04-01'
;