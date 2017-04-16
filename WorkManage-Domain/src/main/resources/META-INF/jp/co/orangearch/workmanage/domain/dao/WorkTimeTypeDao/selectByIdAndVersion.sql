select
  /*%expand*/*
from
  WORK_TIME_TYPE
where
  WORK_TIME_CD = /* workTimeCd */1
  and
  VERSION = /* version */1
