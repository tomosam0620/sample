select
  /*%expand*/*
from
  WORK_TIME
where
  USER_ID = /* userId */'a'
  and
  WORK_DATE = /* workDate */'2010-01-23'
