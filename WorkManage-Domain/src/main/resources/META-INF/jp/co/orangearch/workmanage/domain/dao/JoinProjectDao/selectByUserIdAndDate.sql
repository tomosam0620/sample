select
  /*%expand*/*
from
  JOIN_PROJECT
where
  USER_ID = /* userId */'a'
  and START_DATE <= /* targetDate */'2010-01-23'
  and END_DATE >= /* targetDate */'2010-01-23'
order by
  PROJECT_ID desc
