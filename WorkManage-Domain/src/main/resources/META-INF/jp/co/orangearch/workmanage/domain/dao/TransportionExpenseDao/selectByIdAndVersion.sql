select
  /*%expand*/*
from
  TRANSPORTION_EXPENSE
where
  USER_ID = /* userId */'a'
  and
  WORK_DATE = /* workDate */'2010-01-23'
  and
  TRANSPORT_NUMBER = /* transportNumber */1
  and
  VERSION = /* version */1
