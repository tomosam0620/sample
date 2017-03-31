select
  /*%expand*/*
from
  work_time
where
  USER_ID = /* userId */'a'
  and
  WORK_DATE = /* workDate */'2010-01-23'
  and
  VERSION = /* version */1
