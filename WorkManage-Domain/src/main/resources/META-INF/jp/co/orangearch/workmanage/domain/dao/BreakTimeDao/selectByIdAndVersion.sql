select
  /*%expand*/*
from
  BREAK_TIME
where
  WORK_TIME_CD = /* workTimeCd */1
  and
  BREAK_START_TIME = /* breakStartTime */'12:34:56'
  and
  VERSION = /* version */1
