select
  /*%expand*/*
from
  WORK_TIME_STATUS
where
  USER_ID = /* userId */'a'
  and
  WORK_MONTH = /* workMonth */'a'
  and
  VERSION = /* version */1
